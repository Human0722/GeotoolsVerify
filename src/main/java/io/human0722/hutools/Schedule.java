package io.human0722.hutools;

import cn.hutool.cron.CronUtil;

/**
 * @author xueliang
 * @date 2022-03-08 4:19 PM
 **/
public class Schedule {
    public static void main(String[] args) {
        CronUtil.start();
    }
    public void run() {
        System.out.println("HelloWorld");
    }
}
