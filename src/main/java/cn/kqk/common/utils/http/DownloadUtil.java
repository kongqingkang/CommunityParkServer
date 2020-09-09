package cn.kqk.common.utils.http;

import cn.kqk.common.exception.BaseException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * @author lhr
 * 2020/1/3 17:24
 * 下载
 */
public class DownloadUtil {
    public static void download(HttpServletResponse response, File file, String fileName) {
        try (InputStream fin = new FileInputStream(file); ServletOutputStream out = response.getOutputStream()) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/blob");

            //调用浏览器下载该文件
            // String fileName = file.getPath().substring(file.getPath().lastIndexOf(File.separator) + 1);
            response.setHeader("fileName", URLEncoder.encode(fileName, "UTF-8"));

            //缓冲区
            byte[] buffer = new byte[512];
            int bytesToRead;
            //循环读入文件内容到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(700, "下载文件出错");
        }
    }
}
