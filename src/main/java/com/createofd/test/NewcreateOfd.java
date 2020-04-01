package com.createofd.test;


import com.createofd.constant.FilePathConstant;
import com.suwell.sdk.invoice.InvoiceHelper;
import com.suwell.sdk.invoice.Result;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 测试 创建odf
 */
public class NewcreateOfd {


    public static void main(String[] args) throws MalformedURLException {
        //  请求创建ofd的地址
        InvoiceHelper.init(new URL(FilePathConstant.OfdURL), 1);

        // 原始XML数据的文件路径
        String data = read(FilePathConstant.initXMLPath);
        String dd = FilePathConstant.initXMLPath.substring(FilePathConstant.initXMLPath.length() - 8, FilePathConstant.initXMLPath.length() - 4);
        System.out.println(dd);

        long start = System.currentTimeMillis();
        //临时ofd文件路径
        String temOfdPath = FilePathConstant.temOfdPatg + dd + "_" + start + ".ofd";
        //设置省份模板
        Result ret = InvoiceHelper.convertForm(data, "5000");

        System.out.println(ret);
        try {
            InputStream in = ret.getOFDFile();
            System.out.println(in.available());
            copy(in, new FileOutputStream(temOfdPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("程序运行时间：" + (System.currentTimeMillis() - start));
        System.out.println("临时ofd路径："+temOfdPath);
    }


    public static String read(String fileName) {
        //读取文件
        BufferedReader br = null;
        StringBuffer sb = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8")); //这里可以控制编码
            sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String data = new String(sb); //StringBuffer ==> String
        System.out.println("数据==> " + data);
        return data;
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {
        long start = System.currentTimeMillis();

        byte[] i = new byte[1024 * 200];

        int temp = 0;
        //这是逐个字节读然后写入，逐个字节读然后写入�??-------------------------
//		while((temp=in.read())!=-1) {
//		      out.write((byte)(temp));
//
//		}
        //-------------------------------------------------------
        while ((temp = in.read(i)) != -1) {//当一个一个读取的时�?�，read方法返回的是当前字节，读完返�?-1�?
            //当为read(byte[])的时�? 是一次�?�给给byte【�?�数组读入信息�?�返回�?�为当前数组的长度，当读完返�?-1
            out.write(i, 0, temp);
        }
        in.close();
        out.close();
        long end = System.currentTimeMillis();
        //System.out.println((end-start));
    }


}
