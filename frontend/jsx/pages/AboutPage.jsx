import React from 'react';
import { PropTypes } from 'prop-types';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChild, faChalkboardTeacher } from '@fortawesome/free-solid-svg-icons';

import NavBar from '../common/TuckletsNavBar';

import '../../static/scss/info.scss';

const props = {
    /** i18n object to help with translations.*/
    i18n: PropTypes.object.isRequired,
    /** Handler for updating the selected locale. */
    handleSelectedLocaleChange: PropTypes.func.isRequired,
    /** Number of students that have been sponsored. */
    numStudents: PropTypes.number,
    /** Numer of teachers that have been sponsored. */
    numTeachers: PropTypes.number
}

const AboutPage = ({ handleSelectedLocaleChange, i18n, numStudents, numTeachers }) => {
    return (
        <div id="about" className="about-section bg-light">
            <div className="row"> 
                <div className="col-lg-6 col-md-6 col-sm-12 col-12">
                    <div className="game-changer-image" />
                </div>
                <div className="col-lg-6 col-md-6 col-sm-12 col-12">
                    <div className="text-center container page-section">
                        <h2>{`${i18n.t("about:title")}`}</h2>
                        <h3 className="section-subheading text-muted m-0">
                            <div className="row justify-content-center">
                                <div className="col-lg-8 col-md-12 col-sm-14 col-12">
                                    <p>{`${i18n.t("about:subtext")}`}</p>
                                </div>                
                            </div>
                        </h3>
                        <div className="stats-info row">
                            <div className="students col-md-2">
                                <FontAwesomeIcon icon={faChild} size="4x" />
                                <p>{numStudents} {`${i18n.t("about:students")}`}</p>
                            </div>
                            <div className="teachers col-md-2">
                                <FontAwesomeIcon icon={faChalkboardTeacher} size="4x" />
                                <p>{numTeachers} {`${i18n.t("about:teachers")}`}</p>
                            </div>
                        </div>  
                        <div className="row justify-content-center">
                            <div className="col-lg-8 col-md-12 col-sm-14 col-12">
                                <p>{`${i18n.t("about:description")}`}</p>
                            </div>   
                        </div>          
                    </div>
                        
                </div>

            </div>
        </div>

    )
}

export default AboutPage;