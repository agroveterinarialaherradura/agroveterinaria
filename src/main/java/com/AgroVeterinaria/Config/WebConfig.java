package com.AgroVeterinaria.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private VisitasInterceptor visitasInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Se aplica a todas las rutas excepto a las de recursos estáticos
        registry.addInterceptor(visitasInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**", "/css/**", "/js/**", "/images/**", "/api/**");
    }
}