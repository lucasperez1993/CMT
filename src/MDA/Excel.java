// 
// Decompiled by Procyon v0.5.36
// 

package MDA;

import Conexion.Conexion;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.OutputStream;
import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Excel
{
    public void cargarXLS(final DialogoControl dialogo, final String fileName) throws SQLException, IOException {
        Workbook book = (Workbook)new HSSFWorkbook();
        Sheet sheet = book.createSheet("Consulta");
        String[] cabecera = { "CODIGO", "N° Socio / Cargador", "Nombre", "Motivo", "Solución", "Medio", "Obra Social / Sistema", "Observaciones", "Fecha Inicio", "Fecha Fin" };
        Row filaEncabezado = sheet.createRow(0);
        for (int i = 0; i < cabecera.length; ++i) {
            final Cell celdaEncabezado = filaEncabezado.createCell(i);
            celdaEncabezado.setCellValue(cabecera[i]);
        }
        Conexion con = new Conexion();
        Connection conn = con.GetConnectionCloud();
        int numfilaDatos = 1;
        PreparedStatement ps = conn.prepareStatement(dialogo.consulta);
        ResultSet rs = ps.executeQuery();
        int numCol = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            final Row filaDatos = sheet.createRow(numfilaDatos);
            for (int j = 0; j < numCol; ++j) {
                final Cell celdaDatos = filaDatos.createCell(j);
                celdaDatos.setCellValue(rs.getString(j + 1));
            }
            ++numfilaDatos;
        }
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
        sheet.autoSizeColumn(8);
        sheet.autoSizeColumn(9);
        sheet.autoSizeColumn(10);
        try (final FileOutputStream fileOut = new FileOutputStream("C:\\Mesa de Ayuda\\" + fileName + ".xls")) {
            book.write((OutputStream)fileOut);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
