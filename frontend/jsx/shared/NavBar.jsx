import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import LocaleChanger from './LocaleChanger';
import i18n from './i18n';
import Dropdown from 'react-bootstrap/Dropdown'


import '../../static/styles/locales.scss';


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
              {/* <h1>Demo Component</h1>
              
              <span>The selected locale is: {`${this.state.selectedLocale}`}</span>
              {i18n.t('locales:apptext')} */}

                <nav className="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
                    <div className="container">
                        <a className="navbar-brand js-scroll-trigger" href="#page-top"><img src="../static/img/navbar-logo.svg" alt="" /></a><button className="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">Menu<i className="fas fa-bars ml-1"></i></button>
                        <LocaleChanger handleSelectedLocaleChange={this.props.handleSelectedLocaleChange} i18n={this.props.i18n} />
                        <Dropdown />
                        <Dropdown>
  <Dropdown.Toggle variant="success" id="dropdown-basic">
    Dropdown Button
  </Dropdown.Toggle>

  <Dropdown.Menu>
    <Dropdown.Item href="#/action-1">Action</Dropdown.Item>
    <Dropdown.Item href="#/action-2">Another action</Dropdown.Item>
    <Dropdown.Item href="#/action-3">Something else</Dropdown.Item>
  </Dropdown.Menu>
</Dropdown>
                        <span>The selected locale is: {`${this.state.selectedLocale}`}</span>
                        {i18n.t('locales:apptext')}
                        <div className="collapse navbar-collapse" id="navbarResponsive">
                            <ul className="navbar-nav text-uppercase ml-auto">

                                <li className="nav-item"><a className="nav-link js-scroll-trigger" href="#services">Home</a></li>
                                <li className="nav-item"><a className="nav-link js-scroll-trigger" href="#portfolio">Our Story</a></li>
                                <li className="nav-item"><a className="nav-link js-scroll-trigger" href="#about">About</a></li>
                                <li className="nav-item"><a className="nav-link js-scroll-trigger" href="#team">Donate</a></li>
                                <li className="nav-item"><a className="nav-link js-scroll-trigger" href="#team">Sponsorships</a></li>
                                <li className="nav-item"><a className="nav-link js-scroll-trigger" href="#contact">Contact</a></li>
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
               
               
               