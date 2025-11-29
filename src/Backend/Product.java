package Backend;

// product class
public class Product {
    public String id;
    public String sku;
    public String name;
    public String category;
    public double price;
    public int stock;
    public int reorderPoint;

    public Product(String id, String sku, String name, double price, int stock, String category, int reorderPoint) {
        this.id = id; this.sku = sku; this.name = name; this.price = price;
        this.stock = stock; this.category = category; this.reorderPoint = reorderPoint;
    }

    public static String generateSKU() {
        return "PROD-" + (int)(Math.random() * 10000);
    }

    public boolean isLowStock(){
        return stock <= reorderPoint;
    }
    public void adjustStock(int qty){
        this.stock += qty;
    }
    public String toString(){
        return name;
    }
    public static Product fromCSVString(String csvLine){
        if (csvLine == null || csvLine.trim().isEmpty()){
            throw new IllegalArgumentException("CSV Line is either null or empty.");
        }

        String[] fields = csvLine.split(",");

        if (fields.length != 7){
            throw new IllegalArgumentException("Invalid CSV Format: Expected 8=7 fields");
        }

        try {
            String id = fields[0].trim();
            String sku = fields[1].trim();
            String name = fields[2].trim();
            double price = Double.parseDouble(fields[3].trim());
            int stock = Integer.parseInt(fields[4].trim());
            String category = fields[5].trim();
            int reorderPoints = Integer.parseInt(fields[6].trim());

            return new Product(id,sku,name,price,stock,category,reorderPoints);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Parsing error", e);
        }
    }
    public String toCSVString(){
        return id + "," + sku + "," + name + "," + price + "," +
                stock + "," + category + "," + reorderPoint;
    }
}
