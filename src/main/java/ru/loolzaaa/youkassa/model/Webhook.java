package ru.loolzaaa.youkassa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.RequestValidated;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Webhook implements RequestValidated {
    @JsonProperty("id")
    private String id;
    @JsonProperty("event")
    private String event;
    @JsonProperty("url")
    private String url;

    @Override
    public void validate() {
        //
    }
}
