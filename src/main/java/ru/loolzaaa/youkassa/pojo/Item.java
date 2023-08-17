package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item implements Validated {

    private static final int MAX_DESCRIPTION_LENGTH = 128;
    private static final String QUANTITY_PATTERN = "\\d+\\.?\\d{0,3}";
    private static final String EXCISE_PATTERN = "\\d+\\.\\d{2}";

    @JsonProperty("description")
    private String description;
    @JsonProperty("amount")
    private Amount amount;
    @JsonProperty("vat_code")
    private Integer vatCode;
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
    @JsonProperty("additional_payment_subject_props")
    private String additionalPaymentSubjectProps;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MarkQuantity implements Validated {
        @JsonProperty("numerator")
        private Integer numerator;
        @JsonProperty("denominator")
        private Integer denominator;

        @Override
        public void validate() {
            if (numerator == null || denominator == null) {
                throw new IllegalArgumentException("Numerator and denominator must no be null");
            }
            if (numerator < 1 || numerator >= denominator) {
                throw new IllegalArgumentException("Incorrect numerator. Must be greater than 1 and less than denominator");
            }
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
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

    @Override
    public void validate() {
        if (description == null) {
            throw new IllegalArgumentException("Description must not be null");
        }
        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("Too long description. Max length: " + MAX_DESCRIPTION_LENGTH);
        }
        if (amount == null) {
            throw new IllegalArgumentException("Amount must not be null");
        }
        amount.validate();
        if (vatCode == null) {
            throw new IllegalArgumentException("Vat code must not be null");
        }
        if (vatCode < 1 || vatCode > 6) {
            throw new IllegalArgumentException("Incorrect vat code. Min: 1. Max: 6");
        }
        if (quantity == null) {
            throw new IllegalArgumentException("Quantity must no be null");
        }
        if (!quantity.matches(QUANTITY_PATTERN)) {
            throw new IllegalArgumentException("Incorrect quantity. Correct pattern: " + QUANTITY_PATTERN);
        }
        if (markQuantity != null) {
            markQuantity.validate();
        }
        if (customsDeclarationNumber != null && (customsDeclarationNumber.length() < 1 || customsDeclarationNumber.length() > 32)) {
            throw new IllegalArgumentException("Incorrect customs declaration number. Min: 1. Max: 32");
        }
        if (excise != null && !excise.matches(EXCISE_PATTERN)) {
            throw new IllegalArgumentException("Incorrect excise. Correct pattern: " + EXCISE_PATTERN);
        }
    }
}
