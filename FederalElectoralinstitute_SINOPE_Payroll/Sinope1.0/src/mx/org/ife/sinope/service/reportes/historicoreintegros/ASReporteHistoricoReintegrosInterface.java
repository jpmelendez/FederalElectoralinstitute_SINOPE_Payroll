package mx.org.ife.sinope.service.reportes.historicoreintegros;

import java.util.ArrayList;
import java.util.List;

import mx.org.ife.sinope.dto.catalogos.DTOCQuincenas;
import mx.org.ife.sinope.dto.reportes.DTOHistoricoReintegros;

public interface ASReporteHistoricoReintegrosInterface {

	public ArrayList<DTOCQuincenas> getQuincena();
	public List<DTOHistoricoReintegros> consultaReporte(String cboQuincenas, DTOHistoricoReintegros dto);
	public List<DTOHistoricoReintegros> consultaDetalle(String idFolio);
	public List<DTOHistoricoReintegros> obtieneFirmas(String estadoDetalle, String distritoDetalle, String areaDetalle, String cac);
}
