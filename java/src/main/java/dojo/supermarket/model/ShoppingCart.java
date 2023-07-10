package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    private final Map<Product, Double> productQuantities = new HashMap<>();

    List<ProductQuantity> getItems() {
        return Collections.unmodifiableList(items);
    }

    void addItem(Product product) {
        addItemQuantity(product, 1.0);
    }

    Map<Product, Double> productQuantities() {
        return Collections.unmodifiableMap(productQuantities);
    }

    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product p: productQuantities().keySet()) {
            double quantity = productQuantities.get(p);
            if (offers.containsKey(p)) {
                Offer offer = offers.get(p);
                double unitPrice = catalog.getUnitPrice(p);
                int quantityAsInt = (int) quantity;
                Discount discount = null;
                int numOfBoughtProducts = 1;
                int boughtProductsRatio = quantityAsInt / numOfBoughtProducts;
                
                if (offer.offerType == SpecialOfferType.TEN_PERCENT_DISCOUNT) {
                    discount = new Discount(p, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100.0);
                }
                else {
                    double offer_amount = 0;
                    double price_coefficient = 1;
                    if (offer.offerType == SpecialOfferType.THREE_FOR_TWO){
                        numOfBoughtProducts  = 3;
                        offer_amount = 2;
                        price_coefficient = unitPrice;
                    }
                    else if (offer.offerType == SpecialOfferType.TWO_FOR_AMOUNT) {
                        numOfBoughtProducts = 2;
                        offer_amount = offer.argument;
                    }
                    else if (offer.offerType == SpecialOfferType.FIVE_FOR_AMOUNT) {
                        numOfBoughtProducts  = 5;
                        offer_amount = offer.argument;
                    }
                    if (quantityAsInt >= numOfBoughtProducts) {
                        double discountTotal = unitPrice * quantity - (offer_amount * boughtProductsRatio * price_coefficient + quantityAsInt % numOfBoughtProducts * unitPrice);
                        discount = new Discount(p, numOfBoughtProducts  + " for " + offer_amount, -discountTotal);
                    }
                }

                if (discount != null)
                    receipt.addDiscount(discount);
            }

        }
    }
}
