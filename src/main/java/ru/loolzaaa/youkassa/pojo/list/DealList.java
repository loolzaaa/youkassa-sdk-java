package ru.loolzaaa.youkassa.pojo.list;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.loolzaaa.youkassa.client.PaginatedRequest;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DealList extends PaginatedRequest {

    private String createdAtGte;
    private String createdAtGt;
    private String createdAtLte;
    private String createdAtLt;
    private String expiresAtGte;
    private String expiresAtGt;
    private String expiresAtLte;
    private String expiresAtLt;
    private String status;
    private String fullTextSearch;

    @Builder
    public DealList(Integer limit, String cursor, String createdAtGte, String createdAtGt, String createdAtLte,
                    String createdAtLt, String expiresAtGte, String expiresAtGt, String expiresAtLte,
                    String expiresAtLt, String status, String fullTextSearch) {
        super(limit, cursor);
        this.createdAtGte = createdAtGte;
        this.createdAtGt = createdAtGt;
        this.createdAtLte = createdAtLte;
        this.createdAtLt = createdAtLt;
        this.expiresAtGte = expiresAtGte;
        this.expiresAtGt = expiresAtGt;
        this.expiresAtLte = expiresAtLte;
        this.expiresAtLt = expiresAtLt;
        this.status = status;
        this.fullTextSearch = fullTextSearch;
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
        if (expiresAtGte != null) {
            params.add("expires_at.gte=" + expiresAtGte);
        }
        if (expiresAtGt != null) {
            params.add("expires_at.gt=" + expiresAtGt);
        }
        if (expiresAtLte != null) {
            params.add("expires_at.lte=" + expiresAtLte);
        }
        if (expiresAtLt != null) {
            params.add("expires_at.lt=" + expiresAtLt);
        }
        if (status != null) {
            params.add("status=" + status);
        }
        if (fullTextSearch != null) {
            params.add("full_text_search=" + fullTextSearch);
        }
        return params.size() > 0 ? String.join("&", params) : "";
    }
}
