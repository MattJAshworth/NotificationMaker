package xyz.mattjashworth.android.notificationmaker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.maltaisn.icondialog.IconDialog;
import com.maltaisn.icondialog.IconDialogSettings;
import com.maltaisn.icondialog.data.Icon;
import com.maltaisn.icondialog.pack.IconPack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements IconDialog.Callback {

    ListView list;

    String CHANNEL_ID = "xyz.mattjashworth.android.notificationmaker.notification";
    private static final String ICON_DIALOG_TAG = "icon-dialog";

    String notificationTitle;
    String notificationMessage;
    ImageView notificationImage;
    ImageView notificationIcon;
    ImageView notificationIconSmall;

    Boolean titleSet = false;
    Boolean messageSet = false;
    Boolean imageSet = false;
    Boolean iconSet = false;
    Boolean smallIconSet = false;
    Integer iconInt = 0;
    Boolean optionsSet;

    Bitmap largeIconBitmap;

    NotificationCompat.Builder builder;

    MyListAdapter adapter;

    ArrayList<Integer> iconIDs;

    CheckBoxFlags checkBoxFlags;


    String[] subtitle = {
            "Example", "Example",
            "Example", "Example",
            "Example",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        checkBoxFlags = new CheckBoxFlags();

        IconDialog dialog = (IconDialog) getSupportFragmentManager().findFragmentByTag(ICON_DIALOG_TAG);
        final IconDialog iconDialog = dialog != null ? dialog
                : IconDialog.newInstance(new IconDialogSettings.Builder().build());

        String[] maintitle = {
                "Notification Title:", "Notification Message:",
                "Notification Image:", "Large Notification Icon:", "Small Notification Icon: " ,
                "Notification Actions:", "About:",
        };

        final int[] icons = {R.drawable.ic_alarm_black_icon, R.drawable.ic_battery_black_icon, R.drawable.ic_camera_black_icon, R.drawable.ic_favorite_black_icon,
                R.drawable.ic_key_black_icon, R.drawable.ic_message_black_icon, R.drawable.ic_phone_black_icon, R.drawable.ic_send_black_icon};




        adapter = new MyListAdapter(this, maintitle, subtitle, null, checkBoxFlags);
        list = (ListView) findViewById(R.id.listview_items);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (position == 0) {
                    //code specific to first list item
                    createDialog(0, view);
                } else if (position == 1) {
                    //code specific to 2nd list item
                    createDialog(1, view);
                } else if (position == 2) {
                    notificationImage = (ImageView) view.findViewById(R.id.listview_Image);
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);//one can be replaced with any action code
                } else if (position == 3) {
                    notificationIcon = (ImageView) view.findViewById(R.id.listview_Image);
                    iconDialog.show(getSupportFragmentManager(), ICON_DIALOG_TAG);
                } else if (position == 4) {
                    notificationIconSmall = (ImageView) view.findViewById(R.id.listview_Image);
                    createIconPicker();
                } else if (position == 5) {

                }
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNotification();
                //Snackbar.make(view, "Notification Successfully Sent", Snackbar.LENGTH_LONG).show();
            }
        });
    }


    private  void createIconPicker() {


        View dialogView = getLayoutInflater().inflate(R.layout.icon_picker_bottomsheet, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(dialogView);
        dialog.show();


        ImageButton alarm = (ImageButton) dialogView.findViewById(R.id.img_Alarm);
        ImageButton battery = (ImageButton) dialogView.findViewById(R.id.img_Battery);
        ImageButton camera = (ImageButton) dialogView.findViewById(R.id.img_Camera);
        ImageButton fav = (ImageButton) dialogView.findViewById(R.id.img_Fav);
        ImageButton key = (ImageButton) dialogView.findViewById(R.id.img_Key);
        ImageButton message = (ImageButton) dialogView.findViewById(R.id.img_Message);
        ImageButton pause = (ImageButton) dialogView.findViewById(R.id.img_Pause);
        ImageButton phone = (ImageButton) dialogView.findViewById(R.id.img_Phone);
        ImageButton send = (ImageButton) dialogView.findViewById(R.id.img_Send);

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationIconSmall.setImageDrawable(getDrawable(R.drawable.ic_alarm_black_icon));
                smallIconSet = true;
                iconInt = 1;
                dialog.dismiss();
            }
        });
        battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationIconSmall.setImageDrawable(getDrawable(R.drawable.ic_battery_black_icon));
                smallIconSet = true;
                iconInt = 2;
                dialog.dismiss();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationIconSmall.setImageDrawable(getDrawable(R.drawable.ic_camera_black_icon));
                smallIconSet = true;
                iconInt = 3;
                dialog.dismiss();
            }
        });
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationIconSmall.setImageDrawable(getDrawable(R.drawable.ic_favorite_black_icon));
                smallIconSet = true;
                iconInt = 4;
                dialog.dismiss();
            }
        });
        key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationIconSmall.setImageDrawable(getDrawable(R.drawable.ic_key_black_icon));
                smallIconSet = true;
                iconInt = 5;
                dialog.dismiss();
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationIconSmall.setImageDrawable(getDrawable(R.drawable.ic_message_black_icon));
                smallIconSet = true;
                iconInt = 6;
                dialog.dismiss();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationIconSmall.setImageDrawable(getDrawable(R.drawable.ic_pause_black_icon));
                smallIconSet = true;
                iconInt = 7;
                dialog.dismiss();
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationIconSmall.setImageDrawable(getDrawable(R.drawable.ic_phone_black_icon));
                smallIconSet = true;
                iconInt = 8;
                dialog.dismiss();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationIconSmall.setImageDrawable(getDrawable(R.drawable.ic_send_black_icon));
                smallIconSet = true;
                iconInt = 9;
                dialog.dismiss();
            }
        });


    }

    private void createDialog(final int position, final View view) {

        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_layout, null);

        final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);

        dialogBuilder.setButton(DialogInterface.BUTTON_POSITIVE, "SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // On Save
                if (position == 0) {
                    notificationTitle = editText.getText().toString();
                    subtitle[0] = notificationTitle;
                    adapter.notifyDataSetChanged();
                    titleSet = true;
                }

                if (position == 1) {
                    notificationMessage = editText.getText().toString();
                    subtitle[1] = notificationMessage;
                    adapter.notifyDataSetChanged();
                    messageSet = true;
                }

            }

        });

        dialogBuilder.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancelled
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();

    }


    private void createNotification() {

        builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        if (titleSet)
        {
            builder.setContentTitle(notificationTitle);
        }

        if (messageSet) {

            builder.setContentText(notificationMessage);
        }

        if (smallIconSet) {

            switch (iconInt) {

                case 1:
                    builder.setSmallIcon(R.drawable.ic_alarm_black_icon);
                    break;
                case 2:
                    builder.setSmallIcon(R.drawable.ic_battery_black_icon);
                    break;
                case 3:
                    builder.setSmallIcon(R.drawable.ic_camera_black_icon);
                    break;
                case 4:
                    builder.setSmallIcon(R.drawable.ic_favorite_black_icon);
                    break;
                case 5:
                    builder.setSmallIcon(R.drawable.ic_key_black_icon);
                    break;
                case 6:
                    builder.setSmallIcon(R.drawable.ic_message_black_icon);
                    break;
                case 7:
                    builder.setSmallIcon(R.drawable.ic_pause_black_icon);
                    break;
                case 8:
                    builder.setSmallIcon(R.drawable.ic_phone_black_icon);
                    break;
                case 9:
                    builder.setSmallIcon(R.drawable.ic_send_black_icon);
                    break;
            }

        } else {
            builder.setSmallIcon(R.drawable.ic_favorite_black_icon);
        }

        if (iconSet) {
            builder.setLargeIcon(largeIconBitmap);
        }

        if (imageSet) {


            builder.setStyle(new NotificationCompat.BigPictureStyle()
                    .bigPicture(drawableToBitmap(notificationImage.getDrawable())));
        }



        if (checkBoxFlags.getOkFlag()) {
            builder.addAction(0, "OK", null);
        }

        if (checkBoxFlags.getCancelFlag()) {
            builder.addAction(0, "Cancel", null);
        }

        if (checkBoxFlags.getReplyFlag()) {
            builder.addAction(0, "Reply", null);
        }

        if (checkBoxFlags.getYesFlag()) {
            builder.addAction(0, "Yes", null);
        }

        if (checkBoxFlags.getNoFlag()) {
            builder.addAction(0, "No", null);
        }


        if (!titleSet & !messageSet & !iconSet & !imageSet) {

            Snackbar.make(getCurrentFocus(), getString(R.string.snackbar_empty_notification), BaseTransientBottomBar.LENGTH_LONG).setAction(getString(R.string.snackbar_yes), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Yes
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

// notificationId is a unique int for each notification that you must define
                    notificationManager.notify(createID(), builder.build());

                }
            }).show();

        } else {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

// notificationId is a unique int for each notification that you must define
            notificationManager.notify(createID(), builder.build());
        }


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public int createID() {
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.ENGLISH).format(now));
        return id;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    notificationIcon.setImageURI(selectedImage);
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    notificationImage.getLayoutParams().height = (int) getResources().getDimension(R.dimen.imageview_height);
                    notificationImage.setImageURI(selectedImage);
                    imageSet = true;
                }
                break;
        }
    }

    @Nullable
    @Override
    public IconPack getIconDialogIconPack() {
        return ((App) getApplication()).getIconPack();
    }

    @Override
    public void onIconDialogCancelled() {

    }

    @Override
    public void onIconDialogIconsSelected(@NotNull IconDialog iconDialog, @NotNull List<Icon> list) {

        notificationIcon.setImageDrawable(list.get(0).getDrawable());

        largeIconBitmap = drawableToBitmap(list.get(0).getDrawable());


        iconSet = true;

    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_twitter) {
            String url = "https://twitter.com/MattJAshworth";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }

        if (id == R.id.action_instagram) {
            String url = "https://instagram.com/MattJAshworth";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


}

