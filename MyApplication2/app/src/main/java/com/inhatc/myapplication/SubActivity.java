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






