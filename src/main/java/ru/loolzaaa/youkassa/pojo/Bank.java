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
public class Bank implements Validated {

    private static final String BIC_PATTERN = "\\d{9}";

    @JsonProperty("name")
    private String name;
    @JsonProperty("bic")
    private String bic;
    @JsonProperty("account")
    private String account;
    @JsonProperty("correspondent_account")
    private String correspondentAccount;

    @Override
    public void validate() {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        if (bic == null) {
            throw new IllegalArgumentException("Bic must not be null");
        }
        if (!bic.matches(BIC_PATTERN)) {
            throw new IllegalArgumentException("Incorrect bic. Correct pattern: " + BIC_PATTERN);
        }
        if (account == null) {
            throw new IllegalArgumentException("Account must not be null");
        }
        if (correspondentAccount == null) {
            throw new IllegalArgumentException("Correspondent account must not be null");
        }
    }
}
