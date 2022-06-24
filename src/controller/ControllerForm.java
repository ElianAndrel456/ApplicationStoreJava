package controller;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
//import com.itextpdf.text.Image;
//import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import models.Customer;
import models.Product;
import models.Qry;
import models.ShoppingCart;
import view.ClientsFrame;
import view.DashboardControllUser;
import view.DashboardUser;
import view.EmployeesFrame;
import view.ForgotJFrame;
import view.HistoryShoppingJFrame;
import view.LoginEmployeeJFrame;
import view.LoginJFrame;
import view.PayJFrame;
import view.ProductsFrame;
import view.RegisterJFrame;
import view.ShoppingCartJFrame;

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
    ProductsFrame formProducts;
    ClientsFrame formClients;
    EmployeesFrame formEmployees;
    String ruta;

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String uri) {
        ruta = uri;
    }

    public void controllerEmployees(EmployeesFrame femployees) {
        this.formEmployees = femployees;
        formEmployees.setVisible(false);
        formEmployees.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("id-trabajador");
        tableModel.addColumn("nombre");
        tableModel.addColumn("username");
        formEmployees.tblEmployees.setModel(tableModel);
        ArrayList<Customer> list = q.tableEmployees();
        Iterator<Customer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Customer c = iterator.next();
            tableModel.addRow(c.registroTrabajador());
        }

    }

    public void controllerClients(ClientsFrame fclients) {
        this.formClients = fclients;
        formClients.setVisible(false);
        formClients.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("id-cliente");
        tableModel.addColumn("nombre");
        tableModel.addColumn("edad");
        tableModel.addColumn("dni");
        tableModel.addColumn("email");
        tableModel.addColumn("direccion");
        formClients.tblClients.setModel(tableModel);
        ArrayList<Customer> list = q.tableClients();
        Iterator<Customer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Customer c = iterator.next();
            tableModel.addRow(c.registroClient());
        }
    }

    public void controllerProducts(ProductsFrame fproduct) {
        this.formProducts = fproduct;
        formProducts.setVisible(false);
        formProducts.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Id");
        tableModel.addColumn("Producto");
        tableModel.addColumn("Descripcion");
        tableModel.addColumn("Categoria");
        tableModel.addColumn("Precio");
        formProducts.tblProducts.setModel(tableModel);
        ArrayList<Product> list = q.tableDashboard("todos");
        Iterator<Product> iterator = list.iterator();
        while (iterator.hasNext()) {
            Product p = iterator.next();
            tableModel.addRow(p.registro());
        }
        formProducts.btnImageProduct.addActionListener(this);
        formProducts.btnAddProduct.addActionListener(this);
        formProducts.btnUpdateProduct.addActionListener(this);
        formProducts.btnDeleteProduct.addActionListener(this);
        formProducts.tblProducts.addMouseListener(this);
    }

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

        tableModel.addColumn("id-pedido");// p
        tableModel.addColumn("Producto");// p
        tableModel.addColumn("Cantidad");// p
        tableModel.addColumn("Talla");// p
        tableModel.addColumn("Estado");// c
        tableModel.addColumn("Fecha");// c
        formHistory.tableHistory.setModel(tableModel);

        ShoppingCart sc = q.tableHistory(q.getCliente().getId_customer());
        ArrayList<Product> list = sc.getProductos();
        Iterator<Product> iterator = list.iterator();
        while (iterator.hasNext()) {
            Product p = iterator.next();
            tableModel.addRow(sc.registro(p.getPedido(), p.getNombre(), p.getCantidad(), p.getTalla(), p.getFecha(),
                    p.getEstado()));
        }

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
        formDashboardControllE.lblProducts.addMouseListener(this);
        formDashboardControllE.lblClientes.addMouseListener(this);
        formDashboardControllE.lblEmployees.addMouseListener(this);
        formDashboardControllE.btnFilter.addActionListener(this);
        formDashboardControllE.btnUpdateState.addActionListener(this);
        formDashboardControllE.tblVentas.addMouseListener(this);
        String state = formDashboardControllE.cboxStatef.getSelectedItem().toString();

        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("id-cliente");
        tableModel.addColumn("id-pedido");
        tableModel.addColumn("Producto");
        tableModel.addColumn("Cantidad");
        tableModel.addColumn("Talla");
        tableModel.addColumn("Estado");
        tableModel.addColumn("Fecha");
        formDashboardControllE.tblVentas.setModel(tableModel);

        ShoppingCart sc = q.tableDashE(state);
        ArrayList<Product> list = sc.getProductos();
        Iterator<Product> iterator = list.iterator();
        while (iterator.hasNext()) {
            Product p = iterator.next();
            tableModel.addRow(sc.registro2(p.getIdUser(), p.getPedido(), p.getNombre(), p.getCantidad(), p.getTalla(),
                    p.getFecha(),
                    p.getEstado()));
        }
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
        formDashboard.lblImageUser.addMouseListener(this);

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

            if (q.getImageIdClient() != null) {
                try {
                    byte[] imagen = q.getImageIdClient();
                    BufferedImage bi = null;
                    InputStream is = new ByteArrayInputStream(imagen);
                    bi = ImageIO.read(is);
                    ImageIcon mIcon = new ImageIcon(
                            bi.getScaledInstance(formDashboard.lblImageUser.getWidth(),
                                    formDashboard.lblImageUser.getHeight(), Image.SCALE_DEFAULT));
                    this.formDashboard.lblImageUser.setIcon(mIcon);

                } catch (Exception ex) {
                }
            }

            DefaultTableModel tableModel = new DefaultTableModel();

            tableModel.addColumn("id-pedido");// p
            tableModel.addColumn("Producto");// p
            tableModel.addColumn("Cantidad");// p
            tableModel.addColumn("Talla");// p
            tableModel.addColumn("Estado");// c
            tableModel.addColumn("Fecha");// c
            formHistory.tableHistory.setModel(tableModel);

            ShoppingCart sc = q.tableHistory(q.getCliente().getId_customer());
            ArrayList<Product> list = sc.getProductos();
            Iterator<Product> iterator = list.iterator();
            while (iterator.hasNext()) {
                Product p = iterator.next();
                tableModel.addRow(sc.registro(p.getPedido(), p.getNombre(), p.getCantidad(), p.getTalla(), p.getFecha(),
                        p.getEstado()));
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
        if (e.getSource() == formDashboardControllE.btnUpdateState) {
            String pedido = formDashboardControllE.txtB.getText();
            String state = formDashboardControllE.cboxState.getSelectedItem().toString();
            q.updateDashE(pedido, state);

            DefaultTableModel tableModel = new DefaultTableModel();

            tableModel.addColumn("id-cliente");// p
            tableModel.addColumn("id-pedido");// p
            tableModel.addColumn("Producto");// p
            tableModel.addColumn("Cantidad");// p
            tableModel.addColumn("Talla");// p
            tableModel.addColumn("Estado");// c
            tableModel.addColumn("Fecha");// c
            formDashboardControllE.tblVentas.setModel(tableModel);

            ShoppingCart sc = q.tableDashE(state);
            ArrayList<Product> list = sc.getProductos();
            Iterator<Product> iterator = list.iterator();
            while (iterator.hasNext()) {
                Product p = iterator.next();
                tableModel
                        .addRow(sc.registro2(p.getIdUser(), p.getPedido(), p.getNombre(), p.getCantidad(), p.getTalla(),
                                p.getFecha(),
                                p.getEstado()));
            }
        }
        if (e.getSource() == formDashboardControllE.btnFilter) {
            String state = formDashboardControllE.cboxStatef.getSelectedItem().toString();

            DefaultTableModel tableModel = new DefaultTableModel();

            tableModel.addColumn("id-cliente");// p
            tableModel.addColumn("id-pedido");// p
            tableModel.addColumn("Producto");// p
            tableModel.addColumn("Cantidad");// p
            tableModel.addColumn("Talla");// p
            tableModel.addColumn("Estado");// c
            tableModel.addColumn("Fecha");// c
            formDashboardControllE.tblVentas.setModel(tableModel);

            ShoppingCart sc = q.tableDashE(state);
            ArrayList<Product> list = sc.getProductos();
            Iterator<Product> iterator = list.iterator();
            while (iterator.hasNext()) {
                Product p = iterator.next();
                tableModel
                        .addRow(sc.registro2(p.getIdUser(), p.getPedido(), p.getNombre(), p.getCantidad(), p.getTalla(),
                                p.getFecha(),
                                p.getEstado()));
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
            String direccion = formRegister.txtDireccion.getText();
            int edad = Integer.parseInt(formRegister.txtEdad.getText());
            boolean res = q.insertUser(name, dni, user, pass, email, direccion, edad);

            if (res) {
                formRegister.setVisible(false);
            }

        }

        if (e.getSource() == formDashboard.btnPay) {
            formPay.setVisible(true);
        }
        if (e.getSource() == formDashboard.btnFilter) {
            String o = formDashboard.boxFilter.getSelectedItem().toString().toLowerCase();

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
                totalPrice += pr.getPrecio() * pr.getCantidad();
            }

            formShoppingCart.lblPrecio.setText("" + totalPrice);
            formDashboard.txtCantidad.setText("");
            formDashboard.txtId.setText("");

        }

        if (e.getSource() == formPay.btnPay) {
            boolean b = formPay.txtTarjeta.getText().matches("^3[47][0-9]{13}$");
            int cvv = Integer.parseInt(formPay.txtCVV.getText());
            int idUser = q.getCliente().getId_customer();
            Calendar calendario = Calendar.getInstance();
            int sday = calendario.get(Calendar.DAY_OF_WEEK);
            int day = calendario.get(Calendar.DAY_OF_MONTH);
            int month = calendario.get(Calendar.MONTH);
            int year = calendario.get(Calendar.YEAR);
            Date fecha = new Date(year - 1900, month, day);
            String dia = "";
            String mes = "";

            switch (sday) {
                case 1:
                    dia = "domingo";
                    break;
                case 2:
                    dia = "lunes";
                    break;
                case 3:
                    dia = "martes";
                    break;
                case 4:
                    dia = "miercoles";
                    break;
                case 5:
                    dia = "jueves";
                    break;
                case 6:
                    dia = "viernes";
                    break;
                case 7:
                    dia = "sabado";
                    break;
            }
            switch (month) {
                case 0:
                    mes = "enero";
                    break;
                case 1:
                    mes = "febrero";
                    break;
                case 2:
                    mes = "marzo";
                    break;
                case 3:
                    mes = "abril";
                    break;
                case 4:
                    mes = "mayo";
                    break;
                case 5:
                    mes = "junio";
                    break;
                case 6:
                    mes = "julio";
                    break;
                case 7:
                    mes = "agosto";
                    break;
                case 8:
                    mes = "setiembre";
                    break;
                case 9:
                    mes = "octubre";
                    break;
                case 10:
                    mes = "noviembre";
                    break;
                case 11:
                    mes = "diciembre";
                    break;

            }
            if (b && (cvv > 99 && cvv < 1000)) {
                ArrayList<Product> list = sc.getProductos();
                Iterator<Product> iterator = list.iterator();
                String bol = "cod-" + (Math.round(Math.random() * 10000)) + "ref";
                while (iterator.hasNext()) {
                    Product pr = iterator.next();
                    int idProducto = pr.getCodigo();
                    String talla = pr.getTalla();
                    int cantidad = pr.getCantidad();
                    q.insetCartPay(idUser, idProducto, talla, cantidad, fecha, bol);
                }

                try {
                    long numberRandom = Math.round(Math.random() * 10000);
                    FileOutputStream archivo = new FileOutputStream("Boleta.pdf");
                    Document documento = new Document();
                    PdfWriter.getInstance(documento, archivo);

                    com.itextpdf.text.Image header = com.itextpdf.text.Image.getInstance("src/assets/header.jpg");
                    header.scaleToFit(600, 300);
                    header.setAlignment(Chunk.ALIGN_CENTER);
                    documento.open();
                    Paragraph parrafo = new Paragraph("BLUEJEANS",
                            new Font(FontFamily.TIMES_ROMAN, 32, Font.BOLDITALIC, new BaseColor(30, 30, 30)));
                    // parrafo.setFont(FontFactory.getFont("calibri", 32, Font.BOLD));
                    Paragraph Pfecha = new Paragraph(dia + ", " + day + " de " + mes + " de " + year);
                    Paragraph subTitle = new Paragraph("BOLETA DE VENTA ELECTRONICA",
                            new Font(FontFamily.TIMES_ROMAN, 18, Font.BOLD, new BaseColor(0, 0, 0)));
                    Paragraph importe = new Paragraph("Importe Total: " + formShoppingCart.lblPrecio.getText());
                    importe.setAlignment(Paragraph.ALIGN_RIGHT);
                    subTitle.setAlignment(Paragraph.ALIGN_CENTER);
                    Pfecha.setAlignment(Paragraph.ALIGN_CENTER);
                    Paragraph numberBoleta = new Paragraph("B002-0" + numberRandom);
                    numberBoleta.setAlignment(Paragraph.ALIGN_CENTER);
                    Pfecha.setAlignment(Paragraph.ALIGN_CENTER);
                    parrafo.setAlignment(Paragraph.ALIGN_CENTER);

                    documento.add(header);
                    documento.add(parrafo);
                    documento.add(new Paragraph("Direccion : PERU LIMA-LIMA"));
                    documento.add(new Paragraph("Contacto : 01-13586-16364-54683"));
                    documento.add(new Paragraph("RUC: 9856482364"));
                    documento.add(Pfecha);
                    documento.add(new Paragraph("                       "));
                    documento.add(subTitle);
                    documento.add(numberBoleta);
                    documento.add(new Paragraph("Cliente: " + q.getCliente().getFirstName()));
                    documento.add(new Paragraph("Documento: " + q.getCliente().getDni()));
                    documento.add(new Paragraph("Email: " + q.getCliente().getEmail()));
                    documento.add(new Paragraph("Pago: Tarjeta Visa"));
                    documento.add(new Paragraph("Vendedor: Tienda BlueJeans"));
                    documento.add(new Paragraph("                       "));

                    PdfPTable tabla = new PdfPTable(4);
                    tabla.addCell("Descripcion");
                    tabla.addCell("Cantidad");
                    tabla.addCell("P. U.");
                    tabla.addCell("Prec. Total");

                    ArrayList<Product> lista = sc.getProductos();
                    Iterator<Product> i = lista.iterator();
                    while (i.hasNext()) {
                        Product pr = i.next();
                        tabla.addCell(pr.getNombre());
                        tabla.addCell("" + pr.getCantidad());
                        tabla.addCell("" + pr.getPrecio());
                        tabla.addCell("" + (pr.getPrecio() * pr.getCantidad()));

                    }

                    documento.add(tabla);
                    documento.add(importe);

                    documento.close();

                    File path = new File("Boleta.pdf");
                    Desktop.getDesktop().open(path);
                } catch (IOException ex) {
                    // TODO: handle exception
                } catch (DocumentException ex) {
                    // TODO: handle exception
                }

                DefaultTableModel tableModel = new DefaultTableModel();

                tableModel.addColumn("id-pedido");
                tableModel.addColumn("Producto");
                tableModel.addColumn("Cantidad");
                tableModel.addColumn("Talla");
                tableModel.addColumn("Estado");
                tableModel.addColumn("Fecha");
                formHistory.tableHistory.setModel(tableModel);

                ShoppingCart sc = q.tableHistory(q.getCliente().getId_customer());
                ArrayList<Product> x = sc.getProductos();
                Iterator<Product> it = x.iterator();
                while (it.hasNext()) {
                    Product p = it.next();
                    tableModel.addRow(
                            sc.registro(p.getPedido(), p.getNombre(), p.getCantidad(), p.getTalla(), p.getFecha(),
                                    p.getEstado()));
                }

                JOptionPane.showMessageDialog(null, "Datos Correctos");
            } else {
                JOptionPane.showMessageDialog(null, "Error al Ingresar Datos");
            }
            formPay.txtCVV.setText("");
            formPay.txtTarjeta.setText("");
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Id");
            tableModel.addColumn("Producto");
            tableModel.addColumn("Cantidad");
            tableModel.addColumn("Talla");
            tableModel.addColumn("Precio");
            formShoppingCart.tableShoppingCart.setModel(tableModel);
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
        if (e.getSource() == formProducts.btnImageProduct) {

            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("JPG, PNG & GIF", "jpg", "png",
                    "gif");
            fileChooser.setFileFilter(extensionFilter);
            if (fileChooser.showOpenDialog(this.formProducts) == JFileChooser.APPROVE_OPTION) {
                setRuta(fileChooser.getSelectedFile().getAbsolutePath());
                Image nImage = new ImageIcon(getRuta()).getImage();
                ImageIcon mIcon = new ImageIcon(nImage.getScaledInstance(this.formProducts.lblImage.getWidth(),
                        this.formProducts.lblImage.getHeight(), 0));
                this.formProducts.lblImage.setIcon(mIcon);
            }
        }
        if (e.getSource() == formProducts.btnAddProduct) {

            File image = new File(getRuta());
            try {
                byte[] icono = new byte[(int) image.length()];
                InputStream input = new FileInputStream(image);
                input.read(icono);
                int id = Integer.parseInt(formProducts.txtIdProducts.getText());
                String name = formProducts.txtNameProduct.getText();
                String description = formProducts.txtaDescriptionProducts.getText();
                double price = Double.parseDouble(formProducts.txtPrice.getText());
                String cat = formProducts.cboxCategoryProduct.getSelectedItem().toString();
                byte[] img = icono;
                q.agregarProducto(id, name, description, price, cat, img);

            } catch (Exception ex) {
            }
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Id");
            tableModel.addColumn("Producto");
            tableModel.addColumn("Descripcion");
            tableModel.addColumn("Categoria");
            tableModel.addColumn("Precio");
            formProducts.tblProducts.setModel(tableModel);
            ArrayList<Product> list = q.tableDashboard("todos");
            Iterator<Product> iterator = list.iterator();
            while (iterator.hasNext()) {
                Product p = iterator.next();
                tableModel.addRow(p.registro());
            }
        }
        if (e.getSource() == formProducts.btnDeleteProduct) {
            int id = Integer.parseInt(formProducts.txtIdProducts.getText());
            q.deleteProducto(id);
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Id");
            tableModel.addColumn("Producto");
            tableModel.addColumn("Descripcion");
            tableModel.addColumn("Categoria");
            tableModel.addColumn("Precio");
            formProducts.tblProducts.setModel(tableModel);
            ArrayList<Product> list = q.tableDashboard("todos");
            Iterator<Product> iterator = list.iterator();
            while (iterator.hasNext()) {
                Product p = iterator.next();
                tableModel.addRow(p.registro());
            }
        }
        if (e.getSource() == formProducts.btnUpdateProduct) {

            try {
                if (getRuta() != null) {
                    File image = new File(getRuta());
                    byte[] icono = new byte[(int) image.length()];
                    InputStream input = new FileInputStream(image);
                    input.read(icono);
                    int id = Integer.parseInt(formProducts.txtIdProducts.getText());
                    String name = formProducts.txtNameProduct.getText();
                    String description = formProducts.txtaDescriptionProducts.getText();
                    double price = Double.parseDouble(formProducts.txtPrice.getText());
                    String cat = formProducts.cboxCategoryProduct.getSelectedItem().toString();
                    byte[] img = icono;
                    q.updateProductsWithImage(id, name, description, price, cat, img);

                } else {
                    int id = Integer.parseInt(formProducts.txtIdProducts.getText());
                    String name = formProducts.txtNameProduct.getText();
                    String description = formProducts.txtaDescriptionProducts.getText();
                    double price = Double.parseDouble(formProducts.txtPrice.getText());
                    String cat = formProducts.cboxCategoryProduct.getSelectedItem().toString();
                    q.updateProducts(id, name, description, price, cat);
                }
            } catch (Exception exception) {
            }
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Id");
            tableModel.addColumn("Producto");
            tableModel.addColumn("Descripcion");
            tableModel.addColumn("Categoria");
            tableModel.addColumn("Precio");
            formProducts.tblProducts.setModel(tableModel);
            ArrayList<Product> list = q.tableDashboard("todos");
            Iterator<Product> iterator = list.iterator();
            while (iterator.hasNext()) {
                Product p = iterator.next();
                tableModel.addRow(p.registro());
            }

        }

    }

    /* EVENTOS DE MOUSE */
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
        if (e.getSource() == formDashboard.lblImageUser) {
            String route = "";
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("JPG, PNG & GIF", "jpg", "png",
                    "gif");
            fileChooser.setFileFilter(extensionFilter);
            if (fileChooser.showOpenDialog(this.formDashboard) == JFileChooser.APPROVE_OPTION) {
                route = (fileChooser.getSelectedFile().getAbsolutePath());
                Image nImage = new ImageIcon(route).getImage();
                ImageIcon mIcon = new ImageIcon(nImage.getScaledInstance(this.formDashboard.lblImageUser.getWidth(),
                        this.formDashboard.lblImageUser.getHeight(), Image.SCALE_DEFAULT));
                this.formDashboard.lblImageUser.setIcon(mIcon);
            }
            try {
                File image = new File(route);
                byte[] icono = new byte[(int) image.length()];
                InputStream input = new FileInputStream(image);
                input.read(icono);
                byte[] img = icono;
                q.updateImageClient(img);
            } catch (Exception ex) {
                // TODO: handle exception
            }

        }

        if (e.getSource() == formDashboard.tableProducts) {
            int c = formDashboard.tableProducts.getSelectedRow();
            int id = Integer.parseInt(formDashboard.tableProducts.getValueAt(c, 0).toString());
            formDashboard.txtId.setText(formDashboard.tableProducts.getValueAt(c, 0).toString());

            formDashboard.txtAInfo.setText(formDashboard.tableProducts.getValueAt(c, 1).toString() + "\n" +
                    "Descripcion del producto : " + formDashboard.tableProducts.getValueAt(c, 2).toString() + "\n" +
                    "Precio : S/" + formDashboard.tableProducts.getValueAt(c, 4).toString());
            String categoria = formDashboard.tableProducts.getValueAt(c, 3).toString();

            if (categoria.length() > 6) {
                if (formDashboard.boxTalla.getItemCount() > 0) {
                    formDashboard.boxTalla.removeAllItems();
                }
                formDashboard.boxTalla.addItem("36");
                formDashboard.boxTalla.addItem("38");
                formDashboard.boxTalla.addItem("40");
                formDashboard.boxTalla.addItem("42");
            } else if (categoria.length() <= 6) {
                if (formDashboard.boxTalla.getItemCount() > 0) {
                    formDashboard.boxTalla.removeAllItems();
                }
                formDashboard.boxTalla.addItem("xs");
                formDashboard.boxTalla.addItem("s");
                formDashboard.boxTalla.addItem("m");
                formDashboard.boxTalla.addItem("x");
                formDashboard.boxTalla.addItem("xl");

            } else {
            }

            try {
                byte[] imagen = q.getImageId(id);
                BufferedImage bi = null;
                InputStream is = new ByteArrayInputStream(imagen);
                bi = ImageIO.read(is);
                ImageIcon mIcon = new ImageIcon(
                        bi.getScaledInstance(formProducts.lblImage.getWidth(), formProducts.lblImage.getHeight(), 0));
                this.formDashboard.lblImageProduct.setIcon(mIcon);

            } catch (Exception ex) {
            }

        }
        if (e.getSource() == formDashboardControllE.tblVentas) {
            int c = formDashboardControllE.tblVentas.getSelectedRow();
            formDashboardControllE.txtB.setText(formDashboardControllE.tblVentas.getValueAt(c, 1).toString());

        }

        if (e.getSource() == formProducts.tblProducts) {
            int c = formProducts.tblProducts.getSelectedRow();
            int id = Integer.parseInt(formProducts.tblProducts.getValueAt(c, 0).toString());
            formProducts.txtIdProducts.setText(formProducts.tblProducts.getValueAt(c, 0).toString());
            formProducts.txtNameProduct.setText(formProducts.tblProducts.getValueAt(c, 1).toString());
            formProducts.txtaDescriptionProducts.setText(formProducts.tblProducts.getValueAt(c, 2).toString());
            formProducts.txtPrice.setText(formProducts.tblProducts.getValueAt(c, 4).toString());
            try {
                byte[] imagen = q.getImageId(id);
                BufferedImage bi = null;
                InputStream is = new ByteArrayInputStream(imagen);
                bi = ImageIO.read(is);
                ImageIcon mIcon = new ImageIcon(
                        bi.getScaledInstance(formProducts.lblImage.getWidth(), formProducts.lblImage.getHeight(), 0));
                this.formProducts.lblImage.setIcon(mIcon);
            } catch (Exception ex) {
            }

        }

        if (e.getSource() == formDashboard.lblHistory) {
            formHistory.setVisible(true);
        }
        if (e.getSource() == formDashboard.lblViewCart) {
            formShoppingCart.setVisible(true);
        }
        if (e.getSource() == formDashboardControllE.lblProducts) {
            formProducts.setVisible(true);
        }
        if (e.getSource() == formDashboardControllE.lblClientes) {
            formClients.setVisible(true);
        }
        if (e.getSource() == formDashboardControllE.lblEmployees) {
            formEmployees.setVisible(true);
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
