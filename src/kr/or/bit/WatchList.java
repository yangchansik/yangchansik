package kr.or.bit;

/*
* @Interface : WatchList.java
* @Date : 2019. 09. 22
* @Author : ����ȣ
* @Desc : User_Mode���� ����ϱ� ���� ��ġ����Ʈ������ ������ �� �ִ� ����� ������ ����
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

	// ��ġ����Ʈ �߰� �Լ�
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
		return "[" + "���� : " + getTitle() + "    �帣 : " + getGenre() + "    ���� : " + getRating() + "    ���� : "
				+ getReview() + "]";
	}
}