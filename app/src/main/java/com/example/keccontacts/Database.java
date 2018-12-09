package com.example.keccontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

	static final String sid = "sid";
	static final String name = "sname";
	
	static final String dept = "dept";
	static final String type = "type";
	static final String desig = "desig";
	
	
	static final String mob = "mob";
	static final String mail = "mail";
	static final String office = "office";
	static final String phone = "phone";
	static final String staff_table = "STAFF_INFO";
	static final String user_table = "USER_INFO";
	static final String dName = "TestDB";

	static final String id = "UId";
	String uname;

	
	public Database(Context context) {
		super(context, dName, null, 2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	     db.execSQL("CREATE TABLE " + user_table + "(" + sid + "  TEXT PRIMARY KEY," + name+ " TEXT,"
				 + dept + " TEXT, " + type + " TEXT, " + desig + " TEXT," +mob + " TEXT," + mail + " TEXT)");

	     db.execSQL("CREATE TABLE " + staff_table + "(" + sid + "  TEXT PRIMARY KEY," + name+ " TEXT," 
					 + dept + " TEXT, " + type + " TEXT, " + desig + " TEXT,"  +mob + " TEXT," + mail + " TEXT)");
	
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public Cursor getstaffs() {
		// TODO Auto-generated method stub
		SQLiteDatabase sq = this.getReadableDatabase();
		String sql = "SELECT * FROM " + staff_table + " ORDER BY "+dept+" ";
		Cursor c = sq.rawQuery(sql, null);
		return c;
	}
	public Cursor getUser() {
		// TODO Auto-generated method stub
		SQLiteDatabase sq = this.getReadableDatabase();
		String sql = "SELECT * FROM " + user_table + " ";
		Cursor c = sq.rawQuery(sql, null);
		return c;
	}

	

	public Cursor getDepartments() {
		// TODO Auto-generated method stub
		SQLiteDatabase sq = this.getReadableDatabase();
		String sql = "SELECT DISTINCT "+dept+" FROM " + staff_table +" ORDER BY "+dept+" ";
		Cursor c = sq.rawQuery(sql, null);
		return c;
	}
	public Cursor getStaff(String s1) {
		// TODO Auto-generated method stub
		SQLiteDatabase sq = this.getReadableDatabase();
		String sql = "SELECT * FROM " + staff_table +" WHERE "+sid+" = '"+s1+"' ";
		//String sql = "SELECT * FROM " + staff_table +" ";
		Cursor c = sq.rawQuery(sql, null);
		return c;
	}
	public Cursor getDeptStaffs(String s1) {
		// TODO Auto-generated method stub
		SQLiteDatabase sq = this.getReadableDatabase();
		String sql = "SELECT * FROM " + staff_table +" WHERE "+dept+" = '"+s1+"' ";
		//String sql = "SELECT * FROM " + staff_table +" ";
		Cursor c = sq.rawQuery(sql, null);
		return c;
	}

	public boolean ckstaff() {
		// TODO Auto-generated method stub
		boolean val=false;
		SQLiteDatabase sq = this.getReadableDatabase();
		Cursor c=sq.rawQuery("SELECT * FROM "+staff_table+" ",null);
		if(c.getCount()==0)
		{
			val=false;
		}
		else
		{
			val=true;
		}
		c.close();
		return val;
	}
		public void Add_Staff(String s1, String s2,String s3,String s4,String s5,String s6,String s7) {
		// TODO Auto-generated method stub
			
		SQLiteDatabase sq = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		// cv.put(fname, n);
		// cv.put(lname, ln);
		cv.put(sid,s1);
		cv.put(name,s2);
		
		cv.put(dept,s3);
		cv.put(type,s4);
		cv.put(desig,s5);
		
		
		
			cv.put(mob,s6);
		cv.put(mail,s7);
	
		// cv.put(address, add);
		
		sq.insert(staff_table, null, cv);
		sq.close();

	}
	
	
	
	public void Add_User(String s1, String s2,String s3,String s4,String s5,String s6,String s7) {
		// TODO Auto-generated method stub

		SQLiteDatabase sq = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		// cv.put(fname, n);
		// cv.put(lname, ln);
		cv.put(sid,s1);
		cv.put(name,s2);
		
		cv.put(dept,s3);
		cv.put(type,s4);
		cv.put(desig,s5);
				
		cv.put(mob,s6);
		cv.put(mail,s7);
		// cv.put(address, add);
		
		sq.insert(user_table, null, cv);
		sq.close();

	}

	
	public Cursor login(String p, String u) {
		// TODO Auto-generated method stub
		String uname = p;
		String pass = u;
		System.out.println("Text1:" + uname);
		SQLiteDatabase sq = this.getReadableDatabase();

		String sql = "Select * from " + user_table + " where Mail = '" + uname + "' AND  PhoneNo='" + pass + "'";
		System.out.println(sql);
		Cursor c = sq.rawQuery(sql, null);

		return c;
		// return null;
	}

	public Cursor login(String s) {
		// TODO Auto-generated method stub

		SQLiteDatabase sq = this.getReadableDatabase();
		uname = s;
		System.out.println("Text2:" + uname);
		String sql = "Select * from " + user_table + " where Mail = '" + s + "' ";
		System.out.println(sql);
		Cursor c = sq.rawQuery(sql, null);

		return c;
	}

	public void del(String id1) {
		// TODO Auto-generated method stub
		SQLiteDatabase sq = Database.this.getWritableDatabase();
		String id = id1;
		sq.execSQL("delete from " + staff_table + " where sid = '" + id + "'");
		sq.close();

	}

	public void deleteStaff() {
		// TODO Auto-generated method stub
		SQLiteDatabase sq = Database.this.getWritableDatabase();
		sq.execSQL("delete from " + staff_table + " ");
		sq.close();

	}
	public void deleteUser() {
		// TODO Auto-generated method stub
		SQLiteDatabase sq = Database.this.getWritableDatabase();
		sq.execSQL("delete from " + user_table + " ");
		sq.close();

	}

	public void update_Staff(String s1, String s2,String s3,String s4) {
		// TODO Auto-generated method stub
		SQLiteDatabase sq = Database.this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		//System.out.println("!!" + m);
		//System.out.println("!!" + phn);


		cv.put(name,s2);
		
		cv.put(mob,s3);
		cv.put(mail,s4);
		
		
		
		
		
		int c = sq.update(staff_table, cv, sid + "=?", new String[] { (String.valueOf(s1)) });
		System.out.println("Updated:" + c);
		sq.close();
	}
}
