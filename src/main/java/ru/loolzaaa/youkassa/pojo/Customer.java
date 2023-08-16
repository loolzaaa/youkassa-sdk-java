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
public class Customer {
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("inn")
    private String inn;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;
}
