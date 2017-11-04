package database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.LinkedList;


public class BaseData {
    static SQLiteDatabase prodb_ob;
    static Context co;
    public static void open_or_create_db(Context c){
        prodb_ob = c.openOrCreateDatabase("MyDB", Context.MODE_PRIVATE, null);
        prodb_ob.execSQL("CREATE TABLE IF NOT EXISTS block_list_table (p_key INTEGER PRIMARY KEY ,  phone_no VARCHAR(15))");
        prodb_ob.close();

    }

    public static void insert_db(String ph_no, Context c){

        prodb_ob = c.openOrCreateDatabase("MyDB", Context.MODE_PRIVATE, null);
        String sql = "insert into block_list_table ( phone_no) values(?)";
        Object[] ob= new Object[1];
        ob[0]= ph_no;
        prodb_ob.execSQL(sql,ob);
        prodb_ob.close();
        Toast.makeText(c, "("+ph_no+") is added to your block list", Toast.LENGTH_LONG).show();

    }

    public static LinkedList<String> fatch_db(Context cone){
        LinkedList<String> res = new LinkedList<>();
        prodb_ob = cone.openOrCreateDatabase("MyDB", Context.MODE_PRIVATE, null);
        Cursor c = prodb_ob.rawQuery("SELECT * FROM block_list_table", null);
        while (c.moveToNext()) {
            String s = c.getString(c.getColumnIndex("phone_no"));
            res.add(s);
        }
        prodb_ob.close();
        return res;

    }
    public static boolean check_block_list(Context cone, String num){
        boolean res = false;
        prodb_ob = cone.openOrCreateDatabase("MyDB", Context.MODE_PRIVATE, null);
        Cursor c = prodb_ob.rawQuery("SELECT * FROM block_list_table", null);
        while (c.moveToNext()) {
            String s = c.getString(c.getColumnIndex("phone_no"));
            if (s.equalsIgnoreCase(num)){
                res = true;
            }

        }
        prodb_ob.close();
        return res;

    }

    public static void delete_blocked_no_db(String pas_data, Context c){
        prodb_ob = c.openOrCreateDatabase("MyDB", Context.MODE_PRIVATE, null);
        String[] whereArgs = {pas_data.toString()};
        prodb_ob.delete("block_list_table", "phone_no "+"=?", whereArgs);
        prodb_ob.close();
    }
}
