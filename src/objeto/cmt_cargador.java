// 
// Decompiled by Procyon v0.5.36
// 

package objeto;

import java.sql.PreparedStatement;
import org.json.JSONException;
import java.util.List;
import java.util.Map;
import java.sql.SQLException;
import util.Reflection;
import org.json.JSONObject;
import java.util.ArrayList;
import java.sql.Connection;
import util.ImplementService;

public class cmt_cargador extends ImplementService
{
    public int idcargador;
    public int codme;
    public long dni;
    public String nombre;
    public String telefono;
    public String mail;
    public int estado;
    private static final String nombreClase = "objeto.cmt_cargador";
    
    public static int create(final cmt_cargador objeto, final Connection connection) {
        return createObjeto((Object)objeto, "objeto.cmt_cargador", connection);
    }
    
    public static void store(final cmt_cargador objeto, final Connection connection) {
        storeObjeto((Object)objeto, "objeto.cmt_cargador", connection);
    }
    
    public static cmt_cargador getObjetocmt_cargador(final int id, final Connection conn) {
        return (cmt_cargador)ImplementService.getObjetoPorId("objeto.cmt_cargador", id, "", conn);
    }
    
    public static cmt_cargador getObjetocmt_cargadorPorCodme(final int codme, final Connection conn) {
        return (cmt_cargador)ImplementService.getObjetoPorId("objeto.cmt_cargador", -1, " or codme = " + codme, conn);
    }
    
    public static cmt_cargador getObjetocmt_cargadorPorEmail(final String email, final Connection conn) {
        return (cmt_cargador)ImplementService.getObjetoPorId("objeto.cmt_cargador", -1, " or mail = '" + email + "'", conn);
    }
    
    public static ArrayList<Object> getListacmt_cargador(final String condicion, final Connection conn) {
        return (ArrayList<Object>)ImplementService.getObjetoLista("objeto.cmt_cargador", condicion, conn);
    }
    
    public static JSONObject getListacmt_cargadorPorIdJson(final int id, final Connection conn) {
        return ImplementService.getListaJsonPorId("objeto.cmt_cargador", id, "", "objeto.cmt_cargador", conn);
    }
    
    public static JSONObject createByJson(final JSONObject data, final Connection connection) throws Exception {
        final cmt_cargador objeto = (cmt_cargador)ImplementService.getObjectByJSONOBJECT("objeto.cmt_cargador", (Object)new cmt_cargador(), data.getJSONObject("formulariocmt_cargadorJson"));
        final int id = create(objeto, connection);
        return new JSONObject().put("cmt_cargadorJson", id);
    }
    
    public static int getNuevoCodme(final Connection myConnection) throws Exception {
        List<Map<String, Object>> lista = null;
        try {
            final String query = "select * from cmt_cargador order by codme desc";
            final ArrayList listaParametro = new ArrayList();
            try {
                lista = Reflection.getMapQueryResultByPreparedStatement(query, listaParametro, myConnection);
            }
            catch (SQLException ex) {}
        }
        catch (Exception ex2) {}
        if (lista.size() > 0) {
            return Integer.valueOf(lista.get(0).get(".codme").toString()) + 2;
        }
        return 1000010;
    }
    
    public static int getCargadorPorMail(final String mail, final Connection myConnection) throws Exception {
        List<Map<String, Object>> lista = null;
        try {
            final String query = "select * from cmt_cargador where mail = ?";
            final ArrayList listaParametro = new ArrayList();
            listaParametro.add(mail);
            try {
                lista = Reflection.getMapQueryResultByPreparedStatement(query, listaParametro, myConnection);
            }
            catch (SQLException ex) {}
        }
        catch (Exception ex2) {}
        if (lista.size() > 0) {
            return Integer.valueOf(lista.get(0).get(".idcargador").toString());
        }
        return 0;
    }
    
    public static JSONObject getCodmeCargadorPorMD5(final String md5, final String token, final Connection connection) throws JSONException {
        final JSONObject json = new JSONObject();
        int codme = 0;
        boolean esLongitudCorrecta = false;
        try {
            final String sql = "SELECT codme, dni FROM cmt_cargador";
            final String clave = "";
            final ArrayList<Object> arrayParametro = new ArrayList<Object>();
            final List<Map<String, Object>> lista = Reflection.getMapQueryResultByPreparedStatement(sql, arrayParametro, connection);
            if (lista.size() > 0) {
                for (int indice = 0; indice < lista.size(); indice++) {
                    codme = Integer.valueOf(lista.get(indice).get(".codme").toString());
                    if (md5.equals(clave)) {
                        final String sCuit = lista.get(indice).get(".dni").toString();
                        esLongitudCorrecta = (sCuit.length() > 10);
                        break;
                    }
                    codme = 0;
                }
            }
        }
        catch (SQLException ex) {}
        return json.put("codme", codme).put("cuilcompleto", esLongitudCorrecta);
    }
    
    public static boolean modificarCargagor(final cmt_cargador cargador, final Connection connection) {
        try {
            final String sqlUpdate = "update cmt_cargador set nombre = ?, mail = ?, dni = ?, telefono = ? where idcargador = ?";
            final PreparedStatement pstmtModificarProveedor = connection.prepareStatement(sqlUpdate);
            pstmtModificarProveedor.setString(1, cargador.nombre);
            pstmtModificarProveedor.setString(2, cargador.mail);
            pstmtModificarProveedor.setLong(3, cargador.dni);
            pstmtModificarProveedor.setString(4, cargador.telefono);
            pstmtModificarProveedor.setInt(5, cargador.idcargador);
            pstmtModificarProveedor.execute();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
