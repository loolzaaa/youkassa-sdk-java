package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Receipt {
    @JsonProperty("customer")
    private Customer customer;
    @JsonProperty("items")
    private List<Item> items;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("email")
    private String email;
    @JsonProperty("tax_system_code")
    private Integer taxSystemCode;
    @JsonProperty("receipt_industry_details")
    private List<ReceiptIndustryDetail> receiptIndustryDetails;
    @JsonProperty("receipt_operational_details")
    private ReceiptOperationalDetails receiptOperationalDetails;
}
