package org.microservice.structures.usersData;


import org.microservice.structures.enumerators.GenderEnum;
import org.microservice.structures.enumerators.PassportCategoryEnum;

import java.util.*;


/**
 * The <code>Passport</code> class is a simplified representation of a real passport.
 *  it defines the base structure for <code>Passport</code> entities.
 *
 *  * @author Ханк
 *  * @version 2.1
 */
public class Passport {

    /** The surname of the passport owner. */
    private String surname;
    /** The name of the passport owner. */
    private String name;
    /** The birth date of the passport owner. */
    private Date birthDate;
    /** The gender of the passport owner. */
    private GenderEnum gender;
    /** The nationality of the passport owner. */
    private Country country;
    /** The serial number of the passport. */
    private String serialNumber;
    /** The passport category. */
    PassportCategoryEnum passportCategory;
    /** The passport issue date. */
    private Date passportIssueDate;
    /** The passport expiration date. */
    private Date passportExpirationDate;
    /** The <code>byte</code> massive to store photo. */
    private byte[] photo;
    /** The countries visited by the owner of the passport. */
    private Map<String, ArrayList<Visit>> countriesVisited;


    /** Default constructor */
    public Passport() {
    }

    /** Passport constructor with parameters */
    public Passport(String surname, String name, Date birthDate, GenderEnum gender, Country country,
                    String serialNumber, PassportCategoryEnum passportCategory, Date passportIssueDate,
                    Date passportExpirationDate, byte[] photo, Map<String, ArrayList<Visit>> countriesVisited) {
        this.surname = surname;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.country = country;
        this.serialNumber = serialNumber;
        this.passportCategory = passportCategory;
        this.passportIssueDate = passportIssueDate;
        this.passportExpirationDate = passportExpirationDate;
        this.photo = photo;
        this.countriesVisited = countriesVisited;
    }

    // Utility methods

    /** Insert a <code>Country</code> visit for a specific <code>Passport</code>. */
    public void addVisit(String countryName, Date startDate, Date endDate, String purpose) {
        ArrayList<Visit> visits = !countriesVisited.containsKey(countryName) ? new ArrayList<>() :
                countriesVisited.get(countryName);
        visits.add(new Visit(startDate, endDate, purpose));
        countriesVisited.put(countryName, visits);
    }

    /** Return the number of times this passport's owner has visited the specified country. */
    public int nbrOfVisits(String c) {
        return (countriesVisited.get(c).size());
    }

    /**
     * Return the total number of entries in this passport's <I>countries visited</I> section;
     * It counts every entry, including multiple visits to the same country.
     */
    public int nbrOfVisits() {
        int count = 0;
        for (Map.Entry<String, ArrayList<Visit>> entry : countriesVisited.entrySet()) {
            String K = entry.getKey();
            ArrayList<Visit> V = entry.getValue();
            count = V.size();
        }
        return count;
    }

    public Set<String> getVisitedCountries() { return countriesVisited.keySet(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passport)) return false;
        Passport passport = (Passport) o;
        return getSurname().equals(passport.getSurname()) &&
                getName().equals(passport.getName()) &&
                getBirthDate().equals(passport.getBirthDate()) &&
                getGender() == passport.getGender() &&
                getCountry().equals(passport.getCountry()) &&
                getSerialNumber().equals(passport.getSerialNumber()) &&
                getPassportCategory() == passport.getPassportCategory();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSurname(), getName(), getBirthDate(), getGender(), getCountry(), getSerialNumber(), getPassportCategory());
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /** Return the information about this passport as a <code>String</code>, except photo data */
    @Override
    public String toString() {
        return "Passport{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", country=" + country +
                ", serialNumber=" + serialNumber +
                ", passportCategory=" + passportCategory +
                ", passportIssueDate=" + passportIssueDate +
                ", passportExpirationDate=" + passportExpirationDate +
                ", photo=" + Arrays.toString(photo) +
                ", countriesVisited=" + countriesVisited +
                '}';
    }


    // Getters and Setters

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public PassportCategoryEnum getPassportCategory() {
        return passportCategory;
    }

    public void setPassportCategory(PassportCategoryEnum passportCategory) {
        this.passportCategory = passportCategory;
    }

    public Date getPassportIssueDate() {
        return passportIssueDate;
    }

    public void setPassportIssueDate(Date passportIssueDate) {
        this.passportIssueDate = passportIssueDate;
    }

    public Date getPassportExpirationDate() { return passportExpirationDate; }

    public void setPassportExpirationDate(Date passportExpirationDate) {
        this.passportExpirationDate = passportExpirationDate;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Map<String, ArrayList<Visit>> getCountriesVisited() {
        return countriesVisited;
    }

    public void setCountriesVisited(Map<String, ArrayList<Visit>> countriesVisited) {
        this.countriesVisited = countriesVisited;
    }

}
