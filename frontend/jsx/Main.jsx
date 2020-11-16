import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import { BrowserRouter, Route } from 'react-router-dom';

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
import Footer from './common/Footer';
import i18n from './common/i18n';

import { DonationDuration } from './common/utils/donation';

import 'bootstrap/dist/css/bootstrap.min.css'; 
import '../static/css/bootstrap-agency-theme.css';
import '../static/scss/basic.scss';

class Main extends Component {
    constructor(props) {
        super(props);
        this.state = { 
            selectedLocale: 'en-US', 
            selectedChildren: [], 
            // Initialize donation amount for inital render.
            sponsor: {
                firstName: "",
                lastName: "",
                email: "",
                address: { 
                    streetAddress: "", 
                    city: "",
                    zip: "",
                    state: "",
                    country: "" 
                },
                churchName: "",
                subscribed: false,
                phoneNumber: "",
            },
            donation: {donationAmount: 0, donationDuration: DonationDuration.ONCE },
            allowDonationDurationChange: true,
            payPalClientId: ""
        };

        // Bind handlers here

        this.handleSelectedLocaleChange = this.handleSelectedLocaleChange.bind(this);
        // Handlers for donation updates
        this.handleDonationClick = this.handleDonationClick.bind(this);
        this.donationDurationChangeHandler = this.donationDurationChangeHandler.bind(this);
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
        this.setState({ selectedLocale: selectedLocale });
        i18n.changeLanguage(selectedLocale);
    }

     /**
     * Handler for the Sponsor Form 'NEXT' button click. 
     * Takes in 'values' object which contains an object of fields to values on the form.
     */
    handleSponsorFormClick(values) {
        console.log("values from main.jsx:", values )
        let self = this;
        this.setState({ sponsor: values.sponsor });
        console.log("state from main.jsx:", this.state.sponsor )
    }

    /**
     * Handler for the sponsor info submission button.
     */
    handleSponsorshipSubmission() {
        let self = this;
        console.log(this.state);
        let selectedChildIds = [];
        this.state.selectedChildren.map((childContainer) => selectedChildIds.push(childContainer.child.childId));
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
            axios.get('/sponsor-info/selections/', {
                params: {
                    childIds: requestParams
                }
            })
            .then(function (response) {
                console.log(response);
                self.setState({ 
                    selectedChildren: response.data.children, 
                    donation: { donationAmount: response.data.donation.donationAmount, donationDuration: DonationDuration.MONTHLY},
                    allowDonationDurationChange: false
                });
                // Manually change route after successful response from backend.
                history.push("/sponsor-info/");
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
        this.setState({ 
            donation: { donationAmount: parseInt(amount), donationDuration: donationDuration },
            allowDonationDurationChange: true
        });
        // Manually change route after successful validation.
        history.push("/sponsor-info/");
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


    render() {
        return (
            // Add padding-top to accommodate room for the navigation bar.
            <div className="tucklets-main tucklets-nav-padding">
                <BrowserRouter>
                    <TuckletsNavBar handleSelectedLocaleChange={this.handleSelectedLocaleChange} i18n={i18n} />
                    <Route exact path="/sponsor-info/">
                        <SponsorInfoPage 
                            selectedChildren={this.state.selectedChildren} 
                            donation={this.state.donation}
                            i18n={i18n} 
                            handleSelectedLocaleChange={this.handleSelectedLocaleChange}
                            payPalClientId={this.state.payPalClientId}
                            allowDonationDurationChange={this.state.allowDonationDurationChange}
                            sponsor={this.state.sponsor}
                            sponsorFormClickHandler={this.handleSponsorFormClick}
                        />
                    </Route>
                    <Route exact path="/sponsor-info/confirm/">
                        <ConfirmationPage 
                            selectedChildren={this.state.selectedChildren} 
                            donation={this.state.donation}
                            i18n={i18n} 
                            handleSelectedLocaleChange={this.handleSelectedLocaleChange}
                            payPalClientId={this.state.payPalClientId}
                            willPayByCheck={true}
                            sponsor={this.state.sponsor}
                            submitButtonHandler={this.handleSponsorshipSubmission}
                        />
                    </Route>
                    <Route exact path="/thank-you/">
                        <SponsorThankYouPage i18n={i18n} handleSelectedLocaleChange={this.handleSelectedLocaleChange} 
                        />
                    </Route>
                    <Route exact path="/">
                        <HomePage i18n={i18n} handleSelectedLocaleChange={this.handleSelectedLocaleChange} />
                        <br />
                        <AboutPage i18n={i18n} handleSelectedLocaleChange={this.handleSelectedLocaleChange} numStudents={998} numTeachers={67}/>
                        <br />
                        <OurStoryPage i18n={i18n} handleSelectedLocaleChange={this.handleSelectedLocaleChange} />
                        <br />
                        {/* <NewslettersPage i18n={i18n} handleSelectedLocaleChange={this.handleSelectedLocaleChange} />  */}
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
        axios.get('/fetchConfigs').then((response) => {
            this.setState({ payPalClientId: response.data.paypal_client_id})
        });
    }
}

ReactDOM.render(
    <Main />,
    document.getElementById('tucklets-home')
);