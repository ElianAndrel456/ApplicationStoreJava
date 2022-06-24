package models;

import java.sql.Connection;
import java.sql.Date;
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

    public void setCliente(Customer c) {
        cliente = c;
    }

    public Customer getCliente() {
        return cliente;
    }

    public void updateImageClient(byte[] img) {
        String sql = "UPDATE cliente SET imagen=? where id_cliente=" + getCliente().getId_customer();
        try {
            PreparedStatement ps = cx.prepareStatement(sql);
            ps.setBytes(1, img);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error : ");
            System.out.println(e);
        }
    }

    public void agregarProducto(int id, String name, String description, double price, String cat, byte[] img) {
        String sql = "INSERT INTO `productos` (`id`, `nombre`, `descripcion`, `precio`, `categoria`, `imagen`) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cx.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, description);
            ps.setDouble(4, price);
            ps.setString(5, cat);
            ps.setBytes(6, img);
            ps.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void deleteProducto(int id) {
        String sql = "DELETE FROM productos WHERE id =?";
        try {
            PreparedStatement ps = cx.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateProducts(int id, String name, String description, double price, String cat) {
        String sql = "UPDATE productos SET id=?, nombre =?,descripcion=?,precio=?,categoria=? where id=" + id;
        try {
            PreparedStatement ps = cx.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, description);
            ps.setDouble(4, price);
            ps.setString(5, cat);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateProductsWithImage(int id, String name, String description, double price, String cat, byte[] img) {
        String sql = "UPDATE productos SET id=?, nombre =?,descripcion=?,precio=?,categoria=? ,imagen =? where id="
                + id;
        try {
            PreparedStatement ps = cx.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, description);
            ps.setDouble(4, price);
            ps.setString(5, cat);
            ps.setBytes(6, img);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public byte[] getImageId(int id) {
        String sql = "select * from productos where id=" + id;
        byte[] img = null;
        try {
            st = cx.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                img = rs.getBytes("imagen");

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return img;
    }

    public byte[] getImageIdClient() {
        String sql = "select * from cliente where id_cliente=" + getCliente().getId_customer();
        byte[] img = null;
        try {
            st = cx.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                img = rs.getBytes("imagen");

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println("Result :" + img);
        return img;
    }

    public String nameProducto(int id) {
        String sql = "Select * from `productos` where id='" + id + "';";
        String name = "";
        try {
            st = cx.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                name = rs.getString("nombre");
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return name;
    }

    public double precioProducto(int id) {
        String sql = "Select * from `productos` where id='" + id + "';";
        double price = 00.00;
        try {
            st = cx.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                price = rs.getDouble("precio");
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return price;
    }

    public void updateDashE(String pedido, String state) {
        System.out.println("hola" + pedido);
        System.out.println(state);
        String sql = "UPDATE carrito SET estado=? where pedido='" + pedido + "'";
        try {
            PreparedStatement ps = cx.prepareStatement(sql);
            ps.setString(1, state);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public ShoppingCart tableDashE(String state) {
        String sql;
        if (state == "todos") {
            sql = "SELECT C.id_usuario, C.pedido, C.fecha, C.estado, C.talla, C.cantidad,P.nombre " +
                    "FROM carrito C " +
                    " INNER JOIN productos P " +
                    "ON C.id_producto = P.id ";

        } else {
            sql = "SELECT C.id_usuario, C.pedido, C.fecha, C.estado, C.talla, C.cantidad,P.nombre " +
                    "FROM carrito C " +
                    " INNER JOIN productos P " +
                    "ON C.id_producto = P.id " +
                    "where C.estado='" + state + "'";
        }

        ShoppingCart sc = new ShoppingCart();
        try {
            st = cx.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Product p = new Product();
                p.setIdUser(rs.getInt("C.id_usuario"));
                p.setNombre(rs.getString("P.nombre"));
                p.setTalla(rs.getString("C.talla"));
                p.setCantidad(rs.getInt("C.cantidad"));
                p.setFecha(rs.getDate("C.fecha"));
                p.setEstado(rs.getString("C.estado"));
                p.setPedido(rs.getString("C.pedido"));
                sc.addProductos(p);

            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return sc;
    }

    public ShoppingCart tableHistory(int idCliente) {

        String sql = "SELECT C.pedido, C.fecha, C.estado, C.talla, C.cantidad,P.nombre " +
                "FROM carrito C " +
                " INNER JOIN productos P " +
                "ON C.id_producto = P.id " +
                "WHERE C.id_usuario =" + idCliente;

        ShoppingCart sc = new ShoppingCart();
        try {
            st = cx.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Product p = new Product();
                p.setNombre(rs.getString("P.nombre"));
                p.setTalla(rs.getString("C.talla"));
                p.setCantidad(rs.getInt("C.cantidad"));
                p.setFecha(rs.getDate("C.fecha"));
                p.setEstado(rs.getString("C.estado"));
                p.setPedido(rs.getString("C.pedido"));
                sc.addProductos(p);

            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return sc;
    }

    public ArrayList<Customer> tableClients() {
        String sql = "select * from cliente;";
        ArrayList<Customer> listTable = new ArrayList<>();
        try {
            st = cx.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Customer c = new Customer();
                c.setId_customer(rs.getInt("id_cliente"));
                c.setFirstName(rs.getString("nombre_cliente"));
                c.setDni(rs.getInt("dni"));
                c.setEdad(rs.getInt("edad"));
                c.setEmail(rs.getString("email"));
                c.setDireccion("direccion");
                listTable.add(c);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return listTable;
    }

    public ArrayList<Customer> tableEmployees() {
        String sql = "select * from trabajadores;";
        ArrayList<Customer> listTable = new ArrayList<>();
        try {
            st = cx.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Customer c = new Customer();
                c.setId_customer(rs.getInt("id"));
                c.setFirstName(rs.getString("nombre"));
                c.setUsername_customer(rs.getString("username"));
                listTable.add(c);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return listTable;
    }

    public ArrayList<Product> tableDashboard(String categoria) {

        String sql;
        String cat = categoria;
        if (cat != "todos") {
            sql = "SELECT * FROM `productos` where categoria='" + cat + "';";
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

    public void insetCartPay(int idUser, int idProducto, String talla, int cantidad, Date fecha, String bol) {
        String sql = "insert into carrito (id_usuario,id_producto,talla,cantidad,estado,fecha,pedido) values(?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cx.prepareStatement(sql);
            ps.setInt(1, idUser);
            ps.setInt(2, idProducto);
            ps.setString(3, talla);
            ps.setInt(4, cantidad);
            ps.setString(5, "pagado");
            ps.setDate(6, fecha);
            ps.setString(7, bol);
            ps.executeUpdate();
        } catch (Exception e) {
        }

    }

    public boolean insertUser(String name, String dni, String user, String pass, String email, String direccion,
            int edad) {
        String sql = "insert into cliente (nombre_cliente,dni,username,password,email,direccion,edad) values (?,?,?,?,?,?,?)";
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
                ps.setString(6, direccion);
                ps.setInt(7, edad);

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
                    c.setDni(rs.getInt("dni"));
                    c.setEmail(rs.getString("email"));
                    c.setDireccion(rs.getString("direccion"));
                    c.setEdad(rs.getInt("edad"));
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
