package Backend;

// product class
class Product {
    String id, sku, name, category, description;
    double price;
    int stock, reorderPoint;

    public Product(String id, String sku, String name, double price, int stock, String category, int reorderPoint, String description) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.reorderPoint = reorderPoint;
        this.description = description;
    }

    public static String generateSKU() {
        return "PROD-" + (int) (Math.random() * 10000);
    }

    public boolean isLowStock() {
        return stock <= reorderPoint;
    }

    public void adjustStock(int qty) {
        this.stock += qty;
    }

    public String toString() {
        return name;
    }
}
