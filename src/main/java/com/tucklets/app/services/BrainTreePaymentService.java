package com.tucklets.app.services;

import com.braintreegateway.*;
import com.tucklets.app.configs.SecretsConfig;
import com.tucklets.app.containers.BrainTreePaymentContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class BrainTreePaymentService {

    @Autowired
    SecretsConfig secretsConfig;

    /**
     * Creates a braintreeGateway object to be used when calling braintree methods
     * @return BraintreeGateway object
     */
    private BraintreeGateway createBrainTreeGateway() {
        return new BraintreeGateway(
                Environment.SANDBOX, // Change this to production later
                secretsConfig.getBrainTreeMerchantId(),
                secretsConfig.getBrainTreePublicKey(),
                secretsConfig.getBrainTreePrivateKey()
        );
    }

    /**
     * Processes payment and submits for settlement.
     * @return Result<Transaction>
     */
    public Result<Transaction> processPayment(String nonce, BigDecimal amount) {
        //create transaction
        TransactionRequest request = new TransactionRequest()
                .amount(amount)
                .paymentMethodNonce(nonce)
//                .deviceData(deviceDataFromTheClient)
                .options()
                .submitForSettlement(true)
                .done();
        BraintreeGateway gateway = createBrainTreeGateway();
        Result<Transaction> result = gateway.transaction().sale(request);
        return result;
    }
}
