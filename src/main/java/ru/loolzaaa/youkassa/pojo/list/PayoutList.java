package ru.loolzaaa.youkassa.pojo.list;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.loolzaaa.youkassa.client.PaginatedRequest;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PayoutList extends PaginatedRequest {

    private String createdAtGte;
    private String createdAtGt;
    private String createdAtLte;
    private String createdAtLt;
    private String payoutDestinationType;
    private Map<String, String> metadata;
    private String status;

    @Builder
    public PayoutList(Integer limit, String cursor, String createdAtGte, String createdAtGt, String createdAtLte,
                      String createdAtLt, String payoutDestinationType, String status, Map<String, String> metadata) {
        super(limit, cursor);
        this.createdAtGte = createdAtGte;
        this.createdAtGt = createdAtGt;
        this.createdAtLte = createdAtLte;
        this.createdAtLt = createdAtLt;
        this.payoutDestinationType = payoutDestinationType;
        this.status = status;
        this.metadata = metadata;
    }

    @Override
    public String getQuery() {
        List<String> params = new ArrayList<>();
        if (createdAtGte != null) {
            params.add("created_at.gte=" + createdAtGte);
        }
        if (createdAtGt != null) {
            params.add("created_at.gt=" + createdAtGt);
        }
        if (createdAtLte != null) {
            params.add("created_at.lte=" + createdAtLte);
        }
        if (createdAtLt != null) {
            params.add("created_at.lt=" + createdAtLt);
        }
        if (payoutDestinationType != null) {
            params.add("payout_destination.type=" + payoutDestinationType);
        }
        if (status != null) {
            params.add("status=" + status);
        }
        if (metadata != null) {
            String key = metadata.keySet().stream().findFirst().orElseThrow();
            String value = metadata.get(key);
            params.add(URLEncoder.encode("metadata[%s]=%s".formatted(key, value), StandardCharsets.UTF_8));
        }
        return !params.isEmpty() ? String.join("&", params) : "";
    }
}
