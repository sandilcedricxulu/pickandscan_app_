
package com.example.picknscan;

import com.google.gson.annotations.SerializedName;

public class TempInvoice
{

    @SerializedName("InvoiceID")
  private int InvoiceID;
    @SerializedName("InvoiceDate")
  private String InvoiceDate;
    @SerializedName("InvoiceTime")
  private String InvoiceTime;
    @SerializedName("CustomerID")
  private int CustomerID;
  
  public void InvoiceID(int InvoiceID)
  {
       this.InvoiceID = InvoiceID;
  }
  
  public void InvoiceDate(String InvoiceDate)
  {
       this.InvoiceDate = InvoiceDate;
  }
  
  public void InvoiceTime(String InvoiceTime)
  {
       this.InvoiceTime = InvoiceTime;
  }
  
  
   public void CustomerID(int CustomerID)
  {
       this.CustomerID = CustomerID;
  }
   
  public int CustomerID()
  {
       return CustomerID;
  }
  
  public String InvoiceTime()
  {
       return InvoiceTime;
  }
  
  public String InvoiceDate()
  {
       return InvoiceDate;
  }
  
  public int InvoiceID()
  {
       return InvoiceID;
  }
}
