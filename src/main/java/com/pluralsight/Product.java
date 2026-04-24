package com.pluralsight;

public class Product {
    private String SKU;
    private String productName;
    private double price;
    private String department;

    public Product(String SKU, String productName, double price, String department) {
        this.SKU = SKU;
        this.productName = productName;
        this.price = price;
        this.department = department;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String toString(){
        return this.SKU + "|" + this.productName + "|" + this.price + "|" + this.department;
    }


}
