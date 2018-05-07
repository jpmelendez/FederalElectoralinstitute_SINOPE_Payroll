package mx.org.ife.sinope.queries.reportes;

import com.sun.org.apache.xpath.internal.operations.And;

import mx.org.ife.sinope.dto.reportes.DTOHistoricoReintegros;
import mx.org.ife.sinope.util.Utils;

public class QRYHistoricoReintegros {
	
	private DTOHistoricoReintegros dto;

	/**
	 * 
	 * @param cboQuincena
	 * @return
	 */
	public String getQueryReintegro(String cboQuincenas, DTOHistoricoReintegros dto){
		
		String estadoLDAP=""+dto.getEstadoLdap();
		String distritoLDAP=""+dto.getDistritoLdap();
		String areaLDAP=""+dto.getAreaLdap();
		String estado=""+dto.getEstadoSeleccion();
		String distrito=""+dto.getDistritoSeleccion();
		String area=""+dto.getAreaSeleccion();		
		
		
		StringBuffer qry = new StringBuffer("");
		
		qry.append("select cur.clave_unidad_responsable, hp.id_folio, trim(to_char(dfv.numero_folio, '00000009')) as numero_folio, ");
		qry.append("ctp.tipo_pago||' '||(case when ctp.TIPO_NOMINA = 'O' then 'Ordinaria' when ctp.TIPO_NOMINA = 'E' then 'Extraordinaria' ||' '||rn.numero_periodo end)||' '||rn.numero_periodo as tipo_pago, ");
		qry.append("em.apellido_paterno||' '||em.apellido_materno||' '||em.nombre as nombre_completo, hp.monto_neto, ");
		qry.append("rn.anio||'-'||lpad(rn.numero_qna,2,'0') as QUINCENA_CORRESPONDE_PAGO, TO_CHAR(re.fecha_deposito, 'dd/mm/yyyy') as fecha_deposito ");
		qry.append("from historico_pago hp ");
		qry.append("join reintegros re on hp.id_reintegro = re.id_reintegro ");
		qry.append("join plazas pl on hp.id_plaza = pl.id_plaza ");
		qry.append("join c_unidades_responsables cur on pl.id_unidad_responsable = cur.id_unidad_responsable ");
		qry.append("join empleados em on hp.id_empleado = em.id_empleado ");
		qry.append("join detalle_formas_valoradas dfv on hp.id_folio = dfv.id_folio ");
		qry.append("join resumen_nomina rn on hp.id_nomina = rn.id_nomina ");
		qry.append("join c_tipo_nomina ctp on rn.id_tipo_nomina = ctp.id_tipo_nomina ");
		qry.append("where ");
		qry.append("rn.numero_qna = ").append(Utils.descomponeCadena(cboQuincenas, "-")[1]);
		qry.append(" and anio = ").append(Utils.descomponeCadena(cboQuincenas, "-")[0]);
		if (estado == "") {
			qry.append(" and cur.id_estado = ").append(estadoLDAP).append(" ");
		} else {
			qry.append(" and cur.id_estado = ").append(estado).append(" ");
		}
		
		if (distrito == "") {
			qry.append(" and cur.id_distrito = ").append(distritoLDAP).append(" ");
		} else {
			qry.append(" and cur.id_distrito = ").append(distrito).append(" ");
		}
		
		qry.append(" and hp.id_reintegro is not null ");
		qry.append("order by cur.id_estado, cur.id_distrito, cur.id_area, dfv.numero_folio");
		
		return qry.toString();
	}

	public String queryObtenerDetalle (String idFolio){
		StringBuffer qry = new StringBuffer("");
		
		qry.append("SELECT FOLIO_EMPLEADO, ID_FOLIO, FECHA_DEPOSITO, NUMERO_FOLIO, ");
		qry.append("NOMBRE_UNIDAD, CLAVE_UNIDAD, MONTO_NETO, RFC, ");  
	    qry.append("NOMBRE_C, QNA_PAGO, RADICACION, NUMERO_CUENTA, FICHA_DEPOSITO, ");
	    qry.append("TIPO_PAGO||' '||TIPO_NOMINA||' '||numero_periodo as tipo_pago, FORMA_PAGO, FOLIO_REINTEGRO, ID_ESTADO, ID_DISTRITO, ID_AREA ");
	    qry.append("FROM (select distinct e.id_empleado as folio_empleado, hp.id_folio, TO_CHAR(re.fecha_deposito,'dd/mm/yyyy') as fecha_deposito, ");		
	    qry.append("trim(to_char(dfv.numero_folio, '00000009')) as numero_folio, ures.descripcion as nombre_unidad, ures.clave_unidad_responsable as clave_unidad, ");
	    qry.append("hp.monto_neto, e.rfc, e.apellido_paterno||' '||e.apellido_materno||' '||e.nombre as nombre_c, nom.anio||'-'||lpad(nom.numero_qna,2,'0') as qna_pago, ");
	    qry.append("rad.clave_radicacion as RADICACION, re.numero_cuenta, re.ficha_deposito, ctp.tipo_pago, nom.numero_periodo, ");
	    qry.append("(case when ctp.TIPO_NOMINA = 'O' then 'Ordinaria' when ctp.TIPO_NOMINA = 'E' then 'Extraordinaria' ||' '||nom.numero_periodo end) as Tipo_nomina, ");
	    qry.append("'Cheque' as forma_pago, re.id_reintegro as folio_reintegro, ures.id_estado, ures.id_distrito, ures.id_area ");
	    qry.append("from resumen_nomina nom, historico_pago hp, reintegros re, empleados e, ");
	    qry.append("detalle_formas_valoradas dfv, historico_movimientos hm, plazas p, c_radicaciones rad, ");
	    qry.append("c_unidades_responsables ures, c_tipo_nomina ctp ");
	    qry.append("where nom.id_nomina = hp.id_nomina ");
	    qry.append("and hp.id_empleado = e.id_empleado ");
	    qry.append("and hp.id_folio = ").append(idFolio).append(" ");
	    qry.append("and hp.id_folio = dfv.id_folio ");
	    qry.append("and hp.id_empleado = hm.id_empleado(+) ");
	    qry.append("and hp.id_plaza = hm.id_plaza(+) ");
	    qry.append("and hp.id_plaza = p.id_plaza ");
	    qry.append("and hm.id_radicacion = rad.id_radicacion ");
	    qry.append("and p.id_unidad_responsable = ures.id_unidad_responsable ");
	    qry.append("and nom.id_tipo_nomina = ctp.id_tipo_nomina ");
	    qry.append("and hp.id_reintegro = re.id_reintegro ");
	    qry.append("ORDER BY e.ID_EMPLEADO)");		
	    		
		return qry.toString();
		
	}
	
	public String getQueryFirmas(String estadoDetalle, String distritoDetalle, String areaDetalle, String cac){
		
		int estado = Integer.parseInt(estadoDetalle);
		int distrito = Integer.parseInt(distritoDetalle);
		int area = Integer.parseInt(areaDetalle);
		
		StringBuffer qry = new StringBuffer("");
		
		qry.append("select dru.id_doc_responsable, dru.id_documento_resp, dru.documento_resp_firma, ");
		qry.append("ru.posicion, ru.nombre||' '||ru.apellido_paterno||' '||ru.apellido_materno as nombre_completo, ");
		qry.append("ru.cargo, cur.descripcion, ru.firma, cur.id_estado, cur.id_distrito, cur.id_area ");
		qry.append("from docs_responsables_unidad dru "); 
		qry.append("inner join responsables_unidad ru on ru.id_responsable_unidad = dru.id_responsable_unidad ");
		qry.append("inner join c_unidades_responsables cur on cur.id_unidad_responsable = ru.id_unidad_responsable ");
		qry.append("where dru.id_documento_resp = 8 ");
		qry.append("and cur.id_estado = ").append(estado).append(" ");
		qry.append("and cur.id_distrito = ").append(distrito).append(" ");
		if ((estado == 0) && (distrito == 0)) {
			qry.append("and cur.id_area = ").append(area).append(" ");	
		}
		qry.append("order by cur.id_estado, cur.id_distrito, cur.id_area, dru.id_doc_responsable, ru.posicion");
		
		return qry.toString();
		}
	}
