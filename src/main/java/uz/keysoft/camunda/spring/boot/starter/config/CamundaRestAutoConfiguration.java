package uz.keysoft.camunda.spring.boot.starter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import uz.keysoft.camunda.spring.boot.starter.handler.CamundaRestGlobalHandler;
import uz.keysoft.camunda.spring.boot.starter.interceptor.CamundaRestRequestResponseInterceptor;
import uz.keysoft.camunda.spring.boot.starter.service.CamundaMessageService;
import uz.keysoft.camunda.spring.boot.starter.service.CamundaProcessService;
import uz.keysoft.camunda.spring.boot.starter.service.CamundaTaskService;
import uz.keysoft.camunda.spring.boot.starter.service.MessageService;
import uz.keysoft.camunda.spring.boot.starter.service.ProcessService;
import uz.keysoft.camunda.spring.boot.starter.service.TaskService;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import static org.apache.http.HttpHeaders.ACCEPT;

@Data
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass(RestTemplateBuilder.class)
@EnableConfigurationProperties(CamundaRestProperties.class)
public class CamundaRestAutoConfiguration {

  private final CamundaRestProperties properties;
  private final ObjectMapper mapper;

  public RestTemplate restTemplate() {
    final DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(properties.getBaseUrl());
    return new RestTemplateBuilder()
      .uriTemplateHandler(uriBuilderFactory)
      .requestFactory(() -> new BufferingClientHttpRequestFactory(getRequestFactory()))
      .errorHandler(camundaRestGlobalHandler())
      .interceptors(camundaRestRequestResponseInterceptor())
      .defaultHeader(ACCEPT, MediaType.APPLICATION_JSON_VALUE)
      .build();
  }

  @Bean
  public ProcessService camundaProcessService() {
    return new CamundaProcessService(restTemplate());
  }

  @Bean
  public TaskService camundaTaskService() {
    return new CamundaTaskService(restTemplate(), mapper);
  }

  @Bean
  public MessageService camundaMessageService() {
    return new CamundaMessageService(restTemplate(), mapper);
  }

  @Bean
  public ResponseErrorHandler camundaRestGlobalHandler() {
    return new CamundaRestGlobalHandler(mapper);
  }

  @Bean
  public ClientHttpRequestInterceptor camundaRestRequestResponseInterceptor() {
    return new CamundaRestRequestResponseInterceptor();
  }

  private ClientHttpRequestFactory getRequestFactory() {
    SSLContext sslContext;
    try {
      sslContext = SSLContexts
        .custom()
        .loadTrustMaterial((chain, authType) -> true)
        .build();
    } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
      e.printStackTrace();
      throw new IllegalArgumentException("Can't configure SSLContext");
    }
    final RequestConfig config = requestConfig();
    final CloseableHttpClient client = HttpClientBuilder
      .create()
      .setSSLContext(sslContext)
      .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
      .setDefaultRequestConfig(config)
      .build();
    return new HttpComponentsClientHttpRequestFactory(client);
  }

  private RequestConfig requestConfig() {
    return RequestConfig.custom()
      .setConnectTimeout(30000)
      .setConnectionRequestTimeout(30000)
      .setSocketTimeout(30000)
      .build();
  }

}
