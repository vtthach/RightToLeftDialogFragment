package sf.righttoleftdialogfragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * Helper class to show a sequence of showcase views.
 */
public class NotificationView extends FrameLayout implements View.OnClickListener {
    private static final long DURATION_DISMISS = 5000;
    private final Handler mHandler = new Handler();

    // Default values
    private int styleResId = R.style.NotificationAnim;
    private int windowGravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
    private int windowFlags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
    private int windowType = WindowManager.LayoutParams.TYPE_TOAST;
    private int windowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
    private int windowWidth = WindowManager.LayoutParams.WRAP_CONTENT;

    private WindowManager windowManager;
    private TextView tvMessage;
    private boolean isAddedToWindow;

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

    private boolean isShowing() {
        return isAddedToWindow;
    }

    public void updateData(String msg, int type) {
        tvMessage.setText(msg);
        if (isShowing()) {
            resetAutoDismiss();
        } else {
            show();
        }
    }

    public void resetAutoDismiss() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(this::removeFromWindow, DURATION_DISMISS);
    }

    @Override
    protected void onDetachedFromWindow() {
        isAddedToWindow = false;
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onAttachedToWindow() {
        isAddedToWindow = true;
        super.onAttachedToWindow();
        resetAutoDismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDismiss:
                dismiss();
                break;
            case R.id.tvMessage:
            case R.id.imgIcon:
            default:
                break;
        }
    }

    public void dismiss() {
        mHandler.removeCallbacksAndMessages(null);
        removeFromWindow();
    }


    /**
     * BUILDER CLASS
     * Gives us a builder utility class with a fluent API for eaily configuring Notification views
     */
    public static class Builder {
        final NotificationView notificationView;

        public Builder(Activity activity) {
            notificationView = new NotificationView(activity);
            notificationView.windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        }

        public Builder message(String msg) {
            notificationView.tvMessage.setText(msg);
            return this;
        }

        public Builder windowGravity(int gravity) {
            notificationView.windowGravity = gravity;
            return this;
        }

        public Builder animationStyle(@StyleRes int styleResId) {
            notificationView.styleResId = styleResId;
            return this;
        }

        public Builder windowFlags(int windowFlags) {
            notificationView.windowFlags = windowFlags;
            return this;
        }

        public Builder windowWidth(int windowWidth) {
            notificationView.windowWidth = windowWidth;
            return this;
        }

        public Builder windowHeight(int windowHeight) {
            notificationView.windowHeight = windowHeight;
            return this;
        }

        public Builder windowType(int windowType) {
            notificationView.windowType = windowType;
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
        if (getWindowToken() != null) {
            getWindowManger().removeView(this);
        }
        onCleanData();
    }

    private ViewManager getWindowManger() {
        if (windowManager == null) {
            windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        }
        return windowManager;
    }

    private void onCleanData() {
        // Clean data here
        windowManager = null;
    }

    public void show() {
        if (!isShowing()) {
            getWindowManger().addView(this, getWindowLayoutParams());
        }
    }

    private ViewGroup.LayoutParams getWindowLayoutParams() {
        WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
        windowParams.gravity = windowGravity;
        windowParams.height = windowHeight;
        windowParams.width = windowWidth;
        windowParams.format = PixelFormat.TRANSLUCENT;
        windowParams.windowAnimations = styleResId;
        windowParams.flags = windowFlags;
        windowParams.type = windowType;
        return windowParams;
    }
}
