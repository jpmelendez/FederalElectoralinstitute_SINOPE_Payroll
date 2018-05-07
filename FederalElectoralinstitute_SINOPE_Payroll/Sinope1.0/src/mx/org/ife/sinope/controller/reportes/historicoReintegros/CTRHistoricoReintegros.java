package mx.org.ife.sinope.controller.reportes.historicoReintegros;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import mx.org.ife.components.dto.DTOUsuario;
import mx.org.ife.components.factory.ObjectFactory;
import mx.org.ife.metodos.dto.generales.UsuarioDto;
import mx.org.ife.sinope.controller.nomina.comprobacion.CTRComprobacionNomina;
import mx.org.ife.sinope.delegate.reportes.BSDHistoricoReintegrosInterface;
import mx.org.ife.sinope.dto.DTOUsuarioSinope;
import mx.org.ife.sinope.dto.catalogos.DTOCQuincenas;
import mx.org.ife.sinope.dto.reportes.DTOHistoricoPagos;
import mx.org.ife.sinope.dto.reportes.DTOHistoricoReintegros;
import mx.org.ife.sinope.reports.RPTHistoricoReintegros;
import mx.org.ife.sinope.reports.RPTRelacionChqNomina;
import mx.org.ife.sinope.service.reportes.historicoreintegros.ASReporteHistoricoReintegrosInterface;
import mx.org.ife.sinope.util.GenericConstantsSinope;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class CTRHistoricoReintegros extends ActionSupport implements ServletRequestAware, ServletResponseAware{

	/*Configuracion action*/
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private DTOUsuarioSinope usuarioSesion;
	private DTOHistoricoReintegros dto = new DTOHistoricoReintegros();
	private BSDHistoricoReintegrosInterface historicoReintegrosInterface;
	private static final Logger logger;
	private ArrayList<DTOCQuincenas> listaQuincenas;
	private String cboQuincenas;
	private List<DTOHistoricoReintegros> listaTabla;
	private List<DTOHistoricoReintegros> listaDetalle;
	private List<DTOHistoricoReintegros> listaFirmas;
	private String idFolio;
	private String rdReporte;
	private InputStream fileStream;
	private String cac;
	private String unidadOrganizacional;
	private String nombreCompleto;
	private String estadoDetalle;
	private String distritoDetalle;
	private String areaDetalle;
	
	static {
		logger = LoggerFactory.getLogger(CTRHistoricoReintegros.class);
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

/*
	public void prepare() throws Exception {
		    session = request.getSession();
			usuarioSesion = (DTOUsuarioSinope)session.getAttribute(GenericConstantsSinope.USUARIO_SESION_SINOPE);
	}
*/
	public String inicio(){
		try {
			historicoReintegrosInterface = (BSDHistoricoReintegrosInterface) ObjectFactory.getObjectFactory(GenericConstantsSinope.BSDHistoricoReintegros);
			listaQuincenas = historicoReintegrosInterface.getQuincena();
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "exito";
	}

	public String obtenerDatosTabla(){

		try {
			
			session = request.getSession();
			usuarioSesion = (DTOUsuarioSinope)session.getAttribute(GenericConstantsSinope.USUARIO_SESION_SINOPE);
			DTOUsuario datosesion = usuarioSesion.getDatosLDAP();
			
			this.dto.setEstadoLdap("" + usuarioSesion.getIdEstadoLDAP());
			this.dto.setDistritoLdap("" + usuarioSesion.getIdDistritoLDAP());
			this.dto.setAreaLdap("" + usuarioSesion.getIdAreaLDAP());
			this.dto.setEstadoSeleccion("" + usuarioSesion.getIdEstado());
			this.dto.setDistritoSeleccion("" + usuarioSesion.getIdDistrito());
			this.dto.setAreaSeleccion("" + usuarioSesion.getIdArea());
			this.dto.setTratamientoLdap("" + usuarioSesion.getDatosLDAP().getTratamiento());
			
//			System.out.println("----EstadoLdap : "+usuarioSesion.getIdEstadoLDAP());
//			System.out.println("----DistritoLdap : "+usuarioSesion.getIdDistritoLDAP());
//			System.out.println("----AreaLdap : "+usuarioSesion.getIdAreaLDAP());
//			System.out.println("----EstadoSeleccion : "+usuarioSesion.getIdEstado());
//			System.out.println("----DistritoSeleccion : "+usuarioSesion.getIdDistrito());
//			System.out.println("----AreaSeleccion : "+usuarioSesion.getIdArea());
//			System.out.println("----TratamientoLdap : "+usuarioSesion.getDatosLDAP().getTratamiento());
			
			historicoReintegrosInterface = (BSDHistoricoReintegrosInterface) ObjectFactory.getObjectFactory(GenericConstantsSinope.BSDHistoricoReintegros);
			listaTabla = historicoReintegrosInterface.consultaReporte(cboQuincenas, dto);
			listaQuincenas = historicoReintegrosInterface.getQuincena();
			logger.debug("QUINCENA seleccionada: "+ cboQuincenas);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "obtenerDatosTabla";
	}
	
	public String obtenerDetalle(){
		try {
			
			session = request.getSession();
			usuarioSesion = (DTOUsuarioSinope)session.getAttribute(GenericConstantsSinope.USUARIO_SESION_SINOPE);
			DTOUsuario datosesion = usuarioSesion.getDatosLDAP();
			
			cac = usuarioSesion.getDatosLDAP().getTratamiento();
			unidadOrganizacional = datosesion.getUnidadOrganizacional();
			nombreCompleto = datosesion.getNombreCompleto();
		
			historicoReintegrosInterface = (BSDHistoricoReintegrosInterface) ObjectFactory.getObjectFactory(GenericConstantsSinope.BSDHistoricoReintegros);
			listaDetalle = historicoReintegrosInterface.consultaDetalle(idFolio);
			
			/*Llenamos edo dis, area*/
			for (DTOHistoricoReintegros tmp : listaDetalle) {
				estadoDetalle = tmp.getIdEstado();
				distritoDetalle = tmp.getIdDistrito();
				areaDetalle = tmp.getIdArea();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "obtenerDetalle";
	}

	public String generarReporte(){
		
		try {
			File archivo;
			session = request.getSession();
			usuarioSesion = (DTOUsuarioSinope)session.getAttribute(GenericConstantsSinope.USUARIO_SESION_SINOPE);
			DTOUsuario datosesion = usuarioSesion.getDatosLDAP();
			
			cac = usuarioSesion.getDatosLDAP().getTratamiento();
			nombreCompleto = datosesion.getNombreCompleto();
			unidadOrganizacional = datosesion.getUnidadOrganizacional();
			
			logger.debug("---->nombreCompleto SELECCIONADO: " + nombreCompleto);
			
			historicoReintegrosInterface = (BSDHistoricoReintegrosInterface) ObjectFactory.getObjectFactory(GenericConstantsSinope.BSDHistoricoReintegros);
			listaDetalle = historicoReintegrosInterface.consultaDetalle(idFolio);
			
			listaFirmas = historicoReintegrosInterface.obtieneFirmas(estadoDetalle, distritoDetalle, areaDetalle, cac);
			logger.debug("---->ID_FOLIO para REPORTE: "+ idFolio);
			logger.debug("---->REPORTE SELECCIONADO: " + rdReporte);
			
			
			if(this.rdReporte.equals("3"))//pdf
			{
				RPTHistoricoReintegros rpt;
				rpt = new RPTHistoricoReintegros(listaDetalle, listaFirmas, estadoDetalle, distritoDetalle, areaDetalle, unidadOrganizacional, nombreCompleto);
				
				try {
					archivo = rpt.Reporte();
					fileStream = new FileInputStream(archivo);
					if(archivo.exists()) {
						archivo.delete();
						archivo = null;
					}
					return "ReporteHistReintegroPDF";
					
				} catch (Exception e) {
				}	
			}	
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return "SUCCESS";
		
	}

	/*getter y setters VISTA*/
	public ArrayList<DTOCQuincenas> getListaQuincenas() {
		return listaQuincenas;
	}
	public void setListaQuincenas(ArrayList<DTOCQuincenas> listaQuincenas) {
		this.listaQuincenas = listaQuincenas;
	}

	public List<DTOHistoricoReintegros> getListaTabla() {
		return listaTabla;
	}
	public void setListaTabla(List<DTOHistoricoReintegros> listaTabla) {
		this.listaTabla = listaTabla;
	}
	public String getCboQuincenas() {
		return cboQuincenas;
	}
	public void setCboQuincenas(String cboQuincenas) {
		this.cboQuincenas = cboQuincenas;
	}
	public List<DTOHistoricoReintegros> getListaDetalle() {
		return listaDetalle;
	}
	public void setListaDetalle(List<DTOHistoricoReintegros> listaDetalle) {
		this.listaDetalle = listaDetalle;
	}
	public String getIdFolio() {
		return idFolio;
	}
	public void setIdFolio(String idFolio) {
		this.idFolio = idFolio;
	}
	public String getRdReporte() {
		return rdReporte;
	}
	public void setRdReporte(String rdReporte) {
		this.rdReporte = rdReporte;
	}
	public InputStream getFileStream() {
		return fileStream;
	}
	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}
	public List<DTOHistoricoReintegros> getListaFirmas() {
		return listaFirmas;
	}
	public void setListaFirmas(List<DTOHistoricoReintegros> listaFirmas) {
		this.listaFirmas = listaFirmas;
	}
	
	public String getUnidadOrganizacional() {
		return unidadOrganizacional;
	}
	public void setUnidadOrganizacional(String unidadOrganizacional) {
		this.unidadOrganizacional = unidadOrganizacional;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	
	public String getCac() {
		return cac;
	}
	public void setCac(String cac) {
		this.cac = cac;
	}
	public String getEstadoDetalle() {
		return estadoDetalle;
	}
	public void setEstadoDetalle(String estadoDetalle) {
		this.estadoDetalle = estadoDetalle;
	}
	public String getDistritoDetalle() {
		return distritoDetalle;
	}
	public void setDistritoDetalle(String distritoDetalle) {
		this.distritoDetalle = distritoDetalle;
	}
	public String getAreaDetalle() {
		return areaDetalle;
	}
	public void setAreaDetalle(String areaDetalle) {
		this.areaDetalle = areaDetalle;
	}
	public DTOHistoricoReintegros getDto() {
		return dto;
	}
	public void setDto(DTOHistoricoReintegros dto) {
		this.dto = dto;
	}
	
	
	
}
