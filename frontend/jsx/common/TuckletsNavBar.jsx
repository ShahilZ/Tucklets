import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import { NavHashLink as Link } from 'react-router-hash-link';
import Nav from 'react-bootstrap/Nav'
import Navbar from 'react-bootstrap/Navbar'
import Container from 'react-bootstrap/Container'

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
        const isScrollTop = window.scrollY < 100;
        if (isScrollTop !== this.state.isScrollTop) {
            this.setState({ isScrollTop })
        }
    }

    render() {
        return (
            <div>
                <Navbar className="tucklets-nav navbar-scroll" expand="lg" fixed="top">
                    <Container>
                    <Navbar.Brand href="/#home">
                        <img
                            src="../../static/img/tucklets-logo-purple.png"
                            className={`${this.state.isScrollTop ? "tucklets-logo-regular" : "tucklets-logo-condensed"}`}
                            alt="Tucklets Logo"
                        />
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="ml-auto nav-links">
                            <Link className="nav-link js-scroll-trigger" smooth to="/#home">{this.props.i18n.t("navigation:home")}</Link>
                            <Link className="nav-link js-scroll-trigger" smooth to="/#our-story">{this.props.i18n.t("navigation:our_story")}</Link>
                            <Link className="nav-link js-scroll-trigger" smooth to="/#about">{this.props.i18n.t("navigation:about")}</Link>
                            <Link className="nav-link js-scroll-trigger" smooth to="/#newsletters">{this.props.i18n.t("navigation:newsletters")}</Link>
                            <Link className="nav-link js-scroll-trigger" smooth to="/#donate">{this.props.i18n.t("navigation:donate")}</Link>
                            <Link className="nav-link js-scroll-trigger" smooth to="/#sponsor-a-child">{this.props.i18n.t("navigation:sponsor_navbar")}</Link>
                            <LocaleChanger handleSelectedLocaleChange={this.props.handleSelectedLocaleChange} i18n={this.props.i18n} additionalClasses="nav-link js-scroll-trigger" />
                        </Nav>
                    </Navbar.Collapse>
                    </Container>
                </Navbar>
            </div>
        )
    }
}

TuckletsNavBar.propTypes = props;

export default TuckletsNavBar;
               
               
               