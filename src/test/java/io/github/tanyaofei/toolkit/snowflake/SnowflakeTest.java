package io.github.tanyaofei.toolkit.snowflake;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SnowflakeTest {


    @Test
    public void testGetId() {
        int size = 1000;
        assertEquals(IntStream.range(0, size).boxed().map(i -> Snowflake.getId()).collect(Collectors.toSet()).size(), size);
        System.out.println(Snowflake.getId());
    }


}
