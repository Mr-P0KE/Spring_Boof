package com.poke.Config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.poke.Comon.JacksonObjectMapper;
import com.poke.Comon.UserInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private UserInterceptor userInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        /*   ��̬��Դӳ�䣬�Զ�����Դ��ӳ�䣬Ĭ��Ϊstatic
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        */
    }

    //���������ע�ᵽspring����
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/employee/login","/employee/logout","/backend/**","/front/**","/user/sendMsg","/user/login");
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor  = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }


    /**
     * ��չmvc��ܵ���Ϣת����
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("��չ��Ϣת����...");
        //������Ϣת��������
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //���ö���ת�������ײ�ʹ��Jackson��Java����תΪjson
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //���������Ϣת��������׷�ӵ�mvc��ܵ�ת����������
        converters.add(0,messageConverter);
    }
}
