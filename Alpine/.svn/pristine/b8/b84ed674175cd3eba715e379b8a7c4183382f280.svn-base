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
package com.pcs.alpine.services.repository;

import java.util.List;

import com.pcs.alpine.model.Domain;

/**
 * This class is responsible for Database related operation related to Domain
 *
 * @author pcseg292 Renjith P
 * @author pcseg288 Deepak Dinakaran
 */
public interface DomainRepository {

	/***
	 * Method to deactivate an Domain across Galaxy
	 *
	 * @param domain
	 * @return domain
	 */
	@Deprecated
	public Domain deleteDomain(Domain domain);

	/***
	 * Method to get the domain details for the corresponding domain Name
	 *
	 * @param domainName
	 * @return Domain
	 */
	public Domain findDomainByName(String domainName);

	/***
	 * Method to get all the domains as a list
	 *
	 * @return List<Domain>
	 */
	@Deprecated
	public List<Domain> getAllDomain();

	/***
	 * Method to get the maximum allowed users in a domain
	 *
	 * @param domainName
	 * @return Domain
	 */
	@Deprecated
	public Long getMaxUsers(String domainName);

	/**
	 * @param domainDTO
	 * @return
	 */
	@Deprecated
	public List<Domain> getChildrenOfDomain(String domainName);

	public Domain saveDomain(Domain domain);

	/**
	 * Method is responsible to get all the domains of given domain name list
	 * and status.
	 * 
	 * @param domainNames
	 * @param status
	 * @return
	 */
	@Deprecated
	List<Domain> getAllDomains(List<String> domainNames, Integer status);

	/***
	 * Method to update an Domain across Galaxy
	 *
	 * @param domain
	 * @return domain
	 */
	@Deprecated
	public Domain updateDomain(Domain domain);
}
