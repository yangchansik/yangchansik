package kr.or.bit;

/*
* @Interface : User_Information.java
* @Date : 2019. 09. 22
* @Author : �̿��
* @Desc : User_Mode���� ����ϱ� ���� ���������� ������ �� �ִ� ����� ������ ����
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

	// ȸ�� �߰� �Լ�
	public User_Information input(User_Information userInfo) {
		String reg_phoneNumber = "^\\d{3}\\-\\d{3,4}\\-\\d{4}$";
		System.out.println("����Ͻ� PW�� �Է����ּ���.");
		this.pw = sc.nextLine();
		System.out.println("�̸��� �Է����ּ���.");
		this.name = sc.nextLine();
		System.out.println("�޴��� ��ȣ�� �Է����ּ���.");
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
		return "��й�ȣ : " + getPw() + "�޴��� ��ȣ : " + getPhoneNumber();
	}

}