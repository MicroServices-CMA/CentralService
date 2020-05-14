package org.microservice.structures.usersData;

/**
 * Defines a mailing address.
 *
 * @author Ханк
 * @version 1.0
 */
public class PhysicalAddress {
    private String street = "";
    private String city = "";
    private String state = "";
    private String zip = "";

    public PhysicalAddress() {
    }

    /**
     * Constructs a mailing address.
     *
     * @param street the street
     * @param city   the city
     * @param state  the two-letter state code
     * @param zip    the ZIP postal code.
     */
    public PhysicalAddress(String street, String city, String state, String zip) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "Street: " + street + '\n'
                + "City: " + city + '\n'
                + "State: " + state + '\n'
                + "Zip code: " + zip + '\n';
    }

    // Getters and Setters

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}

