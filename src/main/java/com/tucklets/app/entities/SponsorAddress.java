package com.tucklets.app.entities;

import com.tucklets.app.entities.enums.State;

import javax.persistence.Embeddable;

@Embeddable
public class SponsorAddress {

    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private State state;
    private String zipCode;
    private String country;

    public SponsorAddress() {};

    public SponsorAddress(
            String streetAddress1, String streetAddress2, String city, State state, String zipCode, String country)
    {
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = streetAddress2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }


    public String getStreetAddress1() {
        return streetAddress1;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public String getCity() {
        return city;
    }

    public State getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    /**
     * Custom toString() method to print out the address.
     */

    public String toString() {
        return String.format(
                "%s \n %s \n %s, %s %s \n %s",
                streetAddress1, streetAddress2, city, state, zipCode, country);
    }
}
