// 
// Decompiled by Procyon v0.5.36
// 

package objeto;

import org.json.JSONObject;
import java.util.ArrayList;
import java.sql.Connection;
import util.ImplementService;

public class cmt_motivomesa extends ImplementService
{
    public int id_motivo;
    public String motivo;
    public int estado;
    private static final String nombreClase = "objeto.cmt_motivomesa";
    
    public static int create(final cmt_motivomesa objeto, final Connection connection) {
        return createObjeto((Object)objeto, "objeto.cmt_motivomesa", connection);
    }
    
    public static void store(final cmt_motivomesa objeto, final Connection connection) {
        storeObjeto((Object)objeto, "objeto.cmt_motivomesa", connection);
    }
    
    public static cmt_motivomesa getObjetocmt_motivomesa(final int id, final Connection conn) {
        return (cmt_motivomesa)ImplementService.getObjetoPorId("objeto.cmt_motivomesa", id, "", conn);
    }
    
    public static ArrayList<Object> getListacmt_motivomesa(final String condicion, final Connection conn) {
        return (ArrayList<Object>)ImplementService.getObjetoLista("objeto.cmt_motivomesa", condicion, conn);
    }
    
    public static JSONObject getListacmt_motivomesaPorIdJson(final int id, final Connection conn) {
        return ImplementService.getListaJsonPorId("objeto.cmt_motivomesa", id, "", "objeto.cmt_motivomesa", conn);
    }
    
    public static JSONObject createByJson(final JSONObject data, final Connection connection) throws Exception {
        final cmt_motivomesa objeto = (cmt_motivomesa)ImplementService.getObjectByJSONOBJECT("objeto.cmt_motivomesa", (Object)new cmt_motivomesa(), data.getJSONObject("formulariocmt_motivomesaJson"));
        final int id = create(objeto, connection);
        return new JSONObject().put("cmt_motivomesaJson", id);
    }
}
