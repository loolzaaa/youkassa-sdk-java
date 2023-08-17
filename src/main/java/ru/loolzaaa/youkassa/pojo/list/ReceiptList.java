package ru.loolzaaa.youkassa.pojo.list;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.loolzaaa.youkassa.client.PaginatedRequest;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReceiptList extends PaginatedRequest {

    private String createdAtGte;
    private String createdAtGt;
    private String createdAtLte;
    private String createdAtLt;
    private String paymentId;
    private String refundId;
    private String status;

    @Builder
    public ReceiptList(Integer limit, String cursor, String createdAtGte, String createdAtGt, String createdAtLte,
                       String createdAtLt, String paymentId, String refundId, String status) {
        super(limit, cursor);
        this.createdAtGte = createdAtGte;
        this.createdAtGt = createdAtGt;
        this.createdAtLte = createdAtLte;
        this.createdAtLt = createdAtLt;
        this.paymentId = paymentId;
        this.refundId = refundId;
        this.status = status;
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
        if (paymentId != null) {
            params.add("payment_id=" + paymentId);
        }
        if (refundId != null) {
            params.add("refund_id=" + refundId);
        }
        if (status != null) {
            params.add("status=" + status);
        }
        return params.size() > 0 ? String.join("&", params) : "";
    }
}
