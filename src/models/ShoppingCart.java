package models;

import java.sql.Date;
import java.util.ArrayList;

public class ShoppingCart {

    ArrayList<Product> productos = new ArrayList<>();

    public ShoppingCart() {
    }

    public ArrayList<Product> getProductos() {
        return productos;
    }

    public void addProductos(Product p) {
        productos.add(p);
    }

    public void removeProductos(int i) {
        productos.remove(i);
    }

    public void setProductos(ArrayList<Product> productos) {
        this.productos = productos;
    }

    public Object[] registro(String pedidoP,String nombre, int cantidad, String talla, Date fechaP,String estadoP) {

        Object[] fila = { pedidoP, nombre, cantidad, talla, estadoP, fechaP };
        return fila;
    }
    public Object[] registro2(int idUser,String pedidoP,String nombre, int cantidad, String talla, Date fechaP,String estadoP) {

        Object[] fila = { idUser,pedidoP, nombre, cantidad, talla, estadoP, fechaP };
        return fila;
    }
}
