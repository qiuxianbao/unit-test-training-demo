package com.cainiao.training.infra;

import com.cainiao.training.util.DemoLandlordClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * PowerMockRunner
 * 静态方法
 *
 * @author qiuxianbao
 * @date 2024/06/25
 * @since ace_1.4.2_20240611
 */
@RunWith(PowerMockRunner.class)
// 表示在执行测试之前，需要对DemoLandlordClient类进行预处理
@PrepareForTest(DemoLandlordClient.class)
public class DemoTairClientTest {

    @InjectMocks
    DemoTairClient demoTairClient;

    @Before
    public void setup() {
        // 静态方法
        PowerMockito.mockStatic(DemoLandlordClient.class);
        PowerMockito.when(DemoLandlordClient.getTenantId())
                .thenReturn("TAOBAO_HK");
    }

    @Test
    public void testGetCache() throws Exception {
        String result = demoTairClient.getCache("key");
        assertThat(result).isEqualTo("key TAOBAO_HK");
    }

}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme