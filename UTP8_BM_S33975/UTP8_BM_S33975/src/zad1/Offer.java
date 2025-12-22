package zad1;

import java.util.*;

public class Offer {
    public Locale locale;
    public String country;
    public Date dateStart;
    public Date dateEnd;
    public String place;
    public double price;
    public String currency;

    public Offer(Locale locale, String country, Date dateStart, Date dateEnd, String place, double price, String currency) {
        this.locale = locale;
        this.country = country;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.place = place;
        this.price = price;
        this.currency = currency;
    }
}
