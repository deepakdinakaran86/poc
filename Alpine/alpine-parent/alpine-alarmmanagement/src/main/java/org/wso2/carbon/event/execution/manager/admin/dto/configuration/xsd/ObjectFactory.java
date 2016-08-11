
package org.wso2.carbon.event.execution.manager.admin.dto.configuration.xsd;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.wso2.carbon.event.execution.manager.admin.dto.configuration.xsd package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TemplateConfigurationInfoDTODescription_QNAME = new QName("http://configuration.dto.admin.manager.execution.event.carbon.wso2.org/xsd", "description");
    private final static QName _TemplateConfigurationInfoDTOName_QNAME = new QName("http://configuration.dto.admin.manager.execution.event.carbon.wso2.org/xsd", "name");
    private final static QName _TemplateConfigurationInfoDTOFrom_QNAME = new QName("http://configuration.dto.admin.manager.execution.event.carbon.wso2.org/xsd", "from");
    private final static QName _TemplateConfigurationInfoDTOType_QNAME = new QName("http://configuration.dto.admin.manager.execution.event.carbon.wso2.org/xsd", "type");
    private final static QName _ParameterDTOValue_QNAME = new QName("http://configuration.dto.admin.manager.execution.event.carbon.wso2.org/xsd", "value");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.wso2.carbon.event.execution.manager.admin.dto.configuration.xsd
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ParameterDTO }
     * 
     */
    public ParameterDTO createParameterDTO() {
        return new ParameterDTO();
    }

    /**
     * Create an instance of {@link TemplateConfigurationDTO }
     * 
     */
    public TemplateConfigurationDTO createTemplateConfigurationDTO() {
        return new TemplateConfigurationDTO();
    }

    /**
     * Create an instance of {@link TemplateConfigurationInfoDTO }
     * 
     */
    public TemplateConfigurationInfoDTO createTemplateConfigurationInfoDTO() {
        return new TemplateConfigurationInfoDTO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://configuration.dto.admin.manager.execution.event.carbon.wso2.org/xsd", name = "description", scope = TemplateConfigurationInfoDTO.class)
    public JAXBElement<String> createTemplateConfigurationInfoDTODescription(String value) {
        return new JAXBElement<String>(_TemplateConfigurationInfoDTODescription_QNAME, String.class, TemplateConfigurationInfoDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://configuration.dto.admin.manager.execution.event.carbon.wso2.org/xsd", name = "name", scope = TemplateConfigurationInfoDTO.class)
    public JAXBElement<String> createTemplateConfigurationInfoDTOName(String value) {
        return new JAXBElement<String>(_TemplateConfigurationInfoDTOName_QNAME, String.class, TemplateConfigurationInfoDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://configuration.dto.admin.manager.execution.event.carbon.wso2.org/xsd", name = "from", scope = TemplateConfigurationInfoDTO.class)
    public JAXBElement<String> createTemplateConfigurationInfoDTOFrom(String value) {
        return new JAXBElement<String>(_TemplateConfigurationInfoDTOFrom_QNAME, String.class, TemplateConfigurationInfoDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://configuration.dto.admin.manager.execution.event.carbon.wso2.org/xsd", name = "type", scope = TemplateConfigurationInfoDTO.class)
    public JAXBElement<String> createTemplateConfigurationInfoDTOType(String value) {
        return new JAXBElement<String>(_TemplateConfigurationInfoDTOType_QNAME, String.class, TemplateConfigurationInfoDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://configuration.dto.admin.manager.execution.event.carbon.wso2.org/xsd", name = "description", scope = TemplateConfigurationDTO.class)
    public JAXBElement<String> createTemplateConfigurationDTODescription(String value) {
        return new JAXBElement<String>(_TemplateConfigurationInfoDTODescription_QNAME, String.class, TemplateConfigurationDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://configuration.dto.admin.manager.execution.event.carbon.wso2.org/xsd", name = "name", scope = TemplateConfigurationDTO.class)
    public JAXBElement<String> createTemplateConfigurationDTOName(String value) {
        return new JAXBElement<String>(_TemplateConfigurationInfoDTOName_QNAME, String.class, TemplateConfigurationDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://configuration.dto.admin.manager.execution.event.carbon.wso2.org/xsd", name = "from", scope = TemplateConfigurationDTO.class)
    public JAXBElement<String> createTemplateConfigurationDTOFrom(String value) {
        return new JAXBElement<String>(_TemplateConfigurationInfoDTOFrom_QNAME, String.class, TemplateConfigurationDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://configuration.dto.admin.manager.execution.event.carbon.wso2.org/xsd", name = "type", scope = TemplateConfigurationDTO.class)
    public JAXBElement<String> createTemplateConfigurationDTOType(String value) {
        return new JAXBElement<String>(_TemplateConfigurationInfoDTOType_QNAME, String.class, TemplateConfigurationDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://configuration.dto.admin.manager.execution.event.carbon.wso2.org/xsd", name = "name", scope = ParameterDTO.class)
    public JAXBElement<String> createParameterDTOName(String value) {
        return new JAXBElement<String>(_TemplateConfigurationInfoDTOName_QNAME, String.class, ParameterDTO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://configuration.dto.admin.manager.execution.event.carbon.wso2.org/xsd", name = "value", scope = ParameterDTO.class)
    public JAXBElement<String> createParameterDTOValue(String value) {
        return new JAXBElement<String>(_ParameterDTOValue_QNAME, String.class, ParameterDTO.class, value);
    }

}
