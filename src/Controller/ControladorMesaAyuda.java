package Controller;

import Models.SqlMesaDeAyuda;
import MDA.Mail;
import MDA.Dashboard;
import java.sql.Connection;
import objeto.cmt_configuracion;
import otro.Constante;
import util.MD5;

public class ControladorMesaAyuda
{
    int codme;
    Connection connection;
    Dashboard vistaDashboard;
    Mail vistaMail;
    boolean esNuevaClave;
    
    public ControladorMesaAyuda(int codme, Dashboard vistaDashboard, Connection connection) {
        this.esNuevaClave = false;
        this.codme = codme;
        this.connection = connection;
        this.vistaDashboard = vistaDashboard;
    }

    public boolean esUsuarioWeb() {
        return SqlMesaDeAyuda.esUsuarioWeb(codme, connection);
    }
}
