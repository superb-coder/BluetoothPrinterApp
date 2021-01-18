package com.ecare.printer.models;

import java.io.Serializable;
import java.util.List;

public class Invoice implements Serializable {
    public String name;
    public String address1;
    public String address2;
    public String Id1;
    public String Id2;
    public List<String> numbers;
    public String invoiceNumber;
    public String date;
    public String invoiceValue;

    public Invoice(String name, String address1, String address2, String id1, String id2, List<String> numbers, String invoiceNumber, String date, String invoiceValue){
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.Id1 = id1;
        this.Id2 = id2;
        this.numbers = numbers;
        this.invoiceNumber = invoiceNumber;
        this.date = date;
        this.invoiceValue = invoiceValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }



    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }



    public String getId1() {
        return Id1;
    }

    public void setId1(String id1) {
        Id1 = id1;
    }



    public String getId2() {
        return Id2;
    }

    public void setId2(String id2) {
        Id2 = id2;
    }



    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }



    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getInvoiceValue() {
        return invoiceValue;
    }

    public void setInvoiceValue(String invoiceValue) {
        this.invoiceValue = invoiceValue;
    }


}
