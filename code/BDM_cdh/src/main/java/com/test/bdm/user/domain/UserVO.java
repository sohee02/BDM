package com.test.bdm.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

	private String id;
	private int no;
	private String pw;
	private String email;
	private String name;
	private int birth;
	private int gender;
	private double height;
	private double weight;
	private String regDt;
	private int userFilter;

	@Override
	public String toString() {
		return "UserVO [id=" + id + ", no=" + no + ", pw=" + pw + ", email=" + email + ", name=" + name + ", birth="
				+ birth + ", gender=" + gender + ", height=" + height + ", weight=" + weight + ", regDt=" + regDt
				+ ", userFilter=" + userFilter + ", toString()=" + super.toString() + "]";
	}
}
