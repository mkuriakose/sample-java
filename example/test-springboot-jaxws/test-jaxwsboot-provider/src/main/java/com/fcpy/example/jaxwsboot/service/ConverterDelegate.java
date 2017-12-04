/*
 * Copyright (c) 2015, FCPY Studio.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fcpy.example.jaxwsboot.service;

import java.io.UnsupportedEncodingException;
import com.fcpy.example.jaxwsboot.bean.ResponseBean;
import javax.jws.WebService;
import javax.jws.WebParam;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;


@WebService (targetNamespace="http://service.jaxwsboot.example.fcpy.com/", serviceName="ConverterService", portName="ConverterPort", wsdlLocation="WEB-INF/wsdl/ConverterService.wsdl")
@BindingType (value=SOAPBinding.SOAP12HTTP_BINDING)
public class ConverterDelegate{

    com.fcpy.example.jaxwsboot.service.Converter _converter = null;

    public ResponseBean encodeHex (@WebParam(name="data") String data, @WebParam(name="encoding") String encoding) throws UnsupportedEncodingException {
        return _converter.encodeHex(data,encoding);
    }

    public ResponseBean toUpperCase (@WebParam(name="data") String data) {
        return _converter.toUpperCase(data);
    }

    public ConverterDelegate() {
        _converter = new com.fcpy.example.jaxwsboot.service.Converter(); }

}