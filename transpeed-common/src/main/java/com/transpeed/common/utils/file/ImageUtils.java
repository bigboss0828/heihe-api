package com.transpeed.common.utils.file;

import com.transpeed.common.config.Transpeed;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.common.utils.StringUtils;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

/**
 * @author hanshuai
 * @title: ImageUtils
 * @description: 图片处理工具类
 * @date 2022/4/21 17:02
 */
public class ImageUtils {

    private static final Logger log = LoggerFactory.getLogger(ImageUtils.class);

    /**
     * 校验照片长宽
     * @param base64 照片Base64
     * @param height 高
     * @param width 宽
     * @return 是否符合规则
     */
    public static boolean checkPicHW(String base64, Integer height, Integer width) {
        try {
            byte[] strBase64 = new BASE64Decoder().decodeBuffer(base64);
            InputStream is = new ByteArrayInputStream(strBase64);
            BufferedImage image = ImageIO.read(is);
            if (image.getHeight() > height || image.getWidth() > width) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            log.error("校验照片宽高异常！");
            return false;
        }
    }

    /**
     * @param :base64:上传的base64
     * @param size:限制大小
     * @param unit:限制单位（B,K,M,G)
     * @return boolean:是否大于
     * @dateTime 2020-10-21 14:42:17
     * <p>
     * 判断文件大小
     */
    public static boolean checkBase64Size(String base64, int size, String unit) {
        // 上传文件的大小, 单位为字节.
        double len = base64FileSize(base64);
        // 准备接收换算后文件大小的容器
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        // 如果上传文件大于限定的容量
        if (fileSize > size) {
            return false;
        }
        return true;
    }

    /**
     * 精确计算base64字符串文件大小（单位：B）
     *
     * @param base64String
     * @return double 字节大小
     */
    public static double base64FileSize(String base64String) {
        /**检测是否含有base64,文件头)*/
        if (base64String.lastIndexOf(",") > -1) {
            base64String = base64String.substring(base64String.lastIndexOf(",") + 1);
        }
        /** 获取base64字符串长度(不含data:audio/wav;base64,文件头) */
        int size0 = base64String.length();
        if (size0 > 10) {
            /** 获取字符串的尾巴的最后10个字符，用于判断尾巴是否有等号，正常生成的base64文件'等号'不会超过4个 */
            String tail = base64String.substring(size0 - 10);
            /** 找到等号，把等号也去掉,(等号其实是空的意思,不能算在文件大小里面) */
            int equalIndex = tail.indexOf("=");
            if (equalIndex > 0) {
                size0 = size0 - (10 - equalIndex);
            }
        }
        /** 计算后得到的文件流大小，单位为字节 */
        return size0 - ((double) size0 / 8) * 2;
    }

    public static byte[] getImage(String imagePath) {
        InputStream is = getFile(imagePath);
        try {
            return IOUtils.toByteArray(is);
        } catch (Exception e) {
            log.error("图片加载异常 {}", e);
            return null;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    public static InputStream getFile(String imagePath) {
        try {
            byte[] result = readFile(imagePath);
            result = Arrays.copyOf(result, result.length);
            return new ByteArrayInputStream(result);
        } catch (Exception e) {
            log.error("获取图片异常 {}", e);
        }
        return null;
    }

    /**
     * 读取文件为字节数据
     *
     * @param url 地址
     * @return 字节数据
     */
    public static byte[] readFile(String url) {
        InputStream in = null;
        try {
            if (url.startsWith("http")) {
                // 网络地址
                URL urlObj = new URL(url);
                URLConnection urlConnection = urlObj.openConnection();
                urlConnection.setConnectTimeout(30 * 1000);
                urlConnection.setReadTimeout(60 * 1000);
                urlConnection.setDoInput(true);
                in = urlConnection.getInputStream();
            } else {
                // 本机地址
                String localPath = Transpeed.getProfile();
                String downloadPath = localPath + StringUtils.substringAfter(url, SystemConstants.RESOURCE_PREFIX);
                in = new FileInputStream(downloadPath);
            }
            return IOUtils.toByteArray(in);
        } catch (Exception e) {
            log.error("获取文件路径异常 {}", e);
            return null;
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

}
