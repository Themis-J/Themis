package com.jdc.themis.dealer.service.rest;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public abstract class RestClientUtils {
	public static GetMethod createGetMethod(final String pUrl, final String[] params)
			throws HttpException, IOException {
		final HttpClient client = new HttpClient();
		final GetMethod mGet = new GetMethod(pUrl);
		mGet.getParams().setContentCharset("ISO-8859-1");
		mGet.getParams().setUriCharset("ISO-8859-1");
		final NameValuePair[] pairs = new NameValuePair[params.length];
		int i = 0;
		for (final String param : params) {
			final String[] value = param.split("\\:");
			final NameValuePair pair = new NameValuePair();
			pair.setName(value[0]);
			pair.setValue(value[1]);
			pairs[i++] = pair;
		}
		mGet.setQueryString(pairs);
		System.out.println("calling url: " + mGet.getPath());
		final Header mtHeader = new Header();
		mtHeader.setName("content-type");
		mtHeader.setValue("application/json");
		mtHeader.setName("accept");
		mtHeader.setValue("application/json");
		mGet.addRequestHeader(mtHeader);
		client.executeMethod(mGet);
		return mGet;
	}

	public static PostMethod createPostMethod(final String url, final StringRequestEntity data)
			throws HttpException, IOException {
		final HttpClient client = new HttpClient();
		
		final PostMethod mPost = new PostMethod(url);
		System.out.println("calling url: " + mPost.getPath());
		final Header mtHeader = new Header();
		mtHeader.setName("content-type");
		mtHeader.setValue("application/json");
		mtHeader.setName("accept");
		mtHeader.setValue("application/json");
		mPost.addRequestHeader(mtHeader);
		mPost.setRequestEntity(data);
		client.executeMethod(mPost);
		return mPost;
	}
	

}
