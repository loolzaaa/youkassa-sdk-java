package ru.loolzaaa.youkassa.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Http client for YooKassa API server.
 * <p>
 * This client use Java 11 {@link HttpClient} for communication
 * and Jackson library for request/response body
 * serialization/deserialization respectively.
 * <p>
 * All requests used authentication routine supplied
 * by {@link #authHeaderSupplier} with application/json
 * content-type header.
 * <p>
 * Success status code only 200.
 * <p>
 * Usually, instance of this class created by {@link ApiClientBuilder}.
 */

public class ApiClient {

    private static final String AUTH_HEADER_NAME = "Authorization";

    private static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";
    private static final String CONTENT_TYPE_HEADER_VALUE = "application/json";

    private final ObjectMapper mapper = new ObjectMapper();

    private final HttpClient client = HttpClient.newHttpClient();

    private final String endpoint;
    private final Supplier<String> authHeaderSupplier;

    /**
     * Created {@link ApiClient} instance.
     * <p>
     * Api endpoint and authentication header supplier
     * must not be null.
     *
     * @param endpoint           YooKassa API server endpoint
     * @param authHeaderSupplier authentication header value supplier
     */

    public ApiClient(String endpoint, Supplier<String> authHeaderSupplier) {
        assert endpoint != null : "API endpoint must not be null";
        assert authHeaderSupplier != null : "authentication header value supplier must not be null";
        this.endpoint = endpoint;
        this.authHeaderSupplier = authHeaderSupplier;
    }

    /**
     * Invoke execute method and deserialize response
     * for single entity.
     *
     * @param method        http method
     * @param path          request path with query params
     * @param headers       additional headers
     * @param body          request body
     * @param responseClass response type class
     * @param <T>           type of returned entity
     * @return requested deserialized entity
     */

    public <T> T sendRequest(String method, String path, Map<String, String> headers, RequestBody body, Class<T> responseClass) {
        String response = execute(method, path, headers, body);
        try {
            return mapper.readValue(response, responseClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Invoke execute method and deserialize response
     * for entities collection.
     *
     * @param method        http method
     * @param path          request path with query params
     * @param headers       additional headers
     * @param body          request body
     * @param typeReference jackson type reference
     *                      for response
     * @param <T>           type of returned entity
     * @return requested deserialized entities collection
     */

    public <T> T sendRequest(String method, String path, Map<String, String> headers, RequestBody body, TypeReference<T> typeReference) {
        String response = execute(method, path, headers, body);
        try {
            return mapper.readValue(response, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String execute(String method, String path, Map<String, String> headers, RequestBody body) {
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
