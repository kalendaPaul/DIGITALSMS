package com.dewcis.sms;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;

import java.net.InetAddress;

import org.json.JSONArray;
import org.json.JSONObject;

public class Device {
	Logger log = Logger.getLogger(Device.class.getName());
	String baseUrl= "https://dtsvc.safaricom.com:8480/api";
	String offerCode = "001010400219";
	
	public static void main(String[] args) {
		String url = "https://dtsvc.safaricom.com:8480/api";
		String username = "Etiqet_apiuser";
		String password = "M@endele02020";

		JSONObject jLogin = new JSONObject();
		jLogin.put("username", username);
		jLogin.put("password", password);

		httpClient client = new httpClient();
		String userFile = "/auth/login";
       	String uri = url + userFile; 
		String result = client.getTokens(uri, jLogin.toString());
		JSONObject json = new JSONObject(result);
		String token = json.getString("token");

		Device log = new Device();
		String refreshToken = log.rfToken(json.getString("refreshToken"));
		if(refreshToken.length()!=0){
			System.out.println("Tokens have expired" + refreshToken.length());
			for(int x=1; x<=6; x++){
				System.out.println("               ");
			}
			// Transform reponse to JSon Object
			json = new JSONObject(result);
			token = json.getString("token");
			// log.bulkSms(token);
			// // log.sendSms(token);
			// log.subscr(token);
			// log.unsubscr(token);
		}else if (refreshToken.length()==0) {
			log.bulkSms(token);
			// log.sendSms(token);
			// log.subscr(token);
			// log.unsubscr(token);
		
		}

	}

	//refreshing user token in case it expires or before it expires
	public String rfToken(String refreshToken) {
	    String userFile = "/auth/RefreshToken";
	    String uri = baseUrl + userFile;
	    httpClient get = new httpClient();
	    String results = get.getRefreshTokens(uri, refreshToken);

	    return results;

   }

   //get current timestamp
	public String timeStamp() {
	    String results = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); //get current timestamp and its format
	    return results;

   }

   //get system ip address
   public String ipAddress(){
   		// Returns the instance of InetAddress containing 
   		// local host name and address 
	   InetAddress localhost = null;
	   try {
		   localhost = InetAddress.getLocalHost();
	   } catch (UnknownHostException e) {
		   e.printStackTrace();
		   System.out.println("IO Error UnknownHostException : " + e);
	   }
	   String ip = (localhost.getHostAddress()).trim();

        return ip;
   }

   // user subscription 
	public String subscr(String token) {

		JSONObject jdatav1 = new JSONObject();
		jdatav1.put("name", "OfferCode");
		jdatav1.put("value", offerCode);

		JSONObject jdatav2 = new JSONObject();
		jdatav2.put("name", "Msisdn");
		jdatav2.put("value", "748249894");

		JSONObject jdatav3 = new JSONObject();
		jdatav3.put("name", "Language");
		jdatav3.put("value", "1");

		JSONObject jdatav4 = new JSONObject();
		jdatav4.put("name", "CpId");
		jdatav4.put("value", "104");


		JSONArray jdataset = new JSONArray();
		jdataset.put(jdatav1);
		jdataset.put(jdatav2);
		jdataset.put(jdatav3);
		jdataset.put(jdatav4);

		
		JSONObject jdata = new JSONObject();
		jdata.put("data", jdataset);

		JSONObject jsubscr = new JSONObject();
		jsubscr.put("requestId", "17");
		jsubscr.put("requestTimeStamp", timeStamp());
		jsubscr.put("channel", "SMS");
		jsubscr.put("sourceAddress", ipAddress());
		jsubscr.put("operation", "ACTIVATE");
		jsubscr.put("requestParam", jdata);

		System.out.println("Request body in json, values are : " + jsubscr.toString());
		for(int x=1; x<=6; x++){
				System.out.println("               ");
			}
			
	    String userFile = "/public/SDP/activate";
	    String uri = baseUrl + userFile;
	    httpClient get = new httpClient();
	    String results = get.post(uri, token, jsubscr.toString());

	    return results;

   }

   // user De-activation 
	public String unsubscr(String token) {

		JSONObject jdatav1 = new JSONObject();
		jdatav1.put("name", "OfferCode");
		jdatav1.put("value", offerCode);

		JSONObject jdatav2 = new JSONObject();
		jdatav2.put("name", "Msisdn");
		jdatav2.put("value", "254748249894");

		JSONObject jdatav3 = new JSONObject();
		jdatav3.put("name", "CpId");
		jdatav3.put("value", "104");


		JSONArray jdataset = new JSONArray();
		jdataset.put(jdatav1);
		jdataset.put(jdatav2);
		jdataset.put(jdatav3);

		
		JSONObject jdata = new JSONObject();
		jdata.put("data", jdataset);

		JSONObject junsubscr = new JSONObject();
		junsubscr.put("requestId", "17");
		junsubscr.put("requestTimeStamp", timeStamp());
		junsubscr.put("channel", "3");
		junsubscr.put("sourceAddress", ipAddress());
		junsubscr.put("operation", "DEACTIVATE");
		junsubscr.put("requestParam", jdata);

		System.out.println("Response packet json values : " + junsubscr.toString());
		for(int x=1; x<=6; x++){
				System.out.println("               ");
			}

	    String userFile = "/public/SDP/deactivate";
	    String uri = baseUrl + userFile;
	    httpClient get = new httpClient();
	    String results = get.post(uri, token, junsubscr.toString());

	    return results;

   }

	// user bulk sms
	public String bulkSms(String token) {

		JSONArray jdataset = new JSONArray();
		JSONObject jdata = new JSONObject();
		jdata.put("userName", "Etiqet");
		jdata.put("channel", "sms");
		jdata.put("packageId", "4775");
		jdata.put("oa", "SDPTest");
		jdata.put("msisdn", "254748249894,254715668934");
		jdata.put("message", "hello testing online promo nov 1");
		jdata.put("uniqueId", "2500688298721");
		jdata.put("actionResponseURL", "https://posthere.io/c59f-4786-86ac");

		jdataset.put(jdata);

		JSONObject jbulksms = new JSONObject();
		jbulksms.put("timeStamp", timeStamp());
		jbulksms.put("dataSet", jdataset);

		System.out.println("Bulksms Request body fin json, values are : " + jbulksms.toString());
		for(int x=1; x<=6; x++){
				System.out.println("               ");
			}

	    String userFile = "/public/CMS/bulksms";
	    String uri = baseUrl + userFile;
	    httpClient get = new httpClient();
	    String results = get.post(uri, token, jbulksms.toString());


	    return results;

	}

   // user one sms
	public String sendSms(String token) {

		JSONObject jdatav1 = new JSONObject();
		jdatav1.put("name", "LinkId");
		jdatav1.put("value", "00010310189519161781865526");

		JSONObject jdatav2 = new JSONObject();
		jdatav2.put("name", "Msisdn");
		jdatav2.put("value", "254748249894");

		JSONObject jdatav3 = new JSONObject();
		jdatav3.put("name", "Content");
		jdatav3.put("value", "Thank You for Ondemand Subscription SAFRI TEST TUN Subscption test Send sms");

		JSONObject jdatav4 = new JSONObject();
		jdatav4.put("name", "OfferCode");
		jdatav4.put("value", offerCode);

		JSONObject jdatav5 = new JSONObject();
		jdatav5.put("name", "CpId");
		jdatav5.put("value", "104");

		JSONArray jdataset = new JSONArray();
		jdataset.put(jdatav1);
		jdataset.put(jdatav2);
		jdataset.put(jdatav3);
		jdataset.put(jdatav4);
		jdataset.put(jdatav5);

		
		JSONObject jdata = new JSONObject();
		jdata.put("data", jdataset);

		JSONObject jsendsms = new JSONObject();
		jsendsms.put("requestId", "17");
		jsendsms.put("requestTimeStamp", timeStamp());
		jsendsms.put("channel", "3");
		jsendsms.put("sourceAddress", ipAddress());
		jsendsms.put("operation", "SendSMS");
		jsendsms.put("requestParam", jdata);

		System.out.println("Response packet json send following values : " + jsendsms.toString());
		for(int x=1; x<=6; x++){
				System.out.println("               ");
			}


	    String userFile = "/public/SDP/sendSMSRequest";
	    String uri = baseUrl + userFile;
	    httpClient get = new httpClient();
	    String results = get.post(uri, token, jsendsms.toString());

	    return results;

   }

   // CP Notification 
	public void notification(String token) {

		JSONObject jdatav1 = new JSONObject();
		jdatav1.put("name", "OfferCode");
		jdatav1.put("value", offerCode);

		JSONObject jdatav2 = new JSONObject();
		jdatav2.put("name", "Msisdn");
		jdatav2.put("value", "254711572013");

		JSONObject jdatav3 = new JSONObject();
		jdatav3.put("name", "Command");
		jdatav3.put("value", "XXX" ); //Refer Input parameters


		JSONArray jdataset = new JSONArray();
		jdataset.put(jdatav1);
		jdataset.put(jdatav2);
		jdataset.put(jdatav3);

		JSONObject jadditionalData = new JSONObject();
		jadditionalData.put("name", "");
		jadditionalData.put("value", "");

		JSONArray jadditionalDataSet = new JSONArray();
		jadditionalDataSet.put(jadditionalData);
		
		JSONObject jdata = new JSONObject();
		jdata.put("data", jdataset);

		JSONObject jsendsms = new JSONObject();
		jsendsms.put("requestId", "17223344555");
		jsendsms.put("requestTimeStamp", timeStamp());
		jsendsms.put("operation", "CP_NOTIFICATION");
		jsendsms.put("requestParam", jdata);
		jsendsms.put("additionalData", jadditionalDataSet);

		System.out.println("Response packet json values : " + jsendsms.toString());
		for(int x=1; x<=6; x++){
				System.out.println("               ");
			}

	    // String userFile = "/public/SDP/deactivate";
	    // String uri = baseUrl + userFile;
	    // httpClient get = new httpClient();
	    // String results = get.post(uri, token);

	    // return results;

   }


}
