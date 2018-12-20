package pers.wsins.locker;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JLabel;

/**
 * 文件加密Locker
 * 
 * @author Sin
 * @version 2017年7月20日 00:37:24
 */
public class LockerGUI extends JFrame {

	private static final long serialVersionUID = 1163018887123699318L;
	private JPanel contentPane;
	private JTextField textField;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JTextField textField_1;
	private final JLabel lblNewLabel = new JLabel("文件路径：");
	private JLabel lblNewLabel_1;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LockerGUI frame = new LockerGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 创建窗体 画界面
	 */
	public LockerGUI() {
		setTitle("文件加密AbelLock");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 368, 181);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setToolTipText("在文件路径中，文件名输入原文件名(未加密文件名，无“_lock”)");
		textField.setBounds(75, 10, 268, 34);
		contentPane.add(textField);
		textField.setColumns(10);

		btnNewButton_1 = new JButton("加密");
		btnNewButton_1.setBounds(10, 98, 161, 34);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			private FileInputStream is;

			@Override
			public void mouseClicked(MouseEvent e) {
				String str_dir = new String();
				str_dir = textField.getText();

				if (str_dir.indexOf("_lock") == -1) {
					int index = str_dir.lastIndexOf("\\");
					String str_dir4 = str_dir.substring(index + 1, str_dir.length());

					String str_dir3 = setNamestr(str_dir);

					File file = new File(str_dir);
					if (file.exists()) {
						try {

							is = new FileInputStream(str_dir);
							writeToLocal(str_dir3, is);
							is.close();

						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						file = new File(str_dir);
						file.delete();

						textField_1.setText(str_dir4 + "加密成功！");
					} else {
						textField_1.setText(str_dir4 + "文件不存在！");
					}
				} else {
					textField_1.setText("路径错误！请输入原文件名(未加密文件名)");
				}

			}

		});
		contentPane.add(btnNewButton_1);

		btnNewButton_2 = new JButton("解密");
		btnNewButton_2.setBounds(182, 98, 161, 34);
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			private FileInputStream is;

			@Override
			public void mouseClicked(MouseEvent e) {

				String str_dir = new String();
				str_dir = textField.getText();
				if (str_dir.indexOf("_lock") == -1) {
					String str_dir3 = setNamestr(str_dir);

					int index = str_dir3.lastIndexOf("\\");
					String str_dir4 = str_dir3.substring(index + 1, str_dir3.length());

					File file = new File(str_dir3);
					if (file.exists()) {
						try {

							is = new FileInputStream(str_dir3);
							writeToLocal(str_dir, is);
							is.close();

						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						file = new File(str_dir3);
						file.delete();
						textField_1.setText(str_dir4 + "解密成功！");
					} else {
						textField_1.setText(str_dir4 + "文件不存在！");
					}
				} else {
					textField_1.setText("路径错误！请输入原文件名(未加密文件名)");
				}

			}

		});
		contentPane.add(btnNewButton_2);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(75, 54, 268, 34);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		lblNewLabel.setToolTipText("");
		lblNewLabel.setBounds(10, 10, 75, 33);
		contentPane.add(lblNewLabel);

		lblNewLabel_1 = new JLabel("操作结果：");
		lblNewLabel_1.setBounds(10, 54, 75, 33);
		contentPane.add(lblNewLabel_1);
	}

	/**
	 * 设置"_lock"文件名
	 * 
	 * @param str_dir
	 */
	public String setNamestr(String str_dir) {

		int index = str_dir.lastIndexOf(".");// 获取文件后缀点索引

		String str_dir1 = str_dir.substring(0, index);// 去掉后缀
		String str_dir2 = str_dir.substring(index, str_dir.length());// 获取后缀
		String str_dir3 = str_dir1 + "_lock" + str_dir2;

		return str_dir3;
	}

	/**
	 * 异或操作
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
				// 通过异或运算某个数字或字符串
				// 这里是异或2
				bytes2[i] = (byte) (bytes[i] ^ 2);
			}
			downloadFile.write(bytes2, 0, index);
			downloadFile.flush();
		}
		downloadFile.close();
	}
}
