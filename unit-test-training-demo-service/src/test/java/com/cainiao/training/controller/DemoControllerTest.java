package com.cainiao.training.controller;

import com.cainiao.training.service.DemoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

/**
 * 单元测试：
 *
 * 有些许依赖
 * 异常
 *
 * @author qiuxianbao
 * @date 2024/06/25
 * @since ace_1.4.2_20240611
 */
//自动初始化, RunWith是junit的包
//@RunWith(MockitoJUnitRunner.class)
public class DemoControllerTest {

    // 1、定义对象
    // 此注解用于模拟一个mock对象
    // 模拟对象是用来替换真实对象的，在测试中你可以设置它的行为（比如方法的返回值）以控制测试环境，从而使得测试更加集中和可预测。
    @Mock
    DemoService mockDemoService;

    // 此注解用于创建你要测试的类的一个实例
    // 并自动将其他使用 @Mock 或 @Spy 注解创建的模拟对象注入到这个实例中
    // 简化了依赖注入的过程，会自动处理这些依赖的注入。你可以专注于测试类的核心逻辑，而不是它的构造或依赖管理
    @InjectMocks
    DemoController demoController;

    // 显式初始化
    // MockitoAnnotations.initMocks(this) 等同于 @RunWith(MockitoJUnitRunner.class)
    @Before
    public void setUp() {
        // Mockito会自动扫描当前测试类中的所有成员变量，并将它们初始化为mock对象
        MockitoAnnotations.initMocks(this);
    }

    /**
     * 测试Controller层的execute
     * 模拟service的返回结果，
     *
     * 说明：
     * 此处没有覆盖catch
     *
     * @throws Exception
     */
    @Test
    public void testExecute_succeed() throws Exception {
        // 2、模拟方法
//        String request = anyString();
        String request = "request";
        when(mockDemoService.getResult(request)).thenReturn("getResultResponse");

        // 3、调用方法
        String result = demoController.execute(request);

        // 4、验证方法
        Assert.assertEquals("getResultResponse", result);
        // assertj
        assertThat(result).isEqualTo("getResultResponse");
        assertThat(result).contains("Result");

        // 验证是否调用了getResult()
        verify(mockDemoService).getResult(request);
        // 验证mockDemoService对象是否还调用了其他的方法
        verifyNoMoreInteractions(mockDemoService);
    }

    /**
     * 测试异常
     */
    @Test
    public void testExecute_throwsException_whenServiceThrowsException() throws Exception {
        // 模拟方法
        String request = "request";
        when(mockDemoService.getResult(request)).thenThrow(new IOException("IO Exception"));

        // 调用方法
        // 明确是IOException引起，捕获的是RuntimeException
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> demoController.execute(request))
                .withCauseExactlyInstanceOf(IOException.class)
                .withMessageContaining("IO");

        // 验证是否调用了getResult()
        verify(mockDemoService).getResult(request);
        // 验证mockDemoService对象是否还调用了其他的方法
        verifyNoMoreInteractions(mockDemoService);

    }

    /**
     * 测试Controller层的delete
     * 确保demoService的delete被调用
     * @throws Exception
     */
    @Test
    public void testDelete() throws Exception {
        String request = "request";
        demoController.delete(request);
        // 确认 demoService#deleteByKey 被调用（此处的demoService是一个mock对象）
        // 如果没有被调用，则会抛出异常
        verify(mockDemoService).deleteByKey(request);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme