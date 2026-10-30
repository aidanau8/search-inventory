import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SearchInventory {



    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Product> inventory = getInventory();

        int choice;

        do {
            System.out.println("\nWhat do you want to do?");
            System.out.println("1 - List all products");
            System.out.println("2 - Lookup a product by its ID");
            System.out.println("3 - Find products within a price range");
            System.out.println("4 - Add a new product");
            System.out.println("5 - Quit the application");
            System.out.print("Enter command: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Collections.sort(inventory,
                            Comparator.comparing(Product::getName));

                    for (Product product : inventory) {
                        System.out.println(
                                product.getId() + " | " +
                                        product.getName() + " | $" +
                                        product.getPrice()
                        );
                    }
                    break;


                case 2:
                    System.out.print("Enter product ID: ");
                    int searchId = scanner.nextInt();

                    boolean found = false;

                    for (Product product : inventory) {
                        if (product.getId() == searchId) {
                            System.out.println(
                                    product.getId() + " | " +
                                            product.getName() + " | $" +
                                            product.getPrice()
                            );
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Product not found.");
                    }
                    break;


                case 3:
                    System.out.print("Enter minimum price: ");
                    double minPrice = scanner.nextDouble();

                    System.out.print("Enter maximum price: ");
                    double maxPrice = scanner.nextDouble();

                    boolean productFound = false;

                    for (Product product : inventory) {
                        if (product.getPrice() >= minPrice &&
                                product.getPrice() <= maxPrice) {

                            System.out.println(
                                    product.getId() + " | " +
                                            product.getName() + " | $" +
                                            product.getPrice()
                            );
                            productFound = true;
                        }
                    }

                    if (!productFound) {
                        System.out.println("No products found.");
                    }
                    break;


                case 4:
                    System.out.print("Enter product ID: ");
                    int newId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter product name: ");
                    String newName = scanner.nextLine();

                    System.out.print("Enter product price: ");
                    double newPrice = scanner.nextDouble();

                    Product newProduct = new Product(newId, newName, newPrice);
                    inventory.add(newProduct);

                    System.out.println("Product added successfully!");
                    break;



                case 5:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 5);

        scanner.close();
    }

    public static ArrayList<Product> getInventory() {
        ArrayList<Product> inventory = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new FileReader("src/main/resources/inventory.csv"))) {

            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split("\\|");

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);

                Product product = new Product(id, name, price);
                inventory.add(product);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return inventory;
    }
}

