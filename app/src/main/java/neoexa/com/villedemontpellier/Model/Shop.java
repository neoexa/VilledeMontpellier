package neoexa.com.villedemontpellier.Model;


public class Shop {
    public enum shopCat {
        food,
        market,
        hotel,
        finance;
    }
    public String name;
    public String address;
    public String gps;
    public shopCat category ;


    public Shop (String n, String a, String g, shopCat s){
        this.name = n;
        this.address = a;
        this.gps = g;
        this.category = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return address;
    }

    public void setAdress(String adress) {
        this.address = adress;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public shopCat getCategory() {
        return category;
    }

    public void setShopType(shopCat category) {
        this.category = category;
    }

}