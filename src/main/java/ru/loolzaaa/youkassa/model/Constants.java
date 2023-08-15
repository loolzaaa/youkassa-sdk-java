package ru.loolzaaa.youkassa.model;

public final class Constants {
    public static final String API_ENDPOINT = "https://api.yookassa.ru/v3/";

    public static class PaymentStatus {
        public static final String PENDING = "pending";
        public static final String WAITING_FOR_CAPTURE = "waiting_for_capture";
        public static final String SUCCEEDED = "succeeded";
        public static final String CANCELED = "canceled";
    }
}
