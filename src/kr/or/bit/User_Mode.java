package kr.or.bit;

/*
* @Interface : User_Mode.java
* @Date : 2019. 09. 22
* @Author : 김진호, 양찬식
* @Desc : User_Mode를 사용하기 위한 유저 메뉴, 위시리스트 관리, 왓치리스트 관리, 회원정보 수정, 회원 탈퇴 기능을 가지고 있음
*/

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.Scanner;

public class User_Mode extends AaatCha_Movie_System {
	static HashMap<String, Movie_Information> movielistMap = new HashMap<String, Movie_Information>(); // 영화리스트 관리
	static HashMap<Integer, WishList> wishlistMap = new HashMap<Integer, WishList>(); // 위시리스트 관리
	static HashMap<Integer, WatchList> watchlistMap = new HashMap<Integer, WatchList>(); // 왓치리스트 관리
	static int count1; // 위시리스트 목록
	static int count2; // 왓치리스트 목록
	static Scanner sc;
	static Calendar cal;

	// User_Mode default 생성자 함수
	public User_Mode() {
		movielistMap = new HashMap<String, Movie_Information>();
		wishlistMap = new HashMap<Integer, WishList>();
		watchlistMap = new HashMap<Integer, WatchList>();
		count1 = 1;
		count2 = 1;
		sc = new Scanner(System.in);
		cal = Calendar.getInstance();
	}

	// 메인 메뉴 함수
	public void runMainMenu() {
		do {
			System.out.println("*****************************************");
			System.out.println("무비리스트 입니다^^");
			System.out.println("원하시는 메뉴를 선택해주세요");
			System.out.println("<1>로그인 <2>회원가입 <3>ID, PW찾기 <4>시스템 종료");
			System.out.println("*****************************************");
			String choice = sc.nextLine();
			switch (choice) {
			case "1":
				login();
				break;
			case "2":
				signin();
				break;
			case "3":
				find_idpw();
				break;
			case "4":
				System.exit(0);
			default:
				System.out.println("1~4까지 입력하세요");
			}
		} while (true);
	}

	// 사용자 메뉴 함수
	public void runUserMenu() {
		String choice;
		do {
			System.out.println("***********************************************");
			System.out.println("         " + inputid + " 회원님 안녕하세요^^            ");
			System.out.println("            원하시는 메뉴를 선택해주세요.           ");
			System.out.println("<1> 무비리스트 <2> 회원 정보 수정 <3> 회원탈퇴 <4> 로그아웃");
			System.out.println("***********************************************");
			choice = sc.nextLine();
			switch (choice) {
			case "1":
				runMovieMenu();
				break;
			case "2":
				modifyUserInfo();
				break;
			case "3":
				withdrawUserId();
				break;
			case "4":
				runMainMenu();
				break;
			default:
				System.out.println("1~4까지만 입력하세요");
				break;
			}
		} while (!choice.equals("4"));
	}

	// 무비리스트 메뉴 함수
	public void runMovieMenu() {
		loadMoiveFile(); // 영화 목록 파일 호출
		String choice;
		do {
			System.out.println("*************************************************************");
			System.out.println("원하시는 메뉴를 선택해주세요.");
			System.out.println("<1>위시리스트 추가 <2>위시리스트 삭제 <3>위시리스트 불러오기");
			System.out.println("<4>왓치리스트 추가 <5>왓치리스트 삭제 <6>왓치리스트 불러오기");
			System.out.println("<9>돌아가기");
			System.out.println("*************************************************************");
			choice = sc.nextLine();
			switch (choice) {
			case "1":
				addWishList();
				break;
			case "2":
				deleteWishList();
				break;
			case "3":
				printWishList();
				break;
			case "4":
				addWatchList();
				break;
			case "5":
				deleteWatchList();
				break;
			case "6":
				printWatchList();
				break;
			case "9":
				runUserMenu();
				break;
			default:
				System.out.println("1~9까지 입력하세요");
				break;
			}
		} while (!choice.equals("9"));
	}

	// <1> 위시리스트 추가 함수
	public void addWishList() {
		String mf = "";
		File movieFile = new File("movieList.txt");
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(movieFile);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);
			movielistMap = (HashMap) ois.readObject();
			do {
				System.out.println("위시리스트에 추가하실 영화의 등급 분류 번호를 입력해주세요.");
				mf = sc.nextLine(); // 위시리스트에 추가할 영화의 등급 분류 번호 받기
				if (movielistMap.containsKey(mf)) {
					String title = movielistMap.get(mf).getTitle();
					String genre = movielistMap.get(mf).getGenre();
					WishList wishlist = new WishList(title, genre);
					wishlistMap.put(count1, wishlist.input(wishlist));
					count1++;
					System.out.println("추가 되었습니다.");
					saveWishList();
				} else {
					System.out.println("잘못 입력하셨습니다.");
				}
			} while (!movielistMap.containsKey(mf));
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

	// <2>위시리스트 삭제 함수
	public void deleteWishList() {
		int choice = 0;
		File wishlistFile = new File(inputid + "'s wishList.txt");
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(wishlistFile);
			ois = new ObjectInputStream(fis);
			wishlistMap = (HashMap) ois.readObject();
			for (Map.Entry wishlist : wishlistMap.entrySet()) {
				System.out.println("목록 번호 : " + wishlist.getKey() + "/" + (wishlist.getValue()).toString());
			}
			System.out.println("삭제하실 위시리스트의 목록 번호를 입력해주세요.");
			choice = Integer.parseInt(sc.nextLine());
			if (wishlistMap.containsKey(choice)) {
				wishlistMap.remove(choice);
				System.out.println("삭제했습니다.");
				saveWishList();
			} else {
				System.out.println("잘못 입력하셨습니다");
				runMovieMenu();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				ois.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// <3>위시리스트 불러오기 함수
	public void printWishList() {
		File wishlistFile = new File(inputid + "'s wishList.txt");
		if (wishlistFile.exists()) {
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			try {
				fis = new FileInputStream(wishlistFile);
				ois = new ObjectInputStream(fis);
				wishlistMap = (HashMap) ois.readObject();
				String date = cal.get(Calendar.YEAR) + "년" + (cal.get(Calendar.MONTH) + 1) + "월"
						+ cal.get(Calendar.DATE) + "일";
				for (Map.Entry wishlist : wishlistMap.entrySet()) {
					System.out.println("목록 번호 : " + wishlist.getKey() + "/" + (wishlist.getValue()).toString()
							+ "          " + date);
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				try {
					ois.close();
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("위시리스트 입니다.");
		} else {
			System.out.println("아직 위시리스트가 없습니다. 위시리스트를 만들어보세요!");
		}

	}

	// 위시리스트 파일 저장 함수
	public void saveWishList() {
		File wishlistFile = new File(inputid + "'s wishList.txt");
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(wishlistFile);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(wishlistMap);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				oos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// <4>왓치리스트 추가 함수
	public void addWatchList() {
		String mf = "";
		double myrating = 0.0;

		File movieFile = new File("movieList.txt");
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(movieFile);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);
			movielistMap = (HashMap) ois.readObject();
			System.out.println("왓치리스트에 추가하실 영화의 등급 분류 번호를 입력해주세요.");
			mf = sc.nextLine(); // 왓치리스트에 추가할 영화의 등급 분류 번호 받기
			do {
				if (movielistMap.containsKey(mf)) {
					System.out.println("평점을 입력해주세요(1~5점)");
					myrating = Double.parseDouble(sc.nextLine());
					if (myrating >= 1 && myrating <= 5) {
						movielistMap.get(mf).setRating(myrating);
					} else {
						System.out.println("평점은 1~5점만 줄 수 있습니다");
						runMovieMenu();
					}
					System.out.println("리뷰를 입력해주세요");
					String myreview = sc.nextLine();
					movielistMap.get(mf).setReview(myreview);
					String title = movielistMap.get(mf).getTitle();
					String genre = movielistMap.get(mf).getGenre();
					double rating = movielistMap.get(mf).getRating();
					String review = movielistMap.get(mf).getReview();
					WatchList watchlist = new WatchList(title, genre, rating, review);
					watchlistMap.put(count2, watchlist.input(watchlist));
					count2++;
					saveWatchList();
					System.out.println("추가 되었습니다.");
					return;
				} else {
					System.out.println("잘못 입력하셨습니다.");
				}
			} while (movielistMap.containsKey(mf));
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

	// <5>왓치리스트 삭제 함수
	public void deleteWatchList() {
		int choice = 0;
		File watchlistFile = new File(inputid + "'s watchList.txt");
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(watchlistFile);
			ois = new ObjectInputStream(fis);
			watchlistMap = (HashMap) ois.readObject();
			for (Map.Entry watchlist : watchlistMap.entrySet()) {
				System.out.println("목록 번호 : " + watchlist.getKey() + "/" + (watchlist.getValue()).toString());
			}
			System.out.println("삭제하실 왓치리스트의 목록 번호를 입력해주세요.");
			choice = Integer.parseInt(sc.nextLine());
			if (watchlistMap.containsKey(choice)) {
				watchlistMap.remove(choice);
				System.out.println("삭제했습니다.");
				saveWatchList();
			} else {
				System.out.println("잘못 입력하셨습니다");
				runMovieMenu();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				ois.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// <6>왓치리스트 불러오기
	public void printWatchList() {
		File watchlistFile = new File(inputid + "'s watchList.txt");
		if (watchlistFile.exists()) {
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			try {
				fis = new FileInputStream(watchlistFile);
				ois = new ObjectInputStream(fis);
				watchlistMap = (HashMap) ois.readObject();
				String date = cal.get(Calendar.YEAR) + "년" + (cal.get(Calendar.MONTH) + 1) + "월"
						+ cal.get(Calendar.DATE) + "일";
				for (Map.Entry watchlist : watchlistMap.entrySet()) {
					System.out.println("목록 번호 : " + watchlist.getKey() + "/" + (watchlist.getValue()).toString()
							+ "          " + date);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				try {
					ois.close();
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("왓치리스트 입니다.");
		} else {
			System.out.println("아직 왓치리스트가 없습니다. 왓치리스트를 만들어보세요!");
		}

	}

	// 왓치리스트 저장 함수
	public void saveWatchList() {
		File watchlistFile = new File(inputid + "'s watchList.txt");
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(watchlistFile);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(watchlistMap);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				oos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("저장 되었습니다.");
	}

	// 영화 목록 부르는 함수
	public void loadMoiveFile() {
		File movieFile = new File("movieList.txt");
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
				System.out.println("분류 번호 : " + m.getKey() + "/" + m.getValue().toString());
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

	// 회원 정보 수정 함수
	public void modifyUserInfo() {
		loadMember(); // 회원 정보 파일 불러오기
		String select;
		do {
			System.out.println("회원정보 수정 화면입니다");
			System.out.println("변경하실 정보의 번호를 눌러주세요");
			System.out.println("<1> 비밀번호 변경 <2> 휴대폰번호 변경 <3>돌아가기");
			select = sc.nextLine();
			switch (select) {
			case "1":
				System.out.println("변경하실 비밀번호를 입력해주세요");
				String newPw = sc.nextLine();
				userinfoMap.get(inputid).setPw(newPw);
				File userfile = new File("userinfo.txt");
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
				System.out.println("비밀번호가 변경되었습니다^^");
				break;
			case "2":
				System.out.println("변경하실 휴대폰번호를 입력해주세요");
				String newPn = sc.nextLine();
				userinfoMap.get(inputid).setPhoneNumber(newPn);
				String reg_phoneNumber = "^\\d{3}\\-\\d{3,4}\\-\\d{4}$";
				boolean new_phoneNumber = Pattern.matches(reg_phoneNumber, newPn);
				if (!new_phoneNumber) {
					System.out.println("휴대폰번호 양식이 틀렸습니다.");
					return;
				} else {
					userinfoMap.get(inputid).setPhoneNumber(newPn);
				}
				File userfile2 = new File("userinfo.txt");
				FileOutputStream fos2 = null;
				BufferedOutputStream bos2 = null;
				ObjectOutputStream oos2 = null;
				try {
					fos2 = new FileOutputStream(userfile2);
					bos2 = new BufferedOutputStream(fos2);
					oos2 = new ObjectOutputStream(bos2);
					oos2.writeObject(userinfoMap);
				} catch (IOException e) {
					System.out.println(e.getMessage());
				} finally {
					try {
						oos2.close();
						bos2.close();
						fos2.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println("휴대폰번호가 변경되었습니다.^^");
				break;
			case "3":
				runUserMenu();
				break;
			default:
				System.out.println("1~3번중에서 선택해주세요^^");
				break;
			}
		} while (!select.equals("3"));
	}

	// 회원 탈퇴 기능
	public void withdrawUserId() {
		loadMember(); // 회원 정보 파일 불러오기
		String choice = "";
		System.out.println("정말로 탈퇴하시겠습니까?");
		System.out.println("<1> 예 <2> 돌아가기");
		choice = sc.nextLine();
		switch (choice) {
		case "1":
			userinfoMap.remove(inputid); // HashMap에 있는 회원 정보 삭제
			File userfile = new File("userinfo.txt");
			FileOutputStream fos = null;
			BufferedOutputStream bos = null;
			ObjectOutputStream oos = null;
			try { // 다시 회원 정보 파일 저장
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
			System.out.println("탈퇴가 성공적으로 완료되었습니다 다음에 다시 이용해주세요^^");
			runMainMenu();
			break;
		case "2":
			runUserMenu();
			break;
		default:
			System.out.println("1~2번중에서 선택해주세요^^");
			break;
		}
	}
}