package com.mikeshehadeh.lab6;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyRecyclerAdapter
        extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    private List<Contact> mContactList;
    private Context mContext;
    private RecyclerView myRecyclerView;
    public MyRecyclerAdapter (List<Contact> myDataset, Context context, RecyclerView recyclerView) {
        mContactList = myDataset;
        mContext = context;
        myRecyclerView = recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name_v;
        public TextView lastname_v;
        public TextView phone_v;
        public ViewHolder (View view) {
            super(view);
            name_v = (TextView)view.findViewById(R.id.name_view);
            lastname_v = (TextView)view.findViewById(R.id.lastname_view);
            phone_v = (TextView)view.findViewById(R.id.phone_number);
        }
    }

    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
        final ViewHolder view_holder = new ViewHolder(v);
        return view_holder;
    }

    public void updateList (List<Contact> myDataset) {
        mContactList = myDataset;
        notifyDataSetChanged();
    }

    public void onBindViewHolder (final ViewHolder holder, final int position) {
        final Contact c = mContactList.get(position);
        holder.name_v.setText("Name: " + c.getName());
        holder.lastname_v.setText("Last Name: " + c.getLastname());
        holder.phone_v.setText("Phone: " + c.getPhone_number());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose Option");
                builder.setMessage("Update or Delete User?");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        goToUpdateActivity(c.getId());
                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyDBHelper dbHelper = new MyDBHelper(mContext);
                        dbHelper.deleteContact(c.getId(), mContext);
                        mContactList.remove(position);
                        myRecyclerView.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();

            }
        });
    }

    private void goToUpdateActivity(long personId) {
        Intent intent = new Intent(mContext, UpdateContact.class);
        intent.putExtra("CONTACT_ID", personId);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }
}
