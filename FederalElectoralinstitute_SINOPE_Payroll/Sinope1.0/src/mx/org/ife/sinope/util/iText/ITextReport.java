package mx.org.ife.sinope.util.iText;

import java.awt.Color;
import java.awt.Point;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;

/**
*
* @author Arcenio Brito Hernández
*
* @since Agosto 2011
*
* @copyright I.F.E.
*/
public class ITextReport {
	public final static int 	UNDEFINED_POS_INT 	= -1;
	public final static String 	PAPER_NAME_LETTER 	= "Carta";
	public final static String 	PAPER_NAME_LEGAL 	= "Oficio";	
	
	private Document document;
	private PdfWriter pdfWriter;
	private HtmlWriter htmlWriter;
	private PdfContentByte canvas;	
	private Calendar calendar = Calendar.getInstance();
	
	/**
	 * setter
	 * @param pdfWriter
	 */
	public void setPdfWriter(PdfWriter pdfWriter) { this.pdfWriter = pdfWriter; }
	/**
	 * getter
	 * @return
	 */
	public PdfWriter getPdfWriter() { return pdfWriter; }	
	
	/**
	 * setter
	 * @param htmlWriter
	 */
	public void setHtmlWriter(HtmlWriter htmlWriter) { this.htmlWriter = htmlWriter; }
	/**
	 * getter
	 * @return
	 */
	public HtmlWriter getHtmlWriter() { return htmlWriter; }	
	
	/**
	 * setter
	 * @param canvas
	 */
	public void setCanvas(PdfContentByte canvas) { this.canvas = canvas; }
	/**
	 * getter
	 * @return
	 */
	public PdfContentByte getCanvas() { return canvas; }
	/**
	 * setter
	 * @param document
	 */
	public void setDocument(Document document) { this.document = document; }
	/**
	 * getter
	 * @return
	 */
	public Document getDocument() { return document; }

	/**
	 * regresa un objeto calendario
	 * @return
	 */
	public Calendar getCalendar() { return calendar; }	
	
	/**
	 * Crea un documento PDF
	 * @param pageSize		-	tamaño de página
	 * @param outputStream	-	stream de salida
	 * @throws Exception	-	exception
	 */
	public void createPdfDocument(Rectangle pageSize, OutputStream outputStream) throws Exception {
		this.createPdfDocument (pageSize, outputStream, 
			UNDEFINED_POS_INT, UNDEFINED_POS_INT, UNDEFINED_POS_INT, UNDEFINED_POS_INT);
	}
	
	/**
	 * Crea un documento PDF
	 * @param pageSize		-	tamaño de página
	 * @param outputStream	-	stream de salida
	 * @throws Exception	-	exception
	 */
	public void createPdfDocument(Rectangle pageSize, OutputStream outputStream, 
		float marginLeft, float marginRight, float marginTop, float marginBottom) throws Exception {
		
		if( marginLeft != UNDEFINED_POS_INT &&  marginRight != UNDEFINED_POS_INT && 
			marginTop != UNDEFINED_POS_INT && marginBottom != UNDEFINED_POS_INT)
			this.setDocument(new Document(pageSize, marginLeft, marginRight, marginTop, marginBottom));
		else
			this.setDocument(new Document(pageSize));
		
		this.setPdfWriter(PdfWriter.getInstance(this.getDocument(), outputStream));
		this.getPdfWriter().setPdfVersion(PdfWriter.VERSION_1_7);
		this.getDocument().open();
		this.setCanvas(this.getPdfWriter().getDirectContent());		
	}
	
	/**
	 * Crea un documento HTML
	 * @param pageSize		-	tamaño de página
	 * @param outputStream	-	stream de salida
	 * @throws Exception	-	exception
	 */
	public void createHtmlDocument(Rectangle pageSize, OutputStream outputStream) throws Exception {
		this.setDocument(new Document(pageSize));
		this.setHtmlWriter(HtmlWriter.getInstance(this.getDocument(), outputStream));
		this.getDocument().open();
	}
	
	/**
	 * dibuja un rectangulo, donde (0,0) es la ezquina superior izquierda
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void drawRectangle(int x, int y, int w, int h) {		
		drawLine(x, y, x, y + h);
		drawLine(x, y, x + w, y);
		drawLine(x, y + h, x + w, y + h);
		drawLine(x + w, y + h, x + w, y);	
	}

	/**
	 * dibuja una imagen, donde (0,0) es la ezquina superior izquierda
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2	 
	 * @param imagePath	-	path de la imagen
	 * @param absoluteSize	-	tamaño absoluto de la imagen
	 * @throws DocumentException 
	 */
	public void drawImage(int x, int y, int w, int h, String imagePath, boolean absoluteSize) throws Exception {
		Image image = Image.getInstance(imagePath);
		this.drawByteImage(x, y, w, h, image, absoluteSize);
	}		
	
	public void drawByteImage(int x, int y, int w, int h, byte[] imageByteArray, boolean absoluteSize) throws Exception {
		if (imageByteArray == null ) return;
		Image image = Image.getInstance(imageByteArray);
		this.drawByteImage(x, y, w, h, image, absoluteSize);
	}
	
	public void drawByteImage(int x, int y, int w, int h, Image image, boolean absoluteSize) throws Exception {
		image.setAbsolutePosition(x, getBy(y));
		if( absoluteSize ) {
			image.scaleAbsoluteWidth(w);
			image.scaleAbsoluteHeight(h);
		}
		getCanvas().addImage(image);
	}	
	
	public Image getImageFromArrayByte(byte[] imageByteArray, int w, int h, boolean absoluteSize) throws Exception {
		Image result = null;
		if (imageByteArray != null ) { 
			result = Image.getInstance(imageByteArray);
			if( absoluteSize ) {
				result.scaleAbsoluteWidth(w);
				result.scaleAbsoluteHeight(h);
			}
		} 
		return result;
	}	
	
	/***
	 * dibuja un string, donde (0,0) es la ezquina superior izquierda
	 * @param x
	 * @param y
	 * @param text
	 * @param f1		-	font
	 * @param f2		-	font
	 * @throws Exception
	 */
	public void drawString2
		(int x, int y, String text1, String text2, Font f1, Font f2) throws Exception {
		
		drawString(new Point(x, getBy(y)), text1, f1);
		int dX = (int)f1.getBaseFont().getWidthPoint(text1, (int)f1.getSize());
		drawString(new Point(x + dX, getBy(y)), text2, f2);
	}
	
	/***
	 * dibuja un string, donde (0,0) es la ezquina superior izquierda
	 * @param x
	 * @param y
	 * @param text
	 * @param f		-	font
	 * @throws Exception
	 */
	public void drawString(int x, int y, String text, Font f) throws Exception {		
		drawString(new Point(x, getBy(y)), text, f);
	}
	
	/***
	 * dibuja un string multiple, donde (0,0) es la ezquina superior izquierda
	 * @param x
	 * @param y
	 * @param text
	 * @param font 
	 * @throws Exception
	 */
	public void drawAlignString(int x, int y, int w, int h, int align, int space, String text, Font font) throws Exception {
		ColumnText columnText = new ColumnText(getCanvas());		
		Phrase phrase = new Phrase(text, font);
		columnText.setSimpleColumn(phrase, x, getBy(y), x + w, getBy(y) - h, space, align);
		columnText.go();	
	}
	
	/**
	 * dibuja una linea, donde (0,0) es la ezquina superior izquierda
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void drawLine(int x1, int y1, int x2, int y2) {
		drawLine(new Point(x1, getBy(y2)), new Point(x2, getUy(y1)) );
	}
	
	public int getBy(int y2) { return (int)(getDocument().getPageSize().getHeight() - y2); }
	public int getUy(int y1) { return (int)(getDocument().getPageSize().getHeight() - y1); }
	public int getRx(int x1) { return (int)(getDocument().getPageSize().getWidth() - x1);}

	
	/**
	 * dibuja una linea, donde (0,0) es la ezquina inferior izquierda
	 * @param p1		-	punto 
	 * @param p2		-	punto
	 * @throws Exception	
	 */	
	public void drawLine(Point p1, Point p2) {
		getCanvas().moveTo(p1.x, p1.y);
		getCanvas().lineTo(p2.x, p2.y);		
		getCanvas().stroke();		
	}
	
	/**
	 * dibuja un string, donde (0,0) es la ezquina inferior izquierda
	 * @param p		-	punto 
	 * @param text	-	texto
	 * @param f		-	font
	 * @throws Exception	
	 */
	public void drawString(Point p, String text, Font f) throws Exception {                
        getCanvas().beginText();   
//        System.out.print("f.getBaseFont :"  + f.getBaseFont());        
        getCanvas().setFontAndSize(f.getBaseFont(), f.getSize());
        getCanvas().moveText(p.x, p.y);        
        getCanvas().showText(text);
        getCanvas().endText();
    }
	
	/**
	 * establece información de metadata en el Documento
	 * @param title
	 * @param author
	 * @param subject
	 */
	public void setDocumentInfo
		(String title, String author, String subject) {
		this.getDocument().addTitle(title);
		this.getDocument().addAuthor(author);
		this.getDocument().addSubject(subject);
		this.getDocument().addCreationDate();
	}
	
	/**
	 * establece información de metadata en el Documento
	 * @param title
	 * @param author
	 * @param subject
	 * @param creator
	 * @param keywords
	 */
	public void setDocumentInfo
		(String title, String author, String subject, String creator, String keywords) {
		this.setDocumentInfo(title, author, subject);
		this.getDocument().addCreator(creator);
		this.getDocument().addKeywords(keywords);
	}
	
	/**
	 * devuelve el ancho del area de trabajo
	 * @return un entero
	 */
	public int getWorkAreaWidth() {
		return (int) (document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin()); 
	}
	
	/**
	 * devuelve el alto del area de trabajo
	 * @return un entero
	 */
	public int getWorkAreaHeight() {
		return (int) (document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin());
	}	
	
	/**
	 * devuelve la coordenada derecha del rectángulo que describe el área de trabajo
	 * @return un entero
	 */
	public int getWorkAreaRightX() {
		return (int) (document.getPageSize().getWidth() - document.rightMargin());
	}
	
	/**
	 * devuelve la coordenada inferior del rectángulo que describe el área de trabajo
	 * @return un entero
	 */
	public int getWorkAreaBottomY() {
		return (int) (document.getPageSize().getHeight() - document.bottomMargin());
	}		
	/**
	 * regresa la fecha/hora actual como un string formateado
	 * @param format	-	formato
	 * @return un string
	 */
	public String getCurrentDate(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(this.calendar.getTime());		
	}	
	
	/**
	 * genera una objeto PdfPCell
	 * @param phrase
	 * @param horizontalAlignment
	 * @param backColor
	 * @return
	 * @throws Exception
	 */
	public PdfPCell newPdfPCell(Phrase phrase, int horizontalAlignment, Color backColor) throws Exception {		
		PdfPCell result = new PdfPCell(phrase);
		result.setHorizontalAlignment(horizontalAlignment);
		result.setBackgroundColor(backColor);		
		return result;
	}

	/**
	 * genera un objeto Cell 
	 * @param phrase
	 * @param horizontalAlignment
	 * @param backColor
	 * @return
	 * @throws Exception
	 */
	public Cell newHtmlCell(Phrase phrase, int horizontalAlignment, Color backColor) throws Exception {
		Cell result = new Cell(phrase);
		result.setHorizontalAlignment(horizontalAlignment);
		result.setBackgroundColor(backColor);		
		return result;
	}
	
	/**
	 * genera un objeto Phrase
	 * @param value
	 * @param font
	 * @return
	 */
	public Phrase getPhrase(String value, Font font) {
		Phrase result = new Phrase(value, font);
		return result;
	}
	
	/**
	 * genera un objeto Phrase
	 * @param value
	 * @param font
	 * @return
	 */
	public Phrase getPhrase(String value, Font font, int align) {
		Phrase result = new Phrase(value, font);
		return result;
	}

	
	/**
	 * genera un objeto Paragraph
	 * @param value
	 * @param font
	 * @param align
	 * @return
	 */
	public Paragraph getParagraph(String value, Font font, int align) {
		Paragraph result = new Paragraph(value, font);		
		result.setAlignment(align);
		return result;
	}	
	
	/**
	 * genera un objeto Paragraph
	 * @param value
	 * @param font
	 * @param align
	 * @param indent
	 * @return
	 */
	public Paragraph getParagraph(String value, Font font, int align, int indentationLeft) {
		Paragraph result = new Paragraph(value, font);		
		result.setAlignment(align);
		result.setIndentationLeft(indentationLeft);
		return result;
	}	
	
	/**
	 * convierte milimetros a puntos
	 * @param mm	-	milimetros
	 * @return	correspondencia en puntos
	 */
	public static float mmToPt(int mm) {
		return (float)(mm * (254/72)); 
	}	
}


