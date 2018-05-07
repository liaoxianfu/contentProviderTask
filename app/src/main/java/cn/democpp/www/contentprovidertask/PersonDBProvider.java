package cn.democpp.www.contentprovidertask;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class PersonDBProvider extends ContentProvider {
    //创建表名
    private static final String TABLENAME = "Person";
    //创建uri匹配器 用于匹配uri 如果路径不满足就返回-1
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    //创建数据匹配成功返回码
    private static final int INSERT = 1;
    private static final int DELETE = 2;
    private static final int UPDATE = 3;
    private static final int QUERY = 4;
    private static final int QUERYONE = 5;

    //创建路径值
    private static final String LOCATION = "cn.democpp.www.contentprovidertask";

    private MySQLiteZOpenHelper helper;


    static {
        matcher.addURI(LOCATION, "insert", INSERT);
        matcher.addURI(LOCATION, "delete", DELETE);
        matcher.addURI(LOCATION, "update", UPDATE);
        matcher.addURI(LOCATION, "query", QUERY);
        matcher.addURI(LOCATION, "query/#", QUERYONE);

    }

    @Override
    public boolean onCreate() {
        helper = new MySQLiteZOpenHelper(getContext(), "person.db", null, 1);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String orderBy) {
        if (matcher.match(uri) == QUERY) {
            SQLiteDatabase database = helper.getReadableDatabase();
            Cursor cursor = database.query(TABLENAME, projection, selection, selectionArgs, null, null, orderBy);
            return cursor;
        } else if (matcher.match(uri) == QUERYONE) {
            long id = ContentUris.parseId(uri);
            SQLiteDatabase database = helper.getReadableDatabase();
            Cursor cursor = database.query(TABLENAME, projection, selection, selectionArgs, null, null, orderBy);
            return cursor;
        } else {
            throw new IllegalArgumentException("路径不正确");
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        if (matcher.match(uri) == QUERY) {
            return "vnd.android.cursor.dir/person";
        } else if (matcher.match(uri) == QUERYONE) {
            return "vnd.android.cursor.item/person";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if (matcher.match(uri) == INSERT) {
            SQLiteDatabase database = helper.getWritableDatabase();
            database.insert(TABLENAME, null, contentValues);
        } else {
            throw new IllegalArgumentException("ERROR");
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (matcher.match(uri) == DELETE) {
            SQLiteDatabase database = helper.getWritableDatabase();
            database.delete(TABLENAME, selection, selectionArgs);
        } else {
            throw new IllegalArgumentException("ERROR");
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        if (matcher.match(uri) == UPDATE) {

            SQLiteDatabase database = helper.getWritableDatabase();
        } else {
            throw new IllegalArgumentException("ERROR");
        }
        return 0;
    }
}
