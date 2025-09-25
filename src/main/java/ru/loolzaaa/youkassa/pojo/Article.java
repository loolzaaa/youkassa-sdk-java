package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.Validated;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Article implements Validated {

    private static final int MIN_ARTICLE_NUMBER_VALUE = 1;
    private static final int MAX_ARTICLE_NUMBER_VALUE = 999;
    private static final String TRU_CODE_PATTERN = "\\d{9}\\.\\d{20}";
    private static final int MAX_ARTICLE_CODE_LENGTH = 128;
    private static final int MAX_ARTICLE_NAME_LENGTH = 128;
    private static final int MAX_METADATA_SIZE = 16;
    private static final int MAX_METADATA_KEY_LENGTH = 32;
    private static final int MAX_METADATA_VALUE_LENGTH = 512;

    @JsonProperty("article_number")
    private Integer articleNumber;
    @JsonProperty("tru_code")
    private String truCode;
    @JsonProperty("article_code")
    private String articleCode;
    @JsonProperty("article_name")
    private String articleName;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("price")
    private Amount price;
    @JsonProperty("metadata")
    private Map<String, String> metadata;

    @Override
    public void validate() {
        if (articleNumber == null) {
            throw new IllegalArgumentException("Article number must not be null");
        }
        if (articleNumber < MIN_ARTICLE_NUMBER_VALUE || articleNumber > MAX_ARTICLE_NUMBER_VALUE) {
            throw new IllegalArgumentException("Incorrect article number. Min value: %d. Max Value: %d"
                    .formatted(MIN_ARTICLE_NUMBER_VALUE, MAX_ARTICLE_NUMBER_VALUE));
        }
        if (truCode == null) {
            throw new IllegalArgumentException("Tru code number must not be null");
        }
        if (!truCode.matches(TRU_CODE_PATTERN)) {
            throw new IllegalArgumentException("Incorrect tru code. Correct pattern [NNNNNNNNN.NNNNNNNNNYYYYMMMMZZZ]: " + TRU_CODE_PATTERN);
        }
        if (articleCode != null && articleCode.length() > MAX_ARTICLE_CODE_LENGTH) {
            throw new IllegalArgumentException("Article code too long. Max length: " + MAX_ARTICLE_CODE_LENGTH);
        }
        if (articleName == null) {
            throw new IllegalArgumentException("Article name must not be null");
        }
        if (articleName.length() > MAX_ARTICLE_NAME_LENGTH) {
            throw new IllegalArgumentException("Article name too long. Max length: " + MAX_ARTICLE_CODE_LENGTH);
        }
        if (quantity == null) {
            throw new IllegalArgumentException("Quantity must not be null");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be integer positive");
        }
        if (price == null) {
            throw new IllegalArgumentException("Price must not be null");
        }
        price.validate();
        if (metadata != null) {
            if (metadata.size() > MAX_METADATA_SIZE) {
                throw new IllegalArgumentException("Incorrect metadata size. Max size: " + MAX_METADATA_SIZE);
            }
            for (Map.Entry<String, String> pair : metadata.entrySet()) {
                if (pair.getKey().length() > MAX_METADATA_KEY_LENGTH) {
                    throw new IllegalArgumentException("Too long metadata key. Max Length: " + MAX_METADATA_KEY_LENGTH);
                }
                if (pair.getValue().length() > MAX_METADATA_VALUE_LENGTH) {
                    throw new IllegalArgumentException("Too long metadata value. Max Length: " + MAX_METADATA_VALUE_LENGTH);
                }
            }
        }
    }
}
