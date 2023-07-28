// 
// Decompiled by Procyon v0.5.36
// 

package objeto;

import org.json.JSONException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import util.Reflection;
import java.util.ArrayList;
import org.json.JSONObject;
import java.sql.Connection;

public class Prestadores
{
    public int codme;
    public String nombre;
    
    public static JSONObject getNombrePrestadorJson(final int codme, final Connection connection) throws SQLException, JSONException {
        final String query = "SELECT nombre FROM prestadores WHERE codme = " + codme + "";
        final ArrayList arrayMedicos = new ArrayList();
        final List<Map<String, Object>> listaMed = Reflection.getMapQueryResultByPreparedStatement(query, arrayMedicos, connection);
        if (listaMed.size() > 0) {
            final JSONObject json = new JSONObject().put("result", true).put("codme", codme).put("nombre", (Object)listaMed.get(0).get(".nombre").toString().trim());
            return json;
        }
        return new JSONObject().put("result", false);
    }
    
    public static boolean existePrestador(final int codme, final Connection connection) throws SQLException {
        final String sql1 = "SELECT * from prestadores where codme = ?";
        final ArrayList arrayNom = new ArrayList();
        arrayNom.add(codme);
        final List<Map<String, Object>> lista = Reflection.getMapQueryResultByPreparedStatement(sql1, arrayNom, connection);
        return lista.size() > 0;
    }
    
    public static boolean existeSocio(final int codme, final Connection connection) throws SQLException {
        final String selectQuery = "SELECT * FROM Usuario WHERE NroSocio = ?";
        final ArrayList arrayUsuario = new ArrayList();
        arrayUsuario.add(codme);
        final List<Map<String, Object>> usuarios = Reflection.getMapQueryResultByPreparedStatement(selectQuery, arrayUsuario, connection);
        return usuarios.size() > 0;
    }
}
