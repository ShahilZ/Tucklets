import React from 'react';
import { PropTypes } from 'prop-types';

import { Form, Button, Row, Col } from 'react-bootstrap';

import DonationToggle from './DonationToggle';

import { DonationDuration, PaymentMethod } from '../utils/enums.js';

import '../../../static/scss/basic.scss';
import '../../../static/scss/sponsor-info.scss';

const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
    /** Donation info. */
    donation: PropTypes.object.isRequired,
    /** Handler for updating donation amount. */
    handleDonationDurationChange: PropTypes.func.isRequired,
    /** Handler for updating the payment method */
    handlePaymentMethodChange: PropTypes.func.isRequired
}



const DonationForm = ({ i18n, donation, handleDonationDurationChange, handlePaymentMethodChange }) => {
   
    return (
        <div className="donation-info-div">
            <div className="container donation-info-container">
                <Form className="donation-info-form">
                    <Row>
                        <Form.Group md={3} as={Col} controlId="amount">
                            <Form.Label>{i18n.t("donate:form_amount")}</Form.Label>
                            <Form.Control readOnly 
                                type="text"
                                name="donation.amount"
                                value={donation.donationAmount}              
                            />
                            <Form.Control.Feedback type="invalid">
                                {i18n.t("sponsor_info:form_error_first_name")}
                            </Form.Control.Feedback>
                            <Form.Control.Feedback type="valid" />
                        </Form.Group>
                    </Row>

                    <Row>
                        <Form.Group md={6} as={Col} controlId="duration">
                            <Form.Label>{i18n.t("donate:form_duration")}</Form.Label>
                            <br />
                            <DonationToggle 
                                i18n={i18n}
                                donation={donation}
                                donationField="donationDuration"
                                options={[DonationDuration.MONTHLY, DonationDuration.YEARLY, DonationDuration.YEARLY_RECURRING]}
                                onClickHandler={handleDonationDurationChange}
                            />

                        </Form.Group>

                    </Row>
                    <Row>
                        <Form.Group md={3} as={Col} controlId="payment-method">
                            <Form.Label>{i18n.t("donate:form_payment_method")}</Form.Label>
                            <br />
                            <DonationToggle 
                                i18n={i18n}
                                donation={donation}
                                donationField="paymentMethod"
                                options={[PaymentMethod.PAYPAL, PaymentMethod.CHECK]}
                                onClickHandler={handlePaymentMethodChange}
                            />
                        </Form.Group>
                    </Row>
                </Form>
            </div>
        </div>
    );
}

export default DonationForm;