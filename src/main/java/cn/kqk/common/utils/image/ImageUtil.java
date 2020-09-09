package cn.kqk.common.utils.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lhr
 * 2019/12/21 21:20
 * 图片工具类
 */
public class ImageUtil {
    /**
     * 缩放PNG图片
     *
     * @param originImage  源图片
     * @param outputWidth  缩放宽度
     * @param outputHeight 缩放高度
     * @param proportion   是否是等比缩放
     * @param margin       边距
     * @return 缩放后的图片
     */
    public static BufferedImage resizePng(BufferedImage originImage, int outputWidth, int outputHeight, boolean proportion, int margin) {
        int newWidth;
        int newHeight;
        int trueHeight;
        int trueWidth;
        // 判断是否是等比缩放
        if (proportion) {
            // 为等比缩放计算输出的图片宽度及高度
            double rate1;
            double rate2;
            // 宽度小于或等于0时，按原比例计算
            if (outputWidth <= 0) {
                rate1 = 1;
            } else {
                rate1 = ((double) originImage.getWidth(null)) / (double) outputWidth + 0.1;
            }
            // 高度小于等于0时，按原比例计算
            if (outputHeight <= 0) {
                rate2 = 1;
            } else {
                rate2 = ((double) originImage.getHeight(null)) / (double) outputHeight + 0.1;
            }
            // 根据缩放比率小的进行缩放控制
            double rate = Math.max(rate1, rate2);
            newWidth = (int) (((double) originImage.getWidth(null)) / rate);
            newHeight = (int) (((double) originImage.getHeight(null)) / rate);
        } else {
            // 输出的图片宽度
            newWidth = outputWidth <= 0 ? originImage.getWidth(null) : outputWidth;
            // 输出的图片高度
            newHeight = outputHeight <= 0 ? originImage.getHeight(null) : outputHeight;
        }

        trueHeight = newHeight + (margin * 2);
        trueWidth = newWidth + (margin * 2);

        // 缩放图片
        BufferedImage to = new BufferedImage(trueWidth, trueHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = to.createGraphics();
        to = g2d.getDeviceConfiguration().createCompatibleImage(trueWidth, trueHeight, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = to.createGraphics();
        @SuppressWarnings("static-access")
        Image from = originImage.getScaledInstance(newWidth, newHeight, originImage.SCALE_AREA_AVERAGING);
        g2d.drawImage(from, margin, margin, null);
        g2d.dispose();
        return to;
    }

    /**
     * 拼接图片
     *
     * @param images   图片列表
     * @param isX      是否横向拼接（true-横向，false-竖向）
     * @param isCenter 是否居中拼接（true-居中，false-不居中）
     * @return 拼接完成的图片
     */
    public static BufferedImage appendImage(List<BufferedImage> images, boolean isX, boolean isCenter) {
        // 基础验证，图片列表不能为空，图片列表数量不能为0
        if (images == null || images.size() == 0) {
            return null;
        }

        // 定义变量
        boolean isFirstPng = true;
        BufferedImage outputImg = null;
        int outputImgW = 0;
        int outputImgH = 0;
        int maxWidth = 0;
        int maxHeight = 0;

        // 如果居中，算出最大图片的宽高
        if (isCenter) {
            for (BufferedImage image : images) {
                maxWidth = Math.max(image.getWidth(), maxWidth);
                maxHeight = Math.max(image.getHeight(), maxHeight);
            }
        }

        // 循环拼接图片
        for (BufferedImage image : images) {
            if (isFirstPng) {
                // 第一次循环，将当前图片直接赋值为输出图片
                isFirstPng = false;
                outputImg = image;
                outputImgW = outputImg.getWidth();
                outputImgH = outputImg.getHeight();

                // 如果居中，将第一张图片设置偏移量
                if (isCenter) {
                    Graphics2D g2d = image.createGraphics();
                    BufferedImage imageNew;
                    // 创建空白背景图片，如果是横向拼接，空图片的高度设为所有图片的最大高度
                    //                  如果是竖向拼接，空图片的宽度设为所有图片的最大宽度
                    if (isX) {
                        imageNew = g2d.getDeviceConfiguration().createCompatibleImage(outputImgW, maxHeight, Transparency.TRANSLUCENT);
                    } else {
                        imageNew = g2d.getDeviceConfiguration().createCompatibleImage(maxWidth, outputImgH, Transparency.TRANSLUCENT);
                    }
                    g2d.dispose();
                    g2d = imageNew.createGraphics();
                    int oldImgW = outputImg.getWidth();
                    int oldImgH = outputImg.getHeight();
                    // 如果是横向拼接，设置Y轴偏移量，如果是纵向拼接，设置X轴偏移量
                    if (isX) {
                        g2d.drawImage(outputImg, 0, (maxHeight / 2) - (outputImgH / 2), oldImgW, oldImgH, null);
                    } else {
                        g2d.drawImage(outputImg, (maxWidth / 2) - (outputImgW / 2), 0, oldImgW, oldImgH, null);
                    }
                    g2d.dispose();
                    outputImg = imageNew;
                }
            } else {
                // 后面的循环，在输出图片基础上进行拼接
                // 获取图片宽高
                int appendImgW = image.getWidth();
                int appendImgH = image.getHeight();
                int offset = 0;

                // 判断是横向还是纵向拼接,计算偏移和输出图片大小
                if (isX) {
                    if (isCenter) {
                        offset = (maxHeight / 2) - (appendImgH / 2);
                    }
                    outputImgW = outputImgW + appendImgW;
                    outputImgH = Math.max(outputImgH, appendImgH);
                } else {
                    if (isCenter) {
                        offset = (maxWidth / 2) - (appendImgW / 2);
                    }
                    outputImgW = Math.max(outputImgW, appendImgW);
                    outputImgH = outputImgH + appendImgH;
                }

                // 拼接图片
                Graphics2D g2d = outputImg.createGraphics();
                BufferedImage imageNew = g2d.getDeviceConfiguration().createCompatibleImage(outputImgW, outputImgH, Transparency.TRANSLUCENT);
                g2d.dispose();
                g2d = imageNew.createGraphics();
                int oldImgW = outputImg.getWidth();
                int oldImgH = outputImg.getHeight();
                g2d.drawImage(outputImg, 0, 0, oldImgW, oldImgH, null);
                if (isX) {
                    g2d.drawImage(image, oldImgW, offset, appendImgW, appendImgH, null);
                } else {
                    g2d.drawImage(image, offset, oldImgH, appendImgW, appendImgH, null);
                }
                g2d.dispose();
                outputImg = imageNew;
            }
        }
        return outputImg;
    }

    /**
     * 将图片写入到本地文件
     *
     * @param path  文件路径
     * @param image 图片
     */
    private static void writeImageLocal(String path, BufferedImage image) {
        if (path != null && image != null) {
            try {
                File file = new File(path);
                ImageIO.write(image, "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成组合图片
     *
     * @param imagePathList 图片路径列表
     * @param outputPath    输出路径
     * @param oneLineLimit  每行图片数量限制
     * @param lengthLimit   每个图片的边长限制（横向就是宽度限制，纵向就是高度限制）
     * @param margin        单个图片的边距
     * @param isX           是否横向排列（true-横向，false-竖向）
     * @param inlineCenter  是否单列居中
     * @param lineCenter    是否列居中
     */
    public static void generateComposedImage(List<String> imagePathList, String outputPath, int oneLineLimit, int lengthLimit, int margin, boolean isX, boolean inlineCenter, boolean lineCenter) {
        // 基础验证：图片路径列表不可为空，图片路径列表数量不可为0，输出路径不可为空，每行限制不可小于或等于0
        if (imagePathList == null || imagePathList.size() == 0 || outputPath == null || oneLineLimit <= 0) {
            return;
        }

        // 已拼接完成的行图片列表
        List<BufferedImage> lineImageList = new ArrayList<>();
        // 获取图片总数
        int imageCount = imagePathList.size();
        // 计算出行数
        int lineCount = (imageCount % oneLineLimit) > 0 ? (imageCount / oneLineLimit) + 1 : imageCount / oneLineLimit;
        // 计算出最后一行的图片数
        int lastLineCount = (imageCount % oneLineLimit) > 0 ? imageCount % oneLineLimit : oneLineLimit;
        // 记录当前所有循环走的次数
        int currentIndex = 0;
        try {
            // 循环行
            for (int i = 0; i < lineCount; i++) {
                // 当前行的图片列表
                List<BufferedImage> oneLineImageList = new ArrayList<>();
                // 计算出当前行的图片数量
                int currentLineCount = (i + 1) == lineCount ? lastLineCount : oneLineLimit;
                // 循环列
                for (int j = 0; j < currentLineCount; j++) {
                    BufferedImage image = ImageIO.read(new File(imagePathList.get(currentIndex)));
                    image = resizePng(image, lengthLimit, 0, true, margin);
                    // 读取图片文件并添加到列表中
                    oneLineImageList.add(image);
                    currentIndex++;
                }
                // 拼接图片并添加到列表中
                lineImageList.add(appendImage(oneLineImageList, isX, inlineCenter));
            }
            // 拼接图片并输出到指定路径
            writeImageLocal(outputPath, appendImage(lineImageList, !isX, lineCenter));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addTest(String[] images, int oneLineLimit, int lengthLimit, int margin, boolean isX, boolean inlineCenter, boolean lineCenter) {
        long time = System.currentTimeMillis();
        String basePath = "C:\\Users\\wealthz\\Desktop\\图片拼接测试\\";
        String type = ".png";
        String outName = images.length + "图-每行" + oneLineLimit + "张-每张" + lengthLimit + "-边距" + margin + (isX ? "-横排" : "-竖排") + (inlineCenter ? "-行内居中" : "-行内不居中") + (lineCenter ? "-行间居中" : "-行间不居中");
        List<String> pathList = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            pathList.add(basePath + images[i] + type);
        }
        generateComposedImage(pathList, basePath + outName + type, oneLineLimit, lengthLimit, margin, isX, inlineCenter, lineCenter);
        System.out.println(outName + "   生成时间:" + (System.currentTimeMillis() - time) + "毫秒");
    }

    public static void main(String[] args) {
        // String[] files = new String[]{"C:\\Users\\wealthz\\Desktop\\图片拼接测试\\1.png","C:\\Users\\wealthz\\Desktop\\图片拼接测试\\2.png"};
        // changeSize(100,100,files[0]);
        // changeSize(100,100,files[1]);
        // mergeImage(files,2,"C:\\Users\\wealthz\\Desktop\\图片拼接测试\\merge1.png");
        try {
            long time = System.currentTimeMillis();
            int imageCount = 0;
            for (int i = 0; i < 7; i++) {
                String[] image = new String[i + 1];
                int[] paramLimit = {i + 1, 2, 2, 2, 2};
                int kenen = 1;
                for (int value : paramLimit) {
                    kenen = kenen * value;
                }
                for (int j = 0; j < i + 1; j++) {
                    image[j] = "" + (j + 1);
                }
                int[] param = {0, 0, 0, 0, 0};
                for (int j = 0; j < kenen; j++) {
                    int oneLineLimit = param[0] + 1;
                    int lengthLimit = 500;
                    int margin = param[1] == 0 ? 0 : 20;
                    boolean isX = param[2] == 0;
                    boolean inLineCenter = param[3] == 0;
                    boolean lineCenter = param[4] == 0;
                    addTest(image, oneLineLimit, lengthLimit, margin, isX, inLineCenter, lineCenter);
                    param[param.length - 1]++;
                    for (int k = param.length - 1; k >= 0; k--) {
                        if (param[k] == paramLimit[k] && k != 0) {
                            param[k] = 0;
                            param[k - 1]++;
                        }
                    }
                    imageCount++;
                }
            }
            System.out.println("共生成了" + imageCount + "张图片，花费时间" + (System.currentTimeMillis() - time) + "毫秒");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
