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
import ru.loolzaaa.youkassa.pojo.*;
import ru.loolzaaa.youkassa.pojo.Receipt;

import java.util.List;
import java.util.Map;

/**
 * This class represents Refund object.
 * <p>
 * Contains up-to-date information about
 * the return of a successful payment.
 * <p>
 * It comes in response to any request r
 * elated to returns.
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Refund implements RequestBody {

    private static final int MAX_DESCRIPTION_LENGTH = 250;
    private static final int MAX_METADATA_SIZE = 16;
    private static final int MAX_METADATA_KEY_LENGTH = 32;
    private static final int MAX_METADATA_VALUE_LENGTH = 512;

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
    @JsonProperty("refund_method")
    private RefundMethod refundMethod;
    @JsonProperty("refund_method_data")
    private RefundMethod refundMethodData;
    @JsonProperty("refund_authorization_details")
    private RefundAuthorizationDetails refundAuthorizationDetails;
    @JsonProperty("metadata")
    private Map<String, String> metadata;

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
        if (refund.getRefundMethodData() != null) {
            refund.getRefundMethodData().validate();
        }
        if (refund.getMetadata() != null) {
            if (refund.getMetadata().size() > MAX_METADATA_SIZE) {
                throw new IllegalArgumentException("Incorrect metadata size. Max size: " + MAX_METADATA_SIZE);
            }
            for (Map.Entry<String, String> pair : refund.getMetadata().entrySet()) {
                if (pair.getKey().length() > MAX_METADATA_KEY_LENGTH) {
                    throw new IllegalArgumentException("Too long metadata key. Max Length: " + MAX_METADATA_KEY_LENGTH);
                }
                if (pair.getValue().length() > MAX_METADATA_VALUE_LENGTH) {
                    throw new IllegalArgumentException("Too long metadata value. Max Length: " + MAX_METADATA_VALUE_LENGTH);
                }
            }
        }
    }
}
