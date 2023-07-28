// 
// Decompiled by Procyon v0.5.36
// 

package objeto;

import java.util.List;
import java.util.Map;
import java.sql.SQLException;
import util.Reflection;
import org.json.JSONObject;
import java.util.ArrayList;
import java.sql.Connection;
import util.ImplementService;

public class cmt_configuracion extends ImplementService
{
    public int idconfiguracion;
    public int tipoobjeto;
    public String nombre;
    public String configuracionjson;
    private static final String nombreClase = "objeto.cmt_configuracion";
    
    public static int create(final cmt_configuracion objeto, final Connection connection) {
        return createObjeto((Object)objeto, "objeto.cmt_configuracion", connection);
    }
    
    public static void store(final cmt_configuracion objeto, final Connection connection) {
        storeObjeto((Object)objeto, "objeto.cmt_configuracion", connection);
    }
    
    public static cmt_configuracion getObjetocmt_configuracion(final int id, final Connection conn) {
        return (cmt_configuracion)ImplementService.getObjetoPorId("objeto.cmt_configuracion", id, "", conn);
    }
    
    public static cmt_configuracion getObjetocmt_configuracionPorTipoObjeto(final int tipoobjeto, final Connection conn) {
        return (cmt_configuracion)ImplementService.getObjetoPorId("objeto.cmt_configuracion", -1, "or tipoobjeto = " + tipoobjeto, conn);
    }
    
    public static ArrayList<Object> getListacmt_configuracion(final String condicion, final Connection conn) {
        return (ArrayList<Object>)ImplementService.getObjetoLista("objeto.cmt_configuracion", condicion, conn);
    }
    
    public static JSONObject getListacmt_configuracionPorIdJson(final int id, final Connection conn) {
        return ImplementService.getListaJsonPorId("objeto.cmt_configuracion", id, "", "objeto.cmt_configuracion", conn);
    }
    
    public static JSONObject createByJson(final JSONObject data, final Connection connection) throws Exception {
        final cmt_configuracion objeto = (cmt_configuracion)ImplementService.getObjectByJSONOBJECT("objeto.cmt_configuracion", (Object)new cmt_configuracion(), data.getJSONObject("formulariocmt_configuracionJson"));
        final int id = create(objeto, connection);
        return new JSONObject().put("cmt_configuracionJson", id);
    }
    
    public static JSONObject getcmt_configuracionPorSQLTipo(final int tipoobjeto, final Connection connection) {
        List<Map<String, Object>> lista = null;
        JSONObject json = null;
        final String sql = "select * from cmt_configuracion where tipoobjeto = ?";
        final ArrayList listaParametro = new ArrayList();
        listaParametro.add(tipoobjeto);
        try {
            lista = Reflection.getMapQueryResultByPreparedStatement(sql, listaParametro, connection);
        }
        catch (SQLException ex2) {}
        try {
            if (lista.size() > 0) {
                json = new JSONObject(lista.get(0).get("cmt.configuracionjson").toString());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return json;
    }
    
    public static String getToken(final int idTipoObjeto, final Connection myConnection) throws Exception {
        List<Map<String, Object>> lista = null;
        try {
            final String query = "select * from cmt_configuracion  where tipoobjeto = ?";
            final ArrayList listaParametro = new ArrayList();
            listaParametro.add(idTipoObjeto);
            try {
                lista = Reflection.getMapQueryResultByPreparedStatement(query, listaParametro, myConnection);
            }
            catch (SQLException ex) {}
            if (lista.size() > 0) {
                return lista.get(0).get("cmt.configuracionjson").toString();
            }
        }
        catch (Exception ex2) {}
        return "";
    }
}
