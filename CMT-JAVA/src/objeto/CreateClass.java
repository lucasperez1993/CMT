// 
// Decompiled by Procyon v0.5.36
// 

package objeto;

import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;
import Conexion.Conexion;

public class CreateClass
{
    public static void createTxt(final String Objeto, final ArrayList listaCampos) throws IOException {
        final String path = new File(".").getCanonicalPath();
        final File archivo = new File(path + "/src/objeto/" + Objeto + ".java");
        BufferedWriter bw = null;
        if (!archivo.exists()) {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("\n");
            bw.write("package objeto;");
            bw.write("\n");
            bw.write("import calculos.CalculosFecha;");
            bw.write("\n");
            bw.write("import java.sql.Connection;");
            bw.write("\n");
            bw.write("import java.util.ArrayList;");
            bw.write("\n");
            bw.write("import org.json.JSONObject;");
            bw.write("\n");
            bw.write("import util.ImplementService;");
            bw.write("\n");
            bw.write("import java.sql.Timestamp;");
            bw.write("\n");
            bw.write("import static util.ImplementService.createObjeto;");
            bw.write("\n");
            bw.write("import static util.ImplementService.storeObjeto;");
            bw.write("\n");
            bw.write("\n");
            bw.write("public class " + Objeto + " extends ImplementService{");
            bw.write("\n");
            for (int i = 0; i < listaCampos.size(); ++i) {
                bw.write("    public " + listaCampos.get(i) + ";");
                bw.write("\n");
            }
            bw.write("\n");
            bw.write("    private static final String nombreClase = \"objeto." + Objeto + "\";");
            bw.write("\n");
            bw.write("\n");
            bw.write("    public static int create(" + Objeto + " objeto, Connection connection){");
            bw.write("\n");
            bw.write("        return createObjeto(objeto, nombreClase, connection);");
            bw.write("\n");
            bw.write("    }");
            bw.write("\n");
            bw.write("\n");
            bw.write("    public static void store(" + Objeto + " objeto, Connection connection){");
            bw.write("\n");
            bw.write("        storeObjeto(objeto, nombreClase, connection);");
            bw.write("\n");
            bw.write("    }");
            bw.write("\n");
            bw.write("\n");
            bw.write("    public static " + Objeto + " getObjeto" + Objeto + "(int id, Connection conn){");
            bw.write("\n");
            bw.write("        return (" + Objeto + ") ImplementService.getObjetoPorId(nombreClase, id,\"\", conn);");
            bw.write("\n");
            bw.write("    }");
            bw.write("\n");
            bw.write("\n");
            bw.write("    public static ArrayList<Object> getLista" + Objeto + "(String condicion, Connection conn){");
            bw.write("\n");
            bw.write("        return ImplementService.getObjetoLista(nombreClase, condicion, conn);");
            bw.write("\n");
            bw.write("    }");
            bw.write("\n");
            bw.write("\n");
            bw.write("    public static JSONObject getLista" + Objeto + "PorIdJson(int id, Connection conn){");
            bw.write("\n");
            bw.write("        return ImplementService.getListaJsonPorId(nombreClase, id, \"\", nombreClase, conn);");
            bw.write("\n");
            bw.write("    }");
            bw.write("\n");
            bw.write("\n");
            bw.write("    public static JSONObject getAll" + Objeto + "Json(Connection conn){");
            bw.write("\n");
            bw.write("        return ImplementService.getAllObjetoJson(\"\", nombreClase, CalculosFecha.DATE_FORMAT_D_M_Y, conn);");
            bw.write("\n");
            bw.write("    }");
            bw.write("\n");
            bw.write("\n");
            bw.write("    public static JSONObject createByJson(JSONObject data, Connection connection) throws Exception{");
            bw.write("\n");
            bw.write("        " + Objeto + " objeto = (" + Objeto + ") ImplementService.getObjectByJSONOBJECT(nombreClase, new " + Objeto + "(), data.getJSONObject(\"formulario" + Objeto + "Json\"));");
            bw.write("\n");
            bw.write("        int id = create(objeto, connection);");
            bw.write("\n");
            bw.write("        return new JSONObject().put(\"" + Objeto + "Json\", id);");
            bw.write("\n");
            bw.write("    }");
            bw.write("\n");
            bw.write("}");
        }
        bw.close();
    }
    
    public static void createCommonJavaAndJavascript(final String tablaObjeto, final Connection con) throws SQLException, IOException {
        final PreparedStatement preparedStatement = con.prepareStatement("select * from " + tablaObjeto);
        final ArrayList lista = new ArrayList();
        final ResultSet resultSet = preparedStatement.executeQuery();
        final ResultSetMetaData metaData = resultSet.getMetaData();
        for (int cantidadColumnas = metaData.getColumnCount(), index = 1; index <= cantidadColumnas; ++index) {
            String tabla = "";
            if (metaData.getTableName(index).length() > 3) {
                tabla = metaData.getTableName(index).substring(0, 3);
            }
            else {
                tabla = metaData.getTableName(index);
            }
            try {
                if (metaData.getColumnTypeName(index).equalsIgnoreCase("VARCHAR")) {
                    lista.add("String " + metaData.getColumnLabel(index));
                }
                else if (metaData.getColumnTypeName(index).equalsIgnoreCase("INTEGER")) {
                    lista.add("int " + metaData.getColumnLabel(index));
                }
                else if (metaData.getColumnTypeName(index).equalsIgnoreCase("INT")) {
                    lista.add("int " + metaData.getColumnLabel(index));
                }
                else if (metaData.getColumnTypeName(index).equalsIgnoreCase("DATE")) {
                    lista.add("Timestamp " + metaData.getColumnLabel(index));
                }
                else if (metaData.getColumnTypeName(index).equalsIgnoreCase("FLOAT")) {
                    lista.add("float " + metaData.getColumnLabel(index));
                }
                else if (metaData.getColumnTypeName(index).equalsIgnoreCase("money")) {
                    lista.add("float " + metaData.getColumnLabel(index));
                }
                else if (metaData.getColumnTypeName(index).equalsIgnoreCase("smallint")) {
                    lista.add("int " + metaData.getColumnLabel(index));
                }
                else if (metaData.getColumnTypeName(index).equalsIgnoreCase("bigint")) {
                    lista.add("int " + metaData.getColumnLabel(index));
                }
                else if (metaData.getColumnTypeName(index).equalsIgnoreCase("nvarchar")) {
                    lista.add("String " + metaData.getColumnLabel(index));
                }
                else if (metaData.getColumnTypeName(index).equalsIgnoreCase("text")) {
                    lista.add("String " + metaData.getColumnLabel(index));
                }
                else if (metaData.getColumnTypeName(index).equalsIgnoreCase("datetime")) {
                    lista.add("Timestamp " + metaData.getColumnLabel(index));
                }
                else if (metaData.getColumnTypeName(index).equalsIgnoreCase("char")) {
                    lista.add("String " + metaData.getColumnLabel(index));
                }
                else if (metaData.getColumnTypeName(index).equalsIgnoreCase("real")) {
                    lista.add("float " + metaData.getColumnLabel(index));
                }
                else if (metaData.getColumnTypeName(index).equalsIgnoreCase("numeric")) {
                    lista.add("float " + metaData.getColumnLabel(index));
                }
            }
            catch (Exception e) {
                System.out.println(metaData.getColumnLabel(index));
            }
        }
        createTxt(tablaObjeto, lista);
    }
    
    public static void main(final String[] arg) throws Exception {
        final ArrayList a = null;
        final Conexion con = new Conexion();
        final Connection conn = con.GetConnectionCloud();
        createCommonJavaAndJavascript("cmt_motivomesa", conn);
    }
}
