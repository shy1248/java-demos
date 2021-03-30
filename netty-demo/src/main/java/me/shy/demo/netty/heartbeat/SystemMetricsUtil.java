package me.shy.demo.netty.heartbeat;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;
import org.hyperic.sigar.Who;

public class SystemMetricsUtil {

    public static void main(String[] args) throws InterruptedException, SigarException {
        SystemMetricsUtil.getOsMetrices();
        SystemMetricsUtil.getCpuMetrices();
        SystemMetricsUtil.getMemMetrices();
        SystemMetricsUtil.getDiskMetrices();
        SystemMetricsUtil.getNetMetrices();
    }

    public static void getCpuMetrices() throws SigarException {
        Properties props = System.getProperties();
        String systemName = props.getProperty("os.name");

        // 使用sigar获取CPU信息及内存
        Sigar sigar = new Sigar();

        // 获取CPU信息
        CpuInfo[] infos = sigar.getCpuInfoList();

        CpuInfo infoss = infos[0];

        CpuPerc cpu = sigar.getCpuPerc();
        // CPU总数
        Integer totalCPUs = infoss.getTotalCores();

        String cpuinfo = infoss.getVendor() + "  " + infoss.getModel();
        // 获取CPU基准速度
        double referenceSpeed = infoss.getMhz();

        String referenceSpeeds = String.format("%.2f", referenceSpeed / 1000) + " GHz";

        // 获取CPU用户利用率
        double userUsedPerc = cpu.getUser();
        // 获取CPU系统利用率
        double sysUsedPerc = cpu.getSys();
        // 获取CPU利用率
        double cpuUsedPerc = cpu.getCombined();

        String userPers =  "";
        String sysPers =  "";
        String cpuPers = "";
        if(systemName.startsWith("win") || systemName.startsWith("Win")) {
            //Windows系统 Perc * 100
            userPers = String.format("%.1f", userUsedPerc * 100) + "%";
            sysPers = String.format("%.1f", sysUsedPerc * 100) + "%";
            cpuPers = String.format("%.1f", cpuUsedPerc * 100) + "%";
        }else {
            //Linux系统 Perc * 1000
            cpuPers = String.format("%.1f", cpuUsedPerc * 1000) + "%";
            sysPers = String.format("%.1f", sysUsedPerc * 1000) + "%";
            userPers = String.format("%.1f", userUsedPerc * 1000) + "%";
        }

        System.out.println("CPU======="+cpuinfo);
        System.out.println("CPU总数======="+totalCPUs);
        System.out.println("CPU基准速度======="+referenceSpeeds);
        System.out.println("CPU用户利用率======="+userPers);
        System.out.println("CPU系统利用率======="+sysPers);
        System.out.println("CPU利用率======="+cpuPers);
        sigar.close();
    }

    public static void getMemMetrices() throws SigarException {
        // 使用sigar获取CPU信息及内存
        Sigar sigar = new Sigar();
        double memTotal = sigar.getMem().getTotal();
        double memRam = sigar.getMem().getRam();
        double memUsed = sigar.getMem().getActualUsed();// mem
        double memFree = sigar.getMem().getActualFree();
        double memUsedPerc = sigar.getMem().getUsedPercent();

        String memory = String.format("%.0f", memTotal / 1024 / 1024 / 1024) + " GB";
        String memRamStr = String.format("%.1f", memRam / 1024) + " GB";
        String memused = String.format("%.2f", memUsed / 1024 / 1024 / 1024) + " GB";
        String memFrees = String.format("%.2f", memFree / 1024 / 1024 / 1024) + " GB";
        String memoryUsage = String.format("%.2f", memUsedPerc) + " %";

        // 系统页面文件交换区信息
        Swap swap = sigar.getSwap();
        // 交换区总量
        System.out.println("Total = " + swap.getTotal() / 1024L + "K av");
        // 当前交换区使用量
        System.out.println("Used = " + swap.getUsed() / 1024L + "K used");
        // 当前交换区剩余量
        System.out.println("Free = " + swap.getFree() / 1024L + "K free");


        System.out.println("内存======="+memory);
        System.out.println("内存使用量======="+memRamStr);
        System.out.println("使用中======="+memused);
        System.out.println("可用======="+memFrees);
        System.out.println("内存使用率======="+memoryUsage);
        sigar.close();
    }

    public static void getOsMetrices() throws SigarException {
        Sigar sigar = new Sigar();
        String hostname = "";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (Exception exc) {
            try {
                hostname = sigar.getNetInfo().getHostName();
            } catch (SigarException e) {
                hostname = "localhost.unknown";
            } finally {
                sigar.close();
            }
        }
        System.out.println(hostname);

        // 取当前操作系统的信息
        OperatingSystem OS = OperatingSystem.getInstance();
        // 操作系统内核类型如： 386、486、586等x86
        System.out.println("OS.getArch() = " + OS.getArch());
        System.out.println("OS.getCpuEndian() = " + OS.getCpuEndian());//
        System.out.println("OS.getDataModel() = " + OS.getDataModel());//
        // 系统描述
        System.out.println("OS.getDescription() = " + OS.getDescription());
        System.out.println("OS.getMachine() = " + OS.getMachine());//
        // 操作系统类型
        System.out.println("OS.getName() = " + OS.getName());
        System.out.println("OS.getPatchLevel() = " + OS.getPatchLevel());//
        // 操作系统的卖主
        System.out.println("OS.getVendor() = " + OS.getVendor());
        // 卖主名称
        System.out
            .println("OS.getVendorCodeName() = " + OS.getVendorCodeName());
        // 操作系统名称
        System.out.println("OS.getVendorName() = " + OS.getVendorName());
        // 操作系统卖主类型
        System.out.println("OS.getVendorVersion() = " + OS.getVendorVersion());
        // 操作系统的版本号
        System.out.println("OS.getVersion() = " + OS.getVersion());

        // 取当前系统进程表中的用户信息
        Who who[] = sigar.getWhoList();
        if (who != null && who.length > 0) {
            for (int i = 0; i < who.length; i++) {
                System.out.println("\n~~~~~~~~~" + String.valueOf(i) + "~~~~~~~~~");
                Who _who = who[i];
                System.out.println("getDevice() = " + _who.getDevice());
                System.out.println("getHost() = " + _who.getHost());
                System.out.println("getTime() = " + _who.getTime());
                // 当前系统进程表中的用户名
                System.out.println("getUser() = " + _who.getUser());
            }
        }
    }

    public static void getDiskMetrices() throws SigarException, InterruptedException {
        Sigar sigar = new Sigar();
        FileSystemUsage sfileSystemUsage = null;
        FileSystemUsage efileSystemUsage = null;

        List<FileSystem> list = Arrays.asList(sigar.getFileSystemList());
        String diskName = "";
        double total = 0;
        double usePercent = 0;

        double startreads = 0;
        double startwrites = 0;

        double endreads = 0;
        double endwrites = 0;

        double reads = 0;
        double writes = 0;

        long start = System.currentTimeMillis();
        for (int i = 0; i < list.size(); i++) {
            diskName += list.get(i);
            try {
                sfileSystemUsage = sigar.getFileSystemUsage(String.valueOf(list.get(i)));
            } catch (SigarException e) {// 当fileSystem.getType()为5时会出现该异常——此时文件系统类型为光驱
                continue;
            }
            total += sfileSystemUsage.getTotal();
            usePercent += sfileSystemUsage.getUsePercent();

            startreads += sfileSystemUsage.getDiskReads();
            startwrites += sfileSystemUsage.getDiskWrites();
        }

        Thread.sleep(1000);
        long end = System.currentTimeMillis();
        for (int i = 0; i < list.size(); i++) {
            try {
                efileSystemUsage = sigar.getFileSystemUsage(String.valueOf(list.get(i)));
            } catch (SigarException e) {// 当fileSystem.getType()为5时会出现该异常——此时文件系统类型为光驱
                continue;
            }

            endreads += efileSystemUsage.getDiskReads();
            endwrites += efileSystemUsage.getDiskWrites();
        }

        reads = ((endreads - startreads)*8/(end-start)*1000);
        writes = ((endwrites - startwrites)*8/(end-start)*1000);

        // 读
        String readss = String.format("%.1f", reads) + " KB/s";
        // 写
        String writess = String.format("%.1f", writes) + " KB/s";
        // 磁盘容量
        String totals="";

        if(total / 1024 / 1024 / 1024>1) {

            totals = String.format("%.1f", total / 1024 / 1024 / 1024) + " TB";
        }else if(total / 1024 / 1024>1){

            totals = String.format("%.1f", total / 1024 / 1024) + " GB";
        }else if(total / 1024>1) {
            totals = String.format("%.1f", total / 1024) + " MB";
        }else if(total <=1) {
            totals = String.format("%.1f", total) + " KB";
        }

        // 磁盘使用率
        String usePercents = String.format("%.2f", usePercent * 100) + " %";

        System.out.println("磁盘名======="+diskName);
        System.out.println("总容量======="+totals);
        System.out.println("磁盘使用率======="+usePercents);
        System.out.println("读取速度======="+readss);
        System.out.println("写入速度======="+writess);

        // 关闭sigar
        sigar.close();

    }

    public static void getNetMetrices() throws SigarException, InterruptedException {
        Sigar sigar = new Sigar();

        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                String name = intf.getName();
                if (!name.contains("docker") && !name.contains("lo")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String ipaddress = inetAddress.getHostAddress().toString();
                            if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress.contains("fe80")) {
                                ip = ipaddress;
                            }
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            ip = "127.0.0.1";
            ex.printStackTrace();
        }

        String[] netInterfaceList = sigar.getNetInterfaceList();

        double rxBytes = 0;
        double txBytes = 0;
        String description = null;
        // 一些其它的信息
        for (int i = 0; i < netInterfaceList.length; i++) {
            String netInterface = netInterfaceList[i];// 网络接口
            NetInterfaceConfig netInterfaceConfig = sigar.getNetInterfaceConfig(netInterface);

            if (netInterfaceConfig.getAddress().equals(ip)) {

                description =  netInterfaceConfig.getDescription();

                System.out.println("网卡描述信息 ======="+description);
                double start = System.currentTimeMillis();
                NetInterfaceStat statStart = sigar.getNetInterfaceStat(netInterface);
                double rxBytesStart = statStart.getRxBytes();
                double txBytesStart = statStart.getTxBytes();

                Thread.sleep(1000);
                double end = System.currentTimeMillis();
                NetInterfaceStat statEnd = sigar.getNetInterfaceStat(netInterface);
                double rxBytesEnd = statEnd.getRxBytes();
                double txBytesEnd = statEnd.getTxBytes();

                rxBytes = ((rxBytesEnd - rxBytesStart)*8/(end-start)*1000)/1024;
                txBytes = ((txBytesEnd - txBytesStart)*8/(end-start)*1000)/1024;

                break;
            }

            // 判断网卡信息中是否包含VMware即虚拟机，不存在则设置为返回值
            //System.out.println("网卡MAC地址 ======="+netInterfaceConfig.getHwaddr());

        }
        // 接收字节
        String rxBytess;
        // 发送字节
        String txBytess;

        if(rxBytes>1024) {
            rxBytess = String.format("%.1f", rxBytes/1024)+" Mbps";
        }else {
            rxBytess = String.format("%.0f", rxBytes)+" Kbps";
        }
        if(txBytes>1024) {
            txBytess = String.format("%.1f", txBytes/1024)+" Mbps" ;
        }else {
            txBytess=String.format("%.0f", txBytes)+" Kbps";
        }

        System.out.println("发送======="+rxBytess);
        System.out.println("接收======="+txBytess);
        System.out.println("IP======="+ip);

        // 关闭sigar
        sigar.close();
    }

}
