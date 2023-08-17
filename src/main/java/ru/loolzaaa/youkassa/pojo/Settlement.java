package ru.loolzaaa.youkassa.pojo;

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
public class Settlement implements Validated {
    @JsonProperty("type")
    private String type;
    @JsonProperty("amount")
    private Amount amount;

    @Override
    public void validate() {
        if (type == null || amount == null) {
            throw new IllegalArgumentException("Type and amount must not be null");
        }
        amount.validate();
    }
}
