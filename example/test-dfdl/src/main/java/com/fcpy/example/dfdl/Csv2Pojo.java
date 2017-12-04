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
package com.fcpy.example.dfdl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;

import test.Data;
import edu.illinois.ncsa.daffodil.japi.Compiler;
import edu.illinois.ncsa.daffodil.japi.Daffodil;
import edu.illinois.ncsa.daffodil.japi.DataProcessor;
import edu.illinois.ncsa.daffodil.japi.Diagnostic;
import edu.illinois.ncsa.daffodil.japi.ParseResult;
import edu.illinois.ncsa.daffodil.japi.ProcessorFactory;

public class Csv2Pojo {

    public static void main(String[] args) throws IOException, JAXBException {
        Compiler c = Daffodil.compiler();
        ProcessorFactory pf = c.compileFile(new File(".\\config\\csv.dfdl.xsd"));
        if (pf.isError()) {
            java.util.List<Diagnostic> diags = pf.getDiagnostics();
            for (Diagnostic d : diags) {
                System.out.println(d.toString());
            }
            return;
        }
        DataProcessor dp = pf.onPath("/");
        FileInputStream fis = new FileInputStream(new File(".\\config\\simpleCSV.txt"));
        ReadableByteChannel rbc = fis.getChannel();
        ParseResult pr = dp.parse(rbc);
        Document infoset = pr.result();
        String xmlResult = new XMLOutputter().outputString(infoset);
        System.out.println("----------XML>>");
        System.out.println(xmlResult);
        JAXBContext jc = JAXBContext.newInstance(Data.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Data pojoResult = (Data) unmarshaller.unmarshal(new StringReader(xmlResult));
        System.out.println("---------POJO>>");
        System.out.println("data=" + pojoResult);
        System.out.println("data.header=" + pojoResult.getHeader());
        System.out.println("data.header.title=" + Arrays.toString(pojoResult.getHeader().getTitle()));
        System.out.println("data.record[]=" + Arrays.toString(pojoResult.getRecord()));
        System.out.println("data.record[0].item[]=" + Arrays.toString(pojoResult.getRecord()[0].getItem()));
        System.out.println("data.record[1].item[]=" + Arrays.toString(pojoResult.getRecord()[1].getItem()));
        fis.close();
    }
}
