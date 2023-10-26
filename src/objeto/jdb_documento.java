
package objeto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONObject;
import util.ImplementService;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import otro.ConstanteObjeto;
import static util.ImplementService.createObjeto;
import static util.ImplementService.storeObjeto;
import static util.Reflection.getFechaHoy;

public class jdb_documento extends ImplementService{
    public Long iddocumento;
    public Long idusuario;
    public Long idcirugiapractica;
    public String contenttype;
    public String extension;
    public LocalDateTime fechadocumento;
    public String descripcion;
    public String documento;
    public String path;
    public int idlineahistoriaclinica;
    public int tipoobjeto;
    public int codme;
    public int idpaciente;
    public String predeterminada;
    public int idorganizacion;
    public int estado;
        
    private static final String nombreClase = "objeto.jdb_documento";

    public static int create(jdb_documento objeto, Connection connection){
        return createObjeto(objeto, nombreClase, connection);
    }

    public static void store(jdb_documento objeto, Connection connection){
        storeObjeto(objeto, nombreClase, connection);
    }

    public static jdb_documento getObjetojdb_documento(int id, Connection conn){
        return (jdb_documento) ImplementService.getObjetoPorId(nombreClase, id,"", conn);
    }

    public static ArrayList<Object> getListajdb_documento(String condicion, Connection conn){
        return ImplementService.getObjetoLista(nombreClase, condicion, conn);
    }

    public static JSONObject getListajdb_documentoPorIdJson(int id, Connection conn){
        return ImplementService.getListaJsonPorId(nombreClase, id, "", nombreClase, conn);
    }

    public static JSONObject createByJson(JSONObject data, Connection connection) throws Exception{
        jdb_documento objeto = (jdb_documento) ImplementService.getObjectByJSONOBJECT(nombreClase, new jdb_documento(), data.getJSONObject("formulariojdb_documentoJson"));
        int id = create(objeto, connection);
        return new JSONObject().put("jdb_documentoJson", id);
    }
    
    public static jdb_documento prepareDocumento(
            long idusuario, 
            long idcirugiapractica, 
            String contenttype, 
            String extension, 
            String descripcion, 
            String _documento, 
            String path, 
            int tipoobjeto, 
            int codme){
        jdb_documento documento = new jdb_documento();
        documento.idusuario = idusuario;
        documento.idcirugiapractica = idcirugiapractica;
        documento.contenttype = contenttype;
        documento.extension = extension;
        documento.descripcion = descripcion;
        documento.documento = _documento;
        documento.path = path;
        documento.idlineahistoriaclinica = 0;
        documento.tipoobjeto = tipoobjeto;
        documento.codme = codme;
        documento.idpaciente = 0;
        documento.predeterminada = "";
        return documento;
    }
    
    public int createDocumento(jdb_documento documento, Connection connection){
        int idcirugiapractica = 0;
        try {
            ResultSet key = null;
            String sql = "INSERT INTO jdb_documento (idusuario, idcirugiapractica, contenttype, extension, fechadocumento, descripcion, "
                    + "documento, path, idlineahistoriaclinica, tipoobjeto, codme, idpaciente, predeterminada, idorganizacion, estado) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, documento.idusuario);
            preparedStatement.setObject(2, documento.idcirugiapractica);
            preparedStatement.setObject(3, documento.contenttype);
            preparedStatement.setObject(4, documento.extension);
            preparedStatement.setObject(5, getFechaHoy());
            preparedStatement.setObject(6, documento.descripcion);
            preparedStatement.setObject(7, documento.documento);
            preparedStatement.setObject(8, documento.path);
            preparedStatement.setObject(9, documento.idlineahistoriaclinica);
            preparedStatement.setObject(10, documento.tipoobjeto);
            preparedStatement.setObject(11, documento.codme);
            preparedStatement.setObject(12, documento.idpaciente);
            preparedStatement.setObject(13, documento.predeterminada);
            preparedStatement.setObject(14, 2);
            preparedStatement.setObject(15, ConstanteObjeto.OBJETO_ACTIVO);
            preparedStatement.execute();
            key = preparedStatement.getGeneratedKeys();
            if (key.next()) {
                return key.getInt(1);
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return idcirugiapractica;
    }
    
    public static void modificarDocumento(jdb_documento documento, Connection connection){
        try {
            String sql = "UPDATE jdb_documento SET descripcion = ?, documento = ?, path = ? WHERE iddocumento = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, documento.descripcion);
            preparedStatement.setString(2, documento.documento);
            preparedStatement.setString(3, documento.path);
            preparedStatement.setLong(4, documento.iddocumento);
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }
    
    public static void bajaDocumento(int iddocumento, Connection connection){
        try {
            String sql = "UPDATE jdb_documento SET estado = ? WHERE iddocumento = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, 0);
            preparedStatement.setLong(2, iddocumento);
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }
}