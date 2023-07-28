// 
// Decompiled by Procyon v0.5.36
// 

package Models;

import java.util.Iterator;
import objeto.cmt_motivomesa;
import java.sql.Connection;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ModeloFtermino extends AbstractTableModel
{
    public int colnum;
    private int rownum;
    private String[] colNames;
    private ArrayList<Object[]> registros;
    
    public ModeloFtermino(List<Map<String, Object>> array, Connection conn) {
        this.colnum = 4;
        this.colNames = new String[] { "N° Socio", "Fecha", "Motivo", "Estado" };
        this.registros = new ArrayList<Object[]>();
        try {
            String estado = "";
            for (final Map<String, Object> map : array) {
                //cmt_motivomesa m = cmt_motivomesa.getObjetocmt_motivomesa(Integer.valueOf(map.get(".id_motivo").toString()), conn);
                switch (Integer.valueOf(map.get(".estado").toString())) {
                    case 1: {
                        estado = "Atendió";
                        break;
                    }
                    case 2: {
                        estado = "No atendió";
                        break;
                    }
                    case 3: {
                        estado = "Fuera de Servicio";
                        break;
                    }
                    case 4: {
                        estado = "Enviado por WhatsApp";
                        break;
                    }
                }
                Object[] row = { map.get(".codme"), map.get(".fecha"),map.get(".motivo"), estado };
                registros.add(row);
            }
        }
        catch (Exception e) {
            System.out.println("Problemas en el modelo de tabla: Modelo Ftermino");
        }
    }
    
    @Override
    public Object getValueAt(int rowindex, int columnindex) {
        Object[] row = registros.get(rowindex);
        return row[columnindex];
    }
    
    @Override
    public int getRowCount() {
        return registros.size();
    }
    
    @Override
    public int getColumnCount() {
        return colnum;
    }
    
    @Override
    public String getColumnName(final int param) {
        return colNames[param];
    }
}
