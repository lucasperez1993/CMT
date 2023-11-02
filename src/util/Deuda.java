package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author lperez
 */
public class Deuda {

    public boolean tieneDeuda(int pre_id, Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) cuotas FROM pnegau WHERE codng = 3 AND pre_id = '" + pre_id + "' AND estag = ''";
        ArrayList arrayDeuda = new ArrayList();
        List<Map<String, Object>> lista = Reflection.getMapQueryResultByPreparedStatement(sql, arrayDeuda, connection);
        int cuotas = Integer.valueOf(lista.get(0).get(".cuotas").toString());
        if (cuotas >= 4) {
            return true;
        }
        return false;
    }

    public boolean tieneDeudaPorIngreso(int numdoc, Connection connection) throws SQLException {
        int pre_id = 0;
        String sql1 = "SELECT ISNULL(codme, 0) as codme FROM gym_adh_inscripto "
                + "WHERE numdoc = ? ";
        ArrayList arrayAdh = new ArrayList();
        List<Map<String, Object>> listaAdh = Reflection.getMapQueryResultByPreparedStatement(sql1, arrayAdh, connection);
        int codme = Integer.valueOf(listaAdh.get(0).get(".codme").toString());
        if (listaAdh.size() > 0) {
            if (codme == 0) {
                JOptionPane.showMessageDialog(null, "El Socio debe actualizar datos del grupo familiar.", "Mensaje del Sistema", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String sql2 = "SELECT pre_id FROM prestadores WHERE codme = ? AND tsocio <= 4 AND codme <= 75000 AND entidad = 0";
                ArrayList arrayAdh2 = new ArrayList();
                arrayAdh2.add(codme);
                List<Map<String, Object>> listaAdh2 = Reflection.getMapQueryResultByPreparedStatement(sql2, arrayAdh2, connection);
                if (listaAdh2.size() > 0) {
                    pre_id = Integer.valueOf(listaAdh2.get(0).get(".pre_id").toString());
                    String sql3 = "SELECT COUNT(*) cuotas FROM pnegau WHERE codng = 3 AND pre_id = '" + pre_id + "' AND estag = ''";
                    ArrayList arrayDeuda = new ArrayList();
                    List<Map<String, Object>> lista = Reflection.getMapQueryResultByPreparedStatement(sql3, arrayDeuda, connection);
                    int cuotas = Integer.valueOf(lista.get(0).get(".cuotas").toString());
                    if (cuotas >= 4) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
