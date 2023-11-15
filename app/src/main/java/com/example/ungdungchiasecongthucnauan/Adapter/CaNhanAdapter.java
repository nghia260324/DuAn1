package com.example.ungdungchiasecongthucnauan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.ungdungchiasecongthucnauan.Fragment.FragmentCaNhan;
import com.example.ungdungchiasecongthucnauan.Fragment.IndividualFragment;
import com.example.ungdungchiasecongthucnauan.LoginActivity;
import com.example.ungdungchiasecongthucnauan.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class CaNhanAdapter extends ArrayAdapter<String> {




    public CaNhanAdapter(Context context, List<String> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_canhan, parent, false);
        }


        String item = getItem(position);


        TextView textView = itemView.findViewById(R.id.tvcanhan);
        textView.setText(item);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Phan quyen and them cac nut
                if (item.equals("Đổi mật khẩu")) {
                    // Thực hiện đổi mật khẩu
                } else if (item.equals("Đăng xuất")) {
                       logOut();
                }
            }
        });

        return itemView;
    }

    private void logOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(getContext(),LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getContext().startActivity(intent);
    }

}
