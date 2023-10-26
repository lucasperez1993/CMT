/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author djaime
 */
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PDFToImageConverter {
    public static void ConvertPdfToImg() {
        try {
            String pdfFilePath = "D:\\CIRUGIA_MESA_DE_AYUDA\\GONZALEZ LOURDES PROTOCOLO.pdf";
            PDDocument document = PDDocument.load(new File(pdfFilePath));
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {
                BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 300, ImageType.RGB);
                String outputImagePath = "D:\\CIRUGIA_MESA_DE_AYUDA\\" + (pageIndex + 1) + ".png";
                ImageIO.write(image, "PNG", new File(outputImagePath));
            }
            document.close();

            System.out.println("Conversión de PDF a imágenes completada.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String innovarCompletoPDF(int periodo, String nombre) throws IOException {
		String mensaje = "Error al descargar el documento";
		mensaje = "Documento generado con exito";
		File file = new File("D:\\CIRUGIA IPSST\\202308\\IMPRIMIR\\");
		File[] matriculas = file.listFiles();
		boolean crearPDF = false;
		PDDocument doc = null;
		for (File matricula : matriculas) {
			//try (doc = new PDDocument()) {
				doc = new PDDocument();
				File af = new File("D:\\CIRUGIA IPSST\\202308\\IMPRIMIR\\carga\\" + matricula.getName());
				File[] afiliados = af.listFiles();
				//boolean crearPDF = false;
				 
				for (File afiliado : afiliados) {
					File cirugia = new File("D:\\CIRUGIA IPSST\\202308\\IMPRIMIR\\carga\\" + matricula.getName() + "\\" + afiliado.getName());
					File[] cf = cirugia.listFiles();
					for (File protocolo : cf) {
						String keyword = nombre;
						Boolean found = Arrays.asList(afiliado.getName().split(" ")).contains(keyword);
						if (found) {
							crearPDF = true;
							
							File original = new File("D:\\CIRUGIA IPSST\\202308\\IMPRIMIR\\carga\\" + matricula.getName() + "\\" + afiliado.getName() + "\\" + protocolo.getName());
							//File fileImg = new File("D:\\CIRUGIA IPSST\\CONVERT_202306\\PDF\\INNOVAR\\imagen.jpg");
							PDPage page = new PDPage(PDRectangle.A4);
							doc.addPage(page);
				            PDPageContentStream contentStream = new PDPageContentStream(doc, page);
				            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
					        contentStream.beginText();
					        contentStream.newLineAtOffset(280, 825);
					        contentStream.showText("MATRICULA: " + matricula.getName() + " Afiliado: " + afiliado.getName());
					        contentStream.endText();
							
							PDImageXObject image = PDImageXObject.createFromFileByContent(original, doc);
				            float imageWidth = PDRectangle.A4.getWidth();
				            float imageHeight = PDRectangle.A4.getHeight();
				            contentStream.drawImage(image, 0, 0, imageWidth, imageHeight-20);
				            contentStream.close();
							//	Files.find(Paths.get(original.toString()), Integer.MAX_VALUE, (path, basicFileAttributes) -> Files.isRegularFile(path))
							//			.forEachOrdered(path -> addImageAsNewPage(doc, path.toString()));
							//} catch (IOException e) {}
						}
					}
				}
				
			//}
			if(crearPDF) {
				//doc.save("D:\\CIRUGIA IPSST\\PDF202307\\" + matricula.getName() + "\\" + nombre + "_" + periodo + ".pdf");
				doc.save("D:\\CIRUGIA IPSST\\PDF202307\\COMPLETO\\" + nombre + "\\" + nombre + "_" + periodo + ".pdf");
	            doc.close();
			}
		}
		return mensaje;
	}
} 