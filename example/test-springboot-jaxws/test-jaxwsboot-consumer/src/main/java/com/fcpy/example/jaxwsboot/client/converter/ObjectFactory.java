//
// Generated By:JAX-WS RI IBM 2.2.1-07/09/2014 01:53 PM(foreman)- (JAXB RI IBM 2.2.3-07/07/2014 12:56 PM(foreman)-)
//


package com.fcpy.example.jaxwsboot.client.converter;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.fcpy.example.jaxwsboot.client.converter package. 
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

    private final static QName _ToUpperCaseResponse_QNAME = new QName("http://service.jaxwsboot.example.fcpy.com/", "toUpperCaseResponse");
    private final static QName _EncodeHex_QNAME = new QName("http://service.jaxwsboot.example.fcpy.com/", "encodeHex");
    private final static QName _EncodeHexResponse_QNAME = new QName("http://service.jaxwsboot.example.fcpy.com/", "encodeHexResponse");
    private final static QName _UnsupportedEncodingException_QNAME = new QName("http://service.jaxwsboot.example.fcpy.com/", "UnsupportedEncodingException");
    private final static QName _ToUpperCase_QNAME = new QName("http://service.jaxwsboot.example.fcpy.com/", "toUpperCase");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.fcpy.example.jaxwsboot.client.converter
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EncodeHex }
     * 
     */
    public EncodeHex createEncodeHex() {
        return new EncodeHex();
    }

    /**
     * Create an instance of {@link EncodeHexResponse }
     * 
     */
    public EncodeHexResponse createEncodeHexResponse() {
        return new EncodeHexResponse();
    }

    /**
     * Create an instance of {@link ToUpperCase }
     * 
     */
    public ToUpperCase createToUpperCase() {
        return new ToUpperCase();
    }

    /**
     * Create an instance of {@link UnsupportedEncodingException }
     * 
     */
    public UnsupportedEncodingException createUnsupportedEncodingException() {
        return new UnsupportedEncodingException();
    }

    /**
     * Create an instance of {@link ToUpperCaseResponse }
     * 
     */
    public ToUpperCaseResponse createToUpperCaseResponse() {
        return new ToUpperCaseResponse();
    }

    /**
     * Create an instance of {@link ResponseBean }
     * 
     */
    public ResponseBean createResponseBean() {
        return new ResponseBean();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ToUpperCaseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.jaxwsboot.example.fcpy.com/", name = "toUpperCaseResponse")
    public JAXBElement<ToUpperCaseResponse> createToUpperCaseResponse(ToUpperCaseResponse value) {
        return new JAXBElement<ToUpperCaseResponse>(_ToUpperCaseResponse_QNAME, ToUpperCaseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EncodeHex }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.jaxwsboot.example.fcpy.com/", name = "encodeHex")
    public JAXBElement<EncodeHex> createEncodeHex(EncodeHex value) {
        return new JAXBElement<EncodeHex>(_EncodeHex_QNAME, EncodeHex.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EncodeHexResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.jaxwsboot.example.fcpy.com/", name = "encodeHexResponse")
    public JAXBElement<EncodeHexResponse> createEncodeHexResponse(EncodeHexResponse value) {
        return new JAXBElement<EncodeHexResponse>(_EncodeHexResponse_QNAME, EncodeHexResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnsupportedEncodingException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.jaxwsboot.example.fcpy.com/", name = "UnsupportedEncodingException")
    public JAXBElement<UnsupportedEncodingException> createUnsupportedEncodingException(UnsupportedEncodingException value) {
        return new JAXBElement<UnsupportedEncodingException>(_UnsupportedEncodingException_QNAME, UnsupportedEncodingException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ToUpperCase }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.jaxwsboot.example.fcpy.com/", name = "toUpperCase")
    public JAXBElement<ToUpperCase> createToUpperCase(ToUpperCase value) {
        return new JAXBElement<ToUpperCase>(_ToUpperCase_QNAME, ToUpperCase.class, null, value);
    }

}
