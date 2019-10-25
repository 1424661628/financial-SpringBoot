package com.lvmen.manager.error;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 错误处理相关配置
 * Created by lvmen on 2019/10/25
 */
@Configuration
public class ErrorConfiguration {

    @Bean
    public MyErrorController myErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties,
                                               ObjectProvider<List<ErrorViewResolver>> errorViewResolversProvider){
        return new MyErrorController(errorAttributes, serverProperties.getError(),
                errorViewResolversProvider.getIfAvailable());
    }
}
