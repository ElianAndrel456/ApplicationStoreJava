package models;

import java.util.ArrayList;

public class ShoppingCart {
    ArrayList<Product> productos= new ArrayList<>();
    public ShoppingCart() {
    }

    public ArrayList<Product> getProductos() {
        return productos;
    }
    public void addProductos(Product p){
        productos.add(p);
    }
    public void removeProductos(int i){
        productos.remove(i);
    }
    public void setProductos(ArrayList<Product> productos) {
        this.productos = productos;
    }

      
}
