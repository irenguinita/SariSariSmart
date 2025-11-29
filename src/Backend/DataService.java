package Backend;

import Database.Inventory.InventoryManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    private final List<Product> products;
    private final List<Customer> customers = new ArrayList<>();
    private final List<Transaction> transactions = new ArrayList<>();
    private final InventoryManager inventoryManager = new InventoryManager();

    public DataService() throws IOException {
        this.products = inventoryManager.getInventoryList();
        populateDummyData();
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager; // assuming 'inventoryManager' is a field in DataService
    }

    private void populateDummyData() {
        customers.add(new Customer("1", "C-001", "Juan Dela Cruz", "09170000000", 120, 5000));
        customers.add(new Customer("2", "C-002", "Maria Clara", "09180000000", 50, 2500));
    }

    public List<Product> getProducts() { return products; }
    public List<Customer> getCustomers() { return customers; }
    public List<Transaction> getTransactions() { return transactions; }
}
