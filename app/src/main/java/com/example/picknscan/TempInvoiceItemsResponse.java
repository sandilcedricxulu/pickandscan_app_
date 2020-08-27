
package com.example.picknscan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TempInvoiceItemsResponse
{

    @SerializedName("date_time")
    private String date_time;
    @SerializedName("status_code")
    private String status_code;
    @SerializedName("version")
    private String version;
    @SerializedName("data")
    public List<TempInvoice> data;

    public void date_time(String date_time)
    {
        this.date_time = date_time;
    }
    public void status_code(String status_code)
    {
        this.date_time = status_code;
    }
    public void version(String version)
    {
        this.version = version;
    }
    public String version()
    {
        return version;
    }

    public String date_time()
    {
        return date_time;
    }
    public String status_code()
    {
        return status_code;
    }
    public List<TempInvoice> data()
    {
        return data;
    }
    public void data(List<TempInvoice> tempInvoiceItems)
    {
        data = tempInvoiceItems;
    }
}
