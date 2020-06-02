import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import { NavHashLink as Link } from 'react-router-hash-link';
import Dropdown from 'react-bootstrap/Dropdown'

import LocaleChanger from './LocaleChanger';
import i18n from './i18n';


import '../../static/scss/locales.scss';


const props = {
    /** Handler for updating the selected locale. */
    handleSelectedLocaleChange: PropTypes.func.isRequired,
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired
}

class NavBar extends Component {
    constructor(props) {
        super(props);
        this.state = {selectedTab: 0 }
        // Bind handlers

    } 

    render() {
        return (
            <div>
                <nav className="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
                    <div className="container">
                        <a className="navbar-brand js-scroll-trigger" href="#page-top"><img src="../static/img/navbar-logo.svg" alt="" /></a><button className="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">Menu<i className="fas fa-bars ml-1"></i></button>
                        <div className="collapse navbar-collapse" id="navbarResponsive">
                            <ul className="navbar-nav text-uppercase ml-auto">

                                <li className="nav-item"><Link className="nav-link js-scroll-trigger" smooth to="#home">Home</Link></li>
                                <li className="nav-item"><Link className="nav-link js-scroll-trigger" smooth to="#our-story">Our Story</Link></li>
                                <li className="nav-item"><Link className="nav-link js-scroll-trigger" smooth to="#about">About</Link></li>
                                <li className="nav-item"><a className="nav-link js-scroll-trigger" href="#team">Donate</a></li>
                                <li className="nav-item"><a className="nav-link js-scroll-trigger" href="#team">Sponsorships</a></li>
                                <li className="nav-item"><a className="nav-link js-scroll-trigger" href="#contact">Contact</a></li>
                                <li className="nav-item"><LocaleChanger handleSelectedLocaleChange={this.props.handleSelectedLocaleChange} i18n={this.props.i18n} additionalClasses="nav-link js-scroll-trigger" /></li>
                            </ul>
                        </div>
                    </div>
                </nav>

            </div>
        )
    }
}

NavBar.propTypes = props;

export default NavBar;
               
               
               