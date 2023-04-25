package ru.subbotin;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import ru.subbotin.dao.DaoRepositoryCats;
import ru.subbotin.service.CatsService;
import ru.subbotin.service.CatsServiceImpl;

@Configuration
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
        DaoRepositoryCats repository = context.getBean(DaoRepositoryCats.class);

        CatsServiceImpl service = context.getBean(CatsServiceImpl.class);
        service.test();
    }

}
