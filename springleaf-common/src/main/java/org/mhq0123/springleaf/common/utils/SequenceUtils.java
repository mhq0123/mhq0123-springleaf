package org.mhq0123.springleaf.common.utils;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName SequenceUtils
 * @date 2016-07-06
 * @memo 序号工具
 */
public class SequenceUtils {

    private static final Logger logger = LoggerFactory.getLogger(SequenceUtils.class);

    /** 循环值1  长度5位 */
    private static long CYC_NUM_5 = 1;

    /** 循环值2  长度7位*/
    private static long CYC_NUM_7 = 1;

    /** 循环值3  长度9位 */
    private static long CYC_NUM_9 = 1;

    /**
     * 【28位】
     * 注意：如果系统采用集群，需要区分服务器<br>
     *
     * @return ip(6) + yyyyMMddHHmmssSSS(17) + SEQ(5)
     */
    public static String getNext_28() {
        String currSeq = String.valueOf(getCycNum_5());

        String sequence = InetUtils.getLastSplitIp_6l() + new DateTime().toString("yyyyMMddHHmmssSSS") + fillLeftWith0(currSeq, 5);
        logger.info(">>>>>>>>>>>>>>create sequence:{}", sequence);
        return sequence;
    }

    /**
     * 【30位】
     * 注意：如果系统采用集群，需要区分服务器<br>
     *
     * @return ip(6) + yyyyMMddHHmmssSSS(17) + SEQ(7)
     */
    public static String getNext_30() {
        String currSeq = String.valueOf(getCycNum_7());

        String sequence =  InetUtils.getLastSplitIp_6l() + new DateTime().toString("yyyyMMddHHmmssSSS") + fillLeftWith0(currSeq, 7);
        logger.info(">>>>>>>>>>>>>>create sequence:{}", sequence);
        return sequence;
    }

    /**
     * 【32位】
     * 注意：如果系统采用集群，需要区分服务器<br>
     *
     * @return ip(6) + yyyyMMddHHmmssSSS(17) + SEQ(9)
     */
    public static String getNext_32() {
        String currSeq = String.valueOf(getCycNum_9());

        String sequence = InetUtils.getLastSplitIp_6l() + new DateTime().toString("yyyyMMddHHmmssSSS") + fillLeftWith0(currSeq, 9);
        logger.info(">>>>>>>>>>>>>>create sequence:{}", sequence);
        return sequence;
    }

    /**
     * 使用“0”左填充至指定长度<br>
     * @return
     */
    private static String fillLeftWith0(String currSeq, int length) {
        currSeq = "00000000000000000000" + currSeq;

        // 长度超长则截取
        if (currSeq.length() > length) {
            currSeq = currSeq.substring(currSeq.length() - length);
        }
        return currSeq;
    }

    /**
     * 获取自增长为 1，循环周期为 1000000000  的数值
     * @return
     */
    private synchronized static long getCycNum_5() {
        CYC_NUM_5 %= 1000000000;

        return CYC_NUM_5 ++;
    }

    /**
     * 获取自增长为 1，循环周期为 1000000000  的数值
     * @return
     */
    private synchronized static long getCycNum_7() {
        CYC_NUM_7 %= 1000000000;

        return CYC_NUM_7 ++;
    }

    /**
     * 获取自增长为 1，循环周期为 1000000000  的数值
     * @return
     */
    private synchronized static long getCycNum_9() {
        CYC_NUM_9 %= 1000000000;

        return CYC_NUM_9 ++;
    }
}
