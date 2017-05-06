package com.piedra.platease.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Linux环境下的shell命令
 * @author linwb
 * @since 2017/5/4.
 */
public class ShellUtil {
    private static Logger logger = LoggerFactory.getLogger(ShellUtil.class);

    /**
     * 执行 Shell命令或者Python命令等
     * @param cmds  命令
     */
    public static void execute(String[] cmds){
        if(cmds==null || cmds.length==0){
            logger.error("命令为空无法正常执行");
            return ;
        }

        InputStream in = null;
        BufferedReader reader = null;
        BufferedReader errReader = null;
        try {
            Process process = Runtime.getRuntime().exec(cmds);
            process.waitFor();

            // 打印正常的输出结果
            in = process.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            logger.info("执行命令结果:");
            String line ;
            while((line = reader.readLine()) != null) {
                logger.info("{}", line);
            }

            // 打印错误的输出结果
            List<String> errorLines = new ArrayList<>();
            errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while((line = errReader.readLine()) != null) {
                errorLines.add(line);
            }
            if(errorLines.size()>0) {
                logger.error("执行命令出错信息:");
                errorLines.forEach(errorLine -> logger.error(errorLine));
            }

        } catch (Exception e) {
            logger.error("执行命令出错，请检查！", e);
        } finally {
            IOUtils.closeQuietly(errReader);
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(in);
        }
    }
}
