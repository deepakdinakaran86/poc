/**
 * 
 */
package com.pcs.alpine.serviceImpl.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.alpine.model.Audit;
import com.pcs.alpine.repo.utils.CassandraDBUtil;

/**
 * StatusRepository
 * 
 * @description Responsible for providing the 'C R U D' operations for audit
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date 09 Jul 2016
 * @since alpine-1.0.0
 */
@Repository("auditRepository")
public class AuditRepository {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(AuditRepository.class);

	@Autowired
	private CassandraDBUtil cassandraDBUtil;

	/**
	 * 
	 * Responsible for fetching the status based on status name
	 * 
	 * @param statusName
	 * @return statusKey
	 */

	public List<Audit> getAllAudit() {

		String cqlStatement = "SELECT JSON * from platform_audit_log";
		ResultSet resultSet = cassandraDBUtil.getSession()
		        .execute(cqlStatement);
		List<Audit> auditList = new ArrayList<Audit>();
		for (Row row : resultSet) {
			ObjectMapper mapper = new ObjectMapper();
			String string = row.getString(0);
			try {
				Audit audit = mapper.readValue(string, Audit.class);
				auditList.add(audit);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}

		}
		return auditList;
	}

}
