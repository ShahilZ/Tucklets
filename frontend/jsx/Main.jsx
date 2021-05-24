import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import axios from 'axios';
import { BrowserRouter, Route } from 'react-router-dom';
import { withCookies, Cookies } from 'react-cookie';

import TuckletsNavBar from './common/TuckletsNavBar';
import HomePage from './pages/HomePage';
import AboutPage from './pages/AboutPage';
import ConfirmationPage from './pages/ConfirmationPage';
import OurStoryPage from './pages/OurStoryPage';
import NewslettersPage from './pages/NewslettersPage';
import SponsorChildPage from './pages/SponsorChildPage';
import SponsorInfoPage from './pages/SponsorInfoPage';
import SponsorThankYouPage from './pages/SponsorThankYouPage';
import DonatePage from './pages/DonatePage';
import UnsubscribePage from './pages/UnsubscribePage'
import Footer from './common/Footer';
import i18n from './common/i18n';

import { DonationDuration, DonationOrigin, PaymentMethod } from './common/utils/enums';

import 'bootstrap/dist/css/bootstrap.min.css'; 
import '../static/css/bootstrap-agency-theme.css';
import '../static/scss/basic.scss';


const props = {
    cookies: PropTypes.instanceOf(Cookies).isRequired
};

class Main extends Component {


    constructor(props) {
        super(props);
        this.state = { 
            selectedLocale: i18n.language, 
            selectedChildren: [], 
            // Initialize donation amount for inital render.
            sponsor: {
                firstName: "",
                lastName: "",
                email: "",
                address: { 
                    streetAddress1: "",
                    streetAddress2: "", 
                    city: "",
                    zipCode: "",
                    // TODO: Update
                    state: "CA",
                    country: "" 
                },
                churchName: "",
                subscribed: false,
                phoneNumber: "",
            },
            donation: {
                donationAmount: 0, 
                donationDuration: DonationDuration.MONTHLY,
                paymentMethod: PaymentMethod.PAYPAL }, // default payment method is Paypal. Other options include Check
            payPalClientId: "",
            donationOrigin: DonationOrigin.DONATE_PAGE
        };

        // Bind handlers here

        this.handleSelectedLocaleChange = this.handleSelectedLocaleChange.bind(this);
        // Handlers for donation updates
        this.handleDonationClick = this.handleDonationClick.bind(this);
        this.donationDurationChangeHandler = this.donationDurationChangeHandler.bind(this);
        this.paymentMethodChangeHandler = this.paymentMethodChangeHandler.bind(this);
        this.donationAmountChangeHandler = this.donationAmountChangeHandler.bind(this);
        // Handlers for sponsorship flows.
        this.handleSponsorChildSubmission = this.handleSponsorChildSubmission.bind(this);
        this.handleSponsorshipSubmission = this.handleSponsorshipSubmission.bind(this);
        this.handleSponsorFormClick = this.handleSponsorFormClick.bind(this);
    }

    /**
     * Handler that updates the currently selected locale.
     */
    handleSelectedLocaleChange(event) {
        const selectedLocale = event.target.value;
        // Remove existing cookie if one exists.
        if (this.props.cookies.get('i18next')) {
            this.props.cookies.remove('i18next');
        }
        // Max age of cookie = 1 day.
        this.props.cookies.set('i18next', selectedLocale, { path: '/', maxAge: 86400, secure: true, sameSite: 'strict' })
        i18n.changeLanguage(selectedLocale);
        this.setState({ selectedLocale: selectedLocale });
    }

     /**
     * Handler for the Sponsor Form 'NEXT' button click. 
     * Takes in 'values' object which contains an object of fields to values on the form.
     */
    handleSponsorFormClick(history) {
        let self = this;
        return (values) => {
            self.setState({ 
                sponsor: values.sponsor, 
            });
            history.push("confirm");
        }
    }

    /**
     * Handler for the sponsor info submission button.
     */
    handleSponsorshipSubmission(nonce, history) {
        let self = this;
        return () => {
            let selectedChildIds = [];
            self.state.selectedChildren.map((childContainer) => selectedChildIds.push(childContainer.child.childId));
            axios.post('/sponsor/submit/', {
                sponsor: self.state.sponsor,
                donation: self.state.donation,
                children: selectedChildIds,
                brainTreePaymentContainer: {
                    paymentNonce: nonce
                }
    
            })
            .then(function (response) {
                history.push("/thank-you/");
            })
            .catch(function (error) {
                console.log(error);
            });
        }

    }


    /**
     * Handles form submission after user has selected the children he or she wants to sponsor.
     */
    handleSponsorChildSubmission(childrenSelections, history) {
        let self = this;
        return () => {
            let selectedChildrenIds = []
            for (let childId in childrenSelections) {
                if (childrenSelections[childId]) {
                    selectedChildrenIds.push(childId)
                }
            }
            let requestParams = selectedChildrenIds.join(',');
            axios.get('/sponsor/selections/', {
                params: {
                    childIds: requestParams
                }
            })
            .then(function (response) {
                self.setState({ 
                    selectedChildren: response.data.children, 
                    donation: { donationAmount: response.data.donation.donationAmount, donationDuration: DonationDuration.MONTHLY, paymentMethod: PaymentMethod.PAYPAL },
                    donationOrigin: DonationOrigin.SPONSORSHIP
                });
                // Manually change route after successful response from backend.
                history.push("/sponsor/");
            })
            .catch(function (error) {
                console.log(error);
            });
        }

    }

    /***
     * Handler that leads user from donate to sponsor info page.
     */
    handleDonationClick(amount, donationDuration, history) {
        this.setState(prevState => ({ 
            donation: {
                ...prevState.donation, 
                donationAmount: parseInt(amount), 
                donationDuration: donationDuration

            },
            donationOrigin: DonationOrigin.DONATE_PAGE
        }));
        // Manually change route after successful validation.
        history.push("/sponsor/");
    }

    /***
     * Handles amount changes on sponsor-info page (from donate page).
     */
    donationAmountChangeHandler(event) {
        amount = !event.target.value ? 0 : event.target.value;
        this.setState(prevState => ({ 
            donation: {
                ...prevState.donation, 
                donationAmount: parseInt(amount), 

            },
        }));
    }

    /**
     * Handler for donation duration changes.
     * If the user is coming directly from the "donate page", then skip the backend request to update the amount.
     */
    donationDurationChangeHandler(donationDuration) {
        let self = this;
        return () => {
            // If user is coming from donate page, then just set state and continue.
            if (self.state.donationOrigin === DonationOrigin.DONATE_PAGE) {
                self.setState(prevState => ({
                    donation: {
                        ...prevState.donation,
                        donationDuration: donationDuration,
                    },
                }));
            }
            else {
                axios.get('/info/changeDonationDuration', {
                    params: {
                        amount: self.state.donation.donationAmount,
                        donationDuration: donationDuration.value,
                        prevDuration: self.state.donation.donationDuration.value
                    }
        
                })
                .then(function (response) {
                    self.setState(prevState => ({
                        donation: {
                            ...prevState.donation,
                            donationDuration: donationDuration,
                            donationAmount: response.data.amount
    
                        },
                    }));
                })
                .catch(function (error) {
                    console.log(error);
                });
            }

        }
    }

    /**
     * Handler for payment method changes.
     */
    paymentMethodChangeHandler(paymentMethod) {
        let self = this;
        return () => {
            self.setState(prevState => ({
                donation: {
                    ...prevState.donation,
                    paymentMethod: paymentMethod
                },
            }));
        }
    }


    render() {
        return (
            // Add padding-top to accommodate room for the navigation bar.
            <div className="tucklets-main tucklets-nav-padding">
                <BrowserRouter>
                    <TuckletsNavBar handleSelectedLocaleChange={this.handleSelectedLocaleChange} i18n={i18n} selectedLocale={this.state.selectedLocale} />
                    <Route exact path="/sponsor/">
                        <SponsorInfoPage 
                            selectedChildren={this.state.selectedChildren} 
                            donation={this.state.donation}
                            i18n={i18n} 
                            handleSelectedLocaleChange={this.handleSelectedLocaleChange}
                            payPalClientId={this.state.payPalClientId}
                            sponsor={this.state.sponsor}
                            sponsorFormClickHandler={this.handleSponsorFormClick}
                            handleDonationDurationChange={this.donationDurationChangeHandler}
                            handlePaymentMethodChange={this.paymentMethodChangeHandler}
                            donationOrigin={this.state.donationOrigin}
                            handleDonationAmountChange={this.donationAmountChangeHandler}
                        />
                    </Route>
                    <Route exact path="/sponsor/confirm/">
                        <ConfirmationPage 
                            selectedChildren={this.state.selectedChildren} 
                            donation={this.state.donation}
                            i18n={i18n} 
                            handleSelectedLocaleChange={this.handleSelectedLocaleChange}
                            payPalClientId={this.state.payPalClientId}
                            sponsor={this.state.sponsor}
                            submitButtonHandler={this.handleSponsorshipSubmission}
                        />
                    </Route>
                    <Route exact path="/thank-you/">
                        <SponsorThankYouPage i18n={i18n} handleSelectedLocaleChange={this.handleSelectedLocaleChange} 
                        />
                    </Route>
                    <Route exact path="/unsubscribe/">
                        <UnsubscribePage i18n={i18n} />
                    </Route>
                    <Route exact path="/">
                        <HomePage i18n={i18n} handleSelectedLocaleChange={this.handleSelectedLocaleChange} />
                        <br />
                        <AboutPage i18n={i18n} handleSelectedLocaleChange={this.handleSelectedLocaleChange} numStudents={998} numTeachers={67}/>
                        <br />
                        <OurStoryPage i18n={i18n} handleSelectedLocaleChange={this.handleSelectedLocaleChange} />
                        <br />
                        <NewslettersPage i18n={i18n} handleSelectedLocaleChange={this.handleSelectedLocaleChange} /> 
                        <br />
                        <DonatePage i18n={i18n} handleSelectedLocaleChange={this.handleSelectedLocaleChange} handleDonationClick={this.handleDonationClick}  />
                        <br />
                        <SponsorChildPage i18n={i18n} handleSelectedLocaleChange={this.handleSelectedLocaleChange} handleSponsorChildSubmission={this.handleSponsorChildSubmission} />
                        <br />
                    </Route>
                    
                    <Footer i18n={i18n} />
                </BrowserRouter>
            </div>
        )
    }

    componentDidMount() {
        axios.get('/info/fetchConfigs').then((response) => {
            this.setState({ payPalClientId: response.data.paypal_client_id})
        });
    }
}

Main.propTypes = props;

export default withCookies(Main);
