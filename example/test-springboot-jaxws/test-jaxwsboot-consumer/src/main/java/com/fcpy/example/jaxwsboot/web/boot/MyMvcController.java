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
package com.fcpy.example.jaxwsboot.web.boot;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fcpy.example.jaxwsboot.client.calculator.CalculatorDelegate;
import com.fcpy.example.jaxwsboot.client.converter.ConverterDelegate;

@EnableWebMvc
@Controller
public class MyMvcController extends WebMvcConfigurerAdapter {
    private static final Logger LOGGER = Logger.getLogger("com.fcpy.example.jaxwsboot.web.boot.MyMvcController");

    public MyMvcController() {
        LOGGER.info("-----MyMvcController is constructed.");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        return bean;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getMultipartResolver() {
        return new CommonsMultipartResolver();
    }
    
    @Autowired
    @Qualifier("calculatorServiceFactory")
    private CalculatorDelegate calculator;

    @Autowired
    @Qualifier("converterServiceFactory")
    private ConverterDelegate converter;

    @RequestMapping(value = "/calculate.htm")
    @ResponseBody
    public String calculate() {
        LOGGER.info("-----MyMvcController.calculate is triggered.");
        try {
            com.fcpy.example.jaxwsboot.client.calculator.ResponseBean rspnBean = calculator.substract("1", "2");
            return String.format("[ %-12s ] %-12s", rspnBean.getExecutionTime(), rspnBean.getResult());
        } finally {
            LOGGER.info("-----MyMvcController.calculate is executed.");
        }
    }

    @RequestMapping(value = "/convert.htm")
    @ResponseBody
    public String convert() {
        LOGGER.info("-----MyMvcController.convert is triggered.");
        com.fcpy.example.jaxwsboot.client.converter.ResponseBean rspnBean;
        try {
            rspnBean = converter.encodeHex("EXAMPLE VALUE", "UTF-8");
            return String.format("[ %-12s ] %-12s", rspnBean.getExecutionTime(), rspnBean.getResult());
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        } finally {
            LOGGER.info("-----MyMvcController.convert is executed.");
        }
    }

}