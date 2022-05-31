package Main;

import controller.ControllerForm;
import view.*;

public class Main {

    public static LoginJFrame flogin;
    public static ForgotJFrame fAccount;
    public static RegisterJFrame fregister;
    public static DashboardUser fdashboard;
    public static LoginEmployeeJFrame floginE;
    public static DashboardControllUser fdashControllerE;
    public static PayJFrame fpay;
    public static HistoryShoppingJFrame fhistory;
    public static ShoppingCartJFrame fshoppingcart;
    public static ControllerForm control;

    public static void main(String[] args) {
        flogin = new LoginJFrame();
        fregister = new RegisterJFrame();
        fdashboard = new DashboardUser();
        floginE = new LoginEmployeeJFrame();
        fdashControllerE = new DashboardControllUser();
        fpay = new PayJFrame();
        fhistory=new HistoryShoppingJFrame();
        control = new ControllerForm();
        fshoppingcart= new ShoppingCartJFrame();

        control.controllerLogin(flogin);
        control.controllerAccount(fAccount);
        control.controllerRegister(fregister);
        control.controllerDashboard(fdashboard);
        control.controllerLoginE(floginE);
        control.controllerDashboardE(fdashControllerE);
        control.controllerPay(fpay);
        control.controllerShopping(fshoppingcart);
        control.controllerHistory(fhistory);
    }
}
