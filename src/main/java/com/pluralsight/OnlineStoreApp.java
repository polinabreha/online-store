package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class OnlineStoreApp {
    public static void main(String[] args) throws IOException {
        boolean run = true;
        while (run) {
            Scanner input = new Scanner(System.in);
            System.out.println("-----WELCOME IN OUR ONLINE STORE -----");
            System.out.println("1 - Display Products");
            System.out.println("2 - Display Cart");
            System.out.println("3 - Exit");
            System.out.print("Please enter your choice: ");
            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("----Products List----");
                    productList = getProductList();
                    System.out.println();
                    break;

                case 2:
                    System.out.println("----Cart----");
                    for (Product product : cartList) {
                        System.out.println(product);
                    }
                    break;

                case 3:
                    run = false;
                    break;

            }

        }

    }

    //reading the list of products
    static ArrayList<Product> productList = new ArrayList<>();
        public static ArrayList<Product>  getProductList () throws IOException{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/products.csv"));
            String line;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] item = line.split("\\|");
                Product product = new Product (item[0], item[1],
                        Double.parseDouble(item[2]), item[3]);
                productList.add(product);
            }
            return productList;
        }

        // user can search the product by name
       public static ArrayList<Product> searchByProductName (String productName) {
        ArrayList<Product> results = new ArrayList <>();
        for(Product product: productList){
            if(product.getProductName().toLowerCase().contains(productName.toLowerCase())){
                results.add(product);
            }
            if (productList.isEmpty()){
                break;
            }
        }
        return results;
       }

    // user can search the product by department
    public static ArrayList<Product> searchByProductDepartment (String productDepartment) {
        ArrayList<Product> results = new ArrayList <>();
        for(Product product: productList){
            if(product.getDepartment().toLowerCase().contains(productDepartment.toLowerCase())){
                results.add(product);
            }
            if (results.isEmpty()){
                System.out.println("No products found in that department");
            }
        }
        return results;
    }

    // user can search the product by price
    public static ArrayList<Product> searchByProductPrice (double productPrice) {
        ArrayList<Product> results = new ArrayList <>();
        for(Product product: productList){
            if(product.getPrice() <= productPrice){
                results.add(product);
            }
            if (results.isEmpty()){
                System.out.println("No products found at that price");
            }
        }
        return results;
    }


// add and remove methods
    static ArrayList<Product> cartList = new ArrayList<>();
        public static void  addProductToCart(Product product){
          cartList.add(product);
        }
       public static void removeProductFromCart(Product product){
        cartList.remove(product);
       }



}
