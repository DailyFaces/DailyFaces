package com.pn.groupC.dailyfaces;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.sql.SQLOutput;
import java.util.List;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import static com.pn.groupC.dailyfaces.R.drawable.download;

public class CustomListAdapter extends ArrayAdapter<String> {

    private Activity context;
    private List<MessageGroup> messageGroupList;

    public CustomListAdapter(Activity context, List<MessageGroup> messageGroupList) {
        super(context, R.layout.list_messages);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.messageGroupList=messageGroupList;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.content_main, null,true);
        ImageView img= rowView.findViewById(R.id.icon);
        TextView text = rowView.findViewById(R.id.title);
        TextView text2 = rowView.findViewById(R.id.lastMessage);

        MessageGroup MessageGroup = messageGroupList.get(position);

        text.setText(MessageGroup.getUsername());

        text2.setText(MessageGroup.getLastMessage());

        img.setImageResource(MessageGroup.getImageUrl());

//        Resources res = getResources();
//        Bitmap src = BitmapFactory.decodeResource(res, iconResource);
//        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res, src);
//        dr.setCornerRadius(Math.max(src.getWidth(), src.getHeight()) / 2.0f);
//        img.setImageDrawable(dr);
//        Resources res1 = Resources.getSystem();
//        Bitmap bitmap = BitmapFactory.decodeResource(res, 0);
//        img.setImageResource(messageGroupList.getAdapter().getItem()[position]);
//        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(res, Bitmap.createScaledBitmap(bitmap, 250, 250, false));
//        circularBitmapDrawable.setCircular(true);
//        img.setImageDrawable(circularBitmapDrawable);
//        text.setText(username[position]);
//        img.setImageDrawable(download);
        return rowView;
    };
}
