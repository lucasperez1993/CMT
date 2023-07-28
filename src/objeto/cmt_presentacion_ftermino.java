// 
// Decompiled by Procyon v0.5.36
// 

package objeto;

import org.json.JSONObject;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Timestamp;
import util.ImplementService;

public class cmt_presentacion_ftermino extends ImplementService
{
    public String nombre;
    public int id_llamada;
    public int codme;
    public Timestamp fecha;
    public int id_motivo;
    public int estado;
    private static final String nombreClase = "objeto.cmt_presentacion_ftermino";
    
    public static int create(final cmt_presentacion_ftermino objeto, final Connection connection) {
        return createObjeto((Object)objeto, "objeto.cmt_presentacion_ftermino", connection);
    }
    
    public static void store(final cmt_presentacion_ftermino objeto, final Connection connection) {
        storeObjeto((Object)objeto, "objeto.cmt_presentacion_ftermino", connection);
    }
    
    public static cmt_presentacion_ftermino getObjetocmt_presentacion_ftermino(final int id, final Connection conn) {
        return (cmt_presentacion_ftermino)ImplementService.getObjetoPorId("objeto.cmt_presentacion_ftermino", id, "", conn);
    }
    
    public static ArrayList<Object> getListacmt_presentacion_ftermino(final String condicion, final Connection conn) {
        return (ArrayList<Object>)ImplementService.getObjetoLista("objeto.cmt_presentacion_ftermino", condicion, conn);
    }
    
    public static JSONObject getListacmt_presentacion_fterminoPorIdJson(final int id, final Connection conn) {
        return ImplementService.getListaJsonPorId("objeto.cmt_presentacion_ftermino", id, "", "objeto.cmt_presentacion_ftermino", conn);
    }
    
    public static JSONObject createByJson(final JSONObject data, final Connection connection) throws Exception {
        final cmt_presentacion_ftermino objeto = (cmt_presentacion_ftermino)ImplementService.getObjectByJSONOBJECT("objeto.cmt_presentacion_ftermino", (Object)new cmt_presentacion_ftermino(), data.getJSONObject("formulariocmt_presentacion_fterminoJson"));
        final int id = create(objeto, connection);
        return new JSONObject().put("cmt_presentacion_fterminoJson", id);
    }
}
