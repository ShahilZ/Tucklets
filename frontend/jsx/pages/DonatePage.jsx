import React, { Component } from 'react';
import axios from 'axios';
import { PropTypes } from 'prop-types';
import { Link } from 'react-router-dom'
import { withRouter } from 'react-router-dom';

import { DonationButtonGroup } from '../common/DonationButtonGroup';
import { DonationDuration } from '../common/utils/enum';

import '../../static/scss/info.scss';

const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
    /** Handler for updating the selected locale. */
    handleSelectedLocaleChange: PropTypes.func.isRequired,
    /** Handler for updating donation amount. */
    handleDonationClick: PropTypes.func.isRequired
}

class DonatePage extends Component {

    constructor(props) {
        super(props);
        this.state = { donationAmount: "", amountHasErrors: false }

        this.handleDonationAmountChange = this.handleDonationAmountChange.bind(this);
        this.submitDonationHandler = this.submitDonationHandler.bind(this);
    }

    /** Handler for donation amount changes. When the amount becomes valid, remove any error styling there may be. */
    handleDonationAmountChange(event) {
        if (!!event.target.value) {
            this.setState({ donationAmount: event.target.value, amountHasErrors: false})
        }
    }

    /** Handler for submitting a donation. */
    submitDonationHandler(donationAmount, donationDuration, history) {
        let self = this;
        return () => {
            // TODO: Validate against negatives.
            if ( !donationAmount || donationAmount === "" ) {
                self.setState({ amountHasErrors: true }) 
            }
            else {
                self.props.handleDonationClick(donationAmount, donationDuration, history);
            }
        }
    }

    render() {
        let donationAmountClassName = `form-control ${this.state.amountHasErrors ? 'is-invalid' : ''}`;
        return (
            <div id="donate" className="donate-section">
                <div className="donate-form-section">
                    <div className="text-center">
                        <h2>{`${this.props.i18n.t("donate:title")}`}</h2>
                        <p>{`${this.props.i18n.t("donate:subtext")}`}</p>
                    </div>
                    <div>
                        <div className="input-group mb-3">
                            <div className="input-group-prepend">
                                <span className="input-group-text">$</span>
                            </div>
                            <input type="number" id="donation-amount" className={donationAmountClassName} value={this.state.donationAmount} onChange={this.handleDonationAmountChange}/>
                        </div>
                        <div className="btn-group duration-button-group">
                            <button to="/sponsor-info/" className="col-md-6 btn btn-info donation-button" onClick={this.submitDonationHandler(this.state.donationAmount, DonationDuration.ONCE, this.props.history)}>{this.props.i18n.t("donate:donate-once")} </button>
                            <span className="donate-button-spacing" />
                            <button to="/sponsor-info/" className="col-md-6 btn btn-info donation-button" onClick={this.submitDonationHandler(this.state.donationAmount, DonationDuration.MONTHLY, this.props.history)}>{this.props.i18n.t("donate:donate-monthly")}</button>
                        </div>
                        <br></br>
                        <br></br>
                    </div>
                </div>
            </div>
        )
    }
}


DonatePage.propTypes = props;

export default withRouter(DonatePage);