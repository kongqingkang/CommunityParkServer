package cn.kqk.common.utils.http;

import cn.kqk.common.exception.BaseException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author lhr
 * 2020/1/5 4:31
 * 保存上传文件
 */
public class SaveUploadFile {
    /**
     * 保存上传的文件
     *
     * @param outFile 输出文件
     * @param file    文件
     * @return 文件
     */
    public static File save(File outFile, MultipartFile file) {
        File parentFile = outFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            //获取输出流
            OutputStream os = new FileOutputStream(outFile);
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream is = file.getInputStream();
            int len;
            byte[] b = new byte[1024];
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
            os.flush();
            os.close();
            is.close();
        } catch (Exception e) {
            throw new BaseException(800, "文件上传出错");
        }
        return outFile;
    }
}
