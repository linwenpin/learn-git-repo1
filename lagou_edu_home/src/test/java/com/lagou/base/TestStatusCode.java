package com.lagou.base;

import org.junit.Test;

public class TestStatusCode {

    @Test
    public void testToString() {
        System.out.println( StatusCode.SUCCESS.toString() );
        System.out.println( StatusCode.FAIL.toString() );
    }
}
