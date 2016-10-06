package sf.righttoleftdialogfragment.notificationView;

import android.view.View;
import android.widget.TextView;

import sf.righttoleftdialogfragment.R;

public class NotificationViewHolder implements OverlayWindowView.OverlayViewHolder<NotificationManager.NotificationData> {

    NotificationManager notificationManager;

    public TextView tvMessage;
    public View tvDismiss;

    public NotificationViewHolder(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    @Override
    public int getLayoutId() {
        return R.layout.account_view_notification;
    }

    @Override
    public void initView(View view) {
        tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        tvDismiss = view.findViewById(R.id.tvDismiss);
        tvDismiss.setOnClickListener(notificationManager);
    }

    @Override
    public void updateData(NotificationManager.NotificationData data) {
        tvMessage.setText(data.message);
    }

}
