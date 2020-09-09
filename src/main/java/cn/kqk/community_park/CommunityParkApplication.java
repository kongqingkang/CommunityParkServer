package cn.kqk.community_park;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"cn.kqk.community_park", "cn.kqk.common"})
@MapperScan("cn.kqk.community_park.mapper")
@EnableTransactionManagement
public class CommunityParkApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CommunityParkApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CommunityParkApplication.class);
    }

}
