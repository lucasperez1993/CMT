/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cirugia;

/**
 *
 * @author djaime
 */
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GuardarImagenConFileChooser extends JFrame {

    private JButton btnGuardar;
    private JFileChooser fileChooser;
    //private BufferedImage imagen;
    private int matricula = 0;
    private String practica = "";
    private String cuil = "";
    String path_defoult = "";

    public GuardarImagenConFileChooser(int matricula, String cuil, String practica, String path_defoult) {
        setTitle("Guardar pedio - protocolo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(200, 150);
        setLocationRelativeTo(null);

        this.matricula = matricula;
        this.cuil = cuil;
        this.practica = practica;
        this.path_defoult = path_defoult;
        
        btnGuardar = new JButton("Guardar Imagen");
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarImagen();
            }
        });

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de Imagen", "png", "jpg", "jpeg"));

        JPanel panel = new JPanel();
        panel.add(btnGuardar);

        add(panel, BorderLayout.CENTER);
    }

    private void guardarImagen() {
        int resultado = fileChooser.showSaveDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoOrigen = fileChooser.getSelectedFile();
            // Cambia la ubicación de destino según tus necesidades
            String rutaDestino = "D:\\TEST_IMG\\" + matricula + "_" + cuil + "_" + practica + ".png";;
            try {
                copiarArchivo(archivoOrigen, new File(rutaDestino));
                JOptionPane.showMessageDialog(this, "Imagen copiada exitosamente");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al copiar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
            }
//            File archivo = fileChooser.getSelectedFile();
//            try {
//                // Guardar la imagen en el formato seleccionado (por ejemplo, PNG)
//                //BufferedImage imagen = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
//                File archi = new File(rutaArchivo);
//                ImageIO.write(imagen, "png", archivo);
//                JOptionPane.showMessageDialog(this, "Imagen guardada exitosamente");
//            } catch (IOException e) {
//                JOptionPane.showMessageDialog(this, "Error al guardar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
//            }
        }
    }

    private void copiarArchivo(File origen, File destino) throws IOException {
        InputStream in = new FileInputStream(origen);
        OutputStream out = new FileOutputStream(destino);
        byte[] buffer = new byte[1024];
        int longitud;
        while ((longitud = in.read(buffer)) > 0) {
            out.write(buffer, 0, longitud);
        }
        in.close();
        out.close();
    }

    public void descargarImgParaIPSST(JSONArray lista) {
        try {
            PDDocument doc = null;
            doc = new PDDocument();
            for (int indice = 0; indice < lista.length(); indice++) {
                JSONObject _json = lista.getJSONObject(indice);
                File original = new File(_json.getString("path") + _json.getString("documento"));
                PDPage page = new PDPage(PDRectangle.A4);
                doc.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(doc, page);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.beginText();
                contentStream.newLineAtOffset(280, 825);
                contentStream.showText("MATRICULA: " + matricula + " Afiliado: " + cuil);
                contentStream.endText();
                PDImageXObject image = PDImageXObject.createFromFileByContent(original, doc);
                float imageWidth = PDRectangle.A4.getWidth();
                float imageHeight = PDRectangle.A4.getHeight();
                contentStream.drawImage(image, 0, 0, imageWidth, imageHeight - 20);
                contentStream.close();
            }

            JFileChooser fileChooser = new JFileChooser();
            if(!path_defoult.equals(""))
                fileChooser = new JFileChooser(path_defoult);
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String selectedPath = fileChooser.getSelectedFile().getAbsolutePath();
                String rutaDestino = selectedPath + "\\" + matricula + "_" + cuil + ".pdf";
                doc.save(rutaDestino);
                doc.close();
                JOptionPane.showMessageDialog(this, "Imagen descargada: " + rutaDestino);
            }
        } catch (IOException ex) {
            Logger.getLogger(GuardarImagenConFileChooser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(GuardarImagenConFileChooser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
