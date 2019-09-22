package kr.or.bit;

/*
* @Class : Manager_Mode.java
* @Date : 2019. 09. 22
* @Author : ������, �����
* @Desc : Manager_Mode�� ����ϱ� ���� ��ȭ ��� ����, ȸ�� ���� ����� ������ ����
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
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Manager_Mode implements List_Manageable, Member_Manageable {
	static HashMap<String, User_Information> userinfoMap;
	static HashMap<String, Movie_Information> movielistMap; // ��ȭ���
	static String mf; // ��ȭ ��� �з� ��ȣ ����
	static Scanner sc;
	private User_Mode usermode; // ����ڸ��

	// Manager_Mode() default ������
	public Manager_Mode() {
		userinfoMap = new HashMap<String, User_Information>();
		movielistMap = new HashMap<String, Movie_Information>();
		sc = new Scanner(System.in);
		usermode = new User_Mode(); // ����� ���� composition
	}

	// ������ �޴� �Լ�
	public void runManagerMenu() {
		String choice;
		do {
			System.out.println("<1> ��ȭ ����Ʈ ���� <2> ȸ�� ���� <3> �α׾ƿ�");
			choice = sc.nextLine();
			switch (choice) {
			case "1":
				runMovieAdmin();
				break;
			case "2":
				runMemberAdmin();
				break;
			case "3":
				usermode.runMainMenu();
				break;
			default:
				System.out.println("1~3���� �Է��ϼ���...^^");
			}
		} while (!choice.equals("3"));
	}

	// ��ȭ ����Ʈ ���� �Լ�
	public void runMovieAdmin() {
		String choice = "";
		do {
			System.out.println("<1>��ȭ ��� ���� <2>��ȭ ��� �߰� <3>��ȭ ��� ���� <4>��ȭ ��� ���� <5>���ư���");
			choice = sc.nextLine();
			switch (choice) {
			case "1":
				printMovieList();
				break;
			case "2":
				addMovieList();
				break;
			case "3":
				removeMovieList();
				break;
			case "4":
				modifyMovieList();
				break;
			case "5":
				runManagerMenu();
				break;
			}
		} while (!choice.equals("5"));
	}

	// ȸ�� ���� �޴� �Լ�
	public void runMemberAdmin() {
		String choice;
		do {
			System.out.println("<1>ȸ�� ��� <2>ȸ�� Ż�� <3>���ư���");
			choice = sc.nextLine();
			switch (choice) {
			case "1":
				printMemberList();
				break;
			case "2":
				banMember();
				break;
			case "3":
				runManagerMenu();
				break;
			default:
				System.out.println("1~3���� �Է��ϼ���^^");
			}
		} while (!choice.equals("3"));

	}

	// <1> ��ȭ ��� ���� �Լ�
	@Override
	public void printMovieList() {
		File movieFile = new File("movieList.txt");
		if (!movieFile.exists()) {// ��ȭ ��� ������ �����ϴ� ���
			System.out.println("��ȭ ����� �������� �ʽ��ϴ�. ���� ����� �ּ���.");
		} else {
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			ObjectInputStream ois = null;
			try {
				System.out.println("��ȭ ����Դϴ�.");
				fis = new FileInputStream(movieFile);
				bis = new BufferedInputStream(fis);
				ois = new ObjectInputStream(bis);
				movielistMap = (HashMap) ois.readObject();
				for (Map.Entry m : movielistMap.entrySet()) {
					System.out.println("�з���ȣ : " + m.getKey() + "/" + m.getValue().toString());
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
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

	// <2> ��ȭ ��� �߰� �Լ�
	@Override
	public void addMovieList() {
		File movieFile = new File("movieList.txt");
		if (movieFile.exists()) { // ���� ������ �����ϴ� ��� ���� ���� �ҷ����� �� �ȿ� �߰�
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			ObjectInputStream ois = null;
			try {
				fis = new FileInputStream(movieFile);
				bis = new BufferedInputStream(fis);
				ois = new ObjectInputStream(bis);
				movielistMap = (HashMap) ois.readObject();
				System.out.println("�߰��� ��ȭ�� ��� �з� ��ȣ�� �Է����ּ���.");
				mf = sc.nextLine();
				if (movielistMap.containsKey(mf)) { // �Է��� ��� �з� ��ȣ�� ��ȭ�� �̹� ����Ǿ� �ִ� ���
					System.out.println("�̹� ��ϵ� ��ȭ�� �����մϴ�.");
				} else {
					Movie_Information movieinfo = new Movie_Information();
					movielistMap.put(mf, movieinfo.input(movieinfo));
					saveMovieList();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				try {
					ois.close();
					bis.close();
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("�߰��� ��ȭ�� ��� �з� ��ȣ�� �Է����ּ���.");
			mf = sc.nextLine();
			Movie_Information movieinfo = new Movie_Information();
			movielistMap.put(mf, movieinfo.input(movieinfo));
			saveMovieList(); // ��ȭ ��� ���� ���� �Լ� ȣ��
		}
	}

	// <3> ��ȭ ��� ���� �Լ�
	@Override
	public void removeMovieList() {
		String choice;
		File movieFile = new File("movieList.txt");
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(movieFile);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);
			movielistMap = (HashMap) ois.readObject();
			System.out.println("��ȭ ����Դϴ�.");
			for (Map.Entry m : movielistMap.entrySet()) {
				System.out.println(m.getKey() + "/" + m.getValue().toString());
			}
			System.out.println("������ ��ȭ�� ��� �з� ��ȣ�� �Է����ּ���.");
			mf = sc.nextLine();
			if (!movielistMap.containsKey(mf)) { // �Է��� ��� �з� ��ȣ�� ���� ���
				System.out.println("�ش� ��ȭ�� ������ ã�� �� �����ϴ�.");
				runMovieAdmin();
			} else {
				System.out.println(movielistMap.get(mf).getTitle() + "��ȭ�Դϴ�.");
				System.out.println("�����Ͻðڽ��ϱ�? <1>���� <2>���ư���");
				choice = sc.nextLine();
				switch (choice) {
				case "1":
					movielistMap.remove(mf);
					saveMovieList(); // ��ȭ ��� ���� ���� �Լ� ȣ��
					System.out.println("�����Ǿ����ϴ�.");
					break;
				case "2":
					runMovieAdmin(); // ���� �޴� �Լ� ȣ��
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				ois.close();
				bis.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// <4>��ȭ ��� ���� �Լ�
	@Override
	public void modifyMovieList() {
		String choice;
		File movieFile = new File("movieList.txt");
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(movieFile);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);
			movielistMap = (HashMap) ois.readObject();
			System.out.println("��ȭ ����Դϴ�.");
			for (Map.Entry m : movielistMap.entrySet()) {
				System.out.println(m.getKey() + "/" + m.getValue().toString());
			}
			System.out.println("������ ��ȭ�� ��� �з� ��ȣ�� �Է����ּ���.");
			mf = sc.nextLine();
			if (!movielistMap.containsKey(mf)) { // �Է��� ��� �з� ��ȣ�� ���� ���
				System.out.println("�ش� ��ȭ�� ������ ã�� �� �����ϴ�.");
				runMovieAdmin();
			} else {
				System.out.println(movielistMap.get(mf).getTitle() + "��ȭ�Դϴ�.");
				System.out.println("�����Ͻðڽ��ϱ�? <1>���� <2>���ư���");
				choice = sc.nextLine();
				switch (choice) {
				case "1":
					Movie_Information movieinfo = new Movie_Information();
					movielistMap.put(mf, movieinfo.input(movieinfo));
					saveMovieList(); // ������ ��� ���Ϸ� �����ϴ� �Լ�
					System.out.println("�����Ǿ����ϴ�.");
					break;
				case "2":
					runMovieAdmin();
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				ois.close();
				bis.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// ��ȭ ��� ���� ���� �Լ�
	public void saveMovieList() {
		File movieFile = new File("movieList.txt");
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(movieFile);
			bos = new BufferedOutputStream(fos);
			oos = new ObjectOutputStream(bos);
			oos.writeObject(movielistMap);
		} catch (Exception e) {
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
	}

	// <1>ȸ�� ��� ��� �Լ�
	@Override
	public void printMemberList() {
		File userfile = new File("userinfo.txt");
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(userfile);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);
			userinfoMap = (HashMap) ois.readObject();
			Set set = userinfoMap.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				System.out.println("ȸ�� ID : " + it.next());
			}
		} catch (Exception e) {
			System.out.println("ȸ���� ���� �����ϴ٤Ф� �������ּ���!!^-^");
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

	// <2>ȸ�� Ż�� ��� �Լ�
	@Override
	public void banMember() {
		String choice;
		File userFile = new File("userinfo.txt");
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(userFile);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);
			userinfoMap = (HashMap) ois.readObject();
			System.out.println("ȸ�� ����Դϴ�.");
			for (String s : userinfoMap.keySet()) {
				System.out.println(s);
			}
			System.out.println("������ ȸ���� ���̵� �Է����ּ���.");
			mf = sc.nextLine();
			if (!userinfoMap.containsKey(mf)) { // �Է��� ȸ�� ���̵� ���� ���
				System.out.println("�ش� id�� ã�� �� �����ϴ�.");
				runMemberAdmin();
			} else {
				System.out.println("�����Ͻðڽ��ϱ�? <1>���� <2>���ư���");
				choice = sc.nextLine();
				switch (choice) {
				case "1":
					userinfoMap.remove(mf);
					System.out.println("�����Ǿ����ϴ�.");
					FileOutputStream fos = null;
					BufferedOutputStream bos = null;
					ObjectOutputStream oos = null;
					try {
						fos = new FileOutputStream(userFile);
						bos = new BufferedOutputStream(fos);
						oos = new ObjectOutputStream(bos);
						oos.writeObject(userinfoMap);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					} finally {
						oos.close();
						bos.close();
						fos.close();
					}
					break;
				case "2":
					runMemberAdmin();
					break;
				default:
					System.out.println("1~2������ �����ϼ���");
				}
			}
		} catch (Exception e) {
			System.out.println("ȸ���� ���� �����ϴ٤Ф� �������ּ���!!^-^");
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