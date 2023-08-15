package ru.loolzaaa.youkassa.client;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Supplier;

public class ApiClientBuilder {
    private String apiUrl = "https://api.yookassa.ru/v3";
    private String accountId;
    private String secretKey;
    private String authToken;

    private ApiClientBuilder() {
    }

    public ApiClientBuilder apiUrl(String apiUrl) {
        assert apiUrl != null : "apiUrl must not be null";
        this.apiUrl = apiUrl;
        return this;
    }

    public ApiClientBuilder configureBasicAuth(String accountId, String secretKey) {
        assert accountId != null : "accountId must not be null";
        assert secretKey != null : "secretKey must not be null";
        if (this.authToken != null) {
            throw new IllegalArgumentException("Could not configure Basic auth with OAuth");
        }
        this.accountId = accountId;
        this.secretKey = secretKey;
        return this;
    }

    public ApiClientBuilder configureOAuth(String authToken) {
        assert authToken != null : "authToken must not be null";
        if (this.accountId != null || this.secretKey != null) {
            throw new IllegalArgumentException("Could not configure Basic auth with OAuth");
        }
        this.authToken = authToken;
        return this;
    }

    public ApiClient build() {
        if (!isAuthConfigured()) {
            throw new IllegalArgumentException("Basic auth or OAuth configuration are required");
        }
        Supplier<String> authHeaderSupplier = () -> {
            if (this.authToken != null) {
                return "Bearer " + this.authToken;
            } else {
                String decodedAuthString = this.accountId + ":" + this.secretKey;
                String encodedAuthString = Base64.getEncoder()
                        .encodeToString(decodedAuthString.getBytes(StandardCharsets.UTF_8));
                return "Basic " + encodedAuthString;
            }
        };
        return new ApiClient(this.apiUrl, authHeaderSupplier);
    }

    public static ApiClientBuilder newBuilder() {
        return new ApiClientBuilder();
    }

    private boolean isAuthConfigured() {
        if (this.accountId != null && this.secretKey != null && this.authToken == null) {
            // Basic auth configuration
            return true;
        } else {
            // OAuth configuration or not
            return this.accountId == null && this.secretKey == null && this.authToken != null;
        }
    }
}
