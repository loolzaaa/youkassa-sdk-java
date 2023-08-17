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
public class ReceiptIndustryDetail implements Validated {

    private static final String FEDERAL_ID_PATTERN = "(^00[1-9]$)|(^0[1-6][0-9]$)|(^07[0-3]$)";
    private static final String DOCUMENT_DATE_PATTERN = "^\\d{4}-(0\\d|1[0-2])-([0-2]\\d|3[01])$";
    private static final int MAX_DOCUMENT_NUMBER_LENGTH = 32;
    private static final int MAX_VALUE_LENGTH = 256;

    @JsonProperty("federal_id")
    private String federalId;
    @JsonProperty("document_date")
    private String documentDate;
    @JsonProperty("document_number")
    private String documentNumber;
    @JsonProperty("value")
    private String value;

    @Override
    public void validate() {
        if (federalId == null) {
            throw new IllegalArgumentException("Federal id must not be null");
        }
        if (!federalId.matches(FEDERAL_ID_PATTERN)) {
            throw new IllegalArgumentException("Incorrect federal id. Correct pattern: " + FEDERAL_ID_PATTERN);
        }
        if (documentDate == null) {
            throw new IllegalArgumentException("Document date must not be null");
        }
        if (!documentDate.matches(DOCUMENT_DATE_PATTERN)) {
            throw new IllegalArgumentException("Incorrect document date. Correct pattern: " + DOCUMENT_DATE_PATTERN);
        }
        if (documentNumber == null) {
            throw new IllegalArgumentException("Document number must not be null");
        }
        if (documentNumber.length() > MAX_DOCUMENT_NUMBER_LENGTH) {
            throw new IllegalArgumentException("Too long document number. Max length: " + MAX_DOCUMENT_NUMBER_LENGTH);
        }
        if (value == null) {
            throw new IllegalArgumentException("Value must not be null");
        }
        if (value.length() > MAX_VALUE_LENGTH) {
            throw new IllegalArgumentException("Too long value. Max length: " + MAX_VALUE_LENGTH);
        }
    }
}
