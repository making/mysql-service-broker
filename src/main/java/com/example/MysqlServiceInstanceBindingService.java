package com.example;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.cloud.servicebroker.model.CreateServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MysqlServiceInstanceBindingService implements ServiceInstanceBindingService {

	private final JdbcTemplate jdbcTemplate;

	public MysqlServiceInstanceBindingService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public CreateServiceInstanceBindingResponse createServiceInstanceBinding(
			CreateServiceInstanceBindingRequest request) {
		String database = Database
				.nameFromServiceInstanceId(request.getServiceInstanceId());
		String username = Database.usernameFromBindingId(request.getBindingId());
		String password = UUID.randomUUID().toString();
		URI uri = Database.uriFromDataSource(jdbcTemplate.getDataSource());
		jdbcTemplate
				.execute("CREATE USER " + username + " IDENTIFIED BY '" + password + "'");
		jdbcTemplate.execute(
				"GRANT ALL PRIVILEGES ON `" + database + "`.* TO '" + username + "'@'%'");

		Map<String, Object> credentials = new HashMap<>();
		credentials.put("uri", uri.getHost());
		credentials.put("name", database);
		credentials.put("username", username);
		credentials.put("password", password);
		credentials.put("port", uri.getPort());
		credentials.put("hostname", "localhost");
		credentials.put("jdbcUrl",
				"jdbc:mysql://" + uri.getHost() + ":" + uri.getPort() + "/" + database);
		credentials.put("uri",
				"mysql://" + username + ":" + password + "@" + uri.getHost() + ":"
						+ uri.getPort() + "/" + database + "?reconnect=true");
		return new CreateServiceInstanceAppBindingResponse().withCredentials(credentials);
	}

	@Override
	public void deleteServiceInstanceBinding(
			DeleteServiceInstanceBindingRequest request) {
		String username = Database.usernameFromBindingId(request.getBindingId());
		jdbcTemplate.execute("DROP USER " + username);
	}
}
