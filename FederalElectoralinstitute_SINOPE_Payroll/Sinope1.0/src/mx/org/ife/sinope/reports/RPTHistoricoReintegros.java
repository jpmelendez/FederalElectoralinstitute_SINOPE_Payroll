package mx.org.ife.sinope.reports;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mx.org.ife.sinope.dto.DTOUsuarioSinope;
import mx.org.ife.sinope.dto.reportes.DTOHistoricoReintegros;
import mx.org.ife.sinope.dto.reportes.DTORelacionChqNomina;
import mx.org.ife.sinope.util.GenericConstantsSinope;
import mx.org.ife.sinope.util.Utils;

import org.apache.log4j.Logger;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

public class RPTHistoricoReintegros {
	private Logger logger = Logger.getLogger(RPTHistoricoReintegros.class.getName());
	private List<DTOHistoricoReintegros> listaDetalle;
	private List<DTOHistoricoReintegros> listaFirmas;
	private int estado;
	private int distrito;
	private int area;
	private String unidadOrganizacional;
	private String nombreCompleto;


	public RPTHistoricoReintegros(List<DTOHistoricoReintegros> listaDetalle,
			List<DTOHistoricoReintegros> listaFirmas, String estadoDetalle, String distritoDetalle, String areaDetalle, String unidadOrganizacional, String nombreCompleto) {
		super();
		this.listaDetalle = listaDetalle;
		this.listaFirmas = listaFirmas!=null?listaFirmas:new ArrayList<DTOHistoricoReintegros>();;
		this.estado = Integer.parseInt(estadoDetalle);
		this.distrito = Integer.parseInt(distritoDetalle);
		this.area = Integer.parseInt(areaDetalle);
		this.unidadOrganizacional = unidadOrganizacional;
		this.nombreCompleto = nombreCompleto;
	}

	



	public File Reporte()throws DocumentException, IOException{
		
		try {

			Document document=new Document(PageSize.LETTER);								//si no le damos tamaño hace el estandar A4
			ByteArrayOutputStream baos= new ByteArrayOutputStream();		//creamos el stram de memoria
			PdfWriter writer = PdfWriter.getInstance(document,baos);		//creamos una instancia de pdfwriter y le decimos que lo creara en memoria
			writer.setPdfVersion(PdfWriter.VERSION_1_7);					//le pasamos la version del pdf

			Calendar calendario = Calendar.getInstance();
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
			String fechaBD="";
			String fecha ="";
			fecha=""+Utils.FormatoDateFecha(calendario.getTime(), "dd/MM/yyyy");
			logger.debug("------------Reporte historico de Reintegros---fecha="+fecha+"---------------------------------------");
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			String hora = sdf.format(calendario.getTime());

			/*String idEstado=""+this.dto.getIdEstado();
			String idDistrito=""+this.dto.getIdDistrito();
			String idArea=""+this.dto.getIdArea();*/

			String numeros="";
			PdfPCell cell;	
			Color color=new Color(245,245,252);
			Chunk cacho;
			Phrase frase;
			Font fuente;
			Paragraph parrafo;
			fuente=new Font();
			fuente.setSize(6f);
			fuente.setFamily("Arial");
			Font fuente2;
			fuente2=new Font();
			fuente2.setSize(7f);
			fuente2.setFamily("Arial");
			fuente2.setStyle(Font.BOLD);
			Image imgn=Image.getInstance(System.getProperty("html.home.sinope")+"/img/ife_400.jpg");
			imgn.scalePercent(20f);
			document.setMargins(33f, 33f, 105f, 35f);	//    left right top  bottom

			document.open();	//abrimos el documento

			/************************* tabla *************************************************/
			PdfPTable table;
			table = new PdfPTable(4);				
			table.setSpacingBefore(5);	
			table.setSpacingAfter(5);//definimos el espacio antes y despues de la tabla
			table.setWidthPercentage(97);						//el ancho en porcentaje
			table.setHorizontalAlignment(Element.ALIGN_CENTER);	//y le damos la alineacion	
			table.setWidths(new float[]{1, 1, 1, 1,});

			//renglon del nombre del reporte
			parrafo=new Paragraph(new Phrase("Reporte Historico de Reintegros", fuente2));
			parrafo.setAlignment(Element.ALIGN_CENTER);
			cell = new PdfPCell(parrafo);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setMinimumHeight(17f);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderWidthLeft(1f);
			cell.setBorderWidthTop(1f);
			cell.setBorderWidthRight(1f);
			cell.setBorderWidthBottom(1f);
			cell.setBackgroundColor(color);
			cell.setColspan(4);											
			table.addCell(cell);

			//iterando datos del reintegro Tabla principal
			for (DTOHistoricoReintegros tmp : listaDetalle ) {

				parrafo=new Paragraph(new Phrase("Fecha depósito: ", fuente2));
				parrafo.add(new Phrase("" + tmp.getPdfFechaDeposito(), fuente));
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell(parrafo);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setMinimumHeight(17f);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidthLeft(1f);
				cell.setBorderWidthTop(0f);
				cell.setBorderWidthRight(0f);
				cell.setBorderWidthBottom(0f);
				//cell.setBackgroundColor(color);
				cell.setColspan(2);											
				table.addCell(cell);

				parrafo=new Paragraph(new Phrase("Número de cheque: ", fuente2));
				parrafo.add(new Phrase("" + tmp.getPdfNumFolio(), fuente));
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell(parrafo);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setMinimumHeight(17f);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidthLeft(0f);
				cell.setBorderWidthTop(0f);
				cell.setBorderWidthRight(1f);
				cell.setBorderWidthBottom(0f);
				//cell.setBackgroundColor(color);
				cell.setColspan(2);											
				table.addCell(cell);
				
				parrafo=new Paragraph(new Phrase("Número de cuenta: ", fuente2));
				parrafo.add(new Phrase("" + tmp.getPdfNumCuenta(), fuente));
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell(parrafo);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setMinimumHeight(17f);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidthLeft(1f);
				cell.setBorderWidthTop(0f);
				cell.setBorderWidthRight(0f);
				cell.setBorderWidthBottom(0f);
				//cell.setBackgroundColor(color);
				cell.setColspan(2);											
				table.addCell(cell);

				parrafo=new Paragraph(new Phrase("Ficha de depósito: ", fuente2));
				parrafo.add(new Phrase("" + tmp.getPdfFichaDep(), fuente));
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell(parrafo);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setMinimumHeight(17f);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidthLeft(0f);
				cell.setBorderWidthTop(0f);
				cell.setBorderWidthRight(1f);
				cell.setBorderWidthBottom(0f);
				//cell.setBackgroundColor(color);
				cell.setColspan(2);											
				table.addCell(cell);
				
				parrafo=new Paragraph(new Phrase("Importe: ", fuente2));
				parrafo.add(new Phrase("$ " + tmp.getPdfImporteStr() + "    (" + tmp.getPdfImporteLetra() + ")", fuente));
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell(parrafo);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setMinimumHeight(17f);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidthLeft(1f);
				cell.setBorderWidthTop(0f);
				cell.setBorderWidthRight(1f);
				cell.setBorderWidthBottom(0f);
				//cell.setBackgroundColor(color);
				cell.setColspan(4);											
				table.addCell(cell);
				
				parrafo=new Paragraph(new Phrase("Información del empleado", fuente2));
				parrafo.setAlignment(Element.ALIGN_CENTER);
				cell = new PdfPCell(parrafo);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setMinimumHeight(17f);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidthLeft(1f);
				cell.setBorderWidthTop(1f);
				cell.setBorderWidthRight(1f);
				cell.setBorderWidthBottom(1f);
				cell.setBackgroundColor(color);
				cell.setColspan(4);											
				table.addCell(cell);

				parrafo=new Paragraph(new Phrase("Unidad responsable: ", fuente2));
				parrafo.add(new Phrase("" + tmp.getPdfClaveUnidad() + " - " + tmp.getPdfNombreUnidad(), fuente));
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell(parrafo);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setMinimumHeight(17f);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidthLeft(1f);
				cell.setBorderWidthTop(0f);
				cell.setBorderWidthRight(1f);
				cell.setBorderWidthBottom(0f);
				//cell.setBackgroundColor(color);
				cell.setColspan(4);											
				table.addCell(cell);

				parrafo=new Paragraph(new Phrase("Nombre del empleado: ", fuente2));
				parrafo.add(new Phrase("" + tmp.getPdfNombre(), fuente));
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell(parrafo);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setMinimumHeight(17f);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidthLeft(1f);
				cell.setBorderWidthTop(0f);
				cell.setBorderWidthRight(0f);
				cell.setBorderWidthBottom(0f);
				//cell.setBackgroundColor(color);
				cell.setColspan(2);											
				table.addCell(cell);

				parrafo=new Paragraph(new Phrase("RFC: ", fuente2));
				parrafo.add(new Phrase("" + tmp.getPdfRFC(), fuente));
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell(parrafo);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setMinimumHeight(17f);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidthLeft(0f);
				cell.setBorderWidthTop(0f);
				cell.setBorderWidthRight(1f);
				cell.setBorderWidthBottom(0f);
				//cell.setBackgroundColor(color);
				cell.setColspan(2);											
				table.addCell(cell);

				parrafo=new Paragraph(new Phrase("Quincena: ", fuente2));
				parrafo.add(new Phrase("" + tmp.getPdfQnaPago(), fuente));
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell(parrafo);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setMinimumHeight(17f);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidthLeft(1f);
				cell.setBorderWidthTop(0f);
				cell.setBorderWidthRight(1f);
				cell.setBorderWidthBottom(0f);
				//cell.setBackgroundColor(color);
				cell.setColspan(4);											
				table.addCell(cell);

				parrafo=new Paragraph(new Phrase("Radicación: ", fuente2));
				parrafo.add(new Phrase("" + tmp.getPdfRadicacion(), fuente));
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell(parrafo);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setMinimumHeight(17f);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidthLeft(1f);
				cell.setBorderWidthTop(0f);
				cell.setBorderWidthRight(1f);
				cell.setBorderWidthBottom(0f);
				//cell.setBackgroundColor(color);
				cell.setColspan(4);											
				table.addCell(cell);

				parrafo=new Paragraph(new Phrase("Tipo de Pago: ", fuente2));
				parrafo.add(new Phrase("" + tmp.getPdfTipoPago(), fuente));
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell(parrafo);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setMinimumHeight(17f);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidthLeft(1f);
				cell.setBorderWidthTop(0f);
				cell.setBorderWidthRight(1f);
				cell.setBorderWidthBottom(0f);
				//cell.setBackgroundColor(color);
				cell.setColspan(4);											
				table.addCell(cell);

				parrafo=new Paragraph(new Phrase("Forma de pago: ", fuente2));
				parrafo.add(new Phrase("" + tmp.getPdfFormaPago(), fuente));
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell(parrafo);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setMinimumHeight(17f);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidthLeft(1f);
				cell.setBorderWidthTop(0f);
				cell.setBorderWidthRight(1f);
				cell.setBorderWidthBottom(1f);
				//cell.setBackgroundColor(color);
				cell.setColspan(4);											
				table.addCell(cell);	
			}
			document.add(table);

			/************* Tabla Firmas ***************/
		

			int sizeTabla = listaFirmas.size() + 1;
			table = new PdfPTable(sizeTabla);				
			table.setSpacingBefore(5);	
			table.setSpacingAfter(5);//definimos el espacio antes y despues de la tabla
			table.setWidthPercentage(97);						//el ancho en porcentaje
			table.setHorizontalAlignment(Element.ALIGN_CENTER);	//y le damos la alineacion
			
			logger.debug("--- Reporte Reintegros: Tamaño tabla  " + sizeTabla);
			logger.debug("--- Reporte Reintegros: Total firmantes  " + listaFirmas.size());
			logger.debug("--- Reporte Reintegros: Nombre elaboro  " + nombreCompleto);


			if (sizeTabla == 1) {

				parrafo=new Paragraph(new Phrase("Elaboró", fuente2));
				parrafo.setAlignment(Element.ALIGN_CENTER);
				cell = new PdfPCell(parrafo);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setMinimumHeight(17f);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidthLeft(0.2f);
				cell.setBorderWidthTop(0.2f);
				cell.setBorderWidthRight(0.2f);
				cell.setBorderWidthBottom(0.2f);
				cell.setBackgroundColor(color);	
				table.addCell(cell);

				parrafo=new Paragraph(new Phrase("", fuente2));
				parrafo.setAlignment(Element.ALIGN_CENTER);
				cell = new PdfPCell(parrafo);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setMinimumHeight(68f);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidthLeft(0.2f);
				cell.setBorderWidthTop(0f);
				cell.setBorderWidthRight(0.2f);
				cell.setBorderWidthBottom(0.2f);
				table.addCell(cell);

				
				parrafo=new Paragraph(new Phrase(nombreCompleto, fuente));
				parrafo.setAlignment(Element.ALIGN_CENTER);
				cell = new PdfPCell(parrafo);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);cell.setMinimumHeight(17f);cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidthLeft(0.2f);
				cell.setBorderWidthTop(0.2f);
				cell.setBorderWidthRight(0.2f);
				cell.setBorderWidthBottom(0.2f);
				table.addCell(cell);

			} else {

				parrafo=new Paragraph(new Phrase("Elaboró", fuente2));
				parrafo.setAlignment(Element.ALIGN_CENTER);
				cell = new PdfPCell(parrafo);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setMinimumHeight(17f);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidthLeft(0.2f);
				cell.setBorderWidthTop(0.2f);
				cell.setBorderWidthRight(0f);
				cell.setBorderWidthBottom(0.2f);
				cell.setBackgroundColor(color);	
				table.addCell(cell);


				for (int i = 0; i < listaFirmas.size(); i++) {

					parrafo=new Paragraph(new Phrase("Autorizó", fuente2));
					parrafo.setAlignment(Element.ALIGN_CENTER);
					cell = new PdfPCell(parrafo);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);cell.setMinimumHeight(17f);cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorderWidthLeft(0.2f);
					cell.setBorderWidthTop(0.2f);
					cell.setBorderWidthRight(0.2f);
					cell.setBorderWidthBottom(0.2f);
					cell.setBackgroundColor(color);	
					table.addCell(cell);
				}
				
				parrafo=new Paragraph(new Phrase("", fuente2));
				parrafo.setAlignment(Element.ALIGN_CENTER);
				cell = new PdfPCell(parrafo);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setMinimumHeight(68f);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidthLeft(0.2f);
				cell.setBorderWidthTop(0f);
				cell.setBorderWidthRight(0.2f);
				cell.setBorderWidthBottom(0.2f);
				table.addCell(cell);
				
				
				for(DTOHistoricoReintegros tmp : listaFirmas){
					
					logger.debug("--- CON FIRMA ? ::: " + tmp.getFirmaDocumentoFirma());
					
					if (tmp.getFirmaDocumentoFirma().equals("S") ) {
						
						if(tmp.getFirmaImagenString() != null && !tmp.getFirmaImagenString().equals("")){
							Image imgTMP = Image.getInstance(tmp.getFirmaImagenString());
							imgTMP.scaleToFit(70f, 70f);
							//imgTMP.scalePercent(15f);
							cell = new PdfPCell(imgTMP);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setMinimumHeight(68f);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setBorderWidthLeft(0.2f);
							cell.setBorderWidthTop(0f);
							cell.setBorderWidthRight(0.2f);
							cell.setBorderWidthBottom(0.2f);
							table.addCell(cell);
							
						}else {
							parrafo=new Paragraph(new Phrase("", fuente2));
							parrafo.setAlignment(Element.ALIGN_CENTER);
							cell = new PdfPCell(parrafo);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setMinimumHeight(68f);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setBorderWidthLeft(0.2f);
							cell.setBorderWidthTop(0f);
							cell.setBorderWidthRight(0.2f);
							cell.setBorderWidthBottom(0.2f);
							table.addCell(cell);
						}
						
					} else {
						parrafo=new Paragraph(new Phrase("", fuente2));
						parrafo.setAlignment(Element.ALIGN_CENTER);
						cell = new PdfPCell(parrafo);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setMinimumHeight(68f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorderWidthLeft(0.2f);
						cell.setBorderWidthTop(0f);
						cell.setBorderWidthRight(0.2f);
						cell.setBorderWidthBottom(0.2f);
						table.addCell(cell);
					}
					
				
				}
			

				parrafo=new Paragraph(new Phrase(nombreCompleto, fuente));
				parrafo.setAlignment(Element.ALIGN_CENTER);
				cell = new PdfPCell(parrafo);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);cell.setMinimumHeight(17f);cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidthLeft(0.2f);
				cell.setBorderWidthTop(0.2f);
				cell.setBorderWidthRight(0.2f);
				cell.setBorderWidthBottom(0f);
				table.addCell(cell);

				for (DTOHistoricoReintegros tmp : listaFirmas ) {

					parrafo=new Paragraph(new Phrase(tmp.getFirmaRespNombre(), fuente));
					parrafo.setAlignment(Element.ALIGN_CENTER);
					cell = new PdfPCell(parrafo);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorderWidthLeft(0.2f);
					cell.setBorderWidthTop(0.2f);
					cell.setBorderWidthRight(0.2f);
					cell.setBorderWidthBottom(0);
					table.addCell(cell);	
				}
				parrafo=new Paragraph(new Phrase(" ", fuente));
				parrafo.setAlignment(Element.ALIGN_CENTER);
				cell = new PdfPCell(parrafo);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setMinimumHeight(17f);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidthLeft(0.2f);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthRight(0.2f);
				cell.setBorderWidthBottom(0.2f);
				table.addCell(cell);

				for (DTOHistoricoReintegros tmp : listaFirmas ) {

					parrafo=new Paragraph(new Phrase(tmp.getFirmaCargo(), fuente));
					parrafo.setAlignment(Element.ALIGN_TOP);
					cell = new PdfPCell(parrafo);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_TOP);
					cell.setBorderWidthLeft(0.2f);
					cell.setBorderWidthTop(0);
					cell.setBorderWidthRight(0.2f);
					cell.setBorderWidthBottom(0.2f);
					table.addCell(cell);	
				}

			}

			document.add(table);

			/*****************Cierre de documento********************/

			document.close();		
			File tempFileFinal=File.createTempFile("HistoricoReintegros.pdf", ".tmp");	//creamos otro tempfile
			PdfReader lector = new PdfReader(baos.toByteArray());


			/*****************Encabezado***************************/

			String regla="";
			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);

			PdfStamper stamper =new PdfStamper(lector, new FileOutputStream(tempFileFinal));
			PdfContentByte canvas;
			int n = lector.getNumberOfPages();
			for(int i=1; i<=n;i++)
			{
				canvas = stamper.getOverContent(i);			//nos ponemos en la pagina
				imgn.setAbsolutePosition(40f, document.getPageSize().getHeight()-80f);		//le damos posicion a la imagen
				canvas.addImage(imgn);	//agregamos la imagen

				canvas.saveState();

				//encabezado
				regla="INSTITUTO FEDERAL ELECTORAL";
				canvas.beginText();						
				//				logger.debug("----------------------relacion de cheques entra aqui 2---------------");
				//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
				canvas.setFontAndSize(bf, 7);
				canvas.setTextMatrix(document.getPageSize().getWidth()-(document.getPageSize().getWidth()/2f)-((regla.length()*4.2f)/2f), document.getPageSize().getHeight()-35f);
				canvas.showText(regla);
				canvas.endText(); 


				regla="DIRECCIÓN EJECUTIVA DE ADMINISTRACIÓN"; //
				canvas.beginText();						
				//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
				//				logger.debug("----------------------relacion de cheques entra aqui 3---------------");
				canvas.setFontAndSize(bf, 7);
				canvas.setTextMatrix(document.getPageSize().getWidth()-(document.getPageSize().getWidth()/2f)-((regla.length()*4.2f)/2f), document.getPageSize().getHeight()-45f);
				canvas.showText(regla);
				canvas.endText();

				regla="REINTEGRO DE RECURSOS POR COBRO DE CHEQUE"; //
				canvas.beginText();						
				//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
				//				logger.debug("----------------------relacion de cheques entra aqui 3---------------");
				canvas.setFontAndSize(bf, 7);
				canvas.setTextMatrix(document.getPageSize().getWidth()-(document.getPageSize().getWidth()/2f)-((regla.length()*4.2f)/2f), document.getPageSize().getHeight()-55f);
				canvas.showText(regla);
				canvas.endText();

				canvas.beginText();		//primer renglon				
				//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
				//				logger.debug("----------------------relacion de cheques entra aqui 4---------------");
				canvas.setFontAndSize(bf, 7);
				canvas.setTextMatrix((document.getPageSize().getWidth()-(document.getPageSize().getWidth()/2f))+192f, document.getPageSize().getHeight()-55f);
				canvas.showText("Fecha:  ");
				canvas.endText();

				regla="Y QUE NO PROCEDÍA SU PAGO"; //
				canvas.beginText();						
				//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
				//				logger.debug("----------------------relacion de cheques entra aqui 3---------------");
				canvas.setFontAndSize(bf, 7);
				canvas.setTextMatrix(document.getPageSize().getWidth()-(document.getPageSize().getWidth()/2f)-((regla.length()*4.2f)/2f), document.getPageSize().getHeight()-65f);
				canvas.showText(regla);
				canvas.endText();

				regla="PROCESO FEDERAL ELECTORAL 2011 - 2012"; //
				canvas.beginText();						
				//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
				//				logger.debug("----------------------relacion de cheques entra aqui 3---------------");
				canvas.setFontAndSize(bf, 7);
				canvas.setTextMatrix(document.getPageSize().getWidth()-(document.getPageSize().getWidth()/2f)-((regla.length()*4.2f)/2f), document.getPageSize().getHeight()-75f);
				canvas.showText(regla);
				canvas.endText();

				canvas.beginText();						
				//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
				canvas.setFontAndSize(bf, 7);
				canvas.setTextMatrix((document.getPageSize().getWidth()-(document.getPageSize().getWidth()/2f))+192f, document.getPageSize().getHeight()-65f);
				canvas.showText(fecha+" "+hora+" hrs.");
				canvas.endText();


				//pagina x de y
				canvas.beginText();						
				//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
				canvas.setFontAndSize(bf, 7);
				canvas.setTextMatrix(document.getPageSize().getWidth()-90f, 20f);
				canvas.showText("Pagina "+i+" de "+n);
				canvas.endText();

				canvas.restoreState();
			}
			stamper.close();													//cerramos el stamper
			tempFileFinal.deleteOnExit();
			logger.debug("----------------------Reporte Historico de Reintegros---------------");

			return tempFileFinal;

		} catch (Exception e) {
			logger.debug("---------Reporte Historico Reintegros ----- e: "+e+"-----------");
			e.printStackTrace();
			return null;
		}

	}


}
