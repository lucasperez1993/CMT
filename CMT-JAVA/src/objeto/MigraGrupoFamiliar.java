// 
// Decompiled by Procyon v0.5.36
// 

package objeto;

import Conexion.Conexion;
import org.json.JSONException;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import util.Reflection;
import java.util.ArrayList;
import java.sql.Connection;

public class MigraGrupoFamiliar
{
    public Connection connectionVPS;
    public Connection connection5;
    
    public MigraGrupoFamiliar() {
        this.connectionVPS = null;
        this.connection5 = null;
    }
    
    public void migraGrupoFamiliar(final Connection connection5, final Connection connectionVPS) throws SQLException {
        final String sql = "SELECT P.codme, RTRIM(LTRIM(A.NOMBRE)) as nombrea,a.numdoc AS dni, o.pa_nombre, C.estado as estado FROM club_ti C LEFT JOIN PRESTADORES P ON C.PRE_ID=P.PRE_ID LEFT JOIN CLUB_AD A ON C.PRE_ID=A.PRE_ID LEFT JOIN parentesco o on A.paren=o.pa_codigo WHERE C.ESTADO=1  AND A.estado=1 ";
        final ArrayList arrayAdherentes = new ArrayList();
        final List<Map<String, Object>> listaAdherentes = Reflection.getMapQueryResultByPreparedStatement(sql, arrayAdherentes, connection5);
        for (int indice = 0; indice < listaAdherentes.size(); ++indice) {
            PreparedStatement preparedStatement = null;
            final String sqlStmt = "INSERT INTO club_adherentes VALUES(?,?,?,?,?)";
            preparedStatement = connectionVPS.prepareStatement(sqlStmt, 1);
            preparedStatement.setInt(1, Integer.valueOf(getNotNull(listaAdherentes, indice, ".codme")));
            preparedStatement.setString(2, getNotNull(listaAdherentes, indice, ".nombrea"));
            preparedStatement.setInt(3, Integer.valueOf(getNotNull(listaAdherentes, indice, ".dni")));
            preparedStatement.setString(4, getNotNull(listaAdherentes, indice, ".pa_nombre"));
            preparedStatement.setInt(5, Integer.valueOf(getNotNull(listaAdherentes, indice, ".estado")));
            System.out.println("INSERT N° " + indice + " EN: " + listaAdherentes.get(indice).get(".codme"));
            preparedStatement.execute();
        }
    }
    
    public void updateDNIGrupoFamiliar(final Connection connection5, final Connection connectionVPS) throws SQLException {
        final String sql = "SELECT P.codme, RTRIM(LTRIM(A.NOMBRE)) as nombrea,a.numdoc AS dni, a.id, o.pa_nombre, C.estado as estado FROM club_ti C LEFT JOIN PRESTADORES P ON C.PRE_ID=P.PRE_ID LEFT JOIN CLUB_AD A ON C.PRE_ID=A.PRE_ID LEFT JOIN parentesco o on A.paren=o.pa_codigo WHERE C.ESTADO=1  AND A.estado=1 ";
        final ArrayList arrayAdherentes = new ArrayList();
        final List<Map<String, Object>> listaAdherentes = Reflection.getMapQueryResultByPreparedStatement(sql, arrayAdherentes, connection5);
        for (int indice = 0; indice < listaAdherentes.size(); ++indice) {
            PreparedStatement preparedStatement = null;
            final String sqlStmt = "UPDATE club_adherentes ";
            preparedStatement = connectionVPS.prepareStatement(sqlStmt, 1);
            preparedStatement.setInt(1, Integer.valueOf(getNotNull(listaAdherentes, indice, ".codme")));
            preparedStatement.setString(2, getNotNull(listaAdherentes, indice, ".nombrea"));
            preparedStatement.setInt(3, Integer.valueOf(getNotNull(listaAdherentes, indice, ".dni")));
            preparedStatement.setString(4, getNotNull(listaAdherentes, indice, ".pa_nombre"));
            preparedStatement.setInt(5, Integer.valueOf(getNotNull(listaAdherentes, indice, ".estado")));
            System.out.println("INSERT N° " + indice + " EN: " + listaAdherentes.get(indice).get(".codme"));
            preparedStatement.execute();
        }
    }
    
    public static String getNotNull(final List<Map<String, Object>> lista, final int indice, final String clave) {
        try {
            return lista.get(indice).get(clave).toString();
        }
        catch (Exception e) {
            return "";
        }
    }
    
    public static void main(final String[] arg) throws JSONException, SQLException {
        final Conexion con = new Conexion();
        final Connection connection5 = con.GetConnection5();
        final Connection connectionVPS = con.GetConnectionVPS();
        final MigraGrupoFamiliar migrar = new MigraGrupoFamiliar();
        migrar.migraGrupoFamiliar(connection5, connectionVPS);
    }
}
