package com.jdc.themis.dealer.service.rest;

import static com.jdc.themis.dealer.service.rest.RestClientUtils.createGetMethod;
import static com.jdc.themis.dealer.service.rest.RestClientUtils.createPostMethod;

import java.util.List;

import junit.framework.Assert;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.jdc.themis.dealer.web.domain.DepartmentDetail;
import com.jdc.themis.dealer.web.domain.VehicleDetail;

public class DealerEntryRestServiceTest {

	// private final static String ROOT_URL =
	// "http://localhost:8080/themis/dealer/";
	private final static String ROOT_URL = "http://115.28.15.122:8080/themis/dealer/";

	
	@Test
	public void getDepartments() throws Exception {
		final GetMethod mGet = createGetMethod(ROOT_URL + "department", new String[] {});
		final String output = mGet.getResponseBodyAsString();
		mGet.releaseConnection();

		final ObjectMapper mapper = new ObjectMapper();
		final DepartmentItems items = mapper.readValue(output.getBytes(),
				DepartmentItems.class);
		System.out.println("departments : " + output);

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

	@Test
	public void getVehiclesByCategoryID() throws Exception {
		final GetMethod mGet = createGetMethod(ROOT_URL + "vehicle",
				new String[] { "categoryID:1" });
		final String output = mGet.getResponseBodyAsString();
		mGet.releaseConnection();
		System.out.println("vehicles : " + output);
		final ObjectMapper mapper = new ObjectMapper();
		final VechicleItems items = mapper.readValue(output.getBytes(),
				VechicleItems.class);

		Assert.assertEquals(23, items.getItems().size());
	}

	public static class VechicleItems {
		private List<VehicleDetail> items;

		public List<VehicleDetail> getItems() {
			return items;
		}

		public void setItems(List<VehicleDetail> items) {
			this.items = items;
		}

	}

	@Test
	public void verifyVehicleSalesJournal() throws Exception {
		final StringRequestEntity requestEntity = new StringRequestEntity(
			      "{\"dealerID\": 11," +
			      "\"departmentID\": 1, " +
				  "\"validDate\": \"2013-08-01\"," +
				  "\"updateBy\": \"chenkai\", " +
				  "\"detail\": " +
				  "  [" +
				  "     {" +
				  "       \"vehicleID\": 2," + 
				  "       \"amount\": 1234.00," +
				  "       \"margin\": 4321.00," +
				  "       \"count\": 134 " +
				  "      }" +
				  "  ]}",
			    "application/json",
			    "UTF-8");
		
		final PostMethod mPost = createPostMethod(ROOT_URL + "vehicleSalesRevenue",
				requestEntity);
		final String output = mPost.getResponseBodyAsString();
		mPost.releaseConnection();
		System.out.println("response : " + output);
		final ObjectMapper mapper = new ObjectMapper();
		final GeneralSaveResponse response = mapper.readValue(output.getBytes(),
				GeneralSaveResponse.class);
		Assert.assertNotNull(response);
		Assert.assertEquals(Boolean.TRUE, response.getSuccess());
	}
	
}
