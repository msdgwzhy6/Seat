package com.oxygen.seat;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.oxygen.activity.WifiActivity;



public class LoginWifi {
	
	private static DefaultHttpClient  httpClient = new  DefaultHttpClient();
	//private static String Url = "http://deptweb.cqupt.edu.cn";
	//private static String Url = "http://202.202.32.55";
//private static String Url = "http://www.baidu.com";
	public static boolean isServer = false;
	public static boolean isLogined = false;
	public static boolean isSuccess = false;
	public static String Location = "http://202.202.32.116/portalReceiveAction.do?wlanacname=WLAN-CQUPT";
	public static String token = "";
	public static String wifiIP = "";
	public static String IP192 = "";
	public static String rand = "";
    public static String info="";
	public static String loginUrl = "http://202.202.32.116/portalAuthAction.do";
	public static String quitUrl = "http://202.202.32.116//portalDisconnAction.do";
	
	
	

	public static void connect(String url){
		HttpGet httpGet = new HttpGet(url);
		//设置get请求的header值
		httpGet.addHeader("Accept","text/html, application/xhtml+xml, */*");
		httpGet.addHeader("Accept-Language","zh-CN");
        httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko");
        httpGet.addHeader("Accept-Encoding","gzip, deflate");
        httpGet.addHeader("Connection","Keep-Alive");
        //发送get请求
        HttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
System.out.println("connect()ClientProtocolException");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
System.out.println("connect()ClientProtocolException");
			e.printStackTrace();
		}
        
		if(response.getFirstHeader("Location")!=null){
			Header locationHeader = response.getFirstHeader("Location");
			Location = locationHeader.getValue();
System.out.println("Output Location: "+Location);
		}
		
//		HttpEntity entity = response.getEntity();
//	    while(entity!= null){
//	       System.out.println(EntityUtils.toString(entity,"utf-8"));
//	    }
	        httpGet.abort();       
	}
	
	private static boolean getParams(){
		HttpGet httpGet = new HttpGet(Location);

		//设置get请求的header头文件
		httpGet.addHeader("Accept","text/html, application/xhtml+xml, */*");
		//httpGet.addHeader("Content-Type","application/x-www-form-urlencoded");
		httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko");
        httpGet.addHeader("Accept-Encoding","gzip, deflate");
        httpGet.addHeader("Connection","Keep-Alive");
        httpGet.addHeader("Accept-Language","zh-CN");
        //httpGet.addHeader("Cache-Control","no-cache");
        //发送get请求
        HttpResponse response = null;

		try {
			response = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
System.out.println("-------------FirstGetClientProtocolException--------------");			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
System.out.println("-------------FirstGetIOException--------------");
			e.printStackTrace();
		}
/*
        //获得cookie
        firstCookiestore=httpClient.getCookieStore();
        //httpclient2.setCookieStore(cookiestore);
        //得到cookie中的jsessionid
		List<Cookie> cookieList = firstCookiestore.getCookies();
		for(int i= 0;i<cookieList.size();i++){
			cookieList.get(i).getValue();
			//System.out.println(ck.get(i).getValue());
			jSessionId = cookieList.get(i).getValue();
		}
*/
//HttpEntity httpEntity = response.getEntity();
//System.out.println(EntityUtils.toString(httpEntity,"utf-8"));

			try {
				getLt(response);
			} catch (IOException e) {
System.out.println("getLt Exception");				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			
        
        //输出响应的关键字
System.out.println("wifi first connect"+response.getStatusLine());
       if(response.getStatusLine().getStatusCode()==200&&rand.equals("")==false){
    	   httpGet.abort();
    	   return true;
       }else
    	   return false;
	}
	
	
	public static boolean login(String url) throws Exception{
		//connect(Url);
		isServer = getParams();
System.out.println(String.valueOf(isServer));		

		HttpPost httpPost = new HttpPost(url);
		
		httpPost.addHeader("Accept","text/html, application/xhtml+xml, */*");
		httpPost.addHeader("Referer","http://202.202.32.116/portalReceiveAction.do?wlanacname=WLAN-CQUPT&wlanuserip=172.31.51.3&ssid=WLAN-CQUPT");
		httpPost.addHeader("Accept-Language","zh-CN");
		httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
		httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko");
		httpPost.addHeader("Accept-Encoding","gzip, deflate");
		httpPost.addHeader("Connection","Keep-Alive");
		httpPost.addHeader("Cache-Control","no-cache");
//httpPost.addHeader("Cookie",("userInfo="+LoginActivity.username+"; savepass=; JSESSIONID="+jSessionId));

System.out.println(WifiActivity.username);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
		nvps.add(new BasicNameValuePair("org.apache.struts.taglib.html.TOKEN",token));
		nvps.add(new BasicNameValuePair("wlanuserip",wifiIP));
		nvps.add(new BasicNameValuePair("wlanacname","WLAN-CQUPT"));
		nvps.add(new BasicNameValuePair("chal_id",""));
		nvps.add(new BasicNameValuePair("chal_vector",""));
		nvps.add(new BasicNameValuePair("auth_type","CHAP"));
		nvps.add(new BasicNameValuePair("seq_id",""));
		nvps.add(new BasicNameValuePair("req_id",""));
		nvps.add(new BasicNameValuePair("wlanacIp",IP192));
		nvps.add(new BasicNameValuePair("ssid","WLAN-CQUPT"));
		nvps.add(new BasicNameValuePair("mac",""));
		nvps.add(new BasicNameValuePair("message",""));
		nvps.add(new BasicNameValuePair("bank_acct",""));
		nvps.add(new BasicNameValuePair("isCookies",""));
		nvps.add(new BasicNameValuePair("listpasscode","1"));
		nvps.add(new BasicNameValuePair("randstr",rand));
		nvps.add(new BasicNameValuePair("domain",""));
		nvps.add(new BasicNameValuePair("isRadiusProxy","false"));
		//nvps.add(new BasicNameValuePair("",""));
		nvps.add(new BasicNameValuePair("userid",WifiActivity.username));
		nvps.add(new BasicNameValuePair("passwd",WifiActivity.password));
		nvps.add(new BasicNameValuePair("%CC%E1%BD%BB","%B5%C7+%C2%BC"));
System.out.println(nvps);
		
		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvps,"UTF-8");
    	InputStream inputStream = uefEntity.getContent();
    	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
    	String tempLine = reader.readLine();
    	while (tempLine != null){ 
    		System.out.println(tempLine);
    		tempLine = reader.readLine();
    	 }
        reader.close();
        inputStream.close();
		
	    httpPost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
	    HttpResponse response = httpClient.execute(httpPost);
	    HttpResponse tmpresponse = response;
/*
		if(response.getFirstHeader("Location")!=null){
			Header locationHeader = response.getFirstHeader("Location");
			Location = locationHeader.getValue();
System.out.println("得到Location: "+Location);
		}else{
System.out.println("没有得到Location值");	
		}
		
*/		

	    //HttpEntity httpEntity = response.getEntity();

//HttpEntity tmpEntity = tmpresponse.getEntity();
//System.out.println(EntityUtils.toString(tmpEntity,"utf-8"));
//System.out.println(tmpresponse.getStatusLine());
//System.out.println(tmpresponse.getStatusLine().getStatusCode());
	    //if(response.getStatusLine().getStatusCode()==302){
		//Android没有跳转 直接返回200
//title title_bg1">无线上网认证成功</div>

	if(response!=null){
		System.out.println("respones is not null");
		System.out.println(response.getStatusLine().getStatusCode());
	}else{
		System.out.println("respones is null");
		}	
			
			HttpEntity httpEntity = response.getEntity();
			InputStream input = httpEntity.getContent();
System.out.println("----------------------------");
	        BufferedReader readerStream = new BufferedReader(new InputStreamReader(input, "GB2312"));
	        String tmpLine = readerStream.readLine();
	        String info_str = "title title_bg1\">";

	        
	        while (tmpLine != null){  
System.out.println(tmpLine);

	        		int index_info = tmpLine.indexOf(info_str);  
	        		if(index_info != -1) {  

	        			String s1 = tmpLine.substring(index_info + info_str.length());  
	        			int index1 = s1.indexOf("<");  
	        			if(index1 != -1) {
	        				info = s1.substring(0, index1);
	        				break;
	        			}
	        		}
	        	tmpLine = readerStream.readLine(); 
	        }
	        if(LoginWifi.info.equals("无线上网认证成功")==true){
System.out.println("---------------!!!!!------"+info);		
	    		httpPost.abort();
	    		return true;
	        }else{
	        	
System.out.println("LoginWifi.info无线上网认证失败,httpPost.abort");
System.out.println("info is -------:"+info);
	        	httpPost.abort();
		    	return false;
	        }
	}
         
	

	
	private static void getLt(HttpResponse httpRespone) throws IOException {
		
        HttpEntity httpEntity = httpRespone.getEntity();
        InputStream inputStream = httpEntity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                        inputStream));
        String tempLine = reader.readLine();
        //双引号转义符
        //String token_str = "<input type=\"hidden\" name=\"lt\" value=\""; 
        String token_str = "name=\"org.apache.struts.taglib.html.TOKEN\" value=\"";
        String wifiIP_str = "name=\"wlanuserip\" value='";
        String IP192_str = "name=\"wlanacIp\" value='";
        String rand_str = "name=\"randstr\" id=\"randstr\" value='";

        while (tempLine != null){  
        	//System.out.println(tempLine);
        	if(token==""){
        		int index_token = tempLine.indexOf(token_str);  
        		if(index_token != -1) {  
        			String s1 = tempLine.substring(index_token + token_str.length());  
        			int index1 = s1.indexOf("\"");  
        			if(index1 != -1) {
        				token = s1.substring(0, index1);  
        			}
        		}
        	}else{ 
        		if(wifiIP==""){
        			int index_wifi = tempLine.indexOf(wifiIP_str);
        			if(index_wifi != -1) {  
        				String s2 = tempLine.substring(index_wifi + wifiIP_str.length());  
        				int index2 = s2.indexOf("'");  
        				if(index2 != -1) {
        					wifiIP = s2.substring(0, index2);  
        				}
        			}
        		}else{ 
        			if(IP192==""){
        				int index_192 = tempLine.indexOf(IP192_str);
        				if(index_192 != -1) {  
        					String s3 = tempLine.substring(index_192 + IP192_str.length());  
        					int index3 = s3.indexOf("'");  
        					if(index3 != -1) {
        						IP192 = s3.substring(0, index3);  
        					}
        				}
        			}else{ 
        				if(rand==""){
        					int index_rand = tempLine.indexOf(rand_str);
        					if(index_rand != -1) {  
        						String s4 = tempLine.substring(index_rand + rand_str.length());  
        						int index4 = s4.indexOf("'");  
        						if(index4 != -1) {
        							rand = s4.substring(0, index4);  
        						}
        					}
        				}else{
        					break;
        				}
        			}
        		}
        	}
        	
           tempLine = reader.readLine(); 
        }

}
	
	public static boolean quit(String url) throws Exception{
		//connect(Url);

		HttpPost httpPost = new HttpPost(url);
		
		httpPost.addHeader("Accept","text/html, application/xhtml+xml, */*");
		httpPost.addHeader("Referer","http://202.202.32.116/portalReceiveAction.do?wlanacname=WLAN-CQUPT&wlanuserip=172.31.51.3&ssid=WLAN-CQUPT");
		httpPost.addHeader("Accept-Language","zh-CN");
		httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
		httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko");
		httpPost.addHeader("Accept-Encoding","gzip, deflate");
		httpPost.addHeader("Connection","Keep-Alive");
		httpPost.addHeader("Cache-Control","no-cache");
//httpPost.addHeader("Cookie",("userInfo="+LoginActivity.username+"; savepass=; JSESSIONID="+jSessionId));

System.out.println(WifiActivity.username);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
		nvps.add(new BasicNameValuePair("org.apache.struts.taglib.html.TOKEN",token));
		nvps.add(new BasicNameValuePair("wlanuserip",wifiIP));
		nvps.add(new BasicNameValuePair("wlanacname","WLAN-CQUPT"));
		nvps.add(new BasicNameValuePair("portalUrl",""));
		nvps.add(new BasicNameValuePair("usertime","0"));
		nvps.add(new BasicNameValuePair("wlanacIp",IP192));
		nvps.add(new BasicNameValuePair("message",""));
		nvps.add(new BasicNameValuePair("ssid",""));
		nvps.add(new BasicNameValuePair("%CC%E1%BD%BB","%C0%EB+%CF%DF"));
System.out.println(nvps);
		
		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvps,"UTF-8");
    	InputStream inputStream = uefEntity.getContent();
    	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
    	String tempLine = reader.readLine();
    	while (tempLine != null){ 
    		System.out.println(tempLine);
    		tempLine = reader.readLine();
    	 }
        reader.close();
        inputStream.close();
		
	    httpPost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
	    HttpResponse response = httpClient.execute(httpPost);
	    HttpResponse tmpresponse = response;
/*
		if(response.getFirstHeader("Location")!=null){
			Header locationHeader = response.getFirstHeader("Location");
			Location = locationHeader.getValue();
System.out.println("得到Location: "+Location);
		}else{
System.out.println("没有得到Location值");	
		}
		
*/		

	    //HttpEntity httpEntity = response.getEntity();

//HttpEntity tmpEntity = tmpresponse.getEntity();
//System.out.println(EntityUtils.toString(tmpEntity,"utf-8"));
//System.out.println(tmpresponse.getStatusLine());
//System.out.println(tmpresponse.getStatusLine().getStatusCode());
	    //if(response.getStatusLine().getStatusCode()==302){
		//Android没有跳转 直接返回200
//title title_bg1">无线上网认证成功</div>

	if(response!=null){
		System.out.println("respones is not null");
		System.out.println(response.getStatusLine().getStatusCode());
	}else{
		System.out.println("respones is null");
		}
				
		if(response.getStatusLine().getStatusCode()==200){
			
			HttpEntity httpEntity = response.getEntity();
			InputStream input = httpEntity.getContent();
System.out.println("----------------------------");
	        BufferedReader readerStream = new BufferedReader(new InputStreamReader(input, "GB2312"));
	        String tmpLine = readerStream.readLine();
	        String info_str = "alert('";

	        
	        while (tmpLine != null){  
//System.out.println(tmpLine);
	        	if(info==""){
	        		int index_info = tmpLine.indexOf(info_str);  
	        		if(index_info != -1) {  

	        			String s1 = tmpLine.substring(index_info + info_str.length());  
	        			int index1 = s1.indexOf("(");  
	        			if(index1 != -1) {
	        				info = s1.substring(0, index1); 
	        				break;
	        			}
	        		}
	        	}
	        	
	        	tmpLine = readerStream.readLine(); 
	        }
System.out.println(info);		
	    	httpPost.abort();
	    	return true;
	    }else{
System.out.println("httpPost.abort");
	    	httpPost.abort();
	    	return false;
	     }
	}	
	
	
	
}
