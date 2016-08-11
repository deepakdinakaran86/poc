
package org.wso2.carbon.event.execution.manager.admin;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.wso2.carbon.event.execution.manager.admin.dto.configuration.xsd.TemplateConfigurationDTO;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="configuration" type="{http://configuration.dto.admin.manager.execution.event.carbon.wso2.org/xsd}TemplateConfigurationDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "configuration"
})
@XmlRootElement(name = "saveConfiguration")
public class SaveConfiguration {

    @XmlElementRef(name = "configuration", namespace = "http://admin.manager.execution.event.carbon.wso2.org", type = JAXBElement.class, required = false)
    protected JAXBElement<TemplateConfigurationDTO> configuration;

    /**
     * Gets the value of the configuration property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link TemplateConfigurationDTO }{@code >}
     *     
     */
    public JAXBElement<TemplateConfigurationDTO> getConfiguration() {
        return configuration;
    }

    /**
     * Sets the value of the configuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link TemplateConfigurationDTO }{@code >}
     *     
     */
    public void setConfiguration(JAXBElement<TemplateConfigurationDTO> value) {
        this.configuration = value;
    }

}
