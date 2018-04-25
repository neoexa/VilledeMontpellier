package neoexa.com.VilledeMontpellier.Model;

import java.util.ArrayList;

public class User {
    String username ;
    String email;
    String telephone ;
    String address;
    ArrayList<Shop> favorites;

    public User(String username, String email, String telephone, String address, ArrayList<Shop> favorites) {
        this.username = username;
        this.email = email;
        this.telephone = telephone;
        this.address = address;
        this.favorites= favorites;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
