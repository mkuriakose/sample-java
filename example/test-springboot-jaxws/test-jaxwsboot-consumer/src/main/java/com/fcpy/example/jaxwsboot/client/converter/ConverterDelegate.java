//
// Generated By:JAX-WS RI IBM 2.2.1-07/09/2014 01:53 PM(foreman)- (JAXB RI IBM 2.2.3-07/07/2014 12:56 PM(foreman)-)
//


package com.fcpy.example.jaxwsboot.client.converter;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "ConverterDelegate", targetNamespace = "http://service.jaxwsboot.example.fcpy.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ConverterDelegate {


    /**
     * 
     * @param encoding
     * @param data
     * @return
     *     returns com.fcpy.example.jaxwsboot.client.converter.ResponseBean
     * @throws UnsupportedEncodingException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "encodeHex", targetNamespace = "http://service.jaxwsboot.example.fcpy.com/", className = "com.fcpy.example.jaxwsboot.client.converter.EncodeHex")
    @ResponseWrapper(localName = "encodeHexResponse", targetNamespace = "http://service.jaxwsboot.example.fcpy.com/", className = "com.fcpy.example.jaxwsboot.client.converter.EncodeHexResponse")
    @Action(input = "http://service.jaxwsboot.example.fcpy.com/ConverterDelegate/encodeHexRequest", output = "http://service.jaxwsboot.example.fcpy.com/ConverterDelegate/encodeHexResponse", fault = {
        @FaultAction(className = UnsupportedEncodingException_Exception.class, value = "http://service.jaxwsboot.example.fcpy.com/ConverterDelegate/encodeHex/Fault/UnsupportedEncodingException")
    })
    public ResponseBean encodeHex(
        @WebParam(name = "data", targetNamespace = "")
        String data,
        @WebParam(name = "encoding", targetNamespace = "")
        String encoding)
        throws UnsupportedEncodingException_Exception
    ;

    /**
     * 
     * @param data
     * @return
     *     returns com.fcpy.example.jaxwsboot.client.converter.ResponseBean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "toUpperCase", targetNamespace = "http://service.jaxwsboot.example.fcpy.com/", className = "com.fcpy.example.jaxwsboot.client.converter.ToUpperCase")
    @ResponseWrapper(localName = "toUpperCaseResponse", targetNamespace = "http://service.jaxwsboot.example.fcpy.com/", className = "com.fcpy.example.jaxwsboot.client.converter.ToUpperCaseResponse")
    @Action(input = "http://service.jaxwsboot.example.fcpy.com/ConverterDelegate/toUpperCaseRequest", output = "http://service.jaxwsboot.example.fcpy.com/ConverterDelegate/toUpperCaseResponse")
    public ResponseBean toUpperCase(
        @WebParam(name = "data", targetNamespace = "")
        String data);

}
