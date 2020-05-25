import React, { Component } from "react";
import ReactDOM from 'react-dom';
import axios from 'axios';
import Home from './Home';


import LocaleChanger from './shared/LocaleChanger';
import i18n from './shared/i18n';

class Main extends Component {
    constructor(props) {
        super(props);
        this.state = { selectedLocale: 'en-US' };

        this.handleSelectedLocaleChange = this.handleSelectedLocaleChange.bind(this);
    }

    /**
     * Handler that updates the currently selected locale.
     */
    handleSelectedLocaleChange(event) {
        const selectedLocale = event.target.value;
        this.setState({ selectedLocale: selectedLocale });
        i18n.changeLanguage(selectedLocale);
    }

    render() {
        return (
            <div>
                <LocaleChanger handleSelectedLocaleChange={this.handleSelectedLocaleChange} i18n={i18n} />
                <h1>Demo Component</h1>
                <img src="https://upload.wikimedia.org/wikipedia/commons/a/a7/React-icon.svg"/>
                <span>Hello, World! Welcome to the main page!</span>
                <span>The selected locale is: {`${this.state.selectedLocale}`}</span>
                {i18n.t('locales:apptext')}

                { /* Navbar */ }
                <nav className="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
                    <div className="container">
                        <a className="navbar-brand js-scroll-trigger" href="#page-top"><img src="../static/img/navbar-logo.svg" alt="" /></a><button className="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">Menu<i className="fas fa-bars ml-1"></i></button>
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

    // componentDidMount() {
    //     let name = '';
    //     axios.get('/test').then((response) => {
    //         console.log(response.data);
    //         this.setState({ name: response.data.name });
    //     })

    // }

}


ReactDOM.render(
    <Main />,
    document.getElementById('tucklets-home')
);