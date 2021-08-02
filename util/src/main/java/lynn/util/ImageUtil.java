package lynn.util;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import lynn.util.exception.CommonException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * @ClassName ImageUtil
 * @Description TODO
 * @Date 2019-11-29
 * @Version 0.1
 */
@Slf4j
public class ImageUtil {

    /**
     * compressImage
     *
     * @param path
     * @param ppi
     * @return
     */
    public static byte[] compressImage(String path, int ppi) {
        byte[] smallImage = null;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            Thumbnails.of(path).size(ppi, ppi).outputFormat("png").toOutputStream(out);
            smallImage = out.toByteArray();
            return smallImage;
        } catch (IOException e) {
            log.error("图片压缩失败！", e);
            throw new CommonException("图片压缩失败！");
        } finally {
            FileUtil.close(out);
        }
    }

    /**
     * compressImage
     *
     * @param content
     * @param ppi
     * @return
     */
    public static byte[] compressImage(byte[] content, int ppi) {
        byte[] smallImage = null;
        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        try {
            out = new ByteArrayOutputStream();
            in = new ByteArrayInputStream(content);
            Thumbnails.of(in).size(ppi, ppi).outputFormat("png").toOutputStream(out);
            smallImage = out.toByteArray();
            return smallImage;
        } catch (IOException e) {
            log.error("图片压缩失败！", e);
            throw new CommonException("图片压缩失败！");
        } finally {
            FileUtil.close(out);
            FileUtil.close(in);
        }
    }

    public static String getOcrFilePath() {
        String path = System.getProperty("user.dir");
        String parentPath = path + File.separator + "template" + File.separator + "ocr" + File.separator;
        cn.hutool.core.io.FileUtil.mkdir(parentPath);
        return parentPath;
    }
}
