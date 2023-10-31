/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cirugia;

import Models.ModelAyudante;
import Models.ModelCarga;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import objeto.cmt_configuracion;
import objeto.jdb_cirugiapractica;
import objeto.jdb_documento;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import otro.ConstanteObjeto;
import util.CalculosFecha;
import util.DigitoVerificador;
import util.SqlValorCombo;

/**
 *
 * @author djaime
 */
public class ControladorCirugia {
    
    public static final String PEDIDO = "Pedido";
    public static final String PROTOCOLO = "Protocolo";
    public static final String HC = "HC";
    public static final String OTRO = "Otro";
    
    public static final int CIRUJANO = 1;
    public static final int VIDEO_ENDOSCOPIA = 2;
    public static final int INTERCONSULTA = 3;
    public static final int MIO = 4;
    
    public static final int CODIGO_VIDEO_ENDOSCOPIA_1 = 200122;
    public static final int CODIGO_VIDEO_ENDOSCOPIA_2 = 200124;
    public static final int CODIGO_VIDEO_ENDOSCOPIA_3 = 200126;
    public static final int CODIGO_VIDEO_ENDOSCOPIA_4 = 200134;
    public static final int CODIGO_VIDEO_ENDOSCOPIA_5 = 200135;
    public static final int CODIGO_VIDEO_ENDOSCOPIA_6 = 200137;
    public static final int CODIGO_VIDEO_ENDOSCOPIA_7 = 200138;
    public static final int CODIGO_INTERCONSULTA = 420303;
    public static final int CODIGO_MIO = 170109;
    
    Connection connection;
    FormCirugia form;
    int idorganizacion = 2;
    jdb_cirugiapractica cirugia = null;
    SqlValorCombo sqlCombo = null;
    
    public List<Map<String, Object>> listaCombo;
    public List<Map<String, Object>> listaComboEstado;
    
    public ControladorCirugia(FormCirugia form, Connection connection){
        this.form = form;
        this.connection = connection;
    }
    
    public List<Map<String, Object>> listaCarga;
    
    public void initTablaAyudante(){
        //this.form.getTablaAyudante().setModel(new ModelAyudante(new JSONArray(), this.connection));
    }

    public jdb_cirugiapractica getCirugia() {
        return cirugia;
    }

    public void setCirugia(jdb_cirugiapractica cirugia) {
        this.cirugia = cirugia;
    }
    
    public void initBotones(boolean valor){
        form.getBotonCalcular().setEnabled(valor);
        form.getBotonCrearRegistro().setEnabled(valor);
        form.getTextCuil().setEnabled(valor);
        form.getTextNombreAfiliado().setEnabled(valor);
    }
    
    public void initBotonesCarga(boolean valor){
        form.getBotonAgregarCirujano().setEnabled(valor);
        form.getBotonAgregarAyudante().setEnabled(valor);
        form.getBotonQuitarCirujano().setEnabled(valor);
        form.getBotonQuitarAyudante().setEnabled(valor);
        form.getBotonModificar().setEnabled(valor);
        if(valor)
            prepareCirugia();
    }
    
    public void prepareCirugia(){
        try {
            Map<String, Object> row = listaCarga.get(form.getTablaCirugia().getSelectedRow());
            form.getBotonUpload().setEnabled(true);
            form.getBotonPracticas().setEnabled(true);
            cirugia.idcirugiapractica = Integer.valueOf(row.get("idcirugiapractica").toString());
            cirugia.idusuario = Integer.valueOf(row.get("idusuario").toString());
            cirugia.codme = Integer.valueOf(row.get("codme").toString());
            cirugia.tipoobjeto = Integer.valueOf(row.get("tipoobjeto").toString());
            cirugia.ois = row.get("ois").toString();
            cirugia.matricsol = Integer.valueOf(row.get("matricsol").toString());
            cirugia.nombreafiliado = row.get("nombreafiliado").toString();
            cirugia.practicajson = row.get("practicajson").toString();
            cirugia.idsanatorio = Integer.valueOf(row.get("idsanatorio").toString());
            cirugia.fecha = row.get("fecha").toString();
            cirugia.fechasolicitud = LocalDate.parse(row.get("fechasolicitud").toString());
            cirugia.fechavto = LocalDate.parse(row.get("fechavto").toString());
            cirugia.nota = row.get("nota").toString();
            cirugia.checkestado = row.get("idcirugiapractica").toString();
            cirugia.hora = row.get("hora").toString();
            cirugia.minuto = row.get("minuto").toString();
            cirugia.periodo = Integer.valueOf(row.get("periodo").toString());
            cirugia.ayudantejson = row.get("ayudantejson").toString();
            cirugia.idorganizacion = Integer.valueOf(row.get("idorganizacion").toString());
            cirugia.estado = Integer.valueOf(row.get("idestado").toString());
            cirugia.nombreafiliado = row.get("nombreafiliado").toString();
            cirugia.afiliado = row.get("afiliado").toString();
            form.getTextCuil().setText(row.get("afiliado").toString());
            form.getTextNombreAfiliado().setText(row.get("nombreafiliado").toString());
            form.getDateFecha().setDate(CalculosFecha.retornarFechaDate(row.get("fechasolicitud").toString()));
            form.getLabelFechaVTO().setText(CalculosFecha.transformarFechaConFormatoDiaMesAnio(row.get("fechavto").toString()));
            int hora = 0;
            int minuto = 0;
            try {
                hora = Integer.valueOf(cirugia.hora);
            } catch (Exception e) {}
            try {
                minuto = Integer.valueOf(cirugia.minuto);
            } catch (Exception e) {}
            form.getShora().setValue(hora);
            form.getSminuto().setValue(minuto);
            JSONObject json = getMedicoPorCodme(cirugia.codme);
            if (json.getBoolean("result")) {
                form.getLabelCodme().setText(json.get("codme") + "");
                form.getLabelMatricula().setText(json.get("matricula") + "");
                form.getTxtNombreCirujano().setText(json.getString("nombre"));
//                cargarCirugias(json.getInt("codme"));
            }else{
                form.getLabelCodme().setText("-");
                form.getLabelMatricula().setText("-");
                form.getTxtNombreCirujano().setText("-");
            }
            iniciarComboPorIdSanatorio();
            iniciarComboPorEstado();
            cargarTablaAyudante();
            JSONArray listaDocumentos = getListaDOcumentos();
            for(int indice = 0; indice < listaDocumentos.length(); indice++){
                JSONObject _json = listaDocumentos.getJSONObject(indice);
                getTipoPorNombreDocumento(_json.getString("descripcion"), true);
            }
            verificarTipoPractica();
        } catch (JSONException ex) {
            Logger.getLogger(ControladorCirugia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCirugia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public JSONArray getListaDOcumentos() throws SQLException{
        return SqlCirugia.getDocumentoPorIdcirugia(cirugia.idcirugiapractica, connection);
    }
    
    public void iniciarComboPorIdSanatorio(){
        try {
            String valor = SqlCirugia.seleccionarDesdeCombo(
                    "cmt_sanatorio", 
                    "sanatorio", "idsanatorio", 
                    cirugia.idsanatorio+"", connection);
            form.getComboSanatorio().setSelectedItem(valor);
        } catch (Exception ex) {}
    }
    
    public void iniciarComboPorEstado(){
        try {
            String valor = SqlCirugia.seleccionarDesdeCombo(
                    "cmt_estado", 
                    "descripcion", "estado", 
                    cirugia.estado+"", connection);
            form.getComboEstado().setSelectedItem(valor);
        } catch (Exception ex) {}
    }
    
    public int getTipoPorNombreDocumento(String tipoDocumento, boolean valor){
        int tipo = 0;
        switch(tipoDocumento){
            case PEDIDO:
                form.getCheckPedido().setSelected(valor);
                break;
            case PROTOCOLO:
                form.getCheckProtocolo().setSelected(valor);
                break;
            case HC:
                form.getCheckHC().setSelected(valor);
                break;
            case OTRO:
                form.getCheckOtro().setSelected(valor);
                break;
        }
        return tipo;
    }
    
    public void cargarCirugias(int matricula) {
        listaCarga = SqlCirugia.getCirugiaCMTPorCodmeOrderByFechaVto(false, true, matricula, idorganizacion, connection);
        form.getTablaCirugia().setModel(new ModelCarga(listaCarga));
    }
    
    public void getCirugiaAfiliadoPorCuiL(String afiliado) {
        try {
            prepareObjetoCirugia(form.getIdusuario());
            JSONObject jsonAfiliado = SqlCirugia.getAfiliadoPorCuil(afiliado, connection);
            if(jsonAfiliado.getBoolean("result")){
                cargarTablaCirugia(afiliado);
                form.getTextNombreAfiliado().setText(jsonAfiliado.getString("nombreafiliado"));
                cirugia.afiliado = afiliado;
            }else{
                form.getTextCuil().setFocusable(true);
                form.getTextCuil().selectAll();
                form.getTablaCirugia().setModel(new ModelCarga(listaCarga));
                form.getTextNombreAfiliado().setText("");
            }
        } catch (JSONException ex) {
            Logger.getLogger(ControladorCirugia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarTablaCirugia(String afiliado){
        listaCarga = SqlCirugia.getCirugiaAfiliadoPorCuiL(false, afiliado, idorganizacion, connection);
        form.getTablaCirugia().setModel(new ModelCarga(listaCarga));
        cargaSumaTotal();
    }
    
    public void cargarTablaCirugiaGral(){
        listaCarga = SqlCirugia.getCirugiaAfiliadoGral(false, form.getIdusuario(), form.getTipoUsuario(), idorganizacion, connection);
        form.getTablaCirugia().setModel(new ModelCarga(listaCarga));
        cargaSumaTotal();
    }
    
    public void cargarCombo(){
        this.listaCombo = SqlValorCombo.llenarComboPor("select idsanatorio, sanatorio from cmt_sanatorio", this.form.getComboSanatorio(), "sanatorio", connection);
        this.listaComboEstado = SqlValorCombo.llenarComboPor("select estado, descripcion from cmt_estado", this.form.getComboEstado(), "descripcion", connection);
    }
    
    public JSONObject getMedicoPorMatricula(int matricula){
        return SqlCirugia.getMedicoPorMatricula(matricula, connection);
    }
    
    public JSONObject getMedicoPorCodme(int codme){
        return SqlCirugia.getMedicoPorCodme(codme, connection);
    }
    
    public void prepareObjetoCirugia(int idusuario){
        String _periodo;
        String anio = LocalDate.now().getYear() + "";
        String mes =  DigitoVerificador.getCompletarDigito(LocalDate.now().getMonthValue() + "", 2);
        _periodo = anio +mes;
        cirugia = new jdb_cirugiapractica();
        cirugia.afiliado = "";
        cirugia.ayudantejson = new JSONArray().toString();
        cirugia.checkestado = new JSONObject().toString();
        cirugia.codme = 0;
        cirugia.estado = 1;
        cirugia.fecha = CalculosFecha.getFechaActualFormatoSQL();
        cirugia.fechasolicitud = LocalDate.now();
        cirugia.fechavto = LocalDate.now();
        cirugia.hora = form.getShora().getValue().toString();
        cirugia.minuto = form.getSminuto().getValue().toString();
        cirugia.idcirugiapractica = 0;
        cirugia.idorganizacion = this.idorganizacion;
        cirugia.idsanatorio = 0;
        cirugia.idusuario = idusuario;
        cirugia.matricsol = 0;
        cirugia.nombreafiliado = "";
        cirugia.nota = "";
        cirugia.ois = "";
        cirugia.periodo = Integer.valueOf(_periodo);
        cirugia.practicajson = new JSONArray().toString();
        cirugia.tipoobjeto = 0;
        initTablaAyudante();
        initBotones(true);
    }
    
    public void calcularDiasVTO(Date fechaCalendar){
        try {
            String fecha = CalculosFecha.retornarFechaConFormatoDesado(fechaCalendar, "yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(fecha);
            cirugia.fechasolicitud = localDate;
            JSONObject config = cmt_configuracion.getcmt_configuracionPorSQLTipo(ConstanteObjeto.CONFIGURACION_CANTIDAD_DIAS_ALERTA, connection);
            LocalDate newDate = cirugia.fechasolicitud.plusDays(config.getLong(ConstanteObjeto.CANTIDAD));
            cirugia.fechavto = newDate;
            form.getLabelFechaVTO().setText("VTO: " + CalculosFecha.transformarFechaConFormatoDiaMesAnio(cirugia.fechavto.toString()));
            //String anio = LocalDate.now().getYear() + "";
            //String mes =  DigitoVerificador.getCompletarDigito(LocalDate.now().getMonthValue() + "", 2);
            //_periodo = anio +mes;
        } catch (JSONException ex) {
            Logger.getLogger(ControladorCirugia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void crearRegistro() throws JSONException{
        if(!"VTO: 0000-00-00".equals(form.getLabelFechaVTO().getText()) && !"".equals(form.getTxtNombreCirujano().getText())){
            String fecha = CalculosFecha.retornarFechaConFormatoDesado(form.getDateFecha().getDate(), "yyyy-MM-dd");
            String fechavto = CalculosFecha.transformarFechaConFormatoANIO_MES_DIA(form.getLabelFechaVTO().getText());
            if(!SqlCirugia.existeCarga(fecha, fechavto, form.getTextCuil().getText(), connection)){
                cirugia.idsanatorio = Integer.valueOf(listaCombo.get(form.getComboSanatorio().getSelectedIndex()).get(".idsanatorio").toString());
                cirugia.afiliado = form.getTextCuil().getText();
                cirugia.nombreafiliado = form.getTextNombreAfiliado().getText();
                cirugia.estado = 1;//Integer.valueOf(listaComboEstado.get(form.getComboSanatorio().getSelectedIndex()).get(".estado").toString());
                String _periodo;
                String anio = LocalDate.now().getYear() + "";
                String mes =  DigitoVerificador.getCompletarDigito(LocalDate.now().getMonthValue() + "", 2);
                _periodo = anio +mes;
                cirugia.periodo = Integer.valueOf(_periodo);
                jdb_cirugiapractica jciru = new jdb_cirugiapractica();
                cirugia.idcirugiapractica = jciru.createCirugia(cirugia, connection);
                form.getBotonUpload().setEnabled(true);
                form.getBotonPracticas().setEnabled(true);
                if(cirugia.idcirugiapractica > 0){
                    listaCarga = SqlCirugia.getCirugiaAfiliadoPorCuiL(false, form.getTextCuil().getText(), idorganizacion, connection);
                    form.getTablaCirugia().setModel(new ModelCarga(listaCarga));
                    //initBotonesCarga(true);
                }
    //            listaCarga = SqlCirugia.getCirugiaCMTPorCodmeOrderByFechaVto(false, false, 0, idorganizacion, connection);
    //            form.getTablaCirugia().setModel(new ModelCarga(listaCarga));
            }else{
                JOptionPane.showMessageDialog(null, "!Ya existe un registro con el afiliado y fecha de solicitud y fecha de vto!", "Mensaje del sistema", JOptionPane.WARNING_MESSAGE);
            }
            
        }else{
            form.getBotonUpload().setEnabled(false);
            form.getBotonPracticas().setEnabled(false);
            JOptionPane.showMessageDialog(null, "Falta completar informacion", "Mensaje del sistema", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void agregarMedico(boolean isCirujano, int matricula){
        try {
            JSONObject json = getMedicoPorMatricula(matricula);
            if (json.getBoolean("result")) {
                if(isCirujano){
                    cirugia.codme = json.getInt("codme");
                    jdb_cirugiapractica.modificarCirujano(cirugia, connection);
                    form.getTxtNombreCirujano().setText(json.getString("nombre").trim());
                    form.getLabelCodme().setText(json.get("codme").toString());
                    form.getLabelMatricula().setText(json.get("matricula").toString());
                    cargarCirugias(json.getInt("codme"));
                }else{
                    agregarMedicoAyudante(json);
                }
                cargarTablaCirugia(cirugia.afiliado);
            } else {
                //getTablaCirugia().setModel(new ModelCarga(null));
                //textMatricula.setText("");
                JOptionPane.showMessageDialog(null, "No existe el socio", "Mensaje del sistema", JOptionPane.WARNING_MESSAGE);
            }
        } catch (JSONException ex) {
            Logger.getLogger(ControladorCirugia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void agregarMedicoAyudante(JSONObject json) throws JSONException{
        JSONArray array = new JSONArray(cirugia.ayudantejson);
        boolean existe = false;
        for(int indice = 0; indice < array.length(); indice++){
            JSONObject compareJaon = array.getJSONObject(indice);
            if(compareJaon.getInt("matricula") == json.getInt("matricula")){
                existe = true;
                break;
            }
        }
        if(!existe)
            array.put(json);
        cirugia.ayudantejson = array.toString();
        jdb_cirugiapractica.modificarAyudante(cirugia, connection);
        cargarTablaAyudante();
    }
    
    public void agregarPracticaMedicoAyudante(JSONObject json) throws JSONException{
        JSONArray array = new JSONArray(cirugia.practicajson);
        boolean existe = false;
        for(int indice = 0; indice < array.length(); indice++){
            JSONObject compareJaon = array.getJSONObject(indice);
            if(compareJaon.getString("practica").equals(json.getString("practica"))){
                existe = true;
                break;
            }
        }
        if(!existe)
            array.put(json);
        cirugia.practicajson = array.toString();
        jdb_cirugiapractica.modificarPractica(cirugia, connection);
        cargarTablaCirugia(cirugia.afiliado);
    }
    
    public void modificarPracticaMedicoAyudante(JSONObject json) throws JSONException{
        JSONArray array = new JSONArray(cirugia.practicajson);
        for(int indice = 0; indice < array.length(); indice++){
            JSONObject compareJaon = array.getJSONObject(indice);
            if(compareJaon.getString("practica").equals(json.getString("practica"))){
                array.remove(indice);
                array.put(json);
                break;
            }
        }
        cirugia.practicajson = array.toString();
        jdb_cirugiapractica.modificarPractica(cirugia, connection);
    }
    
    public void modificarPracticaMedicoGral(String practica, JSONArray arrayMatricula) throws JSONException{
        JSONArray array = new JSONArray(cirugia.practicajson);
        for(int indice = 0; indice < array.length(); indice++){
            if(array.getJSONObject(indice).getString("practica").equals(practica)){
                array.getJSONObject(indice).put("medicos", arrayMatricula);
                break;
            }
        }
        cirugia.practicajson = array.toString();
        jdb_cirugiapractica.modificarPractica(cirugia, connection);
        cargarTablaCirugia(cirugia.afiliado);
    }
    
    public void cargarTablaAyudante() throws JSONException{
        JSONArray array = new JSONArray(cirugia.ayudantejson);
        form.getTablaAyudante().setModel(new ModelAyudante(array));
        form.getTablaAyudante().getColumnModel().getColumn(0).setPreferredWidth(30);
        form.getTablaAyudante().getColumnModel().getColumn(1).setPreferredWidth(30);
        form.getTablaAyudante().getColumnModel().getColumn(2).setPreferredWidth(220);
    }
    
    public void eliminarAyudante() throws JSONException{
        JSONArray json = new JSONArray(cirugia.ayudantejson);
        json.remove(form.getTablaAyudante().getSelectedRow());
        cirugia.ayudantejson = json.toString();
        jdb_cirugiapractica.modificarAyudante(cirugia, connection);
        cargarTablaCirugia(cirugia.afiliado);
        cargarTablaAyudante();
    }
    
    public void quitarPractica(int row) throws JSONException{
        JSONArray json = new JSONArray(cirugia.practicajson);
        json.remove(row);
        cirugia.practicajson = json.toString();
        jdb_cirugiapractica.modificarPractica(cirugia, connection);
        cargarTablaCirugia(cirugia.afiliado);
    }
    
    public void eliminarCirujano() throws JSONException{
        cirugia.codme = 0;
        jdb_cirugiapractica.modificarCirujano(cirugia, connection);
        cargarTablaCirugia(cirugia.afiliado);
        form.getLabelCodme().setText("-");
        form.getLabelMatricula().setText("-");
        form.getTxtNombreCirujano().setText("-");
    }
    
    public void descargarImagen(){
        try {
            if(cirugia.codme > 0){
                JSONObject prestadorJson = getMedicoPorCodme(cirugia.codme);
                String practica = "";
                JSONArray practicas = new JSONArray(cirugia.practicajson);
                for(int indice = 0; indice < practicas.length(); indice++){
                    practica += practicas.getJSONObject(indice).getString("practica") +"_";
                }
                //GuardarImagenConFileChooser ventana = new GuardarImagenConFileChooser(prestadorJson.getInt("matricula"), cirugia.afiliado, practica);
                JSONObject config = getConfiguracion();
                DialogoUpload ventana = new DialogoUpload(form.parent, true, cirugia, prestadorJson.getInt("matricula"), cirugia.afiliado, practica, config.getJSONObject("configuracion").getString("pathimg"), connection);
                ventana.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null, "Debe seleccionar el cirujano", "Mensaje del sistema", JOptionPane.WARNING_MESSAGE);
            }
            
        } catch (JSONException ex) {
            Logger.getLogger(ControladorCirugia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void modificarCirugia(){
        if(!"VTO: 0000-00-00".equals(form.getLabelFechaVTO().getText()) && !"".equals(form.getTxtNombreCirujano().getText())){
            int _estado = Integer.valueOf(listaComboEstado.get(form.getComboEstado().getSelectedIndex()).get(".estado").toString());
            if(_estado == ConstanteObjeto.CIRUGIA_ESTADO_FINALIZADO){
                DialogoNroAutorizacion dialogo = new DialogoNroAutorizacion(form.parent, true, this);
                dialogo.setVisible(true);
            }
            cirugia.idsanatorio = Integer.valueOf(listaCombo.get(form.getComboSanatorio().getSelectedIndex()).get(".idsanatorio").toString());
            cirugia.afiliado = form.getTextCuil().getText();
            cirugia.nombreafiliado = form.getTextNombreAfiliado().getText();
            cirugia.estado = _estado;
            cirugia.hora = form.getShora().getValue().toString();
            cirugia.minuto = form.getSminuto().getValue().toString();
            if(jdb_cirugiapractica.modificarCirugia(cirugia, connection)){
                listaCarga = SqlCirugia.getCirugiaAfiliadoPorCuiL(false, form.getTextCuil().getText(), idorganizacion, connection);
                form.getTablaCirugia().setModel(new ModelCarga(listaCarga));
                JOptionPane.showMessageDialog(null, "Registro modificado", "Mensaje del sistema", JOptionPane.WARNING_MESSAGE);
            }
        }else{
            form.getBotonUpload().setEnabled(false);
            form.getBotonPracticas().setEnabled(false);
            JOptionPane.showMessageDialog(null, "Falta completar informacion", "Mensaje del sistema", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void quitarImagen(int iddocumento, String documento){
        jdb_documento.bajaDocumento(iddocumento, connection);
        File archivo = new File(documento);
        archivo.delete();
        cargarTablaCirugia(cirugia.afiliado);
    }
    
    public void verificarTipoPractica(){
        switch(cirugia.tipoobjeto){
            case CIRUJANO:
                form.getPanelCirugia().setBackground(new java.awt.Color(255, 255, 204));
                break;
            case VIDEO_ENDOSCOPIA:
                form.getPanelCirugia().setBackground(new java.awt.Color(153,255,153));
                break;
            case INTERCONSULTA:
                form.getPanelCirugia().setBackground(new java.awt.Color(51,204,255));
                break;
            case MIO:
                form.getPanelCirugia().setBackground(new java.awt.Color(255,204,153));
                break;
        }
    }
    
    public void modificarTipoPractica(int practica){
        switch(practica){
            case CODIGO_VIDEO_ENDOSCOPIA_1:
            case CODIGO_VIDEO_ENDOSCOPIA_2:
            case CODIGO_VIDEO_ENDOSCOPIA_3:
            case CODIGO_VIDEO_ENDOSCOPIA_4:
            case CODIGO_VIDEO_ENDOSCOPIA_5:
            case CODIGO_VIDEO_ENDOSCOPIA_6:
            case CODIGO_VIDEO_ENDOSCOPIA_7:
                cirugia.tipoobjeto = VIDEO_ENDOSCOPIA;
                break;
            case CODIGO_INTERCONSULTA:
                cirugia.tipoobjeto = INTERCONSULTA;
                break;
            case CODIGO_MIO:
                cirugia.tipoobjeto = MIO;
                break;
            default:
                cirugia.tipoobjeto = CIRUJANO;
        }
        jdb_cirugiapractica.modificarTipoObjetoDocumento(cirugia.idcirugiapractica, cirugia.tipoobjeto, connection);
        verificarTipoPractica();
        cargarTablaCirugia(cirugia.afiliado);
    }
    
    public void cargaSumaTotal(){
        try {
            JSONObject json = SqlCirugia.getCantidadGral(false, idorganizacion, connection);
            form.getCirugia().setText(json.getInt("cirugia") + "");
            form.getAyudantia().setText(json.getInt("ayudantia") + "");
            form.getInterconsulta().setText(json.getInt("interconsulta") + "");
            form.getMio().setText(json.getInt("mio") + "");
            form.getVideo().setText(json.getInt("video") + "");
            form.getSumaTotal().setText(json.getInt("suma") + "");
        } catch (JSONException ex) {
            Logger.getLogger(ControladorCirugia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public JSONObject getConfiguracion() throws JSONException{
        return SqlCirugia.getConfiguracion(form.getIdusuario(), connection);
    }
    
    public void modificarConfiguracion(int idconfiguracionmda, JSONObject configuracion) throws JSONException{
        SqlCirugia.modificarConfiguracion(idconfiguracionmda, configuracion, connection);
        JOptionPane.showMessageDialog(null, "Path modificado", "Mensaje del sistema", JOptionPane.WARNING_MESSAGE);
    }
}
