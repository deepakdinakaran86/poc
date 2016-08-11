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
package com.pcs.alpine.services.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.alpine.model.Domain;
import com.pcs.alpine.repo.utils.CassandraDBUtil;
import com.pcs.alpine.services.repository.DomainRepository;

/**
 * This class is responsible for Database related operation related to Domain
 * 
 * @author pcseg292 Renjith P
 * @author pcseg288 Deepak Dinakaran
 */
@Repository
public class DomainRepositoryImpl implements DomainRepository {

	@Autowired
	CassandraDBUtil cassandraDBUtil;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(DomainRepository.class);

	/***
	 * Method to create/update a Domain across Galaxy
	 * 
	 * @param domain
	 * @return domain
	 */
	@Override
	public Domain saveDomain(Domain domain) {
		LOGGER.debug("<<--Entry save Domain-->>");
		Mapper<Domain> mapper = new MappingManager(cassandraDBUtil.getSession())
		        .mapper(Domain.class);
		mapper.save(domain);
		LOGGER.debug("<<--Exit  save Domain-->>");
		return domain;
	}

	/***
	 * Method to get the domain details for the corresponding domain Name
	 * 
	 * @param domainName
	 * @return Domain
	 */
	@Override
	public Domain findDomainByName(String domainName) {

		LOGGER.debug("<<--Entry get findDomainByName -->>");
		String cqlStatement = "SELECT JSON * from domain where name = '"
		        + domainName + "'";
		ResultSet resultSet = cassandraDBUtil.getSession()
		        .execute(cqlStatement);

		Domain domain = null;
		Row one = resultSet.one();
		if (one != null) {
			String string = one.getString(0);
			try {
				ObjectMapper mapper = new ObjectMapper();
				domain = mapper.readValue(string, Domain.class);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
		LOGGER.debug("<<--Exit get findDomainByName -->>");
		return domain;
	}

	/***
	 * Method to get all the domains as a list
	 * 
	 * @return List<Domain>
	 */
	@Override
	public List<Domain> getAllDomain() {
		LOGGER.debug("<<--Entry fetch All Domain()-->>");
		String cqlStatement = "SELECT JSON * from domain";
		ResultSet resultSet = cassandraDBUtil.getSession()
		        .execute(cqlStatement);
		List<Domain> domainList = new ArrayList<Domain>();
		for (Row row : resultSet) {
			ObjectMapper mapper = new ObjectMapper();
			String string = row.getString(0);
			try {
				Domain domain = mapper.readValue(string, Domain.class);
				domainList.add(domain);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
		LOGGER.debug("<<--Exit fetch All Menu()-->>");
		return domainList;
	}

	/***
	 * Method to get all the domains of a list with given status
	 * 
	 * @param domainName
	 * @param status
	 * @return List<Domain>
	 */
	@Override
	public List<Domain> getAllDomains(List<String> domainNames, Integer status) {

		String cqlStatement = "SELECT JSON * from domain  where name in ("
		        + domainNames.toString() + ")  and status_key = " + status + "";
		ResultSet resultSet = cassandraDBUtil.getSession()
		        .execute(cqlStatement);
		List<Domain> domainList = new ArrayList<Domain>();
		for (Row row : resultSet) {
			ObjectMapper mapper = new ObjectMapper();
			String string = row.getString(0);
			try {
				Domain domain = mapper.readValue(string, Domain.class);
				domainList.add(domain);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
		LOGGER.debug("<<--Exit get findDomainByName -->>");
		return domainList;
	}

	/***
	 * Method to update a domain
	 * 
	 * @param domainName
	 * @param status
	 * @return List<Domain>
	 */
	@Override
	public Domain updateDomain(Domain domain) {
		LOGGER.debug("<<--Entry update Domain-->>");
		// cassandraDBUtil.getMappingManager().mapper(Domain.class).save(domain);
		LOGGER.debug("<<--Exit  update Domain-->>");
		return domain;
	}

	@Override
	public Long getMaxUsers(String domainName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Domain> getChildrenOfDomain(String domainName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Domain deleteDomain(Domain domain) {
		// TODO Auto-generated method stub
		return null;
	}
}
