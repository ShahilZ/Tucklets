import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import { withRouter } from 'react-router-dom';

import ChildImageContainer from '../common/sponsorship/ChildImageContainer';
import ConfirmationTable from '../common/sponsorship/ConfirmationTable';
import BrainTreePaymentContainer from '../common/sponsorship/BrainTreePaymentContainer';

import { PaymentMethod } from '../common/utils/enums.js';

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
    /** Handler that will submit the payment to the payment processor + backend. */
    submitButtonHandler: PropTypes.func.isRequired
}

class ConfirmationPage extends Component {
    constructor(props) {
        super(props);


        // Bind handlers here.
        this.renderSelectedChildren = this.renderSelectedChildren.bind(this);
        this.renderPayByCheckView = this.renderPayByCheckView.bind(this);
        this.renderBrainTreePaymentContainer = this.renderBrainTreePaymentContainer.bind(this);

    }

    componentDidMount(){ 
        window.scrollTo(0,0);
    }


    /**
     * Handler to render all selected children from the user.
     */
    renderSelectedChildren() {
        return (
            <div className="row container selected-children-div">
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
                <h4 className="confirmation-header">{this.props.i18n.t("confirm:check_header")}</h4>
                <div className="confirmation-check-div">
                    <p>
                        {this.props.i18n.t("confirm:check_directions")}
                    </p>
                    <div className="text-center">
                        <p>{this.props.i18n.t("confirm:check_address_line_1")}</p>
                        <p>{this.props.i18n.t("confirm:check_address_line_2")}</p>
                    </div>
                    <div className="confirmation-submission-button">
                    <div className="btn btn-primary" onClick={this.props.submitButtonHandler(null, this.props.history)}>{this.props.i18n.t("confirm:check_submit")}</div>
                </div>
                </div>
            </div>
        )
    }

    /**
     * Renders the PayPal buttons for payment submission.
     */
    renderBrainTreePaymentContainer() {
        return (
            <BrainTreePaymentContainer i18n={this.props.i18n} amount={this.props.donation.donationAmount} onSubmitHandler={this.props.submitButtonHandler}/>
        )
    }

    render() {
        // TODO: user refreshes the page or somehow gets here without going through the flow, redirect to the main page.
        let paymentOptions = this.props.donation.paymentMethod === PaymentMethod.CHECK ? this.renderPayByCheckView() :  this.renderBrainTreePaymentContainer();
        return (
            <div id="confirmation" className="bg-light">
                <div className="jumbotron jumbotron-fluid">
                    <div className="container">
                        <h1 className="display-4">{`${this.props.i18n.t("confirm:title")}`}</h1>
                        <p className="lead">{`${this.props.i18n.t("confirm:subtitle")}`}</p>
                    </div>
                </div>
                <div className="container">
                    <ConfirmationTable i18n={this.props.i18n} sponsor={this.props.sponsor} donation={this.props.donation} />
                    <br />
                    {paymentOptions}
                    {/* {this.renderSelectedChildren()} */}
                </div>
            </div>
        )
    }
    


}

ConfirmationPage.props = props;

export default withRouter(ConfirmationPage);