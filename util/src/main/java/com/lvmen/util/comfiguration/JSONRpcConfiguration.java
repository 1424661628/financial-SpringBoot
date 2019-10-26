package com.lvmen.util.comfiguration;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcClientProxyCreator;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * JsonRpc自动化配置类
 * Created by lvmen on 2019/10/26
 */
@Configuration
public class JSONRpcConfiguration {

    private static Logger LOG = LoggerFactory.getLogger(JSONRpcConfiguration.class);

    // 服务端配置
    @Bean
    public AutoJsonRpcServiceImplExporter rpcServiceImplExporter(){ // 服务端入口
        return new AutoJsonRpcServiceImplExporter();
    }

    // 客户端配置
    @Bean
    @ConditionalOnProperty(value = {"rpc.client.url", "rpc.client.basePackage"})
    public AutoJsonRpcClientProxyCreator rpcClientProxyCreator(@Value("${rpc.client.url}") String url, @Value("${rpc.client.basePackage}") String basePackage){ // 客户端入口
        AutoJsonRpcClientProxyCreator creator = new AutoJsonRpcClientProxyCreator();
        try {
            // 1. 设置基础url
            creator.setBaseUrl(new URL(url));
        } catch (MalformedURLException e) {
            LOG.error("创建rpc服务地址错误", e);
        }
        // 2. 设置扫描包路径
        creator.setScanPackage(basePackage);
        return creator;
    }
}
