import React from 'react'
import { PropTypes } from 'prop-types';

import { DonationDuration } from './utils/enum';

import '../../static/scss/donation.scss';


const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
    /** Handler for the donation duration changes. Passes in the donation duration */
    donationDurationChangeHandler: PropTypes.func,
    /** Currently selected donation duration. */
    currentDonationDuration: PropTypes.string,
    /** Whether a label needs to be generated or not. */
    shouldDisplayLabel: PropTypes.bool.isRequired,
    /** Whether the donation duration should be able to be changed. If so, only display the monthly duration option. */
    //shouldAllowDonationDurationChanges: PropTypes.bool.isRequired
}
 
const DonationButtonGroup = ({ i18n, donationDurationChangeHandler, currentDonationDuration, shouldDisplayLabel }) => {
    let shouldAllowDonationDurationChanges = true;
    // Check if the currentDonationDuration is for one time. If it is, style accordingly.
    let isOneTimeDurationSelected = currentDonationDuration == DonationDuration.ONCE;
    let oneTimeDurationStyling = `col-md-6 btn btn-info donation-duration-button ${isOneTimeDurationSelected ? 'donation-duration-selected' : ''} `;
    // Monthly is default if we do not allow duration changes.
    let monthlyDurationStyling = `col-md-6 btn btn-info donation-duration-button ${!isOneTimeDurationSelected ? `donation-duration-selected ${shouldAllowDonationDurationChanges ? '' : 'disabled'} ` : '' } `;
    return (
        <div>
            {shouldDisplayLabel && <label htmlFor="donation-duration">{`${i18n.t("donate:duration")}`}</label>}
            <div id="donation-duration" className="btn-group duration-button-group">
                {shouldAllowDonationDurationChanges && <div className={oneTimeDurationStyling} onClick={donationDurationChangeHandler(DonationDuration.ONCE)}>{i18n.t("donate:donate-once")} </div>}
                <div className={monthlyDurationStyling} onClick={donationDurationChangeHandler(DonationDuration.MONTHLY)}>{i18n.t("donate:donate-monthly")}</div>
            </div>
        </div>

    )
}

DonationButtonGroup.propTypes = props;

export default DonationButtonGroup;