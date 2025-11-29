package Backend;

public class Customer {
    public String id, customId, name, phone;
    public int loyaltyPoints;
    public double totalPurchases;

    public Customer(String id, String customId, String name, String phone, int loyaltyPoints, double totalPurchases) {
        this.id = id;
        this.customId = customId;
        this.name = name;
        this.phone = phone;
        this.loyaltyPoints = loyaltyPoints;
        this.totalPurchases = totalPurchases;
    }

    public static String generateDisplayId() {
        return "CUST-" + (int) (Math.random() * 1000);
    }

    public void addPurchase(double amount) {
        this.totalPurchases += amount;
        this.loyaltyPoints += (int) (amount * 0.01);
    }

    @Override
    public String toString() {
        return customId + " | " + name;
    }
}
