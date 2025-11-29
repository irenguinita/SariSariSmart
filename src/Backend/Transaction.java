package Backend;

import java.util.Date;
import java.util.List;

class Transaction {
    String id;
    Date date;
    List<CartItem> items;
    double total;
    String customerId, customerName;

    public Transaction(String id, Date date, List<CartItem> items, double total, String customerId, String customerName) {
        this.id = id;
        this.date = date;
        this.items = items;
        this.total = total;
        this.customerId = customerId;
        this.customerName = customerName;
    }
}
