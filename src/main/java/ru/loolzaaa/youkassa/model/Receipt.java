package ru.loolzaaa.youkassa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.pojo.Item;
import ru.loolzaaa.youkassa.pojo.ReceiptIndustryDetail;
import ru.loolzaaa.youkassa.pojo.ReceiptOperationalDetails;
import ru.loolzaaa.youkassa.pojo.Settlement;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Receipt {
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
    @JsonProperty("items")
    private List<Item> items;
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
}
