package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.Validated;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Card implements Validated {

    private static final String NUMBER_PATTERN = "\\d{16,19}";
    private static final String EXPIRY_YEAR_PATTERN = "\\d{4}";
    private static final String EXPIRY_MONTH_PATTERN = "\\d{2}";
    private static final String CSC_PATTERN = "\\d{3,4}";
    private static final String CARDHOLDER_PATTERN = "[a-zA-Z]{0,26}";

    @JsonProperty("first6")
    private String first6;
    @JsonProperty("last4")
    private String last4;
    @JsonProperty("expiry_year")
    private String expiryYear;
    @JsonProperty("expiry_month")
    private String expiryMonth;
    @JsonProperty("card_type")
    private String cardType;
    @JsonProperty("issuer_country")
    private String issuerCountry;
    @JsonProperty("issuer_name")
    private String issuerName;
    @JsonProperty("cardholder")
    private String cardHolder;
    @JsonProperty("number")
    private String number;
    @JsonProperty("csc")
    private String csc;
    @JsonProperty("source")
    private String source;

    @Override
    public void validate() {
        if (number == null) {
            throw new IllegalArgumentException("Number must not be null");
        }
        if (!number.matches(NUMBER_PATTERN)) {
            throw new IllegalArgumentException("Incorrect number. Correct pattern: " + NUMBER_PATTERN);
        }
        if (expiryYear == null || expiryMonth == null) {
            throw new IllegalArgumentException("Expiry date must not be null");
        }
        if (!expiryYear.matches(EXPIRY_YEAR_PATTERN)) {
            throw new IllegalArgumentException("Incorrect expiry year. Correct pattern: " + EXPIRY_YEAR_PATTERN);
        }
        if (!expiryMonth.matches(EXPIRY_MONTH_PATTERN)) {
            throw new IllegalArgumentException("Incorrect expiry month. Correct pattern: " + EXPIRY_MONTH_PATTERN);
        }
        if (csc != null && !csc.matches(CSC_PATTERN)) {
            throw new IllegalArgumentException("Incorrect csc. Correct pattern: " + CSC_PATTERN);
        }
        if (cardHolder != null && !cardHolder.matches(CARDHOLDER_PATTERN)) {
            throw new IllegalArgumentException("Incorrect cardholder. Correct pattern: " + CARDHOLDER_PATTERN);
        }
    }
}
