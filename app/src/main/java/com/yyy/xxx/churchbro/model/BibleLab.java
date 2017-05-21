package com.yyy.xxx.churchbro.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yyy.xxx.churchbro.database.BibleBaseHelper;

import java.util.ArrayList;

/**
 * Created by len on 2017. 2. 14..
 * SQLite DateBase를 활용하는 클래스.
 *
 */

public class BibleLab {

    private static final String TAG = BibleLab.class.getName();

    private static BibleLab sBibleLab;
    private Context mContext;
    private SQLiteDatabase db;

    private BibleBaseHelper mHelper;
    private Bible mBible;
    ArrayList<String> bibleNames;

    public BibleLab(Context context) {
        mContext = context.getApplicationContext();
        mHelper = new BibleBaseHelper(context);
    }

    public static BibleLab getIntence(Context context){
        if (sBibleLab == null){
            sBibleLab = new BibleLab(context);
        }
        return sBibleLab;
    }

    public ArrayList<String> setbibleNames() {

        String [][] bibleNames = mHelper.getBibleNames();
        ArrayList<String> date_names = new ArrayList<>();
        ArrayList<String> date_numbers = new ArrayList<>();

        for (int i = 0; i < bibleNames.length; i++) {
            String number = bibleNames[i][0];
            String names = bibleNames[i][1];
            date_names.add(names);
        }
        //선택한 성경의 숫자를 찾으려면,
        return date_names;
    }
    //어떤 성경버전을 선택하고 그 버전의 해당 성경을 선택했을때, 그 성경의 장을 보여주는 어레이 리스트
    public ArrayList<String> getSelectedChapter(String bibleVersion ,int bibleNumber){

        return mHelper.getCountSelectedChapter(bibleVersion, bibleNumber);
    }

    /**
     *  성경 버전을 선택하고 그 성경 버전에서 어떤 성경을 선택하고 그리고 챕터를 선택했을때, 벌스가 얼마나 있는지 보여준다.
     * @param bibleVersion
     * @param bibleNumber
     * @param ChapterNumber
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> getSelectedVerse(String bibleVersion, int bibleNumber, int ChapterNumber){
        return mHelper.getCountSelectedVerse(bibleVersion, bibleNumber, ChapterNumber);
    }

    /**
     *  성경 버전을 선택하고 그 성경 버전에서 어떤 성경을 선택하고 그리고 챕터를 선택했을때, 해당 챕터의 텍스트를 가져온다.
     * @param bibleVersion
     * @param bibleNumber
     * @param bibleChapter
     * @return
     */
    public String[][] getSelectVerse(String bibleVersion , int bibleNumber, int bibleChapter) {
        return mHelper.getSelectedTextArea(bibleVersion,bibleNumber,bibleChapter);
    }

}
