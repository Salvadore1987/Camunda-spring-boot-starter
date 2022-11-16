package uz.keysoft.camunda.spring.boot.starter.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import uz.keysoft.camunda.spring.boot.starter.exception.CamundaRestException;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Slf4j
@RequiredArgsConstructor
public class CamundaRestGlobalHandler implements ResponseErrorHandler {

  private final ObjectMapper mapper;

  @Override
  public boolean hasError(ClientHttpResponse response) throws IOException {
    return (
      response.getStatusCode().series() == CLIENT_ERROR
        || response.getStatusCode().series() == SERVER_ERROR);
  }

  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    log.error(response.getStatusText());
    final Map<String, String> map = mapper.readValue(response.getBody(), Map.class);
    String message = Optional.ofNullable(map).map(v -> v.get("message")).map(String.class::cast).orElse(null);
    throw new CamundaRestException(response.getStatusCode(), message);
  }

}
