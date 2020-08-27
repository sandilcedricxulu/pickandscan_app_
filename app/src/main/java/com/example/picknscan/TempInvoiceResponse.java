
package com.example.picknscan;

public class TempInvoiceResponse 
{
   private String date_time;
  private String version;
  public  TempInvoice data;
  
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
  public TempInvoice Invoice()
  {
      return data;
  }
    public void Invoice(TempInvoice invoice)
  {
      data = invoice;
  } 
}
