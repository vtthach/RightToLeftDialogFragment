package sf.righttoleftdialogfragment.notificationView;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.WindowManager;
import android.widget.FrameLayout;

import sf.righttoleftdialogfragment.R;


/**
 * Helper class to show a sequence of showcase views.
 */
public class OverlayWindowView<T> extends FrameLayout {
    private final Handler mHandler = new Handler();

    // Default values
    private int styleAnimationResId = R.style.NotificationAnim;
    private int windowGravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
    private int windowFlags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
    private int windowType = WindowManager.LayoutParams.TYPE_TOAST;
    private int windowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
    private int windowWidth = WindowManager.LayoutParams.WRAP_CONTENT;
    private long autoDismissDuration = 5000;
    private boolean enableAutoDismiss = true;

    private WindowManager windowManager;
    private boolean isAddedToWindow;
    private OverlayViewHolder<T> viewHolder;
    private T data;

    public OverlayWindowView(Context context) {
        super(context);
    }

    private boolean isShowing() {
        return isAddedToWindow;
    }

    public void onUpdateData(T data) {
        this.data = data;
        viewHolder.updateData(data);
        if (isShowing()) {
            resetAutoDismiss();
        } else {
            show();
        }
    }

    public void resetAutoDismiss() {
        if (enableAutoDismiss) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler.postDelayed(this::removeFromWindow, autoDismissDuration);
        }
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
        // Clean viewHolderData here
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
        windowParams.flags = windowFlags;
        windowParams.type = windowType;
        windowParams.windowAnimations = styleAnimationResId;
        return windowParams;
    }

    public void dismiss() {
        mHandler.removeCallbacksAndMessages(null);
        removeFromWindow();
    }

    /**
     * BUILDER CLASS
     * Gives us a builder utility class with a fluent API for eaily configuring Notification views
     */
    public static class Builder<T> {
        Context context;

        // Default values
        private int styleAnimationResId = R.style.NotificationAnim;
        private int windowGravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        private int windowFlags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        private int windowType = WindowManager.LayoutParams.TYPE_TOAST;
        private int windowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        private int windowWidth = WindowManager.LayoutParams.WRAP_CONTENT;
        private long autoDismissDuration = 5000;
        private boolean enableAutoDismiss = true;

        private OverlayViewHolder<T> viewHolder;
        private T viewHolderData;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder<T> windowGravity(int gravity) {
            this.windowGravity = gravity;
            return this;
        }

        public Builder<T> animationStyle(@StyleRes int styleAnimationResId) {
            this.styleAnimationResId = styleAnimationResId;
            return this;
        }

        public Builder<T> windowFlags(int windowFlags) {
            this.windowFlags = windowFlags;
            return this;
        }

        public Builder<T> windowWidth(int windowWidth) {
            this.windowWidth = windowWidth;
            return this;
        }

        public Builder<T> windowHeight(int windowHeight) {
            this.windowHeight = windowHeight;
            return this;
        }

        public Builder<T> windowType(int windowType) {
            this.windowType = windowType;
            return this;
        }

        public Builder<T> autoDismissDuration(long autoDismissDuration) {
            this.enableAutoDismiss = true;
            this.autoDismissDuration = autoDismissDuration;
            return this;
        }

        public Builder<T> enableAutoDismiss(boolean enableAutoDismiss) {
            this.enableAutoDismiss = enableAutoDismiss;
            return this;
        }

        public Builder<T> withData(T data) {
            this.viewHolderData = data;
            return this;
        }

        public OverlayWindowView<T> build() {
            checkException();
            OverlayWindowView<T> notificationView = new OverlayWindowView<>(context);
            notificationView.windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            notificationView.windowGravity = windowGravity;
            notificationView.styleAnimationResId = styleAnimationResId;
            notificationView.windowFlags = windowFlags;
            notificationView.windowWidth = windowWidth;
            notificationView.windowHeight = windowHeight;
            notificationView.windowType = windowType;
            notificationView.enableAutoDismiss = enableAutoDismiss;
            notificationView.autoDismissDuration = autoDismissDuration;
            inflateViewHolder(notificationView, viewHolder, viewHolderData);
            return notificationView;
        }

        private void inflateViewHolder(OverlayWindowView notificationView, OverlayViewHolder<T> viewHolder, T data) {
            LayoutInflater.from(notificationView.getContext()).inflate(viewHolder.getLayoutId(), notificationView);
            viewHolder.initView(notificationView);
            viewHolder.updateData(data);
            notificationView.viewHolder = viewHolder;
        }

        private void checkException() {
            if (context == null) {
                throw new OverlayBuilderException("Context must not null");
            }
            if (viewHolder == null) {
                throw new OverlayBuilderException("View holder must not null");
            }
        }

        public OverlayWindowView<T> show() {
            OverlayWindowView<T> overlayWindowView = build();
            overlayWindowView.show();
            return overlayWindowView;
        }

        public Builder<T> withViewHolder(@NonNull OverlayViewHolder<T> viewHolder) {
            this.viewHolder = viewHolder;
            return this;
        }

        private static class OverlayBuilderException extends RuntimeException {
            public OverlayBuilderException(String message) {
                super(message);
            }
        }
    }


    public interface OverlayViewHolder<T> {
        @LayoutRes
        int getLayoutId();

        void initView(View view);

        void updateData(T data);
    }
}
