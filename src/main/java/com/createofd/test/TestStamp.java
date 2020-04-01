package com.createofd.test;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 测试签章验章
 * @author kangpeng
 */
public class TestStamp {

    //待签章文件路径
//    static String filePath = "C:\\Users\\Li\\Desktop\\workspace\\03-tempFile\\invoice.ofd";
//    static String filePath = "C:\\Users\\Li\\Desktop\\2019-12-28\\test.ofd";
//    static String filePath = "C:\\Users\\Li\\Desktop\\workspace\\03-tempFile\\11100001001152543405.ofd";
    static String filePath = "E:/Aofd/tem_OFD/011001900311_21010038_2020年04月07日.ofd";
    //签章后文件路径
    static String targetPath = filePath.replace(".ofd", "_temp.ofd");
//    static String targetPath = "E:/Aofd/Tem_OFD/011001900311_21010038_2020年04月07日_temp.ofd";
    //待验章文件路径
//    static String targetFilePath = "C:\\Users\\Li\\Desktop\\workspace\\03-tempFile\\invoice_temp.ofd";
    static String targetFilePath = "E:/Aofd/tem_OFD/Ex_2_1585710083998.ofd";
    //省份名称
    static String sf = "重庆";
    //签章账号
//    static String username = "8ecb744c-b9cc-47de-8d86-1cebfac38812";
//    static String username = "7e4de93b-33a9-4258-9be7-5c10b91e7e08";
    static String username = "8ecb744c-b9cc-47de-8d86-1cebfac38812";
    //请求地址
    static String url = "222.128.104.189:8098";

    public static void main(String[] args) {

        testOfdSign();
        testVerifyOfdSign();
    }

    /**
     * 测试ofd签章
     */
    public static void testOfdSign() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(filePath);
            System.out.println("签名前OFD的文件路径： "+filePath);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            //组织申请制章的数据
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("signFileBytes", Base64Encoder.encode(bytes));
            jsonObject.put("sf", sf);
            jsonObject.put("username", username);
            System.out.println("发送签章http请求：" + jsonObject.toString());
            String result = HttpUtil.post("http://" + url + "/bkwebserver/ofd/sign/net", jsonObject.toString());
            JSONObject resultObj = new JSONObject(result);
            String code = resultObj.getStr("code");
            if (!"0".equals(code)) {
                System.out.println("签章失败：" + result);
            } else {
                System.out.println("签章成功，并将数据写入目标文件:" + result);
                fos = new FileOutputStream(targetPath);
                byte[] stampBytes = Base64Decoder.decode(resultObj.getStr("data"));
                fos.write(stampBytes);
                System.out.println("签章后odf文件路径为："+targetPath);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                System.out.println("关闭资源");
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 测试验章
     */
    public static void testVerifyOfdSign() {

        FileInputStream fis = null;
        try {
            //读取待验签文件
            fis = new FileInputStream(targetPath);
            System.out.println("待验签的文件路径： "+targetPath);
            byte[] fileBytes = new byte[fis.available()];
            fis.read(fileBytes);
            //组装验签数据
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("targetFile", Base64Encoder.encode(fileBytes));
            System.out.println("发送签章http请求：" + jsonObject.toString());
            String result = HttpUtil.post("http://" + url + "/bkwebserver/ofd/verify/net", jsonObject.toString());
            System.out.println("验签结果：" + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("关闭资源");
                if (fis != null) {
                    fis.close();
                }
                /*if (fos != null) {
                    fos.close();
                }*/
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
