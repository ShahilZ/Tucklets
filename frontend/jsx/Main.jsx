import React, { Component } from "react";
import ReactDOM from 'react-dom';

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
            </div>
        );
    }
}

ReactDOM.render(
    <Main />,
    document.getElementById('tucklets-home')
);