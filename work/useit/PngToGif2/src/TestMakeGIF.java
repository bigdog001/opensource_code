import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class TestMakeGIF {
	public static void main(String[] args) {
		try {
			// ָ��Frame���ļ�
			String imgFileName[] = new String[]{"E:/12/1.png","E:/12/2.png", "E:/12/3.png"}; 
			/* File file = new File("E:/1.gif");
			    FileOutputStream fileOutputStream = new FileOutputStream(file);*/
			   // BufferedOutputStream outputStream = new BufferedOutputStream();
			    ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
			AnimatedGifEncoder e = new AnimatedGifEncoder();
			e.start(outputStream2);// ��ʼ����
			e.setDelay(500); // �����ӳ�ʱ��
			for (int i = 0; i < imgFileName.length; i++) {
				e.addFrame(ImageIO.read(new FileInputStream(imgFileName[i])));// ����Frame
			}
			e.finish();
			System.out.println(outputStream2.size());
			byte[] bytearray = outputStream2.toByteArray();
			System.out.println(bytearray.length);
			File file = new File("E:/1.gif");
			  FileOutputStream fileOutputStream = new FileOutputStream(file);
			    fileOutputStream.write(bytearray);
			    fileOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}