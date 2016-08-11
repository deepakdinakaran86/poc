package com.pcs.alpine.serviceImpl;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;

import org.wso2.carbon.event.execution.manager.admin.ExecutionManagerAdminServicePortType;

import com.pcs.alpine.services.AlarmConfigurationServices;
import com.pcs.alpine.services.dto.ConfigurationDTO;
import com.pcs.alpine.services.dto.ConfigurationListDTO;
import com.pcs.alpine.services.dto.Domain;
import com.pcs.alpine.services.dto.DomainListDTO;
import com.pcs.alpine.services.dto.TemplateListDTO;

/**
 * 
 * @author pcseg369
 *
 */
public class AlarmConfigurationServicesImpl implements AlarmConfigurationServices {

	@Override
	public DomainListDTO getDomains() {
		DomainListDTO response = new DomainListDTO();
		Domain domain = new Domain();
		domain.setName("TemperatureAnalysis");
		domain.setDescription("Temperature Analysis Descriptions");;
		return response;
	}

	@Override
	public TemplateListDTO getTemplates(String domainname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addConfiguration(String domainname, String templateName,
			ConfigurationDTO configDTO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ConfigurationDTO getConfiguration(String domainname,
			String configName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateConfiguration(String domainname, String configName,
			ConfigurationDTO configDTO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ConfigurationListDTO getAllConfigurations(String domainname) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) throws Exception {

		final String username = "admin";
		final String password = "admin";
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(
						username,
						password.toCharArray());
			}
		});

		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier()
		{
			public boolean verify(String hostname, SSLSession session)
			{
				// ip address of the service URL(like.23.28.244.244)
				if (hostname.equals("10.234.31.155"))
					return true;
				return false;
			}
		});

		URL url = new URL("https://10.234.31.155:9443/services/ExecutionManagerAdminService?wsdl");

		QName qname = new QName("http://admin.manager.execution.event.carbon.wso2.org", "ExecutionManagerAdminServiceHttpsSoap11Endpoint");
		Service service = Service.create(url, qname);

		ExecutionManagerAdminServicePortType adminService = service.getPort(ExecutionManagerAdminServicePortType.class);
		Map<String, Object> requestContext = ((BindingProvider) adminService).getRequestContext();
		requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url.toString());
		requestContext.put(BindingProvider.USERNAME_PROPERTY, username);
		requestContext.put(BindingProvider.PASSWORD_PROPERTY, password);
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		headers.put("Timeout", Collections.singletonList("10000"));
		requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

		System.out.println(adminService.getAllDomains());

	}

}
