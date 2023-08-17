package ru.loolzaaa.youkassa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.RequestBody;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Webhook implements RequestBody {
    @JsonProperty("id")
    private String id;
    @JsonProperty("event")
    private String event;
    @JsonProperty("url")
    private String url;

    public static void createValidation(Webhook webhook) {
        if (webhook.getEvent() == null || webhook.getUrl() == null) {
            throw new IllegalArgumentException("Event and url must not be null");
        }
    }
}
