package uz.keysoft.camunda.spring.boot.starter.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtils {

  public static HttpHeaders getHeaders() {
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
    return headers;
  }

}
