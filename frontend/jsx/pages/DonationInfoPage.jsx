import React, { Component } from 'react';
import axios from 'axios';
import { PropTypes } from 'prop-types';
import { Link } from 'react-router-dom'
import { withRouter } from 'react-router-dom';
import { Button } from 'react-bootstrap';


import { DonationButtonGroup } from '../common/DonationButtonGroup';
import { DonationDuration } from '../common/utils/enums';
import DonationForm from '../common/sponsorship/DonationForm';

import '../../static/scss/info.scss';

const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
    /** Donation object to use */
    donation: PropTypes.object.isRequired,
    /** Handler for updating the selected locale. */
    handleSelectedLocaleChange: PropTypes.func.isRequired,
    /** Handler for updating the donation duration. */
    handleDonationDurationChange: PropTypes.func.isRequired,
    /** Handler for updating the donation's payment method. */
    handlePaymentMethodChange: PropTypes.func.isRequired
}

class DonationInfoPage extends Component {

    constructor(props) {
        super(props);
        this.state = { donationAmount: "", amountHasErrors: false }

        this.donationSelectionHandler = this.donationSelectionHandler.bind(this);
    }
    
    /***
     * Handler that leads user from donate info page to sponsor info page.
     */
    donationSelectionHandler(history) {
        return () => {
            // Manually change route after successful validation.
            history.push("/sponsor-info/");
        };
    }

    render() {
        let donationAmountClassName = `form-control ${this.state.amountHasErrors ? 'is-invalid' : ''}`;
        return (
            <div className="donation-info-div bg-light">
                <div className="jumbotron jumbotron-fluid">
                    <div className="container">
                        <h1 className="display-4">{`${this.props.i18n.t("donate:form_header_donation_info")}`}</h1>
                        <p className="lead">{`${this.props.i18n.t("donate:donate-description")}`}</p>
                    </div>
                </div>
                <DonationForm
                    i18n={this.props.i18n}
                    donation={this.props.donation}
                    handleDonationDurationChange={this.props.handleDonationDurationChange}
                    handlePaymentMethodChange={this.props.handlePaymentMethodChange}                
                />
                <br />
                <Button className="sponsor-form-btn btn button-primary" onClick={this.donationSelectionHandler(this.props.history)}>{this.props.i18n.t("sponsor_info:form_submit")}</Button>

            </div>

        )
    }
}


DonationInfoPage.propTypes = props;

export default withRouter(DonationInfoPage);