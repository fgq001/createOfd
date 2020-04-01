package com.createofd.controller;


import com.createofd.constant.FilePathConstant;
import com.createofd.constant.InvoiceConstant;
import com.createofd.utils.*;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.createofd.constant.FilePathConstant.TemXMLData;
import static com.createofd.constant.FilePathConstant.temOfdPatg;

@Controller
@RequestMapping("/ofd")
public class CreateOfdController {

    @ResponseBody
    @PostMapping("/createOfd")
    public JSONObject CreatePdf(HttpServletRequest req, HttpServletResponse resp) {
        long startTime = System.currentTimeMillis(); // 获取开始时间
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> sonMap = new HashMap<>();
        JSONObject jsonObject = null;


        try {
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html;charset=UTF-8");
            //获取生成ofd的XML数据
            String xmlContent = req.getParameter("xmlContent") == "" ? null : req.getParameter("xmlContent");
            if (xmlContent != null) {
                //创建存放日志的目录
                File dir = new File(FilePathConstant.LogFilePath);
                // 一、检查放置文件的文件夹路径是否存在，不存在则创建
                if (!dir.exists()) {
                    dir.mkdirs();// mkdirs创建多级目录
                }
//                System.out.println("接受的xmlContent内容为:" + xmlContent);
                //XML数据写入日志文件
                FileUtils.printLog(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "初始xml" + "\n\t" + xmlContent + "\n\t", FilePathConstant.LogFilePath + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "xmlContent.txt");
                String strfpdm = EWMbase64util.strfpdm(xmlContent); //获取xml数据中的发票代码
                String strfphm = EWMbase64util.strfphm(xmlContent); //获取xml数据中的发票号码
                String strkprq = EWMbase64util.strkprq(xmlContent); //获取xml数据中的开票日期
                String xmlPath = TemXMLData + strfpdm + "_" + strfphm + "_" + strkprq + ".xml"; //xml数据的文件路径
                //根据XML数据，创建xml文件
                boolean bxml = ForFile.createFile(xmlPath, xmlContent);
                if (bxml == true) {
                    //临时ofd文件路径
                    String temOfdPath = temOfdPatg + strfpdm + "_" + strfphm + "_" + strkprq + ".ofd";
                    //生成临时ofd文件
                    boolean bcreateOfd = CreateOfd.createOfdFile(xmlPath, temOfdPath, "5000");
                    if (bcreateOfd == true) {
                        String sf = FilePathConstant.sf;    // 省份  5000：重庆
                        String username = FilePathConstant.username;    // 签章账号
                        String url = FilePathConstant.url;    // 请求地址
                        String ofdFilePath = temOfdPath.replace(".ofd", "_temp.ofd");    // 签章后文件路径
                        //给临时ofd签名
                        boolean signOfd = Signature.OfdSign(temOfdPath, sf, username, url, ofdFilePath);
                        if (signOfd == true) {
                            //验证签章 生成的ofd文件
                            String VerifySign = Signature.VerifyOfdSign(ofdFilePath, url);
                            if (VerifySign != null) {
                                //验签成功
                                sonMap.put("生成验证签章后的odf路径", ofdFilePath);
                                sonMap.put("验证签章后的odf数据", VerifySign);
                                map.put("msg", "操作成功");
                                map.put("result", "SUCCESS");
                                map.put("code", "0");
                                map.put("rows", sonMap);
                                jsonObject = JSONObject.fromObject(map);
                            } else {
                                map.put("msg", InvoiceConstant.Verify_OFD_Sign);
                                map.put("result", "ERROR");
                                map.put("code", "500156");
                                map.put("rows", "");
                                jsonObject = JSONObject.fromObject(map);
                                return jsonObject;
                            }
                        } else {
                            map.put("msg", InvoiceConstant.SIGN_TEM_OFD);
                            map.put("result", "ERROR");
                            map.put("code", "500155");
                            map.put("rows", "");
                            jsonObject = JSONObject.fromObject(map);
                            return jsonObject;
                        }
                    } else {
                        map.put("msg", InvoiceConstant.CREATE_TEM_OFD);
                        map.put("result", "ERROR");
                        map.put("code", "500154");
                        map.put("rows", "");
                        jsonObject = JSONObject.fromObject(map);
                        return jsonObject;
                    }
                } else {
                    map.put("msg", InvoiceConstant.CREATE_XML);
                    map.put("result", "ERROR");
                    map.put("code", "500153");
                    map.put("rows", "");
                    jsonObject = JSONObject.fromObject(map);
                    return jsonObject;
                }
            } else {
                map.put("msg", InvoiceConstant.xmlContent);
                map.put("result", "ERROR");
                map.put("code", "500152");
                map.put("rows", "");
                jsonObject = JSONObject.fromObject(map);
                return jsonObject;
            }
        } catch (UnsupportedEncodingException e) {
            map.put("msg", InvoiceConstant.CREATE_OFD_EXCEPTION);
            map.put("result", "ERROR");
            map.put("code", "500151");
            map.put("rows", "");
            jsonObject = JSONObject.fromObject(map);
            e.printStackTrace();
            FileUtils.printLog(new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()) + "，异常，内容为" + "\n\t" + FileUtils.getTrace(e), FilePathConstant.LogFilePath + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "PdfExceptionLog.txt");
            return jsonObject;
        }
        return jsonObject;
    }


}
