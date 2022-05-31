package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;



import connection.Connections;

public class Qry {
    private static Connections cn = new Connections();
    Connection cx = cn.cox();
    private static Statement st;
    private static ResultSet rs;
    private Customer cliente = new Customer();
    String username;
    String password;

    public Qry() {
    }

    /*
     * public Customer cliente(int id, String user, int dni) {
     * Customer c = new Customer();
     * c.setId_customer(id);
     * c.setUsername_customer(user);
     * c.setDni(dni);
     * return c;
     * }
     */
    public void setCliente(Customer c) {
        cliente = c;
    }

    public Customer getCliente() {
        return cliente;
    }

    public String nameProducto(int id){
        String sql = "Select * from `productos` where id='"+id+"';";
        String name="";
        try {
            st = cx.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
                name= rs.getString("nombre");
            }

        } catch (Exception e) {
            //TODO: handle exception
        }
        return name;
    }

    public double precioProducto(int id){
        String sql = "Select * from `productos` where id='"+id+"';";
        double price=00.00; 
        try {
            st = cx.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
                price= rs.getDouble("precio");
            }

        } catch (Exception e) {
            //TODO: handle exception
        }
        return price;
    }

    public ArrayList<ShoppingCart> tableHistory(int idCliente) {

        String sql="SELECT * FROM `carrito` where ;";

        ArrayList<ShoppingCart> listTable = new ArrayList<>();
        try {
            st = cx.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Product p = new Product();
                p.setCodigo(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setCategoria(rs.getString("categoria"));
                /* listTable.add(p); */
            }

        } catch (Exception e) {

        }
        return listTable;
    }

    public ArrayList<Product> tableDashboard(String categoria) {

        String sql ;
        String cat=categoria;
        if (cat !="todos") {
            sql = "SELECT * FROM `productos` where categoria='"+cat+"';";
        } else {
            sql = "SELECT * FROM `productos`;";
        }

        ArrayList<Product> listTable = new ArrayList<>();
        try {
            st = cx.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Product p = new Product();
                p.setCodigo(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setCategoria(rs.getString("categoria"));
                listTable.add(p);
            }

        } catch (Exception e) {

        }
        return listTable;
    }

    public Boolean getAccount(String user, String email) {
        boolean bo = true;
        int res = 0;
        String sql = "SELECT * FROM `cliente` WHERE username='" + user + "' AND email='" + email + "';";
        try {
            st = cx.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                res = 1;
                if (res == 1) {
                    String mssg = "Your account is : \n" +
                            "User" + rs.getString("username") + "\n" +
                            "Password" + rs.getString("password");
                    ;
                    JOptionPane.showMessageDialog(null, mssg);
                    bo = false;
                }

            } else {
                JOptionPane.showMessageDialog(null, "Error Failed in the params");
                bo = true;
            }
        } catch (Exception e) {
        }
        return bo;
    }

    public void insetCartPay(int idUser,int idProducto,String talla,int cantidad){
        String sql = "insert into carrito (id_usuario,id_producto,talla,cantidad,estado) values(?,?,?,?,?)";
        try {
            PreparedStatement ps = cx.prepareStatement(sql);
            ps.setInt(1, idUser);
            ps.setInt(2, idProducto);
            ps.setString(3, talla);
            ps.setInt(4, cantidad);
            ps.setString(5, "pagado");
            ps.executeUpdate();
         } catch (Exception e) {
            //TODO: handle exception
        }

    }

    public boolean insertUser(String name, String dni, String user, String pass, String email) {
        String sql = "insert into cliente (nombre_cliente,dni,username,password,email) values (?,?,?,?,?)";
        boolean isNumeric = dni.matches("[+-]?\\d*(\\.\\d+)?");
        boolean bo = false;
        try {
            if (name.length() > 0 && (isNumeric) && user.length() > 0 && pass.length() > 0
                    && email.length() > 0) {

                PreparedStatement ps = cx.prepareStatement(sql);

                ps.setString(1, name);
                ps.setInt(2, Integer.parseInt(dni));
                ps.setString(3, user);
                ps.setString(4, pass);
                ps.setString(5, email);

                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Se registro correctamente el usuario");
                bo = true;

            } else {
                JOptionPane.showMessageDialog(null, "Ocurrio un error al registrar el usuario");
                bo = false;
            }

        } catch (Exception e) {
        }
        return bo;
    }

    public int validarUser(String user, String pass) {
        int res = 0;
        String sql = "SELECT * FROM `cliente` WHERE username='" + user + "' AND password='" + pass + "';";

        try {

            st = cn.cox().createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                res = 1;
                if (res == 1) {
                    JOptionPane.showMessageDialog(null, "Acceso");
                    Customer c = new Customer();
                    c.setFirstName(rs.getString("nombre_cliente"));
                    c.setId_customer(rs.getInt("id_cliente"));
                    setCliente(c);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Error Acceso Denegado");
            }
        } catch (Exception e) {
        }
        return res;

    }

    public int validarUserE(String user, String pass) {
        int res = 0;
        String sql = "SELECT * FROM `trabajadores` WHERE username='" + user + "' AND password='" + pass + "';";

        try {

            st = cn.cox().createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                res = 1;
                if (res == 1) {
                    JOptionPane.showMessageDialog(null, "Acceso");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Error Acceso Denegado");
            }
        } catch (Exception e) {
        }
        return res;

    }

}

/*
 * public String getSelectLogin(String user, String pass) {
 * try {
 * st = cx.createStatement();
 * rs = st.executeQuery(
 * "SELECT * FROM `user` WHERE username='" + user + "' AND password='" + pass +
 * "';");
 * 
 * while (rs.next()) {
 * System.out.println(rs.getString("username") + " " +
 * rs.getString("password"));
 * username = rs.getString("username");
 * password = rs.getString("password");
 * }
 * 
 * cn.cox().close();
 * } catch (Exception e) {
 * JOptionPane.showMessageDialog(null, e.getMessage());
 * }
 * if (username.length() > 0 && password.length() > 0) {
 * return "Se Inicio seccion correctamente";
 * } else {
 * return "Faliled";
 * }
 * }
 */
