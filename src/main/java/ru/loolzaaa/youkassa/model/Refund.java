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

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Refund implements RequestBody {

    private static final int MAX_DESCRIPTION_LENGTH = 250;

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
    @JsonProperty("receipt")
    private Receipt receipt;
    @JsonProperty("sources")
    private List<Source> sources;
    @JsonProperty("deal")
    private Deal deal;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Deal implements Validated {
        @JsonProperty("id")
        private String id;
        @JsonProperty("refund_settlements")
        private List<Settlement> settlements;

        @Override
        public void validate() {
            if (settlements == null) {
                throw new IllegalArgumentException("Settlements must not be null");
            }
            for (Settlement settlement : settlements) {
                settlement.validate();
            }
        }
    }

    public static void createValidation(Refund refund) {
        if (refund.getPaymentId() == null) {
            throw new IllegalArgumentException("Payment id must not be null");
        }
        if (refund.getAmount() == null) {
            throw new IllegalArgumentException("Amount must not be null");
        }
        refund.getAmount().validate();
        if (refund.getDescription() != null && refund.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("Too long description. Max length: " + MAX_DESCRIPTION_LENGTH);
        }
        if (refund.getReceipt() != null) {
            refund.getReceipt().validate();
        }
        if (refund.getSources() != null) {
            for (Source source : refund.getSources()) {
                source.validate();
            }
        }
        if (refund.getDeal() != null) {
            refund.getDeal().validate();
        }
    }
}
