package Models;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ModeloConsulta extends AbstractTableModel
{
    public int colnum;
    private int rownum;
    private String[] colNames;
    private ArrayList<Object[]> registros;
    
    public ModeloConsulta(final List<Map<String, Object>> array) {
        this.colnum = 9;
        this.colNames = new String[] { "Nombre", "N° Socio / Cargador", "Motivo Consulta", "Solución", "Fecha Inicio", "Fecha Fin", "Medio de Recepción", "Obra Social / Sistema", "Observaciones" };
        this.registros = new ArrayList<Object[]>();
        try {
            for (final Map<String, Object> map : array) {
                final Object[] row = { map.get(".nombre"), map.get(".codme"), map.get(".motivo"), map.get(".solucion"), map.get(".fechaini"), map.get(".fechafin"), map.get(".medio"), map.get(".os"), map.get(".observaciones") };
                this.registros.add(row);
            }
        }
        catch (Exception e) {
            System.out.println("Problemas en el modelo de tabla: Modelo Consultas");
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
