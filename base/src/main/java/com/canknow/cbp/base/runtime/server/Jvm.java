package com.canknow.cbp.base.runtime.server;

import com.canknow.cbp.utils.Math;
import com.canknow.cbp.utils.DatePattern;
import com.canknow.cbp.utils.DateUtils;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.LocalDateTime;

public class Jvm {
    /**
     * 当前JVM占用的内存总数(M)
     */
    private double total;

    /**
     * JVM最大可用内存总数(M)
     */
    private double max;

    /**
     * JVM空闲内存(M)
     */
    private double free;

    /**
     * JDK版本
     */
    private String version;

    /**
     * JDK路径
     */
    private String home;

    public double getTotal() {
        return Math.div(total, (1024 * 1024), 2);
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getMax() {
        return Math.div(max, (1024 * 1024), 2);
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getFree() {
        return Math.div(free, (1024 * 1024), 2);
    }

    public void setFree(double free) {
        this.free = free;
    }

    public double getUsed() {
        return Math.div(total - free, (1024 * 1024), 2);
    }

    public double getUsage() {
        return Math.mul(Math.div(total - free, total, 4), 100);
    }

    /**
     * 获取JDK名称
     */
    public String getName() {
        return ManagementFactory.getRuntimeMXBean().getVmName();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public LocalDateTime getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return DateUtils.timestampToDatetime(time);
    }

    /**
     * JDK启动时间
     */
    public String getStartTime() {
        return DateUtils.format(getServerStartDate(), DatePattern.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * JDK运行时间
     */
    public long getRunTime() {
        return Duration.between(getServerStartDate(), LocalDateTime.now()).getSeconds();
    }
}
