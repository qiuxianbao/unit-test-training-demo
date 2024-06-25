package com.cainiao.training.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * 单元测试：
 *
 * 无依赖
 *
 * @author qiuxianbao
 * @date 2024/06/25
 * @since ace_1.4.2_20240611
 */
public class DemoUtilTest {

    /**
     * 简单的测试可以写在一个方法中，更具有可读性
     * @throws Exception
     */
    @Test
    public void testIsPositive() throws Exception {
        Assert.assertFalse("返回值不为假", DemoUtil.isPositive(-1));
        Assert.assertFalse("返回值不为假", DemoUtil.isPositive(0));
        Assert.assertTrue("返回值不为真", DemoUtil.isPositive(1));
        Assert.assertFalse("返回值不为假", DemoUtil.isPositive(null));
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme