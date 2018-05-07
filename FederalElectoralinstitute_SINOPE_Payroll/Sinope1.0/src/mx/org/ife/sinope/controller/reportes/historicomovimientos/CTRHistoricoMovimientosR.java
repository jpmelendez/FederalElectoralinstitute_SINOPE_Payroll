package mx.org.ife.sinope.controller.reportes.historicomovimientos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.org.ife.components.dto.DTOUsuario;
import mx.org.ife.sinope.dao.reportes.DAOHistoricoMovimientos;
import mx.org.ife.sinope.dto.DTOUsuarioSinope;
import mx.org.ife.sinope.dto.reportes.DTOHistoricoMovimientos;
import mx.org.ife.sinope.reports.RPTHistoricoMovimientosE;
import mx.org.ife.sinope.reports.RPTHistoricoMovimientosEExcel;
import mx.org.ife.sinope.reports.RPTHistoricoMovimientosQ;
import mx.org.ife.sinope.reports.RPTHistoricoMovimientosQExcel;
import mx.org.ife.sinope.reports.RPTHistoricoMovimientosEHTML;
import mx.org.ife.sinope.reports.RPTHistoricoMovimientosQHTML;
import mx.org.ife.sinope.util.GenericConstantsSinope;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.lowagie.text.DocumentException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class CTRHistoricoMovimientosR extends ActionSupport implements ServletRequestAware, Preparable
{
	
	private Logger logger = Logger.getLogger(CTRHistoricoMovimientosR.class.getName());
	private HttpServletRequest request;
	private HttpServletResponse response;
	HttpSession session;
	DTOUsuarioSinope usuarioSesion;
	private static final long serialVersionUID = 1L;
	private DTOHistoricoMovimientos dto = new DTOHistoricoMovimientos();
	private InputStream fileStream;
	
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public DTOHistoricoMovimientos getDto() {
		return dto;
	}
	public void setDto(DTOHistoricoMovimientos dto) {
		this.dto = dto;
	}	
	public InputStream getFileStream() {
		return fileStream;
	}
	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}

	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	/**
	 * Método implementado para obtener el request.
	 * @param request
	 */
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public String reportes()
	{
		
		File archivo;
		String rol=""+usuarioSesion.getRolActual();

		if(rol.equals("1")||rol.equals("2")||rol.equals("5")||rol.equals("6"))
		{
			this.dto.setTipoUsuario("todo");
		}
		else
		{
			if(usuarioSesion.getDatosLDAP().getTratamiento().toUpperCase().contains("(CAC)"))
			{
				this.dto.setTipoUsuario("cac");
			}else
			{
				this.dto.setTipoUsuario("normal");
			}
		}
		this.dto.setIdUnidadResponsable(""+usuarioSesion.getId_unidad_responsable());
		this.dto.setIdDistrito(""+usuarioSesion.getIdDistritoLDAP());
		this.dto.setIdEstado(""+usuarioSesion.getIdEstadoLDAP());
		this.dto.setIdArea(""+usuarioSesion.getIdAreaLDAP());
		this.dto.setIdDistritoNoLDAP(""+usuarioSesion.getIdDistrito());//del combo
		this.dto.setIdEstadoNoLDAP(""+usuarioSesion.getIdEstado());//del combo
		this.dto.setIdAreaNoLDAP(""+usuarioSesion.getIdArea());//del combo
		
		DTOUsuario usuario=usuarioSesion.getDatosLDAP();
		this.dto.setUnidadOrganizacional(""+usuario.getUnidadOrganizacional());
		
		DAOHistoricoMovimientos dao;
		dao = new DAOHistoricoMovimientos(this.dto);
		
		
		if(this.dto.getRdReporte().equals("1"))//html
		{
			if(this.dto.getRdEmpleado().equals("curp")||this.dto.getRdEmpleado().equals("numeroE")||this.dto.getRdEmpleado().equals("numeroP")||this.dto.getRdEmpleado().equals("rfc")||this.dto.getRdEmpleado().equals("nombre"))
			{
				this.dto= dao.ObtieneTodoE();
				RPTHistoricoMovimientosEHTML rpt;
				rpt=new RPTHistoricoMovimientosEHTML(this.dto);
				try 
				{
					archivo = rpt.Reporte();
					fileStream = new FileInputStream(archivo);
					logger.debug("-----------------------en html------------------");
					if(archivo.exists()) {
						archivo.delete();
						archivo = null;
					}
					return "ReporteHistoricoMovimientosE";
				} catch (Exception e) 
				{
				}
			}else
			{
				this.dto= dao.ObtieneTodoQ();
				RPTHistoricoMovimientosQHTML rpt;
				rpt=new RPTHistoricoMovimientosQHTML(this.dto);
				try 
				{
					archivo = rpt.Reporte();
					fileStream = new FileInputStream(archivo);
					logger.debug("-----------------------en html------------------");
					if(archivo.exists()) {
						archivo.delete();
						archivo = null;
					}
					return "ReporteHistoricoMovimientosQ";
				} catch (Exception e) 
				{
				}
			}
		}
		if(this.dto.getRdReporte().equals("2"))//excel
		{
			if(this.dto.getRdEmpleado().equals("curp")||this.dto.getRdEmpleado().equals("numeroE")||this.dto.getRdEmpleado().equals("numeroP")||this.dto.getRdEmpleado().equals("rfc")||this.dto.getRdEmpleado().equals("nombre"))
			{
				this.dto= dao.ObtieneTodoE();
				RPTHistoricoMovimientosEExcel rpt;
				rpt=new RPTHistoricoMovimientosEExcel(this.dto);
				try {
					archivo = rpt.generaHTML();
					fileStream = new FileInputStream(archivo);
					logger.debug("---------------------excel------------------");
					if(archivo.exists()) {
						archivo.delete();
						archivo = null;
					}
					return "ReporteHistoricoMovimientosEXCELE";
				} catch (Exception e) 
				{
				}
			}else
			{
				this.dto= dao.ObtieneTodoQ();
				RPTHistoricoMovimientosQExcel rpt;
				rpt=new RPTHistoricoMovimientosQExcel(this.dto);
				try 
				{
					archivo = rpt.generaHTML();
					fileStream = new FileInputStream(archivo);
					logger.debug("-----------------------en excel-----------------");
					if(archivo.exists()) {
						archivo.delete();
						archivo = null;
					}
					return "ReporteHistoricoMovimientosEXCELQ";
				} catch (Exception e) 
				{
				}
			}
		}
		
		if(this.dto.getRdReporte().equals("3"))//pdf
		{
			if(this.dto.getRdEmpleado().equals("curp")||this.dto.getRdEmpleado().equals("numeroE")||this.dto.getRdEmpleado().equals("numeroP")||this.dto.getRdEmpleado().equals("rfc")||this.dto.getRdEmpleado().equals("nombre"))
			{
				try {
					this.dto= dao.ObtieneTodoE();
					RPTHistoricoMovimientosE rptEP;
					rptEP=new RPTHistoricoMovimientosE(this.dto);
					archivo = rptEP.Reporte();
					fileStream = new FileInputStream(archivo);
					if(archivo.exists()) {
						archivo.delete();
						archivo = null;
					}
					return "ReporteHistoricoMovimientosPDFE";
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else
			{
				try {
					RPTHistoricoMovimientosQ rptQP;
					rptQP=new RPTHistoricoMovimientosQ(this.dto);
					this.dto= dao.ObtieneTodoQ();
					archivo = rptQP.Reporte();
					fileStream = new FileInputStream(archivo);
					if(archivo.exists()) {
						archivo.delete();
						archivo = null;
					}
					return "ReporteHistoricoMovimientosPDFQ";

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "SUCCESS";
	}
	@Override
	public void prepare() throws Exception {
		session = request.getSession();
		usuarioSesion =(DTOUsuarioSinope)session.getAttribute(GenericConstantsSinope.USUARIO_SESION_SINOPE);
		
	}
}
