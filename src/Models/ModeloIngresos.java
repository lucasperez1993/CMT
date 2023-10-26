package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;
/**
 *
 * @author lperez
 */
public class ModeloIngresos extends AbstractTableModel {

    public int colnum;
    private int rownum;
    private String[] colNames;
    private ArrayList<Object[]> registros;

    public ModeloIngresos(final List<Map<String, Object>> array) {
        this.colnum = 3;
        this.colNames = new String[]{"Nombre", "D.N.I", "Hora"};
        this.registros = new ArrayList<Object[]>();
        try {
            for (final Map<String, Object> map : array) {
                final Object[] row = {map.get(".nombre"), map.get(".numdoc"), map.get(".horaIngreso")};
                this.registros.add(row);
            }
        } catch (Exception e) {
            System.out.println("Problemas en el modelo de tabla: Modelo Ingresos");
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
