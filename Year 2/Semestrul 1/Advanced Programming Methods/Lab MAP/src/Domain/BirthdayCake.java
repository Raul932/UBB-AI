package Domain;

import java.io.Serializable;

public class BirthdayCake implements Identifiable<String>, Serializable {
    private String id;

    private int size;
    private String flavour;
    private int candles;
    private double price;

    public BirthdayCake(String id, int size, String flavour, int candles, double price) {
        this.id = id;
        this.size = size;
        this.flavour = flavour;
        this.candles = candles;
        this.price = price;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) { this.id = id; }

    public int getSize() {
        return size;
    }

    public String getFlavour() {
        return flavour;
    }

    public int getCandles() {
        return candles;
    }

    public double getPrice() {
        return price;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public void setCandles(int candles) {
        this.candles = candles;
    }

    public void setPrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("Price cannot be negative.");
        this.price = price;
    }

    @Override
    public String toString() {
        return "Birthday Cake " + id +
                "; size: " + size +
                "; " + flavour + " flavour; " +
                candles + " candles; " +
                "price: " + price;
    }
}
