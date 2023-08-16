package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptOperationalDetails {
    @JsonProperty("operation_id")
    private Integer operationId;
    @JsonProperty("value")
    private String value;
    @JsonProperty("created_at")
    private String createdAt;
}
