import React from 'react'
import { PropTypes } from 'prop-types';
import { NavHashLink as Link } from 'react-router-hash-link';

import NavBar from '../common/TuckletsNavBar';


const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
    /** Handler for updating the selected locale. */
    handleSelectedLocaleChange: PropTypes.func.isRequired
}

const HomePage = ({ i18n, handleSelectedLocaleChange }) => {
    return (
        <div id="home">
            <header className="masthead">
                <div className="container">
    <div className="masthead-subheading"><span>{i18n.t("home:title")}</span></div>
                    <div className="masthead-heading text-uppercase"><span>{i18n.t("home:subtext")}</span></div>
                        <Link className="btn btn-primary btn-xl text-uppercase js-scroll-trigger" smooth to="#about">
                        <span>{i18n.t("home:learn_more")}</span>
                        </Link>
                </div>
            </header> 
        </div>

    )
}

export default HomePage;