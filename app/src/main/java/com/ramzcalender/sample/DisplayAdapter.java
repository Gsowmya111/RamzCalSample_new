package com.ramzcalender.sample;

/**
 * Created by edison office on 4/10/2018.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayAdapter extends BaseAdapter {
    private Context mContext;
    //list fields to be displayed
    private ArrayList<String> anum;
    private ArrayList<String> ahousenum;
    private ArrayList<String> ahousename;
    private ArrayList<String> aroomnum;
    private String one,two,three,four;
    private Object fsfs;
    static String aanum,aahousenum,aahousename,aaroomnum,aadevicename,aadevicenum,aaswitch1,aastatus,aadata,aamood_type;
    static LayoutInflater layoutInflater;
    DataBaseHandler db = null;
    static String device_name;

    public DisplayAdapter(Context c, ArrayList<String> anum) {
        this.mContext = c;
        //transfer content from database to temporary memory
        this.anum = anum;

        this.ahousenum = ahousenum;
        this.ahousename = ahousename;
        this.aroomnum = aroomnum;
    }



    public int getCount() {
        // TODO Auto-generated method stub
        return anum.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    public View getView(int pos, View child, ViewGroup parent) {
        Holder mHolder;

        if (child == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.single_list_item_atendee, null);
            mHolder = new Holder();

            //link to TextView
            mHolder.tno = (TextView) child.findViewById(R.id.atendee_del_icon);
            mHolder.msg = (TextView) child.findViewById(R.id.atendee_del_text);


            child.setTag(mHolder);
        } else {
            mHolder = (Holder) child.getTag();
        }


        //transfer to TextView in screen
      //  mHolder.tno.setText(anum.get(pos));
        mHolder.msg.setText(anum.get(pos));

        return child;
    }

    public class Holder {
        TextView tno;
        TextView msg;


    }

}