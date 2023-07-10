package dojo.supermarket.model;

public class Offer {

    SpecialOfferType offerType;
    private final Product product;
    double argument;

    public Offer(SpecialOfferType offerType, Product product, double argument) {
        this.offerType = offerType;
        this.argument = argument;
        this.product = product;
    }

    public Discount calc_discount_and_get(double quantity, double unitPrice, Product p) {
        
        if (this.offerType == SpecialOfferType.TEN_PERCENT_DISCOUNT) {
            return new Discount(p, this.argument + "% off", -quantity * unitPrice * this.argument / 100.0);
        }
        else if (this.offerType == SpecialOfferType.TEN_PERCENT_DISCOUNT) {
            return calc(p, 3, 2, unitPrice, (int)quantity, unitPrice);
        }
        else if (this.offerType == SpecialOfferType.TWO_FOR_AMOUNT) {
            return calc(p, 2, this.argument, 1, (int)quantity, unitPrice);
        }
        else if (this.offerType == SpecialOfferType.FIVE_FOR_AMOUNT) {
            return calc(p, 5, this.argument, 1, (int)quantity, unitPrice);
        }

        return null;
    }

    Discount calc(Product p, int numOfBoughtProducts, double offer_amount, double price_coefficient, int quantity, double unitPrice) {

            int ProductsRatio = quantity / numOfBoughtProducts;
            if (quantity >= numOfBoughtProducts) {
                double discountTotal = unitPrice * quantity - (offer_amount * ProductsRatio * price_coefficient + quantity % numOfBoughtProducts * unitPrice);
                return new Discount(p, numOfBoughtProducts  + " for " + offer_amount, -discountTotal);
            }
            return null;
    }


    Product getProduct() {
        return product;
    }
}
