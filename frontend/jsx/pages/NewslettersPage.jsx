import React, { Component } from 'react';
import axios from 'axios';
import { PropTypes } from 'prop-types';

import NavBar from '../common/TuckletsNavBar';

import '../../static/scss/info.scss';

const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
    /** Handler for updating the selected locale. */
    handleSelectedLocaleChange: PropTypes.func.isRequired,
}

class NewslettersPage extends Component {

    constructor(props) {
        super(props);
        this.state = { newsletters: [] }
    }

    
    componentDidMount() {
        axios.get('/info/fetchNewsletters').then((response) => {
            // Only update state if there are newsletters to display.
            if (response.data.newsletters.length > 0) {
                this.setState({ newsletters: response.data.newsletters });
            }
        })

    }

    render() {
        return (
            <div id="newsletters" className="newsletter-section bg-light">
                <div className="row"> 
                    <div className="col-lg-12 col-md-12 col-sm-12 col-12">
                        <div className="container page-section">
                            <div className="text-center newsletters-info">
                                <h2>{`${this.props.i18n.t("newsletters:title")}`}</h2>
                                <h3 className="section-subheading text-muted m-0">
                                    <div className="row justify-content-center">
                                        <div className="col-lg-8 col-md-12 col-sm-14 col-12">
                                            <p>{`${this.props.i18n.t("newsletters:subtext")}`}</p>
                                        </div>                
                                    </div>
                                </h3>
                                <div className="row">
                                    <div className="col-lg-12 col-md-12 col-sm-12 col-12">
                                        <a href={this.state.newsletters.length > 0 ? this.state.newsletters[0].newsletterLocation : null} target="_blank"> 
                                            <input type="button" className="btn btn-primary" value={`${this.props.i18n.t("newsletters:latest")}`} />
                                        </a>
                                    </div>
                                </div>
                            </div>

                            <ul className="newsletter-links-container">
                                {  this.state.newsletters.length > 0 && this.state.newsletters.map((newsletter, index) => (
                                    <li key={"newletter-" + index}>
                                        <a className="newsletter-links" href={newsletter.newsletterLocation} target="_blank">{this.props.i18n.t("newsletters:click") + newsletter.filename}</a>
                                    </li>
                                ))}   
                            </ul>
  
                        </div>          
                    </div>
                </div>
            </div>

        )
    }
    

}

NewslettersPage.propTypes = props;

export default NewslettersPage;