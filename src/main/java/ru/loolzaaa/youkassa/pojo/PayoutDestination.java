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
public class PayoutDestination implements Validated {

    private static final int MAX_BANK_ID_LENGTH = 12;
    private static final int MAX_PHONE_LENGTH = 15;

    @JsonProperty("type")
    private String type;
    @JsonProperty("bank_id")
    private String bankId;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("recipient_checked")
    private Boolean recipientChecked;
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("card")
    private Card card;

    @Override
    public void validate() {
        if (type == null) {
            throw new IllegalArgumentException("Type must nor be null");
        }
        ApiHelper.checkObjectType(this, "type", String.class, "Type");
        if (type.equals(Type.BANK_CARD) && card == null) {
            throw new IllegalArgumentException("Card must not be null if type " + Type.BANK_CARD);
        }
        if (card != null) {
            // This also checks expiry date!
            card.validate();
        }
        if (type.equals(Type.SBP) && (bankId == null || phone == null)) {
            throw new IllegalArgumentException("Bank id and phone must not be null if type " + Type.SBP);
        }
        if (bankId != null && bankId.length() > MAX_BANK_ID_LENGTH) {
            throw new IllegalArgumentException("Too long bank id. Max length: " + MAX_BANK_ID_LENGTH);
        }
        if (phone != null && phone.length() > MAX_PHONE_LENGTH) {
            throw new IllegalArgumentException("Too long phone. Max length: " + MAX_PHONE_LENGTH);
        }
        if (type.equals(Type.YOO_MONEY) && accountNumber == null) {
            throw new IllegalArgumentException("Account number must not be null if type " + Type.YOO_MONEY);
        }
        if (accountNumber != null && (accountNumber.length() < 11 || accountNumber.length() > 33)) {
            throw new IllegalArgumentException("Incorrect account number. Min: 11. Max: 33");
        }
    }

    public static class Type {
        public static final String BANK_CARD = "bank_card";
        public static final String SBP = "spb";
        public static final String YOO_MONEY = "yoo_money";
    }
}
