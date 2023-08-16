package ru.loolzaaa.youkassa.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PaginatedResponse<T> {
    @JsonProperty("type")
    private String type;
    @JsonProperty("items")
    private List<T> items;
    @JsonProperty("next_cursor")
    private String nextCursor;
}
