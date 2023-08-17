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
public class Passenger implements Validated {

    private static final String NAME_PATTERN = "[A-Z]{1,64}";

    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;

    @Override
    public void validate() {
        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException("First name and last name must not be null");
        }
        if (!firstName.matches(NAME_PATTERN)) {
            throw new IllegalArgumentException("Incorrect first name. Correct pattern: " + NAME_PATTERN);
        }
        if (!lastName.matches(NAME_PATTERN)) {
            throw new IllegalArgumentException("Incorrect last name. Correct pattern: " + NAME_PATTERN);
        }
    }
}
