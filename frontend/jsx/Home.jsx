import React, { Component } from "react";
import ReactDOM from 'react-dom';
import { PropTypes } from 'prop-types';

const props = {
   /** Test name. */
   name: PropTypes.string.isRequired
}

class Home extends Component {
    render() {
        return (
            <div>
               <p>This is Home component. My name is {this.props.name} </p>
            </div>
        );
    }


}
Home.propTypes = props;

export default Home;