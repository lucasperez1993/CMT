package Controller;

import Admin.Inicio;
import COLEGYM.BuscarSocio;
import COLEGYM.DashboardColegym;
import COLEGYM.DialogoAdherente;
import COLEGYM.DialogoAfiliados;
import COLEGYM.DialogoGrupoFamiliar;
import COLEGYM.DialogoRutina;
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
    DialogoAfiliados dialogoAfiliados;
    DialogoAdherente dialogoAdherente;
    DashboardColegym dashboardColegym;
    Excel excel;
    Cargador objetoCargador;
    Login login;
    
    public static void vistaLogin(){
        Login login = new Login();
        login.setVisible(true);
    }
    
    public void vistaAdmin(int idususario, int tipoUsuario, JSONObject permisJson, Login login, Connection connection){
        Inicio inicio = new Inicio(idususario, tipoUsuario, permisJson, login, connection);
        inicio.setVisible(true);
    }
    
    public void vistaDashboard(int idusuario, int tipoUsuario, JSONObject permisJson) throws JSONException, ParseException{
        Dashboard dashboard = new Dashboard(idusuario, tipoUsuario, permisJson);
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
    
    public void vistaBuscarSocioColegym(java.awt.Frame parent, boolean b, DialogoAfiliados dialogoAfiliados, Connection connection){
        BuscarSocio buscarSocio = new BuscarSocio(parent, b, dialogoAfiliados, connection);
        buscarSocio.setVisible(true);
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
    
    public void vistaDialogoGrupoFamiliar(java.awt.Frame parent, boolean b){
        DialogoGrupoFamiliar dialogoGrupoFamiliar = new DialogoGrupoFamiliar(parent, b);
        dialogoGrupoFamiliar.setVisible(true);
    }
    
    public void vistaDialogoAfiliados(java.awt.Frame parent, boolean b){
        DialogoAfiliados dialogoAfiliados = new DialogoAfiliados(parent, b);
        dialogoAfiliados.setVisible(true);
    }
    
    public void vistaDialogoAdherente(java.awt.Frame parent, boolean b, String nombrea, int numdoc, int codme, Connection connection) throws SQLException{
        DialogoAdherente dialogoAdherente = new DialogoAdherente(parent, b, nombrea, numdoc, codme, connection);
        dialogoAdherente.setVisible(true);
    }
    
    public void vistaDialogoRutina(java.awt.Frame parent, boolean b, Connection connection){
        DialogoRutina dialogoRutina = new DialogoRutina(parent, b, connection);
        dialogoRutina.setVisible(true);
    }
}