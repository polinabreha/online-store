package com.pluralsight;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class OnlineStoreApp {
    public static void main(String[] args) {
        try {
            productList = getProductList();
            Scanner input = new Scanner(System.in);
        boolean run = true;
        while (run) {
            System.out.println("-----WELCOME IN OUR ONLINE STORE -----");
            System.out.println("1 - Display Products");
            System.out.println("2 - Display Cart");
            System.out.println("3 - Exit");
            System.out.print("Please enter your choice: ");
            int choice = input.nextInt();

            switch (choice) {
                case 1:

                    boolean productScreen = true;

                    while (productScreen) {
                        System.out.println("---What would you like to do?---");
                        System.out.println("1 - Search by Name");
                        System.out.println("2 - Search by Department");
                        System.out.println("3 - Search by Price");
                        System.out.println("4 - Add to cart");
                        System.out.println("5 - Go back");
                        System.out.print("Enter your choice: ");
                        int choice2 = input.nextInt();


                        switch (choice2) {
                            case 1:
                                System.out.print("Enter product name: ");
                                input.nextLine();
                                String productName = input.nextLine();
                                ArrayList<Product> results = searchByProductName(productName);
                                for(Product p : results){
                                    System.out.println(p);
                                }
                             break;

                            case 2:
                                System.out.print("Enter product department: ");
                                input.nextLine();
                                String productDepartment = input.nextLine();
                                ArrayList<Product> results2 = searchByProductDepartment(productDepartment);
                                for(Product p : results2){
                                    System.out.println(p);
                                }
                            break;
                            case 3:
                                System.out.print("Enter product price: ");
                                double productPrice = input.nextDouble();
                                ArrayList<Product> results3 = searchByProductPrice(productPrice);
                                for(Product p : results3){
                                    System.out.println(p);
                                }
                            break;

                            case 4:
                                System.out.print("What product would you like to add to cart?(enter name)");
                                input.nextLine();
                                String productName1 = input.nextLine();
                                ArrayList<Product> found = searchByProductName(productName1);

                                if(found.isEmpty()){
                                    System.out.println("Product not found");
                                } else {
                                    addProductToCart(found.get(0));
                                    System.out.println(found.get(0).getProductName() + " added to cart!");
                                }
                            break;

                            case 5:
                                productScreen = false;
                            break;



                        }
                    }



                break;



                case 2:
                    System.out.println("----Cart----");
                    System.out.println("--Products:--");
                    ArrayList<String> displayed = new ArrayList<>();
                    for (Product product : cartList) {
                        if (!displayed.contains(product.getSKU())) {
                            System.out.println(product + " x" + cartQuantity.get(product.getSKU()));
                            displayed.add(product.getSKU());
                        }
                    }

                    boolean productScreen2 = true;
                    while (productScreen2) {
                        System.out.println("What would you like to do next?");
                        System.out.println("1. Check Out");
                        System.out.println("2. Remove product from cart");
                        System.out.println("3. Go Back");
                        System.out.print("Enter your choice: ");
                        int choice2 = input.nextInt();
                        input.nextLine();
                        double total = 0;
                        double change = 0;

                        switch (choice2) {
                            case 1:
                                for (Product product : cartList) {
                                   total += product.getPrice();
                                }
                                System.out.println("Your total is : $ " + total);
                                System.out.print("Enter cash amount : $ ");
                                input.nextLine();
                                double cashAmount = input.nextDouble();

                              boolean paymentSuccess = false;
                              while (!paymentSuccess) {
                                  if (cashAmount < total) {
                                      System.out.println("Not enough! You need $" + (total - cashAmount) + " more");
                                      System.out.println("1 - Try again");
                                      System.out.println("2 - Go back");
                                      System.out.println("Enter your choice: ");
                                      input.nextLine();
                                      int retryChoice = input.nextInt();
                                      if(retryChoice == 1){
                                          System.out.print("Enter cash amount: $");
                                          cashAmount = input.nextDouble();
                                      } else {
                                          break;
                                      }
                                  } else  {
                                      change = cashAmount - total;
                                      LocalDateTime today = LocalDateTime.now();
                                      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                                      System.out.println("-----RECEIPT-----");
                                      System.out.println("Date: " + today.format(dtf));
                                      System.out.println("--Items--");
                                      ArrayList<String> displayed2 = new ArrayList<>();
                                      for(Product product : cartList){
                                          if(!displayed2.contains(product.getSKU())){
                                              System.out.println(product + " x" + cartQuantity.get(product.getSKU()));
                                              displayed2.add(product.getSKU());
                                          }
                                      }
                                      System.out.println("Total: $" + String.format("%.2f", total));
                                      System.out.println("Amount Paid: $" + String.format("%.2f", cashAmount));
                                      System.out.println("Change: $" + String.format("%.2f", change));
                                      saveReceipt(cartList, total, cashAmount, change);

                                      paymentSuccess = true;
                                      cartList.clear();
                                      cartQuantity.clear();
                                      productScreen2 = false;
                                  }
                              }
                              break;
                            case 2:
                                System.out.print("What item would you like to remove?(enter name)");
                                String itemName = input.nextLine();
                                ArrayList<Product> found = searchCartByName(itemName);
                                if(found.isEmpty()){
                                    System.out.println("Product not found");
                                } else{
                                    removeProductFromCart(found.get(0));
                                    System.out.println(found.get(0).getProductName() + " removed from cart!");
                                }
                            break;
                            case 3:
                                productScreen2 = false;
                            break;
                        }

                    }
                break;

                case 3:
                    run = false;
                break;

            }

        }
       }catch (IOException e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
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
        }
        if (results.isEmpty()){
            System.out.println("No products found in that department");
        }

        return results;
    }

    // user can search the product by price
    public static ArrayList<Product> searchByProductPrice (double productPrice) {
        ArrayList<Product> results = new ArrayList <>();
        for(Product product: productList){
            if(product.getPrice() == productPrice){
                results.add(product);
            }
        }
        if (results.isEmpty()){
                System.out.println("No products found at that price");
            }

        return results;
    }


// add and remove methods
     static HashMap<String, Integer> cartQuantity = new HashMap<>();
    static ArrayList<Product> cartList = new ArrayList<>();
        public static void  addProductToCart(Product product){
          cartList.add(product);
            String sku = product.getSKU();
            if (cartQuantity.containsKey(sku)) {
                cartQuantity.put(sku, cartQuantity.get(sku) + 1);
            } else {
                cartQuantity.put(sku, 1);
            }
        }
       public static void removeProductFromCart(Product product){
           String sku = product.getSKU();
           if (cartQuantity.get(sku) > 1) {
               cartQuantity.put(sku, cartQuantity.get(sku) - 1); // decrease quantity
           } else {
               cartQuantity.remove(sku);
           }
        cartList.remove(product);

       }

       public static void saveReceipt(ArrayList<Product> cartList, double total, double cashAmount, double change) throws IOException{
           LocalDateTime today = LocalDateTime.now();
           DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
           DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
           String fileName = today.format(fileFormat);

           BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/Receipts/" + fileName + ".txt"));
          bufferedWriter.write("------RECEIPT------\n");
           bufferedWriter.write("Date: " + today.format(dtf) + "\n");
           bufferedWriter.write("--Items--\n");
           ArrayList<String> displayed = new ArrayList<>();
           for (Product product : cartList) {
               if (!displayed.contains(product.getSKU())) {
                   bufferedWriter.write(product + " x" + cartQuantity.get(product.getSKU()) + "\n");
                   displayed.add(product.getSKU());
               }
           }
           bufferedWriter.write("Total: $" + String.format("%.2f", total) + "\n");
           bufferedWriter.write("Amount Paid: $" + String.format("%.2f", cashAmount) + "\n");
           bufferedWriter.write("Change: $" + String.format("%.2f", change) + "\n");

        bufferedWriter.close();
       }

    public static ArrayList<Product> searchCartByName(String productName) {
        ArrayList<Product> results = new ArrayList<>();
        for (Product product : cartList) {
            if (product.getProductName().toLowerCase().contains(productName.toLowerCase())) {
                results.add(product);
            }
        }
        return results;
    }



}
