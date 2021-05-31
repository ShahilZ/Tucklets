package com.tucklets.app.services;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;

@Profile("test")
@ExtendWith(MockitoExtension.class)
public class BrainTreePaymentServiceTest {

    @Mock
    BrainTreePaymentService brainTreePaymentService;


    @BeforeEach
    public void setUp() {
        brainTreePaymentService = Mockito.mock(BrainTreePaymentService.class, "brainTreePaymentServiceMock");
    }

    @Test
    public void testCancelBrainTreeSubscription(){
        String subscriptionId = "123456";
        Mockito.when(brainTreePaymentService.cancelSubscription(subscriptionId)).thenReturn(true);

        Assertions.assertTrue(brainTreePaymentService.cancelSubscription(subscriptionId));
    }

}
