package top.goldenpigeon.anonymousquestionboxserver.common.configuration;

import top.goldenpigeon.anonymousquestionboxserver.common.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfigurer extends WebMvcConfigurationSupport {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/login", "/test");
        super.addInterceptors(registry);
    }
}
