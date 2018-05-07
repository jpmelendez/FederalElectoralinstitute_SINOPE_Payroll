package mx.org.ife.sinope.reports;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import mx.org.ife.sinope.dto.reportes.DTOHistoricoMovimientos;
import mx.org.ife.sinope.util.GenericConstantsSinope;
//import mx.org.ife.sinope.reports.Object;
//import mx.org.ife.sinope.reports.String;
import mx.org.ife.sinope.util.Utils;

import org.apache.log4j.Logger;

public class RPTHistoricoMovimientosQHTML {

	private Logger logger = Logger.getLogger(RPTHistoricoMovimientosQHTML.class.getName());
	private DTOHistoricoMovimientos dto;
	public RPTHistoricoMovimientosQHTML(DTOHistoricoMovimientos dto) 
	{
		this.dto=dto;
	}

	
	public File Reporte()throws IOException
	{
		File tempFile = null;
		FileWriter fichero = null;
        PrintWriter pw = null;
		try {
			//String rutaImagen=System.getProperty("html.home.sinope")+"img\\ife_400.jpg";
			String rutaImagen="..\\html\\img\\ife_400.jpg";
			String tiponomina="";
			String contrato="";
			String idEstado=""+this.dto.getIdEstado();
			String idDistrito=""+this.dto.getIdDistrito();
			String idArea=""+this.dto.getIdArea();
			Calendar calendario = Calendar.getInstance();
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
			String fecha ="";
			fecha=""+Utils.FormatoDateFecha(calendario.getTime(), "dd/MM/yyyy");
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			String hora = sdf.format(calendario.getTime());
			
			
			String cveDescripcionUR="";
			List list2=this.dto.getRenglonesQReporte();
			Iterator it2=list2.iterator();
			Integer contador = 0;
			while(it2.hasNext())
			{	
				Object[] st2=(Object[])it2.next();
				cveDescripcionUR=""+st2[14];
				logger.debug("cveDescripcionUR:"+cveDescripcionUR);
			}
			
			tempFile = File.createTempFile("histpag.html", ".tmp" );
			fichero = new FileWriter(tempFile);
			pw = new PrintWriter(fichero);
			
			
			pw.println("<html>"+
					"<head><meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>"+
					"<body leftmargin='36.0' rightmargin='36.0' topmargin='36.0' bottommargin='36.0'><div align='Center'> <br /></div>");
			pw.println("<table width='100.0%' align='Center' cellpadding='0.0' cellspacing='0.0' border='1' bordercolor='#ffffff'>");
			pw.println("<tr><td><img src='"+rutaImagen+"' align='Left' width='150' height='65' /></td>");
			pw.println("<td colspan='4'><table width='100%'><tr><td border='0.5' bordercolor='#ffffff' width='0' colspan='4'>");
			pw.println("<div align='Center'>INSTITUTO FEDERAL ELECTORAL</div></td></tr><tr><td border='0.5' bordercolor='#ffffff' width='0' colspan='4'>");
			pw.println("<div align='Center'>"+this.dto.getUnidadOrganizacional()+"</div></td></tr></table></td><td><table><tr><td>&nbsp;</td></tr>");
			pw.println("<tr><td align='center' width='20%'>Fecha:</td></tr><tr><td align='center' width='20%'>"+fecha+" "+hora+" hrs.</td>");
			pw.println("</tr></table></td></tr>");
			
			if(idEstado.equals("0")&&idDistrito.equals("0"))//estado y distrito valen cero
			{
				pw.println("<tr><td width='20%' >&nbsp;</td><td align='center' width='60%' colspan='4'>OFICINAS CENTRALES</td><td width='20%'>&nbsp;</td><tr></table>");
			
			}else//hay estado y distrito
			{
				String distritoSesion=""+this.dto.getDescDistrito();
				if(idDistrito.equals("0"))//junta local
				{
					pw.println("<tr><td colspan='6'><table width='100%'><tr><td align='center' width='26%'>Entidad Federativa:</td>");
					pw.println("<td align='center' width='26%' rowspan='2'>JUNTA LOCAL</td><tr><tr>");
					pw.println("<td align='center' width='26%'>"+this.dto.getIdEstado()+"-"+this.dto.getDescEntidad()+"</td>");
					pw.println("</tr></table></td></tr></table>");
				} else {//junta distrital
					if (distritoSesion.equals("null")||distritoSesion==null||distritoSesion.equals("")) {
						pw.println("<tr><td colspan='6'><table width='100%'><tr><td align='center' width='26%'>Entidad Federativa:</td>");
						pw.println("<td align='center' width='26%' rowspan='2'>JUNTA LOCAL</td></tr><tr>");
						pw.println("<td align='center' width='26%'>"+this.dto.getIdEstado()+"-"+this.dto.getDescEntidad()+"</td>");
						pw.println("</tr></table></td><tr></table>");
					} else {
						pw.println("<tr><td colspan='6'><table width='100%'><tr><td align='center' width='26%'>Entidad Federativa:</td>");
						pw.println("<td align='center' width='26%'>Distrito:</td></tr><tr>");
						pw.println("<td align='center' width='26%'>"+this.dto.getIdEstado()+"-"+this.dto.getDescEntidad()+"</td>");
						pw.println("<td align='center' width='26%'>"+this.dto.getIdDistrito()+"-"+this.dto.getDescDistrito()+"</td>");
						pw.println("</tr></table></td></tr></table>");
					}
				}
			}
			
			pw.println("<span><br /></span><table width='100.0%' align='Center' cellpadding='0.0' cellspacing='0.0' border='1'>");
			
			//	renglon del nombre del reporte
			pw.println("<tr bgcolor='#f5f5fc'><td align='center'><b>Reporte Hist&oacute;rico de Movimientos</b></td></tr>");
			pw.println("<tr bgcolor='#f5f5fc'><td align='center'  colspan='6'><b>"+cveDescripcionUR+"</b></td></tr>");
			String cambio="";
			String palabra="";
			if( dto.getChkAlta() != null && dto.getChkBaja() != null
					&& dto.getChkCambio() != null && dto.getChkReingreso() != null ) {
				palabra = "TODOS";
			} else {
				if(dto.getChkAlta() != null 
						&& dto.getChkAlta().equals("alta")) {
					palabra= "ALTA ";
				}
				if(dto.getChkBaja() != null
						&& dto.getChkBaja().equals("baja")) {
					palabra = palabra + " BAJA ";
				}
				if( dto.getChkCambio() != null 
						&& dto.getChkCambio().equals("cambio")) {
					palabra= palabra + " CAMBIO ";
				}
				if( dto.getChkReingreso() != null
						&& dto.getChkReingreso().equals("reingreso")) {
					palabra = palabra + " REINGRESO ";
				}
			}
			
			pw.println("<tr bgcolor='#f5f5fc'><td align='center'><b>Quincena: "+this.dto.getSelQuincena()+"        Movimientos: "+palabra+"</b></td></tr></table><br>");
			
			
			/********************************tabla 3*********************************/
			pw.println("<table width='100.0%' align='Center' cellpadding='0.0' cellspacing='0.0' border='1'>");	
			List list=this.dto.getRenglonesQReporte();
	 		String anteriorId="";
	 		String rfcC="";//rfc de hist mov
	 		String nombreC="";//nombre de hist mov
			Iterator it=list.iterator();
			while(it.hasNext()) {	
				Object[] st=(Object[])it.next();
				if (st[15].toString().contains(GenericConstantsSinope.Puestos.DIETAS_CONSEJERO_DISTRITAL)
						||st[15].toString().contains(GenericConstantsSinope.Puestos.DIETAS_CONSEJERO_LOCAL)
						||st[15].toString().contains(GenericConstantsSinope.Puestos.DIETAS_PROVISIONAL1)
						||st[15].toString().contains(GenericConstantsSinope.Puestos.DIETAS_PROVISIONAL2)
						||st[15].toString().contains(GenericConstantsSinope.Puestos.DIETAS_SUPLENTE1_DISTRITAL)
						||st[15].toString().contains(GenericConstantsSinope.Puestos.INSTALACION_CONSEJERO_DISTRITAL)
						||st[15].toString().contains(GenericConstantsSinope.Puestos.INSTALACION_CONSEJERO_LOCAL)) {
					if(!anteriorId.equals(""+st[4])) {//comprueba que no sea el mismo empleado que en la corrida pasada
						contador = 0;
						pw.println("<tr bgcolor='#f5f5fc'><td colspan='6' align='center'><b>Nombre del empleado: "+st[1]+"<br>CURP: "+st[2]+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;RFC: "+st[3]+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Núm Empleado: "+st[4]+"</b></td></tr>");
						pw.println("<tr bgcolor='#f5f5fc'><td align='center' width='5%'><b>No.</b></td><td align='center' width='10%'><b>Movimiento</b></td><td align='center' width='20%'><b>Fecha</b></td><td align='center' colspan='2'><b>Puesto</b></td><td align='center' width='15%'><b>Plaza</b></td></tr>");
					}
					rfcC=""+st[11];
					nombreC=""+st[12];
					nombreC=nombreC.trim();
					cambio=""+st[13];
//					logger.debug("rfcC:"+rfcC+", nombreC:"+nombreC+", cambio:"+cambio);
//					logger.debug("condicion:"+(cambio.equals("s")&&((!rfcC.equals("")&&!rfcC.equals("null"))||(!nombreC.equals(" ")&&!nombreC.equals("")&&!nombreC.equals("null")))));
					//si es cambio y rfc o nombre no son vacios pinta el renglon
					if (cambio.equals("s")&&((!rfcC.equals("")&&!rfcC.equals("null"))
							||(!nombreC.equals(" ")&&!nombreC.equals("")&&!nombreC.equals("null")))) {
						contador = contador+1;
						// Consecutivo del movimiento
						pw.println("<tr><td align='center>"+contador+"</td>");
						// Tipo de movimiento (ALTA, BAJA, REINGRESO O MODIFICACIÓN)
						pw.println("<td align='center'>"+st[7]+"</td>");
//						pw.println(suma+"<td align='center' colspan='2'>"+st[7]+"</td>");
						// Fecha de movimiento del empleado
						pw.println("<td align='center'>"+st[8]+"</td>");
						// Puesto
						pw.println("<td align='center' colspan='2'>"+st[9]+"</td>");
						// Número de plaza
						pw.println("<td align='center'>"+st[10]+"</td></tr>");
						anteriorId=""+st[4];
					} else {
						if (cambio.equals("null")) {//si no es renglon de cambio no valida nada y lo presenta
							contador = contador+1;
							// Consecutivo del movimiento
							pw.println("<tr><td align='center'>"+contador+"</td>");
							// Tipo de movimiento (ALTA, BAJA, REINGRESO O MODIFICACIÓN)
							pw.println("<td align='center'>"+st[7]+"</td>");
							// Fecha de movimiento del empleado
							pw.println("<td align='center'>"+st[8]+"</td>");
							// Puesto
							pw.println("<td align='center' colspan='2'>"+st[9]+"</td>");
							// Número de plaza
							pw.println("<td align='center'>"+st[10]+"</td></tr>");
							anteriorId=""+st[4];
						}
					}
				} else {
					if(!anteriorId.equals(""+st[4]))//comprueba que no sea el mismo empleado que en la corrida pasada
					{
						contador = 0;
						if (st[6] == null)
							contrato="&nbsp;";
						else
							contrato = st[6].toString();
						pw.println("<tr bgcolor='#f5f5fc'><td colspan='6' align='center'><b>Nombre del empleado: "+st[1]+"<br>CURP: "+st[2]+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;RFC: "+st[3]+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Núm Empleado: "+st[4]+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Núm Contrato: "+ contrato +"</b></td></tr>");
						pw.println("<tr bgcolor='#f5f5fc'><td align='center' width='5%'><b>No.</b></td><td align='center' width='10%'><b>Movimiento</b></td><td align='center' width='20%'><b>Fecha</b></td><td align='center' colspan='2'><b>Puesto</b></td><td align='center' width='15%'><b>Plaza</b></td></tr>");
					}
					rfcC=""+st[11];
					nombreC=""+st[12];
					nombreC=nombreC.trim();
					cambio=""+st[13];
//					logger.debug("rfcC:"+rfcC+", nombreC:"+nombreC+", cambio:"+cambio);
					//si es cambio y rfc o nombre no son vacios pinta el renglon
					if (cambio.equals("s")&&((!rfcC.equals("")&&!rfcC.equals("null"))
							||(!nombreC.equals(" ")&&!nombreC.equals("")&&!nombreC.equals("null")))) {
						contador = contador+1;
						// Consecutivo del movimiento
						pw.println("<tr><td align='center'>"+contador+"</td>");
						
						//Número de contrato
//						pw.println("<td align='center'>");
//						suma=""+st[6];
//						if (suma.equals("null")) {
//							suma="&nbsp;";
//						}
//						pw.println(suma+"</td>");
						
						// Tipo de movimiento (ALTA, BAJA, REINGRESO O MODIFICACIÓN)
						pw.println("<td align='center'>"+st[7]+"</td><td align='center'>");
						// Fecha de movimiento del empleado
						pw.println("<td align='center'>"+st[8]+"</td>");
						// Puesto
						pw.println("<td align='center' colspan='2'>"+st[9]+"</td>");
						// Número de plaza
						pw.println("<td align='center'>"+st[10]+"</td></tr>");
						anteriorId=""+st[4];
					} else {
						if(cambio.equals("null")) {//si no es renglon de cambio no valida nada y lo presenta
							contador = contador+1;
							// Consecutivo del movimiento
							pw.println("<tr><td align='center'>"+contador+"</td>");
							
							// Número de contrato
//							pw.println("<td align='center'>");
//							suma=""+st[6];
//							if(suma.equals("null")) {
//								suma="&nbsp;";
//							}
//							pw.println(suma+"</td>");
							
							// Tipo de movimiento (ALTA, BAJA, REINGRESO O MODIFICACIÓN|)
							pw.println("<td align='center'>"+st[7]+"</td>");
							// Fecha de movimiento del empleado
							pw.println("<td align='center'>"+st[8]+"</td>");	
							// Puesto
							pw.println("<td align='center' colspan='2'>"+st[9]+"</td>");
							// Número de plaza
							pw.println("<td align='center'>"+st[10]+"</td></tr>");
							anteriorId=""+st[4];
						}
					}
				}
			}
			if(anteriorId.equals("")) {//no hay datos pero imprime un reglon
				pw.println("<tr bgcolor='#f5f5fc'><td colspan='6' align='center'><b>Nombre del empleado:<br>CURP:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;RFC:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;N&uacute;m Empleado:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Núm Contrato: </b></td></tr>");
				pw.println("<tr bgcolor='#f5f5fc'><td align='center' width='5%'><b>No.</b></td><td align='center' width='10%'><b>Movimiento</b></td><td align='center' width='20%'><b>Fecha</b></td><td align='center' colspan='2'><b>Puesto</b></td><td align='center' width='15%'><b>Plaza</b></td></tr>");
				pw.println("<tr><td align='center'>&nbsp;</td><td align='center'>&nbsp;</td><td align='center'>&nbsp;</td><td align='center'>&nbsp;</td><td align='center'>&nbsp;</td></tr>");
			}
			pw.println("</table></body></html>");
			tempFile.deleteOnExit();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally 
        	{
           		try 
           		{
        	   		if (null != fichero)
        		   	fichero.close();
           		} catch (Exception e2) 
           		{
              		e2.printStackTrace();
           		}
       		}
        	return tempFile;
	}
}
