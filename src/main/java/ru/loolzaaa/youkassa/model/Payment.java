package ru.loolzaaa.youkassa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.RequestValidated;
import ru.loolzaaa.youkassa.pojo.Receipt;
import ru.loolzaaa.youkassa.pojo.*;

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
    @JsonProperty("receipt")
    private Receipt receipt;
    @JsonProperty("recipient")
    private Recipient recipient;
    @JsonProperty("payment_method")
    private ObjectNode paymentMethod;
    @JsonProperty("payment_method_data")
    private ObjectNode paymentMethodData;
    @JsonProperty("payment_method_id")
    private String paymentMethodId;
    @JsonProperty("payment_token")
    private String paymentToken;
    @JsonProperty("save_payment_method")
    private boolean savePaymentMethod;
    @JsonProperty("capture")
    private boolean capture;
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
    @JsonProperty("client_ip")
    private String clientIp;
    @JsonProperty("metadata")
    private Map<String, String> metadata;
    @JsonProperty("airline")
    private Airline airline;
    @JsonProperty("cancellation_details")
    private CancellationDetails cancellationDetails;
    @JsonProperty("authorization_details")
    private AuthorizationDetails authorizationDetails;
    @JsonProperty("transfers")
    private List<Transfer> transfers;
    @JsonProperty("deal")
    private Deal deal;
    @JsonProperty("fraud_data")
    private FraudData fraudData;
    @JsonProperty("merchant_customer_id")
    private String merchantCustomerId;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Deal {
        @JsonProperty("id")
        private String id;
        @JsonProperty("settlements")
        private List<Settlement> settlements;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FraudData {
        @JsonProperty("topped_up_phone")
        private String toppedUpPhone;
    }

    @Override
    public void validate() {
        //
    }
}
