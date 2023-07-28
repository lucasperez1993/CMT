// 
// Decompiled by Procyon v0.5.36
// 

package Models;

import MDA.FueraDeTermino;
import objeto.Cargador;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Reflection;
import java.util.ArrayList;
import java.sql.Connection;
import MDA.Dashboard;
import java.util.Map;
import java.util.List;

public class SqlMesaDeAyuda
{
    static List<Map<String, Object>> listaSql;
    static List<Map<String, Object>> listaLlamadas;
    public List<Map<String, Object>> listaMedicos;
    Dashboard dashboard;
    public String username;
    
    public static String getContrasenaMD5UsuarioWeb(int codme, Connection connection) {
        try {
            final String query = "select contrasena from usuario where nrosocio = ?";
            final ArrayList arrayDel = new ArrayList();
            arrayDel.add(codme);
            SqlMesaDeAyuda.listaSql = Reflection.getMapQueryResultByPreparedStatement(query, arrayDel, connection);
            if (SqlMesaDeAyuda.listaSql.size() > 0) {
                return SqlMesaDeAyuda.listaSql.get(0).get(".contrasena").toString().trim();
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static boolean esUsuarioWeb(int codme, Connection connection) {
        try {
            String query = "SELECT contrasena FROM usuario WHERE nrosocio = ?";
            ArrayList arrayDel = new ArrayList();
            arrayDel.add(codme);
            SqlMesaDeAyuda.listaSql = Reflection.getMapQueryResultByPreparedStatement(query, arrayDel, connection);
            return SqlMesaDeAyuda.listaSql.size() > 0;
        }
        catch (SQLException ex) {
            return false;
        }
    }
    
    public void datosMedico(Cargador objetoCargador, Connection connection) {
        try {
            String query = "select p.codme, p.nombre, p.gannro, p.tecel from prestadores p left join cmt_medicocargador m on p.codme=m.codme where m.idcargador = " + objetoCargador.id_cargador + "";
            ArrayList arrayMed = new ArrayList();
            listaMedicos = Reflection.getMapQueryResultByPreparedStatement(query, arrayMed, connection);
//            this.dashboard.getTblMedicos().setModel(new ModeloMesa(this.listaMedicos));
        }
        catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getEstadoLlamadas(int codme, FueraDeTermino fTermino, Connection connection) {
        try {
            String query = "SELECT * FROM cmt_presentacion_ftermino WHERE codme = ? ORDER BY fecha desc";
            ArrayList arrayLlamadas = new ArrayList();
            arrayLlamadas.add(codme);
            SqlMesaDeAyuda.listaLlamadas = Reflection.getMapQueryResultByPreparedStatement(query, arrayLlamadas, connection);
            fTermino.getTblFtermino().setModel(new ModeloFtermino(SqlMesaDeAyuda.listaLlamadas, connection));
        }
        catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
