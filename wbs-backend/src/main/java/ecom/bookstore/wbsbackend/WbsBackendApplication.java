package ecom.bookstore.wbsbackend;

import ecom.bookstore.wbsbackend.utils.InitData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;
import java.util.Random;

@SpringBootApplication//(exclude = DataSourceAutoConfiguration.class)
public class WbsBackendApplication implements CommandLineRunner {

  private final Logger LOGGER = LoggerFactory.getLogger(WbsBackendApplication.class);
  private final Random generator = new Random();

  @Value("${spring.jpa.hibernate.ddl-auto}")
  private String hibernate_ddl;

  @Autowired private InitData initData;

  public static void main(String[] args) {
    SpringApplication.run(WbsBackendApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    if (Objects.equals(hibernate_ddl, "create") || Objects.equals(hibernate_ddl, "create-drop")) {
      initData.init();
    }
    //initData.init();


  }
}
