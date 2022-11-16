package uz.keysoft.camunda.spring.boot.starter.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
public class CamundaRestRequestResponseInterceptor implements ClientHttpRequestInterceptor {

  private static final String PATTERN = "dd.MM.yyyy HH:mm:ss";

  @Override
  public synchronized ClientHttpResponse intercept(final HttpRequest request,
                                                   final byte[] body,
                                                   final ClientHttpRequestExecution execution) throws IOException {
    final String uuid  = UUID.randomUUID().toString();
    final ZonedDateTime start = LocalDateTime.now().atZone(ZoneId.systemDefault());
    log.info("************* Begin Request/Response *************");
    logRequest(request, body, start, uuid);
    final ClientHttpResponse response = execution.execute(request, body);
    final ZonedDateTime end = LocalDateTime.now().atZone(ZoneId.systemDefault());
    logResponse(response, end, uuid);
    final long difference = end.toInstant().toEpochMilli() - start.toInstant().toEpochMilli();
    log.info("Total request time          : {}ms", difference);
    log.info("************* End Request/Response *************");
    return response;
  }

  private void logRequest(final HttpRequest request,
                          final byte[] body,
                          final ZonedDateTime start,
                          final String uuid) {
    final String requestStr = new String(body, StandardCharsets.UTF_8);
    log.info("Request UID                 : {}", uuid);
    log.info("Request URI                 : {}", request.getURI());
    log.info("Request Method              : {}", request.getMethod());
    log.info("Request Headers             : {}", request.getHeaders());
    log.info("Request content length      : {}", body.length);
    log.info("Request body                : {}", requestStr);
    log.info("Request Started at          : {}", DateTimeFormatter.ofPattern(PATTERN).format(start));
  }

  private void logResponse(final ClientHttpResponse response,
                           final ZonedDateTime end,
                           final String uuid) {
    try {
      final String responseStr = StreamUtils.copyToString(response.getBody(), Charset.defaultCharset());
      log.info("Response UID                : {}", uuid);
      log.info("Response content length     : {}", response.getBody().available());
      log.info("Response body               : {}", responseStr);
      log.info("Response status code        : {}", response.getRawStatusCode());
      log.info("Response status message     : {}", response.getStatusText());
      log.info("Response Finished at        : {}", DateTimeFormatter.ofPattern(PATTERN).format(end));
    } catch (Exception ex) {
      log.error("logResponse() -> {}", ex.getMessage());
    }
  }

}
