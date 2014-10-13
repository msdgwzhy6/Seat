package com.oxygen.feedback;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.oxygen.activity.LoadingActivity;
import com.oxygen.activity.LoginActivity;
import com.oxygen.feedback.MultiMailsender.*;

public class SetMail {
	
	public static String content = null;
	public static String Log = "";
	public static String info = "";
	
	
	
	public static void setMail(){
		MultiMailSenderInfo mailInfo = new MultiMailSenderInfo(); 
	    mailInfo.setMailServerHost("smtp.126.com"); 
	    mailInfo.setMailServerPort("25"); 
	    mailInfo.setValidate(true); 
	    mailInfo.setUserName("bookseat@126.com"); 
	    mailInfo.setPassword("wybookseat");//您的邮箱密码 
	    mailInfo.setFromAddress("bookseat@126.com"); 
	    mailInfo.setToAddress("tobookseat@126.com"); 
	    mailInfo.setSubject("重邮图书馆订座APP用户反馈"); //设置邮箱标题
	    mailInfo.setContent(content);//设置邮箱内容
//	    String[] receivers = new String[]{"oxygen0106@163.com"}; 
//	    String[] ccs = receivers; 
//	    mailInfo.setReceivers(receivers); 
//	    mailInfo.setCcs(ccs); 

	    //这个类主要来发送邮件 
	    MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
	    MultiMailsender sms = new MultiMailsender(); 
	    sms.sendTextMail(mailInfo);//发送文体格式 
//	    MultiMailsender.sendHtmlMail(mailInfo);//发送html格式 
//	    MultiMailsender.sendMailtoMultiCC(mailInfo);//发送抄送 
	}
	
	public static void setLog(){
		MultiMailSenderInfo mailInfo = new MultiMailSenderInfo(); 
	    mailInfo.setMailServerHost("smtp.126.com"); 
	    mailInfo.setMailServerPort("25"); 
	    mailInfo.setValidate(true); 
	    mailInfo.setUserName("bookseat@126.com"); 
	    mailInfo.setPassword("wybookseat");//您的邮箱密码 
	    mailInfo.setFromAddress("bookseat@126.com"); 
	    mailInfo.setToAddress("tobookseat@126.com"); 
	    mailInfo.setSubject("重邮订座-用户日志"); //设置邮箱标题
	    mailInfo.setContent(Log);//设置邮箱内容
//	    String[] receivers = new String[]{"oxygen0106@163.com"}; 
//	    String[] ccs = receivers; 
//	    mailInfo.setReceivers(receivers); 
//	    mailInfo.setCcs(ccs); 

	    //这个类主要来发送邮件 
	    MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
	    MultiMailsender sms = new MultiMailsender(); 
	    sms.sendTextMail(mailInfo);//发送文体格式 
//	    MultiMailsender.sendHtmlMail(mailInfo);//发送html格式 
//	    MultiMailsender.sendMailtoMultiCC(mailInfo);//发送抄送 
	}
	
	
	
	
	
}
