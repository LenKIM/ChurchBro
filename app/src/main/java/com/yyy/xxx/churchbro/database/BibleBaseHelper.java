package com.yyy.xxx.churchbro.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * Created by len on 2017. 2. 14..
 */

public class BibleBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION= 1;
    private static final String DB_NAME = "bibles.db";
    private static final String DB_PATH = "/data/data/com.yyy.xxx.churchbro/databases/";
    private static final String TAG = BibleBaseHelper.class.getName();
    private SQLiteDatabase myDataBase;
    private final Context myContext;


    public BibleBaseHelper(Context context){
        super(context, DB_NAME, null, VERSION);
        myContext = context;
    }

    /**
     * 새로운 빈 데이터를 만드는 메서드
     * */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){

            Log.d(TAG, "파일 존재함");

        }else{

            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;

            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does’t exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }


    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     *  원하는 이름과 버전을 가져오는 메서드
     * @param
     * @return
     */
    public ArrayList<HashMap<String, String>> getBibleNameAndVersion(){

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        //리스트이니까, 넘버랑 같이 저장되게해서, 필요할 때 사용.
        myDataBase = getReadableDatabase();

        Cursor cursor;
        HashMap<String, String> map = null;

        cursor = myDataBase.rawQuery("SELECT id,table, abbreviation, version FROM bible_version_key", null);

        if (cursor.moveToFirst()){
            do {
                map = new HashMap<String, String>();

                map.put("id", cursor.getString(cursor.getColumnIndex("id")));
                map.put("table", cursor.getString(cursor.getColumnIndex("table")));
                map.put("abbre",cursor.getString(cursor.getColumnIndex("abbreviation")));
                map.put("version",cursor.getString(cursor.getColumnIndex("version")));

                list.add(map);

            }while (cursor.moveToNext());
        } else {
            list = null;
        }
        cursor.close();
        myDataBase.close();
        return list;
    }

    // 각 성경의 이름들을 출력하는 함수.
    public String[][] getBibleNames(){


        int i=0;
        String[][] str;

        Cursor cursor;
        myDataBase = getReadableDatabase();

        cursor = myDataBase.rawQuery("SELECT b,n FROM key_english", null);

        if (cursor.moveToFirst()){

            str = new String[cursor.getCount()][2]; // n행 2열

            do {
                str[i][0] = cursor.getString(0);
                str[i][1] = cursor.getString(1);
                i++;

            }while (cursor.moveToNext());
        } else {
            str = null;
        }
        cursor.close();
        myDataBase.close();
        return str;
    }

    //성경을 선택했을때 장의 전체 수를 반환하는 함수
    public ArrayList<String> getCountSelectedChapter(String bibleVersion,int bible){

        ArrayList<String> list = new ArrayList<>();

        String a;
        int count = 0;
        Cursor cursor;
        myDataBase = getReadableDatabase();
        //SELECT c,v FROM t_kjv WHERE b = 1
        cursor = myDataBase.rawQuery("SELECT c FROM " +"'"+bibleVersion+"'"+ " WHERE b ="+ bible,null);

        if (cursor.moveToFirst()){

            do {
                a = cursor.getString(0);
                list.add(a);

            } while (cursor.moveToNext());

        } else {
            a = null;
        }
        cursor.close();
        myDataBase.close();

        //중복제거하는부분 순서를 위해 LinkdedHashSet을 사용하였음.
        LinkedHashSet<String> linkedhs = new LinkedHashSet<>(list);
        ArrayList<String> list2 = new ArrayList<>(linkedhs);
        return list2;
    }

    //성경을 선택했을때 장과 절의 텍스트가 반환하는 함수
    public ArrayList<Integer> getCountSelectedVerse(String bibleVersion ,int bible, int chapter){

        ArrayList<Integer> list = new ArrayList<>();

        Cursor cursor;
        int chapterCount = 0;

        myDataBase = getReadableDatabase();
        //SELECT c,v FROM t_kjv WHERE b = 1
        cursor = myDataBase.rawQuery("SELECT v FROM " + "'" +bibleVersion + "'" + " WHERE b ="+ bible + " and " + "c=" + chapter,null);

        if (cursor.moveToFirst()){

            do {
                chapterCount = cursor.getInt(0);
                list.add(chapterCount);

            } while (cursor.moveToNext());

        } else {
        }
        cursor.close();
        myDataBase.close();

        //중복제거하는부분 순서를 위해 LinkdedHashSet을 사용하였음.
        LinkedHashSet<Integer> linkedhs = new LinkedHashSet<>(list);
        ArrayList<Integer> list2 = new ArrayList<>(linkedhs);
//        return count;
        return list2;
    }

    //성경 장 절 선택 // 성경이랑 장 선택했을때 이미 텍스트를 가져오고 텍스트를 뿌린 뒤, 장을 통해서 필요 텍스트를 뿌린다.
    public String[][] getSelectedTextArea(String bibleVersion,int bibleName, int chapter){

        int i=0;

        String[][] verseText;

        myDataBase = getReadableDatabase();
        Cursor cursor;

        cursor = myDataBase.rawQuery("SELECT b,c,v,t FROM"+"'"+ bibleVersion + "'" + "WHERE b ="+ bibleName +" and " + "c =" + chapter, null);

        if (cursor.moveToFirst()){
            verseText = new String[cursor.getCount()][2]; // n행 2열

            do {
                verseText[i][0] = cursor.getString(cursor.getColumnIndex("v"));
                verseText[i][1] = cursor.getString(cursor.getColumnIndex("t"));
                i++;

            }while (cursor.moveToNext());
        } else {
            verseText = null;
        }
        cursor.close();
        myDataBase.close();

        return verseText;
    }

    //뷰를 보여주는 것임을 명심
    public TextView getSelectedVerse(String verseNumber, String verserTotalText){
        TextView textView = null;
        verseNumber = "";

        return textView;
    }

}
