package com.example.loopstr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ssjoleary on 30/03/14.
 */
public class MessageAdapter extends ArrayAdapter<BusinessMessage> {

    private final Context context;
    private final ArrayList<BusinessMessage> itemsArrayList;

    public MessageAdapter(Context context, ArrayList<BusinessMessage> itemsArrayList) {

        super(context, R.layout.row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.row, parent, false);

        // 3. Get the two text view from the rowView
        assert rowView != null;
        TextView labelView = (TextView) rowView.findViewById(R.id.label);

        // 4. Set the text for textView
        labelView.setText(itemsArrayList.get(position).getMessage());

        // 5. return rowView
        return rowView;
    }
}
