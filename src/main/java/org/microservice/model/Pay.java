package org.microservice.model;

import java.util.Date;

public class Pay
{
    Date date;
    double sum;

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
