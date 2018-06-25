package com.ramzcalender.sample;

/**
 * Created by edison office on 5/9/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private static int idname;
    private LayoutInflater layoutInflater;
    public List<Contacts> cont;
    Contacts list;
    private ArrayList<Contacts> arraylist;
    boolean checked = false;
    View vv;
    private String recy_item;

    public static ArrayList<String> cont_nam = new ArrayList<String>();
    public static String contact_count;
    public RecyclerAdapter(LayoutInflater inflater, List<Contacts> items) {
        this.layoutInflater = inflater;
        this.cont = items;
        this.arraylist = new ArrayList<Contacts>();
        this.arraylist.addAll(cont);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.contactslist_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        list = cont.get(position);
        final String name = (list.getName());

    //    cont_nam.clear();

        holder.title.setText(name);
        holder.phone.setText(list.getPhone());

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Snackbar.make(v, "Click detected on item " + name,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                contact_count="contacts";
                cont_nam.add(name);

                Context context = v.getContext();
                Intent intent = new Intent(context, Calender_task.class);
              //  intent.putExtra("text", name);
                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return cont.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView phone;
        public LinearLayout contact_select_layout;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.setIsRecyclable(false);
            title = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.no);
            contact_select_layout = (LinearLayout) itemView.findViewById(R.id.contact_select_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                  /*  idname=  itemView.getId();
                    Context context = v.getContext();
                    Intent intent = new Intent(context, Calender_task.class);
                    intent.putExtra(Calender_task.EXTRA_POSITION, getAdapterPosition());
                    context.startActivity(intent);
*/

                }
            });

        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
