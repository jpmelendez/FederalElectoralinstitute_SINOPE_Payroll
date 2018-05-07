package mx.org.ife.sinope.service.reportes.historicoreintegros;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import mx.org.ife.components.util.HibernateUtil;
import mx.org.ife.sinope.dao.reportes.DAOHistoricoReintegros;
import mx.org.ife.sinope.dto.catalogos.DTOCQuincenas;
import mx.org.ife.sinope.dto.reportes.DTOHistoricoReintegros;

public class ASReporteHistoricoReintegros implements
		ASReporteHistoricoReintegrosInterface {

	public ASReporteHistoricoReintegros() {
	}

	public ASReporteHistoricoReintegros(Object o) {
	}

	private Session session;

	/**
	 * 
	 */
	public ArrayList<DTOCQuincenas> getQuincena() {
		try {
			DAOHistoricoReintegros dao = new DAOHistoricoReintegros(session);
			openSession();
			dao.setSession(session);

			return dao.getQuincena();
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		} finally {
			closeSession();
		}

	}

	public List<DTOHistoricoReintegros> consultaReporte(String cboQuincenas, DTOHistoricoReintegros dto) {
		try {
			DAOHistoricoReintegros dao = new DAOHistoricoReintegros(session);
			openSession();
			dao.setSession(session);

			return dao.consultaReporte(cboQuincenas, dto);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		} finally {
			closeSession();
		}

	}

	public List<DTOHistoricoReintegros> consultaDetalle(String idFolio) {
		try {
			DAOHistoricoReintegros dao = new DAOHistoricoReintegros(session);
			openSession();
			dao.setSession(session);

			return dao.consultaDetalle(idFolio);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		} finally {
			closeSession();
		}

	}

	public List<DTOHistoricoReintegros> obtieneFirmas(String estadoDetalle, String distritoDetalle, String areaDetalle, String cac) {
		try {
			DAOHistoricoReintegros dao = new DAOHistoricoReintegros(session);
			openSession();
			dao.setSession(session);

			return dao.obtieneFirmas(estadoDetalle, distritoDetalle, areaDetalle, cac);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		} finally {
			closeSession();
		}

	}

	public void openSession() {
		session = HibernateUtil.getSessionFactory().openSession();
	}

	public void closeSession() {
		if (session != null && session.isOpen())
			session.close();
	}

}
