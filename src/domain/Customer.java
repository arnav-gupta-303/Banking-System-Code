package domain;

import javax.xml.namespace.QName;

public class Customer {
    public Customer(String name, String email, String id) {
        this.name = name;
        Email = email;
        this.id = id;
    }

    private String name;
    private String Email;
    private String id;
}
