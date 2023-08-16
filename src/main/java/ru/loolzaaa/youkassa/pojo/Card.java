package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Card {
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
}
