package Web.WebBlog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

    @Configuration
    public class MvcConfig implements WebMvcConfigurer {

        @Value("${upload.path}")
        private String uploadPath;

        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/login").setViewName("login");
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {

            registry.addResourceHandler("/img/**")
                    .addResourceLocations("file://"+uploadPath+"/");

        }
    }

/*
spring.jpa.generate-ddl=false
        spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect

        spring.flyway.baseline-on-migrate=true
        spring.flyway.url=jdbc:mysql://localhost:3306/springblog?useUnicode=true&serverTimezone=UTC&useSSL=false
        spring.flyway.user=root
        spring.flyway.password=root*/
