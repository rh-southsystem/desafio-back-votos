package com.southsystem.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.southsystem.service.exception.CouldNotCallCPFValidationServiceException;

@Service
public class CPFValidatorService {

	private static final String API_URL = "https://user-info.herokuapp.com/users/";
	private static final Logger LOGGER = LoggerFactory.getLogger(CPFValidatorService.class);

	public Integer callApiValidation(String cpf) {
		LOGGER.info("Calling CPF validation service...");
		Integer statusCode = null;
		try {
			final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
					.connectTimeout(Duration.ofSeconds(10)).build();

			HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(API_URL + cpf))
					.header("Content-Type", "application/json").build();

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			statusCode = response.statusCode();
			LOGGER.info("Request finished. Status code: " + statusCode);

		} catch (Exception e) {
			LOGGER.error("Request failed");
			e.printStackTrace();
			throw new CouldNotCallCPFValidationServiceException();
		}

		return statusCode;

	}

}
