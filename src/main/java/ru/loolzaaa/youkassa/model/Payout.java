package ru.loolzaaa.youkassa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.RequestBody;
import ru.loolzaaa.youkassa.pojo.Amount;
import ru.loolzaaa.youkassa.pojo.CancellationDetails;
import ru.loolzaaa.youkassa.pojo.Card;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payout implements RequestBody {
    @JsonProperty("id")
    private String id;
    @JsonProperty("amount")
    private Amount amount;
    @JsonProperty("status")
    private String status;
    @JsonProperty("payout_destination")
    private PayoutDestination payoutDestination;
    @JsonProperty("payout_destination_data")
    private PayoutDestination payoutDestinationData;
    @JsonProperty("payout_token")
    private String payoutToken;
    @JsonProperty("payment_method_id")
    private String paymentMethodId;
    @JsonProperty("description")
    private String description;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("deal")
    private Deal deal;
    @JsonProperty("self_employed")
    private SelfEmployed selfEmployed;
    @JsonProperty("receipt")
    private Receipt receipt;
    @JsonProperty("receipt_data")
    private Receipt receiptData;
    @JsonProperty("personal_data")
    private PersonalData personalData;
    @JsonProperty("cancellation_details")
    private CancellationDetails cancellationDetails;
    @JsonProperty("metadata")
    private Map<String, String> metadata;
    @JsonProperty("test")
    private boolean test;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Deal {
        @JsonProperty("id")
        private String id;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SelfEmployed {
        @JsonProperty("id")
        private String id;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PersonalData {
        @JsonProperty("id")
        private String id;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Receipt {
        @JsonProperty("service_name")
        private String serviceName;
        @JsonProperty("npd_receipt_id")
        private String npdReceiptId;
        @JsonProperty("url")
        private String url;
        @JsonProperty("amount")
        private Amount amount;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PayoutDestination {
        @JsonProperty("type")
        private String type;
        @JsonProperty("bank_id")
        private String bankId;
        @JsonProperty("phone")
        private String phone;
        @JsonProperty("recipient_checked")
        private boolean recipientChecked;
        @JsonProperty("account_number")
        private String accountNumber;
        @JsonProperty("card")
        private Card card;

        public static class Type {
            public static final String BANK_CARD = "bank_card";
            public static final String SBP = "spb";
            public static final String YOO_MONEY = "yoo_money";
        }
    }
}
