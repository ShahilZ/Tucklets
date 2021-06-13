package com.tucklets.app.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.stream.Stream;


public enum PaymentMethod
{
    CHECK("CHECK"), OTHER("OTHER");

    private String paymentMethodText;

    PaymentMethod(String paymentMethodText) {
        this.paymentMethodText = paymentMethodText;
    }

    public String getPaymentMethodText() {
        return paymentMethodText;
    }

    public static PaymentMethod of(String paymentMethodText) {
        return Stream.of(PaymentMethod.values())
                .filter(p -> p.getPaymentMethodText().equals(paymentMethodText))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @JsonCreator
    public static PaymentMethod forValue(Map<String, String> jsonObject) {
        return PaymentMethod.of(jsonObject.get("value"));
    }
}