package com.createofd.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.UUID;

import static com.createofd.constant.FilePathConstant.TemXMLData;


/**
 *  创建文件的工具类
 */
public class ForFile {

    //生成临时odf的XML文件路径
//    private static String TemXMLpath = TemXMLData;

    //文件路径+名称
//    private static String filenameTemp;

//    public static final String TemXMLpath ="E:/Aofd/Tem_XML/";

    /**
     * 创建文件
     * @param fileName  文件名称
     * @param filecontent   文件内容
     * @return  是否创建成功，成功则返回true
     */
    public static boolean createFile(String fileName,String filecontent){

        File dir = new File(TemXMLData);
        // 一、检查放置文件的文件夹路径是否存在，不存在则创建
        if (!dir.exists()) {
            dir.mkdirs();// mkdirs创建多级目录
        }

        Boolean bool = false;
//        String filenameTemp = fileName+".xml";//文件路径+名称+文件类型
        File file = new File(fileName);
        try {

            //如果文件不存在，则创建新的文件
            if(!file.exists()){
                file.createNewFile();
                //创建文件成功后，写入内容到文件里
                writeFileContent(fileName, filecontent);
                bool = true;
                System.out.println("创建新文件成功,文件名为："+fileName);
            } else {
                ForFile.delFile(fileName);
                file.createNewFile();
                //创建文件成功后，写入内容到文件里
                writeFileContent(fileName, filecontent);
                System.out.println("重新创建原有文件成功,文件名为："+fileName);
                bool = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return bool;
        }
        return bool;
    }

    /**
     * 向文件中写入内容
     * @param filepath 文件路径与名称
     * @param newstr  写入的内容
     * @return
     * @throws IOException
     */
    public static boolean writeFileContent(String filepath,String newstr) throws IOException{
        Boolean bool = false;
        String filein = newstr+"\r\n";//新写入的行，换行
        String temp  = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos  = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();

            //文件原有内容
            for(int i=0;(temp =br.readLine())!=null;i++){
                buffer.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }

    /**
     * 删除文件
     * @param fileName 文件名称
     * @return
     */
    public static boolean delFile(String fileName){
        Boolean bool = false;
//        filenameTemp = fileName+".xml";
        File file  = new File(fileName);
        try {
            if(file.exists()){
                file.delete();
                bool = true;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return bool;
    }


    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        createFile(uuid+"myfile", "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "\n" +
                "<eInvoice xmlns:fp=\"http://www.edrm.org.cn/schema/e-invoice/2019\" Version=\"1.0\">\n" +
                "  <fp:DocID>01</fp:DocID>\n" +
                "  <fp:InvoiceCode>011001900311</fp:InvoiceCode>\n" +
                "  <fp:InvoiceNo>21010038</fp:InvoiceNo>\n" +
                "  <fp:TypeCode>0</fp:TypeCode>\n" +
                "  <fp:IssueDate>2020年04月07日</fp:IssueDate>\n" +
                "  <fp:InvoiceCheckCode>01731581649436515692</fp:InvoiceCheckCode>\n" +
                "  <fp:MachineNo>016000003072</fp:MachineNo>\n" +
                "  <fp:TaxControlCode>00*9&gt;5*32553-90&gt;13471*&lt;7-8/56&lt;7885979++21/3/6&lt;-97500&lt;0+937290651*41&lt;&lt;904/3+5*&lt;68717-6*++73//4&lt;0+5*01524019*9&lt;0&lt;1</fp:TaxControlCode>\n" +
                "  <fp:Buyer>\n" +
                "    <fp:BuyerName>科游迈利有限公司</fp:BuyerName>\n" +
                "    <fp:BuyerTaxID>9150010807MGZHI002</fp:BuyerTaxID>\n" +
                "    <fp:BuyerAddrTel>北京海淀区西钓鱼台嘉园北门13392342520</fp:BuyerAddrTel>\n" +
                "    <fp:BuyerFinancialAccount>北京农商行32445333322211</fp:BuyerFinancialAccount>\n" +
                "  </fp:Buyer>\n" +
                "  <fp:Tpvn/>\n" +
                "  <fp:Seller>\n" +
                "    <fp:SellerName>北京市有朋自远方来茶艺餐馆</fp:SellerName>\n" +
                "    <fp:SellerTaxID>9111010807MGZHI002</fp:SellerTaxID>\n" +
                "    <fp:SellerAddrTel>北京市海淀区颐和园西路一亩园11号</fp:SellerAddrTel>\n" +
                "    <fp:SellerFinancialAccount>建设银行625555888900998711</fp:SellerFinancialAccount>\n" +
                "    <fp:Issuea1/>\n" +
                "    <fp:Issuea2/>\n" +
                "  </fp:Seller>\n" +
                "  <fp:TaxInclusiveTotalAmount>318.00</fp:TaxInclusiveTotalAmount>\n" +
                "  <fp:Note/>\n" +
                "  <fp:InvoiceClerk>任盈盈</fp:InvoiceClerk>\n" +
                "  <fp:Payee>张一诺</fp:Payee>\n" +
                "  <fp:Checker>张嘉诺</fp:Checker>\n" +
                "  <fp:TaxTotalAmount>18.00</fp:TaxTotalAmount>\n" +
                "  <fp:TaxExclusiveTotalAmount>300.00</fp:TaxExclusiveTotalAmount>\n" +
                "  <fp:GraphCode>01,10,011001900311,21010038,300,20200407,01731581649436515692,53F1,</fp:GraphCode>\n" +
                "  <fp:InvoiceSIA1/>\n" +
                "  <fp:InvoiceSIA2/>\n" +
                "  <fp:Signature>MIHaBgoqgRzPVQYBBAICoIHLMIHIAgEBMQ4wDAYIKoEcz1UBgxEFADAMBgoqgRzPVQYBBAIBMYGkMIGhAgEBMDYwLDELMAkGA1UEBhMCQ04xHTAbBgNVBAMMFOeojuWKoea1i+ivlUNBMihTTTIpAgYDAAAAPT8wDAYIKoEcz1UBgxEFADAMBggqgRzPVQGDdQUABEgwRgIhAImqTf/9gEPGcOY7AmVBtFFEp6y5brQm4QKJ+xUibHIxAiEAkaR4upoxzhEl/kP50M0aZjDtSu9t+VDa73kJrnde8eg=</fp:Signature>\n" +
                "  <fp:GoodsInfos>\n" +
                "    <fp:GoodsInfo>\n" +
                "      <fp:Item>*餐饮服务*餐费</fp:Item>\n" +
                "      <fp:Specification/>\n" +
                "      <fp:MeasurementDimension>次</fp:MeasurementDimension>\n" +
                "      <fp:Price>25</fp:Price>\n" +
                "      <fp:Quantity>4</fp:Quantity>\n" +
                "      <fp:Amount>100.00</fp:Amount>\n" +
                "      <fp:TaxScheme>6%</fp:TaxScheme>\n" +
                "      <fp:TaxAmount>6.00</fp:TaxAmount>\n" +
                "    </fp:GoodsInfo>\n" +
                "    <fp:GoodsInfo>\n" +
                "      <fp:Item>*餐饮服务*餐费</fp:Item>\n" +
                "      <fp:Specification/>\n" +
                "      <fp:MeasurementDimension>次</fp:MeasurementDimension>\n" +
                "      <fp:Price>25</fp:Price>\n" +
                "      <fp:Quantity>4</fp:Quantity>\n" +
                "      <fp:Amount>100.00</fp:Amount>\n" +
                "      <fp:TaxScheme>6%</fp:TaxScheme>\n" +
                "      <fp:TaxAmount>6.00</fp:TaxAmount>\n" +
                "    </fp:GoodsInfo>\n" +
                "    <fp:GoodsInfo>\n" +
                "      <fp:Item>*餐饮服务*餐费</fp:Item>\n" +
                "      <fp:Specification/>\n" +
                "      <fp:MeasurementDimension>次</fp:MeasurementDimension>\n" +
                "      <fp:Price>25</fp:Price>\n" +
                "      <fp:Quantity>4</fp:Quantity>\n" +
                "      <fp:Amount>100.00</fp:Amount>\n" +
                "      <fp:TaxScheme>6%</fp:TaxScheme>\n" +
                "      <fp:TaxAmount>6.00</fp:TaxAmount>\n" +
                "    </fp:GoodsInfo>\n" +
                "  </fp:GoodsInfos>\n" +
                "</eInvoice>\n");
    }
}
