package com.jdc.themis.dealer.service.rest;

import static com.jdc.themis.dealer.service.rest.RestClientUtils.createGetMethod;

import java.util.List;

import junit.framework.Assert;

import org.apache.commons.httpclient.methods.GetMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.jdc.themis.dealer.web.domain.DepartmentDetail;

public class BitHitsApp {

	private final static String ROOT_URL = "http://localhost:8080/themis/dealer/";
	//private final static String ROOT_URL = "http://115.28.15.122:8080/themis/dealer/";
	
	@Test
	public void getDepartments() throws Exception {
		final GetMethod mGet = createGetMethod(ROOT_URL + "department", new String[] {});
		final String output = mGet.getResponseBodyAsString();
		mGet.releaseConnection();

		final ObjectMapper mapper = new ObjectMapper();
		final DepartmentItems items = mapper.readValue(output.getBytes(),
				DepartmentItems.class);
		System.out.println("departments : " + new String(output.getBytes("ISO-8859-1")));

		Assert.assertEquals(8, items.getItems().size());
		Assert.assertEquals("NA", items.getItems().get(0).getName());
	}

	public static class DepartmentItems {
		private List<DepartmentDetail> items;

		public List<DepartmentDetail> getItems() {
			return items;
		}

		public void setItems(List<DepartmentDetail> items) {
			this.items = items;
		}

	}
	
	
}
