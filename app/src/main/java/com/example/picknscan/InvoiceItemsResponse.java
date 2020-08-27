package com.example.picknscan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InvoiceItemsResponse
{
    @SerializedName("date_time")
  private String date_time;
    @SerializedName("version")
  private String version;
    @SerializedName("data")
  public List<InvoiceItems> data;
  
  public void date_time(String date_time)
  {
      this.date_time = date_time;
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
  public List<InvoiceItems> data()
  {
      return data;
  }
    public void data(List<InvoiceItems> invoiceitems)
  {
      data = invoiceitems;
  }
}
