package models;

public class Product {
    private int codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private String categoria;
    private String talla;
    private int cantidad;

    public Product(){}
    public String getTalla() {
        return talla;
    }
    public void setTalla(String talla) {
        this.talla = talla;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Object[] registro(){

        Object[] fila={codigo,nombre,descripcion,categoria,precio};
        return fila;
    }
    public Object[] registro2(){

        Object[] fila={codigo,nombre,cantidad,talla,precio};
        return fila;
    }

    public String imprimirDatos(){
        return "ID"+codigo+" Nombre : "+nombre+" Categoria "+categoria+" Cantidad "+cantidad;
    }


}
