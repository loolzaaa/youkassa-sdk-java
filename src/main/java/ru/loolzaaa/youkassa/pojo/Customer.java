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
public class Customer implements Validated {

    private static final int MAX_FULL_NAME_LENGTH = 256;
    private static final String INN_PATTERN = "\\d{10}|\\d{12}";
    private static final int MAX_PHONE_LENGTH = 15;

    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("inn")
    private String inn;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;

    @Override
    public void validate() {
        if (fullName != null && fullName.length() > MAX_FULL_NAME_LENGTH) {
            throw new IllegalArgumentException("Too long fullName. Max length: " + MAX_FULL_NAME_LENGTH);
        }
        if (inn != null && !inn.matches(INN_PATTERN)) {
            throw new IllegalArgumentException("Incorrect inn. Correct pattern: " + INN_PATTERN);
        }
        if (phone != null && phone.length() > MAX_PHONE_LENGTH) {
            throw new IllegalArgumentException("Too long phone. Max length: " + MAX_PHONE_LENGTH);
        }
    }
}
