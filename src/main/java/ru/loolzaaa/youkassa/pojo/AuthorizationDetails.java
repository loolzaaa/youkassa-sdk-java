package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationDetails {
    @JsonProperty("rrn")
    private String rrn;
    @JsonProperty("auth_code")
    private String authCode;
    @JsonProperty("three_d_secure")
    private ThreeDSecure threeDSecure;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ThreeDSecure {
        @JsonProperty("applied")
        private Boolean applied;
    }
}
