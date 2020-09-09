package cn.kqk.common.utils.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author wj
 * @version 1.0
 * @className ImageisBug
 * @description 图片是否损坏
 * @date 2020/01/14 9:45
 */
public class ImageisBug {

    public static boolean checkimage(String filepath) {
        boolean flag = false;
        try {
            File f = new File(filepath);
            FileInputStream fi = new FileInputStream(f);
            try {
                BufferedImage sourceImg = ImageIO.read(fi);//判断图片是否损坏
                int picWidth = sourceImg.getWidth(); //确保图片是正确的（正确的图片可以取得宽度）
                if (sourceImg != null && picWidth > 0) {
                    flag = true;
                }
            } catch (Exception e) {
                // TODO: handle exception
                fi.close();//关闭IO流才能操作图片
            } finally {
                fi.close();//最后一定要关闭IO流
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.toString());

        }

        return flag;
    }
}
