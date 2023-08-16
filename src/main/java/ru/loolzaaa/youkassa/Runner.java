package ru.loolzaaa.youkassa;

import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.client.ApiClientBuilder;
import ru.loolzaaa.youkassa.model.Payment;
import ru.loolzaaa.youkassa.processors.PaymentProcessor;

public class Runner {
    public static void main(String[] args) {
        ApiClient client = ApiClientBuilder.newBuilder()
                .configureBasicAuth(System.getenv("YOU_ID"), System.getenv("YOU_KEY"))
                .build();
        PaymentProcessor paymentProcessor = new PaymentProcessor(client);

//        Payment paymentParams = Payment.builder()
//                .amount(Amount.builder().value("100.00").currency("RUB").build())
//                .description("Test description")
//                .confirmation(Confirmation.builder()
//                        .type(Confirmation.Type.REDIRECT)
//                        .returnUrl("http://example.com")
//                        .build())
//                .build();
//        Payment payment = paymentProcessor.create(paymentParams, null);
//        System.out.println(payment.getConfirmation().getConfirmationUrl());

        Payment byId = paymentProcessor.findById("2c6e5c24-000f-5000-9000-162da2ef96c3");
        System.out.println(byId.getStatus());

        Payment paymentParams = Payment.builder().build();
        Payment captured = paymentProcessor.capture("2c6e5c24-000f-5000-9000-162da2ef96c3", paymentParams, null);
        System.out.println(captured.getStatus());
    }
}
