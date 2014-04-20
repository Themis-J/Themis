package com.jdc.themis.dealer.service.rest;

import static com.jdc.themis.dealer.service.rest.RestClientUtils.createGetMethod;
import static com.jdc.themis.dealer.service.rest.RestClientUtils.createPostMethod;
import junit.framework.Assert;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

public class DealerSalesLedgerRestServiceTest {
	private final static String ROOT_URL = "http://localhost:8080/themis/dealer/";
	//private final static String ROOT_URL = "http://115.28.15.122:8080/themis/dealer/";
	
	@Test
	public void verifySalesLedger() throws Exception {
		final StringRequestEntity requestEntity = new StringRequestEntity(
			      "{" +
			      "\"dealerID\": 1," +
			      "\"workOrderNo\": 1, " +
				  "\"saleDate\": \"2014-04-10\"," +
				  "\"validDate\": \"2014-04-11\"," +
				  "\"updatedBy\": \"chenkai\" " +
				  "}",
			    "application/json",
			    "UTF-8");
		
		final PostMethod mPost = createPostMethod(ROOT_URL + "postSalesLedger",
				requestEntity);
		final String output = mPost.getResponseBodyAsString();
		mPost.releaseConnection();
		System.out.println("response : " + output);
		final ObjectMapper mapper = new ObjectMapper();
		final GeneralSaveResponse response = mapper.readValue(output.getBytes(),
				GeneralSaveResponse.class);
		Assert.assertNotNull(response);
		Assert.assertEquals(Boolean.TRUE, response.getSuccess());
		
		// query employee fee journal and try to match the saved journal
		final GetMethod mGet = createGetMethod(ROOT_URL + "postSalesLedger",
				new String[] { "workOrderNo:1", });
		final String getOutput = mGet.getResponseBodyAsString();
		mGet.releaseConnection();
		System.out.println("response : " + new String(getOutput.getBytes("ISO-8859-1")));
		final ObjectMapper getMapper = new ObjectMapper();
		final GetDealerPostSalesLedgerResponse getResponse = getMapper.readValue(getOutput.getBytes(),
				GetDealerPostSalesLedgerResponse.class);
		Assert.assertNotNull(getResponse);
		Assert.assertEquals("2014-04-10", getResponse.getPostSalesLedger().get(0).getSaleDate().toString());
	}
}
