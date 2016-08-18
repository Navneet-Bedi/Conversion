/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.w3c.dom.Element;

/**
 *
 * @author Harshitha
 */
@ManagedBean
@SessionScoped
public class ConvManagedBean {

    /**
     * Creates a new instance of ConversionBean
     */
    String datafield = "";
    String ftomtr = "Feet to Meter";
    String gtoltr = "Gallon to Liter";
    String convradio = "";

    public void setDatafield(String s) {
        datafield = s;
    }

    public String getDatafield() {
        return datafield;
    }

    public void setConvradio(String s) {
        convradio = s;
    }

    public String getConvradio() {
        return convradio;
    }

    public String send() {
        String str = "";
        try {
            // Create connection, message factory, and SOAP factory
            SOAPConnectionFactory soapConnectionFactory
                    = SOAPConnectionFactory.newInstance();
            SOAPConnection connection
                    = soapConnectionFactory.createConnection();
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPFactory soapFactory = SOAPFactory.newInstance();

            // Create a message
            SOAPMessage message = messageFactory.createMessage();

            SOAPPart soapPart = message.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration("enc", "http://schemas.xmlsoap.org/soap/encoding/");
            Name encStyle = soapFactory.createName("encodingStyle", "SOAP-ENV", "http://schemas.xmlsoap.org/soap/envelope/");
            envelope.addAttribute(encStyle, "http://schemas.xmlsoap.org/soap/encoding/");

            // Get the SOAP header from the message and remove it
            SOAPHeader header = message.getSOAPHeader();
            header.detachNode();

            // Get the SOAP body from the message
            SOAPBody body = message.getSOAPBody();

            Name bodyName = soapFactory.createName("convert", "tns", "http://bc9959/");
            SOAPBodyElement bodyElement = body.addBodyElement(bodyName);
            Name nm = soapFactory.createName("arg0");
            SOAPElement arg0 = bodyElement.addChildElement(nm);
            arg0.addTextNode(datafield);
            Name nm1 = soapFactory.createName("arg1");
            SOAPElement arg1 = bodyElement.addChildElement(nm1);
            arg1.addTextNode(convradio);

            message.saveChanges();

            URL endpoint = new URL("http://134.154.10.165:8080/bc9959ServiceBeanService/bc9959ServiceBean?");

            SOAPMessage reply = connection.call(message, endpoint);
            org.w3c.dom.NodeList nl = reply.getSOAPBody().getElementsByTagName("return");
            Element result = (Element) nl.item(0);
            str = result.getFirstChild().getNodeValue();

            // Close the connection
            connection.close();
        } catch (Exception ex) {
            //            ex.printStackTrace();
        }
        return str;
    }
}
