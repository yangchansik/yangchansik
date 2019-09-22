package kr.or.bit;

/*
* @Interface : Movie_Information.java
* @Date : 2019. 09. 22
* @Author : ������
* @Desc : Manager_Mode���� ����ϱ� ���� ��ȭ������ ������ �� �ִ� ����� ������ ����
*/

import java.io.Serializable;
import java.util.Scanner;

public class Movie_Information implements Serializable {
	static Scanner sc;
	private String title;
	private String genre;
	private double rating;
	private String review;

	// ��ȭ ��� �߰� �Լ�
	public Movie_Information input(Movie_Information movieinfo) {
		sc = new Scanner(System.in);
		System.out.println("��ȭ ������ �Է����ּ���.");
		this.title = sc.nextLine();
		System.out.println("�帣�� �Է����ּ���.");
		this.genre = sc.nextLine();
		return movieinfo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	@Override
	public String toString() {
		return "[" + "���� : " + getTitle() + "    �帣 : " + getGenre() + "]";
	}

}