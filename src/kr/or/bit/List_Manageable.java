package kr.or.bit;

/*
* @Interface : List_Manageable.java
* @Date : 2019. 09. 22
* @Author : 이혜리, 김수연
* @Desc : Manager_Mode에서 구현할 영화 목록 출력, 영화 목록 추가, 영화 목록 삭제, 영화 목록 수정 기능을 가지고 있음
*/

public interface List_Manageable { // 영화 정보 관리 인터페이스
	void printMovieList(); // 영화 목록 출력 함수

	void addMovieList(); // 영화 목록 추가

	void removeMovieList(); // 영화 목록 삭제

	void modifyMovieList(); // 영화 목록 수정
}