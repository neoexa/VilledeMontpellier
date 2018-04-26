package neoexa.com.VilledeMontpellier.Model;


public class Shop {
    public String userId;
    public String name;
    public String address;
    public String category;

    public Shop() {
    }

    public Shop (String id, String n, String a, String s){
        this.userId = id;
        this.name = n;
        this.address = a;
        this.category = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}