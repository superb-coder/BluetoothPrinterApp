package com.ecare.printer.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecare.printer.R;

import java.util.ArrayList;
import java.util.List;

public class CallListAdapter extends BaseAdapter {

    List<String> numbers;
    Context context;

    public CallListAdapter(Context c, List<String> callNumbers){
        this.context = c;
        this.numbers = callNumbers;
    }
    @Override
    public int getCount() {
        return numbers.size();
    }

    @Override
    public Object getItem(int position) {
        return numbers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_call_list, parent, false);
        TextView callNumView = view.findViewById(R.id.callNumView);
        ImageView callBtnView = view.findViewById(R.id.callBtn);
        Log.d("Call number", String.valueOf(this.numbers));
        String callNum = this.numbers.get(position);
        callNumView.setText(this.numbers.get(position));
        callBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+callNum));
                context.startActivity(callIntent);
            }
        });
        return view;
    }
}
