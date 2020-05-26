import React, { Component } from "react";
import ReactDOM from 'react-dom';
import axios from 'axios';
import Home from './Home';


import LocaleChanger from './shared/LocaleChanger';
import i18n from './shared/i18n';
import NavBar from "./shared/NavBar";

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
                {/* <LocaleChanger handleSelectedLocaleChange={this.handleSelectedLocaleChange} i18n={i18n} />
                <h1>Demo Component</h1>
              
                <span>The selected locale is: {`${this.state.selectedLocale}`}</span>
                {i18n.t('locales:apptext')} */}

                <NavBar handleSelectedLocaleChange={this.handleSelectedLocaleChange} i18n={i18n} />

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