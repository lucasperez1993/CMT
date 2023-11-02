package Models;

import cirugia.DialogoBuscarPractica;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 *
 * @author djaime
 */
public class ModelPractica extends AbstractTableModel {

    public int colnum;
    private int rownum;
    private String[] colNames;
    private ArrayList<Object[]> registros;

    public ModelPractica(JSONArray arrayMatricula) {
        this.colnum = 10;
        this.colNames = new String[]{"Codigo", "Practica", "Cantidad", "Nivel", "Optativa", "(%)", "Req.AP","Capita", "C.Ayu","Ayudantes"};
        this.registros = new ArrayList<Object[]>();
        try {
            for (int indice = 0; indice < arrayMatricula.length(); indice++) {
                JSONObject map = arrayMatricula.getJSONObject(indice);
                String nombre = "-";
                String porcentaje = "100";
                String requiereAP = "";
                String cantidad = "1";
                String capita = "";
                String ayudantes = "";
                try {
                    capita = map.get("capita").toString();
                } catch (Exception e) {
                }
                try {
                    capita = map.get("capita").toString();
                } catch (Exception e) {
                }
                try {
                    nombre = map.get("nombre").toString();
                } catch (Exception e) {
                }
                try {
                    cantidad = map.get("cantidad").toString();
                } catch (Exception e) {
                }
                try {
                    String practica = map.getString("practica");
                    requiereAP = DialogoBuscarPractica.getRequiereAnatomia(practica);
                } catch (Exception e) {
                }
                try {
                    porcentaje = map.getString("porcentaje");
                } catch (Exception e) {
                }
                final Object[] row = {
                    map.get("practica"),
                    nombre,
                    cantidad,
                    map.get("nivel"),
                    map.getBoolean("valor") ? "SI" : "NO",
                    porcentaje,
                    requiereAP,
                    capita,
                    map.get("ayudante"),
                    ayudantes
                };
                this.registros.add(row);
            }
        } catch (Exception e) {
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