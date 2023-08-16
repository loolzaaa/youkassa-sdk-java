package ru.loolzaaa.youkassa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private PaymentMethod paymentMethod;
    @JsonProperty("payment_method_data")
    private PaymentMethod paymentMethodData;
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

    public static class PaymentMethod {
        @JsonProperty("type")
        private String type;
        @JsonProperty("id")
        private String id;
        @JsonProperty("saved")
        private boolean saved;
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

    @Override
    public void validate() {
        //
    }
}
