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
public class ElectronicCertificate implements Validated {
    @JsonProperty("amount")
    private Amount amount;
    @JsonProperty("basket_id")
    private String basketId;

    @Override
    public void validate() {
        if (amount == null) {
            throw new IllegalArgumentException("Amount must not be null");
        }
        amount.validate();
        if (basketId == null) {
            throw new IllegalArgumentException("Basket id must not be null");
        }
    }
}
