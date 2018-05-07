package cn.democpp.www.contentprovidertask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteZOpenHelper extends SQLiteOpenHelper {

    public MySQLiteZOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table Person(" +
                "id integer primary key autoincrement," +
                "name varchar(20)," +
                "age intger" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("数据库变化","数据库");
        onCreate(sqLiteDatabase);
    }
}
