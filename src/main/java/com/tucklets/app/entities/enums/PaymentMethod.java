package com.tucklets.app.entities.enums;

import java.util.stream.Stream;


public enum PaymentMethod
{
    CHECK(0), PAYPAL(1);

    private int paymentMethodValue;

    PaymentMethod(int paymentMethodValue) {
        this.paymentMethodValue = paymentMethodValue;
    }

    public int getPaymentMethodValue() {
        return paymentMethodValue;
    }

    public static PaymentMethod of(int paymentMethodValue) {
        return Stream.of(PaymentMethod.values())
                .filter(p -> p.getPaymentMethodValue() == paymentMethodValue)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}