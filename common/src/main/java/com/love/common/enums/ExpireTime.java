package com.love.common.enums;

public enum ExpireTime {

    /*
     * 无固定期限
     */
    NONE(0, "无固定期限"),

    /*
     * 1秒钟
     */
    ONE_SEC(1, "1秒钟"),

    /*
     * 5秒钟
     */
    FIVE_SEC(5, "5秒钟"),

    /*
     * 10秒钟
     */
    TEN_SEC(10, "10秒钟"),

    /*
     * 30秒钟
     */
    HALF_A_MIN(30, "30秒钟"),

    /*
     * 1分钟
     */
    ONE_MIN(60, "1分钟"),

    /*
     * 5分钟
     */
    FIVE_MIN(5 * 60, "5分钟"),

    /*
     * 10分钟
     */
    TEN_MIN(10 * 60, "10分钟"),

    /*
     * 20分钟
     */
    TWENTY_MIN(20 * 60, "20分钟"),

    /*
     * 30分钟
     */
    HALF_AN_HOUR(30 * 60, "30分钟"),

    /*
     * 1小时
     */
    ONE_HOUR(60 * 60, "1小时"),

    /*
     * 1天
     */
    ONE_DAY(24 * 60 * 60, "1天"),

    /*
     * 一个周
     */
    ONE_WEEK(24 * 7 * 60 * 60, "1周"),

    /*
     * 1个月
     */
    ONE_MONTH(24 * 30 * 60 * 60, "1个月"),

    /*
     * 1年
     */
    ONE_YEAR(365 * 24 * 60 * 60, "1年");

    /*
     * 时间
     */
    private final int time;
    /*
     * 描述
     */
    private final String desc;

    ExpireTime(int time, String desc) {
        this.time = time;
        this.desc = desc;
    }

    /*
     * 根据时间匹配失效期限
     *
     * @param time
     * @return
     */
    public static ExpireTime match(int time) {
        ExpireTime[] expireTimes = ExpireTime.values();
        for (ExpireTime expireTime : expireTimes) {
            if (expireTime.time == time) {
                return expireTime;
            }
        }
        return ExpireTime.HALF_AN_HOUR;
    }

    /*
     * 获取具体时间
     *
     * @return
     */
    public int getTime() {
        return time;
    }

    /*
     * 获取时间描述信息
     *
     * @return
     */
    public String getDesc() {
        return desc;
    }

}
