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
public class AdditionalUserProps implements Validated {

    private static final int MAX_NAME_LENGTH = 64;
    private static final int MAX_VALUE_LENGTH = 234;

    @JsonProperty("name")
    private String name;
    @JsonProperty("value")
    private String value;

    @Override
    public void validate() {
        if (name == null || value == null) {
            throw new IllegalArgumentException("Name and value must not be null");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Too long name. Max length: " + MAX_NAME_LENGTH);
        }
        if (value.length() > MAX_VALUE_LENGTH) {
            throw new IllegalArgumentException("Too long value. Max length: " + MAX_VALUE_LENGTH);
        }
    }
}
