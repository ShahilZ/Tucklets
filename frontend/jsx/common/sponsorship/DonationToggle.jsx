import React from 'react';

import '../../../static/scss/basic.scss';
import '../../../static/scss/donation.scss';
import '../../../static/scss/sponsor-info.scss';

/**
 * Simple component that given an array of options, creates button toggles for those options. This component is intended to use for donation-related UI (uses donation i18n files).
 * Note: This component assumes that the given options will each be an object containing the 'i18nKey' field.
 */

const DonationToggle = ({ i18n, donation, donationField, options, onClickHandler }) => {
    return (
        <div className="btn-group">
            {options.map((option, index) => (
                <button 
                    key={`${option.value}-${index}`} 
                    type="button" 
                    className={`btn btn-info donation-button ${donation[donationField].value === option.value ? "default" : ""} `} 
                    onClick={onClickHandler(option)}
                >
                    { i18n.t(`donate:donate-${option.i18nKey}-text`)}
                </button>
            ))}
        </div>
    )
}

export default DonationToggle;