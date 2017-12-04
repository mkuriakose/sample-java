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

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

import com.fcpy.example.jaxwsboot.bean.ResponseBean;


@WebService (targetNamespace="http://service.jaxwsboot.example.fcpy.com/", serviceName="CalculatorService", portName="CalculatorPort", wsdlLocation="WEB-INF/wsdl/CalculatorService.wsdl")
@BindingType (value=SOAPBinding.SOAP12HTTP_BINDING)
public class CalculatorDelegate{

    com.fcpy.example.jaxwsboot.service.Calculator _calculator = null;

    public ResponseBean add (@WebParam(name="augend") String augend, @WebParam(name="addend") String addend) {
        return _calculator.add(augend,addend);
    }

    public ResponseBean substract (@WebParam(name="minuend") String minuend, @WebParam(name="subtrahend") String subtrahend) {
        return _calculator.substract(minuend,subtrahend);
    }

    public ResponseBean multiply (@WebParam(name="multiplier") String multiplier, @WebParam(name="multiplicand") String multiplicand) {
        return _calculator.multiply(multiplier,multiplicand);
    }

    public ResponseBean divide (@WebParam(name="dividend") String dividend, @WebParam(name="divisor") String divisor) {
        return _calculator.divide(dividend,divisor);
    }

    public CalculatorDelegate() {
        _calculator = new com.fcpy.example.jaxwsboot.service.Calculator(); }

}