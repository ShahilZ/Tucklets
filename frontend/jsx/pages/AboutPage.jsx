import React from 'react'
import { PropTypes } from 'prop-types';

import NavBar from '../shared/NavBar';

const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
    /** Handler for updating the selected locale. */
    handleSelectedLocaleChange: PropTypes.func.isRequired,
}

const AboutPage = ({ handleSelectedLocaleChange, i18n }) => {
    return (
        <div id="about">
            <NavBar handleSelectedLocaleChange={handleSelectedLocaleChange} i18n={i18n} />
            <h2>{`${i18n.t("about:title")}`}</h2>
            <div>
                <h3>{`${i18n.t("about:subtext")}`}</h3>    
                <span>{`${i18n.t("about:description")}`}</span>    
            </div>
        </div>

    )
}

export default AboutPage;