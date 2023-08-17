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

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Receipt implements RequestBody {
    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("payment_id")
    private String paymentId;
    @JsonProperty("refund_id")
    private String refundId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("fiscal_document_number")
    private String fiscalDocumentNumber;
    @JsonProperty("fiscal_storage_number")
    private String fiscalStorageNumber;
    @JsonProperty("fiscal_attribute")
    private String fiscalAttribute;
    @JsonProperty("registered_at")
    private String registeredAt;
    @JsonProperty("fiscal_provider_id")
    private String fiscalProviderId;
    @JsonProperty("customer")
    private Customer customer;
    @JsonProperty("items")
    private List<Item> items;
    @JsonProperty("send")
    private Boolean send;
    @JsonProperty("additional_user_props")
    private AdditionalUserProps additionalUserProps;
    @JsonProperty("settlements")
    private List<Settlement> settlements;
    @JsonProperty("on_behalf_of")
    private String onBehalfOf;
    @JsonProperty("tax_system_code")
    private Integer taxSystemCode;
    @JsonProperty("receipt_industry_details")
    private List<ReceiptIndustryDetail> receiptIndustryDetails;
    @JsonProperty("receipt_operational_details")
    private ReceiptOperationalDetails receiptOperationalDetails;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AdditionalUserProps implements Validated {

        private static final int MAX_NAME_LENGTH = 64;
        private static final int MAX_VALUE_LENGTH = 234;

        @JsonProperty("name")
        private String name;
        @JsonProperty("value")
        private String value;

        @Override
        public void validate() {
            if (name == null || value == null) {
                throw new IllegalArgumentException("Name and value must not be null");
            }
            if (name.length() > MAX_NAME_LENGTH) {
                throw new IllegalArgumentException("Too long name. Max length: " + MAX_NAME_LENGTH);
            }
            if (value.length() > MAX_VALUE_LENGTH) {
                throw new IllegalArgumentException("Too long value. Max length: " + MAX_VALUE_LENGTH);
            }
        }
    }

    public static class Type {
        public static final String PAYMENT = "payment";
        public static final String REFUND = "refund";
    }

    public static void createValidation(Receipt receipt) {
        if (receipt.getType() == null) {
            throw new IllegalArgumentException("Type must not be null");
        }
        if (receipt.getCustomer() == null) {
            throw new IllegalArgumentException("Customer must not be null");
        }
        receipt.getCustomer().validate();
        if (receipt.getItems() != null && receipt.getItems().size() > 0) {
            for (Item item : receipt.getItems()) {
                item.validate();
            }
        } else {
            throw new IllegalArgumentException("Receipt must contains at least one item");
        }
        if (receipt.getSend() == null || !receipt.getSend()) {
            throw new IllegalArgumentException("Send must not be null and always true");
        }
        if (receipt.getType().equals(Type.PAYMENT) && receipt.getPaymentId() == null) {
            throw new IllegalArgumentException("Receipt payment id not specified");
        }
        if (receipt.getType().equals(Type.REFUND) && receipt.getPaymentId() == null && receipt.getRefundId() == null) {
            throw new IllegalArgumentException("Receipt refund id or payment id not specified");
        }
        if (receipt.getTaxSystemCode() != null && (receipt.getTaxSystemCode() < 1 || receipt.getTaxSystemCode() > 6)) {
            throw new IllegalArgumentException("Incorrect tax system code. Min: 1. Max: 6");
        }
        if (receipt.getAdditionalUserProps() != null) {
            receipt.getAdditionalUserProps().validate();
        }
        if (receipt.getReceiptIndustryDetails() != null) {
            for (ReceiptIndustryDetail receiptIndustryDetail : receipt.getReceiptIndustryDetails()) {
                receiptIndustryDetail.validate();
            }
        }
        if (receipt.getReceiptOperationalDetails() != null) {
            receipt.getReceiptOperationalDetails().validate();
        }
        if (receipt.getSettlements() == null) {
            throw new IllegalArgumentException("Settlements must not be null");
        }
        for (Settlement settlement : receipt.getSettlements()) {
            settlement.validate();
        }
    }
}
