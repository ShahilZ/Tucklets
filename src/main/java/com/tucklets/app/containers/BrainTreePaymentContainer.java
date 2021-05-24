package com.tucklets.app.containers;

public class BrainTreePaymentContainer {

    private String paymentNonce;

    public BrainTreePaymentContainer() {};

    public BrainTreePaymentContainer(String paymentNonce) {
        this.paymentNonce = paymentNonce;
    }

    public String getPaymentNonce() {
        return paymentNonce;
    }
}
