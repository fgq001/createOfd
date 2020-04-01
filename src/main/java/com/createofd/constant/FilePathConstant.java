package com.createofd.constant;

import com.createofd.entity.Porper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;

public class FilePathConstant {
	
	/**
	 * OFD日志文件 输出路径
	 */
	public static final String LogFilePath = "E:/onLineDataOfd/";

	//xml 数据文件路径
	public static final String TemXMLData ="E:/Aofd/Tem_XML/";
	
	/**
	 * 创建PDF相关路径 
	 */
	public static final String PdfFilePath = "E:/PDFFile/";

	// 原始XML数据的文件路径
	public static final String initXMLPath = "E:/Aofd/muban_XML/Ex_1.xml";

	//  请求创建ofd的地址
	public static final String OfdURL = "http://124.193.142.2:8090";

	//  创建临时（未签章）的ofd的路径
	public static final String temOfdPatg = "E:/Aofd/Tem_OFD/";


	//签章前的文件路径
	public static  String filePath = "E:/Aofd/tem_OFD/Ex_1_1585633955255.ofd";
	//签章后文件路径
	public static  String targetPath = filePath.replace(".ofd", "_temp.ofd");
	//待验章文件路径
//    static String targetFilePath = "C:\\Users\\Li\\Desktop\\workspace\\03-tempFile\\invoice_temp.ofd";
//    static String targetFilePath = "E:/Aofd/tem_OFD/Ex_4_1585622682123_temp.ofd";
	//省份名称
	public static  String sf = "重庆";
	//签章账号
	public static  String username = "8ecb744c-b9cc-47de-8d86-1cebfac38812";
//	public static  String username = "8ecb744c-b9cc-47de-8d86-1cebfac38812";
	//请求地址
	public static  String url = "222.128.104.189:8098";

}
