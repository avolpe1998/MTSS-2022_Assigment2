////////////////////////////////////////////////////////////////////
// Andrea Volpe 2021904
// Riccardo Contin 1225416
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.model;

public class EItem {
    private EItemType itemType;
    private String name;
    private double price;

    public EItem(EItemType itemType, String name, double price) 
        throws IllegalArgumentException {
        this.itemType = itemType;
        this.name = name;

        if (price < 0) {throw new IllegalArgumentException();}
        this.price = price;
    }

    public EItemType getItemType() {
        return itemType;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
