package com.createofd.utils;


import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *  给临时ofd文件 签章
 */
public class Signature {





    /**
     *        测试ofd签章
     * @param temOfdFilePath    签章前的文件路径
     * @param sf                省份名称
     * @param username          签章账号
     * @param url               请求地址
     * @param ofdFilePath       签章后文件路径
     */
    public static boolean OfdSign(String temOfdFilePath,String sf,String username,String url,String ofdFilePath) {
        Boolean bool = false;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(temOfdFilePath);
            System.out.println("签名前OFD的文件路径： "+temOfdFilePath);
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
                fos = new FileOutputStream(ofdFilePath);
                byte[] stampBytes = Base64Decoder.decode(resultObj.getStr("data"));
                fos.write(stampBytes);
                System.out.println("签章后odf文件路径为："+ofdFilePath);
                bool = true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("关闭资源");
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bool;
    }


    /**
     *        测试验章
     * @param OfdFilePath    签章后的文件路径
     * @param url               请求地址
     */
    public static String VerifyOfdSign(String OfdFilePath,String url) {
        String str = null;
        FileInputStream fis = null;
        try {
            //读取待验签文件
            fis = new FileInputStream(OfdFilePath);
            System.out.println("待验签的文件路径： "+OfdFilePath);
            byte[] fileBytes = new byte[fis.available()];
            fis.read(fileBytes);
            //组装验签数据
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("targetFile", Base64Encoder.encode(fileBytes));
            System.out.println("发送签章http请求：" + jsonObject.toString());
            String result = HttpUtil.post("http://" + url + "/bkwebserver/ofd/verify/net", jsonObject.toString());
            System.out.println("验签结果：" + result);
            str = Base64Encoder.encode(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return str;
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
        return str;
    }



}
