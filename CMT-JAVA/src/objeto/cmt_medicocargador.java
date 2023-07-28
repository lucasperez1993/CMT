package objeto;

import java.util.Set;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.SQLException;
import util.Reflection;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import otro.Constante;
import java.sql.Connection;
import java.sql.Timestamp;
import util.ImplementService;
import util.MD5;

public class cmt_medicocargador extends ImplementService
{
    public int idmedicocargador;
    public int codme;
    public int idcargador;
    public Timestamp fecha;
    public int estado;
    private static final String nombreClase = "objeto.cmt_medicocargador";
    
    public static int create(final cmt_medicocargador objeto, final Connection connection) {
        return createObjeto((Object)objeto, "objeto.cmt_medicocargador", connection);
    }
    
    public static void store(final cmt_medicocargador objeto, final Connection connection) {
        storeObjeto((Object)objeto, "objeto.cmt_medicocargador", connection);
    }
    
    public static cmt_medicocargador getObjetocmt_medicocargador(final int id, final Connection conn) {
        return (cmt_medicocargador)ImplementService.getObjetoPorId("objeto.cmt_medicocargador", id, "", conn);
    }
    
    public static cmt_medicocargador getObjetocmt_medicocargadorPorIdCargador(final int idcargador, final int codme, final Connection conn) {
        return (cmt_medicocargador)ImplementService.getObjetoPorId("objeto.cmt_medicocargador", -1, "or (idcargador = " + idcargador + " and codme = " + codme + ")", conn);
    }
    
    public static cmt_medicocargador getObjetocmt_medicocargadorPorIdCargadorActivo(final int idcargador, final Connection conn) {
        return (cmt_medicocargador)ImplementService.getObjetoPorId("objeto.cmt_medicocargador", -1, "or idcargador = " + idcargador + " and estado = " + Constante.OBJETO_ACTIVO, conn);
    }
    
    public static boolean existMedicocargadorPorCodme(final int codme, final int idcargador, final Connection conn) {
        final cmt_medicocargador mc = (cmt_medicocargador)ImplementService.getObjetoPorId("objeto.cmt_medicocargador", -1, "or codme = " + codme + " and estado in (" + Constante.OBJETO_ACTIVO + ", " + Constante.TIPO_OBJETO_EN_ESPERA + ") and idcargador = " + idcargador, conn);
        return mc.idmedicocargador > 0;
    }
    
    public static boolean existCargadorPorCodme(final int codme, final int idcargador, final Connection conn) {
        final cmt_medicocargador mc = (cmt_medicocargador)ImplementService.getObjetoPorId("objeto.cmt_medicocargador", -1, "or codme = " + codme + " and estado = " + Constante.OBJETO_ACTIVO + " and idcargador = " + idcargador, conn);
        return mc.idmedicocargador > 0;
    }
    
    public static ArrayList<Object> getListacmt_medicocargador(final String condicion, final Connection conn) {
        return (ArrayList<Object>)ImplementService.getObjetoLista("objeto.cmt_medicocargador", condicion, conn);
    }
    
    public static JSONObject getListacmt_medicocargadorPorIdJson(final int id, final Connection conn) {
        return ImplementService.getListaJsonPorId("objeto.cmt_medicocargador", id, "", "objeto.cmt_medicocargador", conn);
    }
    
    public static JSONObject createByJson(final JSONObject data, final Connection connection) throws Exception {
        final cmt_medicocargador objeto = (cmt_medicocargador)ImplementService.getObjectByJSONOBJECT("objeto.cmt_medicocargador", (Object)new cmt_medicocargador(), data.getJSONObject("formulariocmt_medicocargadorJson"));
        final int id = create(objeto, connection);
        return new JSONObject().put("cmt_medicocargadorJson", id);
    }
    
    public static JSONArray getMedicoCargadorPorCargador(final int codme, final Connection connection) {
        List<Map<String, Object>> lista = null;
        JSONObject json = null;
        final JSONArray jsonList = new JSONArray();
        final cmt_cargador cargador = cmt_cargador.getObjetocmt_cargadorPorCodme(codme, connection);
        final String sql = "select c.idmedicocargador, c.idcargador, c.codme, p.nombre from cmt_medicocargador c join prestadores p on c.codme = p.codme where c.idcargador = ? and c.estado = ? order by p.nombre asc";
        final ArrayList listaParametro = new ArrayList();
        listaParametro.add(cargador.idcargador);
        listaParametro.add(Constante.OBJETO_ACTIVO);
        try {
            lista = Reflection.getMapQueryResultByPreparedStatement(sql, listaParametro, connection);
        }
        catch (SQLException ex2) {}
        try {
            for (int indice = 0; indice < lista.size(); ++indice) {
                int codmeLista = 0;
                int idmedicocargador = 0;
                int idcargador = 0;
                try {
                    codmeLista = Integer.valueOf(lista.get(indice).get(".codme").toString());
                }
                catch (Exception ex3) {}
                try {
                    idmedicocargador = Integer.valueOf(lista.get(indice).get(".idmedicocargador").toString());
                }
                catch (Exception ex4) {}
                try {
                    idcargador = Integer.valueOf(lista.get(indice).get(".idcargador").toString());
                }
                catch (Exception ex5) {}
                String nombre = "Prestador";
                try {
                    nombre = lista.get(indice).get(".nombre").toString().trim();
                }
                catch (Exception ex6) {}
                json = new JSONObject();
                json.put("idmedicocargador", idmedicocargador).put("codme", codmeLista).put("nombre", (Object)nombre).put("idcargador", idcargador);
                jsonList.put((Object)json);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return jsonList;
    }
    
    public static JSONArray getCargador(final int codme, final Connection connection) {
        List<Map<String, Object>> lista = null;
        JSONObject json = null;
        final HashMap<Integer, JSONObject> map = new HashMap<Integer, JSONObject>();
        final JSONArray jsonList = new JSONArray();
        final String sql = "select * from cmt_cargador c inner join cmt_medicocargador mc on c.idcargador = mc.idcargador where mc.codme = ? and c.estado = ?";
        final ArrayList listaParametro = new ArrayList();
        listaParametro.add(codme);
        listaParametro.add(Constante.OBJETO_ACTIVO);
        try {
            lista = Reflection.getMapQueryResultByPreparedStatement(sql, listaParametro, connection);
        }
        catch (SQLException ex2) {}
        try {
            for (int indice = 0; indice < lista.size(); ++indice) {
                json = new JSONObject();
                json.put("idcargador", lista.get(indice).get(".idcargador")).put("dni", lista.get(indice).get(".dni")).put("nombre", (Object)lista.get(indice).get(".nombre").toString().trim()).put("telefono", lista.get(indice).get(".telefono"));
                map.put((Integer) lista.get(indice).get(".idcargador"), json);
            }
            final Set<Map.Entry<Integer, JSONObject>> setMapRepido = map.entrySet();
            for (final Map.Entry<Integer, JSONObject> entryMapRepido : setMapRepido) {
                jsonList.put((Object)entryMapRepido.getValue());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return jsonList;
    }
    
    public static JSONArray getCargadorRelacion(final int codme, final Connection connection) {
        List<Map<String, Object>> lista = null;
        JSONObject json = null;
        final JSONArray jsonList = new JSONArray();
        final String sql = "select c.dni, c.nombre, mc.fecha, mc.idmedicocargador, mc.estado from cmt_medicocargador mc left join cmt_cargador c on mc.idcargador = c.idcargador where mc.codme = ? and mc.estado in (?, ?)";
        final ArrayList listaParametro = new ArrayList();
        listaParametro.add(codme);
        listaParametro.add(Constante.OBJETO_ACTIVO);
        listaParametro.add(28);
        try {
            lista = Reflection.getMapQueryResultByPreparedStatement(sql, listaParametro, connection);
        }
        catch (SQLException ex2) {}
        int id = 0;
        long dni = 0L;
        String nombre = "";
        String fecha = "";
        try {
            for (int indice = 0; indice < lista.size(); ++indice) {
                try {
                    id = (int) lista.get(indice).get(".idmedicocargador");
                }
                catch (Exception ex3) {}
                try {
                    dni = (long) lista.get(indice).get(".dni");
                }
                catch (Exception ex4) {}
                try {
                    nombre = lista.get(indice).get(".nombre").toString().trim();
                }
                catch (Exception ex5) {}
                try {
                    fecha = lista.get(indice).get(".fecha").toString().trim();
                }
                catch (Exception ex6) {}
                json = new JSONObject();
                json.put("idmedicocargador", id).put("dni", dni).put("nombre", (Object)nombre).put("fecha", (Object)fecha).put("estado", lista.get(indice).get(".estado"));
                jsonList.put((Object)json);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return jsonList;
    }
    
    public static Object esMailCargador(final String emailMD5, final Connection connection) {
        List<Map<String, Object>> lista = null;
        final String sql = "select * from cmt_cargador";
        final ArrayList listaParametro = new ArrayList();
        try {
            lista = Reflection.getMapQueryResultByPreparedStatement(sql, listaParametro, connection);
        }
        catch (SQLException ex2) {}
        try {
            for (int indice = 0; indice < lista.size(); ++indice) {
                if (emailMD5.equals(MD5.getEncodedString(lista.get(indice).get(".mail").toString()))) {
                    return lista.get(indice).get(".idcargador");
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    
    public static boolean esRelacion(final int codme, final int idcargador, final Connection connection) {
        List<Map<String, Object>> lista = null;
        final String sql = "select * from cmt_medicocargador where idcargador = ? and codme = ? and estado = ?";
        final ArrayList listaParametro = new ArrayList();
        listaParametro.add(idcargador);
        listaParametro.add(codme);
        listaParametro.add(Constante.OBJETO_ACTIVO);
        try {
            lista = Reflection.getMapQueryResultByPreparedStatement(sql, listaParametro, connection);
        }
        catch (SQLException ex) {}
        return lista.size() > 0;
    }
}
