import React from 'react';
import { PropTypes } from 'prop-types';
import DropIn from 'braintree-web-drop-in-react';
import axios from 'axios';

const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
    /** Amount for payment. */
    amount: PropTypes.number.isRequired
}

class BrainTreePaymentContainer extends React.Component {

    constructor(props) {
        super(props);
        this.state = { clientToken: "" };
        this.instance;


        this.submitPayment = this.submitPayment.bind(this);
        this.initializeInstance = this.initializeInstance.bind(this);

    }

    componentDidMount() {
        // Get a client token for authorization from your server
        axios.get('/info/fetchConfigs').then((response) => {
            const clientToken = response.data.braintree_client_id;
            this.setState({ clientToken });
        });

  }

    async submitPayment() {
        // Requence paymentNonce value. Await is necessary here since we want to block until we get the response back.
        const { nonce } = await this.instance.requestPaymentMethod();
        // Post nonce to server.
        axios.post('/sponsor-info/payment', {
            paymentNonce: nonce
        });

    }

    /**
     * Initializes the DropIn instance from BrainTree. This allows the global instance variable
     * to be set by the instance object provided by the BrainTree DropIn after initialization.
     */
    initializeInstance() {
        let self = this;
        return (instance) => {
            self.instance = instance;
        }
    }

    render() {
        if (!this.state.clientToken) {
            return (
                <div>
                <h1>Loading...</h1>
                </div>
            );
        } 
        else {
        return (
            <div className="container">
                <DropIn
                    options={{ 
                        authorization: this.state.clientToken,
                        paypal: {
                            flow: 'checkout',
                            amount: this.props.amount,
                            currency: 'USD'
                        },  
                        venmo: {}

                    }}
                    onInstance={this.initializeInstance()}
                />
                <div className="container text-center">
                <button className="btn btn-primary" onClick={this.submitPayment}>{this.props.i18n.t("donate:donate-submit-text")}</button>
                </div>
            </div>
        );
        }
    }
}

BrainTreePaymentContainer.props = props;

export default BrainTreePaymentContainer;