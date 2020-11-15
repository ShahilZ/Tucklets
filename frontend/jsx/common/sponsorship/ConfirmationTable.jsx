import React from 'react';

import '../../../static/scss/confirmation';

/**
 * Component used to generate the confirmation div/table.
 */
const ConfirmationTable = ({ i18n, sponsor, donation }) => {
    return (
        <div className="confirmation-body container">
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
                                <span>{sponsor.address}</span>
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
                            <span>{i18n.t(`confirm:${donation.donationDuration}`)}</span>
                            </td>
                            <td>
                                <span>{donation.donationAmount}</span>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default ConfirmationTable;