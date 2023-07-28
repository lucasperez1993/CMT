// 
// Decompiled by Procyon v0.5.36
// 

package Models;

import org.json.JSONArray;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ModeloRelacionMedicoCargador extends AbstractTableModel
{
    public int colnum;
    private int rownum;
    private String[] colNames;
    private ArrayList<Object[]> registros;
    
    public ModeloRelacionMedicoCargador(final JSONArray listaPrestadorJson) {
        this.colnum = 2;
        this.colNames = new String[] { "N° Socio", "Nombre" };
        this.registros = new ArrayList<Object[]>();
        try {
            for (int indice = 0; indice < listaPrestadorJson.length(); ++indice) {
                final Object[] row = { listaPrestadorJson.getJSONObject(indice).get("codme"), listaPrestadorJson.getJSONObject(indice).get("nombre") };
                this.registros.add(row);
            }
        }
        catch (Exception e) {
            System.out.println("Problemas en el modelo de tabla: Modelo RelacionMedicoCargador");
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
