import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import { NavHashLink as Link } from 'react-router-hash-link';
import Nav from 'react-bootstrap/Nav'
import Navbar from 'react-bootstrap/Navbar'
import Container from 'react-bootstrap/Container'

import LocaleChanger from './LocaleChanger';

import '../../static/scss/locales.scss';
import '../../static/scss/info.scss';
import '../../static/scss/basic.scss';


const props = {
    /** Currently selected locale */
    selectedLocale: PropTypes.string.isRequired,
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
            isScrollTop: true,
            isNavBarExpanded: false
        }
        // Bind handlers
        this.handleScroll = this.handleScroll.bind(this);
        this.toggleNavBar = this.toggleNavBar.bind(this);
        this.collapseNavBar = this.collapseNavBar.bind(this);
    }

    // 11/11/2020: Commenting out for now, but leaving for reference.
    // Originally, we wanted to change the navbar's height when the user scrolled past a certain threshold. In order to do this, we had
    // to break the React paradigm and add event listeners for the 'scroll' event.

    // componentDidMount() {
    //     window.addEventListener('scroll', this.handleScroll);
    // }

    // componentWillUnmount() {
    //     window.removeEventListener('scroll', this.handleScroll);
    // }

    /**
     * Scroll handler that determines if the user has scrolled. If scrolling past a certain threshold, update the state.
     * This was used earlier to determine if we wanted to display the larger Tucklets logo or the condensed one.
     */
    handleScroll() {
        const isScrollTop = window.scrollY < 100;
        if (isScrollTop !== this.state.isScrollTop) {
            this.setState({ isScrollTop })
        }
    }

    /**
     * Toggle's the state's isNavBarExpanded.
     */
    toggleNavBar() {
        this.setState({ isNavBarExpanded: !this.state.isNavBarExpanded });
    }

    /**
     * Collapse the navigation bar.
     */
    collapseNavBar() {
        this.setState({ isNavBarExpanded: false })
    }

    render() {
        return (
            <div>
                <Navbar className="tucklets-nav navbar-scroll" expand="lg" fixed="top" expanded={this.state.isNavBarExpanded}>
                    <Container>
                    <Navbar.Brand href="/#home">
                        <img
                            src="../../static/img/tucklets-logo-purple.png"
                            // className={`${this.state.isScrollTop ? "tucklets-logo-regular" : "tucklets-logo-condensed"}`}
                            className="tucklets-logo-condensed"
                            alt="Tucklets Logo"
                        />
                    </Navbar.Brand>
                    <button className="navbar-toggler" aria-controls="basic-navbar-nav" onClick={this.toggleNavBar}>
                        <span className="navbar-toggler-icon" />
                    </button>
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="ml-auto nav-links">
                            <Link className="nav-link js-scroll-trigger" smooth to="/#home" onClick={this.collapseNavBar}>{this.props.i18n.t("navigation:home")}</Link>
                            <Link className="nav-link js-scroll-trigger" smooth to="/#about" onClick={this.collapseNavBar}>{this.props.i18n.t("navigation:about")}</Link>
                            <Link className="nav-link js-scroll-trigger" smooth to="/#sponsor-a-child" onClick={this.collapseNavBar}>{this.props.i18n.t("navigation:sponsor_navbar")}</Link>
                            <Link className="nav-link js-scroll-trigger" smooth to="/#our-story" onClick={this.collapseNavBar}>{this.props.i18n.t("navigation:our_story")}</Link>
                            <Link className="nav-link js-scroll-trigger" smooth to="/#newsletters" onClick={this.collapseNavBar}>{this.props.i18n.t("navigation:newsletters")}</Link>
                            <Link className="nav-link js-scroll-trigger" smooth to="/#donate" onClick={this.collapseNavBar}>{this.props.i18n.t("navigation:donate")}</Link>
                            <LocaleChanger 
                                handleSelectedLocaleChange={this.props.handleSelectedLocaleChange} 
                                i18n={this.props.i18n} 
                                additionalClasses="nav-link js-scroll-trigger" 
                                selectedLocale={this.props.selectedLocale}
                            />
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
