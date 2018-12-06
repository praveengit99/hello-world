package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;


import org.apache.http.Header;

import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;


public class GetAPITest extends TestBase{
	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closebaleHttpResponse;
	
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException{
		testBase = new TestBase();
		serviceUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		
		//https://jsonplaceholder.typicode.com/todos/
		
		
		url = serviceUrl + apiUrl;
		
	}
	
	
	@Test
	@Parameters({"id"})

	public  void getAPITestWithoutHeaders(String id) throws ClientProtocolException, IOException{
		restClient = new RestClient();
		closebaleHttpResponse = restClient.get(url+id);
		
		//Status Code
		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		System.out.println("url--->"+ url);
		System.out.println("Status Code--->"+ statusCode);
		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

		// Json String:
		String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API---> "+ responseJson);
		
		//get the value from JSON ARRAY:
		String userId = TestUtil.getValueByJPath(responseJson, "userId");
		String title = TestUtil.getValueByJPath(responseJson, "/title");
		String completed = TestUtil.getValueByJPath(responseJson, "/completed");
		System.out.println(userId);
		System.out.println(title);
		System.out.println(completed);
		
//		// All Headers
//		Header[] headersArray =  closebaleHttpResponse.getAllHeaders();
//		HashMap<String, String> allHeaders = new HashMap<String, String>();	
//		for(Header header : headersArray){
//			allHeaders.put(header.getName(), header.getValue());
//		}	
//		System.out.println("Headers Array-->"+allHeaders);
		
		
		
	}
	

}
