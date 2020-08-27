package com.example.picknscan;

import com.google.gson.annotations.SerializedName;

public class Invoice
{
    @SerializedName("InvoiceID")
    public String InvoiceID;
    @SerializedName("InvoiceDate")
    public String InvoiceDate;
    @SerializedName("InvoiceTime")
    public String InvoiceTime;
    @SerializedName("EmployeeID")
    public String EmployeeID;
    @SerializedName("CustomerID")
    public int CustomerID;

    public String getInvoiceID() {
        return InvoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        InvoiceID = invoiceID;
    }

    public String getInvoiceDate() {
        return InvoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        InvoiceDate = invoiceDate;
    }

    public String getInvoiceTime() {
        return InvoiceTime;
    }

    public void setInvoiceTime(String invoiceTime) {
        InvoiceTime = invoiceTime;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }
}
