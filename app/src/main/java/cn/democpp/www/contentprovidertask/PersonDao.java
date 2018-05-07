package cn.democpp.www.contentprovidertask;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class PersonDao {
    //数据库操作
    private MySQLiteZOpenHelper helper;

    public PersonDao(Context context) {
        helper = new MySQLiteZOpenHelper(context, "person.db", null, 1);

    }

    public long addData(String name, int age) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("age", age);
        long id = database.insert("person", null, values);
        values.clear();
        database.close();
        return id;
    }

    public void cleanAll() {
        SQLiteDatabase database = helper.getWritableDatabase();
        database.delete("person", null, null);
        database.execSQL("UPDATE sqlite_sequence SET seq = 0 WHERE name = 'person'");
        database.close();
    }

}
