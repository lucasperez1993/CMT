// 
// Decompiled by Procyon v0.5.36
// 

package objeto;

import Conexion.Conexion;
import java.text.ParseException;
import java.util.Date;
import otro.FechaHora;
import java.text.SimpleDateFormat;
import otro.Constante;
import java.sql.SQLException;
import org.json.JSONException;
import util.Reflection;
import org.json.JSONObject;
import java.util.ArrayList;
import java.sql.Connection;
import java.util.Map;
import java.util.List;
import util.ImplementService;

public class jdb_permiso extends ImplementService
{
    public int idpermiso;
    public int idorganizacion;
    public int idusuario;
    public String permiso;
    private List<Map<String, Object>> listaComboDelegar;
    private List<Map<String, Object>> listaComboDelegado;
    private static final String nombreClase = "objeto.jdb_permiso";
    
    public static int create(final jdb_permiso objeto, final Connection connection) {
        return createObjeto((Object)objeto, "objeto.jdb_permiso", connection);
    }
    
    public static void store(final jdb_permiso objeto, final Connection connection) {
        storeObjeto((Object)objeto, "objeto.jdb_permiso", connection);
    }
    
    public static jdb_permiso getObjetoPermiso(final int id, final Connection conn) {
        return (jdb_permiso)ImplementService.getObjetoPorId("objeto.jdb_permiso", id, "", conn);
    }
    
    public static jdb_permiso getObjetoPermisoPorIdUsuario(final int id, final Connection conn) {
        return (jdb_permiso)ImplementService.getObjetoPorId("objeto.jdb_permiso", -1, "or idusuario = " + id, conn);
    }
    
    public static ArrayList<Object> getListaPermiso(final String condicion, final Connection conn) {
        return (ArrayList<Object>)ImplementService.getObjetoLista("objeto.jdb_permiso", condicion, conn);
    }
    
    public static JSONObject getListaPermisoPorIdJson(final int id, final Connection conn) {
        return ImplementService.getListaJsonPorId("objeto.jdb_permiso", id, "", "objeto.jdb_permiso", conn);
    }
    
    public static void agregarPermisoMasivo_CENTRO_MEDICO(final Connection connection) throws JSONException, SQLException {
        final String sql = "SELECT * FROM Usuario WHERE nrosocio IN (SELECT codme FROM cmt_centromedico WHERE estado = 1) AND Tipoobjetoid = ?";
        final ArrayList<Object> arrayParametro = new ArrayList<Object>();
        arrayParametro.add(2);
        final List<Map<String, Object>> lista = Reflection.getMapQueryResultByPreparedStatement(sql, arrayParametro, connection);
        final ArrayList<Object> listaObject = Usuario.getListaUsuario("where estadoid = 1 and tipoobjetoid IN (2)", connection);
        for (int indice = 0; indice < lista.size(); ++indice) {
            try {
                final jdb_permiso objetoPermiso = getObjetoPermisoPorIdUsuario(Integer.valueOf(lista.get(indice).get(".Id").toString()), connection);
                System.out.println("Codme:" + lista.get(indice).get(".NroSocio"));
                final JSONObject jsonPermiso = new JSONObject(objetoPermiso.permiso);
                jsonPermiso.put("permiso.modificar.Ultimo Recibo de Pago", true);
                objetoPermiso.permiso = jsonPermiso.toString();
                store(objetoPermiso, connection);
            }
            catch (Exception ex) {}
        }
    }
    
    public static void agregarPermisoMasivo_CMT(final Connection connection) throws JSONException, SQLException {
        final String sql = "SELECT * FROM Usuario WHERE Tipoobjetoid = ?";
        final ArrayList<Object> arrayParametro = new ArrayList<Object>();
        arrayParametro.add(Constante.TIPO_OBJETO_MEDICO);
        final List<Map<String, Object>> lista = Reflection.getMapQueryResultByPreparedStatement(sql, arrayParametro, connection);
        final ArrayList<Object> listaObject = Usuario.getListaUsuario("where estadoid = 1 and tipoobjetoid IN (1)", connection);
        for (int indice = 0; indice < lista.size(); ++indice) {
            if (Integer.valueOf(lista.get(indice).get(".Id").toString()) == 5652) {
                try {
                    final jdb_permiso objetoPermiso = getObjetoPermisoPorIdUsuario(Integer.valueOf(lista.get(indice).get(".Id").toString()), connection);
                    System.out.println("Codme:" + lista.get(indice).get(".NroSocio"));
                    final JSONObject jsonPermiso = new JSONObject(objetoPermiso.permiso);
                    jsonPermiso.put("permiso.modificar.Consultas", true);
                    jsonPermiso.put("permiso.modificar.Practicas", true);
                    jsonPermiso.put("permiso.modificar.Nomenclador", true);
                    objetoPermiso.permiso = jsonPermiso.toString();
                    store(objetoPermiso, connection);
                }
                catch (Exception ex) {}
            }
        }
    }
    
    public static boolean getPermisoPorDelegacion(final JSONObject jsonParametros, final String tipoPermiso, final Connection connection) throws JSONException, ParseException {
        if (!jsonParametros.getBoolean(Constante.NIVEL_DELEGACION)) {
            return getPermisoPorParametro(jsonParametros, tipoPermiso);
        }
        final SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaDateHoy = null;
        fechaDateHoy = formateador.parse(FechaHora.getFechaFoormat());
        Date fechaDateFin = null;
        Date fechaDateInicio = null;
        fechaDateFin = formateador.parse(jsonParametros.getString(Constante.NIVEL_FECHA_FIN_DELEGACION));
        fechaDateInicio = formateador.parse(jsonParametros.getString(Constante.NIVEL_FECHA_INICIO_DELEGACION));
        if (!fechaDateHoy.after(fechaDateInicio)) {
            return false;
        }
        if (fechaDateHoy.before(fechaDateFin)) {
            final jdb_permiso permiso = getObjetoPermiso(jsonParametros.getInt("VEREMOS MAS ADELANTE"), connection);
            return getPermisoPorParametro(new JSONObject(permiso.permiso), tipoPermiso);
        }
        final JSONObject json = new JSONObject(jsonParametros.toString());
        return json.getBoolean(tipoPermiso);
    }
    
    public static boolean getPermisoPorParametro(final JSONObject jsonParametros, final String tipoPermiso) {
        try {
            return jsonParametros.getBoolean(tipoPermiso);
        }
        catch (JSONException ex) {
            return false;
        }
    }
    
    public static void actualizarMenuItem(final int idPermiso, final JSONObject jsonPermiso, final Connection connection) throws JSONException {
        final jdb_permiso objetoPermiso = getObjetoPermiso(idPermiso, connection);
        jsonPermiso.put("permiso.modificar.Preliquidacion", true);
        jsonPermiso.put("permiso.eliminar.Preliquidacion", true);
        objetoPermiso.permiso = jsonPermiso.toString();
    }
    
    public static void mainPermiso(final String[] arg) throws JSONException {
        final JSONObject jsonPermiso = new JSONObject("{\"permiso.modificar.Admin-Permiso\":false,\"permiso.modificar.Novedad\":false,\"permiso.eliminar.Buscar-Anular\":true,\"permiso.eliminar.Autorizar\":true,\"permiso.modificar.Auditoria-Medica\":true,\"permiso.modificar.Socio\":false,\"permiso.eliminar.Movimiento\":false,\"permiso.eliminar.Socio\":false,\"permiso.modificar.Mensaje\":false,\"nivel.id.organizacion\":2,\"permiso.modificar.Obras-Sociales\":true,\"permiso.eliminar.Buscar-Autorizado\":true,\"permiso.modificar.Caja\":false,\"permiso.eliminar.AfiliadoSS\":true,\"fechaInicioDelegacion\":\"28-07-2016\",\"permiso.eliminar.Resumun-Facturacion\":true,\"permiso.modificar.Delegar-Usuario\":true,\"permiso.eliminar.Obras-Sociales\":true,\"permiso.eliminar.Usuario\":false,\"permiso.modificar.Autorizar\":true,\"permiso.modificar.Grupo\":false,\"permiso.modificar.Reporte\":false,\"permiso.eliminar.Inscripcion\":false,\"permiso.modificar.Profecionales\":false,\"permiso.eliminar.Grupo\":false,\"permiso.modificar.Convenios\":true,\"permiso.eliminar.Profecionales\":false,\"permiso.eliminar.Novedad\":false,\"permiso.eliminar.Valores\":true,\"permiso.modificar.Organizacion\":false,\"permiso.eliminar.HC\":false,\"nivelcrearusuario\":2345,\"delegacion\":false,\"permiso.modificar.Valores\":true,\"permiso.eliminar.Permiso\":false,\"permiso.eliminar.Delegar-Usuario\":true,\"permiso.eliminar.Turno\":false,\"permiso.modificar.Certificacion\":false,\"fechaFinDelegacion\":\"28-07-2016\",\"permiso.eliminar.Convenios\":true,\"permiso.modificar.Usuario\":false,\"permiso.eliminar.Reporte\":false,\"permiso.modificar.Inscripcion\":false,\"permiso.eliminar.Profesional\":false,\"permiso.modificar.Permiso\":false,\"permiso.modificar.Resumun-Facturacion\":true,\"permiso.eliminar.Caja\":false,\"permiso.modificar.Profesional\":false,\"permiso.eliminar.Certificacion\":false,\"permiso.modificar.AfiliadoSS\":true,\"permiso.modificar.Turno\":false,\"permiso.modificar.Buscar-Anular\":true,\"permiso.eliminar.Organizacion\":false,\"permiso.eliminar.Validar\":true,\"permiso.modificar.HC\":false,\"permiso.modificar.Buscar-Autorizado\":true,\"permiso.modificar.Movimiento\":false,\"permiso.eliminar.Auditoria-Medica\":true,\"permiso.eliminar.Circuito\":false,\"permiso.modificar.Circuito\":false,\"permiso.modificar.Validar\":true}");
        jsonPermiso.put("permiso.modificar.Subir-Archivo", true);
        jsonPermiso.put("permiso.eliminar.Subir-Archivo", true);
        jsonPermiso.put("permiso.modificar.Planilla", true);
        jsonPermiso.put("permiso.eliminar.Planilla", true);
        jsonPermiso.put("permiso.modificar.Clave", true);
        jsonPermiso.put("permiso.eliminar.Clave", true);
        jsonPermiso.put("permiso.modificar.Comprobantes", true);
        jsonPermiso.put("permiso.eliminar.Comprobantes", true);
        System.out.println(jsonPermiso.toString());
    }
    
    public static String getListaString() {
        return "";
    }
    
    public static void main(final String[] arg) throws JSONException, SQLException {
        final Conexion init = new Conexion();
        final Connection connection = init.GetConnectionCloud();
        agregarPermisoModoCelular_CENTRO_MEDICO(connection);
    }
    
    public static void agregarPermisoModoCelular_CENTRO_MEDICO(final Connection connection) throws JSONException, SQLException {
        final String sql = "SELECT * FROM Usuario WHERE nrosocio IN (SELECT codme FROM cmt_centromedico WHERE estado = 1) AND Tipoobjetoid = ?";
        final ArrayList<Object> arrayParametro = new ArrayList<Object>();
        arrayParametro.add(2);
        final List<Map<String, Object>> lista = Reflection.getMapQueryResultByPreparedStatement(sql, arrayParametro, connection);
        final ArrayList<Object> listaObject = Usuario.getListaUsuario("where estadoid = 1 and tipoobjetoid IN (2)", connection);
        for (int indice = 0; indice < lista.size(); ++indice) {
            try {
                final jdb_permiso objetoPermiso = getObjetoPermisoPorIdUsuario(Integer.valueOf(lista.get(indice).get(".Id").toString()), connection);
                System.out.println("Codme:" + lista.get(indice).get(".NroSocio"));
                final JSONObject jsonPermiso = new JSONObject(objetoPermiso.permiso);
                jsonPermiso.put("permiso.modificar.Modo-Celular", true);
                objetoPermiso.permiso = jsonPermiso.toString();
                store(objetoPermiso, connection);
            }
            catch (Exception ex) {}
        }
    }
}
