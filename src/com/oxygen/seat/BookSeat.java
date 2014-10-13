package com.oxygen.seat;



public class BookSeat {
	
	public static String libId = null;
	public static String roomId = null;
	public static String tableId = null;
	public static String seatId = null;
	
	public static String openInfo = "";
	public static String result = null;
	
	public static String tv_lib; 
	public static String tv_room;
	
	public static final byte FULL = 6;//1个座位被订即显示满	
	
	public static void isOpen()throws Exception{
System.out.println("test openinfo from BookSeat");
		openInfo = LoginCas.isOpen("http://zwyd.cqupt.edu.cn/manage/secure/xsyls?room_id=1302");
	}
	
	public static void emptyInfo() throws Exception{
System.out.println("test isEmpty from BookSeat");		
System.out.println("http://zwyd.cqupt.edu.cn/manage/secure/book?c="+libId);
		LoginCas.connect("http://zwyd.cqupt.edu.cn/manage/secure/book?c="+libId);
System.out.println("http://zwyd.cqupt.edu.cn/manage/secure/xsyls?room_id="+roomId);			
		LoginCas.isEmpty("http://zwyd.cqupt.edu.cn/manage/secure/xsyls?room_id="+roomId);
	}
	
	public static void getLoction(){
		//判断图书馆
				if(BookSeat.libId.equals("1")){
					tv_lib = "老图书馆";
					if(BookSeat.roomId.equals("1202")){
						tv_room = "二楼社科借阅处";
					}else if(BookSeat.roomId.equals("1203")){
						tv_room = "二楼法学阅览室";
					}else if(BookSeat.roomId.equals("1303")){
						tv_room = "三楼外文借阅处";
					}else{
						tv_room = "四楼自习阅览室";
					}
				}else{
					tv_lib = "数字图书馆";
					if(BookSeat.roomId.equals("2107")){
						tv_room = "一楼社科阅览室";
					}else if(BookSeat.roomId.equals("2207")){
						tv_room = "二楼自习阅览室";
					}else {
						tv_room = "三楼自习借阅处";
					}
				}
	}
	
	public static void book() throws Exception{
		
			//LoginCas.connect(LoginCas.Location);
System.out.println("http://zwyd.cqupt.edu.cn/manage/secure/book?c="+libId);
		LoginCas.connect("http://zwyd.cqupt.edu.cn/manage/secure/book?c="+libId);
System.out.println("http://zwyd.cqupt.edu.cn/manage/secure/xsyls?room_id="+roomId);			
		LoginCas.connect("http://zwyd.cqupt.edu.cn/manage/secure/xsyls?room_id="+roomId);
System.out.println("http://zwyd.cqupt.edu.cn/manage/secure/zzSeat?seat_id="+tableId+seatId);
		result = LoginCas.getInfo("http://zwyd.cqupt.edu.cn/manage/secure/zzSeat?seat_id="+tableId+seatId);
		

	}
	
	public static void random() throws Exception{
System.out.println("进入BookSeat.random()方法开始："+libId+" "+roomId);
System.out.println("http://zwyd.cqupt.edu.cn/manage/secure/book?c="+libId);
		LoginCas.connect("http://zwyd.cqupt.edu.cn/manage/secure/book?c="+libId);
System.out.println("http://zwyd.cqupt.edu.cn/manage/secure/book?c="+libId+"random线程访问结束");
System.out.println("http://zwyd.cqupt.edu.cn/manage/secure/xsyls?room_id="+roomId);
		LoginCas.getRandom("http://zwyd.cqupt.edu.cn/manage/secure/xsyls?room_id="+roomId);
	}
	
	public static void quit() throws Exception{
		LoginCas.connect("http://zwyd.cqupt.edu.cn/manage?q=logout");
		LoginCas.connect("https://sfrz.cqupt.edu.cn:8443/cas/logout");
		
	}
}
