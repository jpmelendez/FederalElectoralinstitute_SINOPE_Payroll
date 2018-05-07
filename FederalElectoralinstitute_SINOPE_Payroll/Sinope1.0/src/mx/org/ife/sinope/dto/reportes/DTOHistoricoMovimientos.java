package mx.org.ife.sinope.dto.reportes;

import java.util.List;

public class DTOHistoricoMovimientos 
{
	//datos de la ventana de filtros
	private String txtApeP;
	private String txtApeM;
	private String txtNombres;
	private String txtRfc;
	private String rdEmpleado;
	private String txtCurp;
	private String txtNumE;
	private String txtNumP;
	private String selQuincena;
	private String chkAlta;
	private String chkBaja;
	private String chkReingreso;
	private String chkCambio;
	private String rdReporte;
	
	//resultados pdf
	private String descUnidadResponsable;
	private String descEntidad;
	private String descDistrito;
	private String descArea;
	private List renglonesEReporte;
	private List renglonesQReporte;
	//Contrato
	private List listaContrato;

	private String idEmpleadoContrato;
	private String numPlazaContrato;
	
	/*variables de sesion*/
	private String idUnidadResponsable;
	private String idDistrito;
	private String idDistritoNoLDAP;
	private String idEstado;
	private String idEstadoNoLDAP;
	private String idArea;
	private String idAreaNoLDAP;
	private String unidadOrganizacional;
	private String tipoUsuario; //todo cac normal

	public String getTxtApeP() {
		return txtApeP;
	}
	public void setTxtApeP(String txtApeP) {
		this.txtApeP = txtApeP;
	}
	public String getTxtApeM() {
		return txtApeM;
	}
	public void setTxtApeM(String txtApeM) {
		this.txtApeM = txtApeM;
	}
	public String getTxtNombres() {
		return txtNombres;
	}
	public void setTxtNombres(String txtNombres) {
		this.txtNombres = txtNombres;
	}
	public String getTxtRfc() {
		return txtRfc;
	}
	public void setTxtRfc(String txtRfc) {
		this.txtRfc = txtRfc;
	}
	public String getRdEmpleado() {
		return rdEmpleado;
	}
	public void setRdEmpleado(String rdEmpleado) {
		this.rdEmpleado = rdEmpleado;
	}
	public String getTxtCurp() {
		return txtCurp;
	}
	public void setTxtCurp(String txtCurp) {
		this.txtCurp = txtCurp;
	}
	public String getTxtNumE() {
		return txtNumE;
	}
	public void setTxtNumE(String txtNumE) {
		this.txtNumE = txtNumE;
	}
	public String getTxtNumP() {
		return txtNumP;
	}
	public void setTxtNumP(String txtNumP) {
		this.txtNumP = txtNumP;
	}
	public String getSelQuincena() {
		return selQuincena;
	}
	public void setSelQuincena(String selQuincena) {
		this.selQuincena = selQuincena;
	}
	public String getChkAlta() {
		return chkAlta;
	}
	public void setChkAlta(String chkAlta) {
		this.chkAlta = chkAlta;
	}
	public String getChkBaja() {
		return chkBaja;
	}
	public void setChkBaja(String chkBaja) {
		this.chkBaja = chkBaja;
	}
	public String getChkReingreso() {
		return chkReingreso;
	}
	public void setChkReingreso(String chkReingreso) {
		this.chkReingreso = chkReingreso;
	}
	public String getChkCambio() {
		return chkCambio;
	}
	public void setChkCambio(String chkCambio) {
		this.chkCambio = chkCambio;
	}
	public String getRdReporte() {
		return rdReporte;
	}
	public void setRdReporte(String rdReporte) {
		this.rdReporte = rdReporte;
	}
	public List getRenglonesEReporte() {
		return renglonesEReporte;
	}
	public void setRenglonesEReporte(List renglonesEReporte) {
		this.renglonesEReporte = renglonesEReporte;
	}
	public List getRenglonesQReporte() {
		return renglonesQReporte;
	}
	public void setRenglonesQReporte(List renglonesQReporte) {
		this.renglonesQReporte = renglonesQReporte;
	}
	public String getDescUnidadResponsable() {
		return descUnidadResponsable;
	}
	public void setDescUnidadResponsable(String descUnidadResponsable) {
		this.descUnidadResponsable = descUnidadResponsable;
	}
	public String getDescEntidad() {
		return descEntidad;
	}
	public void setDescEntidad(String descEntidad) {
		this.descEntidad = descEntidad;
	}
	public String getDescDistrito() {
		return descDistrito;
	}
	public void setDescDistrito(String descDistrito) {
		this.descDistrito = descDistrito;
	}
	public String getIdUnidadResponsable() {
		return idUnidadResponsable;
	}
	public void setIdUnidadResponsable(String idUnidadResponsable) {
		this.idUnidadResponsable = idUnidadResponsable;
	}
	public String getIdDistrito() {
		return idDistrito;
	}
	public void setIdDistrito(String idDistrito) {
		this.idDistrito = idDistrito;
	}
	public String getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}
	public String getIdArea() {
		return idArea;
	}
	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}
	public String getDescArea() {
		return descArea;
	}
	public void setDescArea(String descArea) {
		this.descArea = descArea;
	}
	/**
	 * @return the unidadOrganizacional
	 */
	public String getUnidadOrganizacional() {
		return unidadOrganizacional;
	}
	/**
	 * @param unidadOrganizacional the unidadOrganizacional to set
	 */
	public void setUnidadOrganizacional(String unidadOrganizacional) {
		this.unidadOrganizacional = unidadOrganizacional;
	}
	/**
	 * @return the tipoUsuario
	 */
	public String getTipoUsuario() {
		return tipoUsuario;
	}
	/**
	 * @param tipoUsuario the tipoUsuario to set
	 */
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	/**
	 * @return the idDistritoNoLDAP
	 */
	public String getIdDistritoNoLDAP() {
		return idDistritoNoLDAP;
	}
	/**
	 * @param idDistritoNoLDAP the idDistritoNoLDAP to set
	 */
	public void setIdDistritoNoLDAP(String idDistritoNoLDAP) {
		this.idDistritoNoLDAP = idDistritoNoLDAP;
	}
	/**
	 * @return the idEstadoNoLDAP
	 */
	public String getIdEstadoNoLDAP() {
		return idEstadoNoLDAP;
	}
	/**
	 * @param idEstadoNoLDAP the idEstadoNoLDAP to set
	 */
	public void setIdEstadoNoLDAP(String idEstadoNoLDAP) {
		this.idEstadoNoLDAP = idEstadoNoLDAP;
	}
	/**
	 * @return the idAreaNoLDAP
	 */
	public String getIdAreaNoLDAP() {
		return idAreaNoLDAP;
	}
	/**
	 * @param idAreaNoLDAP the idAreaNoLDAP to set
	 */
	public void setIdAreaNoLDAP(String idAreaNoLDAP) {
		this.idAreaNoLDAP = idAreaNoLDAP;
	}
	
	@SuppressWarnings("unchecked")
	public List getListaContrato() {
		return listaContrato;
	}
	@SuppressWarnings("unchecked")
	public void setListaContrato(List listaContrato) {
		this.listaContrato = listaContrato;
	}
	public String getIdEmpleadoContrato() {
		return idEmpleadoContrato;
	}
	public void setIdEmpleadoContrato(String idEmpleadoContrato) {
		this.idEmpleadoContrato = idEmpleadoContrato;
	}
	public String getNumPlazaContrato() {
		return numPlazaContrato;
	}
	public void setNumPlazaContrato(String numPlazaContrato) {
		this.numPlazaContrato = numPlazaContrato;
	}
	
	
	
}
