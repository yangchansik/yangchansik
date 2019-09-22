package kr.or.bit;

/*
* @Interface : User_Mode.java
* @Date : 2019. 09. 22
* @Author : ����ȣ, ������
* @Desc : User_Mode�� ����ϱ� ���� ���� �޴�, ���ø���Ʈ ����, ��ġ����Ʈ ����, ȸ������ ����, ȸ�� Ż�� ����� ������ ����
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
	static HashMap<String, Movie_Information> movielistMap = new HashMap<String, Movie_Information>(); // ��ȭ����Ʈ ����
	static HashMap<Integer, WishList> wishlistMap = new HashMap<Integer, WishList>(); // ���ø���Ʈ ����
	static HashMap<Integer, WatchList> watchlistMap = new HashMap<Integer, WatchList>(); // ��ġ����Ʈ ����
	static int count1; // ���ø���Ʈ ���
	static int count2; // ��ġ����Ʈ ���
	static Scanner sc;
	static Calendar cal;

	// User_Mode default ������ �Լ�
	public User_Mode() {
		movielistMap = new HashMap<String, Movie_Information>();
		wishlistMap = new HashMap<Integer, WishList>();
		watchlistMap = new HashMap<Integer, WatchList>();
		count1 = 1;
		count2 = 1;
		sc = new Scanner(System.in);
		cal = Calendar.getInstance();
	}

	// ���� �޴� �Լ�
	public void runMainMenu() {
		do {
			System.out.println("*****************************************");
			System.out.println("���񸮽�Ʈ �Դϴ�^^");
			System.out.println("���Ͻô� �޴��� �������ּ���");
			System.out.println("<1>�α��� <2>ȸ������ <3>ID, PWã�� <4>�ý��� ����");
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
				System.out.println("1~4���� �Է��ϼ���");
			}
		} while (true);
	}

	// ����� �޴� �Լ�
	public void runUserMenu() {
		String choice;
		do {
			System.out.println("***********************************************");
			System.out.println("         " + inputid + " ȸ���� �ȳ��ϼ���^^            ");
			System.out.println("            ���Ͻô� �޴��� �������ּ���.           ");
			System.out.println("<1> ���񸮽�Ʈ <2> ȸ�� ���� ���� <3> ȸ��Ż�� <4> �α׾ƿ�");
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
				System.out.println("1~4������ �Է��ϼ���");
				break;
			}
		} while (!choice.equals("4"));
	}

	// ���񸮽�Ʈ �޴� �Լ�
	public void runMovieMenu() {
		loadMoiveFile(); // ��ȭ ��� ���� ȣ��
		String choice;
		do {
			System.out.println("*************************************************************");
			System.out.println("���Ͻô� �޴��� �������ּ���.");
			System.out.println("<1>���ø���Ʈ �߰� <2>���ø���Ʈ ���� <3>���ø���Ʈ �ҷ�����");
			System.out.println("<4>��ġ����Ʈ �߰� <5>��ġ����Ʈ ���� <6>��ġ����Ʈ �ҷ�����");
			System.out.println("<9>���ư���");
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
				System.out.println("1~9���� �Է��ϼ���");
				break;
			}
		} while (!choice.equals("9"));
	}

	// <1> ���ø���Ʈ �߰� �Լ�
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
				System.out.println("���ø���Ʈ�� �߰��Ͻ� ��ȭ�� ��� �з� ��ȣ�� �Է����ּ���.");
				mf = sc.nextLine(); // ���ø���Ʈ�� �߰��� ��ȭ�� ��� �з� ��ȣ �ޱ�
				if (movielistMap.containsKey(mf)) {
					String title = movielistMap.get(mf).getTitle();
					String genre = movielistMap.get(mf).getGenre();
					WishList wishlist = new WishList(title, genre);
					wishlistMap.put(count1, wishlist.input(wishlist));
					count1++;
					System.out.println("�߰� �Ǿ����ϴ�.");
					saveWishList();
				} else {
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");
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

	// <2>���ø���Ʈ ���� �Լ�
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
				System.out.println("��� ��ȣ : " + wishlist.getKey() + "/" + (wishlist.getValue()).toString());
			}
			System.out.println("�����Ͻ� ���ø���Ʈ�� ��� ��ȣ�� �Է����ּ���.");
			choice = Integer.parseInt(sc.nextLine());
			if (wishlistMap.containsKey(choice)) {
				wishlistMap.remove(choice);
				System.out.println("�����߽��ϴ�.");
				saveWishList();
			} else {
				System.out.println("�߸� �Է��ϼ̽��ϴ�");
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

	// <3>���ø���Ʈ �ҷ����� �Լ�
	public void printWishList() {
		File wishlistFile = new File(inputid + "'s wishList.txt");
		if (wishlistFile.exists()) {
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			try {
				fis = new FileInputStream(wishlistFile);
				ois = new ObjectInputStream(fis);
				wishlistMap = (HashMap) ois.readObject();
				String date = cal.get(Calendar.YEAR) + "��" + (cal.get(Calendar.MONTH) + 1) + "��"
						+ cal.get(Calendar.DATE) + "��";
				for (Map.Entry wishlist : wishlistMap.entrySet()) {
					System.out.println("��� ��ȣ : " + wishlist.getKey() + "/" + (wishlist.getValue()).toString()
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
			System.out.println("���ø���Ʈ �Դϴ�.");
		} else {
			System.out.println("���� ���ø���Ʈ�� �����ϴ�. ���ø���Ʈ�� ��������!");
		}

	}

	// ���ø���Ʈ ���� ���� �Լ�
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

	// <4>��ġ����Ʈ �߰� �Լ�
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
			System.out.println("��ġ����Ʈ�� �߰��Ͻ� ��ȭ�� ��� �з� ��ȣ�� �Է����ּ���.");
			mf = sc.nextLine(); // ��ġ����Ʈ�� �߰��� ��ȭ�� ��� �з� ��ȣ �ޱ�
			do {
				if (movielistMap.containsKey(mf)) {
					System.out.println("������ �Է����ּ���(1~5��)");
					myrating = Double.parseDouble(sc.nextLine());
					if (myrating >= 1 && myrating <= 5) {
						movielistMap.get(mf).setRating(myrating);
					} else {
						System.out.println("������ 1~5���� �� �� �ֽ��ϴ�");
						runMovieMenu();
					}
					System.out.println("���並 �Է����ּ���");
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
					System.out.println("�߰� �Ǿ����ϴ�.");
					return;
				} else {
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");
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

	// <5>��ġ����Ʈ ���� �Լ�
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
				System.out.println("��� ��ȣ : " + watchlist.getKey() + "/" + (watchlist.getValue()).toString());
			}
			System.out.println("�����Ͻ� ��ġ����Ʈ�� ��� ��ȣ�� �Է����ּ���.");
			choice = Integer.parseInt(sc.nextLine());
			if (watchlistMap.containsKey(choice)) {
				watchlistMap.remove(choice);
				System.out.println("�����߽��ϴ�.");
				saveWatchList();
			} else {
				System.out.println("�߸� �Է��ϼ̽��ϴ�");
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

	// <6>��ġ����Ʈ �ҷ�����
	public void printWatchList() {
		File watchlistFile = new File(inputid + "'s watchList.txt");
		if (watchlistFile.exists()) {
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			try {
				fis = new FileInputStream(watchlistFile);
				ois = new ObjectInputStream(fis);
				watchlistMap = (HashMap) ois.readObject();
				String date = cal.get(Calendar.YEAR) + "��" + (cal.get(Calendar.MONTH) + 1) + "��"
						+ cal.get(Calendar.DATE) + "��";
				for (Map.Entry watchlist : watchlistMap.entrySet()) {
					System.out.println("��� ��ȣ : " + watchlist.getKey() + "/" + (watchlist.getValue()).toString()
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
			System.out.println("��ġ����Ʈ �Դϴ�.");
		} else {
			System.out.println("���� ��ġ����Ʈ�� �����ϴ�. ��ġ����Ʈ�� ��������!");
		}

	}

	// ��ġ����Ʈ ���� �Լ�
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
		System.out.println("���� �Ǿ����ϴ�.");
	}

	// ��ȭ ��� �θ��� �Լ�
	public void loadMoiveFile() {
		File movieFile = new File("movieList.txt");
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
				System.out.println("�з� ��ȣ : " + m.getKey() + "/" + m.getValue().toString());
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

	// ȸ�� ���� ���� �Լ�
	public void modifyUserInfo() {
		loadMember(); // ȸ�� ���� ���� �ҷ�����
		String select;
		do {
			System.out.println("ȸ������ ���� ȭ���Դϴ�");
			System.out.println("�����Ͻ� ������ ��ȣ�� �����ּ���");
			System.out.println("<1> ��й�ȣ ���� <2> �޴�����ȣ ���� <3>���ư���");
			select = sc.nextLine();
			switch (select) {
			case "1":
				System.out.println("�����Ͻ� ��й�ȣ�� �Է����ּ���");
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
				System.out.println("��й�ȣ�� ����Ǿ����ϴ�^^");
				break;
			case "2":
				System.out.println("�����Ͻ� �޴�����ȣ�� �Է����ּ���");
				String newPn = sc.nextLine();
				userinfoMap.get(inputid).setPhoneNumber(newPn);
				String reg_phoneNumber = "^\\d{3}\\-\\d{3,4}\\-\\d{4}$";
				boolean new_phoneNumber = Pattern.matches(reg_phoneNumber, newPn);
				if (!new_phoneNumber) {
					System.out.println("�޴�����ȣ ����� Ʋ�Ƚ��ϴ�.");
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
				System.out.println("�޴�����ȣ�� ����Ǿ����ϴ�.^^");
				break;
			case "3":
				runUserMenu();
				break;
			default:
				System.out.println("1~3���߿��� �������ּ���^^");
				break;
			}
		} while (!select.equals("3"));
	}

	// ȸ�� Ż�� ���
	public void withdrawUserId() {
		loadMember(); // ȸ�� ���� ���� �ҷ�����
		String choice = "";
		System.out.println("������ Ż���Ͻðڽ��ϱ�?");
		System.out.println("<1> �� <2> ���ư���");
		choice = sc.nextLine();
		switch (choice) {
		case "1":
			userinfoMap.remove(inputid); // HashMap�� �ִ� ȸ�� ���� ����
			File userfile = new File("userinfo.txt");
			FileOutputStream fos = null;
			BufferedOutputStream bos = null;
			ObjectOutputStream oos = null;
			try { // �ٽ� ȸ�� ���� ���� ����
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
			System.out.println("Ż�� ���������� �Ϸ�Ǿ����ϴ� ������ �ٽ� �̿����ּ���^^");
			runMainMenu();
			break;
		case "2":
			runUserMenu();
			break;
		default:
			System.out.println("1~2���߿��� �������ּ���^^");
			break;
		}
	}
}