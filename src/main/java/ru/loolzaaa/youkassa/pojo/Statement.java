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
public class Statement implements Validated {

    @JsonProperty("type")
    private String type;
    @JsonProperty("delivery_method")
    private DeliveryMethod deliveryMethod;

    @Override
    public void validate() {
        if (type == null) {
            throw new IllegalArgumentException("Type must not be null");
        }
        if (deliveryMethod == null) {
            throw new IllegalArgumentException("Type must not be null");
        }
        deliveryMethod.validate();
    }

    public static class Type {
        public static final String PAYMENT_OVERVIEW = "payment_overview";
    }
}
