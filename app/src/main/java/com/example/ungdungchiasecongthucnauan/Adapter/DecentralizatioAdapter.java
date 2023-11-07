package com.example.ungdungchiasecongthucnauan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ungdungchiasecongthucnauan.Model.Decentralization;
import com.example.ungdungchiasecongthucnauan.R;

import java.util.ArrayList;
import java.util.List;

public class DecentralizatioAdapter extends ArrayAdapter<Decentralization> {
    Context context;
    ArrayList<Decentralization> lstD;
    TextView tvDecentralization;

    public DecentralizatioAdapter(Context context, int resource,ArrayList<Decentralization> objects) {
        super(context, resource, objects);
        this.context = context;
        this.lstD = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_decentralization_view,parent,false);
        }
        Decentralization decentralization = getItem(position);
        if (decentralization != null){
            tvDecentralization = convertView.findViewById(R.id.tv_idecentralization);
            tvDecentralization.setText(decentralization.getContent());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_decentralization_selected,parent,false);
        }
        Decentralization decentralization = getItem(position);
        if (decentralization != null){
            tvDecentralization = convertView.findViewById(R.id.tv_idecentralization);
            tvDecentralization.setText(decentralization.getContent());
        }
        return convertView;
    }
}
