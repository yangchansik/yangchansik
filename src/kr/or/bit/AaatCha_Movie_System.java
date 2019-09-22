package kr.or.bit;

/*
* @Class : AaaCha_Movie_System.java
* @Date : 2019. 09. 22
* @Author : �̿��
* @Desc : AaatCha_Movie_System�� ����ϱ� ���� �α���, ȸ������, ID/PWã��, ȸ������� �ε��ϴ� ����� ������ ����
*/

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

class AaatCha_Movie_System {
	static HashMap<String, User_Information> userinfoMap; // ����� ���� ����
	static String inputid; // �ٸ� Ŭ���������� ����ϴ� �����ڿ�
	static Scanner sc;
	private User_Information userInfo;
	private Properties admin; // ������ id, pw ����
	private Set<String> set;
	private String inputpw;

	// AaatCha_Movie_System default ������
	public AaatCha_Movie_System() {
		userinfoMap = new HashMap<String, User_Information>();
		sc = new Scanner(System.in);
		userInfo = new User_Information(); // User_Info Ŭ������ composition ����
		set = userinfoMap.keySet();
	}

	// ȸ������ �Լ�
	public void signin() {
		loadMember(); // ȸ�� ��� ���� �ҷ�����
		System.out.println("����Ͻ� ���̵� �Է����ּ���.");
		String id = sc.nextLine();
		if (set.contains(id)) { // id�ߺ�����
			System.out.println("������� ���̵��Դϴ�.");
			return;
		}
		userinfoMap.put(id, userInfo.input(userInfo)); // ȸ�� ���Խ� ���� ������ HaspMap���� ����
		if (userinfoMap.get(id) == null) {
			System.out.println("�޴��� ��ȣ ����� Ʋ�Ƚ��ϴ�.�ٽ� �Է����ּ���...^^");
			userinfoMap.remove(id);
			return;
		}
		File userfile = new File("userinfo.txt"); // HashMap I/O�� ���� �����
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(userfile);
			bos = new BufferedOutputStream(fos);
			oos = new ObjectOutputStream(bos);
			oos.writeObject(userinfoMap);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				oos.close();
				bos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("ȸ�������� �Ϸ�Ǿ����ϴ�.");
	}

	// �α��� �Լ�
	public void login() {
		loadMember(); // ȸ�� ��� ���� �ҷ�����
		admin = new Properties();
		admin.setProperty("admin", "1234"); // ������ id, pw �ο�
		System.out.println("���̵� �Է����ּ���. >>");
		inputid = sc.nextLine();
		System.out.println("�н����带 �Է����ּ���. >> ");
		inputpw = sc.nextLine();
		if (admin.containsKey(inputid) && (admin.get(inputid)).equals(inputpw)) { // �����ڷ� �α����� ��� ������ ȭ��â���� �Ѿ
			Manager_Mode managermode = new Manager_Mode();
			managermode.runManagerMenu();
		} else { // �Ϲ� ȸ������ �α����� ���
			File userfile = new File("userinfo.txt");
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			ObjectInputStream ois = null;
			try {
				fis = new FileInputStream(userfile); // ȸ�� ���� ������ I/O�� ���� �ҷ�����
				bis = new BufferedInputStream(fis);
				ois = new ObjectInputStream(bis);
				userinfoMap = (HashMap) ois.readObject();
				if (!userinfoMap.containsKey(inputid)) {
					System.out.println("���̵� ã�� �� �����ϴ�.");
				} else { // �Է��� id=ȸ�� ���� ���Ͽ� ����� id
					if ((userinfoMap.get(inputid).getPw()).equals(inputpw)) {
						System.out.println("�α��� �Ǿ����ϴ�.");
						User_Mode usermode = new User_Mode();
						usermode.runUserMenu(); // �Ϲ�ȸ������ �α����� ��� ȸ�� ȭ��â���� �Ѿ
					} else {
						System.out.println("��й�ȣ�� �ٽ� Ȯ�����ּ���.");
					}
				}
			} catch (Exception e) {

			} finally {
				try {
					ois.close();
					bis.close();
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// id pw ã�� �Լ�
	public void find_idpw() {
		loadMember(); // ȸ�� ��� ���� �ҷ�����
		String findnum = "";
		String findid = "";
		System.out.println("==========ID/PWã��==========");
		System.out.println("1. ���̵� ã�� 2. ��й�ȣ ã��");
		String select = sc.nextLine();
		Set<String> set = userinfoMap.keySet(); // HashMap�� ����� key���� Set���� ������
		switch (select) {
		case "1":
			Object[] obj = set.toArray();
			Object[] obj2 = new Object[userinfoMap.size()];
			int count = 0;
			System.out.println("���̵� ã���Դϴ� >> �����ϽǶ� �Է��Ͻ� �޴��� ��ȣ�� �Է����ּ���");
			findnum = sc.nextLine();
			for (int i = 0; i < userinfoMap.size(); i++) {
				if (userinfoMap.get(obj[i]).getPhoneNumber().equals(findnum)) {
					System.out.println("������ ID�� " + obj[i] + "�Դϴ�");
				} else {
					obj2[count] = obj[i];
					count++;
				}
			}
			if (count == userinfoMap.size()) {
				System.out.println("ã�� ��ȣ�� �����ϴ�");
			}
			break;
		case "2":
			System.out.println("��й�ȣ ã���Դϴ� >> ID�� �Է��ϼ���");
			findid = sc.nextLine();
			if (userinfoMap.containsKey(findid)) {
				System.out.println("������ ��й�ȣ�� " + userinfoMap.get(findid).getPw() + "�Դϴ�.");
			} else {
				System.out.println("ID�� �߸� �Է� �ϼ̽��ϴ�^^");
			}
			break;
		default:
			System.out.println("1,2�� �Է��ϼ���...^^");
			find_idpw();
			break;
		}
	}

	// ȸ�� ��� �ҷ����� �Լ�
	public void loadMember() { // ȸ�� ��� ���� �ε� �Լ�
		File userfile = new File("userinfo.txt");
		if (userfile.exists()) {
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			ObjectInputStream ois = null;
			try {
				fis = new FileInputStream(userfile);
				bis = new BufferedInputStream(fis);
				ois = new ObjectInputStream(bis);
				userinfoMap = (HashMap) ois.readObject();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					ois.close();
					bis.close();
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}