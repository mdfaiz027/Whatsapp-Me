package com.example.whatsappme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends BaseAdapter {

    Context context;
    List<HistoryModel> historyModelList = new ArrayList<>();
    LayoutInflater layoutInflater;

    public HistoryAdapter(Context context, List<HistoryModel> historyModelList) {
        this.context = context;
        this.historyModelList = historyModelList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return historyModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View root = layoutInflater.inflate(R.layout.mobile_list_layout, null);
        TextView phoneNumber = root.findViewById(R.id.phone_number);
        TextView time = root.findViewById(R.id.time);
        phoneNumber.setText(""+historyModelList.get(i).getMobileNumber());
        time.setText(""+historyModelList.get(i).getTime());

        return root;
    }
}
