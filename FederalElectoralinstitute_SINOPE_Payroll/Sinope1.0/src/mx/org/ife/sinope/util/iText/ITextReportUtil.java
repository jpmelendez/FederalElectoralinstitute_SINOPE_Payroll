package mx.org.ife.sinope.util.iText;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;

/**
*
* @author Arcenio Brito Hernández
*
* @since Agosto 2011
*
* @copyright I.F.E.
*/
public class ITextReportUtil {
	private static BaseFont bFHelvetica = null;
	private static BaseFont bFHelveticaBold = null;
	
	/**
	 * Crea un objeto BaseFont
	 * @return un objeto
	 */
	public static BaseFont getBFHelvetica() {
		try {
			if( bFHelvetica == null) {
				bFHelvetica = BaseFont.
					createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			} 
		} catch (Exception ex) {
			ex.printStackTrace();
		} return bFHelvetica;
	}
	/**
	 * Crea un objeto BaseFont
	 * @return un objeto
	 */
	public static BaseFont getBFHelveticaBold() {
		try {
			if( bFHelveticaBold == null) {
				bFHelveticaBold = BaseFont.
					createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			} 
		} catch (Exception ex) {
			ex.printStackTrace();
		} return bFHelveticaBold;
	}	
	
	/**
	 * Crea una objeto Font
	 * @param family
	 * @param size
	 * @return un objeto Font
	 */
	public static Font createFont(String family, int size) {
		Font result = new Font();
		result.setFamily(family);
		result.setSize(size); 
		return result;
	}	
	
	/**
	 * hace persisnte el [stream] en un archivo
	 * @param fileName		-	nombre del archivo
	 * @param stream		-	stream del tipo [ByteArrayOutputStream]
	 * @throws Exception 	-	excepción
	 */
	public static void saveToPdfFile(String fileName, ByteArrayOutputStream stream) throws Exception {
		FileOutputStream fos = null ;
		try {
			fos = new FileOutputStream(fileName);
			fos.write(stream.toByteArray());
		} catch (Exception ex) {
			throw ex;
		} finally {
			if( fos != null) fos.close();
		}			
	}

}
