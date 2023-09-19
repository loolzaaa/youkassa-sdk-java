package ru.loolzaaa.youkassa.client;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Supplier;

/**
 * This class implement builder pattern for {@link ApiClient}.
 * <p>
 * Compared with constructor creation of {@link ApiClient},
 * this option additionally provides some sanity checks.
 * <p>
 * Usage:
 * <pre>
 * {@code
 * ApiClient client = ApiClientBuilder.newBuilder()
 *     .configureBasicAuth("shop_id", "secret_key")
 *     .build();
 * }
 * </pre>
 *
 * @see ApiClient
 */

public class ApiClientBuilder {

    private String apiUrl = "https://api.yookassa.ru/v3";
    private String accountId;
    private String secretKey;
    private String authToken;

    private ApiClientBuilder() {
    }

    /**
     * Sets API url endpoint for client.
     *
     * @param apiUrl api endpoint url
     * @return builder itself
     */

    public ApiClientBuilder apiUrl(String apiUrl) {
        assert apiUrl != null : "apiUrl must not be null";
        this.apiUrl = apiUrl;
        return this;
    }

    /**
     * Authentication configuration via Basic auth method.
     * <p>
     * Only one method (Basic/OAuth) of authentication
     * can be configured.
     *
     * @param accountId id of yookassa shop/account
     * @param secretKey secret key of yookassa shop/account
     * @return builder itself
     * @throws IllegalArgumentException if OAuth token not null
     */

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

    /**
     * Authentication configuration via OAuth method.
     * <p>
     * Only one method (Basic/OAuth) of authentication
     * can be configured.
     *
     * @param authToken OAuth client token
     * @return builder itself
     * @throws IllegalArgumentException if accountId or secretKey not null
     */

    public ApiClientBuilder configureOAuth(String authToken) {
        assert authToken != null : "authToken must not be null";
        if (this.accountId != null || this.secretKey != null) {
            throw new IllegalArgumentException("Could not configure Basic auth with OAuth");
        }
        this.authToken = authToken;
        return this;
    }

    /**
     * Created {@link ApiClient} instance.
     * <p>
     * Construct authentication header value supplier
     * based on configured authentication method.
     *
     * @return instance of {@link ApiClient}
     * @throws IllegalArgumentException if authentication not configured
     */

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

    /**
     * Creates new builder for {@link ApiClient}.
     *
     * @return new builder instance
     */

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
