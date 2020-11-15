import React, { Component } from 'react';
import { PropTypes } from 'prop-types';

import '../../static/scss/basic.scss';

const props = {
 
    className: PropTypes.string,
    id: PropTypes.string,
    label: PropTypes.string,
    disabled: PropTypes.bool,
    placeholder: PropTypes.string,
    value: PropTypes.string,
    onChange: PropTypes.func,
    type: PropTypes.string,
    hasError: PropTypes.bool,
    errorMessage: PropTypes.string
}

class TuckletsInputBox extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }

    render() {
        return (
        <div className={this.props.hasError ? "tucklets-form-error" : "tucklets-form"}>
            <label 
                htmlFor={this.props.id}>{this.props.label}
            </label>
            {this.props.hasError && <span className='error-message'>{this.props.errorMessage}</span>}
            <input 
                className={this.props.className}
                type={this.props.type}
                id={this.props.id}
                placeholder={this.props.placeholder} 
                value={this.props.value} 
                onChange={this.props.onChange} 
            />
        </div>   
        )
    }
}

TuckletsInputBox.propTypes = props;

export default TuckletsInputBox;