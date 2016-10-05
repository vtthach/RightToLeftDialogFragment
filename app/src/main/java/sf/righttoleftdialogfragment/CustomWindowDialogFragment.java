package sf.righttoleftdialogfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public abstract class CustomWindowDialogFragment extends DialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.setGravity(getGravity());
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setWindowAnimations(getAnimationStyle());

//        // Make the dialog possible to be outside touch
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
//        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        return inflater.inflate(getLayoutId(), null);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Default dialog layout param will wrap_content
        // So just reset it when fragment start to show when onStart()
        Window window = getDialog().getWindow();
        window.setLayout(getWindowWidth(), getWindowHeight());
    }

    protected int getAnimationStyle() {
        return R.style.AccountDialogRightAnim;
    }

    protected abstract int getLayoutId();

    protected abstract int getWindowHeight();

    protected abstract int getWindowWidth();

    protected abstract int getGravity();


}

