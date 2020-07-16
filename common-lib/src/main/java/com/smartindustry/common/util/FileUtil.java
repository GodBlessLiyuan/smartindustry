package com.smartindustry.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

/**
 * @author xiahui
 * @version 1.0
 * @date Created in 2020/2/21 16:39
 * @description
 */
public class FileUtil {

    /**
     * 文件上传处理
     *
     * @param file 文件信息
     * @param dir  file存放文件目录
     * @return 文件路径
     */
    public static String uploadFile(MultipartFile file, String rootPath, String dir, String name) {
        String fileName = FileUtil.buildFileName(file, name);
        File targetFile = new File(rootPath + dir);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        String filePath = rootPath + dir + fileName;
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(filePath));
            stream.write(file.getBytes());
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return dir + fileName;
    }

    /**
     * 构建文件名
     *
     * @param file
     * @param moduleName
     * @return
     */
    private static String buildFileName(MultipartFile file, String moduleName) {
        String ext = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[file.getOriginalFilename().split("\\.").length - 1];
        return moduleName + DateUtil.date2Str(new Date(), DateUtil.YMDHMS) + getRandomString(4) + "." + ext;
    }

    /**
     * 生成随机数
     *
     * @param length
     * @return
     */
    private static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = new Random().nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
