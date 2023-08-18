package ru.loolzaaa.youkassa.client;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class ApiClientBuilderTest {

    ApiClientBuilder builder;

    @Test
    void shouldThrowExceptionsIfInvalidData() {
        builder = ApiClientBuilder.newBuilder();

        assertThrows(AssertionError.class, () -> builder.apiUrl(null));
        assertThrows(AssertionError.class, () -> builder.configureOAuth(null));
        assertThrows(AssertionError.class, () -> builder.configureBasicAuth(null, null));
        assertThrows(AssertionError.class, () -> builder.configureBasicAuth("test", null));
        assertThrows(AssertionError.class, () -> builder.configureBasicAuth(null, "test"));
    }

    @Test
    void shouldThrowExceptionIfMoreThanOneAuthMethodConfigured() {
        builder = ApiClientBuilder.newBuilder();
        builder.configureOAuth("test");
        assertThrows(IllegalArgumentException.class, () -> builder.configureBasicAuth("test", "test"));

        builder = ApiClientBuilder.newBuilder();
        builder.configureBasicAuth("test", "test");
        assertThrows(IllegalArgumentException.class, () -> builder.configureOAuth("test"));
    }

    @Test
    void shouldThrowExceptionIfNoAuthMethodConfigured() {
        assertThrows(IllegalArgumentException.class, () -> ApiClientBuilder.newBuilder().build());
    }

    @Test
    void shouldCreatesApiClientWithOAuth() throws Exception {
        builder = ApiClientBuilder.newBuilder();
        builder.apiUrl("https://example.com/api").configureOAuth("token");

        ApiClient client = assertDoesNotThrow(() -> builder.build());

        Field endpointField = client.getClass().getDeclaredField("endpoint");
        endpointField.setAccessible(true);
        String actualEndpoint = (String) endpointField.get(client);
        Field authHeaderSupplierField = client.getClass().getDeclaredField("authHeaderSupplier");
        authHeaderSupplierField.setAccessible(true);
        Supplier<String> authHeaderSupplier = (Supplier<String>) authHeaderSupplierField.get(client);

        assertEquals("https://example.com/api", actualEndpoint);
        assertEquals("Bearer token", authHeaderSupplier.get());
    }

    @Test
    void shouldCreatesApiClientWithBasicAuth() throws Exception {
        builder = ApiClientBuilder.newBuilder();
        builder.apiUrl("https://example.com/api").configureBasicAuth("user", "password");

        ApiClient client = assertDoesNotThrow(() -> builder.build());

        Field endpointField = client.getClass().getDeclaredField("endpoint");
        endpointField.setAccessible(true);
        String actualEndpoint = (String) endpointField.get(client);
        Field authHeaderSupplierField = client.getClass().getDeclaredField("authHeaderSupplier");
        authHeaderSupplierField.setAccessible(true);
        Supplier<String> authHeaderSupplier = (Supplier<String>) authHeaderSupplierField.get(client);

        assertEquals("https://example.com/api", actualEndpoint);
        assertEquals("Basic dXNlcjpwYXNzd29yZA==", authHeaderSupplier.get());
    }
}