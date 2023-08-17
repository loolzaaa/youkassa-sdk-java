package ru.loolzaaa.youkassa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.RequestBody;
import ru.loolzaaa.youkassa.client.Validated;
import ru.loolzaaa.youkassa.pojo.Receipt;
import ru.loolzaaa.youkassa.pojo.*;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payment implements RequestBody {

    private static final int MAX_DESCRIPTION_LENGTH = 128;
    private static final int MAX_METADATA_SIZE = 16;
    private static final int MAX_METADATA_KEY_LENGTH = 32;
    private static final int MAX_METADATA_VALUE_LENGTH = 512;
    private static final int MAX_MERCHANT_CUSTOMER_ID_LENGTH = 200;

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
    private PaymentMethod paymentMethod;
    @JsonProperty("payment_method_data")
    private PaymentMethod paymentMethodData;
    @JsonProperty("payment_method_id")
    private String paymentMethodId;
    @JsonProperty("payment_token")
    private String paymentToken;
    @JsonProperty("save_payment_method")
    private Boolean savePaymentMethod;
    @JsonProperty("capture")
    private Boolean capture;
    @JsonProperty("captured_at")
    private String capturedAt;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("expires_at")
    private String expiresAt;
    @JsonProperty("confirmation")
    private Confirmation confirmation;
    @JsonProperty("test")
    private Boolean test;
    @JsonProperty("refunded_amount")
    private Amount refundedAmount;
    @JsonProperty("paid")
    private Boolean paid;
    @JsonProperty("refundable")
    private Boolean refundable;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Deal implements Validated {

        @JsonProperty("id")
        private String id;
        @JsonProperty("settlements")
        private List<Settlement> settlements;

        @Override
        public void validate() {
            if (id == null) {
                throw new IllegalArgumentException("Id must not be null");
            }
            if (id.length() < 36 || id.length() > 50) {
                throw new IllegalArgumentException("Incorrect id length. Must be from 36 to 50 inclusively");
            }
            if (settlements == null) {
                throw new IllegalArgumentException("Settlements must not be null");
            }
            for (Settlement settlement : settlements) {
                settlement.validate();
            }
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FraudData implements Validated {

        private static final String PHONE_PATTERN = "\\d{4,15}";

        @JsonProperty("topped_up_phone")
        private String toppedUpPhone;

        @Override
        public void validate() {
            if (toppedUpPhone != null && !toppedUpPhone.matches(PHONE_PATTERN)) {
                throw new IllegalArgumentException("Incorrect topped up phone. Correct pattern: " + PHONE_PATTERN);
            }
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PaymentMethod implements Validated {
        @JsonProperty("type")
        private String type;
        @JsonProperty("id")
        private String id;
        @JsonProperty("saved")
        private Boolean saved;
        @JsonProperty("title")
        private String title;
        @JsonProperty("discount_amount")
        private Amount discountAmount;
        @JsonProperty("loan_option")
        private String loanOption;
        @JsonProperty("logine")
        private String login;
        @JsonProperty("account_number")
        private String accountNumber;
        @JsonProperty("phone")
        private String phone;
        @JsonProperty("card")
        private Card card;

        @Override
        public void validate() {
            if (type == null) {
                throw new IllegalArgumentException("Type must not be null");
            }
            if (type.equals(Type.MOBILE_BALANCE) && phone == null) {
                throw new IllegalArgumentException("Phone must not be null if type " + Type.MOBILE_BALANCE);
            }
            if (card != null) {
                card.validate();
            }
        }

        public static class Type {
            public static final String SBER_LOAN = "sber_loan";
            public static final String ALPHA_CLICK = "alfabank";
            public static final String MOBILE_BALANCE = "mobile_balance";
            public static final String BANK_CARD = "bank_card";
            public static final String INSTALLMENTS = "installments";
            public static final String CASH = "cash";
            public static final String SBP = "sbp";
            public static final String TINKOFF_BANK = "tinkoff_bank";
            public static final String YOO_MONEY = "yoo_money";
            public static final String APPLE_PAY = "apple_pay";
            public static final String GOOGLE_PAY = "google_pay";
            public static final String QIWI = "qiwi";
            public static final String SBER_PAY = "sberbank";
            public static final String WECHAT = "wechat";
            public static final String WEBMONEY = "webmoney";
        }
    }

    public static class Status {
        public static final String PENDING = "pending";
        public static final String WAITING_FOR_CAPTURE = "waiting_for_capture";
        public static final String SUCCEEDED = "succeeded";
        public static final String CANCELED = "canceled";
    }

    public static void createValidation(Payment payment) {
        if (payment.getAmount() != null) {
            payment.getAmount().validate();
        } else {
            throw new IllegalArgumentException("Amount must not be null");
        }
        if (payment.getDescription() != null && payment.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("Too long description. Max length: " + MAX_DESCRIPTION_LENGTH);
        }
        if (payment.getReceipt() != null) {
            payment.getReceipt().validate();
        }
        if (payment.getRecipient() != null) {
            payment.getRecipient().validate();
        }
        if (payment.getPaymentToken() != null && (payment.getPaymentMethodId() != null || payment.getPaymentMethodData() != null)) {
            throw new IllegalArgumentException("Both payment token and payment method id/payment data values are specified");
        }
        if (payment.getPaymentMethodId() != null && payment.getPaymentMethodData() != null) {
            throw new IllegalArgumentException("Both payment method id and payment data values are specified");
        }
        if (payment.getPaymentMethodData() != null) {
            payment.getPaymentMethodData().validate();
        }
        if (payment.getConfirmation() != null) {
            payment.getConfirmation().validate();
        }
        if (payment.getMetadata() != null) {
            if (payment.getMetadata().size() > MAX_METADATA_SIZE) {
                throw new IllegalArgumentException("Incorrect metadata size. Max size: " + MAX_METADATA_SIZE);
            }
            for (Map.Entry<String, String> pair : payment.getMetadata().entrySet()) {
                if (pair.getKey().length() > MAX_METADATA_KEY_LENGTH) {
                    throw new IllegalArgumentException("Too long metadata key. Max Length: " + MAX_METADATA_KEY_LENGTH);
                }
                if (pair.getValue().length() > MAX_METADATA_VALUE_LENGTH) {
                    throw new IllegalArgumentException("Too long metadata value. Max Length: " + MAX_METADATA_VALUE_LENGTH);
                }
            }
        }
        if (payment.getAirline() != null) {
            payment.getAirline().validate();
        }
        if (payment.getTransfers() != null) {
            for (Transfer transfer : payment.getTransfers()) {
                transfer.validate();
            }
        }
        if (payment.getDeal() != null) {
            payment.getDeal().validate();
        }
        if (payment.getFraudData() != null) {
            payment.getFraudData().validate();
        }
        if (payment.getMerchantCustomerId() != null && payment.getMerchantCustomerId().length() > MAX_MERCHANT_CUSTOMER_ID_LENGTH) {
            throw new IllegalArgumentException("Too long merchantCustomerId. Max length: " + MAX_MERCHANT_CUSTOMER_ID_LENGTH);
        }
    }

    public static void captureValidation(Payment payment) {
        if (payment.getAmount() != null) {
            payment.getAmount().validate();
        }
        if (payment.getReceipt() != null) {
            payment.getReceipt().validate();
        }
        if (payment.getAirline() != null) {
            payment.getAirline().validate();
        }
        if (payment.getTransfers() != null) {
            for (Transfer transfer : payment.getTransfers()) {
                transfer.validate();
            }
        }
        if (payment.getDeal() != null) {
            // This validation also checks deal id. Is this problem?
            payment.getDeal().validate();
        }
    }
}
