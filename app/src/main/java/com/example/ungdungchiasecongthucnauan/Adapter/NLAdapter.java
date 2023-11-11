package com.example.ungdungchiasecongthucnauan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.ungdungchiasecongthucnauan.Model.NguyenLieu;
import com.example.ungdungchiasecongthucnauan.R;

import java.util.ArrayList;

public class NLAdapter extends ArrayAdapter<NguyenLieu> {
    private Context context;
    private ArrayList<NguyenLieu> lstNL;
    public NLAdapter(@NonNull Context context, int resource, ArrayList<NguyenLieu> lstNL) {
        super(context,resource,lstNL);
        this.context = context;
        this.lstNL = new ArrayList<>(lstNL);
    }


    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_spinner_knl,parent,false);
        }
        NguyenLieu nguyenLieu = getItem(position);
        TextView tvTypeName = convertView.findViewById(R.id.tv_typeName);
        tvTypeName.setText(nguyenLieu.getTen());
        return convertView;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<NguyenLieu> lstK = new ArrayList<>();
                if (constraint != null && constraint.length() > 0){
                    String fillter = constraint.toString().toLowerCase().trim();
                    for(NguyenLieu nguyenLieu : lstNL){
                        if(nguyenLieu.getTen().toLowerCase().trim().contains(fillter)){
                            lstK.add(nguyenLieu);
                        }
                    }
                } else {
                    lstK.addAll(lstNL);
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = lstK;
                filterResults.count = lstK.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll((ArrayList<NguyenLieu>)results.values);
                notifyDataSetInvalidated();
            }
            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((NguyenLieu)resultValue).getTen();
            }
        };
    }
}
