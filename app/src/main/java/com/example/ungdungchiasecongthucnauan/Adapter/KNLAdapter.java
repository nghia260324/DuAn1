package com.example.ungdungchiasecongthucnauan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.ungdungchiasecongthucnauan.Model.KieuNguyenLieu;
import com.example.ungdungchiasecongthucnauan.R;

import java.util.ArrayList;

public class KNLAdapter extends ArrayAdapter<KieuNguyenLieu> {
    private Context context;
    private ArrayList<KieuNguyenLieu> lstKNL;
    public KNLAdapter(@NonNull Context context, int resource, ArrayList<KieuNguyenLieu> lstKNL) {
        super(context,resource,lstKNL);
        this.context = context;
        this.lstKNL = new ArrayList<>(lstKNL);
    }


    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_spinner_knl,parent,false);
        }
        KieuNguyenLieu kieuNguyenLieu = getItem(position);
        TextView tvTypeName = convertView.findViewById(R.id.tv_typeName);
        tvTypeName.setText(kieuNguyenLieu.getTenKieu());
        return convertView;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<KieuNguyenLieu> lstK = new ArrayList<>();
                if (constraint != null && constraint.length() > 0){
                    String fillter = constraint.toString().toLowerCase().trim();
                    for(KieuNguyenLieu kieuNguyenLieu : lstKNL){
                        if(kieuNguyenLieu.getTenKieu().toLowerCase().trim().contains(fillter)){
                            lstK.add(kieuNguyenLieu);
                        }
                    }
                } else {
                    lstK.addAll(lstKNL);
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = lstK;
                filterResults.count = lstK.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll((ArrayList<KieuNguyenLieu>)results.values);
                notifyDataSetInvalidated();
            }
            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((KieuNguyenLieu)resultValue).getTenKieu();
            }
        };
    }
}
