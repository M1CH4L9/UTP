/**
 *
 *  @author Berlak Michał S33975
 *
 */

package zad1;


import java.text.DecimalFormat;


public class Purchase {
    private final String id;
    private final String name;
    private final String product;
    private final double price;
    private final double qty;

    public Purchase(String line){
        String[] parts = line.split(";", -1);
        if(parts.length != 5){
            throw new IllegalArgumentException("Bad line: " + line);
        }
        this.id = parts[0];
        this.name = parts[1].trim();
        this.product = parts[2].trim();
        this.price = Double.parseDouble(parts[3].trim());
        this.qty = Double.parseDouble(parts[4].trim());
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getProduct() {
        return product;
    }
    public double getPrice() {
        return price;
    }
    public double getQty() {
        return qty;
    }
    public double getCost(){
        return price * qty;
    }

    @Override
    public String toString() {
        return String.join(";", id, name, product, String.valueOf(price), String.valueOf(qty));
    }

    public String toStringWithCost(){
        return toString() + " (koszt: " + String.valueOf(getCost()) + ")";
    }
}
