package com.hcl.mobileserviceprovider.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.hcl.mobileserviceprovider.service.ConnectionService;
import com.hcl.mobileserviceprovider.service.dto.Connection;
import com.hcl.mobileserviceprovider.service.dto.ConnectionResponse;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ConnectionController.class)
public class ConnectionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ConnectionService connectionService;

	@Test
	public void retrieveDetailsForRequest() throws Exception {

		ConnectionResponse connection = new ConnectionResponse();
		LocalDate localDate = LocalDate.of(2020, 10, 10);
		connection.setStatus("in-progress");
		connection.setUpdateDate(localDate);
		Mockito.when(connectionService.fetchById(Mockito.anyString())).thenReturn(Optional.of(connection));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/connection/{id}", "1234")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "{status:in-progress,updateDate:2020-10-10}";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

}
