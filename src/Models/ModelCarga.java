/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Conexion.Conexion;
import cirugia.SqlCirugia;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import util.DigitoVerificador;

/**
 *
 * @author djaime
 */
public class ModelCarga extends AbstractTableModel {

    public int colnum;
    private int rownum;
    private String[] colNames;
    private ArrayList<Object[]> registros;

    public ModelCarga(final List<Map<String, Object>> array) {
        this.colnum = 10;
        this.colNames = new String[]{
            "USUARIO",
            "CUIL",
            "PACIENTE",
            "SANATORIO",
            "PRACTICA",
            "FECHA",
            "HORA",
            "FECHA VTO",
            "AUTORIZACION",
            "ESTADO"};
        Conexion conection = new Conexion();
        this.registros = new ArrayList<Object[]>();
        try {
            for (final Map<String, Object> map : array) {
                ArrayList<Object> listaPracticas = new ArrayList<>();
                JSONArray lis = null;
                String autorizacion = "";
                try {
                    autorizacion = map.get("autorizacion").toString();
                } catch (Exception e) {}
                int idusuario = Integer.valueOf(map.get("idusuario").toString());
                try {
                    lis = new JSONArray(map.get("practicajson").toString());
                } catch (Exception e) {
                    lis = new JSONArray();
                }
                for (int i = 0; i < lis.length(); i++) {
                    try {
                        JSONObject j = lis.getJSONObject(i);
                        listaPracticas.add(j.get("practica"));
                    } catch (Exception e) {
                        listaPracticas.add(000000);
                    }
                }
                String hora = map.get("hora").toString() + ":" + map.get("minuto");
                final Object[] row = {
                    SqlCirugia.getUsuarioPorId(idusuario, conection.GetConnection5()),
                    map.get("afiliado"),
                    map.get("nombreafiliado"),
                    map.get("sanatorio"),
                    listaPracticas.toString(),
                    map.get("fechasolicitud"),
                    hora,
                    map.get("fechavto"),
                    autorizacion,
                    map.get("estado")
                };
                this.registros.add(row);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public Object getValueAt(final int rowindex, final int columnindex) {
        final Object[] row = this.registros.get(rowindex);
        return row[columnindex];
    }

    @Override
    public int getRowCount() {
        return this.registros.size();
    }

    @Override
    public int getColumnCount() {
        return this.colnum;
    }

    @Override
    public String getColumnName(final int param) {
        return this.colNames[param];
    }
}
