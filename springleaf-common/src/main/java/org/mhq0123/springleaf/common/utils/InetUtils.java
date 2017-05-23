package org.mhq0123.springleaf.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName InetUtils
 * @date 2016-07-06
 * @memo
 */
public class InetUtils {

    private static final Logger logger = LoggerFactory.getLogger(InetUtils.class);

    private InetUtils(){}

    private static volatile InetAddress LOCAL_ADDRESS = null;

    /** 截取ip最后一段，补足3位长度*/
    private static String lastSplitIp_3l = null;

    /** 截取ip最后两段，补足6位长度*/
    private static String lastSplitIp_6l = null;

    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

    /**
     * 获取ip
     * @return
     */
    public static String getLocalHost() {
        InetAddress address = getLocalAddress();
        return address == null ? "127.0.0.1" : address.getHostAddress();
    }

    public static InetAddress getLocalAddress() {
        if(LOCAL_ADDRESS != null) {
            return LOCAL_ADDRESS;
        } else {
            InetAddress localAddress = getLocalAddress0();
            LOCAL_ADDRESS = localAddress;
            return localAddress;
        }
    }

    private static InetAddress getLocalAddress0() {
        InetAddress localAddress = null;

        try {
            localAddress = InetAddress.getLocalHost();
            if(isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable var6) {
            logger.warn("Failed to retriving ip address, " + var6.getMessage(), var6);
        }

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if(interfaces != null) {
                while(interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = (NetworkInterface)interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        if(addresses != null) {
                            while(addresses.hasMoreElements()) {
                                try {
                                    InetAddress address = (InetAddress)addresses.nextElement();
                                    if(isValidAddress(address)) {
                                        return address;
                                    }
                                } catch (Throwable var5) {
                                    logger.warn("Failed to retriving ip address, " + var5.getMessage(), var5);
                                }
                            }
                        }
                    } catch (Throwable var7) {
                        logger.warn("Failed to retriving ip address, " + var7.getMessage(), var7);
                    }
                }
            }
        } catch (Throwable var8) {
            logger.warn("Failed to retriving ip address, " + var8.getMessage(), var8);
        }

        logger.error("Could not get local host ip address, will use 127.0.0.1 instead.");
        return localAddress;
    }

    private static boolean isValidAddress(InetAddress address) {
        if(address != null && !address.isLoopbackAddress()) {
            String name = address.getHostAddress();
            return name != null && !"0.0.0.0".equals(name) && !"127.0.0.1".equals(name) && IP_PATTERN.matcher(name).matches();
        } else {
            return false;
        }
    }

    /**
     * 获取ip最后一段 3位长度
     * @return
     */
    public static String getLastSplitIp_3l() {
        if(StringUtils.isBlank(lastSplitIp_3l)) {
            String[] ips = InetUtils.getHostAddress().split("\\.");
            lastSplitIp_3l = StringUtils.leftPad(ips[ips.length-1], 3, '0');
        }
        logger.info(">>>>>>>>>>>>>>get host address lastSplitIp_3l:{}", lastSplitIp_3l);
        return lastSplitIp_3l;
    }

    /**
     * 获取ip最后两段 6位长度
     * @return
     */
    public static String getLastSplitIp_6l() {
        if(StringUtils.isBlank(lastSplitIp_6l)) {
            String[] ips = InetUtils.getHostAddress().split("\\.");
            lastSplitIp_6l = StringUtils.leftPad(ips[ips.length-2], 3, '0') + StringUtils.leftPad(ips[ips.length-1], 3, '0');
        }
        logger.info(">>>>>>>>>>>>>>get host address lastSplitIp_6l:{}", lastSplitIp_6l);
        return lastSplitIp_6l;
    }
}
