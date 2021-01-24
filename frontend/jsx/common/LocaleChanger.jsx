import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import axios  from 'axios';

import '../../static/scss/locales.scss';


const props = {
    /** Currently selected locale */
    selectedLocale: PropTypes.string.isRequired,
    /** Handler for updating the selected locale. */
    handleSelectedLocaleChange: PropTypes.func.isRequired,
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
    /** Any addditional css classes to style this component/ */
    additionalClasses: PropTypes.string
}

const LocaleChanger = ({ selectedLocale, handleSelectedLocaleChange, i18n, additionalClasses }) => {

    return (
        <div className={`locale-changer ${additionalClasses}`}>
            <select id="locale-changer" value={selectedLocale} onChange={handleSelectedLocaleChange}>
                <option value="en-US"> {`${i18n.t("locales:en_us")}`} </option>
                <option value="zh-TW"> {`${i18n.t("locales:zh_tw")}`} </option>
            </select>
        </div>
    )
}




export default LocaleChanger;

