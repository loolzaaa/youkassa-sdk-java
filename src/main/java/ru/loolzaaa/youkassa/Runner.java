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

        Payment paymentById = paymentProcessor.findById("123");
        System.out.println(paymentById);

//        Payment paymentParams = Payment.builder()
//                .amount(Payment.Amount.builder().value("100.00").currency("RUB").build())
//                .description("Test description")
//                .confirmation(Payment.Confirmation.builder().type(Payment.Confirmation.Type.REDIRECT).returnUrl("http://example.com").build())
//                .build();
//        Payment payment = paymentProcessor.create(paymentParams, null);
//        System.out.println(payment);
    }
}
