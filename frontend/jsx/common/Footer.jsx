import React from 'react'
import { PropTypes } from 'prop-types';


const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
}

const Footer = ({ i18n }) => {
    return (
        <footer id="footer">
           footer
        </footer>

    )
}

export default Footer;