package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class OnlineStoreApp {
    public static void main(String[] args) throws IOException {
        productList = getProductList();

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


// add ad remove methods
    static ArrayList<Product> cartList = new ArrayList<>();
        public static void  addProductToCart(Product product){
          cartList.add(product);
        }
       public static void removeProductFromCart(Product product){
        cartList.remove(product);
       }



}
