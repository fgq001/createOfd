package com.createofd.test;


import com.suwell.sdk.invoice.InvoiceHelper;
import com.suwell.sdk.invoice.Result;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *  测试 创建odf
 */
public class TcreateOfd {


    public static void main(String[] args) throws MalformedURLException {
        InvoiceHelper.init(new URL("http://124.193.142.2:8090"), 1);
//        InvoiceHelper.init( new URL("http://172.16.2.157/"), 20);


//        String data = read("F:\\data\\5.xml");
        String data = read("E:/Aofd/original_invoice.xml");
        for (int i = 0; i < 1; i++) {
            new Thread("th-"+i) {
                int i=0;
                @Override
                public void run() {
//                    for (; ; ) {
                    long start = System.currentTimeMillis();
                    Result ret = InvoiceHelper.convertForm(data, "5000");
                    System.out.println(ret);
                    try {

                        InputStream in = ret.getOFDFile();
                        System.out.println(in.available());
                        //ret.getOFDFile().close();
//                            copy(in, new FileOutputStream("D://PJ2/"+this.getName()+"test2"+(i++)+".ofd"));
                        copy(in, new FileOutputStream("E:/Aofd/"+this.getName()+"test2"+(i++)+".ofd"));
                        //in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("############"+(System.currentTimeMillis()-start));
//                    }
                }
            }.start();
        }

        //InvoiceHelper.destroy();
    }


    public static String read(String fileName){
        //读取文件
        BufferedReader br = null;
        StringBuffer sb = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8")); //这里可以控制编码
            sb = new StringBuffer();
            String line = null;
            while((line = br.readLine()) != null) {
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

    public static void copy(InputStream in,OutputStream out) throws IOException {
        long start=System.currentTimeMillis();

        byte[] i=new byte[1024*200];

        int temp=0;
        //这是逐个字节读然后写入，逐个字节读然后写入�??-------------------------
//		while((temp=in.read())!=-1) {
//		      out.write((byte)(temp));
//
//		}
        //-------------------------------------------------------
        while ((temp=in.read(i))!=-1){//当一个一个读取的时�?�，read方法返回的是当前字节，读完返�?-1�?
            //当为read(byte[])的时�? 是一次�?�给给byte【�?�数组读入信息�?�返回�?�为当前数组的长度，当读完返�?-1
            out.write(i, 0,temp);
        }
        in.close();
        out.close();
        long end=System.currentTimeMillis();
        //System.out.println((end-start));
    }


}
