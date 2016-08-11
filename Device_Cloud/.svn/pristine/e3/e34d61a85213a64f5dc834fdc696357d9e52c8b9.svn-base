/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.cache.neo4j.repository;

import static com.pcs.datasource.enums.PQDataFields.STATUS_KEY;
import static com.pcs.datasource.enums.PQDataFields.STATUS_NAME;
import static com.pcs.datasource.enums.PQDataFields.STATUS_TABLE;

import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.pcs.cache.cassandra.service.CassandraService;
import com.pcs.datasource.model.Status;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date Apr 30, 2015
 * @since galaxy-1.0.0
 */
public class StatusUtil {

	/**
	 * @param statusName
	 * @return
	 */
	public static Integer getStatus(String statusName) {

		Select selectStatus = null;
		selectStatus = QueryBuilder.select().from(STATUS_TABLE.getFieldName());
		selectStatus.where(QueryBuilder.eq(STATUS_NAME.toString(), statusName));
		ResultSet resultSet = CassandraService.getSession().execute(
		        selectStatus);
		List<Row> rows = resultSet.all();

		Status status = new Status();
		for (Row row : rows) {
			status.setStatusKey(row.getInt(STATUS_KEY.getFieldName()));
			status.setStatusName(row.getString(STATUS_NAME.getFieldName()));;
		}
		return status.getStatusKey();

	}

	public static void main(String[] args) {
		getStatus(com.pcs.devicecloud.enums.Status.ACTIVE.getStatus());
	}

}
