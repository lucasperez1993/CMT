// 
// Decompiled by Procyon v0.5.36
// 

package Models;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ModeloDomEspe extends AbstractTableModel
{
    public int colnum;
    private int rownum;
    private String[] colNames;
    private ArrayList<Object[]> registros;
    
    public ModeloDomEspe(final JSONArray arrayJson) {
        this.colnum = 3;
        this.colNames = new String[] { "Domicilio", "Horario", "Telefono" };
        this.registros = new ArrayList<Object[]>();
        try {
            for (int indice = 0; indice < arrayJson.length(); ++indice) {
                final JSONObject json = arrayJson.getJSONObject(indice);
                final Object[] row = { json.get("domicilio"), json.get("horario"), json.get("telefono") };
                this.registros.add(row);
            }
        }
        catch (Exception e) {
            System.out.println("Problemas en el modelo de tabla: Modelo Espe");
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
