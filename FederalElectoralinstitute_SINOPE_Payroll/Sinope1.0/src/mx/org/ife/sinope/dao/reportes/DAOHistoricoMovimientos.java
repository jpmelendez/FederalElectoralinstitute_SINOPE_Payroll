package mx.org.ife.sinope.dao.reportes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mx.org.ife.components.dao.DAOException;
import mx.org.ife.components.queries.QRYMaster;
import mx.org.ife.components.util.HibernateUtil;
import mx.org.ife.sinope.dto.reportes.DTOHistoricoMovimientos;
import mx.org.ife.sinope.queries.reportes.QRYHistoricoMovimientos;




import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class DAOHistoricoMovimientos 
{
	private Logger logger = Logger.getLogger(DAOHistoricoMovimientos.class.getName());
	/**
	 * Variable que me permite almacernar el nombre jndi por el cual se iniciará la busqueda de la base de datos
	 * en la cual se ejecutarán las operaciones.
	 */
	private Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	/**
	 * Variable que almacena los queries.
	 */

	private DTOHistoricoMovimientos dto;
	private QRYMaster qryContainer;
	/**
	 * Constructor que me permite inicializar un DAO que va en busqueda de la base de datos con la que trabajará mediante 
	 * una busqueda en jndi del recurso.
	 * @throws DAOException Excepcion generica que me permite cachar cualquier excepcion ocurrida en el proceso y enmascarla
	 * para presentarla en capas superiores.
	 */
	public DAOHistoricoMovimientos(DTOHistoricoMovimientos dto) {
		this.dto=dto;
	}

	@SuppressWarnings({ "unchecked" })
	public List ObtieneDescripArea()
	{
		SQLQuery query;
		QRYHistoricoMovimientos qryC=new QRYHistoricoMovimientos(this.dto);
		List area=null;
		try 
		{
			query = session.createSQLQuery( qryC.ObtenerArea());
			area= query
			.setCacheMode(CacheMode.GET).list();
		} catch( RuntimeException re ) 
		{
			if(session.isOpen())
			{
				session.close();
			}
			logger.error("Mensaje de error",re);
		}
		return area;
	}	
	@SuppressWarnings({ "unchecked" })
	public List ObtieneDescripUnidadResp()
	{
		SQLQuery query;
		QRYHistoricoMovimientos qryC=new QRYHistoricoMovimientos(this.dto);
		List pagados=null;
		try 
		{
			query = session.createSQLQuery( qryC.ObtenerUnidadResponsable());
			pagados= query
			.setCacheMode(CacheMode.GET).list();
		} catch( RuntimeException re ) 
		{
			if(session.isOpen())
			{
				session.close();
			}
			logger.error("Mensaje de error",re);
		}
		return pagados;
	}	
	
	@SuppressWarnings({ "unchecked" })
	public List ObtieneDescripEntidad()
	{
		SQLQuery query;
		QRYHistoricoMovimientos qryC=new QRYHistoricoMovimientos(this.dto);
		List pagados=null;
		try 
		{
			query = session.createSQLQuery( qryC.ObtenerDescEstado());
			pagados= query
			.setCacheMode(CacheMode.GET).list();
		} catch( RuntimeException re ) 
		{
			if(session.isOpen())
			{
				session.close();
			}
			logger.error("Mensaje de error",re);
		}
		return pagados;
	}	
	
	@SuppressWarnings({ "unchecked" })
	public List ObtieneDescripDistrito()
	{
		SQLQuery query;
		QRYHistoricoMovimientos qryC=new QRYHistoricoMovimientos(this.dto);
		List pagados=null;
		try 
		{
			query = session.createSQLQuery( qryC.ObtenerDescDistrito());
			pagados= query
			.setCacheMode(CacheMode.GET).list();
		} catch( RuntimeException re ) 
		{
			if(session.isOpen())
			{
				session.close();
			}
			logger.error("Mensaje de error",re);
		}
		return pagados;
	}	
	
	@SuppressWarnings({ "unchecked" })
	public List ObtieneRenglonesE()
	{
		SQLQuery query;
		QRYHistoricoMovimientos qryC=new QRYHistoricoMovimientos(this.dto);
		List renglones=null;
		try 
		{
			query = session.createSQLQuery( qryC.ObtenerDatosEmpleado());
			renglones= query
			.addScalar("S0",Hibernate.STRING)
			.addScalar("S1",Hibernate.STRING)
			.addScalar("S2",Hibernate.STRING)
			.addScalar("S3",Hibernate.STRING)
			.addScalar("S4",Hibernate.STRING)
			.addScalar("S5",Hibernate.STRING)
			.addScalar("S6",Hibernate.STRING)
			.addScalar("S7",Hibernate.STRING)
			.addScalar("S8",Hibernate.STRING)
			.addScalar("S9",Hibernate.STRING)
			.addScalar("S10",Hibernate.STRING)
			.addScalar("S11",Hibernate.STRING)
			.addScalar("S12",Hibernate.STRING)
			.addScalar("S13",Hibernate.STRING)
			.addScalar("S14",Hibernate.STRING)
			.addScalar("S15",Hibernate.STRING)
			.setCacheMode(CacheMode.GET).list();
		} catch( RuntimeException re ) 
		{
			if(session.isOpen())
			{
				session.close();
			}
			logger.error("Mensaje de error",re);
		}
		return renglones;
	}	
	@SuppressWarnings({ "unchecked" })
	public List ObtieneRenglonesQ()
	{
		SQLQuery query;
		QRYHistoricoMovimientos qryC=new QRYHistoricoMovimientos(this.dto);
		List renglones=null;
		try 
		{
			query = session.createSQLQuery( qryC.ObtenerDatosQuincena());
			renglones= query
			.addScalar("S0",Hibernate.STRING)
			.addScalar("S1",Hibernate.STRING)
			.addScalar("S2",Hibernate.STRING)
			.addScalar("S3",Hibernate.STRING)
			.addScalar("S4",Hibernate.STRING)
			.addScalar("S5",Hibernate.STRING)
			.addScalar("S6",Hibernate.STRING)
			.addScalar("S7",Hibernate.STRING)
			.addScalar("S8",Hibernate.STRING)
			.addScalar("S9",Hibernate.STRING)
			.addScalar("S10",Hibernate.STRING)
			.addScalar("S11",Hibernate.STRING)
			.addScalar("S12",Hibernate.STRING)
			.addScalar("S13",Hibernate.STRING)
			.addScalar("S14",Hibernate.STRING)
			.addScalar("S15",Hibernate.STRING)
			.setCacheMode(CacheMode.GET).list();
		} catch( RuntimeException re ) 
		{
			if(session.isOpen())
			{
				session.close();
			}
			logger.error("Mensaje de error",re);
		}
		return renglones;
	}	
	
	@SuppressWarnings("unchecked")
	public DTOHistoricoMovimientos ObtieneTodoE(){
		logger.debug("-------------------------------en obtiene todo-----------------------------------");
		List listA;		//descripcion id DISTRITO
		List listB;		//descripcion id ENTIDAD(ESTADO)
		List listC;		//descripcion area
		List list0;		//descripcion id unidad responsable
		List list;		//resultados por empleado
		this.session.beginTransaction();
		listA=this.ObtieneDescripDistrito();
		listB= this.ObtieneDescripEntidad();
		listC=this.ObtieneDescripArea();
		list0=this.ObtieneDescripUnidadResp();
		list = this.ObtieneRenglonesE();
		session.close();
		String descDistrito="";
		String descEntidad="";
		String descUnidadResponsable="";
		String descArea="";
		logger.debug("---------------------------------despues cerrar sesion rg historico pago--------------------------------------");
		Iterator itA=listA.iterator();
		while(itA.hasNext())
		{
			descDistrito=""+itA.next();
		}	
		Iterator itB=listB.iterator();
		while(itB.hasNext())
		{
			descEntidad=""+itB.next();
		}	
		Iterator itC=listC.iterator();
		while(itC.hasNext())
		{
			descArea=""+itC.next();
		}	
		Iterator it0=list0.iterator();
		while(it0.hasNext())
		{
			descUnidadResponsable=""+it0.next();
		}	
		this.dto.setDescDistrito(descDistrito);
		this.dto.setDescEntidad(descEntidad);
		this.dto.setDescArea(descArea);
		this.dto.setDescUnidadResponsable(descUnidadResponsable);
		this.dto.setRenglonesEReporte(list);
		return dto;
	}
	@SuppressWarnings("unchecked")
	public DTOHistoricoMovimientos ObtieneTodoQ(){
		logger.debug("-------------------------------en obtiene todo-----------------------------------");
		List listA;		//descripcion id DISTRITO
		List listB;		//descripcion id ENTIDAD(ESTADO)
		List listC;		//descripcion area
		List list0;		//descripcion id unidad responsable
		List list;		//resultados por empleado
		this.session.beginTransaction();
		listA=this.ObtieneDescripDistrito();
		listB= this.ObtieneDescripEntidad();
		listC=this.ObtieneDescripArea();
		list0=this.ObtieneDescripUnidadResp();
		list = this.ObtieneRenglonesQ();
		session.close();
		String descDistrito="";
		String descEntidad="";
		String descUnidadResponsable="";
		String descArea="";
		logger.debug("---------------------------------despues cerrar sesion rg historico movimiento--------------------------------------");
		Iterator itA=listA.iterator();
		while(itA.hasNext())
		{
			descDistrito=""+itA.next();
		}	
		Iterator itB=listB.iterator();
		while(itB.hasNext())
		{
			descEntidad=""+itB.next();
		}	
		Iterator itC=listC.iterator();
		while(itC.hasNext())
		{
			descArea=""+itC.next();
		}	
		Iterator it0=list0.iterator();
		while(it0.hasNext())
		{
			descUnidadResponsable=""+it0.next();
		}	
		this.dto.setDescDistrito(descDistrito);
		this.dto.setDescEntidad(descEntidad);
		this.dto.setDescArea(descArea);
		this.dto.setDescUnidadResponsable(descUnidadResponsable);
		this.dto.setRenglonesQReporte(list);
		return dto;
	}
	
	@SuppressWarnings("unchecked")
	public DTOHistoricoMovimientos obtieneContrato(String idEmpleadoContrato, String numPlazaContrato) {
		SQLQuery query;
		QRYHistoricoMovimientos qryC=new QRYHistoricoMovimientos(this.dto);
		List listaContrato = null;
		this.session.beginTransaction();
		try {
			query = session.createSQLQuery(qryC.obtenerDatosContrato());
			listaContrato = query
			.addScalar("numContrato", Hibernate.STRING)
			.addScalar("idCont", Hibernate.STRING)
			.setCacheMode(CacheMode.GET).list();
			
			logger.debug("---------------------------------despues cerrar sesion Contratos historico movimiento--------------------------------------");
			
			
			logger.debug("/*********->Valor lista : " + listaContrato.size());
			
			session.close();
			
			this.dto.setListaContrato(listaContrato);
			
				
		} catch (Exception re) {
			
			if(session.isOpen())
			{
				session.close();
			}
			logger.error("Mensaje de error", re);
			
		}
		
		return dto;
		
	}
}
