import React, { Component } from 'react';
import axios from 'axios';
import { PropTypes } from 'prop-types';

import '../../static/scss/sponsor-a-child.scss';
import '../../static/scss/sponsor-info.scss';

const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
    /** Handler for updating the selected locale. */
    handleSelectedLocaleChange: PropTypes.func.isRequired,
    /** Array of selected children to display */
    selectedChildren: PropTypes.array.isRequired,
    /** Sponsor container object used to store sponsor/donor information. */
    sponsor: PropTypes.object.isRequired
}

class SponsorInfoPage extends Component {

    constructor(props) {
        super(props);
        this.state = { };

        this.renderSelectedChildren = this.renderSelectedChildren.bind(this);
    }

    componentDidMount(){ 
        window.scrollTo(0,0);
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
                            <p className="card-text"><b>{`${this.props.i18n.t("sponsor_a_child:age")}`}</b><span>{`${childContainer.age}`}</span></p>
                            <p className="card-text"><b>{`${this.props.i18n.t("sponsor_a_child:grade")}`}</b><span>{`${childContainer.child.grade}`}</span></p>
                            <p className="card-text"><b>{`${this.props.i18n.t("sponsor_a_child:info")}`}</b><span>{`${childContainer.child.information}`}</span></p>
                        </div>
                        <div className="w-100"></div>
                    </div>
                ))}
            </div>


        )
    }
    

    render() {
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
                            <input type="text" id="sponsor-first-name" placeholder={this.props.i18n.t("sponsor_info:form_placeholder_first_name")} />
                            <label htmlFor="sponsor-last-name">{`${this.props.i18n.t("sponsor_info:form_last_name")}`}</label>
                            <input type="text" id="sponsor-last-name" placeholder={this.props.i18n.t("sponsor_info:form_placeholder_last_name")} />
                            <label htmlFor="sponsor-email">{`${this.props.i18n.t("sponsor_info:form_email")}`}</label>
                            <input type="text" id="sponsor-email" placeholder={this.props.i18n.t("sponsor_info:form_placeholder_email")} />
                            <label htmlFor="sponsor-address">{`${this.props.i18n.t("sponsor_info:form_address")}`}</label>
                            <input type="text" id="sponsor-address" placeholder={this.props.i18n.t("sponsor_info:form_placeholder_address")} />
                            <label htmlFor="sponsor-church-name">{`${this.props.i18n.t("sponsor_info:form_church_name")}`}</label>
                            <input type="text" id="sponsor-church-name" placeholder={this.props.i18n.t("sponsor_info:form_placeholder_church_name")} />
                        </fieldset>
                        <fieldset>
                            <legend><span className="sponsor-info-section-number">2</span><span>{`${this.props.i18n.t("sponsor_info:form_header_donation_info")}`}</span></legend>
                            <label htmlFor="donation-amount">{`${this.props.i18n.t("sponsor_info:form_amount")}`}</label>
                            <input type="text" id="donation-amount" readOnly value={this.props.sponsor.donationAmount} />
                        </fieldset>
                    </form>
                </div>
            </div>
        )
    }
    

}
SponsorInfoPage.propTypes = props;

export default SponsorInfoPage;