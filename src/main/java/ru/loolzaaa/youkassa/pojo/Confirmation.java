package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Confirmation {
    @JsonProperty("type")
    private String type;
    @JsonProperty("confirmation_token")
    private String confirmationToken;
    @JsonProperty("confirmation_url")
    private String confirmationUrl;
    @JsonProperty("enforce")
    private boolean enforce;
    @JsonProperty("locale")
    private String locale;
    @JsonProperty("return_url")
    private String returnUrl;

    public static class Type {
        public static final String EMBEDDED = "embedded";
        public static final String REDIRECT = "redirect";
    }
}
