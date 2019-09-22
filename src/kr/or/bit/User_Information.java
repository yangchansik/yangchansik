package kr.or.bit;

/*
* @Interface : User_Information.java
* @Date : 2019. 09. 22
* @Author : 이용욱
* @Desc : User_Mode에서 사용하기 위한 유저정보를 관리할 수 있는 기능을 가지고 있음
*/

import java.io.Serializable;
import java.util.Scanner;
import java.util.regex.Pattern;

public class User_Information implements Serializable {
	static Scanner sc;
	private String pw;
	private String phoneNumber;
	private String name;

	public User_Information() {
		sc = new Scanner(System.in);
	}

	// 회원 추가 함수
	public User_Information input(User_Information userInfo) {
		String reg_phoneNumber = "^\\d{3}\\-\\d{3,4}\\-\\d{4}$";
		System.out.println("사용하실 PW를 입력해주세요.");
		this.pw = sc.nextLine();
		System.out.println("이름을 입력해주세요.");
		this.name = sc.nextLine();
		System.out.println("휴대폰 번호를 입력해주세요.");
		this.phoneNumber = sc.nextLine();
		boolean is_phoneNumber = Pattern.matches(reg_phoneNumber, this.phoneNumber);
		if (!is_phoneNumber) {
			return null;
		} else {
			return userInfo;
		}
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "비밀번호 : " + getPw() + "휴대폰 번호 : " + getPhoneNumber();
	}

}