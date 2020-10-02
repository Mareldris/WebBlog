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
        spring.flyway.password=root


create table sequence_post (next_val bigint) engine=InnoDB
insert into sequence_post values ( 1 )
create table sequence_user (next_val bigint) engine=InnoDB
insert into sequence_user values ( 1 )

create table post (
id bigint not null,
anons varchar(255),
filename varchar(255),
full_text varchar(255),
title varchar(255),
views integer not null,
user_id bigint,
primary key (id)) engine=InnoDB

create table user (
id bigint not null,
activation_code varchar(255),
active bit not null,
email varchar(255),
password varchar(255),
username varchar(255), p
rimary key (id)) engine=InnoDB

create table user_role (
user_id bigint not null,
roles varchar(255)) engine=InnoDB





        */
