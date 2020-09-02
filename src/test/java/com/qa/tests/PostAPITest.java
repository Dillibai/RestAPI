package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.Base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;

public class PostAPITest extends TestBase{
	
	TestBase testBase;
	String serviceURL;
	String apiURL;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException {
		testBase = new TestBase();
		serviceURL = prop.getProperty("URL");
		apiURL = prop.getProperty("serviceURL");
		
		url = serviceURL + apiURL;
	}
	
	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException {
		restClient = new RestClient();
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("content-Type", "application/json");
		
		//jackson API
		ObjectMapper mapper = new ObjectMapper();
		Users users = new Users("morpheus", "leader");  //expected users object
		
		//object to json file
		mapper.writeValue(new File("C:\\Users\\ddillibai\\OneDrive - DXC Production\\Documents\\JAVA\\BDD\\restAPI\\src\\main\\java\\com\\qa\\data\\users.json"), users);
		
		//object to json string (marshelling)
		String userjsonString = mapper.writeValueAsString(users);
		System.out.println(userjsonString);
	
		closeableHttpResponse = restClient.post(url, userjsonString, headerMap);
		
		//validate response from API
		//1. status code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, testBase.RESPONSE_STATUS_CODE_201);
		
		//2.Jsonstring
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("the response from api is : " + responseJson);
		
		//json to javaobject  (unmarshalling)
		Users userobj = mapper.readValue(responseString, Users.class);
		System.out.println(userobj);
		
		System.out.println(users.getName().equals(userobj.getName()));
		
		System.out.println(users.getJob().equals(userobj.getJob()));
	}

}
