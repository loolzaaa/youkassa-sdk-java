package ru.loolzaaa.youkassa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.RequestValidated;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements RequestValidated {
    @JsonProperty("id")
    private String id;
    @JsonProperty("status")
    private String status;
    @JsonProperty("amount")
    private Amount amount;
    @JsonProperty("income_amount")
    private Amount incomeAmount;
    @JsonProperty("description")
    private String description;
    @JsonProperty("recipient")
    private Recipient recipient;
    //private PaymentMethod paymentMethod;
    @JsonProperty("captured_at")
    private String capturedAt;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("expires_at")
    private String expiresAt;
    @JsonProperty("confirmation")
    private Confirmation confirmation;
    @JsonProperty("test")
    private boolean test;
    @JsonProperty("refunded_amount")
    private Amount refundedAmount;
    @JsonProperty("paid")
    private boolean paid;
    @JsonProperty("refundable")
    private boolean refundable;
    @JsonProperty("receipt_registration")
    private String receiptRegistration;
    @JsonProperty("metadata")
    private Map<String, String> metadata;
    @JsonProperty("cancellation_details")
    private CancellationDetails cancellationDetails;
    @JsonProperty("authorization_details")
    private AuthorizationDetails authorizationDetails;
    @JsonProperty("transfers")
    private Transfers transfers;
    @JsonProperty("deal")
    private Deal deal;
    @JsonProperty("merchant_customer_id")
    private String merchantCustomerId;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Amount {
        @JsonProperty("value")
        private String value;
        @JsonProperty("currency")
        private String currency;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Recipient {
        @JsonProperty("account_id")
        private String accountId;
        @JsonProperty("gateway_id")
        private String gatewayId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Confirmation {
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

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CancellationDetails {
        @JsonProperty("party")
        private String party;
        @JsonProperty("reason")
        private String reason;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthorizationDetails {
        @JsonProperty("rrn")
        private String rrn;
        @JsonProperty("auth_code")
        private String authCode;
        @JsonProperty("three_d_secure")
        private ThreeDSecure threeDSecure;

        @Builder
        public static class ThreeDSecure {
            @JsonProperty("applied")
            private boolean applied;
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Transfers {
        @JsonProperty("account_id")
        private String accountId;
        @JsonProperty("amount")
        private Amount amount;
        @JsonProperty("status")
        private String status;
        @JsonProperty("platform_fee_amount")
        private Amount platformFeeAmount;
        @JsonProperty("description")
        private String description;
        @JsonProperty("metadata")
        private Map<String, String> metadata;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Deal {
        @JsonProperty("id")
        private String id;
        @JsonProperty("settlement")
        private Settlement settlement;

        @Getter
        @Builder
        public static class Settlement {
            @JsonProperty("type")
            private String type;
            @JsonProperty("amount")
            private List<Amount> amounts;
        }
    }

    @Override
    public void validate() {
        if (amount == null) {
            throw new NullPointerException("Payment amount not specified");
        }
        //TODO: add another checks
    }
}
