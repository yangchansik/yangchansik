package kr.or.bit;

/*
* @Interface : WishList.java
* @Date : 2019. 09. 22
* @Author : ������
* @Desc : User_Mode���� ����ϱ� ���� ��ġ����Ʈ������ ������ �� �ִ� ����� ������ ����
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

	// ���ø���Ʈ �߰� �Լ�
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
		return "[" + "���� : " + getTitle() + "    �帣 : " + getGenre() + "]";
	}
}