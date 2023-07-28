// 
// Decompiled by Procyon v0.5.36
// 

package Models;

import MDA.Adjunto;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ModeloAdjunto extends AbstractTableModel
{
    public int colnum;
    private int rownum;
    private String[] colNames;
    private ArrayList<Object[]> registros;
    
    public ModeloAdjunto(final ArrayList<Adjunto> listaAdjunto) {
        this.colnum = 1;
        this.colNames = new String[] { "Archivos Adjuntos" };
        this.registros = new ArrayList<Object[]>();
        try {
            for (final Adjunto ad : listaAdjunto) {
                final Object[] row = { ad.nombre };
                this.registros.add(row);
            }
        }
        catch (Exception e) {
            System.out.println("Problemas en el modelo de tabla: Modelo Adjunto");
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
