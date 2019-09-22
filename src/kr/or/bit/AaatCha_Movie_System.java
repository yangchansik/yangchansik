package kr.or.bit;

/*
* @Class : AaaCha_Movie_System.java
* @Date : 2019. 09. 22
* @Author : 이용욱
* @Desc : AaatCha_Movie_System을 사용하기 위한 로그인, 회원가입, ID/PW찾기, 회원목록을 로드하는 기능을 가지고 있음
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
	static HashMap<String, User_Information> userinfoMap; // 사용자 정보 저장
	static String inputid; // 다른 클래스에서도 사용하는 공유자원
	static Scanner sc;
	private User_Information userInfo;
	private Properties admin; // 관리자 id, pw 저장
	private Set<String> set;
	private String inputpw;

	// AaatCha_Movie_System default 생성자
	public AaatCha_Movie_System() {
		userinfoMap = new HashMap<String, User_Information>();
		sc = new Scanner(System.in);
		userInfo = new User_Information(); // User_Info 클래스와 composition 관계
		set = userinfoMap.keySet();
	}

	// 회원가입 함수
	public void signin() {
		loadMember(); // 회원 목록 파일 불러오기
		System.out.println("사용하실 아이디를 입력해주세요.");
		String id = sc.nextLine();
		if (set.contains(id)) { // id중복방지
			System.out.println("사용중인 아이디입니다.");
			return;
		}
		userinfoMap.put(id, userInfo.input(userInfo)); // 회원 가입시 받은 정보를 HaspMap으로 저장
		if (userinfoMap.get(id) == null) {
			System.out.println("휴대폰 번호 양식이 틀렸습니다.다시 입력해주세요...^^");
			userinfoMap.remove(id);
			return;
		}
		File userfile = new File("userinfo.txt"); // HashMap I/O를 통해 입출력
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
		System.out.println("회원가입이 완료되었습니다.");
	}

	// 로그인 함수
	public void login() {
		loadMember(); // 회원 목록 파일 불러오기
		admin = new Properties();
		admin.setProperty("admin", "1234"); // 관리자 id, pw 부여
		System.out.println("아이디를 입력해주세요. >>");
		inputid = sc.nextLine();
		System.out.println("패스워드를 입력해주세요. >> ");
		inputpw = sc.nextLine();
		if (admin.containsKey(inputid) && (admin.get(inputid)).equals(inputpw)) { // 관리자로 로그인할 경우 관리자 화면창으로 넘어감
			Manager_Mode managermode = new Manager_Mode();
			managermode.runManagerMenu();
		} else { // 일반 회원으로 로그인할 경우
			File userfile = new File("userinfo.txt");
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			ObjectInputStream ois = null;
			try {
				fis = new FileInputStream(userfile); // 회원 정보 파일을 I/O를 통해 불러오기
				bis = new BufferedInputStream(fis);
				ois = new ObjectInputStream(bis);
				userinfoMap = (HashMap) ois.readObject();
				if (!userinfoMap.containsKey(inputid)) {
					System.out.println("아이디를 찾을 수 없습니다.");
				} else { // 입력한 id=회원 정보 파일에 저장된 id
					if ((userinfoMap.get(inputid).getPw()).equals(inputpw)) {
						System.out.println("로그인 되었습니다.");
						User_Mode usermode = new User_Mode();
						usermode.runUserMenu(); // 일반회원으로 로그인한 경우 회원 화면창으로 넘어감
					} else {
						System.out.println("비밀번호를 다시 확인해주세요.");
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

	// id pw 찾기 함수
	public void find_idpw() {
		loadMember(); // 회원 목록 파일 불러오기
		String findnum = "";
		String findid = "";
		System.out.println("==========ID/PW찾기==========");
		System.out.println("1. 아이디 찾기 2. 비밀번호 찾기");
		String select = sc.nextLine();
		Set<String> set = userinfoMap.keySet(); // HashMap에 저장된 key값을 Set으로 가져옴
		switch (select) {
		case "1":
			Object[] obj = set.toArray();
			Object[] obj2 = new Object[userinfoMap.size()];
			int count = 0;
			System.out.println("아이디 찾기입니다 >> 가입하실때 입력하신 휴대폰 번호를 입력해주세요");
			findnum = sc.nextLine();
			for (int i = 0; i < userinfoMap.size(); i++) {
				if (userinfoMap.get(obj[i]).getPhoneNumber().equals(findnum)) {
					System.out.println("고객님의 ID는 " + obj[i] + "입니다");
				} else {
					obj2[count] = obj[i];
					count++;
				}
			}
			if (count == userinfoMap.size()) {
				System.out.println("찾는 번호가 없습니다");
			}
			break;
		case "2":
			System.out.println("비밀번호 찾기입니다 >> ID를 입력하세요");
			findid = sc.nextLine();
			if (userinfoMap.containsKey(findid)) {
				System.out.println("고객님의 비밀번호는 " + userinfoMap.get(findid).getPw() + "입니다.");
			} else {
				System.out.println("ID를 잘못 입력 하셨습니다^^");
			}
			break;
		default:
			System.out.println("1,2만 입력하세요...^^");
			find_idpw();
			break;
		}
	}

	// 회원 목록 불러오는 함수
	public void loadMember() { // 회원 목록 파일 로드 함수
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