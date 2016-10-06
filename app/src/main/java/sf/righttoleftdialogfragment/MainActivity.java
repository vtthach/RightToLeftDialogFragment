package sf.righttoleftdialogfragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import sf.righttoleftdialogfragment.notificationView.NotificationManager;
import sf.righttoleftdialogfragment.notificationView.OverlayWindowView;

public class MainActivity extends AppCompatActivity {

    private Toast mToast;
    private NotificationManager notifyNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notifyNotificationManager = new NotificationManager(this);

        mToast = Toast.makeText(this, "", Toast.LENGTH_LONG);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccMgtEnterEmailDialogFragment fragment = new AccMgtEnterEmailDialogFragment();
                fragment.show(getSupportFragmentManager(), null);
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccMgtEnterEmailDialogFragment2 fragment2 = new AccMgtEnterEmailDialogFragment2();
                fragment2.show(getSupportFragmentManager(), null);
            }
        });

        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotificationNewInstance(System.currentTimeMillis() + "");
            }
        });
    }

    private void showToast(long l) {
        mToast.setText(" " + l);
        mToast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    OverlayWindowView notificationView;

    public void showNotificationUpdateData(String msg) {
        notifyNotificationManager.showNotifyUpdateData(msg);
    }

    public void showNotificationNewSingleInstance(String msg) {
        notifyNotificationManager.showNotifySingleInstance(msg);
    }

    public void showNotificationNewInstance(String msg) {
        notifyNotificationManager.showNotifyNewInstance(msg);
    }
}
