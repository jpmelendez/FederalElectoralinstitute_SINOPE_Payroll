package mx.org.ife.sinope.dao.reportes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mx.org.ife.sinope.dto.catalogos.DTOCQuincenas;
import mx.org.ife.sinope.dto.reportes.DTOHistoricoReintegros;
import mx.org.ife.sinope.helper.reportes.VHHistoricoReintegros;
import mx.org.ife.sinope.queries.reportes.QRYHistoricoReintegros;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

public class DAOHistoricoReintegros {

	private Logger log = Logger.getLogger(DAOHistoricoReintegros.class.getName());
	private Session session;
	
	
	public DAOHistoricoReintegros(Session session){
		this.session = session;
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<DTOCQuincenas> getQuincena() throws RuntimeException{
		log.debug("finding DTOCQuincenas instance by example");
		
		ArrayList<DTOCQuincenas> iter = new ArrayList<DTOCQuincenas>();
			
			Criteria criteria = session.createCriteria(DTOCQuincenas.class);
					 criteria.addOrder(Order.asc("id.anio"));
					 criteria.addOrder(Order.asc("id.numeroQna"));
					 
			iter = (ArrayList<DTOCQuincenas>)criteria.list();
			
		return iter;
	}
	
	
	/**
	 * Metodo que se utiliza para consultar el reintegro en base a la quincena seleccionada.
	 * @param cboQuincena
	 * @return
	 */
	public List<DTOHistoricoReintegros> consultaReporte(String cboQuincenas, DTOHistoricoReintegros dto){
		
		List<DTOHistoricoReintegros> listaTabla = new ArrayList<DTOHistoricoReintegros>();
		VHHistoricoReintegros helper = new VHHistoricoReintegros();
		QRYHistoricoReintegros qry = new QRYHistoricoReintegros();
		
		Query query = session.createSQLQuery(qry.getQueryReintegro(cboQuincenas, dto));
		Iterator<?> iter = query.list().listIterator();
		
		while(iter.hasNext()){
			Object[] objeto = (Object[])iter.next();
			listaTabla.add(helper.getDTO(objeto));
		}
		
		return listaTabla;
	}
	
/**
 * Metodo que se utiliza poara obtener el detalle del reintegro
 * @param idFolio
 * @return
 */
public List<DTOHistoricoReintegros> consultaDetalle(String idFolio){
		
		List<DTOHistoricoReintegros> listaDetalle = new ArrayList<DTOHistoricoReintegros>();
		VHHistoricoReintegros helper = new VHHistoricoReintegros();
		QRYHistoricoReintegros qry = new QRYHistoricoReintegros();
		
		Query query = session.createSQLQuery(qry.queryObtenerDetalle(idFolio));
		Iterator<?> iter = query.list().listIterator();
		
		while(iter.hasNext()){
			Object[] objeto = (Object[])iter.next();
			listaDetalle.add(helper.getDTOPDF(objeto));
		}
		
		return listaDetalle;
	}

/**
 * Metodo que se utiliza para obtener las firmas de los responsables dependiendo de la unidad y el usuario que ingreso en el sistema
 * @param estado
 * @param distrito
 * @param unidadOrganizacional
 * @return
 * @throws Exception 
 */

public List<DTOHistoricoReintegros> obtieneFirmas(String estadoDetalle, String distritoDetalle, String areaDetalle, String cac) throws Exception{
	
	List<DTOHistoricoReintegros> listaFirmas = new ArrayList<DTOHistoricoReintegros>();
	VHHistoricoReintegros helper = new VHHistoricoReintegros();
	QRYHistoricoReintegros qry = new QRYHistoricoReintegros();
	
	Query query = session.createSQLQuery(qry.getQueryFirmas(estadoDetalle, distritoDetalle, areaDetalle, cac));
	Iterator<?> iter = query.list().listIterator();
	
	while(iter.hasNext()){
		Object[] objeto = (Object[])iter.next();
		listaFirmas.add(helper.getDTOFirmas(objeto));
	}
	
	return listaFirmas;
	
}
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
