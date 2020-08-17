package xyz.mattjashworth.android.notificationmaker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] maintitle;
    private final String[] subtitle;
    private final Integer[] imgid;

    Boolean okCheck = false;
    Boolean cancelCheck = false;
    Boolean replyCheck = false;
    Boolean yesCheck = false;
    Boolean noCheck = false;

    CheckBoxFlags checkBoxFlags;

    public MyListAdapter(Activity context, String[] maintitle,String[] subtitle, Integer[] imgid, CheckBoxFlags check) {
        super(context, R.layout.custom_listview_layout, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;
        this.subtitle=subtitle;
        this.imgid=imgid;
        this.checkBoxFlags=check;

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

        if (position == 3 || position == 4) {
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

        if (position == 5) {

            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.custom_listview_actions_layout, null,true);

            TextView titleText = (TextView) rowView.findViewById(R.id.item_Title);

            titleText.setText(maintitle[position]);

            CheckBox chxOk = (CheckBox) rowView.findViewById(R.id.checkBox_OK);
            CheckBox chxCancel = (CheckBox) rowView.findViewById(R.id.checkBox_Cancel);
            CheckBox chxReply = (CheckBox) rowView.findViewById(R.id.checkBox_Reply);
            CheckBox chxYes = (CheckBox) rowView.findViewById(R.id.checkBox_Yes);
            CheckBox chxNo = (CheckBox) rowView.findViewById(R.id.checkBox_No);

            chxCancel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    checkBoxFlags.setCancelFlag(isChecked);
                }
            });

            chxReply.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkBoxFlags.setReplyFlag(isChecked);
                }
            });

            chxYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkBoxFlags.setYesFlag(isChecked);
                }
            });

            chxNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkBoxFlags.setNoFlag(isChecked);
                }
            });

            chxOk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkBoxFlags.setOkFlag(isChecked);
                }
            });

            return rowView;
        }

        if (position > 5) {

            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.custom_listview_layout, null,true);

            TextView titleText = (TextView) rowView.findViewById(R.id.item_Title);
            //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            TextView subtitleText = (TextView) rowView.findViewById(R.id.item_Subtitle);

            titleText.setText(R.string.about);
            //imageView.setImageResource(imgid[position]);
            subtitleText.setText(R.string.about_desc);

            return rowView;

        }




        return null;
    };
}