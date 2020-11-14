import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import { Link, withRouter } from 'react-router-dom'
import { PayPalButton } from 'react-paypal-button-v2';
import axios from 'axios';

import DonationButtonGroup from '../common/DonationButtonGroup';
import '../../static/scss/basic.scss';
import '../../static/scss/sponsor-info.scss';

const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
    /** Handler for updating the selected locale. */
    handleSelectedLocaleChange: PropTypes.func.isRequired,
    /** Array of selected children to display */
    selectedChildren: PropTypes.array,
    /** The donation amount provided either by the user or by the backend. */
    donationAmount: PropTypes.number.isRequired,
    /** The donation duration provided by the user. */
    donationDuration: PropTypes.string,
    /** The PayPal Client ID necessary to complete payment. */
    payPalClientId: PropTypes.string.isRequired,
    /** Whether donation duration can be updated. */
    allowDonationDurationChange: PropTypes.bool.isRequired
}

class SponsorInfoPage extends Component {

    constructor(props) {
        super(props);
        this.state = {
            sponsor: {
                firstName: "",
                lastName: "",
                email: "",
                address: "",
                churchName: ""
            },
            donation: {
                donationAmount: props.donationAmount,
                donationDuration: props.donationDuration,
                // default to 0 for now.
                paymentMethod: 0
            },

         };

        this.donationDurationChangeHandler = this.donationDurationChangeHandler.bind(this);
        this.renderSelectedChildren = this.renderSelectedChildren.bind(this);
        this.sponsorInfoChangeHandler = this.sponsorInfoChangeHandler.bind(this);
        this.sponsorInfoSubmitHandler = this.sponsorInfoSubmitHandler.bind(this);
        this.paypalSuccessHandler = this.paypalSuccessHandler.bind(this);
    }

    componentDidMount(){ 
        window.scrollTo(0,0);
    }

    /**
     * Handler for sponsor info changes.
     */
    sponsorInfoChangeHandler(field) {
        let self = this;
        return (event) => {
            let newValue = event.target.value;
            self.setState(prevState => ({
                sponsor: {
                    ...prevState.sponsor,
                    [field]: newValue
                },
            }));
        }
    }

    /**
     * Handler for donation duration changes.
     */
    donationDurationChangeHandler(donationDuration) {
        let self = this;
        return () => {
            self.setState(prevState => ({
                donation: {
                    ...prevState.donation,
                    donationDuration: donationDuration
                },
            }));
        }
    }

    /**
     * Handler for the sponsor info submission button.
     */
    sponsorInfoSubmitHandler() {
        let self = this;
        console.log(this.state);
        let selectedChildIds = [];
        this.props.selectedChildren.map((childContainer) => selectedChildIds.push(childContainer.child.childId));
        axios.post('/sponsor-info/submit/', {
            sponsor: this.state.sponsor,
            donation: this.state.donation,
            children: selectedChildIds

        })
        .then(function (response) {
            console.log(response);
            self.props.history.push("/thank-you/");
        })
        .catch(function (error) {
            console.log(error);
        });
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

    renderSelectedChildren() {
        return (
            <div className="container selected-children-div">
                {this.props.selectedChildren.map((childContainer) => (
                    <div id={`selected-child-id-${childContainer.child.childId}`} key={childContainer.child.childId} className="child-div">
                        <div className="sponsor-child-image-container">
                            <img className="card-img-top"src={childContainer.childImageLocation} alt="Card image cap" />
                        </div>
                        <div className="card-block px-2">
                            <h5 className="card-title">{`${childContainer.child.firstName} ${childContainer.child.lastName}`}</h5>
                            <p className="card-text"><b>{`${this.props.i18n.t("sponsorship:age")}`}</b><span>{`${childContainer.age}`}</span></p>
                            <p className="card-text"><b>{`${this.props.i18n.t("sponsorship:grade")}`}</b><span>{`${childContainer.child.grade}`}</span></p>
                            <p className="card-text"><b>{`${this.props.i18n.t("sponsorship:info")}`}</b><span>{`${childContainer.child.information}`}</span></p>
                        </div>
                        <div className="w-100"></div>
                    </div>
                ))}
            </div>
        )
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
                {this.renderSelectedChildren()}
                <div className="sponsor-info-div">
                    <form id="sponsor-info-form" className="sponsor-info-form">
                        <fieldset>
                            <legend><span className="sponsor-info-section-number">1</span><span>{`${this.props.i18n.t("sponsor_info:form_header_personal")}`}</span></legend>
                            <label htmlFor="sponsor-first-name">{`${this.props.i18n.t("sponsor_info:form_first_name")}`}</label>
                            <input type="text" id="sponsor-first-name" placeholder={this.props.i18n.t("sponsor_info:form_placeholder_first_name")} value={this.state.sponsor.firstName} onChange={this.sponsorInfoChangeHandler("firstName")} />
                            <label htmlFor="sponsor-last-name">{`${this.props.i18n.t("sponsor_info:form_last_name")}`}</label>
                            <input type="text" id="sponsor-last-name" placeholder={this.props.i18n.t("sponsor_info:form_placeholder_last_name")} value={this.state.sponsor.lastName} onChange={this.sponsorInfoChangeHandler("lastName")} />
                            <label htmlFor="sponsor-email">{`${this.props.i18n.t("sponsor_info:form_email")}`}</label>
                            <input type="text" id="sponsor-email" placeholder={this.props.i18n.t("sponsor_info:form_placeholder_email")} value={this.state.sponsor.email} onChange={this.sponsorInfoChangeHandler("email")} />
                            <label htmlFor="sponsor-address">{`${this.props.i18n.t("sponsor_info:form_address")}`}</label>
                            <input type="text" id="sponsor-address" placeholder={this.props.i18n.t("sponsor_info:form_placeholder_address")} value={this.state.sponsor.address} onChange={this.sponsorInfoChangeHandler("address")} />
                            <label htmlFor="sponsor-church-name">{`${this.props.i18n.t("sponsor_info:form_church_name")}`}</label>
                            <input type="text" id="sponsor-church-name" placeholder={this.props.i18n.t("sponsor_info:form_placeholder_church_name")} value={this.state.sponsor.churchName} onChange={this.sponsorInfoChangeHandler("churchName")} />
                        </fieldset>
                        <fieldset>
                            <legend><span className="sponsor-info-section-number">2</span><span>{`${this.props.i18n.t("sponsor_info:form_header_donation_info")}`}</span></legend>
                            <DonationButtonGroup
                                i18n={this.props.i18n}
                                donationDurationChangeHandler={this.donationDurationChangeHandler}
                                currentDonationDuration={this.state.donation.donationDuration}
                                shouldDisplayLabel={true}
                                shouldAllowDonationDurationChanges={this.props.allowDonationDurationChange}
                            />
                            <label htmlFor="donation-amount">{`${this.props.i18n.t("sponsor_info:form_amount")}`}</label>
                            <input type="text" id="donation-amount" readOnly value={this.props.donationAmount} />
                        </fieldset>
                        <PayPalButton
                            amount={this.props.donationAmount}
                            shippingPreference="NO_SHIPPING" // default is "GET_FROM_FILE"
                            onSuccess={this.paypalSuccessHandler()}
                            options={{
                                clientId: this.props.payPalClientId
                              }}
                        />
                    </form>
                </div>
            </div>
        )
    }
    

}
SponsorInfoPage.propTypes = props;

export default withRouter(SponsorInfoPage);