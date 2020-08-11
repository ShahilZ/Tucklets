import React, { Component } from 'react';
import axios from 'axios';
import { PropTypes } from 'prop-types';
import { Redirect } from 'react-router-dom'

import '../../static/scss/info.scss';

const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
    /** Handler for updating the selected locale. */
    handleSelectedLocaleChange: PropTypes.func.isRequired,
    /** Handler for updating donation amount. */
    handleDonationClick: PropTypes.func.isRequired,
    /** Whether this page needs to redirect to another page or not. */
    shouldRedirect: PropTypes.bool.isRequired
}

class DonatePage extends Component {

    constructor(props) {
        super(props);
        this.state = { donationAmount: "" }

        this.handleDonationAmountChange = this.handleDonationAmountChange.bind(this);
    }

    handleDonationAmountChange(event) {
        this.setState({ donationAmount: event.target.value})
    }


    render() {
        if (this.props.shouldRedirect) {
            return <Redirect to="/sponsor-info/"/>
        }
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
                            <input type="number" id="donation-amount" className="form-control" value={this.state.donationAmount} onChange={this.handleDonationAmountChange}/>
                        </div>
                        <div className="row btn-group duration-button-group">
                            <button type="button" className="col-md-6 btn btn-primary" onClick={this.props.handleDonationClick(this.state.donationAmount)}>{this.props.i18n.t("donate:donate-once")}</button>
                            <button type="button" className="col-md-6 btn btn-secondary" onClick={this.props.handleDonationClick(this.state.donationAmount)}>{this.props.i18n.t("donate:donate-monthly")}</button>
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

export default DonatePage;