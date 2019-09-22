package kr.or.bit;

/*
* @Interface : WishList.java
* @Date : 2019. 09. 22
* @Author : 양찬식
* @Desc : User_Mode에서 사용하기 위한 왓치리스트정보를 관리할 수 있는 기능을 가지고 있음
*/

import java.io.Serializable;

class WishList implements Serializable {
	private String title;
	private String genre;
	private double rating;

	public WishList(String title, String genre) {
		this.title = title;
		this.genre = genre;
	}

	// 위시리스트 추가 함수
	public WishList input(WishList wishlist) {
		return wishlist;
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

	@Override
	public String toString() {
		return "[" + "제목 : " + getTitle() + "    장르 : " + getGenre() + "]";
	}
}