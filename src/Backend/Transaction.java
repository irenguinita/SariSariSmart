package Backend;

import java.util.Date;
import java.util.List;

public class Transaction {
    public String id;
    public Date date;
    public List<CartItem> items;
    public double total;
    public String customerId, customerName;

    public Transaction(String id, Date date, List<CartItem> items, double total, String customerId, String customerName) {
        this.id = id;
        this.date = date;
        this.items = items;
        this.total = total;
        this.customerId = customerId;
        this.customerName = customerName;
    }
}
