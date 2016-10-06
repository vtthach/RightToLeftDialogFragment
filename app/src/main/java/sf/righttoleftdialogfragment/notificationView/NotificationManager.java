package sf.righttoleftdialogfragment.notificationView;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.view.View;

import sf.righttoleftdialogfragment.R;
import sf.righttoleftdialogfragment.notificationView.OverlayWindowView.OverlayViewHolder;

public class NotificationManager implements View.OnClickListener {

    static final Handler handler = new Handler();

    OverlayWindowView.Builder<NotificationData> builder;
    OverlayWindowView<NotificationData> notificationView;
    NotificationData notificationData;
    private NotificationMode mode;

    public NotificationManager(Context context) {
        notificationData = new NotificationData();
        builder = new OverlayWindowView.Builder<NotificationData>(context)
                .withData(notificationData)
                .withViewHolder(getNotificationViewHolder());
    }

    private OverlayViewHolder<NotificationData> getNotificationViewHolder() {
        return new NotificationViewHolder(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDismiss:
                if (notificationView != null) {
                    notificationView.dismiss();
                }
                break;
            case R.id.tvMessage:
            case R.id.imgIcon:
            default:
                break;
        }
    }

    public void show(String msg) {
        switch (mode) {
            case UPDATE_DATA:
                showNotifyUpdateData(msg);
                break;
            case NEW_INSTANCE:
                showNotifyNewInstance(msg);
                break;
            case SINGLE_INSTANCE:
            default:
                showNotifySingleInstance(msg);
                break;
        }
    }

    public void showNotifyUpdateData(String msg) {
        notificationData.message = msg;
        if (notificationView == null) {
            notificationView = builder.withData(notificationData).show();
        } else {
            notificationView.onUpdateData(notificationData);
        }
    }

    public void showNotifySingleInstance(String msg) {
        notificationData.message = msg;
        if (notificationView != null) {
            notificationView.dismiss();
        }
        notificationView = builder.withData(notificationData).show();
    }

    public void showNotifyNewInstance(String msg) {
        notificationData.message = msg;
        notificationView = builder.withData(notificationData).show();
    }

    public static class NotificationData {
        public String message;
        @IdRes
        public int resIcon;
    }
}
