// 
// Decompiled by Procyon v0.5.36
// 

package objeto;

import util.Reflection;
import java.util.Map;
import java.util.List;
import org.json.JSONObject;
import java.util.ArrayList;
import java.sql.Connection;
import util.ImplementService;

public class Usuario extends ImplementService
{
    public int Id;
    public String Cuit;
    public String NroSocio;
    public String Contrasena;
    public String Email;
    public int pre_id;
    public int TipoObjetoId;
    public int Owner;
    public int EstadoId;
    private static final String nombreClase = "objeto.Usuario";
    
    public static int create(final Usuario objeto, final Connection connection) {
        return createObjeto((Object)objeto, "objeto.Usuario", connection);
    }
    
    public static void store(final Usuario objeto, final Connection connection) {
        storeObjeto((Object)objeto, "objeto.Usuario", connection);
    }
    
    public static Usuario getObjetoUsuario(final int id, final Connection conn) {
        return (Usuario)ImplementService.getObjetoPorId("objeto.Usuario", id, "", conn);
    }
    
    public static ArrayList<Object> getListaUsuario(final String condicion, final Connection conn) {
        return (ArrayList<Object>)ImplementService.getObjetoLista("objeto.Usuario", condicion, conn);
    }
    
    public static JSONObject getListaUsuarioPorIdJson(final int id, final Connection conn) {
        return ImplementService.getListaJsonPorId("objeto.Usuario", id, "", "objeto.Usuario", conn);
    }
    
    public static Usuario getObjetoUsuarioLogin(final String codme, final String clave, final Connection conn) {
        return (Usuario)ImplementService.getObjetoPorId("objeto.Usuario", -1, " or (NroSocio = '" + codme + "' and contrasena = '" + clave + "')", conn);
    }
    
    public static Usuario getObjetoUsuarioLoginPorCodme(final String codme, final Connection conn) {
        return (Usuario)ImplementService.getObjetoPorId("objeto.Usuario", -1, " or (NroSocio = '" + codme + "')", conn);
    }
    
    public static List<Map<String, Object>> getValoresTablaUsuario(final String sql, final Connection connection) throws Exception {
        final ArrayList<Object> arrayParametro = new ArrayList<Object>();
        return Reflection.getMapQueryResultByPreparedStatement(sql, arrayParametro, connection);
    }
}
