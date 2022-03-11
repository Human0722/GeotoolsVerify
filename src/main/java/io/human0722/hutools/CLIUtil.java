package io.human0722.hutools;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;

import java.io.*;

/**
 * @author xueliang
 * @description  Java Run Shell Script
 * @date 2022-03-10 11:08 AM
 **/
public class CLIUtil {
    // move dhusget.txt to Lab dir,and execute it
    public static void main(String[] args) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("shell/dhusget.sh");
        FileUtil.copy(new File(classPathResource.getAbsolutePath()), new File("/Users/rebot0722/Desktop/puhou/script/lab"),true);
        ProcessBuilder pb = new ProcessBuilder();
        pb.directory(new File("/Users/rebot0722/Desktop/puhou/script/lab"));
        pb.command("./dhusget.sh");
        Process start = pb.start();
        InputStream inputStream = start.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
    }
    public static void output(Process process) throws IOException {
        InputStream inputStream = process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while((line = bufferedReader.readLine()) != null) {
            System.out.printf(line);
        }
    }

}
