package com.example.ungdungchiasecongthucnauan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.ungdungchiasecongthucnauan.Model.LoaiCongThuc;
import com.example.ungdungchiasecongthucnauan.R;

import java.util.ArrayList;

public class LoaiCongThucAdapter extends ArrayAdapter<LoaiCongThuc> {
    private Context context;
    private ArrayList<LoaiCongThuc> lstNL;
    public LoaiCongThucAdapter(@NonNull Context context, int resource, ArrayList<LoaiCongThuc> lstNL) {
        super(context,resource,lstNL);
        this.context = context;
        this.lstNL = new ArrayList<>(lstNL);
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_spinner_knl,parent,false);
        }
        LoaiCongThuc loaiCongThuc = getItem(position);
        TextView tvTypeName = convertView.findViewById(R.id.tv_typeName);
        tvTypeName.setText(loaiCongThuc.getTenLoai());
        return convertView;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<LoaiCongThuc> lstK = new ArrayList<>();
                if (constraint != null && constraint.length() > 0){
                    String fillter = constraint.toString().toLowerCase().trim();
                    for(LoaiCongThuc loaiCongThuc : lstNL){
                        if(loaiCongThuc.getTenLoai().toLowerCase().trim().contains(fillter)){
                            lstK.add(loaiCongThuc);
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
                addAll((ArrayList<LoaiCongThuc>)results.values);
                notifyDataSetInvalidated();
            }
            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((LoaiCongThuc)resultValue).getTenLoai();
            }
        };
    }
}
