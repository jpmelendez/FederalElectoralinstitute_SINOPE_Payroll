package mx.org.ife.sinope.reports;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import mx.org.ife.sinope.dao.reportes.DAOHistoricoMovimientos;
import mx.org.ife.sinope.dto.reportes.DTOHistoricoMovimientos;
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

public class RPTHistoricoMovimientosE 
{
	private Logger logger = Logger.getLogger(RPTHistoricoMovimientosE.class.getName());
	private DTOHistoricoMovimientos dto;
	public  RPTHistoricoMovimientosE(DTOHistoricoMovimientos dto) 
	{
		this.dto=dto;
	}
	public File Reporte()throws DocumentException, IOException
	{
		String tiponomina="";
		String contrato="";
		Document document=new Document(PageSize.LETTER);				//si no le damos tamaño hace el estandar A4
		ByteArrayOutputStream baos= new ByteArrayOutputStream();		//creamos el stram de memoria
		PdfWriter writer = PdfWriter.getInstance(document,baos);		//creamos una instancia de pdfwriter y le decimos que lo creara en memoria
		writer.setPdfVersion(PdfWriter.VERSION_1_7);					//le pasamos la version del pdf
		
		String idEstado=dto.getIdEstado();
		String idDistrito=""+this.dto.getIdDistrito();
		String idArea=""+this.dto.getIdArea();
		
		String idEmpleadoContrato = ""; //Variable utilizada para buscar el contrato
		String numPlazaContrato = ""; //Variable utilizada para buscar el contrato
		
		String cveDescripcionUR="";
		List list2=this.dto.getRenglonesEReporte();
		Iterator it2=list2.iterator();
		while(it2.hasNext())
		{	
			Object[] st2=(Object[])it2.next();
			cveDescripcionUR=""+st2[14];
			logger.debug("cveDescripcionUR:"+cveDescripcionUR);
		}
		
		
		Calendar calendario = Calendar.getInstance();
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		String fecha ="";
		fecha=""+Utils.FormatoDateFecha(calendario.getTime(), "dd/MM/yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String hora = sdf.format(calendario.getTime());
		PdfPCell cell;	
		PdfPCell cell1;	
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
		document.setMargins(33f, 33f, 85f, 35f);	//    left right top  bottom
		
		document.open();	
		
		/*****************************tabla titulo del reporte***************************************/
		PdfPTable table;
		table = new PdfPTable(5);				
		table.setSpacingBefore(5);	
		table.setSpacingAfter(5);//definimos el espacio antes y despues de la tabla
		table.setWidthPercentage(97);						//el ancho en porcentaje
		table.setHorizontalAlignment(Element.ALIGN_CENTER);	//y le damos la alineacion	
		table.setWidths(new float[]{0.2f, 0.14f, 0.20f, 0.35f, 0.12f});
		
		
		//renglon del nombre del reporte
		parrafo=new Paragraph(new Phrase("Reporte Histórico de Movimientos", fuente2));
		parrafo.setAlignment(Element.ALIGN_CENTER);
		cell = new PdfPCell(parrafo);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setMinimumHeight(17f);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorderWidthLeft(1f);
		cell.setBorderWidthTop(1f);
		cell.setBorderWidthRight(1f);
		cell.setBorderWidthBottom(0.2f);
		cell.setBackgroundColor(color);
		cell.setColspan(5);											
		table.addCell(cell);
		
		
		//renglon cve descripcion de unidad responsable
		parrafo=new Paragraph(new Phrase(""+cveDescripcionUR, fuente2));
		parrafo.setAlignment(Element.ALIGN_CENTER);
		cell = new PdfPCell(parrafo);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setMinimumHeight(17f);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorderWidthLeft(1f);
		cell.setBorderWidthTop(0.2f);
		cell.setBorderWidthRight(1f);
		cell.setBorderWidthBottom(0.2f);
		cell.setBackgroundColor(color);
		cell.setColspan(5);											
		table.addCell(cell);
		
		//renglon del nombre del reporte
		parrafo=new Paragraph(new Phrase("EMPLEADO", fuente2));
		parrafo.setAlignment(Element.ALIGN_CENTER);
		cell = new PdfPCell(parrafo);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setMinimumHeight(17f);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorderWidthLeft(1f);
		cell.setBorderWidthTop(0.2f);
		cell.setBorderWidthRight(1f);
		cell.setBorderWidthBottom(0.2f);
		cell.setBackgroundColor(color);
		cell.setColspan(5);											
		table.addCell(cell);
		
//		System.out.println("-------------------- en reporte e ----------------------");
		
		PdfPTable table1 = new PdfPTable(5);					//Tabla para los puestos de consejero
		table1.setSpacingBefore(5);	
		table1.setSpacingAfter(5);								//definimos el espacio antes y despues de la tabla
		table1.setWidthPercentage(97);							//el ancho en porcentaje
		table1.setHorizontalAlignment(Element.ALIGN_CENTER);	//y le damos la alineacion
		
		PdfPTable table2 = new PdfPTable(5);					//Tabla para el puesto diferentes de consejero
		table2.setSpacingBefore(5);	
		table2.setSpacingAfter(5);								//definimos el espacio antes y despues de la tabla
		table2.setWidthPercentage(97);							//el ancho en porcentaje
		table2.setHorizontalAlignment(Element.ALIGN_CENTER);	//y le damos la alineacion
		
		PdfPTable table3 = new PdfPTable(1);					//Tabla contenedora para las otras dos tablas
		table3.setSpacingBefore(5);	
		table3.setSpacingAfter(5);								//definimos el espacio antes y despues de la tabla
		table3.setWidthPercentage(97);							//el ancho en porcentaje
		table3.setHorizontalAlignment(Element.ALIGN_CENTER);	//y le damos la alineacion
		table3.setWidths(new float[]{ 0.98f });
		
 		String anteriorId="";
 		String rfcC="";//rfc de hist mov
 		String nombreC="";//nombre de hist mov
 		String cambio="";// indica si es movimiento CAMBIO poniendo una 's'
		Iterator<?> it = this.dto.getRenglonesEReporte().iterator();
		Integer contador = 0;
		while(it.hasNext())
		{	
			Object[] st=(Object[])it.next();
			
//			System.out.println("------ >>>>> JONAS ---->>>>" + st[15]);
			if (st[15].toString().contains(GenericConstantsSinope.Puestos.DIETAS_CONSEJERO_DISTRITAL)
					||st[15].toString().contains(GenericConstantsSinope.Puestos.DIETAS_CONSEJERO_LOCAL)
					||st[15].toString().contains(GenericConstantsSinope.Puestos.DIETAS_PROVISIONAL1)
					||st[15].toString().contains(GenericConstantsSinope.Puestos.DIETAS_PROVISIONAL2)
					||st[15].toString().contains(GenericConstantsSinope.Puestos.DIETAS_SUPLENTE1_DISTRITAL)
					||st[15].toString().contains(GenericConstantsSinope.Puestos.INSTALACION_CONSEJERO_DISTRITAL)
					||st[15].toString().contains(GenericConstantsSinope.Puestos.INSTALACION_CONSEJERO_LOCAL)) {
				//Aqui inicia la tabla para puesto de consejero
				table1.setWidths(new float[]{0.14f, 0.20f, 0.25f, 0.35f, 0.12f});
				
				if(!anteriorId.equals(st[4].toString())){			//comprueba que no sea el mismo empleado que en la corrida pasada
					contador = 0;
					parrafo=new Paragraph(new Phrase("", fuente2));
					parrafo.setAlignment(Element.ALIGN_CENTER);
					cell = new PdfPCell(parrafo);
					cell.setBorderWidthLeft(0f);
					cell.setBorderWidthTop(0f);
					cell.setBorderWidthRight(0f);
					cell.setBorderWidthBottom(0.5f);
					cell.setColspan(5);											
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table1.addCell(cell);	
					
					parrafo=new Paragraph(new Phrase("Nombre del empleado: ", fuente2));
					parrafo.add(new Phrase(""+st[1]+Chunk.NEWLINE, fuente));
					parrafo.add(new Phrase("                    CURP: ", fuente2));
					parrafo.add(new Phrase(""+st[2], fuente));
					parrafo.add(new Phrase("                RFC: ", fuente2));
					parrafo.add(new Phrase(""+st[3], fuente));
					parrafo.add(new Phrase("                 Núm Empleado: ", fuente2));
					parrafo.add(new Phrase(""+st[4], fuente));
					parrafo.setAlignment(Element.ALIGN_CENTER);
					cell = new PdfPCell(parrafo);
					cell.setBorderWidthLeft(1f);
					cell.setBorderWidthTop(1f);
					cell.setBorderWidthRight(1f);
					cell.setBorderWidthBottom(0.5f);
					cell.setBackgroundColor(color);
					cell.setColspan(5);											
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table1.addCell(cell);				
					
					cell=new PdfPCell(new Phrase("No.",fuente2));//renglon 2
					cell.setBorderWidthLeft(1f);
					cell.setBorderWidthTop(0.5f);
					cell.setBorderWidthRight(0.5f);
					cell.setBorderWidthBottom(0.5f);
					cell.setBackgroundColor(color);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table1.addCell(cell);
					
					cell=new PdfPCell(new Phrase("Movimiento",fuente2));
					cell.setBorderWidthLeft(0.5f);
					cell.setBorderWidthTop(0.5f);
					cell.setBorderWidthRight(0.5f);
					cell.setBorderWidthBottom(0.5f);
					cell.setBackgroundColor(color);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table1.addCell(cell);
					
					cell=new PdfPCell(new Phrase("Fecha",fuente2));
					cell.setBorderWidthLeft(0.5f);
					cell.setBorderWidthTop(0.5f);
					cell.setBorderWidthRight(0.5f);
					cell.setBorderWidthBottom(0.5f);
					cell.setBackgroundColor(color);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table1.addCell(cell);
	
					cell=new PdfPCell(new Phrase("Puesto",fuente2));
					cell.setBorderWidthLeft(0.5f);
					cell.setBorderWidthTop(0.5f);
					cell.setBorderWidthRight(0.5f);
					cell.setBorderWidthBottom(0.5f);
					cell.setBackgroundColor(color);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table1.addCell(cell);
					
					cell=new PdfPCell(new Phrase("Plaza",fuente2));
					cell.setBorderWidthLeft(0.5f);
					cell.setBorderWidthTop(0.5f);
					cell.setBorderWidthRight(1f);
					cell.setBorderWidthBottom(0.5f);
					cell.setBackgroundColor(color);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table1.addCell(cell);
				
				}
				
				rfcC = ""+st[11];
				nombreC = ""+st[12];
				nombreC = nombreC.trim();
				cambio = ""+st[13];
//				logger.debug("rfcC:"+rfcC+", nombreC:"+nombreC+", cambio:"+cambio);
				
				if(cambio.equals("s") && ((!rfcC.equals("")&&!rfcC.equals("null"))
						||(!nombreC.equals(" ")&&!nombreC.equals("")&&!nombreC.equals("null")))) {

					contador = contador + 1;
					// Consecutivo del movimiento
					cell=new PdfPCell(new Phrase(contador.toString(),fuente));
					cell.setBorderWidthLeft(1f);
					cell.setBorderWidthTop(0.5f);
					cell.setBorderWidthRight(0.5f);
					cell.setBorderWidthBottom(1f);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table1.addCell(cell);
					
					// Tipo de movimiento (ALTA, BAJA, REINGRESO O MODIFICACIÓN)
					cell=new PdfPCell(new Phrase(""+st[7],fuente));
					cell.setBorderWidthLeft(0.5f);
					cell.setBorderWidthTop(0.5f);
					cell.setBorderWidthRight(0.5f);
					cell.setBorderWidthBottom(1f);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table1.addCell(cell);
					
					// Fecha de movimiento del empleado
					cell=new PdfPCell(new Phrase(""+st[8],fuente));
					cell.setBorderWidthLeft(0.5f);
					cell.setBorderWidthTop(0.5f);
					cell.setBorderWidthRight(0.5f);
					cell.setBorderWidthBottom(1f);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table1.addCell(cell);
					
					// Puesto
					cell=new PdfPCell(new Phrase(""+st[9],fuente));
					cell.setBorderWidthLeft(0.5f);
					cell.setBorderWidthTop(0.5f);
					cell.setBorderWidthRight(0.5f);
					cell.setBorderWidthBottom(1f);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table1.addCell(cell);
					
					// Número de plaza
					cell=new PdfPCell(new Phrase(""+st[10],fuente));
					cell.setBorderWidthLeft(0.5f);
					cell.setBorderWidthTop(0.5f);
					cell.setBorderWidthRight(1f);
					cell.setBorderWidthBottom(1f);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table1.addCell(cell);
					
					anteriorId=""+st[4];
				} else {//no escribe el cambio
					if(cambio.equals("null")) {//si no es renglon de cambio no valida nada y lo presenta
						contador = contador + 1;
						
						// Consecutivo del movimiento
						cell=new PdfPCell(new Phrase(contador.toString(),fuente));
						cell.setBorderWidthLeft(1f);
						cell.setBorderWidthTop(0.5f);
						cell.setBorderWidthRight(0.5f);
						cell.setBorderWidthBottom(1f);
						cell.setMinimumHeight(17f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table1.addCell(cell);
						
						// Tipo de movimiento (ALTA, BAJA, REINGRESO O MODIFICACIÓN)
						cell=new PdfPCell(new Phrase(""+st[7],fuente));
						cell.setBorderWidthLeft(0.5f);
						cell.setBorderWidthTop(0.5f);
						cell.setBorderWidthRight(0.5f);
						cell.setBorderWidthBottom(1f);
						cell.setMinimumHeight(17f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table1.addCell(cell);
						
						// Fecha de movimiento del empleado
						cell=new PdfPCell(new Phrase(""+st[8],fuente));
						cell.setBorderWidthLeft(0.5f);
						cell.setBorderWidthTop(0.5f);
						cell.setBorderWidthRight(0.5f);
						cell.setBorderWidthBottom(1f);
						cell.setMinimumHeight(17f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table1.addCell(cell);
						
						// Puesto
						cell=new PdfPCell(new Phrase(""+st[9],fuente));
						cell.setBorderWidthLeft(0.5f);
						cell.setBorderWidthTop(0.5f);
						cell.setBorderWidthRight(0.5f);
						cell.setBorderWidthBottom(1f);
						cell.setMinimumHeight(17f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table1.addCell(cell);
						
						// Número de plaza
						cell=new PdfPCell(new Phrase(""+st[10],fuente));
						cell.setBorderWidthLeft(0.5f);
						cell.setBorderWidthTop(0.5f);
						cell.setBorderWidthRight(1f);
						cell.setBorderWidthBottom(1f);
						cell.setMinimumHeight(17f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table1.addCell(cell);
						
						anteriorId=""+st[4];
					}
				}
			} else {
				//Aqui comienza la tabla para puestos diferentes a consejero
				table2.setWidths(new float[]{0.14f, 0.20f, 0.25f, 0.35f, 0.12f});
			/*	
				System.out.println("<<<<<< ID: " + st[4]);
				System.out.println("<<<<<< Anterior ID: " + anteriorId);
				System.out.println("<<<<<< Movimiento: " + st[7]);
				System.out.println("<<<<<< Fecha: " + st[8]);
				System.out.println("<<<<<< Puesto: " + st[9]);
				System.out.println("<<<<<< Plaza: " + st[10]);
				System.out.println("------------FIN-------------------");
				*/
				
				
				
				if(!anteriorId.equals(""+st[4])) { //comprueba que no sea el mismo empleado que en la corrida pasada
					//renglon en blanco
				
					contador = 0;
					parrafo=new Paragraph(new Phrase(" ", fuente2));
					parrafo.setAlignment(Element.ALIGN_CENTER);
					cell = new PdfPCell(parrafo);
					cell.setBorderWidthLeft(0f);
					cell.setBorderWidthTop(0f);
					cell.setBorderWidthRight(0f);
					cell.setBorderWidthBottom(0.5f);
					cell.setColspan(5);											
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table2.addCell(cell);	
					
					/****Pasa los parametros para buscar el contrato Pablo****/
					idEmpleadoContrato=st[4].toString();
					numPlazaContrato=st[10].toString();
					
					parrafo=new Paragraph(new Phrase("Nombre del empleado: ", fuente2));
					parrafo.add(new Phrase(""+st[1]+Chunk.NEWLINE, fuente));
					parrafo.add(new Phrase("                 CURP: ", fuente2));
					parrafo.add(new Phrase(""+st[2], fuente));
					parrafo.add(new Phrase("             RFC: ", fuente2));
					parrafo.add(new Phrase(""+st[3], fuente));
					parrafo.add(new Phrase("              Núm Empleado: ", fuente2));
					parrafo.add(new Phrase(""+st[4], fuente));
					parrafo.add(new Phrase("            Núm Contrato: ", fuente2));
					parrafo.add(new Phrase(""+obtenerContrato(idEmpleadoContrato, numPlazaContrato), fuente));
					parrafo.setAlignment(Element.ALIGN_CENTER);
					
					cell = new PdfPCell(parrafo);
					cell.setBorderWidthLeft(1f);
					cell.setBorderWidthTop(1f);
					cell.setBorderWidthRight(1f);
					cell.setBorderWidthBottom(0.5f);
					cell.setBackgroundColor(color);
					cell.setColspan(5);											
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);				
					
					// Consecutivo del movimiento
					cell=new PdfPCell(new Phrase("No.",fuente2));//renglon 2
					cell.setBorderWidthLeft(1f);
					cell.setBorderWidthTop(0.5f);
					cell.setBorderWidthRight(0.5f);
					cell.setBorderWidthBottom(0.5f);
					cell.setBackgroundColor(color);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
					
					// Número de contrato
//					cell=new PdfPCell(new Phrase("Núm. de Contrato",fuente2));
//					cell.setBorderWidthLeft(0.5f);
//					cell.setBorderWidthTop(0.5f);
//					cell.setBorderWidthRight(0.5f);
//					cell.setBorderWidthBottom(0.5f);
//					cell.setBackgroundColor(color);
//					cell.setMinimumHeight(17f);
//					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table2.addCell(cell);
					
					// Tipo de movimiento (ALTA, BAJA, REINGRESO O MODIFICACIÓN)
					cell=new PdfPCell(new Phrase("Movimiento",fuente2));
					cell.setBorderWidthLeft(0.5f);
					cell.setBorderWidthTop(0.5f);
					cell.setBorderWidthRight(0.5f);
					cell.setBorderWidthBottom(0.5f);
					cell.setBackgroundColor(color);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
					
					// Fecha de movimiento del empleado
					cell=new PdfPCell(new Phrase("Fecha",fuente2));
					cell.setBorderWidthLeft(0.5f);
					cell.setBorderWidthTop(0.5f);
					cell.setBorderWidthRight(0.5f);
					cell.setBorderWidthBottom(0.5f);
					cell.setBackgroundColor(color);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
	
					// Puesto
					cell=new PdfPCell(new Phrase("Puesto",fuente2));
					cell.setBorderWidthLeft(0.5f);
					cell.setBorderWidthTop(0.5f);
					cell.setBorderWidthRight(0.5f);
					cell.setBorderWidthBottom(0.5f);
					cell.setBackgroundColor(color);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
					
					// Número de plaza
					cell=new PdfPCell(new Phrase("Plaza",fuente2));
					cell.setBorderWidthLeft(0.5f);
					cell.setBorderWidthTop(0.5f);
					cell.setBorderWidthRight(1f);
					cell.setBorderWidthBottom(0.5f);
					cell.setBackgroundColor(color);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
				}
				
				rfcC=""+st[11];
				nombreC=""+st[12];
				nombreC=nombreC.trim();
				cambio=""+st[13];
				//si es cambio y rfc o nombre no son vacios pinta el renglon
				if(cambio.equals("s")&&((!rfcC.equals("")&&!rfcC.equals("null"))||(!nombreC.equals(" ")&&!nombreC.equals("")&&!nombreC.equals("null"))))
				{
					contador = contador +1;
					
					cell=new PdfPCell(new Phrase(contador.toString(),fuente));
					cell.setBorderWidthLeft(1f);
					cell.setBorderWidthTop(0.5f);
					cell.setBorderWidthRight(0.5f);
					cell.setBorderWidthBottom(1f);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table2.addCell(cell);
					
					//contrato
//					suma=""+st[6];
//					if(suma.equals("null"))
//					{
//						suma="";
//					}
//					cell=new PdfPCell(new Phrase(""+suma,fuente));
//					cell.setBorderWidthLeft(0.5f);
//					cell.setBorderWidthTop(0.5f);
//					cell.setBorderWidthRight(0.5f);
//					cell.setBorderWidthBottom(1f);
//					cell.setMinimumHeight(17f);
//					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//					table2.addCell(cell);
					
					// Tipo de movimiento (ALTA, BAJA, REINGRESO O MODIFICACIÓN)
					cell=new PdfPCell(new Phrase(""+st[7],fuente));
					cell.setBorderWidthLeft(0.5f);
					cell.setBorderWidthTop(0.5f);
					cell.setBorderWidthRight(0.5f);
					cell.setBorderWidthBottom(1f);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table2.addCell(cell);
					
					// Fecha de movimiento del empleado
					cell=new PdfPCell(new Phrase(""+st[8],fuente));
					cell.setBorderWidthLeft(0.5f);
					cell.setBorderWidthTop(0.5f);
					cell.setBorderWidthRight(0.5f);
					cell.setBorderWidthBottom(1f);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table2.addCell(cell);
					
					// Puesto
					cell=new PdfPCell(new Phrase(""+st[9],fuente));
					cell.setBorderWidthLeft(0.5f);
					cell.setBorderWidthTop(0.5f);
					cell.setBorderWidthRight(0.5f);
					cell.setBorderWidthBottom(1f);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table2.addCell(cell);
					
					// Número de plaza
					cell=new PdfPCell(new Phrase(""+st[10],fuente));
					cell.setBorderWidthLeft(0.5f);
					cell.setBorderWidthTop(0.5f);
					cell.setBorderWidthRight(1f);
					cell.setBorderWidthBottom(1f);
					cell.setMinimumHeight(17f);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table2.addCell(cell);
					
					anteriorId=""+st[4];
				} else {//no escribe el cambio
					if(cambio.equals("null")) {//si no es renglon de cambio no valida nada y lo presenta
						contador = contador +1;
						cell=new PdfPCell(new Phrase(contador.toString(),fuente));
						cell.setBorderWidthLeft(1f);
						cell.setBorderWidthTop(0.5f);
						cell.setBorderWidthRight(0.5f);
						cell.setBorderWidthBottom(1f);
						cell.setMinimumHeight(17f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table2.addCell(cell);
						
						//contrato
//						suma=""+st[6];
//						if(suma.equals("null"))
//						{
//							suma="";
//						}
//						cell=new PdfPCell(new Phrase(""+suma,fuente));
//						cell.setBorderWidthLeft(0.5f);
//						cell.setBorderWidthTop(0.5f);
//						cell.setBorderWidthRight(0.5f);
//						cell.setBorderWidthBottom(1f);
//						cell.setMinimumHeight(17f);
//						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//						table2.addCell(cell);
						
						// Tipo de movimiento (ALTA, BAJA, REINGRESO O MODIFICACIÓN)
						cell=new PdfPCell(new Phrase(""+st[7],fuente));
						cell.setBorderWidthLeft(0.5f);
						cell.setBorderWidthTop(0.5f);
						cell.setBorderWidthRight(0.5f);
						cell.setBorderWidthBottom(1f);
						cell.setMinimumHeight(17f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table2.addCell(cell);
						
						// Fecha de movimiento del empleado
						cell=new PdfPCell(new Phrase(""+st[8],fuente));
						cell.setBorderWidthLeft(0.5f);
						cell.setBorderWidthTop(0.5f);
						cell.setBorderWidthRight(0.5f);
						cell.setBorderWidthBottom(1f);
						cell.setMinimumHeight(17f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table2.addCell(cell);
						
						// Puesto
						cell=new PdfPCell(new Phrase(""+st[9],fuente));
						cell.setBorderWidthLeft(0.5f);
						cell.setBorderWidthTop(0.5f);
						cell.setBorderWidthRight(0.5f);
						cell.setBorderWidthBottom(1f);
						cell.setMinimumHeight(17f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table2.addCell(cell);
						
						// Puesto
						cell=new PdfPCell(new Phrase(""+st[10],fuente));
						cell.setBorderWidthLeft(0.5f);
						cell.setBorderWidthTop(0.5f);
						cell.setBorderWidthRight(1f);
						cell.setBorderWidthBottom(1f);
						cell.setMinimumHeight(17f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table2.addCell(cell);
						
						anteriorId=""+st[4];
					}
				}
			}
		}	
		
		cell1 = new PdfPCell(table1);
		cell1.setBorderWidthLeft(0f);
		cell1.setBorderWidthTop(0f);
		cell1.setBorderWidthRight(0f);
		cell1.setBorderWidthBottom(0.0f);
		cell1.setColspan(5);											
		cell1.setMinimumHeight(17f);
		cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table3.addCell(cell1);
		
		cell1 = new PdfPCell(table2);
		cell1.setBorderWidthLeft(0f);
		cell1.setBorderWidthTop(0f);
		cell1.setBorderWidthRight(0f);
		cell1.setBorderWidthBottom(0.0f);
		cell1.setColspan(5);											
		cell1.setMinimumHeight(17f);
		cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table3.addCell(cell1);
		
		if(anteriorId.equals(""))//no hay datos pero imprime un reglon
		{
			parrafo=new Paragraph(new Phrase("Nombre del empleado:"+Chunk.NEWLINE+"CURP:    RFC:    Núm Empleado: ",fuente2));
			parrafo.setAlignment(Element.ALIGN_CENTER);
			cell = new PdfPCell(parrafo);		//renglon 1
			cell.setBorderWidthLeft(1f);
			cell.setBorderWidthTop(1f);
			cell.setBorderWidthRight(1f);
			cell.setBorderWidthBottom(0.5f);
			cell.setBackgroundColor(color);
			cell.setColspan(5);											
			cell.setMinimumHeight(17f);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);				
			
			cell=new PdfPCell(new Phrase("No.",fuente2));//renglon 2
			cell.setBorderWidthLeft(1f);
			cell.setBorderWidthTop(0.5f);
			cell.setBorderWidthRight(0.5f);
			cell.setBorderWidthBottom(0.5f);
			cell.setBackgroundColor(color);
			cell.setMinimumHeight(17f);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
//			cell=new PdfPCell(new Phrase("Núm. de Contrato",fuente2));
//			cell.setBorderWidthLeft(0.5f);
//			cell.setBorderWidthTop(0.5f);
//			cell.setBorderWidthRight(0.5f);
//			cell.setBorderWidthBottom(0.5f);
//			cell.setBackgroundColor(color);
//			cell.setMinimumHeight(17f);
//			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			table.addCell(cell);
			
			cell=new PdfPCell(new Phrase("Movimiento",fuente2));
			cell.setBorderWidthLeft(0.5f);
			cell.setBorderWidthTop(0.5f);
			cell.setBorderWidthRight(0.5f);
			cell.setBorderWidthBottom(0.5f);
			cell.setBackgroundColor(color);
			cell.setMinimumHeight(17f);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell=new PdfPCell(new Phrase("Fecha",fuente2));
			cell.setBorderWidthLeft(0.5f);
			cell.setBorderWidthTop(0.5f);
			cell.setBorderWidthRight(0.5f);
			cell.setBorderWidthBottom(0.5f);
			cell.setBackgroundColor(color);
			cell.setMinimumHeight(17f);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell=new PdfPCell(new Phrase("Puesto",fuente2));
			cell.setBorderWidthLeft(0.5f);
			cell.setBorderWidthTop(0.5f);
			cell.setBorderWidthRight(0.5f);
			cell.setBorderWidthBottom(0.5f);
			cell.setBackgroundColor(color);
			cell.setMinimumHeight(17f);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell=new PdfPCell(new Phrase("Plaza",fuente2));
			cell.setBorderWidthLeft(0.5f);
			cell.setBorderWidthTop(0.5f);
			cell.setBorderWidthRight(1f);
			cell.setBorderWidthBottom(0.5f);
			cell.setBackgroundColor(color);
			cell.setMinimumHeight(17f);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell=new PdfPCell(new Phrase("",fuente));
			cell.setBorderWidthLeft(1f);
			cell.setBorderWidthTop(0.5f);
			cell.setBorderWidthRight(0.5f);
			cell.setBorderWidthBottom(1f);
			cell.setMinimumHeight(17f);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
//			cell=new PdfPCell(new Phrase("",fuente));
//			cell.setBorderWidthLeft(0.5f);
//			cell.setBorderWidthTop(0.5f);
//			cell.setBorderWidthRight(0.5f);
//			cell.setBorderWidthBottom(1f);
//			cell.setMinimumHeight(17f);
//			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			table.addCell(cell);
			
			cell=new PdfPCell(new Phrase("",fuente));
			cell.setBorderWidthLeft(0.5f);
			cell.setBorderWidthTop(0.5f);
			cell.setBorderWidthRight(0.5f);
			cell.setBorderWidthBottom(1f);
			cell.setMinimumHeight(17f);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell=new PdfPCell(new Phrase("",fuente));
			cell.setBorderWidthLeft(0.5f);
			cell.setBorderWidthTop(0.5f);
			cell.setBorderWidthRight(0.5f);
			cell.setBorderWidthBottom(1f);
			cell.setMinimumHeight(17f);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell=new PdfPCell(new Phrase("",fuente));
			cell.setBorderWidthLeft(0.5f);
			cell.setBorderWidthTop(0.5f);
			cell.setBorderWidthRight(0.5f);
			cell.setBorderWidthBottom(1f);
			cell.setMinimumHeight(17f);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			
			cell=new PdfPCell(new Phrase("",fuente));
			cell.setBorderWidthLeft(0.5f);
			cell.setBorderWidthTop(0.5f);
			cell.setBorderWidthRight(1f);
			cell.setBorderWidthBottom(1f);
			cell.setMinimumHeight(17f);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
		}		
		table.setHeaderRows(2);	
		document.add(table);
		document.add(table3);
		document.close();	
		
		File tempFileFinal=File.createTempFile("HistoricoMovimientosE.pdf", ".tmp");	//creamos otro tempfile
		PdfReader lector = new PdfReader(baos.toByteArray());				//le pasamos al reader el primer pdf creado
		
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
			//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
			canvas.setFontAndSize(bf, 7);
			canvas.setTextMatrix(document.getPageSize().getWidth()-(document.getPageSize().getWidth()/2f)-((regla.length()*4.2f)/2f), document.getPageSize().getHeight()-35f);
			canvas.showText(regla);
			canvas.endText(); 
		
			
			regla=""+this.dto.getUnidadOrganizacional();//
			canvas.beginText();						
			//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
			canvas.setFontAndSize(bf, 7);
			canvas.setTextMatrix(document.getPageSize().getWidth()-(document.getPageSize().getWidth()/2f)-((regla.length()*4.2f)/2f), document.getPageSize().getHeight()-45f);
			canvas.showText(regla);
			canvas.endText();
			
			
			if(idEstado.equals("0")&&idDistrito.equals("0"))//estado y distrito valen cero
			{
				canvas.beginText();		//primer renglon				
				//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
				canvas.setFontAndSize(bf, 7);
				canvas.setTextMatrix((document.getPageSize().getWidth()-(document.getPageSize().getWidth()/2f))+192f, document.getPageSize().getHeight()-55f);
				canvas.showText("Fecha:  ");
				canvas.endText();
				
				regla="OFICINAS CENTRALES";
				canvas.beginText();						
				//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
				canvas.setFontAndSize(bf, 7);
				canvas.setTextMatrix(document.getPageSize().getWidth()-(document.getPageSize().getWidth()/2f)-((regla.length()*4.2f)/2f), document.getPageSize().getHeight()-55f);
				canvas.showText(regla);
				canvas.endText();

				canvas.beginText();		//segundo renglon				
				//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
				canvas.setFontAndSize(bf, 7);
				canvas.setTextMatrix((document.getPageSize().getWidth()-(document.getPageSize().getWidth()/2f))+192f, document.getPageSize().getHeight()-65f);
				canvas.showText(fecha+" "+hora+" hrs.");
				canvas.endText();	
			}else
			{	
				if(idDistrito.equals("0"))//junta local
				{
					canvas.beginText();		//primer renglon				
					//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
					canvas.setFontAndSize(bf, 7);
					canvas.setTextMatrix((document.getPageSize().getWidth()-(document.getPageSize().getWidth()/2f))-90f, document.getPageSize().getHeight()-55f);
					canvas.showText("Entidad Federativa:                                                                                                          Fecha:                ");
					canvas.endText();
				
					canvas.beginText();		//segundo renglon				
					//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
					canvas.setFontAndSize(bf, 7);
					canvas.setTextMatrix((document.getPageSize().getWidth()-(document.getPageSize().getWidth()/2f))-90f, document.getPageSize().getHeight()-65f);
					canvas.showText(this.dto.getIdEstado()+"-"+this.dto.getDescEntidad());
					canvas.endText();
					
					canvas.beginText();						
					//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
					canvas.setFontAndSize(bf, 7);
					canvas.setTextMatrix((document.getPageSize().getWidth()-(document.getPageSize().getWidth()/2f))+59f, document.getPageSize().getHeight()-65f);
					canvas.showText("JUNTA LOCAL");
					canvas.endText();
					
				}else//junta distrital
				{
					canvas.beginText();		//primer renglon				
					//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
					canvas.setFontAndSize(bf, 7);
					canvas.setTextMatrix((document.getPageSize().getWidth()-(document.getPageSize().getWidth()/2f))-90f, document.getPageSize().getHeight()-55f);
					canvas.showText("Entidad Federativa:                                            Distrito:                                                    Fecha:                ");
					canvas.endText();
				
					canvas.beginText();		//segundo renglon				
					//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
					canvas.setFontAndSize(bf, 7);
					canvas.setTextMatrix((document.getPageSize().getWidth()-(document.getPageSize().getWidth()/2f))-90f, document.getPageSize().getHeight()-65f);
					canvas.showText(this.dto.getIdEstado()+"-"+this.dto.getDescEntidad());
					canvas.endText();
					
					String distritoSesion=""+this.dto.getDescDistrito();
					if(distritoSesion.equals("null")||distritoSesion==null||distritoSesion.equals(""))
					{
						canvas.beginText();						
						//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
						canvas.setFontAndSize(bf, 7);
						canvas.setTextMatrix((document.getPageSize().getWidth()-(document.getPageSize().getWidth()/2f))+59f, document.getPageSize().getHeight()-65f);
						canvas.showText("0");
						canvas.endText();
					}else
					{
						canvas.beginText();						
						//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
						canvas.setFontAndSize(bf, 7);
						canvas.setTextMatrix((document.getPageSize().getWidth()-(document.getPageSize().getWidth()/2f))+60f, document.getPageSize().getHeight()-65f);
						canvas.showText(this.dto.getIdDistrito()+"-"+this.dto.getDescDistrito());
						canvas.endText();
					}
				}
				
				canvas.beginText();						
				//canvas.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false), 8);
				canvas.setFontAndSize(bf, 7);
				canvas.setTextMatrix((document.getPageSize().getWidth()-(document.getPageSize().getWidth()/2f))+192f, document.getPageSize().getHeight()-65f);
				canvas.showText(fecha+" "+hora+" hrs.");
				canvas.endText();
			}
			
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
		logger.debug("----------------------relacion de cheques de nomina---------------");
		return tempFileFinal;
	}

	public String getDoubleConFormato(String numeroAFormatear) 
	{
	    String decimales = "";
	    String entero = "";
	    String[] temp;
	    temp=numeroAFormatear.split("\\.");
	    boolean negativo = false;
	    int numeroDeComas = 0;
	  
	    entero = temp[0];
	    if(temp.length>1)
	    {
	    	if(temp[1].length()>2)
	    	{
	    		decimales = temp[1].substring(0, 2);
	    	}else
	    	{
	    		decimales = temp[1];
	    	}
	    }else
	    {
	    	decimales = "0";
	    }
	    
	   
	    if (decimales.length() <2)
	        decimales = decimales + "0";
	  
	    if (entero.contains("-")) {
	        negativo = true;
	        entero = entero.substring(1);
	    }
	    numeroDeComas = (entero.length() -1) / 3;
	   
	    for (int a = 0; a < numeroDeComas; a++) {
	        entero = entero.substring(0, entero.length() - (3 * (a + 1)) - a) + "," + entero.substring(entero.length() - (3 * (a + 1)) - a);
	    }
	   
	    if (negativo)
	        entero = "-" + entero;
	   
	    return entero + "." + decimales;
	}
	


	public String obtenerContrato(String idEmpleadoContrato, String numPlazaContrato){
		String numContrato="";
		this.dto.setIdEmpleadoContrato(idEmpleadoContrato);
		this.dto.setNumPlazaContrato(numPlazaContrato);
		
		try {
			logger.error("----------------------Entro a obtener Contrato Historico de Movimientos---------------");
			if (idEmpleadoContrato.equals("")||numPlazaContrato.equals("")) {
				numContrato="";
			
			} else {	
				DAOHistoricoMovimientos dao = new DAOHistoricoMovimientos(dto);
				dao.obtieneContrato(idEmpleadoContrato, numPlazaContrato);
				
				List<?> listaC = dto.getListaContrato();
				
				if (listaC.isEmpty()) {
					numContrato="";
				}
				else {
					Iterator<?> cto = listaC.iterator();
					while(cto.hasNext())
					{	
						Object[] st3=(Object[])cto.next();
						numContrato ="" + (String) st3[0];
					}
				}								
			}
			logger.debug("----------------------CONTRATO --------------- " + numContrato);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return numContrato;
		
	}
}