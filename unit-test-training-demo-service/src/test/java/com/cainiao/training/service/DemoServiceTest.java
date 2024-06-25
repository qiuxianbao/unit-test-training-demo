package com.cainiao.training.service;

import com.cainiao.training.infra.DemoDBMapper;
import com.cainiao.training.infra.DemoTairClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

/**
 * 有许多依赖的单元测试
 *
 * @author qiuxianbao
 * @date 2024/06/25
 * @since ace_1.4.2_20240611
 */
@RunWith(MockitoJUnitRunner.class)
public class DemoServiceTest {

    @Mock
    DemoTairClient mockDemoTairClient;
    @Mock
    DemoDBMapper mockDemoDBMapper;

    @InjectMocks
    DemoService demoService;

    /**
     * 稍复杂的逻辑
     * @throws Exception
     */
    @Test
    public void testGetResult_succeed_getFromCache() throws Exception {

        String request = "request";
        when(mockDemoTairClient.getCache(request)).thenReturn("getCacheResponse");

        String result = demoService.getResult(request);
        assertThat(result).isEqualTo("getCacheResponse");

        verify(mockDemoTairClient, times(1)).getCache(request);
        verifyNoMoreInteractions(mockDemoTairClient);
        verifyNoInteractions(mockDemoDBMapper);
    }

    /**
     * 异常
     * @throws Exception
     */
    @Test
    public void testGetResult_throwsException_whenCacheDataIsNull() throws Exception {
        String request = "request";
        when(mockDemoTairClient.getCache(request)).thenReturn(null);

        // 调用方法
        // 捕获的是IOException
        assertThatExceptionOfType(IOException.class).isThrownBy(() -> demoService.getResult(request))
                .withMessageContaining("Timeout");

        verify(mockDemoTairClient, times(1)).getCache(request);
        verifyNoMoreInteractions(mockDemoTairClient);
        verifyNoInteractions(mockDemoDBMapper);
    }

    @Test
    public void testGetResult_succeed_getFromDB() throws Exception {
        String request = "request";
        when(mockDemoTairClient.getCache(request)).thenReturn("");
        when(mockDemoDBMapper.queryData(request)).thenReturn("queryDataResponse");

        String result = demoService.getResult(request);
        Assert.assertEquals("queryDataResponse", result);

        verify(mockDemoTairClient, times(1)).getCache(request);
        verify(mockDemoDBMapper, times(1)).queryData(request);
        verifyNoMoreInteractions(mockDemoTairClient);
        verifyNoMoreInteractions(mockDemoDBMapper);
    }

    /**
     * 高阶用法
     *
     * Lambda 表达式内的逻辑怎么执行
     * @throws Exception
     */
    @Test
    public void testGetResults_succeed_getFromDB() throws Exception {
        List<String> requests = Collections.nCopies(2, "KEY");

        // 函数式接口
        doAnswer((Answer<List<String>>) invocationOnMock -> {
            Supplier<List<String>> loader = (Supplier<List<String>>) invocationOnMock.getArguments()[1];
            List dataFromSource = loader.get();
            return new ArrayList<>(dataFromSource);
        }).when(mockDemoTairClient)
                .batchGet(any(), any());

        when(mockDemoDBMapper.queryData(requests)).thenReturn(Collections.singletonList("EXPECTED"));

        List<String> actuals = demoService.getResults(requests);

        assertThat(actuals).containsExactly("EXPECTED");

        verify(mockDemoTairClient).batchGet(any(), any());
        verify(mockDemoDBMapper).queryData(anyList());
        // 可以传多个参数
        verifyNoMoreInteractions(mockDemoTairClient, mockDemoDBMapper);

    }

    /**
     * 没有返回值，用于验证过程
     * 可以拿到中间结果，即方法的入参（经过中间处理）
     * Argument Captor 的使用
     */
    @Test
    public void testDeleteByKey_succeed_givenValidKey() {
        String key = "KEY";
        demoService.deleteByKey(key);

        // void
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockDemoDBMapper).deleteData(argumentCaptor.capture());

        String actual = argumentCaptor.getValue();
        assertThat(actual).contains("Delete from", "key");

        verifyNoMoreInteractions(mockDemoDBMapper);
        verifyNoInteractions(mockDemoTairClient);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme