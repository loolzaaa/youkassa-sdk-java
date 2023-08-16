package ru.loolzaaa.youkassa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.pojo.Amount;
import ru.loolzaaa.youkassa.pojo.CancellationDetails;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payout {
    @JsonProperty("id")
    private String id;
    @JsonProperty("amount")
    private Amount amount;
    @JsonProperty("status")
    private String status;
    @JsonProperty("payout_destination")
    private JsonNode payoutDestination;
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
}
