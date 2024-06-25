package com.cainiao.training.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * 单元测试
 * @author qiuxianbao
 * @date 2024/06/25
 * @since ace_1.4.2_20240611
 */
public class DemoUtilTest {

    @Test
    public void testIsPositive() throws Exception {
        boolean result = DemoUtil.isPositive(Integer.valueOf(0));
        Assert.assertEquals(true, result);
    }

    @Test
    public void testIsLargerThan() throws Exception {
        boolean result = DemoUtil.isLargerThan(Integer.valueOf(0), Integer.valueOf(0));
        Assert.assertEquals(true, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme