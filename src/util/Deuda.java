package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lperez
 */
public class Deuda {

    public boolean tieneDeuda(int codme, Connection connection) throws SQLException {
        String sql2 = "SELECT * FROM gym_estados WHERE estado = ?";
        ArrayList arrayDeuda = new ArrayList();
        arrayDeuda.add(codme);
        List<Map<String, Object>> listaDeuda = Reflection.getMapQueryResultByPreparedStatement(sql2, arrayDeuda, connection);
        if (listaDeuda.size() >= 4) {
            return true;
        }
        return false;
    }
}