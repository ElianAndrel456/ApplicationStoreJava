package connection;

import java.sql.Connection;
import java.sql.DriverManager;

import interfaces.IConexion;

import java.sql.SQLException;

public class Connections implements IConexion {
    Connection con=null;
    
    public Connection cox() {
        try {
             con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE, USER,
                    PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error:" + e);
        }
        return con;
    }

    /* public abstract void getInfo() {
    };

    public abstract void insetInfo() {
    };

    public abstract void updateInfo() {
    };

    public abstract void deleteInfo() {
    }; */

    /*
     * public static void main(String[] args) {
     * Connections cn = new Connections();
     * Statement st;
     * ResultSet rs;
     * try {
     * st = cn.con.createStatement();
     * rs = st.executeQuery("select * from user");
     * while (rs.next()) {
     * System.out.println(rs.getInt("id") + " " + rs.getString("username") + " " +
     * rs.getString("password"));
     * }
     * cn.con.close();
     * } catch (SQLException e) {
     * }
     * 
     * }
     */
}
