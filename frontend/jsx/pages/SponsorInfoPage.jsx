import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import { Link, withRouter } from 'react-router-dom'
import axios from 'axios';

import DonationButtonGroup from '../common/DonationButtonGroup';
import {Jumbotron, Form, Button, InputGroup, FormControl, Col, Row } from 'react-bootstrap';
import SponsorForm from '../common/sponsorship/SponsorForm';

import '../../static/scss/basic.scss';
import '../../static/scss/sponsor-info.scss';

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
    /** Whether donation duration can be updated. */
    //allowDonationDurationChange: PropTypes.bool.isRequired,
    /** The boolean willPayByCheck determines if payment is by check or not. */
    willPayByCheck: PropTypes.bool.isRequired,
    /** Handler for when Sponsor form 'NEXT' button is clicked */
    sponsorFormClickHandler: PropTypes.func.isRequired
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
        return (
            <div id="sponsor-info" className="sponsor-info">
                <Jumbotron>
                    <div className="container">
                        <h1 className="text-center text-md-left">{`${this.props.i18n.t("sponsor_info:title")}`}</h1>
                    </div>
                </Jumbotron>
                <SponsorForm 
                    i18n={this.props.i18n} 
                    sponsor={this.props.sponsor} 
                    donation={this.props.donation} 
                    willPayByCheck={this.props.willPayByCheck}
                    sponsorFormClickHandler={this.props.sponsorFormClickHandler(this.props.history)}
                />


                <div className="sponsor-info-div">
                    <form id="sponsor-info-form" className="sponsor-info-form">
                    
                        <fieldset>
                            <legend><span className="sponsor-info-section-number">2</span><span>{`${this.props.i18n.t("sponsor_info:form_header_donation_info")}`}</span></legend>
                            {/* <DonationButtonGroup
                                i18n={this.props.i18n}
                                donationDurationChangeHandler={this.donationDurationChangeHandler}
                                currentDonationDuration={this.state.donation.donationDuration}
                                shouldDisplayLabel={true}
                                shouldAllowDonationDurationChanges={this.props.allowDonationDurationChange}
                            /> */}
                            <label htmlFor="donation-amount">{`${this.props.i18n.t("sponsor_info:form_amount")}`}</label>
                            <input type="text" id="donation-amount" readOnly value={this.props.donation.donationAmount} />
                        </fieldset>
                    </form>
                </div>
            </div>
        )
    }
    

}
SponsorInfoPage.propTypes = props;

export default withRouter(SponsorInfoPage);