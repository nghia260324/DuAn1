package com.example.ungdungchiasecongthucnauan.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungchiasecongthucnauan.IReturnString;
import com.example.ungdungchiasecongthucnauan.R;

import java.util.ArrayList;

public class SearchSuggestionAdapter extends RecyclerView.Adapter<SearchSuggestionAdapter.ViewHolder> {
    ArrayList<String> lstString;
    IReturnString returnString;

    public SearchSuggestionAdapter(ArrayList<String> lstString,IReturnString iReturnString) {
        this.lstString = lstString;
        this.returnString = iReturnString;
    }

    @NonNull
    @Override
    public SearchSuggestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_searchsuggestion,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchSuggestionAdapter.ViewHolder holder, int position) {
        String name = lstString.get(position);
        holder.tvName.setText(name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnString.ReturnString(name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return !lstString.isEmpty()?lstString.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
