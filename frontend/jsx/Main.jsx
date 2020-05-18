import React, { Component } from "react";
import ReactDOM from 'react-dom';

class Main extends Component {
    render() {
        return (
            <div>
                <h1>Demo Component</h1>
                <img src="https://upload.wikimedia.org/wikipedia/commons/a/a7/React-icon.svg"/>
                <span>Hello, World! Welcome to the main page!</span>
                <span>Nice!</span>
            </div>
        );
    }
}

ReactDOM.render(
    <Main />,
    document.getElementById('tucklets-home')
);