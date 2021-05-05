package com.canknow.cbp.base.runtime.server;

import com.canknow.cbp.utils.Math;
import com.canknow.cbp.utils.FileUtil;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class Server {
    private static final int WAIT_SECOND = 1000;

    private Cpu cpu = new Cpu();

    private Memory memory = new Memory();

    private Jvm jvm = new Jvm();

    private System system = new System();

    private List<SysFile> sysFiles = new LinkedList<>();

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public Jvm getJvm() {
        return jvm;
    }

    public void setJvm(Jvm jvm) {
        this.jvm = jvm;
    }

    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    public List<SysFile> getSysFiles() {
        return sysFiles;
    }

    public void setSysFiles(List<SysFile> sysFiles) {
        this.sysFiles = sysFiles;
    }

    public void fillData() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();

        setCpuInfo(hardwareAbstractionLayer.getProcessor());

        setMemoryInfo(hardwareAbstractionLayer.getMemory());

        setSysInfo();

        setJvmInfo();

        setSysFiles(systemInfo.getOperatingSystem());
    }

    private void setCpuInfo(CentralProcessor processor) {
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softIrq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long ioWait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long totalCpu = user + nice + cSys + idle + ioWait + irq + softIrq + steal;
        cpu.setCpuNum(processor.getLogicalProcessorCount());
        cpu.setTotal(totalCpu);
        cpu.setSys(cSys);
        cpu.setUsed(user);
        cpu.setWait(ioWait);
        cpu.setFree(idle);
    }

    private void setMemoryInfo(GlobalMemory memory) {
        this.memory.setTotal(memory.getTotal());
        this.memory.setUsed(memory.getTotal() - memory.getAvailable());
        this.memory.setFree(memory.getAvailable());
    }


    public String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    public String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e) {
            return "";
        }
    }

    private void setSysInfo() {
        Properties properties = java.lang.System.getProperties();
        system.setComputerName(getHostName());
        system.setComputerIp(getHostIp());
        system.setOsName(properties.getProperty("os.name"));
        system.setOsArch(properties.getProperty("os.arch"));
        system.setUserDir(properties.getProperty("user.dir"));
    }

    private void setJvmInfo() {
        Properties props = java.lang.System.getProperties();
        jvm.setTotal(Runtime.getRuntime().totalMemory());
        jvm.setMax(Runtime.getRuntime().maxMemory());
        jvm.setFree(Runtime.getRuntime().freeMemory());
        jvm.setVersion(props.getProperty("java.version"));
        jvm.setHome(props.getProperty("java.home"));
    }

    private void setSysFiles(OperatingSystem os) {
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fileStores = fileSystem.getFileStores();

        for (OSFileStore fileStore : fileStores) {
            long free = fileStore.getUsableSpace();
            long total = fileStore.getTotalSpace();
            long used = total - free;
            SysFile sysFile = new SysFile();
            sysFile.setDirName(fileStore.getMount());
            sysFile.setSysTypeName(fileStore.getType());
            sysFile.setTypeName(fileStore.getName());
            sysFile.setTotal(FileUtil.convertFileSize(total));
            sysFile.setFree(FileUtil.convertFileSize(free));
            sysFile.setUsed(FileUtil.convertFileSize(used));

            if (total != 0) {
                sysFile.setUsage(Math.mul(Math.div(used, total, 4), 100));
            }
            sysFiles.add(sysFile);
        }
    }
}
