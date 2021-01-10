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







