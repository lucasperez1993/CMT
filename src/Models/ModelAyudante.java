/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import cirugia.FormCirugia;
import cirugia.SqlCirugia;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author djaime
 */
public class ModelAyudante extends AbstractTableModel
{
    public int colnum;
    private int rownum;
    private String[] colNames;
    private ArrayList<Object[]> registros;
    
    public ModelAyudante(JSONArray arrayMatricula) {
        this.colnum = 3;
        this.colNames = new String[] {"Matric.", "Codme", "Nombre"};
        this.registros = new ArrayList<Object[]>();
        try {
            for(int indice = 0; indice < arrayMatricula.length(); indice++) {
                JSONObject map = arrayMatricula.getJSONObject(indice);
                final Object[] row = { 
                    map.get("matricula"),
                    map.get("codme"),
                    map.get("nombre")
                };
                this.registros.add(row);
            }
        }
        catch (Exception e) {
            System.out.println("Problemas en el modelo de tabla: Modelo Cirugias");
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