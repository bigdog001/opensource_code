package com.ok.tool;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.Iterator;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-21
 * Time: 0:02:44
 */
public class ImgUtil {
    private static Logger logger = Logger.getLogger(ImgUtil.class);

    public static void readUsingImageReader(String InputFile, String OutputFile, Rectangle rect) throws IOException {
        // 取得图片读入器
        Iterator readers = ImageIO.getImageReadersByFormatName("jpg");
        //   System.out.println(readers);
        ImageReader reader = (ImageReader) readers.next();
        //  System.out.println(reader);
        // 取得图片读入流
        InputStream source = new FileInputStream(InputFile);
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        // 图片参数
        ImageReadParam param = reader.getDefaultReadParam();
        int imageIndex = 0;
        // int half_width = reader.getWidth(imageIndex)/2;
        //  int half_height = reader.getHeight(imageIndex)/2;
        // Rectangle rect = new Rectangle(60, 60, half_width, half_height);
        // Rectangle rect = new Rectangle(60, 60, 500, 500);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        OutputStream os = new FileOutputStream(OutputFile);
        ImageIO.write(bi, "jpg", os);
        source.close();
        os.close();

    }

    public static boolean CutPic(String inputpath, String OutputFile, String picparm) {
        if ("".equals(picparm) || picparm == null) {
            logger.error("用戶已经上传的图片：" + inputpath + " 的裁剪参数为空！");
            return false;
        }
        picparm = picparm.trim();
        picparm = picparm.replace("rect(", "");
        picparm = picparm.replace(")", "");
        picparm = picparm.replace("px", "");
        String[] recs_ = picparm.split(",");
        if (recs_.length != 4) {
            logger.error("用戶已经上传的图片：" + inputpath + " 的裁剪参数不合法！其长度为" + recs_.length);
            return false;
        }
        int x = Integer.valueOf(recs_[3].substring(0, recs_[3].indexOf(".")).trim());
        int y = Integer.valueOf(recs_[0].substring(0, recs_[0].indexOf(".")).trim());
        int width = Integer.valueOf(recs_[1].substring(0, recs_[1].indexOf(".")).trim());
        width = width - x;
        int heigth = Integer.valueOf(recs_[2].substring(0, recs_[2].indexOf(".")).trim());
        heigth = heigth - y;
        Rectangle rt = new Rectangle(x, y, width, heigth);
        logger.error("用戶已经上传的图片：" + inputpath + " 的裁剪参数为：(x,y)(" + x + "," + y + "),width=" + width + ",heigth=" + heigth);
        logger.error("调用裁剪程序，开始裁剪用戶已经上传的图片：" + inputpath);
        try {
            readUsingImageReader(inputpath, OutputFile, rt);
        } catch (IOException e) {
            logger.error(e.getStackTrace() + "---------");
            return false;
        }
        return true;
    }

    public static void saveImageAsJpg(String fromFileStr, String saveToFileStr, int width, int hight)
            throws Exception {
        System.out.println("fromFileStr:"+fromFileStr);
        System.out.println("saveToFileStr:"+saveToFileStr);
        BufferedImage srcImage;
        // String ex = fromFileStr.substring(fromFileStr.indexOf("."),fromFileStr.length());
        String imgType = "JPEG";
        if (fromFileStr.toLowerCase().endsWith(".png")) {
            imgType = "PNG";
        }
        // System.out.println(ex);
        File saveFile = new File(saveToFileStr);
        File fromFile = new File(fromFileStr);
        srcImage = ImageIO.read(fromFile);
        if (width > 0 || hight > 0) {
            srcImage = resize(srcImage, width, hight);
        }
        ImageIO.write(srcImage, imgType, saveFile);

    }

    public static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
        // targetW，targetH分别表示目标长和宽
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        //这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
        //则将下面的if else语句注释即可
        if (sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) { //handmade
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else
            target = new BufferedImage(targetW, targetH, type);
        Graphics2D g = target.createGraphics();
        //smoother than exlax:
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    public static void main(String argv[]) {
        try {
            //参数1(from),参数2(to),参数3(宽),参数4(高)
            saveImageAsJpg("e:\\banner2.jpg",
                    "e:\\ss.jpg",
                    500, 500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    }
