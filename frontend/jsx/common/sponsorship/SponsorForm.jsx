import React from 'react';
import { PropTypes } from 'prop-types';

import { getIn, Formik, useField, useFormikContext } from 'formik';
import * as Yup from 'yup';
import { Form, Button, Row, Col } from 'react-bootstrap';
import { DonationDuration } from '../../common/utils/donation';



import '../../../static/scss/basic.scss';
import '../../../static/scss/sponsor-info.scss';


// Schema for yup
const validationSchema = Yup.object().shape({
    sponsor: Yup.object().shape({
        firstName: Yup.string()
            .required(),
        lastName: Yup.string()
            .required(),
        email: Yup.string()
            .required()
            .email(),
        churchName: Yup.string(),
        phoneNumber: Yup.string(),
        address: Yup.object().shape({
            streetAddress: Yup.string(),
            city: Yup.string(),
            zip: Yup.string(),
            state: Yup.string(),
            country: Yup.string() 
        }) 
    })
  });


const SponsorForm = ({ i18n, sponsor, donation, sponsorFormClickHandler }) => {
   
    return (
        <div className="sponsor-info-div">
            <div className="container">
            <Formik
                // sets initial values for the form inputs
                initialValues={{
                    sponsor: sponsor,
                    donation: donation
                    }}
                validationSchema={validationSchema}
                onSubmit={(values) => {sponsorFormClickHandler(values)} }
            >
                {/* Callback functions containing Formik state and methods that handle common form actions */} 
                { ({values, 
                    errors, 
                    touched, 
                    handleChange, 
                    handleSubmit, 
                    setFieldValue  }) => (
                        
                    <Form onSubmit={handleSubmit}> 
                        <Row>
                            {/* mb-4 means margin-botton to $spacer * 1.5. $spacer defined in bootstrap css
                            Look at https://getbootstrap.com/docs/4.0/utilities/spacing/ for more info. */}
                            <Col xl={6} className="mb-1">
                                <div className="mb-3">
                                    <h4>{`${i18n.t("sponsor_info:form_header_personal")}`}</h4>
                                </div>
                                <Form.Row>
                                    <Form.Group md={6} as={Col} controlId="firstName">
                                        <Form.Label>{`${i18n.t("sponsor_info:form_first_name")}`}</Form.Label>
                                        <Form.Control 
                                            type="text"
                                            name="sponsor.firstName"
                                            value={values.sponsor.firstName}
                                            onChange={handleChange}
                                            placeholder={i18n.t("sponsor_info:form_placeholder_first_name")}
                                            /* Check if the firstName field has been touched and
                                             if there is an error, if so add the .is_invalid bootstrap class */
                                            isInvalid={getIn(touched, "sponsor.firstName") && getIn(errors, "sponsor.firstName")}
                                            isValid={getIn(touched, "sponsor.firstName") && !getIn(errors, "sponsor.firstName")}
                                        />
                                        <Form.Control.Feedback type="invalid">
                                            {`${i18n.t("sponsor_info:form_error_first_name")}`}
                                        </Form.Control.Feedback>
                                        <Form.Control.Feedback type="valid" />
                                    </Form.Group>
                                    <Form.Group md={6} as={Col} controlId="lastName">
                                        <Form.Label>{`${i18n.t("sponsor_info:form_last_name")}`}</Form.Label>
                                        <Form.Control 
                                            type="text"
                                            name="sponsor.lastName"
                                            value={values.sponsor.lastName}
                                            onChange={handleChange}
                                            isInvalid={getIn(touched, "sponsor.lastName") && getIn(errors, "sponsor.lastName")}
                                            isValid={getIn(touched, "sponsor.lastName") && !getIn(errors, "sponsor.lastName")}
                                            placeholder={i18n.t("sponsor_info:form_placeholder_last_name")}
                                        />
                                        <Form.Control.Feedback type="invalid">
                                            {`${i18n.t("sponsor_info:form_error_last_name")}`}
                                        </Form.Control.Feedback>
                                        <Form.Control.Feedback type="valid" />
                                    </Form.Group>
                                </Form.Row>
                                <Form.Row>
                                    <Form.Group as={Col} controlId="email">
                                        <Form.Label>{`${i18n.t("sponsor_info:form_email")}`}</Form.Label>
                                        <Form.Control 
                                            type="text" 
                                            name="sponsor.email"
                                            value={values.sponsor.email}
                                            onChange={handleChange}
                                            isInvalid={getIn(touched, "sponsor.email") && getIn(errors, "sponsor.email")}
                                            isValid={getIn(touched, "sponsor.email") && !getIn(errors, "sponsor.email")}
                                            placeholder={i18n.t("sponsor_info:form_placeholder_email")}
                                        />
                                        <Form.Control.Feedback type="invalid">
                                           {`${i18n.t("sponsor_info:form_error_email")}`}
                                        </Form.Control.Feedback>
                                        <Form.Control.Feedback type="valid" />
                                    </Form.Group>
                                </Form.Row>
                                <Form.Row>
                                </Form.Row>
                                <Form.Row>
                                    <Form.Group md={6} as={Col} controlId="phoneNumber">
                                        <Form.Label>{`${i18n.t("sponsor_info:form_phone_number")}`}</Form.Label>   
                                        <Form.Control 
                                            placeholder={i18n.t("sponsor_info:form_placeholder_phone_number")}
                                            type="text"
                                            name="sponsor.phoneNumber"
                                            value={values.sponsor.phoneNumber}
                                            onChange={handleChange}
                                        />
                                    </Form.Group>
                                    <Form.Group md={6} as={Col} controlId="churchName">
                                        <Form.Label>{`${i18n.t("sponsor_info:form_church_name")}`}</Form.Label>   
                                        <Form.Control 
                                            placeholder={i18n.t("sponsor_info:form_placeholder_church_name")}
                                            type="text"
                                            name="sponsor.churchName"
                                            value={values.sponsor.churchName}
                                            onChange={handleChange}
                                        />
                                    </Form.Group>
                                </Form.Row>
                                 <Form.Group>
                                    <Form.Check
                                        label={i18n.t("sponsor_info:form_newsletters_subscription_checkbox")}
                                        inline
                                        custom
                                        name="sponsor.subscribed"
                                        id="subsribed"
                                        checked={values.sponsor.subscribed}
                                        type="checkbox"
                                        onChange={() => setFieldValue("subscribed", !values.sponsor.subscribed)}
                                    />
                                </Form.Group>
                            </Col>
                            <Col xl={6}>
                                <div className="mb-3">
                                    <h4>{`${i18n.t("sponsor_info:form_header_address")}`}</h4>
                                </div>
                                <Form.Row>
                                    <Form.Group as={Col} controlId="streetAddress">
                                        <Form.Label>Street Address</Form.Label>
                                        <Form.Control 
                                            placeholder="Street name, number, P.O box, Apt #"
                                            name="sponsor.address.streetAddress"
                                            value={values.sponsor.address.streetAddress}
                                            onChange={handleChange}
                                        />
                                    </Form.Group>
                                </Form.Row>
                                <Form.Row>
                                    <Form.Group md={6} as={Col} controlId="country">
                                        <Form.Label>Country/Region</Form.Label>
                                        <Form.Control 
                                            name="sponsor.address.country"
                                            value={values.sponsor.address.country}
                                            onChange={handleChange}
                                        />
                                    </Form.Group>
                                    <Form.Group md={6} as={Col} controlId="city">
                                        <Form.Label>City</Form.Label>
                                        <Form.Control 
                                            name="sponsor.address.city"
                                            value={values.sponsor.address.city}
                                            onChange={handleChange}
                                        />
                                    </Form.Group>
                                </Form.Row>
                                <Form.Row>
                                    <Form.Group as={Col} controlId="state">
                                        <Form.Label>State</Form.Label>
                                        <Form.Control 
                                            name="sponsor.address.state"
                                            value={values.sponsor.address.state}
                                            onChange={handleChange}
                                        />
                                    </Form.Group>
                                    <Form.Group as={Col} controlId="zip">
                                        <Form.Label>Zip</Form.Label>
                                        <Form.Control 
                                            name="sponsor.address.zip"
                                            value={values.sponsor.address.zip}
                                            onChange={handleChange}
                                        />
                                    </Form.Group>
                                </Form.Row>
                            </Col>
                        </Row>
                        <Row>
                            <Col>
                                <Form.Group>
                                    <Form.Check 
                                        label={`${i18n.t("sponsor_info:form_pay_by_check_checkbox")}`}
                                        name="sponsor.payByCheck"
                                        id="payByCheck"
                                        inline
                                        custom
                                        type="checkbox"
                                        checked={values.sponsor.payByCheck}
                                        onChange={() => setFieldValue("payByCheck", !values.sponsor.payByCheck)}
                                    />
                                </Form.Group>
                            </Col>
                        </Row>
                        <Row className="ml-3 sponsor-form-bottom">
                            <Button className="sponsor-form-btn btn button-primary" type="submit">{`${i18n.t("sponsor_info:form_submit")}`}</Button>
                        </Row>
                    </Form>
                )}
            </Formik>
            </div>
        </div>
    );
}

export default SponsorForm;