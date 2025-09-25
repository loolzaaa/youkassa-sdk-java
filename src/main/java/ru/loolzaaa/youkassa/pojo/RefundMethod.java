package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.Validated;
import ru.loolzaaa.youkassa.helper.ApiHelper;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefundMethod implements Validated {
    private String type;
    private String sbpOperationId;
    private List<Article> articles;
    private ElectronicCertificate electronicCertificate;

    @Override
    public void validate() {
        if (type == null) {
            throw new IllegalArgumentException("Type must not be null");
        }
        ApiHelper.checkObjectType(this, "type", String.class, "Type");
        if (articles != null) {
            for (Article article : articles) {
                article.validate();
            }
        }
        if (electronicCertificate != null) {
            electronicCertificate.validate();
        }
    }

    public static class Type {
        public static final String SBP = "sbp";
        public static final String ELECTRONIC_CERTIFICATE = "electronic_certificate";
    }
}
