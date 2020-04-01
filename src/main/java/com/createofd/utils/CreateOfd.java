package com.createofd.utils;

import com.createofd.constant.FilePathConstant;
import com.suwell.sdk.invoice.InvoiceHelper;
import com.suwell.sdk.invoice.Result;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import static com.createofd.constant.FilePathConstant.TemXMLData;
import static com.createofd.constant.FilePathConstant.temOfdPatg;

/**
 * 根据xml文件数据  创建ofd文件
 */
public class CreateOfd {


    /**
     *
     * @param xmlPath       xml数据的文件路径
     * @param temOfdPath    临时ofd文件路径
     * @param sf            省份  5000:重庆
     */
    public static boolean createOfdFile(String xmlPath,String temOfdPath,String sf) {
        Boolean bool = false;
        try {
            File dir = new File(temOfdPatg);
            // 一、检查放置文件的文件夹路径是否存在，不存在则创建
            if (!dir.exists()) {
                dir.mkdirs();// mkdirs创建多级目录
            }

            //  请求创建ofd的地址
            InvoiceHelper.init(new URL(FilePathConstant.OfdURL), 1);

            // 原始XML数据的文件路径
            String data = read(xmlPath);
            long start = System.currentTimeMillis();
            //临时ofd文件路径
//            temOfdPath = FilePathConstant.temOfdPatg + dd + "_" + start + ".ofd";
            //设置省份模板
            Result ret = InvoiceHelper.convertForm(data, sf);
//            Result ret = InvoiceHelper.convertForm(data, "5000");

            System.out.println("Result: "+ret);

            InputStream in = ret.getOFDFile();
//            System.out.println(in.available());
            copy(in, new FileOutputStream(temOfdPath));

            System.out.println("程序运行时间：" + (System.currentTimeMillis() - start)+"ms");
            System.out.println("临时ofd路径：" + temOfdPath);
            bool = true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return bool;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return bool;
        } catch (IOException e) {
            e.printStackTrace();
            return bool;
        }
        return bool;
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
    //        System.out.println("数据==> " + data);
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
