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
package com.pcs.datasource.repository.utils;

import static com.pcs.datasource.enums.PQDataFields.STATUS_NAME;
import static com.pcs.datasource.enums.PQDataFields.STATUS_TABLE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.pcs.datasource.model.Status;

/**
 * This utility class to get status details
 *
 * @author pcseg323 (Greeshma)
 * @date April 2015
 * @since galaxy-1.0.0
 */

@Component
public class StatusUtil {

	@Autowired
	private CassandraSessionManager cassandraSessionManager;

	@Autowired
	private DataConversion dataConversion;

	public Integer getStatus(String status) {

		Select selectStatus = null;
		selectStatus = QueryBuilder.select().from(STATUS_TABLE.getFieldName());
		selectStatus.where(QueryBuilder.eq(STATUS_NAME.toString(), status));
		ResultSet resultSet = this.cassandraSessionManager.getSession(
		        "dataSourceCassandra").execute(selectStatus);
		Status statusDetails = dataConversion.convertToStatus(resultSet);
		return statusDetails.getStatusKey();

	}

}
