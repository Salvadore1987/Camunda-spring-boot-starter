package uz.keysoft.camunda.spring.boot.starter.service;

import uz.keysoft.camunda.spring.boot.starter.dto.incident.IncidentTask;
import uz.keysoft.camunda.spring.boot.starter.dto.task.CamundaTask;

import java.util.List;

public interface IncidentService {

  List<IncidentTask> getIncidentListByProcessId(String processInstanceId);
  void retry(String taskId);

}
