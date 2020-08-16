import React from 'react';
import { PropTypes } from 'prop-types';

import '../../static/scss/info.scss';
import '../../static/scss/thank-you.scss';


const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
    /** Handler for updating the selected locale. */
    handleSelectedLocaleChange: PropTypes.func.isRequired
}

const SponsorThankYouPage = ({ handleSelectedLocaleChange, i18n }) => {
    return (
        <div>
            <div className="jumbotron jumbotron-fluid">
            <div className="container">
                <h1 className="display-4">{`${i18n.t("sponsor_thank_you:title")}`}</h1>
            </div>
            </div>
            <div className="container thank-you-container">
                <img className="checkmark-image" alt="checkmarx" src="/images/checkmark.png" />
                <div className="thank-you-text">
                    <p>{`${i18n.t("sponsor_thank_you:subtitle")}`}</p>
                    <p>{`${i18n.t("sponsor_thank_you:processed_text")}`}</p>
                    <p>{`${i18n.t("sponsor_thank_you:impact_text")}`} </p>
                    <br />
                    <br />
                </div>
            </div>
            <div className="group-photo-div">
                <img className="" alt="group-of-students" src="/images/group-student-photo.png" />
            </div>
        </div>
    )
}

export default SponsorThankYouPage;