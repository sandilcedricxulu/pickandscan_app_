
package com.example.picknscan;

public class TempInvoiceItems 
{
  private int InvoiceID;
  private int ProductID;
  private int Quantity;
  private double Price;
  
  public void InvoiceID(int InvoiceID)
  {
      this.InvoiceID = InvoiceID; 
  }
  public void ProductID(int ProductID)
  {
      this.ProductID = ProductID; 
  }
  public void Quantity(int Quantity)
  {
      this.Quantity = Quantity; 
  }
    public void Quantity(double Price)
  {
      this.Price = Price; 
  }
  
    
  public double Price()
  {
      return Price; 
  }
  
  public int Quantity()
  {
      return Quantity; 
  }
  
  public int ProductID()
  {
      return ProductID; 
  }
  public int InvoiceID()
  {
      return InvoiceID; 
  }    
}
