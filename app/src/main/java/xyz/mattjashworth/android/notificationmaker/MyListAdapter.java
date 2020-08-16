package xyz.mattjashworth.android.notificationmaker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] maintitle;
    private final String[] subtitle;
    private final Integer[] imgid;

    public MyListAdapter(Activity context, String[] maintitle,String[] subtitle, Integer[] imgid) {
        super(context, R.layout.custom_listview_layout, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;
        this.subtitle=subtitle;
        this.imgid=imgid;

    }

    public View getView(int position,View view,ViewGroup parent) {

        if (position < 2 ){
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.custom_listview_layout, null,true);

            TextView titleText = (TextView) rowView.findViewById(R.id.item_Title);
            //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            TextView subtitleText = (TextView) rowView.findViewById(R.id.item_Subtitle);

            titleText.setText(maintitle[position]);
            //imageView.setImageResource(imgid[position]);
            subtitleText.setText(subtitle[position]);

            return rowView;
        }

        if (position == 2) {
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.custom_listview_image_layout, null,true);

            TextView titleText = (TextView) rowView.findViewById(R.id.item_Title);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.listview_Image);
            //TextView subtitleText = (TextView) rowView.findViewById(R.id.item_Subtitle);

            titleText.setText(maintitle[position]);
            //imageView.setImageResource(imgid[position]);
            //subtitleText.setText(subtitle[position]);

            return rowView;
        }

        if (position == 3) {
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.custom_listview_dialog_layout, null,true);

            TextView titleText = (TextView) rowView.findViewById(R.id.item_Title);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.listview_Image);
            //TextView subtitleText = (TextView) rowView.findViewById(R.id.item_Subtitle);

            titleText.setText(maintitle[position]);
            //imageView.setImageResource(imgid[position]);
            //subtitleText.setText(subtitle[position]);

            return rowView;
        }

        if (position == 4) {

            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.custom_listview_actions_layout, null,true);

            TextView titleText = (TextView) rowView.findViewById(R.id.item_Title);

            titleText.setText(maintitle[position]);

            return rowView;
        }




        return null;
    };
}