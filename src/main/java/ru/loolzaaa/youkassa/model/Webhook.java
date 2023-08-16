package ru.loolzaaa.youkassa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Webhook {
    @JsonProperty("id")
    private String id;
    @JsonProperty("event")
    private String event;
    @JsonProperty("url")
    private String url;
}
