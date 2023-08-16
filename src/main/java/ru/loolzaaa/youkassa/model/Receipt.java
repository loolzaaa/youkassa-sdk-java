package ru.loolzaaa.youkassa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.RequestValidated;
import ru.loolzaaa.youkassa.pojo.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Receipt implements RequestValidated {
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
    private boolean send;
    @JsonProperty("additional_user_props")
    private AdditionalUserProps additionalUserProps;
    @JsonProperty("settlements")
    private List<Settlement> settlements;
    @JsonProperty("on_behalf_of")
    private String onBehalfOf;
    @JsonProperty("tax_system_code")
    private int taxSystemCode;
    @JsonProperty("receipt_industry_details")
    private List<ReceiptIndustryDetail> receiptIndustryDetails;
    @JsonProperty("receipt_operational_details")
    private ReceiptOperationalDetails receiptOperationalDetails;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AdditionalUserProps {
        @JsonProperty("name")
        private String name;
        @JsonProperty("value")
        private String value;
    }

    @Override
    public void validate() {
        //
    }
}
