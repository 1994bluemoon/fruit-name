package com.example.dminh.fruitname.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dminh.fruitname.Models.FruitNames;
import com.example.dminh.fruitname.R;

import java.util.List;

/**
 * Created by dminh on 12/11/2017.
 */

public class ListviewAdapter extends BaseAdapter {

    Context ContextObj;
    List<FruitNames> TempList;

    public ListviewAdapter(List<FruitNames> listValue, Context context)
    {
        this.ContextObj = context;

        this.TempList = listValue;
    }

    @Override
    public int getCount()
    {
        return this.TempList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.TempList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewItem viewItem = null;

        if(convertView == null)
        {
            viewItem = new ViewItem();

            LayoutInflater layoutInfiater = (LayoutInflater)this.ContextObj.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInfiater.inflate(R.layout.listview_items, null);

            viewItem.FruitNameTextView = (TextView)convertView.findViewById(R.id.tv_lv_it_fruitname);

            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItem) convertView.getTag();
        }

        viewItem.FruitNameTextView.setText(TempList.get(position).Fruit_Name);

        return convertView;
    }
}

class ViewItem
{
    TextView FruitNameTextView;
}

