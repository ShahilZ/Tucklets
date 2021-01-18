import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import { Link, withRouter } from 'react-router-dom'
import axios from 'axios';

import {Jumbotron, Form, Button, InputGroup, FormControl, Col, Row } from 'react-bootstrap';

import DonationForm from '../common/sponsorship/DonationForm';
import SponsorForm from '../common/sponsorship/SponsorForm';

import '../../static/scss/basic.scss';
import '../../static/scss/sponsor-info.scss';
import { DonationOrigin } from '../common/utils/enums';

const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
    /** Handler for updating the selected locale. */
    handleSelectedLocaleChange: PropTypes.func.isRequired,
    /** Sponsor object used to say form values */
    sponsor: PropTypes.object.isRequired,
    /** The donation object containing the amount + duration. */
    donation: PropTypes.object.isRequired,
    /** The PayPal Client ID necessary to complete payment. */
    payPalClientId: PropTypes.string.isRequired,
    /** Handler for when Sponsor form 'NEXT' button is clicked */
    sponsorFormClickHandler: PropTypes.func.isRequired,
    /** Donation object to use */
    donation: PropTypes.object.isRequired,
    /** Handler for updating the selected locale. */
    handleSelectedLocaleChange: PropTypes.func.isRequired,
    /** Handler for updating the donation duration. */
    handleDonationDurationChange: PropTypes.func.isRequired,
    /** Handler for updating the donation's payment method. */
    handlePaymentMethodChange: PropTypes.func.isRequired,
    /** Enum that indicates which (UI) page the donation originated from. */
    donationOrigin: PropTypes.string.isRequired,
    /** Handler for updating the donation amount. */
    handleDonationAmountChange: PropTypes.func,
}

class SponsorInfoPage extends Component {

    constructor(props) {
        super(props);
        this.state = {
        };
    }

    componentDidMount(){ 
        window.scrollTo(0,0);
    }

    render() {
        // TODO: user refreshes the page or somehow gets here without going through the flow, redirect to the main page.

        // If user originated from the donationPage, allow for donatoin amount to be changed + show limited donation duration options.
        const isAmountFieldDisabled = this.props.donationOrigin === DonationOrigin.DONATE_PAGE ? false : true;
        const showLimitedOptions = this.props.donationOrigin === DonationOrigin.DONATE_PAGE ? true : false;
        return (
            <div id="sponsor-info" className="sponsor-info">
                <Jumbotron>
                    <div className="container">
                        <h1 className="text-center text-md-left">{`${this.props.i18n.t("sponsor_info:title")}`}</h1>
                    </div>
                </Jumbotron>
                <DonationForm
                    i18n={this.props.i18n}
                    donation={this.props.donation}
                    handleDonationDurationChange={this.props.handleDonationDurationChange}
                    handlePaymentMethodChange={this.props.handlePaymentMethodChange}        
                    isAmountFieldDisabled={isAmountFieldDisabled}   
                    showLimitedDonationDurationOptions={showLimitedOptions}
                    handleDonationAmountChange={this.props.handleDonationAmountChange}
                />
                <br />
                <SponsorForm 
                    i18n={this.props.i18n} 
                    sponsor={this.props.sponsor} 
                    donation={this.props.donation} 
                    sponsorFormClickHandler={this.props.sponsorFormClickHandler(this.props.history)}
                    handleDonationDurationChange={this.props.handleDonationDurationChange}
                    handlePaymentMethodChange={this.props.handlePaymentMethodChange}   
                />
            </div>
        )
    }
    

}
SponsorInfoPage.propTypes = props;

export default withRouter(SponsorInfoPage);