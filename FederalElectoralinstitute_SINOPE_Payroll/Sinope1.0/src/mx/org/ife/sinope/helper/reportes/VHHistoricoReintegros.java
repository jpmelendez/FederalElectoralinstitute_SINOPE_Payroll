package mx.org.ife.sinope.helper.reportes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.text.DecimalFormat;
import java.util.Locale;

import mx.org.ife.sinope.dto.reportes.DTOHistoricoReintegros;
import mx.org.ife.sinope.util.Converter;

public class VHHistoricoReintegros {


	/**
	 * 
	 * @param Metodo para formatear monto
	 * @return
	 */
	public String formato_miles(String valor){
		double val = Double.parseDouble(valor);
		Locale.setDefault(Locale.US);
		DecimalFormat _num  = new DecimalFormat("###,##0.00");
		return _num.format(val);
	}

	/**
	 * Metodo que se utiliza para setear unos parametros
	 * @return
	 */
	public DTOHistoricoReintegros getDTO(Object[] objeto){
		DTOHistoricoReintegros dto = new DTOHistoricoReintegros();
		dto.setUr(objeto[0]!= null ? objeto[0].toString():"");
		BigDecimal tmp = new BigDecimal(objeto[1]!= null ? objeto[1].toString():"0");
		dto.setIdFolio(tmp.intValue());
		dto.setFolio(objeto[2]!= null ? objeto[2].toString():"");
		//dto.setTipoNomina(objeto[3]!= null ? objeto[3].toString():"");
		dto.setTipoPago(objeto[3]!= null ? objeto[3].toString():"");
		dto.setNombreCompleto(objeto[4] != null ? objeto[4].toString():"");
		String tmpImportemiles = this.formato_miles(String.valueOf(objeto[5]));
		dto.setImporteString(tmpImportemiles != null ? tmpImportemiles.toString():""); 
		dto.setImporte(Double.valueOf(objeto[5] != null ? objeto[5].toString():"0"));
		dto.setQuincenaPago(objeto[6]!= null ? objeto[6].toString():"");
		dto.setFechaDeposito(objeto[7]!= null ? objeto[7].toString():"");

		return dto;
	}

	public DTOHistoricoReintegros getDTOPDF(Object[] objeto){
		DTOHistoricoReintegros dto = new DTOHistoricoReintegros();
		dto.setPdfFolioEmpleado(objeto[0]!= null ? objeto[0].toString():"");
		dto.setPdfIdFolio(objeto[1]!= null ? objeto[1].toString():"");
		dto.setPdfFechaDeposito(objeto[2]!= null ? objeto[2].toString():"");
		dto.setPdfNumFolio(objeto[3]!= null ? objeto[3].toString():"");
		dto.setPdfNombreUnidad(objeto[4]!= null ? objeto[4].toString():"");
		dto.setPdfClaveUnidad(objeto[5]!= null ? objeto[5].toString():"");
		String tmpImportemiles = this.formato_miles(String.valueOf(objeto[6]));
		dto.setPdfImporteStr(tmpImportemiles!= null ? tmpImportemiles.toString():"");
		dto.setPdfImporte(Double.valueOf(objeto[6]!= null ? objeto[6].toString():""));
		dto.setPdfRFC(objeto[7]!= null ? objeto[7].toString():"");
		dto.setPdfNombre(objeto[8]!= null ? objeto[8].toString():"");
		dto.setPdfQnaPago(objeto[9]!= null ? objeto[9].toString():"");
		dto.setPdfRadicacion(objeto[10]!= null ? objeto[10].toString():"");
		dto.setPdfNumCuenta(objeto[11]!= null ? objeto[11].toString():"");
		dto.setPdfFichaDep(objeto[12]!= null ? objeto[12].toString():"");
		dto.setPdfTipoPago(objeto[13]!= null ? objeto[13].toString():"");
		//dto.setPdfTipoNomina(objeto[14]!= null ? objeto[14].toString():"");
		String FormaPago = "Cheque";
		objeto[14] = FormaPago;
		dto.setPdfFormaPago(objeto[14]!= null ? objeto[14].toString():"");
		dto.setPdfFolioReintegro(objeto[15]!= null ? objeto[15].toString():"");

		Converter convertidor = new Converter();
		String ImporteLetra  = convertidor.getStringOfNumber(dto.getPdfImporte());	
		dto.setPdfImporteLetra(ImporteLetra != null ? ImporteLetra.toString():"");
		
		/*generamos el id estado*/
		dto.setIdEstado(objeto[16]!= null ? objeto[16].toString():"");
		dto.setIdDistrito(objeto[17]!= null ? objeto[17].toString():"");
		dto.setIdArea(objeto[18]!= null ? objeto[18].toString():"");
		

		return dto;
	}

	public DTOHistoricoReintegros getDTOFirmas (Object[] objeto)throws Exception{
		DTOHistoricoReintegros dto = new DTOHistoricoReintegros();

		dto.setFirmaIDResponsable(objeto[0]!= null ? objeto[0].toString():"");
		dto.setFirmaIDDocumento(objeto[1]!= null ? objeto[1].toString():"");
		dto.setFirmaDocumentoFirma(objeto[2]!= null ? objeto[2].toString():"");
		dto.setFirmaPosicion(objeto[3]!= null ? objeto[3].toString():"");
		dto.setFirmaRespNombre(objeto[4]!= null ? objeto[4].toString():"");
		dto.setFirmaCargo(objeto[5]!= null ? objeto[5].toString():"");
		dto.setFirmaUResp(objeto[6]!= null ? objeto[6].toString():"");
		//dto.setFirmaImagen(null);
		/*Transformacion blob*/
		if(objeto[7]!= null){
			String strImg = obtenerImagenFirma((Blob) objeto[7]);
			dto.setFirmaImagenString(strImg);
		}else{
			String strImg = null;
			dto.setFirmaImagenString(strImg);
		}
		dto.setFirmaIDEstado(objeto[8]!= null ? objeto[8].toString():"");
		dto.setFirmaIDDistrito(objeto[9]!= null ? objeto[9].toString():"");

		return dto;
	}
/*
	public byte[] getByteArrayFromBlob(Blob blob){
		try {
			int blobLength = (int) blob.length();  
			byte[] blobAsBytes = blob.getBytes(1, blobLength);
			blob.free();

			return blobAsBytes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	*/
	
	
	public String obtenerImagenFirma(Blob blob) 
	{
		
		File tempFile = null;
		FileOutputStream fos;
		InputStream inStream;
		int size, length = -1;
		byte[] buffer;
		
		Blob blob1_1 = blob;
		if (blob1_1 != null) 
		{
			
			try 
			{
				
				tempFile = null; 
				tempFile = File.createTempFile("firma", ".jpg" );
				fos = new FileOutputStream(tempFile);

				inStream = blob1_1.getBinaryStream();
				size = (int)blob1_1.length();
				buffer = new byte[size];
				length = -1;
				while ((length = inStream.read(buffer)) != -1)
				{
					fos.write(buffer, 0, length);                
				}    
				fos.close();
				inStream.close();
				tempFile.deleteOnExit();
				
				return tempFile.getAbsolutePath();
			} catch (Exception e) {
				
				return null;
			}
			
		}else
		{
			
			return null;
		}
	}   
}

