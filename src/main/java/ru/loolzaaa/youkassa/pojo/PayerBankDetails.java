package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayerBankDetails {
    @JsonProperty("bank_id")
    private String bankId;
    @JsonProperty("bic")
    private String bic;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("short_name")
    private String shortName;
    @JsonProperty("address")
    private String address;
    @JsonProperty("inn")
    private String inn;
    @JsonProperty("bank_name")
    private String bankName;
    @JsonProperty("bank_branch")
    private String bankBranch;
    @JsonProperty("bank_bik")
    private String bankBik;
    @JsonProperty("account")
    private String account;
    @JsonProperty("kpp")
    private String kpp;
}
