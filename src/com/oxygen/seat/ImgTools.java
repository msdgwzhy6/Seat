package com.oxygen.seat;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImgTools {
	/*
	public static void writeImageToFile(String imgFile, Bitmap bi) {
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(imgFile.substring(imgFile.lastIndexOf('.') + 1));
		ImageWriter writer = (ImageWriter) writers.next();
		File f = new File(imgFile);
		ImageOutputStream ios;

		try {
			ios = ImageIO.createImageOutputStream(f);
			writer.setOutput(ios);
			writer.write(bi);
			ios.close();
		} catch (Exception e) {
				e.printStackTrace();
			}
	}
	*/
	//RGB转换灰度值
	public static int pixelConvert(int pixel) {
		int result = 0;

		int r = (pixel >> 16) & 0xff;//getRGB返回int值，4字节，前三个字节是RGB，高位时透明度
		int g = (pixel >> 8) & 0xff;
		int b = (pixel) & 0xff;

		result = 1;

		int tmp = r * r + g * g + b * b;
		if (tmp > 3 * 128 * 128) {
			result -= 1;			
		}

		return result;
	}

	public static Bitmap getImage(String path) {
		Bitmap image = null;
		try {
			image = BitmapFactory.decodeFile(path);
		} catch (Exception e) {
				e.printStackTrace();
			}

		return image;
	}

	//剪裁一整张验证码的白边
	public static Bitmap getSingleCode(Bitmap image) {
		return Bitmap.createBitmap(image,1, 0, 66, 28);
	}
	//将验证码图片分成4份，每个8*28px
	public static Bitmap[] getCheckCodes(Bitmap image) {
		Bitmap checkCode[] = new Bitmap[4];
		checkCode[0] = Bitmap.createBitmap(image,0,0,8,28);//输入测得的单张数据(x,y,w,h)
		checkCode[1] = Bitmap.createBitmap(image,19,0,8,28);
		checkCode[2] = Bitmap.createBitmap(image,38,0,8,28);
		checkCode[3] = Bitmap.createBitmap(image,57,0,8,28);
		return checkCode;
	}
}
