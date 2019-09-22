package kr.or.bit;

/*
* @Interface : WatchList.java
* @Date : 2019. 09. 22
* @Author : 김진호
* @Desc : User_Mode에서 사용하기 위한 왓치리스트정보를 관리할 수 있는 기능을 가지고 있음
*/

import java.io.Serializable;

class WatchList implements Serializable {
	private String title;
	private String genre;
	private double rating;
	private String review;

	public WatchList(String title, String genre, double rating, String review) {
		this.title = title;
		this.genre = genre;
		this.rating = rating;
		this.review = review;
	}

	// 왓치리스트 추가 함수
	public WatchList input(WatchList watchlist) {
		return watchlist;
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
		return "[" + "제목 : " + getTitle() + "    장르 : " + getGenre() + "    평점 : " + getRating() + "    리뷰 : "
				+ getReview() + "]";
	}
}