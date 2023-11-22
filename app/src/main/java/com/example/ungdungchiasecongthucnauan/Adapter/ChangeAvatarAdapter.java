package com.example.ungdungchiasecongthucnauan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungchiasecongthucnauan.IReturnString;
import com.example.ungdungchiasecongthucnauan.MainActivity;
import com.example.ungdungchiasecongthucnauan.R;

import java.util.ArrayList;

public class ChangeAvatarAdapter extends RecyclerView.Adapter<ChangeAvatarAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> lstAvatar;
    private MainActivity mainActivity;
    private IReturnString returnString;
    public int selectedItem = RecyclerView.NO_POSITION;
    public ChangeAvatarAdapter(Context context, ArrayList<String> lstAvatar,MainActivity mainActivity,IReturnString iReturnString) {
        this.context = context;
        this.lstAvatar = lstAvatar;
        this.mainActivity = mainActivity;
        this.returnString = iReturnString;
    }

    @NonNull
    @Override
    public ChangeAvatarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_change_avatar,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChangeAvatarAdapter.ViewHolder holder, int position) {
        String avatar = lstAvatar.get(position);
        int index = position;
        int resID = context.getResources().getIdentifier(avatar, "drawable", context.getPackageName());

        if (position == selectedItem) {
            holder.layout_select.setVisibility(View.VISIBLE);
        } else {
            holder.layout_select.setVisibility(View.GONE);
        }

        if (resID != 0) {
            holder.imgAvatar.setImageResource(resID);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnString.ReturnString(avatar);
                int previousSelectedItem = selectedItem;
                selectedItem = holder.getAdapterPosition();
                notifyItemChanged(previousSelectedItem);
                notifyItemChanged(selectedItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstAvatar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        RelativeLayout layout_select;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            layout_select = itemView.findViewById(R.id.layout_select);
        }
    }
}
