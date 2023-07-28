// 
// Decompiled by Procyon v0.5.36
// 

package objeto;

import org.json.JSONObject;
import java.util.ArrayList;
import java.sql.Connection;
import util.ImplementService;

public class ObraSocial extends ImplementService
{
    public int Id;
    public int os_id;
    public int os_cod;
    public int os_dig;
    public int os_scd;
    public String os_nombre;
    public int os_cpostal;
    public String os_cuit;
    public String os_direccion;
    public int grupo;
    public int boc_id;
    public int os_parent;
    public String convenio;
    public String provincia;
    public String tipo;
    public int colhono;
    public int colbioq;
    public String nomen;
    public String blanco1;
    public String blanco2;
    public int iva;
    public int nomenclador;
    public int vencimiento;
    public String factura;
    public String texto;
    public String liquida;
    public String ncredito;
    public int agrupa;
    public int condiva;
    public float porho;
    public float porga;
    public String conveniod;
    public String nomsifo;
    private int estado;
    private int altaBaja;
    private int modificacion;
    private String email;
    private static final String nombreClase = "objeto.ObraSocial";
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(final String email) {
        this.email = email;
    }
    
    public int getAltaBaja() {
        return this.altaBaja;
    }
    
    public void setAltaBaja(final int altaBaja) {
        this.altaBaja = altaBaja;
    }
    
    public int getModificacion() {
        return this.modificacion;
    }
    
    public void setModificacion(final int modificacion) {
        this.modificacion = modificacion;
    }
    
    public int getEstado() {
        return this.estado;
    }
    
    public void setEstado(final int estado) {
        this.estado = estado;
    }
    
    public int getId() {
        return this.Id;
    }
    
    public void setId(final int Id) {
        this.Id = Id;
    }
    
    public int getOs_id() {
        return this.os_id;
    }
    
    public void setOs_id(final int os_id) {
        this.os_id = os_id;
    }
    
    public int getOs_cod() {
        return this.os_cod;
    }
    
    public void setOs_cod(final int os_cod) {
        this.os_cod = os_cod;
    }
    
    public int getOs_dig() {
        return this.os_dig;
    }
    
    public void setOs_dig(final int os_dig) {
        this.os_dig = os_dig;
    }
    
    public int getOs_scd() {
        return this.os_scd;
    }
    
    public void setOs_scd(final int os_scd) {
        this.os_scd = os_scd;
    }
    
    public String getOs_nombre() {
        return this.os_nombre;
    }
    
    public void setOs_nombre(final String os_nombre) {
        this.os_nombre = os_nombre;
    }
    
    public int getOs_cpostal() {
        return this.os_cpostal;
    }
    
    public void setOs_cpostal(final int os_cpostal) {
        this.os_cpostal = os_cpostal;
    }
    
    public String getOs_cuit() {
        return this.os_cuit;
    }
    
    public void setOs_cuit(final String os_cuit) {
        this.os_cuit = os_cuit;
    }
    
    public String getOs_direccion() {
        return this.os_direccion;
    }
    
    public void setOs_direccion(final String os_direccion) {
        this.os_direccion = os_direccion;
    }
    
    public int getGrupo() {
        return this.grupo;
    }
    
    public void setGrupo(final int grupo) {
        this.grupo = grupo;
    }
    
    public int getBoc_id() {
        return this.boc_id;
    }
    
    public void setBoc_id(final int boc_id) {
        this.boc_id = boc_id;
    }
    
    public int getOs_parent() {
        return this.os_parent;
    }
    
    public void setOs_parent(final int os_parent) {
        this.os_parent = os_parent;
    }
    
    public String getConvenio() {
        return this.convenio;
    }
    
    public void setConvenio(final String convenio) {
        this.convenio = convenio;
    }
    
    public String getProvincia() {
        return this.provincia;
    }
    
    public void setProvincia(final String provincia) {
        this.provincia = provincia;
    }
    
    public String getTipo() {
        return this.tipo;
    }
    
    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }
    
    public int getColhono() {
        return this.colhono;
    }
    
    public void setColhono(final int colhono) {
        this.colhono = colhono;
    }
    
    public int getColbioq() {
        return this.colbioq;
    }
    
    public void setColbioq(final int colbioq) {
        this.colbioq = colbioq;
    }
    
    public String getNomen() {
        return this.nomen;
    }
    
    public void setNomen(final String nomen) {
        this.nomen = nomen;
    }
    
    public String getBlanco1() {
        return this.blanco1;
    }
    
    public void setBlanco1(final String blanco1) {
        this.blanco1 = blanco1;
    }
    
    public String getBlanco2() {
        return this.blanco2;
    }
    
    public void setBlanco2(final String blanco2) {
        this.blanco2 = blanco2;
    }
    
    public int getIva() {
        return this.iva;
    }
    
    public void setIva(final int iva) {
        this.iva = iva;
    }
    
    public int getNomenclador() {
        return this.nomenclador;
    }
    
    public void setNomenclador(final int nomenclador) {
        this.nomenclador = nomenclador;
    }
    
    public int getVencimiento() {
        return this.vencimiento;
    }
    
    public void setVencimiento(final int vencimiento) {
        this.vencimiento = vencimiento;
    }
    
    public String getFactura() {
        return this.factura;
    }
    
    public void setFactura(final String factura) {
        this.factura = factura;
    }
    
    public String getTexto() {
        return this.texto;
    }
    
    public void setTexto(final String texto) {
        this.texto = texto;
    }
    
    public String getLiquida() {
        return this.liquida;
    }
    
    public void setLiquida(final String liquida) {
        this.liquida = liquida;
    }
    
    public String getNcredito() {
        return this.ncredito;
    }
    
    public void setNcredito(final String ncredito) {
        this.ncredito = ncredito;
    }
    
    public int getAgrupa() {
        return this.agrupa;
    }
    
    public void setAgrupa(final int agrupa) {
        this.agrupa = agrupa;
    }
    
    public int getCondiva() {
        return this.condiva;
    }
    
    public void setCondiva(final int condiva) {
        this.condiva = condiva;
    }
    
    public float getPorho() {
        return this.porho;
    }
    
    public void setPorho(final float porho) {
        this.porho = porho;
    }
    
    public float getPorga() {
        return this.porga;
    }
    
    public void setPorga(final float porga) {
        this.porga = porga;
    }
    
    public String getConveniod() {
        return this.conveniod;
    }
    
    public void setConveniod(final String conveniod) {
        this.conveniod = conveniod;
    }
    
    public String getNomsifo() {
        return this.nomsifo;
    }
    
    public void setNomsifo(final String nomsifo) {
        this.nomsifo = nomsifo;
    }
    
    public static int create(final ObraSocial objeto, final Connection connection) {
        return createObjeto((Object)objeto, "objeto.ObraSocial", connection);
    }
    
    public static void store(final ObraSocial objeto, final Connection connection) {
        storeObjeto((Object)objeto, "objeto.ObraSocial", connection);
    }
    
    public static ObraSocial getObjetoObraSocial(final int id, final Connection conn) {
        return (ObraSocial)ImplementService.getObjetoPorId("objeto.ObraSocial", id, "", conn);
    }
    
    public static ObraSocial getObjetoObraSocialPorOsId(final int os_id, final Connection conn) {
        return (ObraSocial)ImplementService.getObjetoPorId("objeto.ObraSocial", -1, "or os_id = " + os_id, conn);
    }
    
    public static ArrayList<Object> getListaObraSocial(final String condicion, final Connection conn) {
        return (ArrayList<Object>)ImplementService.getObjetoLista("objeto.ObraSocial", condicion, conn);
    }
    
    public static JSONObject getListaObraSocialPorIdJson(final int id, final Connection conn) {
        return ImplementService.getListaJsonPorId("objeto.ObraSocial", id, "", "objeto.ObraSocial", conn);
    }
    
    public static JSONObject createByJson(final JSONObject data, final Connection connection) throws Exception {
        final ObraSocial objeto = (ObraSocial)ImplementService.getObjectByJSONOBJECT("objeto.ObraSocial", (Object)new ObraSocial(), data.getJSONObject("formularioObraSocialJson"));
        final int id = create(objeto, connection);
        return new JSONObject().put("ObraSocialJson", id);
    }
}
