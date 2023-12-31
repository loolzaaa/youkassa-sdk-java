package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.Validated;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Confirmation implements Validated {

    private static final int MAX_RETURN_URL_LENGTH = 2048;

    @JsonProperty("type")
    private String type;
    @JsonProperty("confirmation_token")
    private String confirmationToken;
    @JsonProperty("confirmation_url")
    private String confirmationUrl;
    @JsonProperty("confirmation_data")
    private String confirmationData;
    @JsonProperty("enforce")
    private Boolean enforce;
    @JsonProperty("locale")
    private String locale;
    @JsonProperty("return_url")
    private String returnUrl;

    public static class Type {
        public static final String EMBEDDED = "embedded";
        public static final String EXTERNAL = "external";
        public static final String MOBILE_APPLICATION = "mobile_application";
        public static final String QR_CODE = "qr";
        public static final String REDIRECT = "redirect";
    }

    @Override
    public void validate() {
        if (type == null) {
            throw new IllegalArgumentException("Type must not be null");
        }
        if (type.equals(Type.MOBILE_APPLICATION) && returnUrl == null) {
            throw new IllegalArgumentException("Return url must not be null if type " + Type.MOBILE_APPLICATION);
        }
        if (returnUrl != null && returnUrl.length() > MAX_RETURN_URL_LENGTH) {
            throw new IllegalArgumentException("Too long return url. Max length: " + MAX_RETURN_URL_LENGTH);
        }
    }
}
