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
public class DeliveryMethod implements Validated {

    private static final int MAX_URL_LENGTH = 2048;

    @JsonProperty("type")
    private String type;
    @JsonProperty("url")
    private String url;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;

    @Override
    public void validate() {
        if (type == null) {
            throw new IllegalArgumentException("Type must not be null");
        }
        ApiHelper.checkObjectType(this, "type", String.class, "Type");
        if (type.equals(Type.SELF) && url == null) {
            throw new IllegalArgumentException("Url must not be null if type equals " + Type.SELF);
        }
        if (type.equals(Type.EMAIL) && email == null) {
            throw new IllegalArgumentException("Email must not be null if type equals " + Type.EMAIL);
        }
        if (type.equals(Type.SMS) && phone == null) {
            throw new IllegalArgumentException("Phone must not be null if type equals " + Type.SMS);
        }
        if (url != null && url.length() > MAX_URL_LENGTH) {
            throw new IllegalArgumentException("Incorrect url length. Max length: " + MAX_URL_LENGTH);
        }
    }

    public static class Type {
        public static final String SELF = "self";
        public static final String EMAIL = "email";
        public static final String SMS = "sms";
    }
}
