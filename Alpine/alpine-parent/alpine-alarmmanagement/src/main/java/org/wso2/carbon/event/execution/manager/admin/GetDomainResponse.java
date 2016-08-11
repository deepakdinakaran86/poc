
package org.wso2.carbon.event.execution.manager.admin;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.wso2.carbon.event.execution.manager.admin.dto.domain.xsd.TemplateDomainDTO;


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
 *         &lt;element name="return" type="{http://domain.dto.admin.manager.execution.event.carbon.wso2.org/xsd}TemplateDomainDTO" minOccurs="0"/>
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
    "_return"
})
@XmlRootElement(name = "getDomainResponse")
public class GetDomainResponse {

    @XmlElementRef(name = "return", namespace = "http://admin.manager.execution.event.carbon.wso2.org", type = JAXBElement.class, required = false)
    protected JAXBElement<TemplateDomainDTO> _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link TemplateDomainDTO }{@code >}
     *     
     */
    public JAXBElement<TemplateDomainDTO> getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link TemplateDomainDTO }{@code >}
     *     
     */
    public void setReturn(JAXBElement<TemplateDomainDTO> value) {
        this._return = value;
    }

}
