package uz.keysoft.camunda.spring.boot.starter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.keysoft.camunda.spring.boot.starter.dto.message.CorrelateMessageRequest;
import uz.keysoft.camunda.spring.boot.starter.utils.HttpUtils;
import uz.keysoft.camunda.spring.boot.starter.utils.PayloadUtil;

import java.util.Map;

/**
 * This class used for working with Camunda Messages Events by REST API
 * @see <a href='https://docs.camunda.org/manual/7.5/reference/rest/message/'>Message</a>
 * @author Sagitov Eldar
 * @since 17.11.2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CamundaMessageService implements MessageService {

  RestTemplate restTemplate;
  ObjectMapper mapper;

  /**
   * Send and correlate message by process business key
   * @param messageName message topic name
   * @param businessKey process business key
   * @param data process variables to send into process
   * @see <a href='https://docs.camunda.org/manual/7.5/reference/rest/message/post-message/'>Deliver a Message</a>
   */
  @Override
  public <T> void correlateMessage(String messageName, String businessKey, T data) {
    final CorrelateMessageRequest request = CorrelateMessageRequest.builder()
      .messageName(messageName)
      .businessKey(businessKey)
      .processVariables(PayloadUtil.extractPayload(mapper.convertValue(data, Map.class)))
      .build();
    final HttpEntity<CorrelateMessageRequest> entity = new HttpEntity<>(request, HttpUtils.getHeaders());
    restTemplate.exchange(
      "/message",
      HttpMethod.POST,
      entity,
      new ParameterizedTypeReference<>() {}
    );
  }

  /**
   * Send and correlate message by correlation keys
   * @param messageName message topic name
   * @param data process variables to send into process
   * @param keys key to be correlated
   * @see <a href='https://docs.camunda.org/manual/7.5/reference/rest/message/post-message/'>Deliver a Message</a>
   */
  @Override
  public <T> void correlateMessage(String messageName, T data, Map<String, Object> keys) {
    final CorrelateMessageRequest request = CorrelateMessageRequest.builder()
      .messageName(messageName)
      .correlationKeys(PayloadUtil.extractCorrelationKeys(keys))
      .processVariables(PayloadUtil.extractPayload(mapper.convertValue(data, Map.class)))
      .build();
    final HttpEntity<CorrelateMessageRequest> entity = new HttpEntity<>(request, HttpUtils.getHeaders());
    restTemplate.exchange(
      "/message",
      HttpMethod.POST,
      entity,
      new ParameterizedTypeReference<>() {}
    );
  }
}
