import React from 'react';
import { PropTypes } from 'prop-types';

import NavBar from '../common/NavBar';

import '../../static/scss/info.scss';

const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
    /** Handler for updating the selected locale. */
    handleSelectedLocaleChange: PropTypes.func.isRequired

}

const OurStoryPage = ({ handleSelectedLocaleChange, i18n }) => {
    return (
        <div id="our-story" className="our-story-section bg-light">
            <NavBar handleSelectedLocaleChange={handleSelectedLocaleChange} i18n={i18n} />
            <div className="row"> 
                <div className="col-lg-6 col-md-6 col-sm-12 col-12">
                    <div className="container page-section">
                        <h2 className="text-center">{`${i18n.t("our_story:title")}`}</h2>
                        <br />
                        <h3 className="section-subheading text-muted m-0">
                            <div className="row justify-content-center">
                                <div className="col-lg-8 col-md-12 col-sm-14 col-12">
                                    <div className="our-story-acrostic">
                                        <p><b>T</b> - {`${i18n.t("our_story:t_acrostic_first")}`}</p>
                                        <p><b>U</b> - {`${i18n.t("our_story:u_acrostic")}`}</p>
                                        <p><b>C</b> - {`${i18n.t("our_story:c_acrostic")}`}</p>
                                        <p><b>K</b> - {`${i18n.t("our_story:k_acrostic")}`}</p>
                                        <p><b>L</b> - {`${i18n.t("our_story:l_acrostic")}`}</p>
                                        <p><b>E</b> - {`${i18n.t("our_story:e_acrostic")}`}</p>
                                        <p><b>T</b> - {`${i18n.t("our_story:t_acrostic_second")}`}</p>
                                        <p><b>S</b> - {`${i18n.t("our_story:s_acrostic")}`}</p>
                                    </div>

                                </div>                
                            </div>
                        </h3>
                        <div className="text-center row justify-content-center">
                                <div className="col-lg-8 col-md-12 col-sm-14 col-12">
                                    <p>{`${i18n.t("our_story:message")}`}</p>
                                    <p className="signature">{`${i18n.t("our_story:signature")}`}</p>
                                </div>
                        </div>        
                    </div>
                        
                </div>
                <div className="col-lg-6 col-md-6 col-sm-12 col-12">
                    <div className="our-story-image" />
                </div>

            </div>
        </div>

    )
}

export default OurStoryPage;