package com.example.admin.grocerypricewholesale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Admin on 1/3/2018.
 */

public class CustomListViewAdapter extends BaseAdapter{
    Context c;
    ArrayList<String> items;
    ArrayList<String> prices;
    LayoutInflater inflater;

    CustomListViewAdapter(Context context,ArrayList<String> items,ArrayList<String> prices){
        c=context;
        this.items=items;
        this.prices=prices;
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public static class Hold{
        TextView tvsno;
        TextView tvitem;
        TextView tvprice;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View listview;
        Hold hold=new Hold();

        listview=inflater.inflate(R.layout.customlistview,null);

        hold.tvsno=listview.findViewById(R.id.tvsno);
        hold.tvitem=listview.findViewById(R.id.tvitems);
        hold.tvprice=listview.findViewById(R.id.tvprice);

        hold.tvsno.setText(String.valueOf(i+1));
        hold.tvitem.setText(items.get(i));
        hold.tvprice.setText("₹ "+prices.get(i));

        return listview;
    }


}
