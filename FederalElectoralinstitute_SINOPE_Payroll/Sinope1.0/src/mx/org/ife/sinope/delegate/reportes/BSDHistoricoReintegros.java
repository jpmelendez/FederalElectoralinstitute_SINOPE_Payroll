package mx.org.ife.sinope.delegate.reportes;

import java.util.ArrayList;
import java.util.List;

import mx.org.ife.components.factory.ObjectFactory;
import mx.org.ife.sinope.dto.catalogos.DTOCQuincenas;
import mx.org.ife.sinope.dto.reportes.DTOHistoricoPagos;
import mx.org.ife.sinope.dto.reportes.DTOHistoricoReintegros;
import mx.org.ife.sinope.service.reportes.historicoreintegros.ASReporteHistoricoReintegrosInterface;
import mx.org.ife.sinope.util.GenericConstantsSinope;

public class BSDHistoricoReintegros implements BSDHistoricoReintegrosInterface{

	private ASReporteHistoricoReintegrosInterface  historicoReintegrosInterface;

	public BSDHistoricoReintegros(){}
	public BSDHistoricoReintegros(Object o){}

	/**
	 * 
	 */
	public ArrayList<DTOCQuincenas> getQuincena(){
		try {
			historicoReintegrosInterface = (ASReporteHistoricoReintegrosInterface) ObjectFactory.getObjectFactory(GenericConstantsSinope.ASReporteHistoricoReintegro);

			return historicoReintegrosInterface.getQuincena();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Consulta reintegros por quincena
	 */
	public List<DTOHistoricoReintegros> consultaReporte(String cboQuincenas, DTOHistoricoReintegros dto){
		try {
			historicoReintegrosInterface = (ASReporteHistoricoReintegrosInterface) ObjectFactory.getObjectFactory(GenericConstantsSinope.ASReporteHistoricoReintegro);

			 return historicoReintegrosInterface.consultaReporte(cboQuincenas, dto);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<DTOHistoricoReintegros> consultaDetalle(String idFolio){
		try {
			historicoReintegrosInterface = (ASReporteHistoricoReintegrosInterface) ObjectFactory.getObjectFactory(GenericConstantsSinope.ASReporteHistoricoReintegro);

			 return historicoReintegrosInterface.consultaDetalle(idFolio);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<DTOHistoricoReintegros> obtieneFirmas(String estadoDetalle, String distritoDetalle, String areaDetalle, String cac){
		
		try {
			historicoReintegrosInterface = (ASReporteHistoricoReintegrosInterface) ObjectFactory.getObjectFactory(GenericConstantsSinope.ASReporteHistoricoReintegro);

		return historicoReintegrosInterface.obtieneFirmas(estadoDetalle, distritoDetalle, areaDetalle, cac);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
