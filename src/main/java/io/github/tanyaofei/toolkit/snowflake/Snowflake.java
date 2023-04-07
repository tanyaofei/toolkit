package io.github.tanyaofei.toolkit.snowflake;

import java.net.InetAddress;

/**
 * 雪花算法
 */
public class Snowflake {

    private final static Snowflake theSnowflake = new Snowflake();

    private final Sequence sequence;

    public Snowflake() {
        this.sequence = new Sequence(null);
    }

    public Snowflake(InetAddress inetAddress) {
        this.sequence = new Sequence(inetAddress);
    }

    public Snowflake(long workerId, long dataCenterId) {
        this.sequence = new Sequence(workerId, dataCenterId);
    }

    public Snowflake(Sequence sequence) {
        this.sequence = sequence;
    }

    /**
     * 通过单例获取 ID
     *
     * @return 雪花算法 ID
     */
    public static Long getId() {
        return theSnowflake.nextId();
    }

    /**
     * 获取雪花算法 ID
     *
     * @return 雪花算法 ID
     */
    public Long nextId() {
        return sequence.nextId();
    }

}
