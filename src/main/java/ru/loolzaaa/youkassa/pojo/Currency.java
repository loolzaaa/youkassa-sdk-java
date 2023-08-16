package ru.loolzaaa.youkassa.pojo;

public class Currency {
    public static final String RUB = "RUB";
    public static final String USD = "USD";
    public static final String EUR = "EUR";
    public static final String BYN = "BYN";
    public static final String CNY = "CNY";
    public static final String KZT = "KZT";
    public static final String UAH = "UAH";

    public static boolean validate(String currency) {
        return RUB.equals(currency) ||
                USD.equals(currency) ||
                EUR.equals(currency) ||
                BYN.equals(currency) ||
                CNY.equals(currency) ||
                KZT.equals(currency) ||
                UAH.equals(currency);
    }
}
