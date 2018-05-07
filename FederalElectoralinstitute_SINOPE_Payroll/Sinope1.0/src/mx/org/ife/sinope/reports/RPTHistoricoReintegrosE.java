package mx.org.ife.sinope.reports;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import mx.org.ife.sinope.dto.reportes.DTOHistoricoReintegros;
import mx.org.ife.sinope.util.Utils;

import org.apache.log4j.Logger;

public class RPTHistoricoReintegrosE {
	
	private Logger logger = Logger.getLogger(RPTHistoricoReintegros.class.getName());
	private List<DTOHistoricoReintegros> listaDetalle;
	
	public RPTHistoricoReintegrosE(List<DTOHistoricoReintegros> listaDetalle) {
		this.listaDetalle = listaDetalle;
	}

	public File Reporte()throws IOException{
		File tempFile = null;
		FileWriter fichero = null;
	    PrintWriter pw = null;
	    
	    try {
			String numeros="";
			Calendar calendario = Calendar.getInstance();
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
			String fechaBD="";
			String fecha ="";
			fecha=""+Utils.FormatoDateFecha(calendario.getTime(), "dd/MM/yyyy");
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			String hora = sdf.format(calendario.getTime());
			
			tempFile = File.createTempFile("historicoReintegrosE.html", ".tmp" );
			fichero = new FileWriter(tempFile);
			pw = new PrintWriter(fichero);
			
			/*** Encabezado***/
			pw.println("<html><head><style type='text/css'>.estilo1 {font-family: Arial, Helvetica, sans-serif; font-size: 10px; } .estilo2 {font-family: Arial, Helvetica, sans-serif; font-size: 10px; font-weight: bold; }</style></head><body leftmargin='36.0' rightmargin='36.0' topmargin='36.0' bottommargin='36.0'><div align='Center'> <br /></div>");
			pw.println("<table width='100.0%' align='Center' cellpadding='0.0' cellspacing='0.0' border='0' bordercolor='#ffffff'>");
			pw.println("<tr><td border='0.5' bordercolor='#ffffff' width='0' colspan='7' class='estilo1'><div align='Center'>INSTITUTO FEDERAL ELECTORAL</div></td></tr>");
			pw.println("<tr><td border='0.5' bordercolor='#ffffff' width='0' colspan='7' class='estilo1'><div align='Center'>DIRECCIÓN EJECUTIVA DE ADMINISTRACIÓN</div></td></tr>");
			pw.println("<tr><td border='0.5' bordercolor='#ffffff' width='0' colspan='7' class='estilo1'><div align='Center'>REINTEGRO DE RECURSOS POR COBRO DE CHEQUE</div></td></tr>");
			pw.println("<tr><td border='0.5' bordercolor='#ffffff' width='0' colspan='4' class='estilo1'><div align='Center'>Y QUE NO PROCEDÍA SU PAGO</div></td><td align='left' width='20%' class='estilo1'>Fecha:</td></tr>");
			pw.println("<tr><td border='0.5' bordercolor='#ffffff' width='0' colspan='4' class='estilo1'><div align='Center'>PROCESO FEDERAL ELECTORAL 2011 - 2012</div></td><td align='center' width='20%' class='estilo1'>"+fecha+" "+hora+" hrs.</td></tr></table>");	
			
			/*** Tabla de datos***/
			
			pw.println("<span><br /></span><table width='100.0%' align='Center' cellpadding='0.0' cellspacing='0.0' border='1'>");
			pw.println("<tr bgcolor='#f5f5fc'><td colspan='4' align='center' class='estilo2'>Reporte Historico de Reintegros</td></tr>");
			
			
			for (DTOHistoricoReintegros tmp : listaDetalle ) {
				
			System.out.println("pruebas:------->" + tmp.getPdfFechaDeposito());
			
			pw.println("<tr><td align='left' class='dato2' width='18%'>Fecha depósito : </td>");
			pw.println("<td align='left' bgcolor='#EBEBEB' width='40%'>" + tmp.getPdfFechaDeposito() + "</td>");
			pw.println("<td align='left' class='dato2' width='13%'>Número de folio : </td>");
			pw.println("<td align='left' bgcolor='#EBEBEB' width='29%'>" + tmp.getPdfNumFolio() + "</td></tr>");
			
			pw.println("<tr><td align='left' class='dato2' width='18%'>Unidad responsable: </td>");
			pw.println("<td align='left' bgcolor='#EBEBEB' width='40%'>" + tmp.getPdfNombreUnidad() + "</td>");
			pw.println("<td align='left' class='dato2' width='13%'>Clave unidad : </td>");
			pw.println("<td align='left' bgcolor='#EBEBEB' width='29%'>" + tmp.getPdfClaveUnidad() + "</td></tr>");
			
			pw.println("<tr class='migaja2'><td align='dato2' class='dato2' width='18%'>Importe : </td>");
			pw.println("<td align='left' bgcolor='#EBEBEB' width='12%' >$" + tmp.getPdfImporteStr() + "</td>");
			pw.println("<td align='left' bgcolor='#EBEBEB' width='53%' colspan='2'>" + tmp.getPdfImporteLetra() + "</td></tr>");
				
			pw.println("<tr class='migaja2'><td align='left' class='dato2' width='18%'>Nombre del empleado : </td>");
			pw.println("<td align='left' bgcolor='#EBEBEB' width='40%'>" + tmp.getPdfNombre() + "</td>");
			pw.println("<td align='left' class='dato2' width='13%'>RFC : </td>");
			pw.println("<td align='left' bgcolor='#EBEBEB' width='29%'>" + tmp.getPdfRFC() + "</td></tr>");
				
			pw.println("<tr class='migaja2'><td align='left' class='dato2' width='18%'>Quincena : </td>");
			pw.println("<td align='left' bgcolor='#EBEBEB' width='37%' colspan='3'>" + tmp.getPdfQnaPago() + "</td></tr>");
			
			pw.println("<tr class='migaja2'><td align='left' class='dato2' width='18%'>Radicación : </td>");
			pw.println("<td align='left' bgcolor='#EBEBEB' width='37%' colspan='3'>" + tmp.getPdfRadicacion() + "</td></tr>");
			
			pw.println("<tr class='migaja2'><td align='left' class='dato2' width='18%'>Número de cuenta : </td>");
			pw.println("<td align='left' bgcolor='#EBEBEB' width='40%'>" + tmp.getPdfNumCuenta() + "</td>");
			pw.println("<td align='left' class='dato2' width='13%'>Ficha de deposito : </td>");
			pw.println("<td align='left' bgcolor='#EBEBEB' width='29%'>" + tmp.getPdfFichaDep() + "</td></tr>");
				
			pw.println("<tr class='migaja2'><td align='left' class='dato2' width='18%'>Tipo de Pago : </td>");
			pw.println("<td align='left' bgcolor='#EBEBEB' width='37%' colspan='3'>" + tmp.getPdfTipoPago() + "</td></tr>");
				
			pw.println("<tr class='migaja2'><td align='left' class='dato2' width='18%'>Forma de pago : </td>");
			pw.println("<td align='left' bgcolor='#EBEBEB' colspan='3'>" + tmp.getPdfFormaPago() + "</td></tr>");
			
			}
				
		} catch (Exception e) {
			
		}
		return tempFile;
		   
	}
	
	
	
	
    
   

}
