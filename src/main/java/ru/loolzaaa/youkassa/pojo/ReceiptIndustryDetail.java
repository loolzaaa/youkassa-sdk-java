package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptIndustryDetail {
    @JsonProperty("federal_id")
    private String federalId;
    @JsonProperty("document_date")
    private String documentDate;
    @JsonProperty("document_number")
    private String documentNumber;
    @JsonProperty("value")
    private String value;
}
