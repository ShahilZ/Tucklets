
import React, { Component } from 'react';
import ReactDOM from 'react-dom';

import Main from './Main';
import { CookiesProvider } from 'react-cookie';

class Root extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <CookiesProvider>
              <Main />
            </CookiesProvider>
        );
    }

}

ReactDOM.render(
    <Root />,
    document.getElementById('tucklets-home')
);