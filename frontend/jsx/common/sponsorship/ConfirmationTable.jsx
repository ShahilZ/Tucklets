import React from 'react';

import '../../../static/scss/confirmation';

/**
 * Component used to generate the confirmation div/table.
 */
const ConfirmationTable = ({ i18n, sponsor, donation }) => {
    // Create our number formatter.
    console.log(i18n.language);
    let currencyFormatter = new Intl.NumberFormat(i18n.language, {
        style: 'currency',
        currency: 'USD',
    });
    console.log(sponsor.address)
    return (
        <div className="confirmation-summary">
            <h4 className="confirmation-header">{i18n.t("confirm:personal")}</h4>
            <div className="confirmation-div">
                <table className="confirmation-table">
                    <thead />
                    <tbody>
                        <tr className="confirmation-spacer"></tr>
                        <tr>
                            <td />
                            <td>
                                <span className="text-muted">{i18n.t("confirm:name")}</span>
                            </td>
                            <td>
                                <span className="text-muted">{i18n.t("confirm:email")}</span>
                            </td>
                            <td>
                                <span className="text-muted">{i18n.t("confirm:address")}</span>
                            </td>
                        </tr>
                        <tr>
                            <td />
                            <td>
                                <span>{sponsor.firstName} {sponsor.lastName}</span>
                            </td>
                            <td>
                                <span>{sponsor.email}</span>
                            </td>
                            <td>
                                { sponsor.address.streetAddress1 === "" && <span>{i18n.t("confirm:not_provided")}</span>}

                                { sponsor.address.streetAddress1 !== "" && 
                                    <div><span>{sponsor.address.streetAddress} </span><br />
                                    <span>{sponsor.address.city}, {sponsor.address.state} {sponsor.address.zip}</span> <br />
                                    <span>{sponsor.address.country}</span></div> }
                            </td>
                        </tr>
                        <tr className="confirmation-spacer"></tr>
                        <tr className="confirmation-spacer"></tr>
                        <tr>
                            <td />
                            <td />
                            <td>
                                <span className="text-muted">{i18n.t("confirm:form_duration")}</span>
                            </td>
                            <td>
                                <span className="text-muted">{i18n.t("confirm:form_amount")}</span>
                            </td>
                        </tr>
                        <tr>
                            <td />
                            <td>
                                <span>{i18n.t("confirm:form_sponsorship")}</span>
                            </td>
                            <td>
                            <span>{i18n.t(`confirm:${donation.donationDuration.value.toLowerCase()}`)}</span>
                            </td>
                            <td>
                                <span>{currencyFormatter.format(donation.donationAmount)}</span>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default ConfirmationTable;