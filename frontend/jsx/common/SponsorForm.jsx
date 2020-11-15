import React from 'react';
import { PropTypes } from 'prop-types';

import { Formik, useField, useFormikContext } from 'formik';
import * as Yup from 'yup';
import { Form, Button, Row, Col } from 'react-bootstrap';


import '../../static/scss/basic.scss';
import '../../static/scss/sponsor-info.scss';


// Schema for yup
const validationSchema = Yup.object().shape({
    firstName: Yup.string()
        .required(),
    lastName: Yup.string()
        .required(),
    email: Yup.string()
        .required()
        .email(),
    address: Yup.string(),
    churchName: Yup.string(),
    subscribed: false,
    phoneNumber: Yup.string(),
    streetAddress: Yup.string(),
    city: Yup.string(),
    zip: Yup.string(),
    state: Yup.string(),
    country: Yup.string() 
  });

const handleSubmitSponsorForm = (event) => {
    const form = event.currentTarget;
    const form1 = event.target;
    console.log("event.currentTarget: ", form);
    console.log("event.target: ", form1);
    //{() => this.props.history.push("confirm")}
}


const SponsorForm = ({ i18n }) => {
    return (
        <div className="sponsor-info-div">
            <div className="container">
            <Formik
                // sets initial values for the form inputs
                initialValues={{
                    firstName: "",
                    lastName: "",
                    email: "",
                    address: "",
                    churchName: "",
                    subscribed: false,
                    phoneNumber: "",
                    streetAddress: "",
                    city: "",
                    zip: "",
                    state: "",
                    country: "" }}
                validationSchema={validationSchema}
            >
                {/* Callback functions containing Formik state and methods that handle common form actions */} 
                { ({values, 
                    errors, 
                    touched, 
                    handleChange, 
                    handleSubmit = {handleSubmitSponsorForm}, 
                    setFieldValue  }) => (
                        
                    <Form onSubmit={handleSubmit}> 
                    {console.log("values: ", values)}
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
                                            name="firstName"
                                            value={values.firstName}
                                            onChange={handleChange}
                                            placeholder={i18n.t("sponsor_info:form_placeholder_first_name")}
                                            /* Check if the firstName field has been touched and
                                             if there is an error, if so add the .is_invalid bootstrap class */
                                            isInvalid={touched.firstName && errors.firstName}
                                            isValid={touched.firstName && !errors.firstName}
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
                                            name="lastName"
                                            value={values.lastName}
                                            onChange={handleChange}
                                            isInvalid={touched.lastName && errors.lastName}
                                            isValid={touched.lastName && !errors.lastName}
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
                                            name="email"
                                            value={values.email}
                                            onChange={handleChange}
                                            isInvalid={touched.email && errors.email}
                                            isValid={touched.email && !errors.email}
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
                                            name="phoneNumber"
                                            value={values.phoneNumber}
                                            onChange={handleChange}
                                        />
                                    </Form.Group>
                                    <Form.Group md={6} as={Col} controlId="churchName">
                                        <Form.Label>{`${i18n.t("sponsor_info:form_church_name")}`}</Form.Label>   
                                        <Form.Control 
                                            placeholder={i18n.t("sponsor_info:form_placeholder_church_name")}
                                            type="text"
                                            name="churchName"
                                            value={values.churchName}
                                            onChange={handleChange}
                                        />
                                    </Form.Group>
                                </Form.Row>
                                 <Form.Group>
                                    <Form.Check
                                        label={i18n.t("sponsor_info:form_newsletters_subscription_checkbox")}
                                        inline
                                        custom
                                        name="subscribed"
                                        id="subsribed"
                                        checked={values.subscribed}
                                        type="checkbox"
                                        onChange={() => setFieldValue("subscribed", !values.subscribed)}
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
                                            name="streetAddress"
                                            value={values.streetAddress}
                                            onChange={handleChange}
                                        />
                                    </Form.Group>
                                </Form.Row>
                                <Form.Row>
                                    <Form.Group md={6} as={Col} controlId="country">
                                        <Form.Label>Country/Region</Form.Label>
                                        <Form.Control 
                                            name="country"
                                            value={values.country}
                                            onChange={handleChange}
                                        />
                                    </Form.Group>
                                    <Form.Group md={6} as={Col} controlId="city">
                                        <Form.Label>City</Form.Label>
                                        <Form.Control 
                                            name="city"
                                            value={values.city}
                                            onChange={handleChange}
                                        />
                                    </Form.Group>
                                </Form.Row>
                                <Form.Row>
                                    <Form.Group as={Col} controlId="state">
                                        <Form.Label>State</Form.Label>
                                        <Form.Control 
                                            name="state"
                                            value={values.state}
                                            onChange={handleChange}
                                        />
                                    </Form.Group>
                                    <Form.Group as={Col} controlId="zip">
                                        <Form.Label>Zip</Form.Label>
                                        <Form.Control 
                                            name="zip"
                                            value={values.zip}
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
                                        name="payByCheck"
                                        id="payByCheck"
                                        inline
                                        custom
                                        type="checkbox"
                                        checked={values.payByCheck}
                                        onChange={() => setFieldValue("payByCheck", !values.payByCheck)}
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