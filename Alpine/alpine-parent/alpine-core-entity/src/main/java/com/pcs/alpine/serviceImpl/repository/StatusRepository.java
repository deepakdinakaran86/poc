/**
 * 
 */
package com.pcs.alpine.serviceImpl.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.alpine.model.Status;
import com.pcs.alpine.repo.utils.CassandraDBUtil;

/**
 * StatusRepository
 * 
 * @description Responsible for providing the 'C R U D' operations for status
 * @author Daniela (PCSEG191)
 * @date 25 Aug 2014
 * @since galaxy-1.0.0
 */
@Repository("statusRepository")
public class StatusRepository {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(StatusRepository.class);

	@Autowired
	private CassandraDBUtil cassandraDBUtil;

	/**
	 * Responsible for fetching the status based on status name
	 * 
	 * @param statusName
	 * @return statusKey
	 */

	public Integer getStatus(String statusName) {

		String cqlStatement = "SELECT status_key from status where status_name = '"
		        + statusName + "'";
		ResultSet resultSet = cassandraDBUtil.getSession()
		        .execute(cqlStatement);
		Integer status = null;
		Row one = null;
		if (resultSet != null) {
			List<Row> array = resultSet.all();
			if (CollectionUtils.isNotEmpty(array)) {
				one = array.get(0);
			}
		}
		if (one != null) {
			try {
				// ObjectMapper mapper = new ObjectMapper();
				status = one.getInt(0);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
		if (status == null) {
			return null;
		}
		return status;
	}

	public List<Status> getAllStatus() {

		String cqlStatement = "SELECT JSON * from status";
		ResultSet resultSet = cassandraDBUtil.getSession()
		        .execute(cqlStatement);
		List<Status> statusList = new ArrayList<Status>();
		for (Row row : resultSet) {
			ObjectMapper mapper = new ObjectMapper();
			String string = row.getString(0);
			try {
				Status status = mapper.readValue(string, Status.class);
				statusList.add(status);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}

		}
		return statusList;
	}

}
