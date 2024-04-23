package com.example.travelerstote.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelerstote.Constants.MyConstants;
import com.example.travelerstote.Database.RoomDB;
import com.example.travelerstote.Models.Items;
import com.example.travelerstote.R;

import java.util.List;

public class CheckListAdepter extends RecyclerView.Adapter<CheckListViewHolder> {

    Context context;
    List<Items> itemsList;
    RoomDB database;
    String show;

    public CheckListAdepter(Context context, List<Items> itemsList, RoomDB database, String show) {
        this.context = context;
        this.itemsList = itemsList;
        this.database = database;
        this.show = show;
        if (itemsList.isEmpty())
            Toast.makeText(context.getApplicationContext(), "Nothing to show", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public CheckListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckListViewHolder(LayoutInflater.from(context).inflate(R.layout.check_list_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CheckListViewHolder holder, int position) {
        Items currentItem = itemsList.get(position);

        holder.checkBox.setText(currentItem.getItemname());
        holder.checkBox.setChecked(currentItem.getChecked());

        if (MyConstants.FALSE_STRING.equals(show)) {
            holder.btnDelete.setVisibility(View.GONE);
            holder.layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.border_one));
        } else {
            if (currentItem.getChecked()) {
                holder.layout.setBackgroundColor(Color.parseColor("#8e546f"));
            } else
                holder.layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.border_one));
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean check = holder.checkBox.isChecked();
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Items clickedItem = itemsList.get(adapterPosition);
                    database.mainDao().checkUncheck(clickedItem.getID(), check);
                    if (MyConstants.FALSE_STRING.equals(show)) {
                        itemsList = database.mainDao().getAllSelected(true);
                        notifyDataSetChanged();
                    } else {
                        clickedItem.setChecked(check);
                        notifyDataSetChanged();
                        Toast toastMessage = null;
                        if (toastMessage != null) {
                            toastMessage.cancel();
                        }
                        if (check) {
                            toastMessage = Toast.makeText(context, "(" + holder.checkBox.getText() + ")Packed", Toast.LENGTH_SHORT);
                        } else {
                            toastMessage = Toast.makeText(context, "(" + holder.checkBox.getText() + ")Un-Packed", Toast.LENGTH_SHORT);
                        }
                        toastMessage.show();
                    }
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Items currentItem = itemsList.get(adapterPosition);
                    new AlertDialog.Builder(context)
                            .setTitle("Delete(" + currentItem.getItemname() + ")")
                            .setMessage("Are you sure?")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    database.mainDao().delete(currentItem);
                                    itemsList.remove(currentItem);
                                    notifyDataSetChanged();
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                                }
                            }).setIcon(R.drawable.ic_delete)
                            .show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}


class CheckListViewHolder extends RecyclerView.ViewHolder {


    LinearLayout layout;
    CheckBox checkBox;
    Button btnDelete;

    public CheckListViewHolder(@NonNull View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.linearLayout);
        checkBox = itemView.findViewById(R.id.checkbox);
        btnDelete = itemView.findViewById(R.id.btnDelete);
    }
}
