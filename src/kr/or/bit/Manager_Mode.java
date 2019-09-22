package kr.or.bit;

/*
* @Class : Manager_Mode.java
* @Date : 2019. 09. 22
* @Author : 이혜리, 김수연
* @Desc : Manager_Mode를 사용하기 위한 영화 목록 관리, 회원 관리 기능을 가지고 있음
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
	static HashMap<String, Movie_Information> movielistMap; // 영화목록
	static String mf; // 영화 등급 분류 번호 변수
	static Scanner sc;
	private User_Mode usermode; // 사용자모드

	// Manager_Mode() default 생성자
	public Manager_Mode() {
		userinfoMap = new HashMap<String, User_Information>();
		movielistMap = new HashMap<String, Movie_Information>();
		sc = new Scanner(System.in);
		usermode = new User_Mode(); // 사용자 모드와 composition
	}

	// 관리자 메뉴 함수
	public void runManagerMenu() {
		String choice;
		do {
			System.out.println("<1> 영화 리스트 관리 <2> 회원 관리 <3> 로그아웃");
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
				System.out.println("1~3까지 입력하세요...^^");
			}
		} while (!choice.equals("3"));
	}

	// 영화 리스트 관리 함수
	public void runMovieAdmin() {
		String choice = "";
		do {
			System.out.println("<1>영화 목록 보기 <2>영화 목록 추가 <3>영화 목록 빼기 <4>영화 목록 수정 <5>돌아가기");
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

	// 회원 관리 메뉴 함수
	public void runMemberAdmin() {
		String choice;
		do {
			System.out.println("<1>회원 목록 <2>회원 탈퇴 <3>돌아가기");
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
				System.out.println("1~3까지 입력하세요^^");
			}
		} while (!choice.equals("3"));

	}

	// <1> 영화 목록 보기 함수
	@Override
	public void printMovieList() {
		File movieFile = new File("movieList.txt");
		if (!movieFile.exists()) {// 영화 목록 파일이 존재하는 경우
			System.out.println("영화 목록이 존재하지 않습니다. 새로 만들어 주세요.");
		} else {
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			ObjectInputStream ois = null;
			try {
				System.out.println("영화 목록입니다.");
				fis = new FileInputStream(movieFile);
				bis = new BufferedInputStream(fis);
				ois = new ObjectInputStream(bis);
				movielistMap = (HashMap) ois.readObject();
				for (Map.Entry m : movielistMap.entrySet()) {
					System.out.println("분류번호 : " + m.getKey() + "/" + m.getValue().toString());
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

	// <2> 영화 목록 추가 함수
	@Override
	public void addMovieList() {
		File movieFile = new File("movieList.txt");
		if (movieFile.exists()) { // 기존 파일이 존재하는 경우 기존 파일 불러오고 그 안에 추가
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			ObjectInputStream ois = null;
			try {
				fis = new FileInputStream(movieFile);
				bis = new BufferedInputStream(fis);
				ois = new ObjectInputStream(bis);
				movielistMap = (HashMap) ois.readObject();
				System.out.println("추가할 영화의 등급 분류 번호를 입력해주세요.");
				mf = sc.nextLine();
				if (movielistMap.containsKey(mf)) { // 입력한 등급 분류 번호의 영화가 이미 저장되어 있는 경우
					System.out.println("이미 등록된 영화가 존재합니다.");
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
			System.out.println("추가할 영화의 등급 분류 번호를 입력해주세요.");
			mf = sc.nextLine();
			Movie_Information movieinfo = new Movie_Information();
			movielistMap.put(mf, movieinfo.input(movieinfo));
			saveMovieList(); // 영화 목록 파일 저장 함수 호출
		}
	}

	// <3> 영화 목록 빼기 함수
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
			System.out.println("영화 목록입니다.");
			for (Map.Entry m : movielistMap.entrySet()) {
				System.out.println(m.getKey() + "/" + m.getValue().toString());
			}
			System.out.println("삭제할 영화의 등급 분류 번호를 입력해주세요.");
			mf = sc.nextLine();
			if (!movielistMap.containsKey(mf)) { // 입력한 등급 분류 번호가 없는 경우
				System.out.println("해당 영화의 정보를 찾을 수 없습니다.");
				runMovieAdmin();
			} else {
				System.out.println(movielistMap.get(mf).getTitle() + "영화입니다.");
				System.out.println("삭제하시겠습니까? <1>삭제 <2>돌아가기");
				choice = sc.nextLine();
				switch (choice) {
				case "1":
					movielistMap.remove(mf);
					saveMovieList(); // 영화 목록 파일 저장 함수 호출
					System.out.println("삭제되었습니다.");
					break;
				case "2":
					runMovieAdmin(); // 이전 메뉴 함수 호출
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

	// <4>영화 목록 수정 함수
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
			System.out.println("영화 목록입니다.");
			for (Map.Entry m : movielistMap.entrySet()) {
				System.out.println(m.getKey() + "/" + m.getValue().toString());
			}
			System.out.println("수정할 영화의 등급 분류 번호를 입력해주세요.");
			mf = sc.nextLine();
			if (!movielistMap.containsKey(mf)) { // 입력한 등급 분류 번호가 없는 경우
				System.out.println("해당 영화의 정보를 찾을 수 없습니다.");
				runMovieAdmin();
			} else {
				System.out.println(movielistMap.get(mf).getTitle() + "영화입니다.");
				System.out.println("수정하시겠습니까? <1>수정 <2>돌아가기");
				choice = sc.nextLine();
				switch (choice) {
				case "1":
					Movie_Information movieinfo = new Movie_Information();
					movielistMap.put(mf, movieinfo.input(movieinfo));
					saveMovieList(); // 수정한 목록 파일로 저장하는 함수
					System.out.println("수정되었습니다.");
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

	// 영화 목록 파일 저장 함수
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

	// <1>회원 목록 출력 함수
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
				System.out.println("회원 ID : " + it.next());
			}
		} catch (Exception e) {
			System.out.println("회원이 아직 없습니다ㅠㅠ 모집해주세요!!^-^");
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

	// <2>회원 탈퇴 기능 함수
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
			System.out.println("회원 목록입니다.");
			for (String s : userinfoMap.keySet()) {
				System.out.println(s);
			}
			System.out.println("삭제할 회원의 아이디를 입력해주세요.");
			mf = sc.nextLine();
			if (!userinfoMap.containsKey(mf)) { // 입력한 회원 아이디가 없는 경우
				System.out.println("해당 id를 찾을 수 없습니다.");
				runMemberAdmin();
			} else {
				System.out.println("삭제하시겠습니까? <1>삭제 <2>돌아가기");
				choice = sc.nextLine();
				switch (choice) {
				case "1":
					userinfoMap.remove(mf);
					System.out.println("삭제되었습니다.");
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
					System.out.println("1~2까지만 선택하세요");
				}
			}
		} catch (Exception e) {
			System.out.println("회원이 아직 없습니다ㅠㅠ 모집해주세요!!^-^");
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