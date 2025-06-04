package Model;

import java.util.Date;

public class User {
    int id;
    String login;
    String email;
    String password;
    String first_name;
    String last_name;
    String phone;
    Date dateBirth;
    public User(String login, String email, String password){
        this.login = login;
        this.email = email;
        this.password = password;
    }
    public User(String login, String email, String password, String first_name,
                String last_name, String phone, Date dateBirth){
        this.login = login;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.dateBirth = dateBirth;
    }
    public String getLogin(){
        return login;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getFirstName(){
        return first_name;
    }
    public String getLastName(){
        return last_name;
    }
    public String getPhone(){
        return phone;
    }
    public Date getDateBirth(){
        return dateBirth;
    }
}
