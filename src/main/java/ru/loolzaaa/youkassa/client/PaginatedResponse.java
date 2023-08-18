package ru.loolzaaa.youkassa.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This class represents collection of requested entities.
 * <p>
 * If count of requested entities more than
 * specified in request limit, then {@link #nextCursor}
 * would not be null. This cursor must be specified
 * in the request if it is necessary to obtain
 * the next piece of data.
 *
 * @param <T> response entity type
 */

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
