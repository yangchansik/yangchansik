package kr.or.bit;

/*
* @Interface : Movie_Information.java
* @Date : 2019. 09. 22
* @Author : 이혜리
* @Desc : Manager_Mode에서 사용하기 위한 영화정보를 관리할 수 있는 기능을 가지고 있음
*/

import java.io.Serializable;
import java.util.Scanner;

public class Movie_Information implements Serializable {
	static Scanner sc;
	private String title;
	private String genre;
	private double rating;
	private String review;

	// 영화 목록 추가 함수
	public Movie_Information input(Movie_Information movieinfo) {
		sc = new Scanner(System.in);
		System.out.println("영화 제목을 입력해주세요.");
		this.title = sc.nextLine();
		System.out.println("장르를 입력해주세요.");
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
		return "[" + "제목 : " + getTitle() + "    장르 : " + getGenre() + "]";
	}

}