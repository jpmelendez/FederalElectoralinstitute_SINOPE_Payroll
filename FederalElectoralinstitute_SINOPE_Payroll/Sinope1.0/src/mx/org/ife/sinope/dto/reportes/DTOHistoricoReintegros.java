package mx.org.ife.sinope.dto.reportes;

import java.sql.Blob;

public class DTOHistoricoReintegros {


	private String ur;
	private String folio;
	private Integer idFolio;
	//private String tipoNomina;
	private String tipoPago;
	private String nombreCompleto;
	private String importeString;
	private double importe;
	private String quincenaPago;
	private String fechaDeposito;
	private String idEstado;
	private String idDistrito;
	private String idArea;

	private String pdfFolioEmpleado;
	private String pdfIdFolio;
	private String pdfFechaDeposito;
	private String pdfNumFolio;
	private String pdfNombreUnidad;
	private String pdfClaveUnidad;
	private String pdfImporteStr;
	private double pdfImporte;
	private String pdfImporteLetra;
	private String pdfRFC;
	private String pdfNombre;
	private String pdfQnaPago;
	private String pdfRadicacion;
	private String pdfNumCuenta;
	private String pdfFichaDep;
	private String pdfTipoPago;
	//private String pdfTipoNomina;
	private String pdfFormaPago;
	private String pdfFolioReintegro;

	private String firmaIDResponsable;
	private String firmaIDDocumento;
	private String firmaDocumentoFirma;
	private String firmaPosicion;
	private String firmaRespNombre;
	private String firmaCargo;
	private String firmaUResp;
	private byte[]   firmaImagen;
	private String firmaIDEstado;
	private String firmaIDDistrito;
	private String firmaImagenString;
	
	//datos sesion
	
	private String estadoLdap;
	private String distritoLdap;
	private String areaLdap;
	private String tratamientoLdap;
	private String estadoSeleccion;
	private String distritoSeleccion;
	private String areaSeleccion;
	
	public String getUr() {
		return ur;
	}
	public void setUr(String ur) {
		this.ur = ur;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public Integer getIdFolio() {
		return idFolio;
	}
	public void setIdFolio(Integer idFolio) {
		this.idFolio = idFolio;
	}
	public String getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getImporteString() {
		return importeString;
	}
	public void setImporteString(String importeString) {
		this.importeString = importeString;
	}
	public double getImporte() {
		return importe;
	}
	public void setImporte(double importe) {
		this.importe = importe;
	}
	public String getQuincenaPago() {
		return quincenaPago;
	}
	public void setQuincenaPago(String quincenaPago) {
		this.quincenaPago = quincenaPago;
	}
	public String getFechaDeposito() {
		return fechaDeposito;
	}
	public void setFechaDeposito(String fechaDeposito) {
		this.fechaDeposito = fechaDeposito;
	}
	public String getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}
	public String getIdDistrito() {
		return idDistrito;
	}
	public void setIdDistrito(String idDistrito) {
		this.idDistrito = idDistrito;
	}
	public String getIdArea() {
		return idArea;
	}
	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}
	public String getPdfFolioEmpleado() {
		return pdfFolioEmpleado;
	}
	public void setPdfFolioEmpleado(String pdfFolioEmpleado) {
		this.pdfFolioEmpleado = pdfFolioEmpleado;
	}
	public String getPdfIdFolio() {
		return pdfIdFolio;
	}
	public void setPdfIdFolio(String pdfIdFolio) {
		this.pdfIdFolio = pdfIdFolio;
	}
	public String getPdfFechaDeposito() {
		return pdfFechaDeposito;
	}
	public void setPdfFechaDeposito(String pdfFechaDeposito) {
		this.pdfFechaDeposito = pdfFechaDeposito;
	}
	public String getPdfNumFolio() {
		return pdfNumFolio;
	}
	public void setPdfNumFolio(String pdfNumFolio) {
		this.pdfNumFolio = pdfNumFolio;
	}
	public String getPdfNombreUnidad() {
		return pdfNombreUnidad;
	}
	public void setPdfNombreUnidad(String pdfNombreUnidad) {
		this.pdfNombreUnidad = pdfNombreUnidad;
	}
	public String getPdfClaveUnidad() {
		return pdfClaveUnidad;
	}
	public void setPdfClaveUnidad(String pdfClaveUnidad) {
		this.pdfClaveUnidad = pdfClaveUnidad;
	}
	public String getPdfImporteStr() {
		return pdfImporteStr;
	}
	public void setPdfImporteStr(String pdfImporteStr) {
		this.pdfImporteStr = pdfImporteStr;
	}
	public double getPdfImporte() {
		return pdfImporte;
	}
	public void setPdfImporte(double pdfImporte) {
		this.pdfImporte = pdfImporte;
	}
	public String getPdfImporteLetra() {
		return pdfImporteLetra;
	}
	public void setPdfImporteLetra(String pdfImporteLetra) {
		this.pdfImporteLetra = pdfImporteLetra;
	}
	public String getPdfRFC() {
		return pdfRFC;
	}
	public void setPdfRFC(String pdfRFC) {
		this.pdfRFC = pdfRFC;
	}
	public String getPdfNombre() {
		return pdfNombre;
	}
	public void setPdfNombre(String pdfNombre) {
		this.pdfNombre = pdfNombre;
	}
	public String getPdfQnaPago() {
		return pdfQnaPago;
	}
	public void setPdfQnaPago(String pdfQnaPago) {
		this.pdfQnaPago = pdfQnaPago;
	}
	public String getPdfRadicacion() {
		return pdfRadicacion;
	}
	public void setPdfRadicacion(String pdfRadicacion) {
		this.pdfRadicacion = pdfRadicacion;
	}
	public String getPdfNumCuenta() {
		return pdfNumCuenta;
	}
	public void setPdfNumCuenta(String pdfNumCuenta) {
		this.pdfNumCuenta = pdfNumCuenta;
	}
	public String getPdfFichaDep() {
		return pdfFichaDep;
	}
	public void setPdfFichaDep(String pdfFichaDep) {
		this.pdfFichaDep = pdfFichaDep;
	}
	public String getPdfTipoPago() {
		return pdfTipoPago;
	}
	public void setPdfTipoPago(String pdfTipoPago) {
		this.pdfTipoPago = pdfTipoPago;
	}
	public String getPdfFormaPago() {
		return pdfFormaPago;
	}
	public void setPdfFormaPago(String pdfFormaPago) {
		this.pdfFormaPago = pdfFormaPago;
	}
	public String getPdfFolioReintegro() {
		return pdfFolioReintegro;
	}
	public void setPdfFolioReintegro(String pdfFolioReintegro) {
		this.pdfFolioReintegro = pdfFolioReintegro;
	}
	public String getFirmaIDResponsable() {
		return firmaIDResponsable;
	}
	public void setFirmaIDResponsable(String firmaIDResponsable) {
		this.firmaIDResponsable = firmaIDResponsable;
	}
	public String getFirmaIDDocumento() {
		return firmaIDDocumento;
	}
	public void setFirmaIDDocumento(String firmaIDDocumento) {
		this.firmaIDDocumento = firmaIDDocumento;
	}
	public String getFirmaDocumentoFirma() {
		return firmaDocumentoFirma;
	}
	public void setFirmaDocumentoFirma(String firmaDocumentoFirma) {
		this.firmaDocumentoFirma = firmaDocumentoFirma;
	}
	public String getFirmaPosicion() {
		return firmaPosicion;
	}
	public void setFirmaPosicion(String firmaPosicion) {
		this.firmaPosicion = firmaPosicion;
	}
	public String getFirmaRespNombre() {
		return firmaRespNombre;
	}
	public void setFirmaRespNombre(String firmaRespNombre) {
		this.firmaRespNombre = firmaRespNombre;
	}
	public String getFirmaCargo() {
		return firmaCargo;
	}
	public void setFirmaCargo(String firmaCargo) {
		this.firmaCargo = firmaCargo;
	}
	public String getFirmaUResp() {
		return firmaUResp;
	}
	public void setFirmaUResp(String firmaUResp) {
		this.firmaUResp = firmaUResp;
	}
	public byte[] getFirmaImagen() {
		return firmaImagen;
	}
	public void setFirmaImagen(byte[] firmaImagen) {
		this.firmaImagen = firmaImagen;
	}
	public String getFirmaIDEstado() {
		return firmaIDEstado;
	}
	public void setFirmaIDEstado(String firmaIDEstado) {
		this.firmaIDEstado = firmaIDEstado;
	}
	public String getFirmaIDDistrito() {
		return firmaIDDistrito;
	}
	public void setFirmaIDDistrito(String firmaIDDistrito) {
		this.firmaIDDistrito = firmaIDDistrito;
	}
	public String getFirmaImagenString() {
		return firmaImagenString;
	}
	public void setFirmaImagenString(String firmaImagenString) {
		this.firmaImagenString = firmaImagenString;
	}
	public String getEstadoLdap() {
		return estadoLdap;
	}
	public void setEstadoLdap(String estadoLdap) {
		this.estadoLdap = estadoLdap;
	}
	public String getDistritoLdap() {
		return distritoLdap;
	}
	public void setDistritoLdap(String distritoLdap) {
		this.distritoLdap = distritoLdap;
	}
	public String getAreaLdap() {
		return areaLdap;
	}
	public void setAreaLdap(String areaLdap) {
		this.areaLdap = areaLdap;
	}
	public String getEstadoSeleccion() {
		return estadoSeleccion;
	}
	public void setEstadoSeleccion(String estadoSeleccion) {
		this.estadoSeleccion = estadoSeleccion;
	}
	public String getDistritoSeleccion() {
		return distritoSeleccion;
	}
	public void setDistritoSeleccion(String distritoSeleccion) {
		this.distritoSeleccion = distritoSeleccion;
	}
	public String getAreaSeleccion() {
		return areaSeleccion;
	}
	public void setAreaSeleccion(String areaSeleccion) {
		this.areaSeleccion = areaSeleccion;
	}
	public String getTratamientoLdap() {
		return tratamientoLdap;
	}
	public void setTratamientoLdap(String tratamientoLdap) {
		this.tratamientoLdap = tratamientoLdap;
	}
	
	
}
