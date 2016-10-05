package com.example;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

public class Database {
	public static String nameFromServiceInstanceId(String serviceInstanceId) {
		UUID uuid = UUID.fromString(serviceInstanceId); // check format
		return "cf_" + uuid.toString().replace("-", "_");
	}

	public static String usernameFromBindingId(String bindingId) {
		UUID uuid = UUID.fromString(bindingId); // check format
		return uuid.toString().replace("-", "");
	}

	public static URI uriFromDataSource(DataSource dataSource) {
		try {
			String url = DataSourceUtils.getConnection(dataSource).getMetaData().getURL()
					.replace("jdbc:", "");
			return new URI(url);
		}
		catch (SQLException | URISyntaxException e) {
			throw new IllegalStateException(e);
		}
	}
}
