package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.Validated;
import ru.loolzaaa.youkassa.helper.ApiHelper;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentOrder implements Validated {

    private static final int MAX_PAYMENT_PURPOSE_LENGTH = 210;

    @JsonProperty("type")
    private String type;
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("amount")
    private Amount amount;
    @JsonProperty("kbk")
    private String kbk;
    @JsonProperty("oktmo")
    private String oktmo;
    @JsonProperty("payment_document_id")
    private String paymentDocumentId;
    @JsonProperty("payment_document_number")
    private String paymentDocumentNumber;
    @JsonProperty("payment_period")
    private PaymentPeriod paymentPeriod;
    @JsonProperty("payment_purpose")
    private String paymentPurpose;
    @JsonProperty("recipient")
    private Recipient recipient;
    @JsonProperty("service_id")
    private String serviceId;
    @JsonProperty("unified_account_number")
    private String unifiedAccountNumber;

    @Override
    public void validate() {
        if (type == null) {
            throw new IllegalArgumentException("Type must not be null");
        }
        ApiHelper.checkObjectType(this, "type", String.class, "Type");
        if (accountNumber == null && paymentDocumentId == null
                && paymentDocumentNumber == null && unifiedAccountNumber == null
                && serviceId == null) {
            throw new IllegalArgumentException("You must define some of: accountNumber, " +
                    "paymentDocumentId, paymentDocumentNumber, unifiedAccountNumber, serviceId");
        }
        if (amount == null) {
            throw new IllegalArgumentException("Amount must not be null");
        }
        amount.validate();
        if (paymentPeriod != null) {
            paymentPeriod.validate();
        }
        if (paymentPurpose == null) {
            throw new IllegalArgumentException("Payment purpose must not be null");
        }
        if (paymentPurpose.length() > MAX_PAYMENT_PURPOSE_LENGTH) {
            throw new IllegalArgumentException("Payment purpose too long. Max length: " + MAX_PAYMENT_PURPOSE_LENGTH);
        }
        if (recipient == null) {
            throw new IllegalArgumentException("Recipient must not be null");
        }
        recipient.validate();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Recipient implements Validated {

        private static final String INN_PATTERN = "\\d{9}";
        private static final String KPP_PATTERN = "\\d{9}";

        @JsonProperty("name")
        private String name;
        @JsonProperty("inn")
        private String inn;
        @JsonProperty("kpp")
        private String kpp;
        @JsonProperty("bank")
        private Bank bank;

        @Override
        public void validate() {
            if (name == null) {
                throw new IllegalArgumentException("Name must not be null");
            }
            if (inn == null) {
                throw new IllegalArgumentException("Inn must not be null");
            }
            if (!inn.matches(INN_PATTERN)) {
                throw new IllegalArgumentException("Incorrect inn. Correct pattern: " + INN_PATTERN);
            }
            if (kpp == null) {
                throw new IllegalArgumentException("Kpp must not be null");
            }
            if (!kpp.matches(KPP_PATTERN)) {
                throw new IllegalArgumentException("Incorrect kpp. Correct pattern: " + KPP_PATTERN);
            }
            if (bank == null) {
                throw new IllegalArgumentException("Bank must not be null");
            }
            bank.validate();
        }
    }

    public static class Type {
        public static final String UTILITIES = "utilities";
    }
}
