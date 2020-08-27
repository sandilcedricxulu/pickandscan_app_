package com.example.picknscan;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class InvoiceResponse {
    @SerializedName("date_time")
    private String date_time;
    @SerializedName("version")
    private String version;
    @SerializedName("data")
    private List<Invoice> data;

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Invoice> getData() {
        return data;
    }

    public void setData(List<Invoice> data) {
        this.data = data;
    }
}
