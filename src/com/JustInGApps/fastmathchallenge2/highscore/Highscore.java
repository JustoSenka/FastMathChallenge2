package com.JustInGApps.fastmathchallenge2.highscore;

public class Highscore {

	String _name;
	int _score;
	int _id;
	
	public Highscore(){
		this._name = "---";
		this._score = 0;
	}
	
	public Highscore(String name, int score){
		this._name = name;
		this._score = score;
	}
	
	public Highscore(int id, String name, int score){
		this._id = id;
		this._name = name;
		this._score = score;
	}
	
	public String getName(){
		return _name;
	}
	public int getScore(){
		return _score;
	}
	public int getId(){
		return _id;
	}
	public void setName(String name){
		this._name = name;
	}
	public void setScore(int score){
		this._score= score;
	}
	public void setId(int id){
		this._id= id;
	}
}
