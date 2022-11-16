package uz.keysoft.camunda.spring.boot.starter.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "camunda.bpm.client")
public class CamundaRestProperties {

  String baseUrl;

}
