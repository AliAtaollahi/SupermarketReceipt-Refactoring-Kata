package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Receipt {

    private final List<ReceiptItem> items = new ArrayList<>();
    private final List<Discount> discounts = new ArrayList<>();

    public double getTotalPrice() {
        double total = 0.0;
        for (ReceiptItem item : items) {
            total += item.getTotalPrice();
        }
        for (Discount discount : discounts) {
            total += discount.getDiscountAmount();
        }
        return total;
    }

    private String presentTotal() {
        String name = "Total: ";
        String value = presentPrice(this.getTotalPrice());
        return formatLineWithWhitespace(name, value);
    }

    public void addProduct(Product p, double quantity, double price) {
        items.add(new ReceiptItem(p, quantity, price));
    }

    public List<ReceiptItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addDiscount(Discount discount) {
        discounts.add(discount);
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }
}
