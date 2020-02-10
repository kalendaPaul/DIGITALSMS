package com.dewcis.sms;

import java.util.logging.Logger;
import java.io.IOException;
import javax.swing.JOptionPane;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Call;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.SSLSocketFactory;

import java.security.cert.CertificateException;

import org.json.JSONObject;

public class httpClient {
	Logger log = Logger.getLogger(httpClient.class.getName());

	// String result = null;
	// String token = null;
	// String refreshToken = null;
	OkHttpClient client = null;
	

private static OkHttpClient getUnsafeOkHttpClient() {
	try {
		// Create a trust manager that does not validate certificate chains
		final TrustManager[] trustAllCerts = new TrustManager[] {
		new X509TrustManager() {
		@Override
		public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		return new java.security.cert.X509Certificate[]{};
		}
		}
		};

		// Install the all-trusting trust manager
		final SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
		// Create an ssl socket factory with our all-trusting manager
		final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
		builder.hostnameVerifier(new HostnameVerifier() {
		@Override
		public boolean verify(String hostname, SSLSession session) {
		return true;
		}
		});

		OkHttpClient okHttpClient = builder.build();
		return okHttpClient;
		} catch (Exception e) {
		throw new RuntimeException(e);
	}
}
	
public String getTokens(String bURL, String sData) {
   // String json = "{\"id\":1,\"name\":\"John\"}";
		client = getUnsafeOkHttpClient();
		String result = null;

	try {


	    RequestBody body = RequestBody.create(
	    MediaType.parse("application/json"), sData);

	    Request request = new Request.Builder()
		  .header("X-Requested-With", "XMLHttpRequest")
	      .url(bURL)
	      .post(body)
	      .build();

	    Call call = client.newCall(request);
	    Response response = call.execute();
	    result = response.body().string();     // Get response
	    int responseCode = 0;

		if ((responseCode = response.code()) == 200) {

		    System.out.println("Response body : " + result);
		    for(int x=1; x<=6; x++){
				System.out.println("               ");
			}
	    	// Transform reponse to JSon Object
           JSONObject json = new JSONObject(result);
           JOptionPane.showMessageDialog(null, json.getString("msg"));
           // Use the JSon Object to get token value
           // token = json.getString("token");
           // refreshToken = json.getString("refreshToken");

		}else if((responseCode = response.code()) != 200) {
			JSONObject jObject = new JSONObject(result);
			JOptionPane.showMessageDialog(null, jObject.getString("msg"));
			System.out.println("IO Error : " + jObject);
		}


   } catch (IOException ex) {
		System.out.println("IO Error : " + ex);
	}

   return result;
}

public String getRefreshTokens(String bURL, String refreshToken) {
	client = getUnsafeOkHttpClient();
	String result = null;
	try {

	    Request request = new Request.Builder()
                .url(bURL)
                .header("X-Requested-With", "XMLHttpRequest")
                .header("X-Authorization", "Bearer"+refreshToken+"" )
                .header("Content-Type", "application/json")
                .build();

	    Call call = client.newCall(request);
	    Response response = call.execute();
	    result = response.body().string();     // Get response
	    int responseCode = 0;

		if ((responseCode = response.code()) == 200) {

		    System.out.println("Response body Token refresh : " + result);
		    for(int x=1; x<=6; x++){
				System.out.println("               ");
			}
		    if(result.length()!=0){
				System.out.println("Tokens have expired");
				for(int x=1; x<=6; x++){
				System.out.println("               ");
			}
		    	// Transform reponse to JSon Object
	           JSONObject json = new JSONObject(result);
				result = json.getString("token");
		    }
	    	
		}else if((responseCode = response.code()) != 200) {
			JSONObject jObject = new JSONObject(result);
			JOptionPane.showMessageDialog(null, jObject.getString("msg"));
			System.out.println("IO Error : " + jObject);
		}


   } catch (IOException ex) {
		System.out.println("IO Error : " + ex);
	}

   return result;
}

public String post(String bURL, String token, String jbody) {
	client = getUnsafeOkHttpClient();
	String result = null;
	try {

		RequestBody body = RequestBody.create(
	    MediaType.parse("application/json"), jbody);

	    Request request = new Request.Builder()
                .url(bURL)
                .header("X-Authorization", "Bearer "+token+"" )
                // .header("Content-Type", "application/json")
                .header("X-Requested-With", "XMLHttpRequest")
                .post(body)
                .build();

	    Call call = client.newCall(request);
	    Response response = call.execute();
	    result = response.body().string();     // Get response
	    int responseCode = 0;

		if ((responseCode = response.code()) == 200) {

		    System.out.println("Response body bulksms : " + result);
		   for(int x=1; x<=6; x++){
				System.out.println("               ");
			}
//		    if(result.isEmpty()){
//		    	// Transform reponse to JSon Object
//	           JSONObject json = new JSONObject(result);
//	           token = json.getString("token");
//		    }
	    	
		}else if((responseCode = response.code()) != 200) {
			// JSONObject jObject = new JSONObject(result);
			// JOptionPane.showMessageDialog(null, jObject.getString("msg"));
			System.out.println("IO Error Response body bulksms : " + result);
		}


   } catch (IOException ex) {
		System.out.println("IO Error : " + ex);
	}

   return result;
}
	
}

