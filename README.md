# YooKassa API Java Client Library (unofficial)
**Неофициальный** клиент для работы с платежами по [API ЮKassa](https://yookassa.ru/developers/api) Подходит тем, у кого способ подключения к ЮKassa называется API.

## Установка
Подключите зависимость для maven:
```xml
<dependency>
    <groupId>ru.loolzaaa</groupId>
    <artifactId>youkassa-sdk-java</artifactId>
    <version>0.0.1</version>
</dependency>
```

## Начало работы
Для начала работы необходимо создать экземпляр `ApiClient`.
Конфигурация аутентификации через Basic Authentication:
```java
ApiClient client = ApiClientBuilder.newBuilder()
        .configureBasicAuth("shop_id", "secret_key")
        .build();
```
Или через OAuth:
```java
ApiClient client = ApiClientBuilder.newBuilder()
        .configureOAuth("oauth_token")
        .build();
```
После этого следует использовать один из процессоров:
```java
PaymentProcessor paymentProcessor = new PaymentProcessor(client);
Payment payment = paymentProcessor.findById("payment_id");
```

## Примеры использования SDK
С помощью этого SDK вы можете работать с онлайн-платежами: отправлять запросы на оплату, сохранять платежную информацию для повторных списаний, совершать возвраты и многое другое.

### Настройки SDK API ЮKassa
#### [Аутентификация](https://yookassa.ru/developers/partners-api/basics)
Для работы с API необходимо прописать в конфигурации данные аутентификации. Существует два способа аутентификации:

- shopId + секретный ключ
```java
ApiClient client = ApiClientBuilder.newBuilder()
        .configureBasicAuth("shop_id", "secret_key")
        .build();
```

- OAuth-токен
```java
ApiClient client = ApiClientBuilder.newBuilder()
    .configureOAuth("oauth_token")
    .build();
```

#### [Получение информации о магазине](https://yookassa.ru/developers/api?lang=python#me_object)
После установки конфигурации можно проверить корректность данных, а также получить информацию о магазине.
```java
MeProcessor meProcessor = new MeProcessor(client);
Me me = meProcessor.findMe();
```

#### [Работа с Webhook](https://yookassa.ru/developers/api?lang=python#webhook)
Если вы подключаетесь к API через Oauth-токен, то можете настроить получение уведомлений о смене статуса платежа или возврата.

Например, ЮKassa может сообщить, когда объект платежа, созданный в вашем приложении, перейдет в статус `succeeded`.

В данном примере мы создаем новых вебхук для succeeded уведомлений, после чего находим все вебхуки и удаляем их.
```java
WebhookProcessor webhookProcessor = new WebhookProcessor(client);
Webhook newWebhook = webhookProcessor.create(Webhook.builder()
        .event("payment.succeeded")
        .url("http://example.com/api")
        .build(), null);
PaginatedResponse<Webhook> webhooks = webhookProcessor.findAll();
for (Webhook webhook : webhooks.getItems()) {
    webhookProcessor.removeById(webhook.getId(), null);
}
```

### Работа с платежами
SDK позволяет создавать, подтверждать, отменять платежи, а также получать информацию о них.

Объект платежа `Payment` содержит всю информацию о платеже, актуальную на текущий момент времени. Он формируется при создании платежа и приходит в ответ на любой запрос, связанный с платежами.

#### [Запрос на создание платежа](https://yookassa.ru/developers/api?lang=python#create_payment)
Чтобы принять оплату, необходимо создать объект платежа — `Payment`. Он содержит всю необходимую информацию для проведения оплаты (сумму, валюту и статус). У платежа линейный жизненный цикл, он последовательно переходит из статуса в статус.

В ответ на запрос придет объект платежа — `Payment` в актуальном статусе.
```java
PaymentProcessor paymentProcessor = new PaymentProcessor(client);
Payment payment = paymentProcessor.create(Payment.builder()
        .amount(Amount.builder().value("100.00").currency(Currency.RUB).build())
        .description("New payment")
        .confirmation(Confirmation.builder()
                .type(Confirmation.Type.REDIRECT)
                .returnUrl("https://www.example.com/return_url")
                .build())
        .build(), null);
```

#### [Запрос на частичное подтверждение платежа](https://yookassa.ru/developers/api?lang=python#capture_payment)
Подтверждает вашу готовность принять платеж. После подтверждения платеж перейдет в статус `succeeded`. Это значит, что вы можете выдать товар или оказать услугу пользователю.

Подтвердить можно только платеж в статусе `waiting_for_capture` и только в течение определенного времени (зависит от способа оплаты). Если вы не подтвердите платеж в отведенное время, он автоматически перейдет в статус `canceled`, и деньги вернутся пользователю.

В ответ на запрос придет объект платежа в актуальном статусе.
```java
PaymentProcessor paymentProcessor = new PaymentProcessor(client);
Payment payment = paymentProcessor.capture("payment_id", Payment.builder().build(), null);
```

#### [Запрос на отмену незавершенного платежа](https://yookassa.ru/developers/api?lang=python#cancel_payment)
Отменяет платеж, находящийся в статусе `waiting_for_capture`. Отмена платежа значит, что вы не готовы выдать пользователю товар или оказать услугу. Как только вы отменяете платеж, мы начинаем возвращать деньги на счет плательщика. Для платежей банковскими картами или из кошелька ЮMoney отмена происходит мгновенно. Для остальных способов оплаты возврат может занимать до нескольких дней.

В ответ на запрос придет объект платежа в актуальном статусе.
```java
PaymentProcessor paymentProcessor = new PaymentProcessor(client);
Payment payment = paymentProcessor.cancel("payment_id", null);
```

#### [Получить информацию о платеже](https://yookassa.ru/developers/api?lang=python#get_payment)
Запрос позволяет получить информацию о текущем состоянии платежа по его уникальному идентификатору.

В ответ на запрос придет объект платежа в актуальном статусе.
```java
PaymentProcessor paymentProcessor = new PaymentProcessor(client);
Payment payment = paymentProcessor.findById("payment_id");
```

#### [Получить список платежей с фильтрацией](https://yookassa.ru/developers/api?lang=python#get_payments_list)
Запрос позволяет получить список платежей, отфильтрованный по заданным критериям.

В ответ на запрос вернется список платежей с учетом переданных параметров. В списке будет информация о платежах, созданных за последние 3 года. Список будет отсортирован по времени создания платежей в порядке убывания.

Если результатов больше, чем задано в `limit`, список будет выводиться фрагментами. В этом случае в ответе на запрос вернется фрагмент списка и параметр `next_cursor` с указателем на следующий фрагмент.
```java
PaymentProcessor paymentProcessor = new PaymentProcessor(client);
PaymentList paymentList = PaymentList.builder()
        .limit(10)
        .status(Payment.Status.WAITING_FOR_CAPTURE)
        .createdAtLte("2020-08-08T00:00:00.000Z")
        .build();
PaginatedResponse<Payment> payments = paymentProcessor.findAll(paymentList);
```

### Работа с возвратами
С помощью SDK можно возвращать платежи — полностью или частично. Порядок возврата зависит от способа оплаты (`payment_method`) исходного платежа. При оплате банковской картой деньги возвращаются на карту, которая была использована для проведения платежа.

#### [Запрос на создание возврата](https://yookassa.ru/developers/api?lang=python#create_refund)
Создает возврат успешного платежа на указанную сумму. Платеж можно вернуть только в течение трех лет с момента его создания. Комиссия ЮKassa за проведение платежа не возвращается.

В ответ на запрос придет объект возврата - `Refund` в актуальном статусе.
```java
RefundProcessor refundProcessor = new RefundProcessor(client);
Refund refund = refundProcessor.create(Refund.builder()
        .paymentId("payment_id")
        .amount(Amount.builder().value("100.00").currency(Currency.RUB).build())
        .build(), null);
```

#### [Получить информацию о возврате](https://yookassa.ru/developers/api?lang=python#get_refund)
Запрос позволяет получить информацию о текущем состоянии возврата по его уникальному идентификатору.

В ответ на запрос придет объект возврата - `Refund` в актуальном статусе.
```java
RefundProcessor refundProcessor = new RefundProcessor(client);
Refund refund = refundProcessor.findById("refund_id");
```

#### [Получить список возвратов с фильтрацией](https://yookassa.ru/developers/api?lang=python#get_refunds_list)
Запрос позволяет получить список возвратов, отфильтрованный по заданным критериям.

В ответ на запрос вернется список возвратов с учетом переданных параметров. В списке будет информация о возвратах, созданных за последние 3 года. Список будет отсортирован по времени создания возвратов в порядке убывания.

сли результатов больше, чем задано в `limit`, список будет выводиться фрагментами. В этом случае в ответе на запрос вернется фрагмент списка и параметр `next_cursor` с указателем на следующий фрагмент.
```java
RefundProcessor refundProcessor = new RefundProcessor(client);
RefundList refundList = RefundList.builder()
        .createdAtGte("2020-08-08T00:00:00.000Z")
        .limit(20)
        .build();
PaginatedResponse<Refund> refunds = refundProcessor.findAll(refundList);
```

### Работа с чеками
> Для тех, кто использует [решение ЮKassa для 54-ФЗ](https://yookassa.ru/developers/54fz/basics).

С помощью SDK можно получать информацию о чеках, для которых вы отправили данные через ЮKassa.

#### [Запрос на создание чека](https://yookassa.ru/developers/api?lang=python#create_receipt)
Запрос позволяет передать онлайн-кассе данные для формирования [чека зачета предоплаты](https://yookassa.ru/developers/54fz/payments#settlement-receipt).

Если вы работаете по сценарию [Сначала платеж, потом чек](https://yookassa.ru/developers/54fz/basics#receipt-after-payment), в запросе также нужно передавать данные для формирования чека прихода и чека возврата прихода.

В ответ на запрос придет объект чека - `Receipt` в актуальном статусе.
```java
ReceiptProcessor receiptProcessor = new ReceiptProcessor(client);
Receipt receipt = receiptProcessor.create(Receipt.builder()
        .type(Receipt.Type.PAYMENT)
        .customer(Customer.builder().phone("79000000000").build())
        .items(List.of(Item.builder()
                .description("Item 1")
                .amount(Amount.builder().value("100.00").currency(Currency.RUB).build())
                .vatCode(1)
                .quantity("1")
                .build()))
        .send(true)
        .settlements(List.of(Settlement.builder()
                .type("cashless")
                .amount(Amount.builder().value("100.00").currency(Currency.RUB).build())
                .build()))
        .build(), null);
```

#### [Получить информацию о чеке](https://yookassa.ru/developers/api?lang=python#get_receipt)
Запрос позволяет получить информацию о текущем состоянии чека по его уникальному идентификатору.

В ответ на запрос придет объект чека - `Receipt` в актуальном статусе.
```java
ReceiptProcessor receiptProcessor = new ReceiptProcessor(client);
Receipt receipt = receiptProcessor.findById("receipt_id");
```

#### [Получить список чеков с фильтрацией](https://yookassa.ru/developers/api?lang=python#get_receipts_list)
Запрос позволяет получить список чеков, отфильтрованный по заданным критериям. Можно запросить чеки по конкретному платежу, чеки по конкретному возврату или все чеки магазина.

В ответ на запрос вернется список чеков с учетом переданных параметров. В списке будет информация о чеках, созданных за последние 3 года. Список будет отсортирован по времени создания чеков в порядке убывания.

Если результатов больше, чем задано в `limit`, список будет выводиться фрагментами. В этом случае в ответе на запрос вернется фрагмент списка и параметр `next_cursor` с указателем на следующий фрагмент.
```java
ReceiptProcessor receiptProcessor = new ReceiptProcessor(client);
ReceiptList refundList = ReceiptList.builder()
        .paymentId("payment_id")
        .createdAtLt("2020-10-20T00:00:00.000Z")
        .build();
PaginatedResponse<Receipt> receipts = receiptProcessor.findAll(refundList);
```
