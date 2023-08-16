package ru.loolzaaa.youkassa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.pojo.Amount;
import ru.loolzaaa.youkassa.pojo.CancellationDetails;
import ru.loolzaaa.youkassa.pojo.Settlement;
import ru.loolzaaa.youkassa.pojo.Source;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Refund {
    @JsonProperty("id")
    private String id;
    @JsonProperty("payment_id")
    private String paymentId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("cancellation_details")
    private CancellationDetails cancellationDetails;
    @JsonProperty("receipt_registration")
    private String receiptRegistration;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("amount")
    private Amount amount;
    @JsonProperty("description")
    private String description;
    @JsonProperty("sources")
    private List<Source> sources;
    @JsonProperty("deal")
    private Deal deal;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Deal {
        @JsonProperty("id")
        private String id;
        @JsonProperty("refund_settlements")
        private List<Settlement> settlements;
    }
}
