import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import * as Yup from 'yup';
import { getIn, Formik } from 'formik';
import { Form, Button, Row, Col } from 'react-bootstrap';
import axios from 'axios';

import { withRouter } from 'react-router-dom'


import '../../static/scss/basic.scss';
import '../../static/scss/sponsor-info.scss';
import '../../static/scss/unsubscribe.scss';


const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
}

// Schema for yup
const validationSchema = Yup.object().shape({
    email: Yup.string()
                .required()
                .email(),
});

class UnsubscribePage extends Component {

    constructor(props) {
        super(props);
        this.state = {
            showUnsubscribedSuccessfulMessage: false
        };
        this.handleUnsubscribeClick = this.handleUnsubscribeClick.bind(this);
    }

    /***
     * Handler that submits unsubscriptions
     */
    handleUnsubscribeClick(email) {
        let self = this;
        // I had to add headers's Content-Type to 'text-plain' in order for the backend endpoint, unsubscribe/submit/, to accept a string as the parameter.
        axios.post('/unsubscribe/submit/', email, { headers: {'Content-Type' : 'text/plain'}})
        .then(function (response) {
            self.setState({
                showUnsubscribedSuccessfulMessage: true
            });
        })
        .catch(function (error) {
            console.log(error);
        });
    }

    render() {
        // TODO: user refreshes the page or somehow gets here without going through the flow, redirect to the main page.
        return (
            <div className="sponsor-info-div">
            <div className="container">
                <Formik
                    // sets initial values for the form inputs
                    initialValues={{
                        email: ""
                    }}
                    validationSchema={validationSchema}
                    onSubmit={(values) => {this.handleUnsubscribeClick(values.email)} }
                >
                    {/* Callback functions containing Formik state and methods that handle common form actions */}
                    { ({values,
                        errors,
                        touched,
                        handleChange,
                        handleSubmit }) => (

                        <Form onSubmit={handleSubmit}>
                            <Row>
                                <div className="mb-3">
                                    <h4>{this.props.i18n.t("unsubscribe:title")}</h4>
                                </div>
                            </Row>
                            <Row>
                                <Form.Group as={Col} controlId="email">
                                    <Form.Label> {this.props.i18n.t("unsubscribe:email_heading")} </Form.Label>
                                    <Form.Control
                                        type="text"
                                        name="email"
                                        value={values.email}
                                        onChange={handleChange}
                                        isInvalid={getIn(touched, "email") && getIn(errors, "email")}
                                        isValid={getIn(touched, "email") && !getIn(errors, "email")}
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        {this.props.i18n.t("sponsor_info:form_error_email")}
                                    </Form.Control.Feedback>
                                    <Form.Control.Feedback type="valid" />
                                </Form.Group>
                            </Row>
                            <Row className="ml-3 sponsor-form-bottom">
                                    <Button className="sponsor-form-btn btn button-primary" type="submit">{this.props.i18n.t("unsubscribe:unsubscribe")}</Button>
                                    { this.state.showUnsubscribedSuccessfulMessage &&
                                    <div className="success-message">
                                        <p>{this.props.i18n.t("unsubscribe:successfully_unsubscribed")}</p>
                                    </div>
                                    }
                            </Row>
                        </Form>
                    )}
                </Formik>
            </div>
        </div>
        );
    }
}

UnsubscribePage.propTypes = props;

export default withRouter(UnsubscribePage);
