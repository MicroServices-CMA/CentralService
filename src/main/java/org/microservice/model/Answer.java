package org.microservice.model;

import java.util.List;

public class Answer <T>
{
    private String status;
    private List<T> items;

    public Answer(String status, List<T> items)
    {
        this.status = status;
        this.items = items;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public List<T> getItems()
    {
        return items;
    }

    public void setItems(List<T> items)
    {
        this.items = items;
    }
}
