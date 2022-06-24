package models;

public class Customer extends Person {
    private int id_customer;
    private String username_customer, password_customer;

    public Customer() {

    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getUsername_customer() {
        return username_customer;
    }

    public void setUsername_customer(String username_customer) {
        this.username_customer = username_customer;
    }

    public String getPassword_customer() {
        return password_customer;
    }

    public void setPassword_customer(String password_customer) {
        this.password_customer = password_customer;
    }

    public Object[] registroClient() {
        Object[] fila = { id_customer, getFirstName(), getEdad(), getDni(), getEmail(), getDireccion() };
        return fila;
    }

    public Object[] registroTrabajador() {
        Object[] fila = { id_customer, getFirstName(), getUsername_customer() };
        return fila;
    }

}
