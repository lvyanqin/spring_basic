package lynn.util.image;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @since 2019-09-26
 */
@Slf4j
public class Base64Util {

    public static final String UTL_HTTP_PRE = "http://";

    public static final String UTL_HTTPS_PRE = "https://";

    /**
     * 图片转化成base64字符串
     * @param imgPath
     * @return
     */
    public static String getImageStr(String imgPath){
        if(StringUtils.isBlank(imgPath)){
            return "";
        }
        String encode = "";
        if(imgPath.startsWith(UTL_HTTPS_PRE) || imgPath.startsWith(UTL_HTTP_PRE)) {
//        if(ValidateUtil.isUrl(imgPath)){
            encode = getNetImageStr(imgPath);
        }else{
            encode = getLocalImageStr(imgPath);
        }
        // 拼接前端需要用到的 前缀内容
        String imgType = "";
        if(StringUtils.isNotBlank(imgPath)){
            imgType = imgPath.substring(imgPath.lastIndexOf(".") + 1);
        }
        if(StringUtils.isNotBlank(encode)){
            return String.format("data:image/%s;base64,", imgType) + encode;
        }else{
            log.info("encode为空，imgPath:{}", imgPath);
        }
        return encode;
    }

    /**
     * 图片转化成base64字符串  本地图片
     * @param imgPath
     * @return
     */
    private static String getLocalImageStr(String imgPath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = imgPath;// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        String encode = null; // 返回Base64编码过的字节数组字符串
        // 对字节数组Base64编码
        try {
            // 读取图片字节数组
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            encode = Base64Encoder.encode(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return encode;
    }

    /**
     * 图片转化成base64字符串  网络图片
     * @param imgPath
     * @return
     */
    private static String getNetImageStr(String imgPath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = imgPath;// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        String encode = null; // 返回Base64编码过的字节数组字符串
        // 对字节数组Base64编码
        HttpURLConnection conn = null;
        try {
            URL url = new URL(imgFile);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3 * 1000);
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.connect();
            in = conn.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2 * 1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            data = outStream.toByteArray();
            encode = Base64Encoder.encode(data);
        } catch (IOException e) {
            log.info("图片hash64" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if(in != null){
                    in.close();
                }
                if(conn != null){
                    conn.disconnect();
                }
            } catch (IOException e) {
                log.info("图片hash64" + e.getMessage());
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return encode;
    }

    /**
     * 压缩  网络图片
     * @param imgPath
     * @return
     */
    public static void compressImage(String imgPath) {
        BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_BGR);
        Graphics graphics = image.createGraphics();
        BufferedImage buffImg = null;
        try {
            buffImg = ImageIO.read(new URL(imgPath));//读取图片
        } catch (IOException e) {
            e.printStackTrace();
        }
        graphics.drawImage(buffImg, 0, 0, 50, 50, null);//绘制缩小后的图
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            // 写到本地
            ImageIO.write(buffImg, "jpg", new File("/Users/admin/Downloads/c.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * base64字符串转化成图片
     *
     * @param imgData
     *            图片编码
     * @param imgFilePath
     *            存放到本地路径
     * @return
     * @throws IOException
     */
    @SuppressWarnings("finally")
    public static boolean generateImage(String imgData, String imgFilePath) throws IOException { // 对字节数组字符串进行Base64解码并生成图片
        if (imgData == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            out = new FileOutputStream(imgFilePath);
            // Base64解码
            byte[] b = Base64Decoder.decode(imgData);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            out.write(b);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
            return true;
        }
    }
}
