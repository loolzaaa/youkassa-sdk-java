package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.Validated;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Receipt implements Validated {
    @JsonProperty("customer")
    private Customer customer;
    @JsonProperty("items")
    private List<Item> items;
    @Deprecated
    @JsonProperty("phone")
    private String phone;
    @Deprecated
    @JsonProperty("email")
    private String email;
    @JsonProperty("tax_system_code")
    private Integer taxSystemCode;
    @JsonProperty("receipt_industry_details")
    private List<ReceiptIndustryDetail> receiptIndustryDetails;
    @JsonProperty("receipt_operational_details")
    private ReceiptOperationalDetails receiptOperationalDetails;

    @Override
    public void validate() {
        if (customer != null) {
            customer.validate();
        }
        if (items != null && items.size() > 0) {
            for (Item item : items) {
                item.validate();
            }
        } else {
            throw new IllegalArgumentException("Receipt must contains at least one item");
        }
        if (taxSystemCode != null && (taxSystemCode < 1 || taxSystemCode > 6)) {
            throw new IllegalArgumentException("Incorrect tax system code. Min: 1. Max: 6");
        }
        if (receiptIndustryDetails != null) {
            for (ReceiptIndustryDetail receiptIndustryDetail : receiptIndustryDetails) {
                receiptIndustryDetail.validate();
            }
        }
        if (receiptOperationalDetails != null) {
            receiptOperationalDetails.validate();
        }
    }
}
