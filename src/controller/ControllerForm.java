package controller;

import view.LoginJFrame;
import view.PayJFrame;
import view.RegisterJFrame;
import view.ShoppingCartJFrame;
import view.DashboardControllUser;
import view.DashboardUser;
import view.ForgotJFrame;
import view.HistoryShoppingJFrame;
import view.LoginEmployeeJFrame;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableModel;

import models.Customer;
import models.Product;
import models.Qry;
import models.ShoppingCart;

public class ControllerForm implements ActionListener, MouseInputListener {
    Qry q = new Qry();
    ShoppingCart sc = new ShoppingCart();
    LoginJFrame formLogin;
    ForgotJFrame formAccount;
    RegisterJFrame formRegister;
    DashboardUser formDashboard;
    DashboardControllUser formDashboardControllE;
    LoginEmployeeJFrame formLoginE;
    PayJFrame formPay;
    HistoryShoppingJFrame formHistory;
    ShoppingCartJFrame formShoppingCart;

    public void controllerShopping(ShoppingCartJFrame fshoppingcart) {
        this.formShoppingCart = fshoppingcart;
        formShoppingCart.setVisible(false);
        formShoppingCart.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Id");
        tableModel.addColumn("Producto");
        tableModel.addColumn("Cantidad");
        tableModel.addColumn("Talla");
        tableModel.addColumn("Precio");
        formShoppingCart.tableShoppingCart.setModel(tableModel);
        formShoppingCart.btnDeleteProduct.addActionListener(this);
    }

    public void controllerHistory(HistoryShoppingJFrame fhistory) {
        this.formHistory = fhistory;
        formHistory.setTitle("Historial de compras");
        formHistory.setVisible(false);
        formHistory.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Id Pedido");
        tableModel.addColumn("Producto");
        tableModel.addColumn("Cantidad");
        tableModel.addColumn("Talla");
        tableModel.addColumn("Estado");
        formHistory.tableHistory.setModel(tableModel);
       /*  ArrayList<Product> list = q.tableHistory();
        Iterator<Product> iterator = list.iterator();
        while (iterator.hasNext()) {
            Product p = iterator.next();
            tableModel.addRow(p.registro());
        } */
    }

    public void controllerPay(PayJFrame fpay) {
        this.formPay = fpay;
        formPay.setTitle("Pago");
        formPay.setVisible(false);
        formPay.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        formPay.btnPay.addActionListener(this);

    }

    public void controllerLoginE(LoginEmployeeJFrame floginE) {
        this.formLoginE = floginE;
        formLoginE.setTitle("Login para Empleados");
        formLoginE.setVisible(false);
        formLoginE.btnBackLogin.addActionListener(this);
        formLoginE.LoginjButton.addActionListener(this);
        formLoginE.UsernameTextField.addMouseListener(this);
        formLoginE.jPasswordField1.addMouseListener(this);
    }

    public void controllerDashboardE(DashboardControllUser fdashControllerE) {
        this.formDashboardControllE = fdashControllerE;
        formDashboardControllE.setTitle("Main Dashboard");
        formDashboardControllE.setVisible(false);
        formDashboardControllE.setLocationRelativeTo(null);
    }

    public void controllerRegister(RegisterJFrame fregister) {
        this.formRegister = fregister;
        formRegister.setTitle("Registros");
        formRegister.setVisible(false);
        formRegister.setLocationRelativeTo(null);
        this.formRegister.btnRegister.addActionListener(this);
        formRegister.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    }

    public void controllerLogin(LoginJFrame flogin) {
        this.formLogin = flogin;
        formLogin.setVisible(true);
        formLogin.setTitle("Login");
        this.formLogin.LoginjButton.addActionListener(this);
        this.formLogin.btnAccount.addActionListener(this);
        this.formLogin.btnRegisterAccount.addActionListener(this);
        this.formLogin.btnDashboardEmpleado.addActionListener(this);
    }

    public void controllerAccount(ForgotJFrame fAccount) {
        formAccount = new ForgotJFrame();
        formAccount.setVisible(false);
        formAccount.setTitle("Account");
        this.formAccount.AccountjButton.addActionListener(this);
        this.formAccount.btnBack.addActionListener(this);
    }

    public void controllerDashboard(DashboardUser fdashboard) {
        this.formDashboard = fdashboard;
        formDashboard.setVisible(false);
        formDashboard.setTitle("Principal");
        formDashboard.setLocationRelativeTo(null);
        formDashboard.btnPay.addActionListener(this);
        formDashboard.btnFilter.addActionListener(this);
        formDashboard.btnAddCart.addActionListener(this);
        ImageIcon image = new ImageIcon("src/assets/usericon.png");
        ImageIcon icono = new ImageIcon(image.getImage().getScaledInstance(formDashboard.lblImageUser.getWidth(),
                formDashboard.lblImageUser.getHeight(), Image.SCALE_DEFAULT));
        formDashboard.lblImageUser.setIcon(icono);
        formDashboard.lblImageUser.repaint();

        formDashboard.lblExit.addMouseListener(this);
        formDashboard.lblViewCart.addMouseListener(this);
        formDashboard.lblHistory.addMouseListener(this);

        String o = formDashboard.boxFilter.getSelectedItem().toString();

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Id");
        tableModel.addColumn("Producto");
        tableModel.addColumn("Descripcion");
        tableModel.addColumn("Categoria");
        tableModel.addColumn("Precio");
        formDashboard.tableProducts.setModel(tableModel);
        ArrayList<Product> list = q.tableDashboard(o);
        Iterator<Product> iterator = list.iterator();
        while (iterator.hasNext()) {
            Product p = iterator.next();
            tableModel.addRow(p.registro());
        }

        formDashboard.tableProducts.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == formLogin.LoginjButton) {
            String user = formLogin.UsernameTextField.getText();
            String pass = String.valueOf(formLogin.jPasswordField1.getPassword());

            int res = q.validarUser(user, pass);
            if (res == 1) {
                formDashboard.lblID.setText("ID : " + q.getCliente().getId_customer());
                formDashboard.lblUsername.setText("Names : " + q.getCliente().getFirstName());
                formLogin.setVisible(false);
                formDashboard.setVisible(true);
            }
        }
        if (e.getSource() == formLogin.btnAccount) {

            this.formLogin.setVisible(false);
            this.formAccount.setVisible(true);

        }
        if (e.getSource() == formLogin.btnRegisterAccount) {
            formRegister.setVisible(true);
        }
        if (e.getSource() == formLogin.btnDashboardEmpleado) {
            formLogin.setVisible(false);
            formLoginE.setVisible(true);
        }

        if (e.getSource() == formLoginE.btnBackLogin) {

            this.formLoginE.setVisible(false);
            this.formLogin.setVisible(true);
        }
        if (e.getSource() == formLoginE.LoginjButton) {
            String user = formLoginE.UsernameTextField.getText();
            String password = String.valueOf(formLoginE.jPasswordField1.getPassword());

            int res = q.validarUserE(user, password);
            if (res == 1) {
                formLoginE.setVisible(false);
                formDashboardControllE.setVisible(true);
            }
        }

        if (e.getSource() == formAccount.AccountjButton) {
            String user = formAccount.UsernameTextField1.getText();
            String email = formAccount.txtCorreo.getText();
            Boolean c = q.getAccount(user, email);

            this.formAccount.setVisible(c);
            this.formLogin.setVisible(!c);
        }
        if (e.getSource() == formAccount.btnBack) {

            this.formAccount.setVisible(false);
            this.formLogin.setVisible(true);
        }

        if (e.getSource() == formRegister.btnRegister) {

            String name = formRegister.txtName.getText();
            String dni = formRegister.txtDNI.getText();
            String user = formRegister.txtUser.getText();
            String pass = formRegister.txtPassword.getText();
            String email = formRegister.txtEmail.getText();

            boolean res = q.insertUser(name, dni, user, pass, email);

            if (res) {

                formRegister.setVisible(false);
            }

        }

        if (e.getSource() == formDashboard.btnPay) {
            formPay.setVisible(true);
        }
        if (e.getSource() == formDashboard.btnFilter) {
            String o = formDashboard.boxFilter.getSelectedItem().toString().toLowerCase();
            System.out.println(o);
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Id");
            tableModel.addColumn("Producto");
            tableModel.addColumn("Descripcion");
            tableModel.addColumn("Categoria");
            tableModel.addColumn("Precio");
            formDashboard.tableProducts.setModel(tableModel);
            ArrayList<Product> list = q.tableDashboard(o);
            Iterator<Product> iterator = list.iterator();
            while (iterator.hasNext()) {
                Product p = iterator.next();
                tableModel.addRow(p.registro());
                System.out.println(p.imprimirDatos());
            }
        }
        if (e.getSource() == formDashboard.btnAddCart) {
            double totalPrice = 00.00;
            int idP = Integer.parseInt(formDashboard.txtId.getText());
            String talla = formDashboard.boxTalla.getSelectedItem().toString();
            int cantidad = Integer.parseInt(formDashboard.txtCantidad.getText());
            double precio = q.precioProducto(idP);
            String nombre = q.nameProducto(idP);

            Product p = new Product();
            p.setCodigo(idP);
            p.setNombre(nombre);
            p.setTalla(talla);
            p.setCantidad(cantidad);
            p.setPrecio(precio);

            sc.addProductos(p);
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Id");
            tableModel.addColumn("Producto");
            tableModel.addColumn("Cantidad");
            tableModel.addColumn("Talla");
            tableModel.addColumn("Precio");
            formShoppingCart.tableShoppingCart.setModel(tableModel);

            ArrayList<Product> list = sc.getProductos();
            Iterator<Product> iterator = list.iterator();
            while (iterator.hasNext()) {
                Product pr = iterator.next();
                tableModel.addRow(pr.registro2());
                totalPrice += pr.getPrecio()*pr.getCantidad();
            }

            formShoppingCart.lblPrecio.setText("" + totalPrice);

        }

        if (e.getSource() == formPay.btnPay) {
            boolean b = formPay.txtTarjeta.getText().matches("^3[47][0-9]{13}$");
            int cvv = Integer.parseInt(formPay.txtCVV.getText());
            int idUser=q.getCliente().getId_customer();
            //q.insetCartPay(1, 1, "40", 5);
            if (b && (cvv > 99 && cvv < 1000)) {
                ArrayList<Product> list = sc.getProductos();
                Iterator<Product> iterator = list.iterator();
                while (iterator.hasNext()) {
                Product pr = iterator.next();
                int idProducto=pr.getCodigo();
                String talla=pr.getTalla();
                int cantidad=pr.getCantidad();
                q.insetCartPay(idUser, idProducto, talla, cantidad);
            }
                JOptionPane.showMessageDialog(null, "Datos Correctos");
            } else {
                JOptionPane.showMessageDialog(null, "Error al Ingresar Datos");
            }
        }

        if (e.getSource() == formShoppingCart.btnDeleteProduct) {
            int index = formShoppingCart.tableShoppingCart.getSelectedRow();
            double totalPrice = 00.00;
            sc.removeProductos(index);

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Id");
            tableModel.addColumn("Producto");
            tableModel.addColumn("Cantidad");
            tableModel.addColumn("Talla");
            tableModel.addColumn("Precio");
            formShoppingCart.tableShoppingCart.setModel(tableModel);

            ArrayList<Product> list = sc.getProductos();
            Iterator<Product> iterator = list.iterator();
            while (iterator.hasNext()) {
                Product pr = iterator.next();
                tableModel.addRow(pr.registro2());
                totalPrice += pr.getPrecio();
            }

            formShoppingCart.lblPrecio.setText("" + totalPrice);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == formLoginE.UsernameTextField) {
            this.formLoginE.UsernameTextField.setText("");
            formLoginE.UsernameTextField.setForeground(Color.BLACK);
            String r = String.valueOf(formLoginE.jPasswordField1.getPassword());

            if (r.length() <= 0) {
                this.formLoginE.jPasswordField1.setText("*******************");
                formLoginE.jPasswordField1.setForeground(Color.GRAY);
            }
        }
        if (e.getSource() == formLoginE.jPasswordField1) {
            this.formLoginE.jPasswordField1.setText("");
            formLoginE.jPasswordField1.setForeground(Color.BLACK);
            String r = formLoginE.UsernameTextField.getText();

            if (r.length() <= 0) {
                this.formLoginE.UsernameTextField.setText("Ingrese su Nombre de Usuario");
                formLoginE.UsernameTextField.setForeground(Color.GRAY);
            }

        }
        if (e.getSource() == formDashboard.lblExit) {
            int c = JOptionPane.showConfirmDialog(null, "Desea salir");
            if (c == 0) {
                System.exit(0);
            }

        }
        if (e.getSource() == formDashboard.tableProducts) {
            int c = formDashboard.tableProducts.getSelectedRow();
            formDashboard.txtId.setText(formDashboard.tableProducts.getValueAt(c, 0).toString());
            formDashboard.txtAInfo.setText(formDashboard.tableProducts.getValueAt(c, 1).toString());

            String categoria = formDashboard.tableProducts.getValueAt(c, 3).toString();
 
            if (categoria.length() > 6) {
                System.out.println("Se presiono categoria zapato||pantalones");
                if (formDashboard.boxTalla.getItemCount() > 0) {
                    formDashboard.boxTalla.removeAllItems();
                }
                formDashboard.boxTalla.addItem("38");
                formDashboard.boxTalla.addItem("40");
                formDashboard.boxTalla.addItem("42");
            } else if (categoria.length() <= 6) {
                if (formDashboard.boxTalla.getItemCount() > 0) {
                    formDashboard.boxTalla.removeAllItems();
                }
                formDashboard.boxTalla.addItem("s");
                formDashboard.boxTalla.addItem("m");
                formDashboard.boxTalla.addItem("x");

            } else {
            }
        }

        if (e.getSource() == formDashboard.lblHistory) {
            formHistory.setVisible(true);
        }
        if (e.getSource() == formDashboard.lblViewCart) {
            formShoppingCart.setVisible(true);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == formDashboard.lblExit) {
            // formDashboard.lblExit.setFont(new java.awt.Font("FONTSPRING DEMO - Cera Round
            // Pro Black", 1, 18));
            formDashboard.lblExit.setForeground(Color.RED);
        }
        if (e.getSource() == formDashboard.lblHistory) {
            formDashboard.lblHistory.setForeground(Color.GREEN);
        }
        if (e.getSource() == formDashboard.lblViewCart) {
            formDashboard.lblViewCart.setForeground(Color.GREEN);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == formDashboard.lblExit) {
            formDashboard.lblExit.setForeground(Color.BLACK);
        }
        if (e.getSource() == formDashboard.lblHistory) {
            formDashboard.lblHistory.setForeground(Color.BLACK);
        }
        if (e.getSource() == formDashboard.lblViewCart) {
            formDashboard.lblViewCart.setForeground(Color.BLACK);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}
