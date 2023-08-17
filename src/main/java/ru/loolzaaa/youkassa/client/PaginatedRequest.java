package ru.loolzaaa.youkassa.client;


import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public abstract class PaginatedRequest {

    private Integer limit;
    private String cursor;

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

    public abstract String getQuery();
}
