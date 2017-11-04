package com.example.shubhamchandrakar.callblocker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import database.BaseData;

public class BlockList extends Activity {

     ListView block_lv;
    public void refresh_listview(){

        block_lv= (ListView) findViewById(R.id.block_listview);
        LinkedList<String> link_s1= BaseData.fatch_db(BlockList.this);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(BlockList.this, android.R.layout.simple_list_item_1, link_s1);
        block_lv.setAdapter(aa);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_list);
        refresh_listview();
        Button add_btn = (Button) findViewById(R.id.add_button);
        block_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv1  =(TextView) view;
                final String no1 = tv1.getText().toString();
                List<String> options = new ArrayList<String>();
                options.add("Delete");
                final CharSequence[] all_option= options.toArray(new String[options.size()]);
                AlertDialog.Builder alert_dialog_profile_mode = new AlertDialog.Builder(BlockList.this);
                alert_dialog_profile_mode.setItems(all_option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int option_position) {
                        if (option_position==0) {
                            BaseData.delete_blocked_no_db(no1,BlockList.this);
                            refresh_listview();
                        }
                    }
                });
                alert_dialog_profile_mode.create();
                alert_dialog_profile_mode.show();
                return false;
            }
        });
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(BlockList.this, AddBlockList.class);
                startActivity(i3);
                BlockList.this.finish();
            }
        });
    }
}
