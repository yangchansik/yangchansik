package kr.or.bit;

/*
* @Interface : List_Manageable.java
* @Date : 2019. 09. 22
* @Author : ������, �����
* @Desc : Manager_Mode���� ������ ��ȭ ��� ���, ��ȭ ��� �߰�, ��ȭ ��� ����, ��ȭ ��� ���� ����� ������ ����
*/

public interface List_Manageable { // ��ȭ ���� ���� �������̽�
	void printMovieList(); // ��ȭ ��� ��� �Լ�

	void addMovieList(); // ��ȭ ��� �߰�

	void removeMovieList(); // ��ȭ ��� ����

	void modifyMovieList(); // ��ȭ ��� ����
}