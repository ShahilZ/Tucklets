import React, { Component } from 'react';
import { PropTypes } from 'prop-types';

import '../../static/styles/locales.scss';


const props = {
    /** Handler for updating the selected locale. */
    handleSelectedLocaleChange: PropTypes.func.isRequired,
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired
}

class LocaleChanger extends Component {
    constructor(props) {
        super(props);
        this.state = { selectedLocale: '', supportedLocales: [] }
        // Bind handlers
        this.updateLocaleState = this.updateLocaleState.bind(this);
    }

    /**
     * Handler that updates the state based on backend changes.
     */
    
    updateLocaleState(selectedLocale, supportedLocales) {
        this.setState({ selectedLocale: selectedLocale, supportedLocales: supportedLocales });
    }



    componentDidMount() {
        axios.get('/locale')
          .then((response) => {
            // handle success
            this.updateLocaleState(response.data.selectedLocale, response.data.supportedLocales);
          })
          .catch(function (error) {
            // handle error
            console.log(error);
          });
    }
    render() {
        let localeList = this.state.supportedLocales.map(function(localeObject, index) {
            return <option key={`${localeObject.locale}-${index}`} value={localeObject.localeString}> {`${localeObject.localeDisplayName}`} </option>
        });
        return (
            <div className="locale-changer">
            <label className="locale-changer-label" htmlFor="locale-changer">{`${this.props.i18n.t('locales:language')}`}</label>
            <select id="locale-changer" value={this.state.selectedLocale} onChange={this.props.handleSelectedLocaleChange}>
                {localeList}
            </select>
            </div>
        );
    }
}
LocaleChanger.propTypes = props;

export default LocaleChanger;

