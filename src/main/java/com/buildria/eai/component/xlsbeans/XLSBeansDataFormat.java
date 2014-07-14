package com.buildria.eai.component.xlsbeans;

import java.io.InputStream;
import java.io.OutputStream;
import net.java.amateras.xlsbeans.XLSBeans;
import net.java.amateras.xlsbeans.XLSBeansConfig;
import net.java.amateras.xlsbeans.xssfconverter.WorkbookFinder;
import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.support.ServiceSupport;

/**
 * XLSBean data format component
 * 
 * @author Seiji Sogabe
 */
public class XLSBeansDataFormat extends ServiceSupport implements DataFormat {

    /**
     * Object type.
     */
    private Class objectType;

    /**
     * Configuration instance.
     */
    private XLSBeansConfig config;

    /**
     * XLSBean instance.
     */
    private XLSBeans xlsBeans;

    public Class getObjectType() {
        return objectType;
    }

    public void setObjectType(Class objectType) {
        this.objectType = objectType;
    }

    public XLSBeansConfig getConfig() {
        return config;
    }

    public void setConfig(XLSBeansConfig config) {
        this.config = config;
    }

    @Override
    protected void doStart() throws Exception {
        xlsBeans = new XLSBeans();
        if (config != null) {
            xlsBeans.setConfig(config);
        }
    }

    @Override
    protected void doStop() throws Exception {
        // do nothing
    }

    @Override
    public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {
        if (objectType == null) {
            throw new IllegalArgumentException("No object type specified.");
        }
        return xlsBeans.load(stream, objectType, WorkbookFinder.TYPE_XSSF);
    }

}
