package dojo.supermarket.model;

import java.util.HashMap;
import java.util.Map;

public class SupermarketCatalog {
    private Map<String, Product> products = new HashMap<>();
    private Map<String, Double> prices = new HashMap<>();

    public void addProduct(Product product, double price) {
        this.products.put(product.getName(), product);
        this.prices.put(product.getName(), price);
    }

    public double getUnitPrice(Product p) {
        return this.prices.get(p.getName());
    }
}