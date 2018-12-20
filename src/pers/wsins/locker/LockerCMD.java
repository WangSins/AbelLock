package pers.wsins.locker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * 文件加密Locker
 * 
 * @author Sin
 * @version 2017年7月11日下午2:00:30
 */
public class LockerCMD {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		while (true) {
			System.out.print("请选择操作(1.加密)(2.解密)(0.结束程序)：");
			Scanner sc = new Scanner(System.in);
			int n = sc.nextInt();
			switch (n) {
			case 1:
				// 加密
				InputStream is = new FileInputStream("D:/Locker/123.jpg");
				writeToLocal("D:/Locker/123_lock.jpg", is);
				is.close();
				File file = new File("D:/Locker/123.jpg");
				file.delete();
				System.out.println("加密成功！");
				break;
			case 2:
				// 解密
				InputStream is1 = new FileInputStream("D:/Locker/123_lock.jpg");
				writeToLocal("D:/Locker/123.jpg", is1);
				is1.close();
				File file1 = new File("D:/Locker/123_lock.jpg");
				file1.delete();
				System.out.println("解密成功！");
				break;
			case 0:
				// 退出
				System.out.println("结束程序！");
				System.exit(0);
			default:
				System.out.println("错误操作！");
				break;
			}

		}

	}

	/**
	 * 流写入本地文件
	 * 
	 * @param destination
	 * @param input
	 * @throws IOException
	 */
	private static void writeToLocal(String destination, InputStream input) throws IOException {
		int index;
		byte[] bytes = new byte[1024];
		byte[] bytes2 = new byte[1024];
		FileOutputStream downloadFile = new FileOutputStream(destination);
		while ((index = input.read(bytes)) != -1) {
			for (int i = 0; i < index; i++) {
				// 通过异或运算某个数字或字符串（这里以2为例）
				bytes2[i] = (byte) (bytes[i] ^ 2);
			}
			downloadFile.write(bytes2, 0, index);
			downloadFile.flush();
		}
		downloadFile.close();
	}

}
