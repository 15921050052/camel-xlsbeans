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
     * Excelファイルから取得するJava Objectのクラス。
     */
    private Class objectType;

    /**
     * 設定情報。
     */
    private XLSBeansConfig config;

    /**
     * XLSBeanのインスタンス。
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

    /**
     * ルート起動時に実行される初期処理。
     * 
     * XLSBeansのインスタンスを生成します。
     * 
     * @throws Exception 初期処理に失敗
     */
    @Override
    protected void doStart() throws Exception {
        xlsBeans = new XLSBeans();
        if (config != null) {
            xlsBeans.setConfig(config);
        }
    }

    /**
     * ルート終了時に実行される終了処理。
     * 
     * @throws Exception 終了処理に失敗
     */
    @Override
    protected void doStop() throws Exception {
        xlsBeans = null;
    }

    /**
     * Java ObjectをExcelファイルに変換します。
     * 
     * XLSBeansではサポートしていないため、このメソッドは未実装です。
     * 
     * @param exchange Exchange
     * @param graph Java Object
     * @param stream Excelファイル
     * @throws Exception 変換に失敗
     */
    @Override
    public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * ExcelファイルをJava Objectに変換します。
     * 
     * @param exchange Exchange
     * @param stream Excelファイル
     * @return Java Object　
     * @throws Exception 変換に失敗
     */
    @Override
    public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {
        if (objectType == null) {
            throw new IllegalArgumentException("No object type specified.");
        }
        return xlsBeans.load(stream, objectType, WorkbookFinder.TYPE_XSSF);
    }

}
