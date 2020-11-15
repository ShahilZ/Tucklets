import React from 'react';

import '../../../static/scss/sponsor-a-child.scss';

/**
 * Component used to generate a container containing the child's image + information. 
 * This is a basic "logical" component that should be styled to the caller's liking by wrapping ir in another appropriately styled (responsive) element.
 * Note: The `childImageContainer` provided as props should be the response from the backend. It contains a 'child' object plus relevant information.
 */
const ChildImageContainer = ({ i18n, childContainer }) => {
    return (
        <div className="sponsor-child-image-container">
            <img className="card-img-top child-image-box" src={childContainer.childImageLocation} alt="Card image cap" />
            <div className="card-body">
                <table>
                    <thead />
                    <tbody>
                        <tr>
                            <td>
                                <h5 className="card-title child-name">{`${childContainer.child.firstName} ${childContainer.child.lastName}`}</h5>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span className="card-text"><b>{i18n.t("sponsorship:age")}</b></span>
                            </td>
                            <td>
                                <span className="card-text">{`${childContainer.age} ${i18n.t("sponsorship:age_suffix")}`}</span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span className="card-text"><b>{i18n.t("sponsorship:grade")}</b></span>
                            </td>
                            <td>
                                <span className="card-text">{`${childContainer.child.grade} ${i18n.t("sponsorship:grade_suffix")}`}</span>
                            </td>
                        </tr>
                        <tr>
                            <td colSpan="2">
                                <span className="card-text">{childContainer.child.information}</span>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default ChildImageContainer;