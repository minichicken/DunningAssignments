package pe.kr.crasy.dunningassignments.database;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 *
 * Created by maybe on 16. 11. 24.
 */
@RealmClass
public class People extends RealmObject{
    private String  Email;
    private String  name;
    private int     age;
    private String  address;
    private String  phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
