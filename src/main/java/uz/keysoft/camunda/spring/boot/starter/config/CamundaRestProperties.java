package uz.keysoft.camunda.spring.boot.starter.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "camunda.bpm.client")
public class CamundaRestProperties {

  String baseUrl;
  BasicAuth basicAuth = new BasicAuth();

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class BasicAuth {
    String username;
    String password;
  }

}
