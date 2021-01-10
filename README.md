# Android 2020 project
## 전화번호부 v0.1

### 주요 코드

#### DBHelper.java
    package com.inhatc.myapplication;


    import android.content.Context;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;

    public class DBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "test5.db";
    //static final int DATABASE_VERSION = 2;

    public DBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tableName ( name TEXT NOT NULL, tele TEXT PRIMARY KEY NOT NULL, email TEXT, member TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tableName");
        onCreate(db);
    }

    }

#### MainActivity.java
    package com.inhatc.myapplication;

    import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;

    import android.os.Bundle;
    import android.telephony.PhoneNumberFormattingTextWatcher;
    import android.view.View;
    import android.widget.ArrayAdapter;
    import android.widget.EditText;
    import android.widget.ListView;
    import android.widget.Toast;

    public class MainActivity extends AppCompatActivity {

    EditText editText, editText2, editText3, editText4;

    DBHelper dbHelper;
    SQLiteDatabase db = null;
    String strSQL = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText1);

        editText2 = findViewById(R.id.editText2);
        editText2.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);


        dbHelper = new DBHelper(this, 4);
        db = dbHelper.getWritableDatabase();    // 읽기/쓰기 모드로 데이터베이스를 오픈


    }
    public void Next(View v){
        Intent intent = new Intent(MainActivity.this, SubActivity.class);
        startActivity(intent);
    }


    public void insert(View v) {
        try {
            String name = editText.getText().toString();
            String tele = editText2.getText().toString();
            String email = editText3.getText().toString();
            String member = editText4.getText().toString();
            if(name.getBytes().length <=0) {
                Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
            else if (tele.getBytes().length <= 0){
                Toast.makeText(getApplicationContext(), "번호 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
            else {
                db.execSQL("INSERT INTO tableName VALUES ('" + name + "', '" + tele + "', '" + email + "', '" + member + "');");
                Toast.makeText(getApplicationContext(), "저장 완료", Toast.LENGTH_SHORT).show();

                editText.setText("");
                editText2.setText("");
                editText3.setText("");
                editText4.setText("");
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "잘못된 양식이거나 이미 저장된 번호 입니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(View v) {
        String tele = editText2.getText().toString();
        if (tele.getBytes().length <= 0) {
            Toast.makeText(getApplicationContext(), "삭제할 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            db.execSQL("DELETE FROM tableName WHERE tele = '" + tele + "';");
            Toast.makeText(getApplicationContext(), "삭제 완료", Toast.LENGTH_SHORT).show();
        }
    }

    public void modify(View v) {
        String name = editText.getText().toString();
        if (name.getBytes().length <= 0) {
            Toast.makeText(getApplicationContext(), "수정할 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            strSQL = "Update tableName Set ";
            strSQL += "name = " + "'" + editText.getText().toString() + "', ";
            strSQL += "tele = " + "'" + editText2.getText().toString() + "', ";
            strSQL += "email = " + "'" + editText3.getText().toString() + "', ";
            strSQL += "member = " + "'" + editText4.getText().toString() + "' ";
            strSQL += " Where name = '" + editText.getText().toString() + "';";
            db.execSQL(strSQL);
            Toast.makeText(getApplicationContext(), "수정 완료", Toast.LENGTH_SHORT).show();

            editText.setText("");
            editText2.setText("");
            editText3.setText("");
            editText4.setText("");
        }
    }

}
#### SubActivity.java
    package com.inhatc.myapplication;


    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.ArrayAdapter;
    import android.widget.EditText;
    import android.widget.ListView;


    import androidx.appcompat.app.AppCompatActivity;

    public class SubActivity extends AppCompatActivity {
    EditText editText;
    ListView listView, listView2, listView3, listView4;

    DBHelper dbHelper;
    SQLiteDatabase db = null;
    Cursor cursor;
    ArrayAdapter adapter, adapter2, adapter3, adapter4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        listView = findViewById(R.id.listView1);
        listView2 = findViewById(R.id.listView2);
        listView3 = findViewById(R.id.listView3);
        listView4 = findViewById(R.id.listView4);

        dbHelper = new DBHelper(this, 4);
        db = dbHelper.getWritableDatabase();

    }
    public void listUpdate(View v) {
        cursor = db.rawQuery("SELECT * FROM tableName", null);
        startManagingCursor(cursor);    //엑티비티의 생명주기와 커서의 생명주기를 같게 한다.

        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1);
        adapter2 = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1);
        adapter3 = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1);
        adapter4 = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1);

        while (cursor.moveToNext()) {
            adapter.add(cursor.getString(0));
            adapter2.add(cursor.getString(1));
            adapter3.add(cursor.getString(2));
            adapter4.add(cursor.getString(3));
        }

        /*
        cursor.moveToFirst();
        cursor.moveToPrevious();
        cursor.moveToPosition(2);
        */

        listView.setAdapter(adapter);
        listView2.setAdapter(adapter2);
        listView3.setAdapter(adapter3);
        listView4.setAdapter(adapter4);
    }




}

#### activity_main.xml
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <EditText
            android:id="@+id/editText1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:ems="10"
            android:hint="이 름"
            android:inputType="textPersonName" />

        <EditText
            android:id="@+id/editText2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:ems="10"
            android:hint="번 호"
            android:inputType="phone" />
    </LinearLayout>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <EditText
            android:id="@+id/editText3"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:ems="10"
            android:hint="이 메 일"
            android:inputType="textPersonName" />

        <EditText
            android:id="@+id/editText4"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:ems="10"
            android:hint="그 룹"
            android:inputType="textPersonName" />
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <Button
            android:id="@+id/button"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:onClick="insert"
            android:text="추 가" />

        <Button
            android:id="@+id/button2"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:onClick="delete"
            android:text="삭 제" />

        <Button
            android:id="@+id/button3"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:onClick="modify"
            android:text="수 정" />
    </LinearLayout>

    <Button
        android:id="@+id/button4"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="Next"
        android:text="목 록 보 기" />
    </LinearLayout>
    
#### activity_sub.xml
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <Button
        android:id="@+id/button1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="listUpdate"
        android:text="불 러 오 기" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <TextView
            android:id="@+id/textName"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="이 름"
            android:gravity="center"/>

        <TextView
            android:id="@+id/textView2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="번 호"
            android:gravity="center"/>

        <TextView
            android:id="@+id/textView3"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="이 메 일"
            android:gravity="center"/>

        <TextView
            android:id="@+id/textView4"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="그 룹"
            android:gravity="center"/>
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="horizontal">

        <ListView
            android:id="@+id/listView1"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_weight="1">


        </ListView>

        <ListView
            android:id="@+id/listView2"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_weight="1" />
        <ListView
            android:id="@+id/listView3"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_weight="1" />

        <ListView
            android:id="@+id/listView4"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_weight="1" />


       </LinearLayout>
