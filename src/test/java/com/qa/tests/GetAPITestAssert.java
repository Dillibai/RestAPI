package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;
import org.apache.commons.logging.impl.AvalonLogger;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.Base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetAPITestAssert extends TestBase{
	
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
	
	@Test(priority = 1)
	public void getAPITest() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		closeableHttpResponse = restClient.get1(url);
		
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code : " + statusCode);
		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

		// json string
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response Jsonf from API : " + responseJson);
		
		//per page
		String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("Value of perpage : " + perPageValue);
		
		Assert.assertEquals(perPageValue, "6");
		
		//totalvalue
		String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
		System.err.println("Total value : " + totalValue);
		
		Assert.assertEquals(totalValue, "12");
		
		String lastname = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String firstname = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");
		
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(lastname);
		System.out.println(firstname);


		// all headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers array :" + allHeaders);
	}
	
	@Test(priority = 2)
	public void getAPITestwithHeader() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("content-Type", "application/json");
		
		closeableHttpResponse = restClient.get1(url);
		
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code : " + statusCode);
		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

		// json string
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response Jsonf from API : " + responseJson);
		
		//per page
		String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("Value of perpage : " + perPageValue);
		
		Assert.assertEquals(perPageValue, "6");
		
		//totalvalue
		String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
		System.err.println("Total value : " + totalValue);
		
		Assert.assertEquals(totalValue, "12");
		
		String lastname = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String firstname = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");
		
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(lastname);
		System.out.println(firstname);

		// all headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers array :" + allHeaders);
	}
	
}
