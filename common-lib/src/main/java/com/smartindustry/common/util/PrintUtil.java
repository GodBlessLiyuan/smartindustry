package com.smartindustry.common.util;

import com.smartindustry.common.pojo.si.PrintLabelPO;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: xiahui
 * @date: Created in 2020/7/22 9:41
 * @description: 打印工具
 * @version: 1.0
 */
public class PrintUtil {
    private static final String FILE_PATH = "D:/print-label321.prn";

    private static final Pattern PATTERN_FD = Pattern.compile("^\\^FD>;.*\\^FS$");
    private static final Pattern PATTERN_FDMA = Pattern.compile("^\\^FDMA,.*\\^FS$");

    /**
     * 生成打印文件
     */
    public static void genPrintFile(PrintLabelPO po) {
        if (null == po) {
            return;
        }

        try {
            FileInputStream fis = new FileInputStream(new File(FILE_PATH));
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            StringBuilder sb = new StringBuilder();
            String str;
            int fdNum = 0;
            while ((str = reader.readLine()) != null) {
                Matcher mFd = PATTERN_FD.matcher(str);
                while (mFd.find()) {
//                    str = "^FD>;" + (fdNum == 0 ? po.getMaterialNo() : po.getNum()) + "^FS";
                    fdNum++;
                }

                Matcher mFdma = PATTERN_FDMA.matcher(str);
                while (mFdma.find()) {
//                    str = "^FDMA," + po.getPackageId() + "," + po.getMaterialNo() + "," + po.getNum() + "," + po.getProduceBatch() + "," + po.getProduceDate() + "^FS";
                }

                System.out.println(str);
                sb.append(str);
                sb.append(System.getProperty("line.separator"));
            }
            reader.close();
            fis.close();

            FileWriter fw = new FileWriter(FILE_PATH);
            fw.write(sb.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
