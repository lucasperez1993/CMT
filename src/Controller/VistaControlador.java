package Controller;

import Admin.Inicio;
import COLEGYM.DashboardColegym;
import Login.Login;
import MDA.Buscar;
import MDA.CambiarClave;
import MDA.Dashboard;
import OS.DashboardOS;
import MDA.DialogoConsulta;
import MDA.DialogoControl;
import MDA.DialogoDelegaciones;
import MDA.DialogoEspecialidad;
import MDA.Excel;
import MDA.FueraDeTermino;
import MDA.GuiaMedica;
import OS.DialogoFaltantes;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import objeto.Cargador;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *
 * @author lperez
 */
public class VistaControlador {
    Connection connection;
    Dashboard dashboard;
    Excel excel;
    Cargador objetoCargador;
    Login login;
    
    public static void vistaLogin(){
        Login login = new Login();
        login.setVisible(true);
    }
    
    public void vistaAdmin(JSONObject permisJson, Login login, Connection connection){
        Inicio inicio = new Inicio(permisJson, login, connection);
        inicio.setVisible(true);
    }
    
    public void vistaDashboard(JSONObject permisJson) throws JSONException, ParseException{
        Dashboard dashboard = new Dashboard(permisJson);
        dashboard.setVisible(true);
    }
    
    public void vistaDashboarColegym(JSONObject permisJson) throws SQLException{
        DashboardColegym dashboard = new DashboardColegym(permisJson);
        dashboard.setVisible(true);
    }
    
    public void vistaDialogoControl(java.awt.Frame parent, Dashboard dashboard, Excel excel, Connection connection){
        DialogoControl dialogoControl = new DialogoControl(parent, dashboard, excel, connection);
        dialogoControl.setVisible(true);
    }
    
    public void vistaDialogoConsulta(java.awt.Frame parent, Cargador objCargador, Dashboard dashboard, Connection connection){
        DialogoConsulta dialogoConsulta = new DialogoConsulta(parent, objCargador, dashboard, connection);
        dialogoConsulta.setVisible(true);
    }
    
    public void vistaBuscar(java.awt.Frame parent, boolean b, Dashboard dashboard, Connection connection){
        Buscar buscar = new Buscar(parent, b, dashboard, connection);
        buscar.setVisible(true);
    }
 
    public void vistaEspecialidad(java.awt.Frame parent, boolean b, Dashboard dashboard, Connection connection){
        DialogoEspecialidad dialogoEspe = new DialogoEspecialidad(parent, b, dashboard, connection);
        dialogoEspe.setVisible(true);
    }
    
    public void vistaCambiarClave(java.awt.Frame parent, boolean b, Cargador objetoCargador, ControladorMesaAyuda controlador, 
        DialogoConsulta dialogoconsulta, String username, Connection connection){
        CambiarClave cambiarClave = new CambiarClave(parent, b, objetoCargador, controlador, dialogoconsulta, login.username, connection);
        cambiarClave.setVisible(true);
    }
    
    public void vistaDelegaciones(java.awt.Frame parent, boolean b, Connection connection){
        DialogoDelegaciones dialogoDelegaciones = new DialogoDelegaciones(parent, b, connection);
        dialogoDelegaciones.setVisible(true);
    }
    
    public void vistaGuiaMedica(java.awt.Frame parent, boolean b, Dashboard dashboard, int codme, String username, Connection connection){
        GuiaMedica guiaMedica = new GuiaMedica(parent, b, dashboard, codme, username, connection);
        guiaMedica.setVisible(true);
    }
    
    public void vistaFtermino(java.awt.Frame parent, boolean b, Connection connection){
        FueraDeTermino ftermino = new FueraDeTermino(parent, b, connection);
        ftermino.setVisible(true);
    }
    
    public void vistaDashboardOS(JSONObject permisoJson, Connection connection){
        DashboardOS dashboardOS = new DashboardOS(permisoJson, connection);
        dashboardOS.setVisible(true);
    }
    
    public void vistaFaltantesIPSST(java.awt.Frame parent, boolean b, Connection connection){
        DialogoFaltantes dialogoFaltantes = new DialogoFaltantes(parent, b, connection);
        dialogoFaltantes.setVisible(true);
    }
}
