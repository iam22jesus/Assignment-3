import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RedBlackTree rbt = new RedBlackTree();
        String csvFile = "src/amazon-product-data.csv";

        System.out.println("Inserting products from CSV file into the tree...");

        // Insert products information from the CSV file without showing it as output
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String[] productDetails = parseCSVLine(line);

                if (productDetails.length < 4 || productDetails[3].isEmpty()) {
                    continue;  // Skip line if data missing
                }

                String productId = productDetails[0];
                String productName = productDetails[1];
                String category = productDetails[2];
                double price;

                try {
                    price = parsePrice(productDetails[3]);
                    rbt.insert(productId, productName, category, price); // Insert in Red-Black Tree
                } catch (NumberFormatException e) {
                    continue;  // Skip line if price format is incorrect
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }

        // hardcode insert products for testing
        String productId1 = "4c69b61db1fc16e7013b43fc926e5072d";
        String productName1 = "DB Longboards CoreFlex Crossbow 41\" Bamboo Fiberglass Longboard Complete";
        String category1 = "Sports & Outdoors | Outdoor Recreation | Skates, Skateboards & Scooters | Skateboarding | Standard Skateboards & Longboards | Longboards";
        double price1 = 237.68;

        String productId2 = "6d77c7b8b1fc21f8013b55caa94d6a2e";
        String productName2 = "Garmin Forerunner 945 GPS Smartwatch";
        String category2 = "Electronics | Wearable Technology | Smartwatches";
        double price2 = 599.99;

        System.out.println("Testing manual insertion of products...");

        // Inserting first product
        boolean inserted1 = rbt.insert(productId1, productName1, category1, price1);
        if (inserted1) {
            System.out.println("First Product inserted successfully!");
            displayProductDetails(productId1, productName1, category1, price1);
        } else {
            System.out.println("Error: Product with ID " + productId1 + " already exists.");
        }

        // Attempting to insert a duplicate product
        boolean insertedDuplicate = rbt.insert(productId1, productName1, category1, price1);
        if (insertedDuplicate) {
            System.out.println("Product inserted successfully!");
            displayProductDetails(productId1, productName1, category1, price1);
        } else {
            System.out.println("Error: Product with ID " + productId1 + " already exists. It will not be inserted again.");
        }

        // Inserting second product
        boolean inserted2 = rbt.insert(productId2, productName2, category2, price2);
        if (inserted2) {
            System.out.println("Second Product inserted successfully!");
            displayProductDetails(productId2, productName2, category2, price2);
        } else {
            System.out.println("Error: Product with ID " + productId2 + " already exists.");
        }

        // Allow dynamic searches
        Scanner scanner = new Scanner(System.in);
        String continueSearch;

        do {
            System.out.println("\nEnter a Product ID to search:");
            String searchId = scanner.nextLine();
            System.out.println("Searching for product with ID: " + searchId);
            rbt.searchById(searchId);  // searchById displays details in formatted way

            System.out.println("\nDo you want to search for another product? (yes/no):");
            continueSearch = scanner.nextLine();
        } while (continueSearch.equalsIgnoreCase("yes"));

        System.out.println("Exiting the program. Thank you!");
        scanner.close();
    }

    //parse CSV line accounting for fields and commas
    private static String[] parseCSVLine(String line) {
        return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    }

    // parse price, handling ranges or missing values
    private static double parsePrice(String priceField) throws NumberFormatException {
        if (priceField.contains("-")) {
            String[] range = priceField.replace("$", "").trim().split("-");
            return (Double.parseDouble(range[0]) + Double.parseDouble(range[1])) / 2; // Average
        }
        return Double.parseDouble(priceField.replace("$", "").trim());
    }

    // display product details in a readable format
    private static void displayProductDetails(String productId, String productName, String category, double price) {
        System.out.println("Product Details:");
        System.out.println("ID: " + productId);
        System.out.println("Name: " + productName);
        System.out.println("Category: " + category);
        System.out.println("Price: $" + price);
    }
}
