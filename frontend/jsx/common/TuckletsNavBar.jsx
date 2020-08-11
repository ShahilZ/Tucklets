import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import { NavHashLink as Link } from 'react-router-hash-link';
import Nav from 'react-bootstrap/Nav'
import Navbar from 'react-bootstrap/Navbar'

import LocaleChanger from './LocaleChanger';
import i18n from './i18n';


import '../../static/scss/locales.scss';
import '../../static/scss/info.scss';
import '../../static/scss/basic.scss';


const props = {
    /** Handler for updating the selected locale. */
    handleSelectedLocaleChange: PropTypes.func.isRequired,
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired
}

class TuckletsNavBar extends Component {
    constructor(props) {
        super(props);
        this.state = {
            selectedTab: 0,
            isScrollTop: true }
        // Bind handlers
        this.handleScroll = this.handleScroll.bind(this);
    }

    componentDidMount() {
        window.addEventListener('scroll', this.handleScroll);
    }
    
    componentWillUnmount() {
        window.removeEventListener('scroll', this.handleScroll);
    }

    handleScroll() {
        console.log(window.scrollY);
        const isScrollTop = window.scrollY < 100;
        console.log(isScrollTop);
     
        if (isScrollTop !== this.state.isScrollTop) {
            console.log(this.state.isScrollTop)
            this.setState({ isScrollTop })
        }
    }

    render() {
        return (
            <div>
                <Navbar className={`tucklets-nav ${this.state.isScrollTop ? "" : "navbar-scroll"}`} expand="lg" fixed="top">
                    <Navbar.Brand href="#home">
                        Tucklets
                        {/* <a className="navbar-brand js-scroll-trigger" href="#page-top"><img src="../static/img/tucklets_logo.png" alt="" /></a><button className="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">Menu<i className="fas fa-bars ml-1"></i></button> */}
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="mr-auto">
                            <Link className="nav-link js-scroll-trigger" smooth to="#home">{this.props.i18n.t("navigation:home")}</Link>
                            <Link className="nav-link js-scroll-trigger" smooth to="#our-story">{this.props.i18n.t("navigation:our_story")}</Link>
                            <Link className="nav-link js-scroll-trigger" smooth to="#about">{this.props.i18n.t("navigation:about")}</Link>
                            <a className="nav-link js-scroll-trigger" href="#team">{this.props.i18n.t("navigation:donate")}</a>
                            <Link className="nav-link js-scroll-trigger" smooth to="#newsletters">{this.props.i18n.t("navigation:newsletters")}</Link>
                            <LocaleChanger handleSelectedLocaleChange={this.props.handleSelectedLocaleChange} i18n={this.props.i18n} additionalClasses="nav-link js-scroll-trigger" />
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
            </div>
        )
    }
}

TuckletsNavBar.propTypes = props;

export default TuckletsNavBar;
               
               
               