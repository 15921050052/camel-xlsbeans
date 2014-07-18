package com.buildria.eai.component.xlsbeans;

import java.io.InputStream;
import java.io.OutputStream;
import net.java.amateras.xlsbeans.XLSBeans;
import net.java.amateras.xlsbeans.XLSBeansConfig;
import net.java.amateras.xlsbeans.xssfconverter.WorkbookFinder;
import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.support.ServiceSupport;
import org.apache.camel.util.ObjectHelper;

/**
 * XLSBean data format component
 * 
 * @author Seiji Sogabe
 */
public class XLSBeansDataFormat extends ServiceSupport implements DataFormat {

    /**
     * Apache POI - Excel 2003形式(xls)。
     */
    public static final String TYPE_HSSF = "HSSF";
    
    /**
     * Apache POI - Excel 2007形式(xlsx)。
     */
    public static final String TYPE_XSSF = "XSSF";
    
    /**
     * Java Excel API - Excel 2003形式(xls)。
     */
    public static final String TYPE_JXL = "JXL";
    
    /**
     * Excelファイルから取得するJavaBeansのクラス。
     */
    private Class objectType;

    /**
     * Excelファイル形式。
     *  - HSSF Apache POI Excel 2003形式 (xls)
     *  - XSSF Apache POI Excel 2007形式 (xlsx)
     *  - JXL  Java Excel API Excel 2003形式 (xls)
     */
    private String bookType;
    
    /**
     * 正規化したExcelファイル形式。
     * 
     * 入力のbookTypeをXLSBeans内部の値に正規化した値。
     */
    private String normalizedBookType;
    
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

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
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
     * 必須項目のチェックを行います。
     * 
     * @throws Exception 初期処理に失敗
     */
    @Override
    protected void doStart() throws Exception {
        // objectTypeは必須
        ObjectHelper.notNull(objectType, "objectType");
        // bookTypeは必須
        ObjectHelper.notNull(bookType, "bookType");
        // bookTypeの正規化
        normalizedBookType = normalizeBookType(bookType);
        
        xlsBeans = new XLSBeans();
        // configは任意
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
        // do nothing
    }

    /**
     * bookTypeを正規化します。
     * 
     * @param from 入力のExcel形式(<code>null</code>は許容しない)
     * @return 正規化したbookType
     */
    private String normalizeBookType(String from) {
        String f = from.toUpperCase();
        switch (f) {
            case TYPE_HSSF:
                return WorkbookFinder.TYPE_HSSF;
            case TYPE_XSSF:
                return WorkbookFinder.TYPE_XSSF;
            case TYPE_JXL:
                return WorkbookFinder.TYPE_JXL;
        }
        throw new IllegalArgumentException(from + " is not supported.");
    }
    
    /**
     * JavaBeansをExcelファイルに変換します。
     * 
     * XLSBeansではサポートしていないため、このメソッドは未実装です。
     * 
     * @param exchange Exchange
     * @param graph JavaBeans
     * @param stream Excelファイル
     * @throws Exception 変換に失敗
     */
    @Override
    public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * ExcelファイルをJavaBeansに変換します。
     * 
     * @param exchange Exchange
     * @param stream Excelファイル
     * @return JavaBeans　
     * @throws Exception 変換に失敗
     */
    @Override
    public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {
        return xlsBeans.load(stream, objectType, normalizedBookType);
    }

}