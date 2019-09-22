package kr.or.bit;

/*
* @Interface : Member_Manageable.java
* @Date : 2019. 09. 22
* @Author : 이혜리, 김수연
* @Desc : Manager_Mode에서 구현할 회원 목록 보기, 회원을 탈퇴시키는 기능을 가지고 있음
*/

public interface Member_Manageable { // 회원 관리 인터페이스
	void printMemberList(); // 회원 목록 보기

	void banMember(); // 회원 탈퇴
}