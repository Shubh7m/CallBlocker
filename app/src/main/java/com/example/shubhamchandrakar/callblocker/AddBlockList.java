package com.example.shubhamchandrakar.callblocker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import database.BaseData;

public class AddBlockList extends Activity {

    ListView lv ;
    EditText search_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_block_list);
        final Button search_btn = (Button) findViewById(R.id.add_button);
        search_et = (EditText) findViewById(R.id.search_editText);
        lv = (ListView) findViewById(R.id.listview1);
        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                populateList();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long i;
                String str= null;
                try {
                    i = Long.parseLong(search_et.getText().toString());
                    str = Long.toString(i);
                    if (str.length()==10){
                        BaseData.insert_db(search_et.getText().toString(),AddBlockList.this);
                        Intent i4 = new Intent(AddBlockList.this, BlockList.class);
                        startActivity(i4);
                        AddBlockList.this.finish();
                    }

                    Log.e("ASD","i : "+ i);
                }
                catch (Exception e){
                    Toast.makeText(AddBlockList.this, "Please enter valid PPHONE-NUMBER", Toast.LENGTH_LONG).show();
                    search_et.setText("");
                Log.e("ASD","i : "+ e);
                }
//                Toast.makeText(AddBlockList.this, search_et.getText(), Toast.LENGTH_SHORT).show();



            }
        });

    }
    private void populateList() {
        LinkedList<String> res = new LinkedList<>();
        String whr = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME  + " like ? ";
        String sa[] = new String[1];
        sa[0]="%"+search_et.getText()+"%";
        Cursor c1 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, whr, sa, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int in1 = c1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int in2 = c1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        while(c1.moveToNext()){
            String name = c1.getString(in1);
            String no = c1.getString(in2);
            res.add(name + " : " + no);
        }
        ArrayAdapter<String> aa = new ArrayAdapter<String>(AddBlockList.this, android.R.layout.simple_list_item_1, res);
        lv.setAdapter(aa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv1  =(TextView) view;
                String no1 = tv1.getText().toString();
                 no1 = no1.replaceAll("-", "").trim();
                no1 = no1.replace("+", "").trim();
                no1 = no1.replaceAll(" ", "").trim();
                String no2 = no1.substring(no1.length()-10);
                search_et.setText(no2);
            }
        });
    }
}



