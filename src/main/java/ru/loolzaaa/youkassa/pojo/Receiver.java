package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.Validated;
import ru.loolzaaa.youkassa.helper.ApiHelper;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Receiver implements Validated {

    private static final int MAX_ACCOUNT_NUMBER_LENGTH = 20;
    private static final int BIC_LENGTH = 9;
    private static final int MAX_PHONE_LENGTH = 15;

    @JsonProperty("type")
    private String type;
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("bic")
    private String bic;
    @JsonProperty("phone")
    private String phone;

    @Override
    public void validate() {
        if (type == null) {
            throw new IllegalArgumentException("Type must not be null");
        }
        ApiHelper.checkObjectType(this, "type", String.class, "Type");
        if (type.equals(Type.BANK_ACCOUNT)) {
            if (accountNumber == null) {
                throw new IllegalArgumentException("Account number must not be null");
            }
            if (accountNumber.length() > MAX_ACCOUNT_NUMBER_LENGTH) {
                throw new IllegalArgumentException("Incorrect account number. Max length: " + MAX_ACCOUNT_NUMBER_LENGTH);
            }
            if (bic == null) {
                throw new IllegalArgumentException("Bic must not be null");
            }
            if (bic.length() > BIC_LENGTH) {
                throw new IllegalArgumentException("Incorrect bic. Length must be: " + BIC_LENGTH);
            }
        }
        if (type.equals(Type.MOBILE_BALANCE)) {
            if (phone == null) {
                throw new IllegalArgumentException("Phone must not be null");
            }
            if (phone.length() > MAX_PHONE_LENGTH) {
                throw new IllegalArgumentException("Incorrect phone. Max length: " + MAX_PHONE_LENGTH);
            }
        }
        if (type.equals(Type.DIGITAL_WALLET)) {
            if (accountNumber == null) {
                throw new IllegalArgumentException("Account number must not be null");
            }
            if (accountNumber.length() > MAX_ACCOUNT_NUMBER_LENGTH) {
                throw new IllegalArgumentException("Incorrect account number. Max length: " + MAX_ACCOUNT_NUMBER_LENGTH);
            }
        }
    }

    public static class Type {
        public static final String BANK_ACCOUNT = "bank_account";
        public static final String MOBILE_BALANCE = "mobile_balance";
        public static final String DIGITAL_WALLET = "digital_wallet";
    }
}
