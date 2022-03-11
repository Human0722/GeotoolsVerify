package io.human0722.geotools.runtime;

import cn.hutool.core.util.RuntimeUtil;

/**
 * @author xueliang
 * @description TODO
 * @date 2022-03-02 10:09
 */
public class CommandExecutor {
    public static void main(String[] args) {
        String ipconfig = RuntimeUtil.execForStr("ipconfig");
        long usableMemory = RuntimeUtil.getUsableMemory();
        System.out.println(usableMemory);
        System.out.println(ipconfig);
    }
}
