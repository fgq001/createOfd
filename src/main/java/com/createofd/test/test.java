package com.createofd.test;

import com.createofd.entity.Porper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class test {
    //签名pdf路径
    @Autowired
    private Porper porper;


    //签名pdf路径
    @Value("${crm.aa}")
    private String qmPdfPath;

    public void dd(){
//        System.out.println();
//        String filePath = qmPdfPath;
//        String path = porper.getFpPdfPath();
        System.out.println("path: "+qmPdfPath);
    }

    public static void main(String[] args) {
        test d = new test();
        d.dd();
        System.out.println();
    }

}
