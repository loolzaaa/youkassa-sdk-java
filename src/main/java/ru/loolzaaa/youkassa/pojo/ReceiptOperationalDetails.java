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
public class ReceiptOperationalDetails implements Validated {

    private static final int MAX_VALUE_LENGTH = 64;
    private static final String CREATED_AT_PATTERN = "\\d{4}-(0\\d|1[0-2])-([0-2]\\d|3[01])T([0-1]\\d|2[0-3]):[0-5]\\d:[0-5]\\d\\.\\d+Z";

    @JsonProperty("operation_id")
    private Integer operationId;
    @JsonProperty("value")
    private String value;
    @JsonProperty("created_at")
    private String createdAt;

    @Override
    public void validate() {
        if (operationId == null) {
            throw new IllegalArgumentException("Operation id must not be null");
        }
        if (operationId < 0 || operationId > 255) {
            throw new IllegalArgumentException("Incorrect operation id. Must be in range [0,255]");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value must not be null");
        }
        if (value.length() > MAX_VALUE_LENGTH) {
            throw new IllegalArgumentException("Too long value. Max length: " + MAX_VALUE_LENGTH);
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("Created at must not be null");
        }
        if (createdAt.matches(CREATED_AT_PATTERN)) {
            throw new IllegalArgumentException("Incorrect created at. Example: 2017-11-03T11:52:31.827Z");
        }
    }
}
