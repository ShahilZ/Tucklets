import React, { Component } from 'react';
import axios from 'axios';
import { PropTypes } from 'prop-types';
import { Link } from 'react-router-dom'
import { withRouter } from 'react-router-dom';

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
    /** Handler for updating a donation amount */
    handleDonationClick: PropTypes.func.isRequired,
    /** Handler for updating the donation duration. */
    handleDonationDurationChange: PropTypes.func.isRequired,
    /** Handler for updating the donation's payment method. */
    handlePaymentMethodChange: PropTypes.func.isRequired
}

class DonationInfoPage extends Component {

    constructor(props) {
        super(props);
        this.state = { donationAmount: "", amountHasErrors: false }
    }

    render() {
        let donationAmountClassName = `form-control ${this.state.amountHasErrors ? 'is-invalid' : ''}`;
        return (
            <DonationForm
                i18n={this.props.i18n}
                donation={this.props.donation}
                handleDonationClick={this.props.handleDonationClick}
                handleDonationDurationChange={this.props.handleDonationDurationChange}
                handlePaymentMethodChange={this.props.handlePaymentMethodChange}                
            />
        )
    }
}


DonationInfoPage.propTypes = props;

export default withRouter(DonationInfoPage);