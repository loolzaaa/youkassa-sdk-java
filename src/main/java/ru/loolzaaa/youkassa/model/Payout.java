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
import ru.loolzaaa.youkassa.pojo.PersonalData;
import ru.loolzaaa.youkassa.pojo.SelfEmployed;

import java.util.Map;

/**
 * This class represents Payout object.
 * <p>
 * Contains all information about the payout
 * that is relevant at the current time.
 * <p>
 * It is generated when creating a payout
 * and comes in response to any request
 * related to payouts.
 * <p>
 * The set of returned parameters depends on
 * the status of the object (the value of
 * the status parameter) and what parameters
 * you passed in the request to create a payout.
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payout implements RequestBody {

    private static final int MAX_DESCRIPTION_LENGTH = 128;
    private static final int MAX_METADATA_SIZE = 16;
    private static final int MAX_METADATA_KEY_LENGTH = 32;
    private static final int MAX_METADATA_VALUE_LENGTH = 512;

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
    @JsonProperty("succeeded_at")
    private String succeededAt;
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
    private Boolean test;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Deal implements Validated {
        @JsonProperty("id")
        private String id;

        @Override
        public void validate() {
            if (id == null) {
                throw new IllegalArgumentException("Id must not be null");
            }
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Receipt implements Validated {

        private static final int MAX_SERVICE_NAME_LENGTH = 50;

        @JsonProperty("service_name")
        private String serviceName;
        @JsonProperty("npd_receipt_id")
        private String npdReceiptId;
        @JsonProperty("url")
        private String url;
        @JsonProperty("amount")
        private Amount amount;

        @Override
        public void validate() {
            if (serviceName == null) {
                throw new IllegalArgumentException("Service name must not be null");
            }
            if (serviceName.length() > MAX_SERVICE_NAME_LENGTH) {
                throw new IllegalArgumentException("Too long service name. Max length: " + MAX_SERVICE_NAME_LENGTH);
            }
            if (amount != null) {
                amount.validate();
            }
        }
    }

    public static void createValidation(Payout payout) {
        if (payout.getAmount() == null) {
            throw new IllegalArgumentException("Amount must not be null");
        }
        payout.getAmount().validate();
        if (payout.getPayoutDestinationData() == null && payout.getPayoutToken() == null && payout.getPaymentMethodId() == null) {
            throw new IllegalArgumentException("One of payout destination data, payout token and payment method id must not be null");
        }
        if (payout.getPayoutDestinationData() != null && (payout.getPayoutToken() != null || payout.getPaymentMethodId() != null)) {
            throw new IllegalArgumentException("Only one of payout destination data, payout token and payment method id must be specified");
        }
        if (payout.getPayoutToken() != null && (payout.getPayoutDestinationData() != null || payout.getPaymentMethodId() != null)) {
            throw new IllegalArgumentException("Only one of payout destination data, payout token and payment method id must be specified");
        }
        if (payout.getPaymentMethodId() != null && (payout.getPayoutDestinationData() != null || payout.getPayoutToken() != null)) {
            throw new IllegalArgumentException("Only one of payout destination data, payout token and payment method id must be specified");
        }
        if (payout.getPayoutDestinationData() != null) {
            payout.getPayoutDestinationData().validate();
        }
        if (payout.getDescription() != null && payout.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("Too long description. Max length: " + MAX_DESCRIPTION_LENGTH);
        }
        if (payout.getDeal() != null) {
            payout.getDeal().validate();
        }
        if (payout.getSelfEmployed() != null) {
            payout.getSelfEmployed().validate();
        }
        if (payout.getReceiptData() != null) {
            payout.getReceiptData().validate();
        }
        if (payout.getPersonalData() != null) {
            payout.getPersonalData().validate();
        }
        if (payout.getMetadata() != null) {
            if (payout.getMetadata().size() > MAX_METADATA_SIZE) {
                throw new IllegalArgumentException("Incorrect metadata size. Max size: " + MAX_METADATA_SIZE);
            }
            for (Map.Entry<String, String> pair : payout.getMetadata().entrySet()) {
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
