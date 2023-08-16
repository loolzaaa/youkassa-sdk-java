package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Item {
    @JsonProperty("description")
    private String description;
    @JsonProperty("amount")
    private Amount amount;
    @JsonProperty("vat_code")
    private int vatCode;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("measure")
    private String measure;
    @JsonProperty("mark_quantity")
    private MarkQuantity markQuantity;
    @JsonProperty("payment_subject")
    private String paymentSubject;
    @JsonProperty("payment_mode")
    private String paymentMode;
    @JsonProperty("country_of_origin_code")
    private String countryOfOriginCode;
    @JsonProperty("custom_declaration_number")
    private String customsDeclarationNumber;
    @JsonProperty("excise")
    private String excise;
    @JsonProperty("supplier")
    private Supplier supplier;
    @JsonProperty("agent_type")
    private String agentType;
    @JsonProperty("product_code")
    private String productCode;
    @JsonProperty("mark_code_info")
    private MarkCodeInfo markCodeInfo;
    @JsonProperty("mark_mode")
    private String markMode;
    @JsonProperty("payment_subject_industry_details")
    private List<PaymentSubjectIndustryDetail> paymentSubjectIndustryDetails;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MarkQuantity {
        @JsonProperty("numerator")
        private int numerator;
        @JsonProperty("denominator")
        private int denominator;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Supplier {
        @JsonProperty("name")
        private String name;
        @JsonProperty("phone")
        private String phone;
        @JsonProperty("inn")
        private String inn;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MarkCodeInfo {
        @JsonProperty("mark_code_raw")
        private String markCodeRaw;
        @JsonProperty("unknown")
        private String unknown;
        @JsonProperty("ean_8")
        private String ean8;
        @JsonProperty("ean_13")
        private String ean13;
        @JsonProperty("itf_14")
        private String itf14;
        @JsonProperty("gs_10")
        private String gs10;
        @JsonProperty("gs_1m")
        private String gs1m;
        @JsonProperty("short")
        private String _short;
        @JsonProperty("fur")
        private String fur;
        @JsonProperty("egais_20")
        private String egais20;
        @JsonProperty("egais_30")
        private String egais30;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PaymentSubjectIndustryDetail {
        @JsonProperty("federal_id")
        private String federalId;
        @JsonProperty("document_date")
        private String documentDate;
        @JsonProperty("document_number")
        private String documentNumber;
        @JsonProperty("value")
        private String value;
    }
}
