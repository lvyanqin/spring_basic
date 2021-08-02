package lynn.util;

import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;
import lynn.util.exception.CommonException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

@Slf4j
public class ImageMergeUtil {

    public static BufferedImage resize(int targetWidth, int targetHeight,
                                       BufferedImage src) {
        double scaleW = (double) targetWidth / (double) src.getWidth();
        double scaleH = (double) targetHeight / (double) src.getHeight();

        double scale = scaleW < scaleH ? scaleW : scaleH;

        BufferedImage result = new BufferedImage((int) (src.getWidth() * scale),
                (int) (src.getHeight() * scale), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = result.createGraphics();
        g2d.drawImage(src, 0, 0, result.getWidth(), result.getHeight(), null);
        g2d.dispose();

        return result;
    }

    public static void mergeImageFile(String mainFilePath, String attachFilePath, String destFilePath){
    	InputStream is1 = null;
    	InputStream is2 = null;
        try {
			is1 = new FileInputStream(mainFilePath);
			is2 = new FileInputStream(attachFilePath);
	        BufferedImage bi1 = ImageIO.read(is1);
	        BufferedImage bi2 = ImageIO.read(is2);
	        mergeImage(bi1, bi2, destFilePath);
        } catch (Exception e) {
            log.error("图片合并失败！", e);
            throw new CommonException("图片合并失败！");
        } finally {
        	FileUtil.close(is1);
        	FileUtil.close(is2);
        }
    }
    
    public static void mergeImageFile(String mainFilePath, InputStream attachStream, String destFilePath){
    	InputStream is1 = null;
        try {
			is1 = new FileInputStream(mainFilePath);
	        BufferedImage bi1 = ImageIO.read(is1);
	        BufferedImage bi2 = ImageIO.read(attachStream);
        } catch (Exception e) {
            log.error("图片合并失败！", e);
            throw new CommonException("图片合并失败！");
        } finally {
        	FileUtil.close(is1);
        }
    }
    
    public static void mergeImageStream(InputStream mainStream, String attachFilePath, String destFilePath){
    	InputStream is2 = null;
        try {
	        BufferedImage bi1 = ImageIO.read(mainStream);
	        is2 = new FileInputStream(attachFilePath);
	        BufferedImage bi2 = ImageIO.read(is2);
	        mergeImage(bi1, bi2, destFilePath);
        } catch (Exception e) {
        	log.error("图片合并失败！", e);
            throw new CommonException("图片合并失败！");
        } finally {
        	FileUtil.close(is2);
        }
    }

    public static void mergeImageStream(InputStream mainStream, InputStream attachStream, OutputStream destStream){

        try {
	        BufferedImage bi1 = ImageIO.read(mainStream);
	        BufferedImage bi2 = ImageIO.read(attachStream);
	        mergeImage(bi1, bi2, destStream);
        } catch (Exception e) {
        	log.error("图片合并失败！", e);
            throw new CommonException("图片合并失败！");
        }
    }



    public static void mergeImageURL(String mainUrl, String attachUrl, String destFilePath){
        try{
            BufferedImage bi1 = ImageIO.read(new URL(mainUrl));
            BufferedImage bi2 = ImageIO.read(new URL(attachUrl));
            mergeImage(bi1, bi2, destFilePath);
        }catch (Exception e){
        	log.error("图片合并失败！", e);
            throw new CommonException("图片合并失败！");
        }
    }
    
    public static void mergeImageURL(String mainUrl, InputStream attachStream, String destFilePath){
        try{
            BufferedImage bi1 = ImageIO.read(new URL(mainUrl));
            BufferedImage bi2 = ImageIO.read(attachStream);
            mergeImage(bi1, bi2, destFilePath);
        }catch (Exception e){
        	log.error("图片合并失败！", e);
            throw new CommonException("图片合并失败！");
        }
    }

    public static void mergeImage(BufferedImage mainImage, BufferedImage attachImage, String destFilePath){
        try {
            attachImage = resize(182, 182, attachImage);
            Graphics g = mainImage.getGraphics();
            g.drawImage(attachImage, mainImage.getWidth() - attachImage.getWidth() - 37,
                    mainImage.getHeight() - attachImage.getHeight() - 25, null);
            g.dispose();

            File outputfile = new File(destFilePath);
            //此处要写PNG否则可能会出现遮罩层
            ImageIO.write(mainImage, "png", outputfile);
        } catch (Exception e) {
        	log.error("图片合并失败！", e);
            throw new CommonException("图片合并失败！");
        }
    }

    public static void mergeImage(BufferedImage mainImage, BufferedImage attachImage, OutputStream destStream){
        try {
            attachImage = resize(182, 182, attachImage);
            Graphics g = mainImage.getGraphics();
            g.drawImage(attachImage, mainImage.getWidth() - attachImage.getWidth() - 380,
                    mainImage.getHeight() - attachImage.getHeight() -270, null);
            g.dispose();

//            File outputfile = new File(destFilePath);
//            //此处要写PNG否则可能会出现遮罩层
//            ImageIO.write(mainImage, "png", outputfile);
            ImageIO.write(mainImage, "png", destStream);
        } catch (Exception e) {
        	log.error("图片合并失败！", e);
            throw new CommonException("图片合并失败！");
        }
    }

}