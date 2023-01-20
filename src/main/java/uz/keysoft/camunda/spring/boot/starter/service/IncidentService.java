package uz.keysoft.camunda.spring.boot.starter.service;

import uz.keysoft.camunda.spring.boot.starter.dto.incident.IncidentTask;
import uz.keysoft.camunda.spring.boot.starter.dto.incident.Pagination;

import java.util.List;

public interface IncidentService {

  List<IncidentTask> getIncidentListByProcessId(String processInstanceId);
  List<IncidentTask> getIncidentList(Pagination page);
  void retry(String configuration);

}
