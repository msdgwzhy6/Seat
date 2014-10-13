package com.oxygen.seat;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.os.Environment;

import com.oxygen.activity.LoginActivity;


public class LoginCas {

	
	//private static DefaultHttpClient  httpClient = WebClientDevWrapper.wrapClient(new DefaultHttpClient());
	public static DefaultHttpClient  httpClient = MySSLSocketFactory.getNewHttpClient();
	//private static DefaultHttpClient  httpClient = new  DefaultHttpClient();
	//public static final String Domain_URL = "https://202.202.43.130:8443";
	public static final String Domain_URL = "https://sfrz.cqupt.edu.cn:8443";
	public static String callBack = "/cas/login?service=http%3A%2F%2Fzwyd.cqupt.edu.cn%2F";
	public static boolean isServer = false;
	public static boolean isLogined = false;
	public static boolean isSuccess = false;
	public static CookieStore firstCookiestore;
	public static CookieStore secondCookiestore;
	public static CookieStore thirdCookiestore;
//	public static String library = "";
//	public static String room = "";
//	public static String seat = "";
	public static String captcha = "",lt = "",jSessionId = "";
	public static String Location = "";
	
	public static byte[] seat = new byte[430];
	public static byte[] one = new byte[6];
	public static ArrayList randomID;

	
	
	
	private static boolean getFirstCookie(){
		//String url = Domain_URL+callBack;
		String url = "https://sfrz.cqupt.edu.cn:8443/cas/login?service=http%3A%2F%2Fzwyd.cqupt.edu.cn%2F";
		HttpGet httpGet = new HttpGet(url);
		
		//设置get请求的header头文件
		httpGet.addHeader("Accept","text/html, application/xhtml+xml, */*");
		//httpGet.addHeader("Content-Type","application/x-www-form-urlencoded");
		httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko");
        httpGet.addHeader("Accept-Encoding","gzip, deflate");
        httpGet.addHeader("Connection","Keep-Alive");
        httpGet.addHeader("Accept-Language","zh-CN");
        
        //发送get请求
        HttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			
//HttpEntity httpEntity = response.getEntity();
//System.out.println(EntityUtils.toString(httpEntity,"utf-8"));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
System.out.println("-------------first ClientProtocolException--------------");			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
System.out.println("-------------first IOException--------------");
			e.printStackTrace();
		}
        //获得cookie
        firstCookiestore=httpClient.getCookieStore();
        //得到cookie中的jsessionid
		List<Cookie> cookieList = firstCookiestore.getCookies();
		for(int i= 0;i<cookieList.size();i++){
			cookieList.get(i).getValue();
			//System.out.println(ck.get(i).getValue());
			jSessionId = cookieList.get(i).getValue();
		}
//HttpEntity httpEntity = response.getEntity();
//System.out.println(EntityUtils.toString(httpEntity,"utf-8"));
		//解析出LoginTicket
        try {
        	String tmpLt = getLt(response);
System.out.println(tmpLt.equals(""));
        	if(tmpLt.equals("")==false){
        		lt=tmpLt;
        	}
System.out.println("lt is null:"+lt.equals(""));
System.out.println("get lt:"+lt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
System.out.println("---------first response exception");			
			e.printStackTrace();
		}
        //输出响应的关键字
System.out.println("first connect:"+response.getStatusLine());
       if(lt.equals("")==false&&response.getStatusLine().getStatusCode()==200){
    	   httpGet.abort();
    	   return true;
       }else
    	   return false;
	}
	
	public static boolean login() throws Exception{
		isServer = getFirstCookie();
System.out.println("isServer:"+isServer);
		if(isServer){
			String url = Domain_URL;
			HttpPost httpPost = new HttpPost(url+("/cas/login;jsessionid="+jSessionId+"?service=http%3A%2F%2Fzwyd.cqupt.edu.cn%2F"));
			
			httpPost.addHeader("Accept","text/html, application/xhtml+xml, */*");
			httpPost.addHeader("Referer","https://sfrz.cqupt.edu.cn:8443/cas/login?service=http%3A%2F%2Fzwyd.cqupt.edu.cn%2F");
			httpPost.addHeader("Accept-Language","zh-CN");
			httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
			//httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko");
			httpPost.addHeader("Accept-Encoding","gzip, deflate");
			httpPost.addHeader("Connection","Keep-Alive");
			httpPost.addHeader("Cache-Control","no-cache");
			httpPost.addHeader("Cookie",("userInfo="+LoginActivity.username+"; savepass=; JSESSIONID="+jSessionId));
	
			captcha = getCaptchaCode();//得到验证码
System.out.println("j_captcha_response:"+captcha);
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
			nvps.add(new BasicNameValuePair("username",LoginActivity.username));
			nvps.add(new BasicNameValuePair("password",LoginActivity.password));
			nvps.add(new BasicNameValuePair("j_captcha_response",captcha));
			nvps.add(new BasicNameValuePair("lt",lt));
			nvps.add(new BasicNameValuePair("_eventId","submit"));
			nvps.add(new BasicNameValuePair("submit",""));
System.out.println(nvps);
			
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvps,"UTF-8");
	    	InputStream inputStream = uefEntity.getContent();
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));//
	    	String tempLine = reader.readLine();
	    	while (tempLine != null){ 
	    		System.out.println(tempLine);
	    		tempLine = reader.readLine();
	    	 }
	        reader.close();
	        inputStream.close();
			
		    httpPost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
		    HttpResponse response = httpClient.execute(httpPost);
		 	secondCookiestore = httpClient.getCookieStore(); 
			if(response.getFirstHeader("Location")!=null){
				Header locationHeader = response.getFirstHeader("Location");
				Location = locationHeader.getValue();
System.out.println("Get Location: "+Location);
			}else{
System.out.println("Location is null");	
			}
	        //得到第二个Cookie中的CASPRIVACY 和 CASTGC	TGT
		    List<Cookie> ck = secondCookiestore.getCookies();
		    for(int i= 0;i<ck.size();i++){
		    	ck.get(i).getValue();
System.out.println(ck.get(i).getValue());
		    }  
	HttpEntity httpEntity = response.getEntity();
	//System.out.println(EntityUtils.toString(httpEntity,"utf-8"));
	//
System.out.println(response.getStatusLine());
System.out.println(response.getStatusLine().getStatusCode());
		    //if(response.getStatusLine().getStatusCode()==302){
			//Android没有跳转 直接返回200
			if(ck.size()==3&&ck.get(ck.size()-1).getValue()!=null){
		    	httpPost.abort();
		    	return true;
		    }else{
System.out.println("httpPost.abort");
		    	httpPost.abort();
		    	return false;
		     }
			
		}else{
			//isServer==false
			return false;
		}
         
 }

	private static String getCaptchaCode(){
		String picUrl = "https://sfrz.cqupt.edu.cn:8443/cas/captcha.htm";
		String cookie = "JSESSIONID="+jSessionId;
    	HttpGet httpPic = new HttpGet(picUrl);
    	httpPic.addHeader("Accept","image/png, image/svg+xml, image/*;q=0.8, */*;q=0.5");
    	httpPic.addHeader("Referer","https://sfrz.cqupt.edu.cn:8443/cas/login?service=http%3A%2F%2Fzwyd.cqupt.edu.cn%2F");
		httpPic.addHeader("Accept-Language","zh-CN");
		httpPic.addHeader("Accept-Encoding","gzip, deflate");
		httpPic.addHeader("Connection","Keep-Alive");
		//在Header中第一次get到的cookie值
		httpPic.addHeader("Cookie",cookie);
		String fullFilename = "";
        try  
        {  
        	File extDir = Environment.getExternalStorageDirectory();
        	String filename = "/loginPic.jpg";
        	fullFilename = extDir+filename;
        	HttpResponse httpRespone = httpClient.execute(httpPic);
        	HttpEntity httpEntity = httpRespone.getEntity();
            //得到响应中的流对象
            InputStream inputStream = httpEntity.getContent();
            //包装并读出验证码jpg文件  
            BufferedInputStream bis = new BufferedInputStream(inputStream);  
            File file = new File(fullFilename);  
            FileOutputStream fs = new FileOutputStream(file);  
            byte[] buf = new byte[1024];  
            int len = bis.read(buf);  
            if(len == -1 || len == 0){  
                file.delete();  
                file = null;  
            }  
            while (len != -1) {  
                fs.write(buf, 0, len);  
                len = bis.read(buf);  
            }  
            fs.flush();  
            fs.close();  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();
            System.out.println("Picture is null");
        }
        
//        BufferedImage image = ImgTools.getImage("E:/login.jpg");
        Bitmap image = ImgTools.getImage(fullFilename);//绝对路径
        Bitmap img = ImgTools.getSingleCode(image);
        String code = GetImgCode.compare(img);
        return code;
	}
	
	private static String getLt(HttpResponse httpRespone) throws IOException {
		String str = "";
        HttpEntity httpEntity = httpRespone.getEntity();
        InputStream inputStream = httpEntity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                        inputStream));
        String tempLine = reader.readLine();
        //双引号转义符
        String s = "<input type=\"hidden\" name=\"lt\" value=\""; 

        while (tempLine != null){  
        	//System.out.println(tempLine);
          int index = tempLine.indexOf(s);  
             if(index != -1) {  
            	 String s1 = tempLine.substring(index + s.length());  
            	 int index1 = s1.indexOf("\"");  
            	 if(index1 != -1) {
            		 str = s1.substring(0, index1);
            		 break;	 
            	 }  
             }  
           tempLine = reader.readLine(); 
        }
//System.out.println(str);
        return str;
}
	
	public static void connect(String Location) throws Exception{
		HttpGet httpGet = new HttpGet(Location);
		//设置get请求的header值
		httpGet.addHeader("Accept","text/html, application/xhtml+xml, */*");
		httpGet.addHeader("Accept-Language","zh-CN");
        httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko");
        httpGet.addHeader("Accept-Encoding","gzip, deflate");
        httpGet.addHeader("Connection","Keep-Alive");
        httpGet.addHeader("Cache-Control","no-cache");
        //发送get请求
        HttpResponse response = httpClient.execute(httpGet);
 /*	
        //获得第三个cookie
        thirdCookiestore=httpClient.getCookieStore();
        //得到cookie中的jsessionid
	List<Cookie> ck = thirdCookiestore.getCookies();
		for(int i= 0;i<ck.size();i++){
			ck.get(i).getValue();
//System.out.println(ck.get(i).getValue());
			jSessionId = ck.get(i).getValue();
		}


		if(response.getFirstHeader("Location")!=null){
			Header locationHeader = response.getFirstHeader("Location");
			Location = locationHeader.getValue();
		}
		
//		HttpEntity entity = response.getEntity();
//	    while(entity!= null){
//	       System.out.println(EntityUtils.toString(entity,"utf-8"));
//	    }
 
*/
	        httpGet.abort();       
	}
	
	//从返回的response判断订座系统是否开通
		public static String isOpen(String Location)throws Exception{
			String result = "";
			HttpGet httpGet = new HttpGet(Location);
			//设置get请求的header值
			httpGet.addHeader("Accept","text/html, application/xhtml+xml, */*");
			httpGet.addHeader("Accept-Language","zh-CN");
	        httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko");
	        httpGet.addHeader("Accept-Encoding","gzip, deflate");
	        httpGet.addHeader("Connection","Keep-Alive");
	        httpGet.addHeader("Cache-Control","no-cache");
	        //发送get请求
	        HttpResponse response = httpClient.execute(httpGet);


			if(response!=null){
				System.out.println("get respones");
				System.out.println(response.getStatusLine().getStatusCode());
			}else{
				System.out.println("respones is null");
				}
						
					HttpEntity httpEntity = response.getEntity();
					InputStream input = httpEntity.getContent();
		System.out.println("------read openinfo----------");
			        BufferedReader readerStream = new BufferedReader(new InputStreamReader(input, "UTF-8"));
			        String tmpLine = readerStream.readLine();
			        String info_str = "font20y\">";

			        
			        while (tmpLine != null){  
//System.out.println(tmpLine);
			        	if(result==""){
			        		int index_info = tmpLine.indexOf(info_str);  
			        		if(index_info != -1) {  

			        			String s1 = tmpLine.substring(index_info + info_str.length());  
			        			int index1 = s1.indexOf("！　　　　　</");  
			        			if(index1 != -1) {
			        				result = s1.substring(0, index1);
			        				break;
			        			}
			        		}
			        	}
			        	tmpLine = readerStream.readLine(); 
			        }
	System.out.println(result);		
					httpGet.abort();
			    	return result;
		}
	
		//得到空余座位情况
				public static void isEmpty(String Location)throws Exception{
					//int id = 0;
					int i=0;
					seat = new byte[430];
					HttpGet httpGet = new HttpGet(Location);
					//设置get请求的header值
					httpGet.addHeader("Accept","text/html, application/xhtml+xml, */*");
					httpGet.addHeader("Accept-Language","zh-CN");
			        httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko");
			        httpGet.addHeader("Accept-Encoding","gzip, deflate");
			        httpGet.addHeader("Connection","Keep-Alive");
			        httpGet.addHeader("Cache-Control","no-cache");
			        //发送get请求
			        HttpResponse response = httpClient.execute(httpGet);

//					if(response!=null){
//						System.out.println("get respones");
//						System.out.println(response.getStatusLine().getStatusCode());
//					}else{
//						System.out.println("respones is null");
//						}	
							HttpEntity httpEntity = response.getEntity();
							InputStream input = httpEntity.getContent();
System.out.println("------read emptyinfo----------");
					        BufferedReader readerStream = new BufferedReader(new InputStreamReader(input, "UTF-8"));
					        String tmpLine = readerStream.readLine();
					        String info_str = "class=\"Blue\">";
					        
					        while (tmpLine != null){  
//System.out.println(tmpLine);
					        		int index_info = tmpLine.indexOf(info_str);  
					        		if(index_info != -1) {  

					        			String s1 = tmpLine.substring(index_info + info_str.length());  
					        			int index1 = s1.indexOf("</");  
					        			if(index1 != -1) {
					        				//System.out.println(index1);
					        				//result = s1.substring(0, index1);	
					        				if(!(index1==0)){
					        					seat[i]=1;
					        				}
					        				i++;
					        			}
					        		}

					        	tmpLine = readerStream.readLine(); 
					        }
//System.out.println(result);
//					        for(int j=0;j<seat.length;j++){
//					        	System.out.print(seat[j]);
//					        	if(j!=0&&(j+1)%6==0){
//					        		System.out.println();
//					        	}
//					        }
					        httpGet.abort();
				}
				
				//随机得到空余座位情况
				public static void getRandom(String Location)throws Exception{
					String result;
					int i=0;
					randomID = new ArrayList<Integer>();
					HttpGet httpGet = new HttpGet(Location);
					//设置get请求的header值
					httpGet.addHeader("Accept","text/html, application/xhtml+xml, */*");
					httpGet.addHeader("Accept-Language","zh-CN");
			        httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko");
			        httpGet.addHeader("Accept-Encoding","gzip, deflate");
			        httpGet.addHeader("Connection","Keep-Alive");
			        httpGet.addHeader("Cache-Control","no-cache");
			        //发送get请求
			        HttpResponse response = httpClient.execute(httpGet);

					if(response!=null){
						System.out.println("get respones");
						System.out.println(response.getStatusLine().getStatusCode());
					}else{
						System.out.println("respones is null");
						}	
							HttpEntity httpEntity = response.getEntity();
							InputStream input = httpEntity.getContent();
System.out.println("------read randominfo----------");
					        BufferedReader readerStream = new BufferedReader(new InputStreamReader(input, "UTF-8"));
					        String tmpLine = readerStream.readLine();
					        String tmp2="";
//					        tmp2=tmpLine+" ";
					        String info_str = "Red\">(";
					        String empty_str = "strong>";
					        
					        while (tmpLine != null){  
//System.out.println(tmpLine);
					        	tmp2 = tmpLine +" ";
					        		int index_info = tmpLine.indexOf(info_str);  
					        		if(index_info != -1) {  

					        			String s1 = tmpLine.substring(index_info + info_str.length());  
					        			int index1 = s1.indexOf(")");  
					        			if(index1 != -1) { 
					        				//System.out.println(index1);
					        				result = s1.substring(0, index1);
//System.out.println(result);
					        				if(result.equals("空")){
					        					int index_empty = tmp2.indexOf(empty_str);
//System.out.println("tmp2"+tmp2);
								        		if(index_empty != -1) {  
								        			String s2 = tmp2.substring(index_empty + empty_str.length());  
								        			int index2 = s2.indexOf("-<");
								        			if(index2 != -1) { 
//System.out.println(s2.substring(0, index2));
								        				randomID.add(Integer.valueOf(s2.substring(0, index2)));
//System.out.println(randomID.get(i));
								        			}
					        					
								        		}
								        		i++;
					        				}
					        			}
					        		}
					        	tmpLine = readerStream.readLine();
					        	
					        }
//System.out.println(result);
					        httpGet.abort();    
				}
		
	
		
	//从返回的response中得到“预订成功”
	public static String getInfo(String Location)throws Exception{
		String result = "";
		HttpGet httpGet = new HttpGet(Location);
		//设置get请求的header值
		httpGet.addHeader("Accept","text/html, application/xhtml+xml, */*");
		httpGet.addHeader("Accept-Language","zh-CN");
        httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko");
        httpGet.addHeader("Accept-Encoding","gzip, deflate");
        httpGet.addHeader("Connection","Keep-Alive");
        httpGet.addHeader("Cache-Control","no-cache");
        //发送get请求
        HttpResponse response = httpClient.execute(httpGet);


		if(response!=null){
			System.out.println("get respones");
			System.out.println(response.getStatusLine().getStatusCode());
		}else{
			System.out.println("respones is null");
			}
					
				HttpEntity httpEntity = response.getEntity();
				InputStream input = httpEntity.getContent();
System.out.println("------read bookinfo--------");
		        BufferedReader readerStream = new BufferedReader(new InputStreamReader(input, "UTF-8"));
		        String tmpLine = readerStream.readLine();
		        String info_str = "font22\">";

		        
		        while (tmpLine != null){  
	//System.out.println(tmpLine);
		        	if(result==""){
		        		int index_info = tmpLine.indexOf(info_str);  
		        		if(index_info != -1) {  

		        			String s1 = tmpLine.substring(index_info + info_str.length());  
		        			int index1 = s1.indexOf("！ 你");  
		        			if(index1 != -1) {
		        				result = s1.substring(0, index1);
		        				break;
		        			}
		        		}
		        	}
		        	tmpLine = readerStream.readLine(); 
		        }
System.out.println(result);		
			httpGet.abort();
		    return result;
	}
	
}
