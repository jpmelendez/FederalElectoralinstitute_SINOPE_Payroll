package mx.org.ife.sinope.queries.reportes;

import org.apache.log4j.Logger;

import mx.org.ife.components.queries.QRYMaster;
import mx.org.ife.sinope.dto.reportes.DTOHistoricoMovimientos;


public class QRYHistoricoMovimientos extends QRYMaster
{
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(QRYHistoricoMovimientos.class.getName());
	private DTOHistoricoMovimientos dto;
	
	public QRYHistoricoMovimientos(DTOHistoricoMovimientos dto) 
	{
		this.dto=dto;
	}
	public String ObtenerArea()
	{
		String query="";
		try
		{
				query="select nombre_area from c_areas where id_area="+this.dto.getIdArea();
		}
		catch(Exception e)
		{
		}	
		return query;
	} 
	public String ObtenerUnidadResponsable()
	{
		String query="";
		try
		{
				query="SELECT A1.DESCRIPCION "
					+"FROM C_UNIDADES_RESPONSABLES A1 " 
					+"WHERE A1.ID_UNIDAD_RESPONSABLE="+this.dto.getIdUnidadResponsable();
		}
		catch(Exception e)
		{
		}	
		return query;
	} 
	public String ObtenerDescDistrito()
	{
		String query="";
		try
		{
				query="SELECT A1.CABECERA_DISTRITAL "
					+"FROM GEOGRAFICO2012.DISTRITOS A1 " 
					+"WHERE "
					+"A1.ID_ESTADO="+this.dto.getIdEstado()+" AND A1.ID_DISTRITO="+this.dto.getIdDistrito()+"";
		}
		catch(Exception e)
		{
		}	
		return query;
	} 
	public String ObtenerDescEstado()
	{
		String query="";
		try
		{
				query="SELECT A1.NOMBRE_ESTADO "
					+"FROM GEOGRAFICO2012.ESTADOS A1 " 
					+"WHERE "
					+"A1.ID_ESTADO="+this.dto.getIdEstado();
		}
		catch(Exception e)
		{
		}	
		return query;
	} 

	
	public String ObtenerDatosEmpleado()
	{
		String query="";
		String estadoLDAP=""+this.dto.getIdEstado();
		String distritoLDAP=""+this.dto.getIdDistrito();
		String areaLDAP=""+this.dto.getIdArea();
		String estado=""+this.dto.getIdEstadoNoLDAP();
		String distrito=""+this.dto.getIdDistritoNoLDAP();
		String area=""+this.dto.getIdAreaNoLDAP();
		String tipoempleado=""+this.dto.getTipoUsuario();
		boolean combo=false;
	
		
		try{
			query="SELECT distinct hm.id_historico_movimiento S0, e.apellido_paterno ||' '|| e.apellido_materno||' '|| e.nombre as S1, e.curp S2 , e.rfc S3, e.id_empleado S4, "
				+"hm.id_movimiento S5, c.numero_contrato S6, cm.descripcion S7, TO_CHAR(hm.fecha_inicio,'dd/mm/yyyy')||'-'||TO_CHAR(hm.fecha_fin,'dd/mm/yyyy') S8, cp.descripcion S9, p.numero_plaza S10, "
				+"case cm.descripcion when 'CAMBIO' then hm.rfc end s11, case cm.descripcion when 'CAMBIO' then hm.apellido_paterno||' '||hm.apellido_materno||' '||hm.nombre end s12, "
				+"case cm.descripcion when 'CAMBIO' then 's' end s13, "
				+"ur.clave_unidad_responsable||' '||ur.descripcion s14, "
				+"cp.clave_puesto s15 "
				+"from Empleados e "
				+"LEFT OUTER  join Historico_Movimientos hm on e.id_empleado=hm.id_empleado "
				+"LEFT OUTER  join Plazas p on hm.id_plaza=p.id_plaza "
				+"left outer join c_unidades_responsables ur on ur.id_unidad_responsable=p.id_unidad_responsable "
				+"LEFT OUTER  join Contratos c on e.id_empleado=c.id_empleado and hm.id_contrato = c.id_contrato "
				+"LEFT OUTER  join C_Puestos cp on p.id_puesto=cp.id_puesto "
				+"LEFT OUTER  join C_Movimientos cm on hm.id_movimiento=cm.id_movimiento "
				+"where p.id_unidad_responsable=ur.id_unidad_responsable ";
			
			if(estado.equals(""))//estado del combo vacio
			{
				query+="and ur.id_estado="+estadoLDAP+" ";
			}else//estado del combo no vacio
			{
				query+="and ur.id_estado="+estado+" ";
				combo=true;
			}
			
			
			if(distrito.equals(""))//id_distrito del combo vacio
			{
				query+="and ur.id_distrito="+distritoLDAP+" ";
			}else//distrito del combo no vacio
			{
				query+="and ur.id_distrito="+distrito+" ";
			}
			
			if(combo)//datos del combo
			{
				if(estado.equals("0"))//si el estado es cero consideramos el area sino no
				{
					query+="and ur.ID_AREA="+area+" ";
				}
				
			}else//datos de ldap
			{
				if(estadoLDAP.equals("0"))//si el estado es cero consideramos el area sino no
				{
					query+="and ur.ID_AREA="+areaLDAP+" ";
				}
			}
			
			if(this.dto.getRdEmpleado().equals("curp"))
			{
				query+="and e.curp='"+this.dto.getTxtCurp()+"' ";
			}
			if(this.dto.getRdEmpleado().equals("numeroE"))
			{
				query+="and e.id_empleado="+this.dto.getTxtNumE()+" ";
			}
			if(this.dto.getRdEmpleado().equals("rfc"))
			{
				query+="and e.rfc='"+this.dto.getTxtRfc()+"' ";
			}
			if(this.dto.getRdEmpleado().equals("numeroP"))
			{
				query+="and p.numero_plaza="+this.dto.getTxtNumP()+" ";
			}
			if(this.dto.getRdEmpleado().equals("nombre")&&!this.dto.getTxtNombres().equals(""))
			{
				query+="and e.nombre LIKE '%"+this.dto.getTxtNombres()+"%' ";
			}
			if(this.dto.getRdEmpleado().equals("nombre")&&!this.dto.getTxtApeP().equals(""))
			{
				query+="AND e.apellido_paterno LIKE '"+this.dto.getTxtApeP()+"%' ";
			}
			if(this.dto.getRdEmpleado().equals("nombre")&&!this.dto.getTxtApeM().equals(""))
			{
				query+="AND e.apellido_materno LIKE '"+this.dto.getTxtApeM()+"%' ";
			}
			if(this.dto.getRdEmpleado().equals("nombre"))//cuando es por nombre pone el order by al ultimo
			{
				query+="order by s4, S0, S8 asc";
			}else 
			{
				query+=" order by s4, S0, S8 asc";
			}
		}
		catch(Exception e){
			
		}	
		//logger.error("------ObtenerDatosEmpleado(): **  "+query+"-------");
		return query;
	}
	public String ObtenerDatosQuincena()
	{
		String query="";
		String estadoLDAP=""+this.dto.getIdEstado();
		String distritoLDAP=""+this.dto.getIdDistrito();
		String areaLDAP=""+this.dto.getIdArea();
		String estado=""+this.dto.getIdEstadoNoLDAP();
		String distrito=""+this.dto.getIdDistritoNoLDAP();
		String area=""+this.dto.getIdAreaNoLDAP();
		String tipoempleado=""+this.dto.getTipoUsuario();
		String anio="";
		String qna="";
		String alta=""+this.dto.getChkAlta();
		String baja=""+this.dto.getChkBaja();
		String cambio=""+this.dto.getChkCambio();
		String reingreso=""+this.dto.getChkReingreso();
		boolean previo=false;
		boolean chekbox=false;
		boolean combo=false;
		try{
			anio=""+this.dto.getSelQuincena().substring(0, 4);
			if(this.dto.getSelQuincena().length()==6)
			{
				qna=""+this.dto.getSelQuincena().substring(5, 6);
			}else
			{
				qna=""+this.dto.getSelQuincena().substring(5, 7);
			}
			query="SELECT distinct hm.id_historico_movimiento S0, e.apellido_paterno ||' '|| e.apellido_materno||' '|| e.nombre as S1, e.curp S2 , e.rfc S3, e.id_empleado S4, "
				+"hm.id_movimiento S5, c.numero_contrato S6, cm.descripcion S7, TO_CHAR(hm.fecha_inicio,'dd/mm/yyyy')||'-'||TO_CHAR(hm.fecha_fin,'dd/mm/yyyy') S8, cp.descripcion S9, p.numero_plaza S10, "
				+"case cm.descripcion when 'CAMBIO' then hm.rfc end s11, case cm.descripcion when 'CAMBIO' then hm.apellido_paterno||' '||hm.apellido_materno||' '||hm.nombre end s12, "
				+"case cm.descripcion when 'CAMBIO' then 's' end s13, "
				+"ur.clave_unidad_responsable||' '||ur.descripcion s14, "
				+"cp.clave_puesto s15 "
				+"from Empleados e "
				+"LEFT OUTER  join Historico_Movimientos hm on e.id_empleado=hm.id_empleado "
				+"LEFT OUTER  join Plazas p on hm.id_plaza=p.id_plaza "
				+"left outer join c_unidades_responsables ur on ur.id_unidad_responsable=p.id_unidad_responsable "
				+"LEFT OUTER  join Contratos c on e.id_empleado=c.id_empleado and hm.id_contrato = c.id_contrato "
				+"LEFT OUTER  join C_Puestos cp on p.id_puesto=cp.id_puesto "
				+"LEFT OUTER  join C_Movimientos cm on hm.id_movimiento=cm.id_movimiento "
				+"where p.id_unidad_responsable=ur.id_unidad_responsable ";
			
			if(estado.equals(""))//estado del combo vacio
			{
				query+="and ur.id_estado="+estadoLDAP+" ";
			}else//estado del combo no vacio
			{
				query+="and ur.id_estado="+estado+" ";
				combo=true; 
			}
			
			
			if(distrito.equals(""))//id_distrito del combo vacio
			{
				query+="and ur.id_distrito="+distritoLDAP+" ";
			}else//distrito del combo no vacio
			{
				query+="and ur.id_distrito="+distrito+" ";
			}
			
			if(combo)//datos del combo
			{
				if(estado.equals("0"))//si el estado es cero consideramos el area sino no
				{
					query+="and ur.ID_AREA="+area+" ";
				}
				
			}else//datos de ldap
			{
				if(estadoLDAP.equals("0"))//si el estado es cero consideramos el area sino no
				{
					query+="and ur.ID_AREA="+areaLDAP+" ";
				}
			}
			
			
				query+="and hm.anio="+anio+" "
				+"AND hm.numero_qna="+qna+" ";
			logger.debug("---PREVIO---checkalta="+this.dto.getChkAlta()+"-----cehckbaja="+this.dto.getChkBaja()+"--------chekreingreso="+this.dto.getChkReingreso()+"---");
			if(alta.equals("alta")||baja.equals("baja")||cambio.equals("cambio")||reingreso.equals("reingreso"))
			{
				query+="AND (";
				chekbox=true;
			}else
			{
				query+="order by s4, S0, S8 asc ";
			}
			if(!alta.equals("null")&&!alta.equals(""))
			{
				logger.debug("---------2-------");
				query+="cm.descripcion='ALTA' ";
				previo=true;
			}
			if(!baja.equals("null")&&!baja.equals(""))
			{
				if(previo)
				{
					query+="OR cm.descripcion='BAJA' ";
				}else
				{
					query+="cm.descripcion='BAJA' ";
					previo=true;
				}
				logger.debug("---------3-------");
				
			}
			if(!cambio.equals("null")&&!cambio.equals(""))
			{
				if(previo)
				{
					query+="OR cm.descripcion='CAMBIO' ";
				}else
				{
					query+="cm.descripcion='CAMBIO' ";
					previo=true;
				}
			}
			if(!reingreso.equals("null")&&!reingreso.equals(""))
			{
				if(previo)
				{
					query+="OR cm.descripcion='REINGRESO' ";
				}else
				{
					query+="cm.descripcion='REINGRESO' ";
				}
			}
			if(chekbox==true)
			{
				logger.debug("---------6--"+chekbox+"---");
				query+=") order by s4, S0, S8 asc";
			}
		}
		catch(Exception e){
			
		}	
		//logger.error("--------query hist movimientos-  ObtenerDatosQuincena() : *** "+query+"---");
		return query;
	}
	public String Obtener()
	{
		String query="";
		try
		{
				query="";
		}
		catch(Exception e)
		{
		}	
		return query;
	} 
	
	public String obtenerDatosContrato(){
		
		String idEmpleadoContrato = this.dto.getIdEmpleadoContrato();
		//String numPlazaContrato = this.dto.getNumPlazaContrato();
		
		String query="";
		
		try
		{
			/*
			query="select distinct(c.numero_contrato) as numContrato "
				+ " from historico_movimientos hm "
				+ " inner join plazas p on hm.id_plaza = p.id_plaza "
				+ " inner join contratos c on hm.id_contrato = c.id_contrato "
				+ " where hm.id_empleado = " + idEmpleadoContrato
				+ " and p.numero_plaza = " + numPlazaContrato;
			*/
			
			query = "select distinct numero_contrato as numContrato, id_contrato as idCont from contratos " 
				+ " where id_empleado = " + idEmpleadoContrato 
				+ " order by idCont asc";
		}
		catch(Exception e)
		{
		}	
		//logger.error("--------query obtenerDatosContrato : *** "+query+"---");
		return query;
		
	}
}
