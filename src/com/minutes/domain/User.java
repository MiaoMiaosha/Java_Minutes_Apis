package com.minutes.domain;

public class User {
	
	private int id;
	private String tel;
	private String nickname;
	private int gender;//0为未知，1为男性，2为女性
	private int createAt;
	private float fromScore;//作为下单人平均分
	private float toScore;//作为接单人平均分
	private float money;
	private String token;
	private int type;//用户当前身份,0为未选择，1为下单人，2为接单人
	private int token_time;

	
	public int getToken_time() {
		return token_time;
	}


	public void setToken_time(int token_time) {
		this.token_time = token_time;
	}


	@Override
	public String toString(){
		return "User:[id="+id+" ,tel="+tel+", nickname="+nickname+" ,gender="+gender
				+" ,createAt="+createAt+" ,fromSore="+fromScore+" ,toSore="+toScore+" ,money="+money
				+" ,token="+token+" ,type="+type+"]";
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public int getGender() {
		return gender;
	}


	public void setGender(int gender) {
		this.gender = gender;
	}


	public int getCreateAt() {
		return createAt;
	}


	public void setCreateAt(int createAt) {
		this.createAt = createAt;
	}


	public float getFromScore() {
		return fromScore;
	}


	public void setFromScore(float fromSore) {
		this.fromScore = fromScore;
	}


	public float getToScore() {
		return toScore;
	}


	public void setToScore(float toSore) {
		this.toScore = toScore;
	}


	public float getMoney() {
		return money;
	}


	public void setMoney(float money) {
		this.money = money;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}

}
