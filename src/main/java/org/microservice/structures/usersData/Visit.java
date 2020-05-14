package org.microservice.structures.usersData;

import java.util.Date;
import java.lang.String;

/**
 * Defines the base structure for <code>Visit</code> entities.
 *
 * @author Ханк
 */
public class Visit {
    private Date startDate;
    private Date endDate;
    private String purpose;

    public Visit() {
    }

    public Visit(Date _startDate, Date _endDate, String _purpose){
        startDate = _startDate;
        endDate = _endDate;
        purpose = _purpose;
    }

    // Getters and Setters

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", purpose=" + purpose +
                '}';
    }
}


