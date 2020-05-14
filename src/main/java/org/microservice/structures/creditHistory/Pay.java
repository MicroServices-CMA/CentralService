package org.microservice.structures.creditHistory;

import java.util.Date;

/**
 * Defines the base structure for <code>Pay</code> entities.
 *
 * @author Ксения
 * @version 1.0
 */
public class Pay
{
    Date date;
    double sum;

    public Pay() {
    }

    public Pay(Date date, double sum) {
        this.date = date;
        this.sum = sum;
    }

    public Date getDate() {
        return (Date) date.clone();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}