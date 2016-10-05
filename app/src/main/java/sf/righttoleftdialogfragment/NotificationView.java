package sf.righttoleftdialogfragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * Helper class to show a sequence of showcase views.
 */
public class NotificationView extends FrameLayout implements View.OnClickListener {

    private static final long DURATION_DISMISS = 5000;
    private final Handler mHandler = new Handler();
    private WindowManager windowManager;
    private TextView tvMessage;

    public NotificationView(Context context) {
        super(context);
        init(context);
    }

    public NotificationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NotificationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.account_view_notification, this);
        findViewById(R.id.tvDismiss).setOnClickListener(this);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
    }

    public void updateData(String msg, int type) {
        if (isShowing()) {
            tvMessage.setText(msg);
        } else {
            show();
        }
    }

    private boolean isShowing() {
        return getWindowToken() != null;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i("vtt", "onDetachFromWindow");
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDismiss:
                removeFromWindow();
                break;
            case R.id.tvMessage:
            case R.id.imgIcon:
            default:
                break;
        }
    }


    /**
     * BUILDER CLASS
     * Gives us a builder utility class with a fluent API for eaily configuring showcase views
     */
    public static class Builder {
        final NotificationView notificationView;

        public Builder(Activity activity) {
            notificationView = new NotificationView(activity);
            notificationView.windowManager = activity.getWindowManager();
        }

        public Builder message(String msg) {
            notificationView.tvMessage.setText(msg);
            return this;
        }

        public NotificationView build() {
            return notificationView;
        }

        public NotificationView show() {
            build().show();
            return notificationView;
        }
    }

    public void removeFromWindow() {
        if (windowManager != null && getWindowToken() != null) {
            windowManager.removeView(this);
        }
        onCleanData();
    }

    private void onCleanData() {
        // Clean data here ext: bitmap , image view
    }

    public void show() {
        if (getWindowToken() == null) {
            windowManager.addView(this, getWindowLayoutParams());
        }
        postDelayDismissFromWindow();
    }

    private ViewGroup.LayoutParams getWindowLayoutParams() {
        WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
        windowParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        windowParams.format = PixelFormat.TRANSLUCENT;
        windowParams.windowAnimations = R.style.AccountDialogRightAnim;
        return windowParams;
    }

    public void postDelayDismissFromWindow() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(this::removeFromWindow, DURATION_DISMISS);
    }
}
