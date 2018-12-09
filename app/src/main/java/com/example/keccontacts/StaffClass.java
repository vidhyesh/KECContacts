package com.example.keccontacts;

public class StaffClass {
	String stid;
	String stname;
	String stdept;
	String type;
	String mobile;
	String mail;
	String dept;
	String soff;
	String sphon;
	Boolean box;

	public StaffClass(String soff, String sphon) {
		this.soff = soff;
		this.sphon = sphon;
		
	}
	public StaffClass(String stid, String stname, String stdept,String mobile, String mail) {
		this.stid = stid;
		this.stname = stname;
		this.stdept = stdept;
		this.mobile = mobile;
		this.mail = mail;
	}
	public StaffClass(String stid, String stname, String stdept,String type,String mobile, String mail,String dept) {
		this.stid = stid;
		this.stname = stname;
		this.stdept = stdept;
		this.type=type;
		this.mobile = mobile;
		this.mail = mail;
		this.dept=dept;
	}
	public StaffClass(String stid, String stname, String stdept,String type,String mobile, String mail,int i) {
		this.stid = stid;
		this.stname = stname;
		this.stdept = stdept;
		this.type=type;
		this.mobile = mobile;
		this.mail = mail;
		
	}
	
	public StaffClass(String stid, String stname, String stdept,String type,String mobile, String mail, Boolean _box) {
		this.stid = stid;
		this.stname = stname;
		this.stdept = stdept;
		this.type=type;
		this.mobile = mobile;
		this.mail = mail;
		box = _box;
	}
	public StaffClass(String stid, String stname, String stdept) {
		this.stid = stid;
		this.stname = stname;
		this.stdept = stdept;
	}
	public StaffClass(String rank) {
		this.stid = rank;
		
	}
	public boolean getBox()
	{
	return this.box;
	}

	public String getId() {
		return this.stid;
	}
	public String getOff() {
		return this.soff;
	}
	public String getPhone() {
		return this.sphon;
	}
	public String getDept() {
		return this.dept;
	}
	public String getType() {
		return this.type;
	}
	public void setBox() {
		this.box=true;
	}

	public String getName() {
		return this.stname;
	}

	public String getDepart() {
		return this.stdept;
	}
	public String getMobile() {
		return this.mobile;
	}
	public String getMail() {
		return this.mail;
	}
}