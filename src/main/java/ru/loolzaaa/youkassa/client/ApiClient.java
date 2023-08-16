package ru.loolzaaa.youkassa.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class ApiClient {

    private static final String AUTH_HEADER_NAME = "Authorization";

    private static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";
    private static final String CONTENT_TYPE_HEADER_VALUE = "application/json";

    private final ObjectMapper mapper = new ObjectMapper();

    private final HttpClient client = HttpClient.newHttpClient();

    private final String endpoint;
    private final Supplier<String> authHeaderSupplier;

    public <T> T sendRequest(String method, String path, Map<String, String> headers, RequestValidated body, Class<T> responseClass) {
        String response = execute(method, path, headers, body);
        try {
            return mapper.readValue(response, responseClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T sendRequest(String method, String path, Map<String, String> headers, RequestValidated body, TypeReference<T> typeReference) {
        String response = execute(method, path, headers, body);
        try {
            return mapper.readValue(response, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String execute(String method, String path, Map<String, String> headers, RequestValidated body) {
        if (body != null) {
            body.validate();
        }

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(endpoint + path))
                .method(method, toJsonPublisher(body))
                .header(CONTENT_TYPE_HEADER_NAME, CONTENT_TYPE_HEADER_VALUE)
                .header(AUTH_HEADER_NAME, authHeaderSupplier.get());
        if (headers != null) {
            headers.forEach(requestBuilder::header);
        }
        HttpRequest request = requestBuilder.build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                handleRequestError(response.body());
            }
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleRequestError(String error) {
        throw new RuntimeException(error);
    }

    private BodyPublisher toJsonPublisher(Object o) {
        try {
            return HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(o), StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
