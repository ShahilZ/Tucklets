import React, { Component } from 'react';
import axios from 'axios';
import { PropTypes } from 'prop-types';
import { withRouter } from 'react-router-dom';

import ChildImageContainer from '../common/sponsorship/ChildImageContainer';

import '../../static/scss/sponsor-a-child.scss';


const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
    /** Handler for updating the selected locale. */
    handleSelectedLocaleChange: PropTypes.func.isRequired,
    /** Handler for sponsor-a-child selections*/
    handleSponsorChildSubmission: PropTypes.func.isRequired,
}

class SponsorChildPage extends Component {

    constructor(props) {
        super(props);
        this.state = { children: [], childrenSelections: {} }

        this.onChildSelection = this.onChildSelection.bind(this);
    }

    
    componentDidMount() {
        let name = '';
        axios.get('/info/fetchChildren').then((response) => {
            let initialChildrenSelections = {};
            response.data.children.map((childContainer) => initialChildrenSelections[childContainer.child.childId] = false);
            this.setState({ children: response.data.children, childrenSelections: initialChildrenSelections });
        })

    }

    /**
    * Updates the checkbox's style depending on whether the child was selected or not..
    */
    onChildSelection(childId) {
        return () => {
            this.setState(prevState => ({
                childrenSelections: {
                    ...prevState.childrenSelections, 
                    [childId]: !prevState.childrenSelections[childId]
                }
            }));
        }
    }


    renderChildrenComponents() {
        if (this.state.children) {
            return (
                this.state.children.map((childContainer) => (
                    <label 
                        key={`child-id${childContainer.child.childId}`} 
                        className={`card child-label col-12 col-sm-6 col-md-4 ${this.state.childrenSelections[childContainer.child.childId] ? 'selected-overlay' : ''}`} 
                        htmlFor={`checkbox-${childContainer.child.childId}`} 
                        onChange={this.onChildSelection(childContainer.child.childId)}>
                            <ChildImageContainer i18n={this.props.i18n} childContainer={childContainer} />
                            <input type="checkbox" name="childId" id={`checkbox-${childContainer.child.childId}`} value={childContainer.child.childId} />
                    </label>
                ))
            );
        }
        return "";

    }

    render() {
    
        // Determine selected children.
        let selectedChildrenList = [];
        for (let childId in this.state.childrenSelections) {
            if (this.state.childrenSelections[childId]) {
                selectedChildrenList.push(childId)
            }
        }
        // let selectedChildrenRequest = `?childIds=${selectedChildrenList.join(',')}`;
        let selectedChildrenRequest = '';
        return (
            <div id="sponsor-a-child" className="bg-light">
                <div className="jumbotron jumbotron-fluid">
                    <div className="container">
                        <h1 className="display-4">{`${this.props.i18n.t("sponsorship:title")}`}</h1>
                        <p className="lead">{`${this.props.i18n.t("sponsorship:subtitle")}`}</p>
                    </div>
                </div>
                <div className="container">
                    <p>{`${this.props.i18n.t("sponsorship:description")}`}</p>
                </div>
                <div className="select-child-form container">
                    <div className="container children-div">
                        { this.renderChildrenComponents() }
                    </div>
                    <button className="btn btn-primary sponsor-now-button" onClick={this.props.handleSponsorChildSubmission(this.state.childrenSelections, this.props.history)}>{this.props.i18n.t("sponsorship:submit")}</button>

                </div>
            </div>
        )
    }
    

}

SponsorChildPage.propTypes = props;

export default withRouter(SponsorChildPage);