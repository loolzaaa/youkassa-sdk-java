package ru.loolzaaa.youkassa.processors;

import com.fasterxml.jackson.core.type.TypeReference;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.client.RequestBody;

import java.util.Map;
import java.util.function.Supplier;

public class FakeApiClient extends ApiClient {

    String method;
    String path;
    Map<String, String> headers;
    RequestBody body;

    public FakeApiClient(String endpoint, Supplier<String> authHeaderSupplier) {
        super(endpoint, authHeaderSupplier);
    }

    @Override
    public <T> T sendRequest(String method, String path, Map<String, String> headers, RequestBody body, Class<T> responseClass) {
        this.method = method;
        this.path = path;
        this.headers = headers;
        this.body = body;
        return null;
    }

    @Override
    public <T> T sendRequest(String method, String path, Map<String, String> headers, RequestBody body, TypeReference<T> typeReference) {
        this.method = method;
        this.path = path;
        this.headers = headers;
        this.body = body;
        return null;
    }
}
