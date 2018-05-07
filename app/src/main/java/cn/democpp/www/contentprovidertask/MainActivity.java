package cn.democpp.www.contentprovidertask;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //创建适配器
    private ListView lv;
    private List<Person> personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 保存数据文件
        Button button = (Button) findViewById(R.id.btn_save);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                lv = (ListView) findViewById(R.id.list_view_id);
                new Thread() {
                    @Override
                    public void run() {
                        Log.d("成功0", "yuanixnv ");
                        addData();
                        getData();
                        if (personList.size() > 0) {
                            handler.sendEmptyMessage(100);
                        }
                    }
                }.start();
            }
        });
        // 清空数据库文件
        Button button1 = (Button) findViewById(R.id.btn_clean);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(){
                    @Override
                    public void run() {
                        new PersonDao(MainActivity.this).cleanAll();
                        getData();
                    }
                }.start();
            }
        });
    }


    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return personList.size();
        }

        @Override
        public Object getItem(int i) {
            return personList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view1, ViewGroup viewGroup) {
            Person person = personList.get(i);
            Log.d("Perple", person.getName());
            View view = View.inflate(MainActivity.this, R.layout.list_peson, null);

            TextView tv_id = (TextView) view.findViewById(R.id.list_item_id);
            TextView tv_name = (TextView) view.findViewById(R.id.list_name);
            TextView tv_age = (TextView) view.findViewById(R.id.list_item_age);
            tv_id.setText(person.getId() + "");
            tv_name.setText(person.getName());
            Log.d("name", person.getName());
            tv_age.setText(person.getAge() + "");
            Log.d("id", person.getId() + "");
            return view;
        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    lv.setAdapter(new MyAdapter());
                    break;
            }
        }

    };


    // 添加数据
    public void addData() {
        PersonDao dao = new PersonDao(MainActivity.this);
        EditText name_ed = (EditText) findViewById(R.id.name);
        String name = name_ed.getText().toString();
        Log.d("姓名", name);
        EditText age_ed = (EditText) findViewById(R.id.age);
        String age_s = age_ed.getText().toString();
        int age = Integer.valueOf(age_s);
        dao.addData(name, age);
    }

    // 获取数据

    public void getData() {
        String path = "content://cn.democpp.www.contentprovidertask/query";
        Uri uri = Uri.parse(path);
        ContentResolver contentResolver = getContentResolver();

        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        personList = new ArrayList<Person>();
        if (cursor == null)
            return;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            Person p = new Person(id, name, age);
            personList.add(p);
        }
        cursor.close();
    }


}
