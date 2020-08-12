import React from 'react'
import { PropTypes } from 'prop-types';


const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
}

const Footer = ({ i18n }) => {
    return (
        <div className="footer">
            <footer id="footer" className="container">
                Copyright @ Tucklets 2020
           
           </footer>

        </div>
       

    )
}

export default Footer;