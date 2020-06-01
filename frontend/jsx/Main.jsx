import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import { BrowserRouter, Route } from 'react-router-dom';

import HomePage from './pages/HomePage';
import AboutPage from './pages/AboutPage'
import i18n from './shared/i18n';

import 'bootstrap/dist/css/bootstrap.min.css'; 

import '../static/css/bootstrap-agency-theme.css';


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
                <BrowserRouter>
                    <HomePage i18n={i18n} handleSelectedLocaleChange={this.handleSelectedLocaleChange} />
                    <AboutPage i18n={i18n} handleSelectedLocaleChange={this.handleSelectedLocaleChange} numStudents={998} numTeachers={67}/>
                </BrowserRouter>
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