package xyz.mattjashworth.android.notificationmaker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.maltaisn.icondialog.IconDialog;
import com.maltaisn.icondialog.IconDialogSettings;
import com.maltaisn.icondialog.data.Icon;
import com.maltaisn.icondialog.pack.IconPack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
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

    MyListAdapter adapter;

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

        IconDialog dialog = (IconDialog) getSupportFragmentManager().findFragmentByTag(ICON_DIALOG_TAG);
        final IconDialog iconDialog = dialog != null ? dialog
                : IconDialog.newInstance(new IconDialogSettings.Builder().build());


        String[] maintitle = {
                "Notification Title:", "Notification Message:",
                "Notification Image:", "Notification Icon:",
                "Notification Actions:",
        };


        adapter = new MyListAdapter(this, maintitle, subtitle, null);
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

                }
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNotification();
                Snackbar.make(view, "Notification Successfully Sent", Snackbar.LENGTH_LONG).show();
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
                }

                if (position == 1) {
                    notificationMessage = editText.getText().toString();
                    subtitle[1] = notificationMessage;
                    adapter.notifyDataSetChanged();
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

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(createID(), builder.build());
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
                    notificationImage.setImageURI(selectedImage);
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

    }
}

