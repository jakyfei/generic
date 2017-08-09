package com.topsec.tsm.generic.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by yue_tf on 2017/3/28
 */
public class IOUtil {

    public static void write(String fileName, String content) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
            bw.write(content);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String read(String fileName) {
        BufferedReader br = null;
        StringBuffer content = new StringBuffer();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));
            if (br == null) {
                return "";
            }
            String tmp;
            while ((tmp = br.readLine()) != null) {
                content.append(tmp + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public static String readConfFile(String filePath) {
        String content = "";
        BufferedReader br = null;
        try {
            if (!StringUtils.isEmpty(filePath.trim())) {
                StringBuffer buffer = new StringBuffer();
                br = new BufferedReader(new InputStreamReader(IOUtil.class.getClassLoader().getResourceAsStream(filePath), "UTF-8"));
                String tmp;
                while ((tmp = br.readLine()) != null) {
                    buffer.append(tmp);
                }
                content = buffer.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != br) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return content;
    }

}
