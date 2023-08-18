package ru.loolzaaa.youkassa.client;


import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * This class used to request collection of entities.
 * <p>
 * If limit is specified, then returned size of list
 * of requested entities would be less than
 * or equal specified limit.
 * <p>
 * If cursor is specified, then the required piece
 * of data will be requested.
 * <p>
 * Contains base logic for convert filter parameters
 * to http query parameters.
 */

@AllArgsConstructor
public abstract class PaginatedRequest {

    private Integer limit;
    private String cursor;

    /**
     * Converts all specified filter parameters
     * to http query parameters.
     *
     * @return string representation of http query parameters
     * or empty string if there is no specified parameters
     */

    public final String toQueryString() {
        List<String> params = new ArrayList<>(2);
        if (limit != null) {
            params.add("limit=" + limit);
        }
        if (cursor != null) {
            params.add("cursor=" + cursor);
        }
        String baseQueryParams = params.size() > 0 ? String.join("&", params) : "";
        String queryParams = getQuery();
        if (baseQueryParams.isEmpty() && !queryParams.isEmpty()) {
            return "?" + queryParams;
        } else if (!baseQueryParams.isEmpty() && queryParams.isEmpty()) {
            return "?" + baseQueryParams;
        } else if (!baseQueryParams.isEmpty()) {
            return "?" + baseQueryParams + "&" + queryParams;
        } else {
            return "";
        }
    }

    /**
     * Query parameters converter for classes that extends this.
     *
     * @return string representation of http query parameters
     * or empty string if there is no specified parameters
     */

    public abstract String getQuery();
}
