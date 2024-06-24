package web.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class Applnit extends AbstractAnnotationConfigDispatcherServletInitializer {
    // Метод, указывающий на класс конфигурации.
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    // Добавление конфигурации, в которой инициализируем ViewResolver, для корректного отображения http.
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                WebConfig.class
        };
    }

    // Данный метод указывает url, на котором будет базироваться приложение.
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}

