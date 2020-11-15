import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import { Link, withRouter } from 'react-router-dom'
import axios from 'axios';

import DonationButtonGroup from '../common/DonationButtonGroup';
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
    allowDonationDurationChange: PropTypes.bool.isRequired,
    /** Handler for sponsor info changes */
    sponsorInfoChangeHandler: PropTypes.func.isRequired
}

class SponsorInfoPage extends Component {

    constructor(props) {
        super(props);
        this.state = {
            donation: {
                donationAmount: props.donationAmount,
                donationDuration: props.donationDuration,
                // default to 0 for now.
                paymentMethod: 0
            },

         };

        this.paypalSuccessHandler = this.paypalSuccessHandler.bind(this);
    }

    componentDidMount(){ 
        window.scrollTo(0,0);
    }


    /**
     * Success handler for PayPal submission.
     */
    paypalSuccessHandler() {
        let self = this;
        return (details, data) => {
            self.sponsorInfoSubmitHandler();
        }
    }
    

    render() {
        // TODO: user refreshes the page or somehow gets here without going through the flow, redirect to the main page.
        return (
            <div id="sponsor-info">
                <div className="jumbotron jumbotron-fluid">
                    <div className="container">
                        <h1 className="display-4">{`${this.props.i18n.t("sponsor_info:title")}`}</h1>
                        <p className="lead">{`${this.props.i18n.t("sponsor_info:subtitle")}`}</p>
                    </div>
                </div>
                <div className="sponsor-info-div">
                    <form id="sponsor-info-form" className="sponsor-info-form">
                        <fieldset>
                            <legend><span className="sponsor-info-section-number">1</span><span>{`${this.props.i18n.t("sponsor_info:form_header_personal")}`}</span></legend>
                            <label htmlFor="sponsor-first-name">{`${this.props.i18n.t("sponsor_info:form_first_name")}`}</label>
                            <input type="text" id="sponsor-first-name" placeholder={this.props.i18n.t("sponsor_info:form_placeholder_first_name")} value={this.props.sponsor.firstName} onChange={this.props.sponsorInfoChangeHandler("firstName")} />
                            <label htmlFor="sponsor-last-name">{`${this.props.i18n.t("sponsor_info:form_last_name")}`}</label>
                            <input type="text" id="sponsor-last-name" placeholder={this.props.i18n.t("sponsor_info:form_placeholder_last_name")} value={this.props.sponsor.lastName} onChange={this.props.sponsorInfoChangeHandler("lastName")} />
                            <label htmlFor="sponsor-email">{`${this.props.i18n.t("sponsor_info:form_email")}`}</label>
                            <input type="text" id="sponsor-email" placeholder={this.props.i18n.t("sponsor_info:form_placeholder_email")} value={this.props.sponsor.email} onChange={this.props.sponsorInfoChangeHandler("email")} />
                            <label htmlFor="sponsor-address">{`${this.props.i18n.t("sponsor_info:form_address")}`}</label>
                            <input type="text" id="sponsor-address" placeholder={this.props.i18n.t("sponsor_info:form_placeholder_address")} value={this.props.sponsor.address} onChange={this.props.sponsorInfoChangeHandler("address")} />
                            <label htmlFor="sponsor-church-name">{`${this.props.i18n.t("sponsor_info:form_church_name")}`}</label>
                            <input type="text" id="sponsor-church-name" placeholder={this.props.i18n.t("sponsor_info:form_placeholder_church_name")} value={this.props.sponsor.churchName} onChange={this.props.sponsorInfoChangeHandler("churchName")} />
                        </fieldset>
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
                            <div className="btn btn-secondary" onClick={() => this.props.history.push("confirm")}>Next</div>
                        </fieldset>
                    </form>
                </div>
            </div>
        )
    }
    

}
SponsorInfoPage.propTypes = props;

export default withRouter(SponsorInfoPage);