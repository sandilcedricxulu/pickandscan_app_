
package com.example.picknscan;

public class Products 
{
    private int ProductID;
    private String ProductName;
    private String Barcode;
    private int SupplierID;
    private int CategoryID;
    private String QuantityPerUnit;
    private int Quantity;
    private double UnitPrice;
    private double Total;
    private int UnitsInStock;
    private int ReOrderLevel;
    private int Discontinued;

    public Products(int ProductID,String ProductName,double UnitPrice,String QuantityPerUnit,int Quantity,double Total)
    {
        this.ProductName = ProductName;
        this.ProductID = ProductID;
        this.UnitPrice = UnitPrice;
        this.Quantity = Quantity;
        this.QuantityPerUnit = QuantityPerUnit;
        this.Total = Total;
    }

    public Products()
    {

    }

    public void ProductName(String ProductName)
    {
        this.ProductName = ProductName;
    }
    public void ProducID(int ProductID)
    {
        this.ProductID = ProductID;
    }
    public void Barcode(String Barcode)
    {
        this.Barcode = Barcode;
    }
    public void SupplierID(int SupplierID)
    {
        this.SupplierID = SupplierID;
    }
    public void CategoryID(int CategoryID)
    {
        this.CategoryID = CategoryID;
    }
    public void QuantityPerUnit(String QuantityPerUnit)
    {
        this.QuantityPerUnit = QuantityPerUnit;
    }
    public void Quantity(int Quantity)
    {
        this.Quantity = Quantity;
    }
    public void UnitPrice(double UnitPrice)
    {
        this.UnitPrice = UnitPrice;
    }
    public void Total(double unitPrice,int quantity)
    {
        this.Total = unitPrice * quantity;
    }
    public void UnitsInStock(int UnitsInStock)
    {
        this.UnitsInStock = UnitsInStock;
    }
    public void ReOrderLevel(int ReorderLevel)
    {
        this.ReOrderLevel = ReorderLevel;
    }
    public void Discontinued(int Discontinued)
    {
        this.Discontinued = Discontinued;
    }


    public String ProductName()
    {
        return ProductName;
    }
    public int Discontinued()
    {
        return Discontinued;
    }
    public int ReOrderLevel()
    {
        return ReOrderLevel;
    }
    public int UnitsInStock()
    {
        return UnitsInStock;
    }
    public double UnitPrice()
    {
        return UnitPrice;
    }
    public double Total(int qty,double unitPrice)
    {
        return qty*unitPrice;
    }
    public String QuantityPerUnit()
    {
        return QuantityPerUnit;
    }
    public int Quantity()
    {
        return Quantity;
    }
    public int CategoryID()
    {
        return CategoryID;
    }
    public int SupplierID()
    {
        return SupplierID;
    }
    public String Barcode()
    {
        return Barcode;
    }
    public int ProductID()
    {
        return ProductID;
    }

}
