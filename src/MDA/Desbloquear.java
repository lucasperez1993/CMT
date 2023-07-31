package MDA;

import java.sql.Connection;
import java.sql.SQLException;
import objeto.Cargador;
/**
 *
 * @author lperez
 */
public class Desbloquear {
    public Connection connection;
    Cargador objetoCargador;
    Dashboard dashboard;
    
    public Desbloquear() {
        this.connection = null;
    }
    
    public static void updateDesbloqueo(Cargador objetoCargador, Connection connection) {
        try {
            String update = "UPDATE Usuario SET EstadoId = 1 WHERE NroSocio = '" + objetoCargador.codme + "'";
            connection.createStatement().execute(update);
        }
        catch (SQLException ex) {}
    }
}
