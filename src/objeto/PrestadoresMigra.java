// 
// Decompiled by Procyon v0.5.36
// 

package objeto;

import util.Reflection;
import java.util.Map;
import java.util.List;
import org.json.JSONObject;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Timestamp;
import util.ImplementService;

public class PrestadoresMigra extends ImplementService
{
    public int pre_id;
    public int codme;
    public String nombre;
    public int tipdoc;
    public int numdoc;
    public Timestamp fchnac;
    public String dompar;
    public int cppar;
    public int dppar;
    public String provin;
    public String tepar;
    public int tsocio;
    public Timestamp socing;
    public Timestamp socult;
    public Timestamp fecini;
    public Timestamp fecfin;
    public String sexo;
    public String ecivil;
    public int matric;
    public int gradua;
    public String dompro;
    public int cppro;
    public int dppro;
    public String tepro;
    public int espe1;
    public int espe2;
    public int espe3;
    public String horari;
    public String cod1;
    public String cod2;
    public String cod3;
    public String cod4;
    public String cod5;
    public String cod6;
    public String locobr;
    public int grupop;
    public int precbs;
    public int cooper;
    public int coonro;
    public int comra;
    public int comnro;
    public int comcuo;
    public int gananc;
    public String gannro;
    public String forpgo;
    public String codbco;
    public String ctaban;
    public int instit;
    public int digins;
    public String condic;
    public int casa;
    public String nrodgr;
    public int prosed;
    public int flore1;
    public int flore2;
    public int flore3;
    public int flore4;
    public int flore5;
    public int previs;
    public int preing;
    public int preult;
    public int presin;
    public int precon;
    public int precod;
    public String prejub;
    public int precar;
    public int premat;
    public int prenac;
    public int preenf;
    public int prexxx;
    public String tipoju;
    public String fdo3cm;
    public String tarea;
    public String usuario;
    public Timestamp fechora;
    public String maquina;
    public int entidad;
    public String uniper;
    public int iva;
    public String estatuto;
    public String recesta;
    public String carpeta;
    public Timestamp fvtdgi;
    public Timestamp fvtdgr;
    public Timestamp graduaf;
    public String mail;
    public String tecel;
    public String res1;
    public Timestamp vtor1;
    public Timestamp vto1;
    public String vence1;
    public String res2;
    public Timestamp vtor2;
    public Timestamp vto2;
    public String vence2;
    public String res3;
    public Timestamp vtor3;
    public Timestamp vto3;
    public String vence3;
    public String factura;
    public String rnp;
    public Timestamp fecrnp;
    public Timestamp vtohab;
    public int dgr;
    public int tem;
    public Timestamp vtotem;
    public int mailprioridad;
    public int smsprioridad;
    private static final String nombreClase = "objeto.Prestadores";
    
    public static int create(final PrestadoresMigra objeto, final Connection connection) {
        return createObjeto((Object)objeto, "objeto.Prestadores", connection);
    }
    
    public static void store(final PrestadoresMigra objeto, final Connection connection) {
        storeObjeto((Object)objeto, "objeto.Prestadores", connection);
    }
    
    public static PrestadoresMigra getObjetoPrestadores(final int id, final Connection conn) {
        return (PrestadoresMigra)ImplementService.getObjetoPorId("objeto.Prestadores", id, "", conn);
    }
    
    public static PrestadoresMigra getObjetoPrestadorPorCodme(final int codme, final Connection conn) {
        return (PrestadoresMigra)ImplementService.getObjetoPorId("objeto.Prestadores", -1, " or codme = " + codme, conn);
    }
    
    public static ArrayList<Object> getListaPrestadores(final String condicion, final Connection conn) {
        return (ArrayList<Object>)ImplementService.getObjetoLista("objeto.Prestadores", condicion, conn);
    }
    
    public static JSONObject getListaPrestadoresPorIdJson(final int id, final Connection conn) {
        return ImplementService.getListaJsonPorId("objeto.Prestadores", id, "", "objeto.Prestadores", conn);
    }
    
    public static JSONObject getAllPrestadoresJson(final Connection conn) {
        return null;
    }
    
    public static JSONObject createByJson(final JSONObject data, final Connection connection) throws Exception {
        final PrestadoresMigra objeto = (PrestadoresMigra)ImplementService.getObjectByJSONOBJECT("objeto.Prestadores", (Object)new PrestadoresMigra(), data.getJSONObject("formularioPrestadoresJson"));
        final int id = create(objeto, connection);
        return new JSONObject().put("PrestadoresJson", id);
    }
    
    public static boolean esPrestador(final int codme, final Connection connectionIBM) {
        List<Map<String, Object>> listaCodme = null;
        try {
            listaCodme = getValoresTablaorecepcionrefact("select * from Prestadores where codme = " + codme, connectionIBM);
        }
        catch (Exception ex) {}
        return listaCodme.size() > 0;
    }
    
    public static List<Map<String, Object>> getValoresTablaorecepcionrefact(final String sql, final Connection connection) throws Exception {
        final ArrayList<Object> arrayParametro = new ArrayList<Object>();
        return Reflection.getMapQueryResultByPreparedStatement(sql, arrayParametro, connection);
    }
}
