package Backend;

class CartItem {
    Product product;
    int quantity;
    double unitPrice;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = product.price;
    }

    public double getTotal() {
        return quantity * unitPrice;
    }
}
