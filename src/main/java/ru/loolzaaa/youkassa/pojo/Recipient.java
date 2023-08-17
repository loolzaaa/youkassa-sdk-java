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
public class Recipient implements Validated {
    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("gateway_id")
    private String gatewayId;

    @Override
    public void validate() {
        if (gatewayId == null) {
            throw new IllegalArgumentException("Gateway id must not be null");
        }
    }
}
