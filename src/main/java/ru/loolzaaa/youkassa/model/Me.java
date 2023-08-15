package ru.loolzaaa.youkassa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Me {
    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("test")
    private boolean test;
    @JsonProperty("fiscalization_enabled")
    private boolean fiscalizationEnabled;
    @JsonProperty("payment_methods")
    private List<String> paymentMethods;
    @JsonProperty("itn")
    private String itn;
    @JsonProperty("payout_methods")
    private List<String> payoutMethods;
    @JsonProperty("name")
    private String name;
    @JsonProperty("payout_balance")
    private PayoutBalance payoutBalance;

    static class PayoutBalance {
        @JsonProperty("value")
        private String value;
        @JsonProperty("currency")
        private String currency;
    }
}
