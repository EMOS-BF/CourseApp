package com.example.courseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String  DATABASE_NAME="student_db";
    private static final String TABLE_NAME="users";

    private static final String ID="id";
    private static final String username ="username";
    private static final String email="email";
    private static final String niveau ="niveau";
    private static final String domaine ="domaine";
    private static final String created_at="created_at";
    private StudentAdapter adapter;

    public DatabaseHelper(Context context, StudentAdapter adapter){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.adapter = adapter;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE admins (username TEXT PRIMARY KEY, email TEXT, password TEXT)");
        String table_query="CREATE TABLE if not EXISTS "+TABLE_NAME+
                "("+
                ID+" INTEGER PRIMARY KEY,"+
                username +" TEXT ,"+
                email+" TEXT ,"+
                niveau + " TEXT ,"+
                domaine +" TEXT ,"+
                created_at+ " TEXT "+
                ")";
        db.execSQL(table_query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public void AddStudnet(StudentModel studentModel){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(username,studentModel.getUsername());
        contentValues.put(email,studentModel.getEmail());
        contentValues.put(domaine,studentModel.getDomaine());
        contentValues.put(niveau,studentModel.getNiveau());
        contentValues.put(created_at,studentModel.getCreated_at());
        db.insert(TABLE_NAME,null,contentValues);
        List<StudentModel> updatedStudentList = getAllStudents();
        adapter.updateData(updatedStudentList);
    }

    public StudentModel getStudent(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,new String[]{ID, username,email, domaine, niveau,created_at},ID+" = ?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        StudentModel studentModel=new StudentModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
        return studentModel;
    }

    public List<StudentModel> getAllStudents(){
        List<StudentModel> studentModelList=new ArrayList<>();
        String query="SELECT * from "+TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                StudentModel studentModel=new StudentModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(4),cursor.getString(3),cursor.getString(5));
                studentModelList.add(studentModel);
            }
            while (cursor.moveToNext());

        }
        db.close();
        return studentModelList;
    }

    public int updateStudent(StudentModel studentModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(username,studentModel.getUsername());
        contentValues.put(email,studentModel.getEmail());
        contentValues.put(domaine,studentModel.getDomaine());
        contentValues.put(niveau,studentModel.getNiveau());
        int rowsAffected = db.update(TABLE_NAME, contentValues, ID + "=?", new String[]{String.valueOf(studentModel.getId())});

        // Update the adapter with the updated data
        if (rowsAffected > 0) {
            List<StudentModel> updatedStudentList = getAllStudents();
            adapter.updateData(updatedStudentList);
        }

        return rowsAffected;
    }

    public void deleteStudent(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,ID+"=?",new String[]{id});
        List<StudentModel> updatedStudentList = getAllStudents();
        adapter.updateData(updatedStudentList);
    }

    public int getTotalCount(){
        String query="SELECT * from "+TABLE_NAME;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(query,null);
        return cursor.getCount();
    }

    public boolean checkusername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from admins where username = ?",new String[] {username});
        if(cursor.getCount() > 0){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkusernamepassword(String username,String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from admins where username = ? and password =?",new String[] {username,password});
        if(cursor.getCount() > 0){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean insertData(String username,String email,String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("email",email);
        contentValues.put("password",password);
        long result = MyDB.insert("admins",null,contentValues);
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

}
