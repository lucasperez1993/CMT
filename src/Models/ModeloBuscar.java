// 
// Decompiled by Procyon v0.5.36
// 

package Models;

import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ModeloBuscar extends AbstractTableModel
{
    public int colnum;
    private int rownum;
    private String[] colNames;
    private ArrayList<Object[]> registros;
    
    public ModeloBuscar(final List<Map<String, Object>> array) {
        this.colnum = 2;
        this.colNames = new String[] { "N° Socio / Usuario", "Nombre" };
        this.registros = new ArrayList<Object[]>();
        try {
            for (final Map<String, Object> map : array) {
                final Object[] row = { map.get(".codme"), map.get(".nombre") };
                this.registros.add(row);
            }
        }
        catch (Exception e) {
            System.out.println("Problemas en el modelo de tabla: Modelo Buscar");
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
