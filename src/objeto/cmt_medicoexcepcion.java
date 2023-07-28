// 
// Decompiled by Procyon v0.5.36
// 

package objeto;

import org.json.JSONObject;
import java.util.ArrayList;
import java.sql.Connection;
import util.ImplementService;

public class cmt_medicoexcepcion extends ImplementService
{
    public int idmedicoexcepcion;
    public int codme;
    public int tipoobjeto;
    public int owner;
    public int estado;
    private static final String nombreClase = "objeto.cmt_medicoexcepcion";
    
    public static int create(final cmt_medicoexcepcion objeto, final Connection connection) {
        return createObjeto((Object)objeto, "objeto.cmt_medicoexcepcion", connection);
    }
    
    public static void store(final cmt_medicoexcepcion objeto, final Connection connection) {
        storeObjeto((Object)objeto, "objeto.cmt_medicoexcepcion", connection);
    }
    
    public static cmt_medicoexcepcion getObjetocmt_medicoexcepcion(final int id, final Connection conn) {
        return (cmt_medicoexcepcion)ImplementService.getObjetoPorId("objeto.cmt_medicoexcepcion", id, "", conn);
    }
    
    public static ArrayList<Object> getListacmt_medicoexcepcion(final String condicion, final Connection conn) {
        return (ArrayList<Object>)ImplementService.getObjetoLista("objeto.cmt_medicoexcepcion", condicion, conn);
    }
    
    public static JSONObject getListacmt_medicoexcepcionPorIdJson(final int id, final Connection conn) {
        return ImplementService.getListaJsonPorId("objeto.cmt_medicoexcepcion", id, "", "objeto.cmt_medicoexcepcion", conn);
    }
    
    public static JSONObject createByJson(final JSONObject data, final Connection connection) throws Exception {
        final cmt_medicoexcepcion objeto = (cmt_medicoexcepcion)ImplementService.getObjectByJSONOBJECT("objeto.cmt_medicoexcepcion", (Object)new cmt_medicoexcepcion(), data.getJSONObject("formulariocmt_medicoexcepcionJson"));
        final int id = create(objeto, connection);
        return new JSONObject().put("cmt_medicoexcepcionJson", id);
    }
}
