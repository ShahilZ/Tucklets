import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import { Link, withRouter } from 'react-router-dom';
import { PayPalButton } from 'react-paypal-button-v2';

import ChildImageContainer from '../common/sponsorship/ChildImageContainer';
import ConfirmationTable from '../common/sponsorship/ConfirmationTable';


import '../../static/scss/basic.scss';
import '../../static/scss/confirmation.scss';
import '../../static/scss/sponsor-info.scss';

const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
    /** Handler for updating the selected locale. */
    handleSelectedLocaleChange: PropTypes.func.isRequired,
    /** Sponsor object that should have values filled from the sponsor-info form. */
    sponsor: PropTypes.object.isRequired,
    /** The donation object containing the amount and duration */
    donation: PropTypes.object.isRequired,
    /** Array of selected children to display */
    selectedChildren: PropTypes.array,
    /** The PayPal Client ID necessary to complete payment. */
    payPalClientId: PropTypes.string.isRequired,
    /** Boolean that indicates whether the user wants to pay by check */
    willPayByCheck: PropTypes.bool.isRequired
}

class ConfirmationPage extends Component {
    constructor(props) {
        super(props);


        // Bind handlers here.
        this.renderSelectedChildren = this.renderSelectedChildren.bind(this);
        this.renderPayByCheckView = this.renderPayByCheckView.bind(this);
        this.renderPayPalButtons = this.renderPayPalButtons.bind(this);

    }


    /**
     * Handler to render all selected children from the user.
     */
    renderSelectedChildren() {
        return (
            <div className="container selected-children-div">
                {this.props.selectedChildren.map((childContainer) => (
                    <div className="child-div col-12 col-sm-6 col-md-4" key={`child-id${childContainer.child.childId}`}>
                        <ChildImageContainer i18n={this.props.i18n} childContainer={childContainer} />
                    </div>
                ))}
                <br />
            </div>
        )
    }

    /**
     * Renders the view for paying by check.
     */
    renderPayByCheckView() {
        return (
            <div>
                Check
            </div>
        )
    }

    /**
     * Renders the PayPal buttons for payment submission.
     */
    renderPayPalButtons() {
        return (
            <PayPalButton
            amount={this.props.donationAmount}
            shippingPreference="NO_SHIPPING" // default is "GET_FROM_FILE"
            onSuccess={() => console.log("Success")}
            options={{
                clientId: this.props.payPalClientId
              }}
        />
        )
    }

    render() {
        // TODO: user refreshes the page or somehow gets here without going through the flow, redirect to the main page.
        let paymentOptions = this.props.willPayByCheck ? this.renderPayByCheckView() :  this.renderPayPalButtons();
        return (
            <div id="confirmation" className="bg-light">
                <div className="jumbotron jumbotron-fluid">
                    <div className="container">
                        <h1 className="display-4">{`${this.props.i18n.t("confirm:title")}`}</h1>
                        <p className="lead">{`${this.props.i18n.t("confirm:subtitle")}`}</p>
                    </div>
                </div>
                {this.renderSelectedChildren()}
                <ConfirmationTable i18n={this.props.i18n} sponsor={this.props.sponsor} donation={this.props.donation} />
                {paymentOptions}
            </div>
        )
    }
    


}

ConfirmationPage.props = props;

export default ConfirmationPage;