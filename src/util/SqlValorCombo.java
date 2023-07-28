package util;

import MDA.Dashboard;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import org.json.JSONArray;
import org.json.JSONObject;

public class SqlValorCombo {
    public Connection connection = null;
    private java.sql.Statement sentencia;
    private java.sql.Statement sentencia2;
    private ResultSet resultado;

    public SqlValorCombo() throws Exception {
        Conexion con = new Conexion();
        connection = con.GetConnection();
        sentencia = connection.createStatement();
        sentencia2 = connection.createStatement();
    }

    public void cerrarConexion() {
//        mysql.close(resultado);
    }

    public ResultSet comboSeleccionado(String tabla, String campo, String opcional) throws Exception {
        if (opcional.equalsIgnoreCase("1")) {
            resultado = sentencia.executeQuery("select " + campo + " from " + tabla + " GROUP BY " + campo + "");
        } else {
            resultado = sentencia.executeQuery("select " + campo + "," + opcional + " from " + tabla + " ");
        }

        return resultado;
    }

    /**
     *
     * @param tabla = nombre de la tabla
     * @param campo = campo resultado
     * @param campo1 = campo para filtrar
     * @param filtro = valor del filtro
     * @return Retorna el codigo, o un valor asociado del item, seleccionado
     * desde el combo
     * @throws java.lang.Exception
     */
    public String seleccionarDesdeCombo(String tabla, String campo, String campo1, String filtro) throws Exception {
        String resultado = null;
        try {
            ResultSet consulta;
//         System.out.println("select "+campo+" from "+tabla+" where "+campo1+" = '"+filtro+"'");
            consulta = sentencia.executeQuery("select " + campo + " from " + tabla + " where " + campo1 + " = '" + filtro + "'");
            consulta.first();
            resultado = consulta.getString(campo);
        } catch (Exception e) {
//             System.err.println("Este error es en la clase sqlcombo metodo seleccionardesdecombo");
        }
        return resultado;
    }

    public String seleccionarDesdeCombo2(String tabla, String campo, String campo1, String filtro) throws Exception {
        String resultado = null;
        try {
            ResultSet consulta;
            System.out.println("select " + campo + " from " + tabla + " where " + campo1 + " = '" + filtro + "'");
            consulta = sentencia.executeQuery("select " + campo + " from " + tabla + " where " + campo1 + " = '" + filtro + "' and tipo=1");
            consulta.first();
            resultado = consulta.getString(campo);
        } catch (Exception e) {
            System.err.println("Este error es en la clase sqlcombo metodo seleccionardesdecombo");
        }
        return resultado;
    }

    /*
     * @param combo @param tabla @param campo @return El combo completo
     * <li>Ejemplo combo = SqlValorCombo.llenarCombo(combo,persona,nombre);
     * @throws java.lang.Exception
     */
    public JComboBox llenarComboVehiculo(JComboBox combo, String tabla, String campo) throws Exception {
        try {
            ResultSet resultado = sentencia.executeQuery("select " + campo + " from " + tabla + " WHERE estado=1 order by '" + campo + "'");
            resultado.first();
            combo.removeAllItems();
            combo.addItem(resultado.getString(campo));
            while (resultado.next()) {
                combo.addItem(resultado.getString(campo));
            }
        } catch (Exception e) {
            System.err.println("Error en la clase llenarcombo en el metodo llenarcombovehiculo");
        }
        return combo;
    }

    public JComboBox llenarComboEmpleado(JComboBox combo, String tabla, String campo) throws Exception {
        ResultSet resultado = sentencia.executeQuery("select " + campo + " from " + tabla + " order by '" + campo + "'");
        resultado.first();
        combo.removeAllItems();
        combo.addItem(resultado.getString(campo));
        while (resultado.next()) {
            combo.addItem(resultado.getString(campo));
        }
        return combo;
    }

    public JComboBox llenarCombo(JComboBox combo, String tabla, String campo) throws Exception {
        ResultSet resultado = sentencia2.executeQuery("select " + campo + " from " + tabla + "  GROUP BY " + campo + " order by '" + campo + "'");
        resultado.first();
        combo.removeAllItems();
        combo.addItem(resultado.getString(campo));
        while (resultado.next()) {
            combo.addItem(resultado.getString(campo));
        }
        return combo;
    }

    public JComboBox llenarComboMenosAtributo(JComboBox combo, String tabla, String campo, String atributo) throws Exception {
        ResultSet resultado = sentencia2.executeQuery("select " + campo + " from " + tabla + " where id_cat != '" + atributo + "' and detalle != 'borradopordamian123' GROUP BY " + campo + " order by '" + campo + "'");
        resultado.first();
        combo.removeAllItems();
        combo.addItem(resultado.getString(campo));
        while (resultado.next()) {
            combo.addItem(resultado.getString(campo));
        }
        return combo;
    }

    public JComboBox llenarComboMarca(JComboBox combo, String tabla, String campo) throws Exception {
        ResultSet resultado = sentencia.executeQuery("select " + campo + " from " + tabla + " where tipo = '0' GROUP BY " + campo + "  order by '" + campo + "' ");
        resultado.first();
        combo.removeAllItems();
        combo.addItem(resultado.getString(campo));
        while (resultado.next()) {
            combo.addItem(resultado.getString(campo));
        }
        return combo;
    }

    public JComboBox llenarComboMarca2(JComboBox combo, String tabla, String campo) throws Exception {
        ResultSet resultado = sentencia.executeQuery("select " + campo + " from " + tabla + " where tipo = '1' GROUP BY " + campo + "  order by '" + campo + "' ");
        resultado.first();
        combo.removeAllItems();
        combo.addItem(resultado.getString(campo));
        while (resultado.next()) {
            combo.addItem(resultado.getString(campo));
        }
        return combo;
    }

    public JComboBox llenarComboLocalidad(JComboBox combo, String campo) throws Exception {
//         System.out.println                          ("SELECT  l.nombre FROM localidades l, provincias p, departamentos d WHERE p.id = d.provincia_id and d.id = l.departamento_id  and p.nombre='"+campo+"'");
        ResultSet resultado = sentencia.executeQuery("SELECT  l.nombre FROM localidades l, provincias p, departamentos d WHERE p.id = d.provincia_id and d.id = l.departamento_id  and p.nombre='" + campo + "'");
        resultado.first();
        combo.removeAllItems();
        combo.addItem(resultado.getString("l.nombre"));
        while (resultado.next()) {
            combo.addItem(resultado.getString("l.nombre"));
        }
        return combo;
    }

    public JComboBox llenarComboEspecifico(JComboBox combo, String tabla, String campo, int filtro) throws Exception {
        System.out.println("select " + tabla + "." + campo + " from " + tabla + ", provincias where provincias.id = '" + filtro + "' GROUP BY " + campo + " order by '" + campo + "'");
        ResultSet resultado = sentencia.executeQuery("select " + tabla + "." + campo + " from " + tabla + ", provincias where provincias.id = '" + filtro + "' GROUP BY " + campo + " order by '" + campo + "'");
        resultado.first();
        combo.removeAllItems();
        combo.addItem(resultado.getString(campo));
        while (resultado.next()) {
            combo.addItem(resultado.getString(campo));
        }
        return combo;
    }

    public JComboBox llenarComboEspesorPoliuretano(JComboBox combo, String tabla, String campo) throws Exception {
        ResultSet resultado = sentencia.executeQuery("select " + campo + ",preciocostoEspesor from " + tabla + " where poliuretano='si' GROUP BY " + campo + " order by idespesor");
        resultado.first();
        combo.removeAllItems();
        combo.addItem(resultado.getString(campo));
        while (resultado.next()) {
            combo.addItem(resultado.getString(campo));
        }
        return combo;
    }

    public JComboBox llenarComboEspesorPoliestireno(JComboBox combo, String tabla, String campo) throws Exception {
        ResultSet resultado = sentencia.executeQuery("select " + campo + ",preciocostoEspesor from " + tabla + " where poliestireno='si' GROUP BY " + campo + " order by idespesor");
        resultado.first();
        combo.removeAllItems();
        combo.addItem(resultado.getString(campo));
        while (resultado.next()) {
            combo.addItem(resultado.getString(campo));
        }
        return combo;
    }

    public JComboBox llenarComboPiso(JComboBox combo, String tabla, String campo) throws Exception {
        ResultSet resultado = sentencia.executeQuery("select " + campo + " from " + tabla + " where idPiso in(2,5) GROUP BY " + campo + "");
        resultado.first();
        combo.removeAllItems();
        combo.addItem(resultado.getString(campo));
        while (resultado.next()) {
            combo.addItem(resultado.getString(campo));
        }
        return combo;
    }

    public JComboBox llenarComboRevestimiento(JComboBox combo, String tabla, String campo) throws Exception {
        ResultSet resultado = sentencia.executeQuery("select " + campo + " from " + tabla + " GROUP BY " + campo + "");
        resultado.first();
        combo.removeAllItems();
        combo.addItem("Revestimiento");
        combo.addItem(resultado.getString(campo));
        while (resultado.next()) {
            combo.addItem(resultado.getString(campo));
        }
        return combo;
    }

    /**
     *
     * @param combo
     * @param tabla
     * @param campo
     * @return Todos los elementos de la tabla con estado activo (estado = 1).
     * <li>Ejemplo combo = SqlValorCombo.llenarCombo(combo,persona,nombre);
     * @throws java.lang.Exception
     */
    public JComboBox llenarComboConEstado(JComboBox combo, String tabla, String campo) throws Exception {
        try {
            ResultSet resultado = sentencia.executeQuery("select " + campo + " from " + tabla + " where estado = '1' GROUP BY " + campo + " asc");
            resultado.first();
            combo.removeAllItems();
            combo.addItem(resultado.getString(campo));
            while (resultado.next()) {
                combo.addItem(resultado.getString(campo));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return combo;
    }

    public JComboBox llenarComboConJoin(JComboBox combo, String idCtaCte, String campoAMostrar) throws Exception {

        ResultSet resu = sentencia.executeQuery("SELECT p." + campoAMostrar + " FROM proveedor p, lineacuentacorriente lcc WHERE p.idproveedor = lcc.idproveedor AND lcc.idcuentacorriente ='" + idCtaCte + "' group by p.razonsocial");
        resu.first();
        combo.removeAllItems();
        combo.addItem("TODOS");
        combo.addItem(resu.getString(campoAMostrar));
        while (resu.next()) {
            combo.addItem(resu.getString(campoAMostrar));
        }
        return combo;
    }
    
    public static JSONObject llenarComboLocalidadByProvincia(String campo, Connection connection) throws Exception {
        JSONObject json;
        JSONArray jsonArray = new JSONArray();
        ResultSet resultado = connection.createStatement().executeQuery("SELECT  l.id, l.nombre FROM localidades l, provincias p, departamentos d WHERE p.id = d.provincia_id and d.id = l.departamento_id  and p.nombre='" + campo + "'");
        
        if(resultado.absolute(1)){
            resultado.first();
            json = new JSONObject();
            json.put("id",resultado.getInt("l.id"));
            json.put("nombre",resultado.getString("l.nombre"));
            jsonArray.put(json);
            while (resultado.next()) {
                json = new JSONObject();
                json.put("id",resultado.getString("l.id"));
                json.put("nombre",resultado.getString("l.nombre"));
                jsonArray.put(json);
            }
        }
        return new JSONObject().put("listaLocalidades", jsonArray);
    }
    
    public static List<Map<String, Object>> llenarComboPor(String sql, JComboBox combo, String campo, Connection connection) {
        List<Map<String, Object>> lista = null;
        try {
            ArrayList arrayCombo = new ArrayList();
            lista = Reflection.getMapQueryResultByPreparedStatement(sql, arrayCombo, connection);
            if (lista.size() > 0) {
                combo.removeAllItems();
                for (int i = 0; i < lista.size(); ++i) {
                    combo.addItem(lista.get(i).get("." + campo).toString().trim());
                }
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
