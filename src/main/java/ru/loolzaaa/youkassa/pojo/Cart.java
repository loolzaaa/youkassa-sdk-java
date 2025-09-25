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
public class Cart implements Validated {

    private static final int MAX_DESCRIPTION_LENGTH = 128;
    private static final String QUANTITY_PATTERN = "\\d\\.\\d{0,3}";

    @JsonProperty("description")
    private String description;
    @JsonProperty("price")
    private Amount price;
    @JsonProperty("discount_price")
    private Amount discountPrice;
    @JsonProperty("quantity")
    private String quantity;

    @Override
    public void validate() {
        if (description == null) {
            throw new IllegalArgumentException("Description must not be null");
        }
        if (description.isEmpty() || description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("Incorrect description. Min length: 1. Max length: " + MAX_DESCRIPTION_LENGTH);
        }
        if (price == null) {
            throw new IllegalArgumentException("Price must not be null");
        }
        price.validate();
        if (discountPrice != null) {
            discountPrice.validate();
        }
        if (quantity == null) {
            throw new IllegalArgumentException("Quantity must not be null");
        }
        if (!quantity.matches(QUANTITY_PATTERN)) {
            throw new IllegalArgumentException("Incorrect quantity. Correct pattern: " + QUANTITY_PATTERN);
        }
    }
}
