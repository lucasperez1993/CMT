
package objeto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import org.json.JSONObject;
import util.ImplementService;
import static util.ImplementService.createObjeto;
import static util.ImplementService.storeObjeto;
import static util.Reflection.getFechaHoy;

public class jdb_cirugiapractica extends ImplementService{
    public int idcirugiapractica;
    public String autorizacion;
    public int idusuario;
    public int idsanatorio;
    public int codme;
    public String afiliado;
    public String nombreafiliado;
    public String fecha;
    public LocalDate fechasolicitud;
    public LocalDate fechavto;
    public String nota;
    public String checkestado;
    public String hora;
    public String minuto;
    public String practicajson;
    public String ois;
    public int tipoobjeto;
    public int periodo;
    public int matricsol;
    public String ayudantejson;
    public int idorganizacion;
    public int estado;
    private int idestado;
    
    private static final String nombreClase = "objeto.jdb_cirugiapractica";

    public static int create(jdb_cirugiapractica objeto, Connection connection){
        return createObjeto(objeto, nombreClase, connection);
    }

    public static void store(jdb_cirugiapractica objeto, Connection connection){
        storeObjeto(objeto, nombreClase, connection);
    }

    public static jdb_cirugiapractica getObjetojdb_cirugiapractica(int id, Connection conn){
        return (jdb_cirugiapractica) ImplementService.getObjetoPorId(nombreClase, id,"", conn);
    }

    public static ArrayList<Object> getListajdb_cirugiapractica(String condicion, Connection conn){
        return ImplementService.getObjetoLista(nombreClase, condicion, conn);
    }

    public static JSONObject getListajdb_cirugiapracticaPorIdJson(int id, Connection conn){
        return ImplementService.getListaJsonPorId(nombreClase, id, "", nombreClase, conn);
    }

    public int getIdestado(){
        return idestado;
    }

//    public static JSONObject getAlljdb_cirugiapracticaJson(Connection conn){
//        return ImplementService.getAllObjetoJson("", nombreClase, CalculosFecha.DATE_FORMAT_D_M_Y, conn);
//    }
    public void setIdestado(int idestado) {
        this.idestado = idestado;
    }

    public static JSONObject createByJson(JSONObject data, Connection connection) throws Exception {
        jdb_cirugiapractica objeto = (jdb_cirugiapractica) ImplementService.getObjectByJSONOBJECT(nombreClase, new jdb_cirugiapractica(), data.getJSONObject("formulariojdb_cirugiapracticaJson"));
        int id = create(objeto, connection);
        return new JSONObject().put("jdb_cirugiapracticaJson", id);
    }
    
    public int createCirugia(jdb_cirugiapractica cirugia, Connection connection){
        int idcirugiapractica = 0;
        try {
            ResultSet key = null;
            String sql = "INSERT INTO jdb_cirugiapractica (idusuario, idsanatorio, codme, afiliado, nombreafiliado, fecha, "
                    + "fechasolicitud, fechavto, idorganizacion, estado, periodo) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, cirugia.idusuario);
            preparedStatement.setInt(2, cirugia.idsanatorio);
            preparedStatement.setInt(3, cirugia.codme);
            preparedStatement.setString(4, cirugia.afiliado);
            preparedStatement.setString(5, cirugia.nombreafiliado);
            preparedStatement.setTimestamp(6, getFechaHoy());
            preparedStatement.setString(7, cirugia.fechasolicitud.toString());
            preparedStatement.setString(8, cirugia.fechavto.toString());
            preparedStatement.setInt(9, cirugia.idorganizacion);
            preparedStatement.setInt(10, cirugia.estado);
            preparedStatement.setInt(11, cirugia.periodo);
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
    
    public static void modificarAyudante(jdb_cirugiapractica cirugia, Connection connection){
        try {
            String sql = "UPDATE jdb_cirugiapractica SET ayudantejson = ? WHERE idcirugiapractica = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, cirugia.ayudantejson);
            preparedStatement.setInt(2, cirugia.idcirugiapractica);
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }
    
    public static void modificarCirujano(jdb_cirugiapractica cirugia, Connection connection){
        try {
            String sql = "UPDATE jdb_cirugiapractica SET codme = ? WHERE idcirugiapractica = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, cirugia.codme);
            preparedStatement.setInt(2, cirugia.idcirugiapractica);
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }
    
    public static void modificarPractica(jdb_cirugiapractica cirugia, Connection connection){
        try {
            String sql = "UPDATE jdb_cirugiapractica SET practicajson = ? WHERE idcirugiapractica = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, cirugia.practicajson);
            preparedStatement.setInt(2, cirugia.idcirugiapractica);
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }
    
    public static boolean modificarCirugia(jdb_cirugiapractica cirugia, Connection connection){
        boolean exito = false;
        try {
            String sql = "UPDATE jdb_cirugiapractica SET idsanatorio = ?, codme = ?, afiliado = ?, nombreafiliado = ?,"
                    + " fechasolicitud = ?, fechavto = ?, estado = ?, hora = ?, minuto = ?, autorizacion = ? "
                    + "WHERE idcirugiapractica = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, cirugia.idsanatorio);
            preparedStatement.setInt(2, cirugia.codme);
            preparedStatement.setString(3, cirugia.afiliado);
            preparedStatement.setString(4, cirugia.nombreafiliado);
            preparedStatement.setString(5, cirugia.fechasolicitud.toString());
            preparedStatement.setString(6, cirugia.fechavto.toString());
            preparedStatement.setInt(7, cirugia.estado);
            preparedStatement.setObject(8, cirugia.hora);
            preparedStatement.setObject(9, cirugia.minuto);
            preparedStatement.setObject(10, cirugia.autorizacion);
            preparedStatement.setLong(11, cirugia.idcirugiapractica);
            preparedStatement.execute();
            exito = true;
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return exito;
    }
    
    public static void modificarTipoObjetoDocumento(int idcirugiapractica, int tipoobjeto, Connection connection){
        try {
            String sql = "UPDATE jdb_cirugiapractica SET tipoobjeto = ? WHERE idcirugiapractica = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, tipoobjeto);
            preparedStatement.setLong(2, idcirugiapractica);
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }
}