import React from 'react'
import { PropTypes } from 'prop-types';

import NavBar from '../common/NavBar';


const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
    /** Handler for updating the selected locale. */
    handleSelectedLocaleChange: PropTypes.func.isRequired
}

const HomePage = ({ i18n, handleSelectedLocaleChange }) => {
    return (
        <div id="home">
            <NavBar handleSelectedLocaleChange={handleSelectedLocaleChange} i18n={i18n} />
            <header className="masthead">
                <div className="container">
                    <div className="masthead-subheading">Welcome to Tucklets.org!</div>
                    <div className="masthead-heading text-uppercase">Tucklets: Supporting the orphaned children of Kenya</div>
                    <a className="btn btn-primary btn-xl text-uppercase js-scroll-trigger" href="#services">Tell Me More</a>
                </div>
            </header> 
        </div>

    )
}

export default HomePage;