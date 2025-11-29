package Database.Inventory;

import Backend.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private static final String INVENTORY_FILE_CSV = "data/Inventory/inventory.csv";
    private final List<Product> inventoryList;

    public InventoryManager(){
        this.inventoryList = new ArrayList<>();
        try {
            loadInventoryFromCSV();
        } catch (IOException e) {
            System.err.println("ERROR: Could not load inventory data. " + e.getMessage());
        }
    }
    public void inventoryFolder(){
        File dataDirectory = new File("data/Inventory");
        if (!dataDirectory.exists()){
            boolean created = dataDirectory.mkdirs();
            if (created){
                System.out.println("Created data directory: " + dataDirectory.getAbsolutePath());
            } else {
                System.err.println("ERROR: Could not create directory");
            }
        } else {
            System.err.println("Folder already existed.");
        }
    }
    public List<Product> getInventoryList() {
        return inventoryList;
    }
    public void loadInventoryFromCSV() throws IOException{
        File inventoryFile = new File(INVENTORY_FILE_CSV);
        if (!inventoryFile.exists()){
            saveInventoryToCSV();
            throw new FileNotFoundException("File does not exist. Creating empty list.");
        }
        int lineNumber = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(INVENTORY_FILE_CSV))){
            String line;
            br.readLine();

            while((line = br.readLine()) != null){
                lineNumber++;

                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    Product inventory = Product.fromCSVString(line);
                    inventoryList.add(inventory);
                } catch (NumberFormatException e) {
                    System.err.println("Parsing error. " + e.getMessage());
                    throw new NumberFormatException("Invalid format on line " + lineNumber + " in file " + INVENTORY_FILE_CSV);
                }
            }
        } catch (IOException e){
            throw new FileNotFoundException("File not exist. " + e.getMessage());
        }
    }
    public void saveInventoryToCSV() throws IOException {
        String[] header = {"ID", "SKU", "Name", "Price", "Stock", "Category", "Reorder Points"};

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(INVENTORY_FILE_CSV))){
            bw.write(String.join(",", header));
            bw.newLine();

            for (Product inventory : inventoryList){
                String userLine = inventory.toCSVString();

                bw.write(userLine);
                bw.newLine();
            }
        } catch (IOException e){
            System.err.println("Error while saving CSV File" + e.getMessage());
            throw e;
        }
    }
    private String getID(){
        int maxID = 0;

        for (Product product : inventoryList){
            try {
                int currentID = Integer.parseInt(product.id);

                if (currentID > maxID){
                    maxID = currentID;
                }
            } catch (NumberFormatException e){
                System.err.println("Invalid ID Format. " + e.getMessage());
            }
        }
        return (maxID + 1) + "";
    }
    public void saveProducts(String sku, String name, String category, double price, int stock, int reorderPoints) {
        boolean productUpdated = false;
        for (Product existingProduct : inventoryList) {
            if (existingProduct.sku.equalsIgnoreCase(sku)) {
                existingProduct.name = name;
                existingProduct.category = category;
                existingProduct.price = price;
                existingProduct.stock = stock;
                existingProduct.reorderPoint = reorderPoints;

                productUpdated = true;
                System.out.println("INFO: Product updated: " + sku);
                break;
            }
        }

        if (!productUpdated) {
            String newId = getID();
            // Create a new Product instance
            Product newProduct = new Product(newId, sku, name, price, stock, category, reorderPoints);
            inventoryList.add(newProduct);
            System.out.println("INFO: New product added: " + sku);
        }

        try {
            saveInventoryToCSV();
        } catch (IOException e){
            throw new RuntimeException("Failed to save inventory after modification.", e);
        }
    }
    public boolean deleteProductsBySku(String sku){
        boolean removed = false;

        for (int i = 0; i < inventoryList.size(); i++){
            Product p = inventoryList.get(i);
            if (p.sku.equalsIgnoreCase(sku)){
                inventoryList.remove(i);
                removed = true;
                System.out.println("INFO: Product deleted with SKU: " + sku);
                break;
            }
        }

        if (removed) {
            try {
                saveInventoryToCSV();
            } catch (IOException e) {
                throw new RuntimeException("Failed to save inventory after modification.", e);
            }
        } else {
            return false;
        }
        return true;
    }
}