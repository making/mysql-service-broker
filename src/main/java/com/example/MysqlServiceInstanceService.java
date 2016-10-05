package com.example;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.servicebroker.exception.ServiceInstanceExistsException;
import org.springframework.cloud.servicebroker.model.*;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MysqlServiceInstanceService implements ServiceInstanceService {

	private final JdbcTemplate jdbcTemplate;

	public MysqlServiceInstanceService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public CreateServiceInstanceResponse createServiceInstance(
			CreateServiceInstanceRequest request) {
		List<Map<String, Object>> databases = jdbcTemplate.queryForList("SHOW DATABASES");
		String database = Database
				.nameFromServiceInstanceId(request.getServiceInstanceId());
		if (databases.stream().filter(db -> database.equals(db.get("Database"))).findAny()
				.isPresent()) {
			throw new ServiceInstanceExistsException(request.getServiceInstanceId(),
					request.getServiceDefinitionId());
		}
		jdbcTemplate.execute("CREATE DATABASE " + database);
		return new CreateServiceInstanceResponse();
	}

	@Override
	public GetLastServiceOperationResponse getLastOperation(
			GetLastServiceOperationRequest request) {
		return new GetLastServiceOperationResponse()
				.withOperationState(OperationState.SUCCEEDED);
	}

	@Override
	public DeleteServiceInstanceResponse deleteServiceInstance(
			DeleteServiceInstanceRequest request) {
		String database = Database
				.nameFromServiceInstanceId(request.getServiceInstanceId());
		jdbcTemplate.execute("DROP DATABASE " + database);
		return new DeleteServiceInstanceResponse();
	}

	@Override
	public UpdateServiceInstanceResponse updateServiceInstance(
			UpdateServiceInstanceRequest request) {
		return new UpdateServiceInstanceResponse();
	}
}
