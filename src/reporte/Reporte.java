/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporte;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author djaime
 */
public class Reporte {
    public static boolean imprimir(){
        try {
            JasperReport masterReport = null;
            Map params = new HashMap();
            masterReport = (JasperReport) JRLoader.loadObject("D:\\ECO\\eco.jasper");
            //masterReport = (JasperReport) JRLoader.loadObject(prope.getProperty("path").trim());
            params.clear();
            params.put("idhcparametro", 1);
            JasperPrint jasperprint = JasperFillManager.fillReport(masterReport,params);
            //DialogoImprimir dialogo = new DialogoImprimir(null, true, new JRViewer(jasperprint));
            //DialogoImprimir dialogo = new DialogoImprimir(formLineaHc.parent, true, new JRViewer(jasperprint));
        } catch (JRException ex) {}
        return false;
    }
    
    public static JRViewer runReporteObjeto(String ruta) throws JRException{
    //public static JasperPrint runReporteObjeto(String ruta) throws SQLException, JRException{
        JasperPrint jasperprint = null;
            ArrayList<ObjetoReporte> listaObjetoReporte = new ArrayList<>();

        for(int indice = 1; indice < 10; indice++){
            ObjetoReporte objetoReporte = new ObjetoReporte();
            objetoReporte.setParametro1("Catidad: " + indice);
            listaObjetoReporte.add(objetoReporte);
        }
        JasperReport masterReport = (JasperReport) JRLoader.loadObject(ruta);
        Map parametro = new HashMap();
        parametro.put("ptitulo", "Reporte Objeto");
        jasperprint = JasperFillManager.fillReport(masterReport,parametro,new JRBeanCollectionDataSource(listaObjetoReporte));
        return new JRViewer(jasperprint);
        //return jasperprint;
    }
    
    public static byte[] getReporteWeb(String ruta) throws JRException{
        ArrayList<ObjetoReporte> listaObjetoReporte = new ArrayList<>();

        for(int indice = 1; indice < 10; indice++){
            ObjetoReporte objetoReporte = new ObjetoReporte();
            objetoReporte.setParametro1("Catidad: " + indice);
            listaObjetoReporte.add(objetoReporte);
        }
        JasperReport masterReport = (JasperReport) JRLoader.loadObject(ruta);
        Map parametro = new HashMap();
        parametro.put("ptitulo", "Reporte Objeto");
        byte[] bytes = JasperRunManager.runReportToPdf(masterReport, parametro);
        return bytes;
    }
    
//    public static boolean imprimirTest(java.awt.Frame parent) throws SQLException, JRException{
//        
//        DialogoImprimir dialogo = new DialogoImprimir(parent, true, runReporteObjeto("D:\\REPORTE\\r_medico_paciente.jasper"));
//        dialogo.setVisible(true);
//        return false;
//    }
//     
    public static ArrayList<ObjetoReporte> getListaMedicoPaciente(){
        ArrayList<ObjetoReporte> listaObjetoReporte = new ArrayList<>();
        return listaObjetoReporte;
    }
    
//    public static void main(String arg[]) throws JRException{
//        runReporteObjeto("D:\\REPORTE\\r_medico_paciente");
//        //imprimirTest(null)
//    }
}
