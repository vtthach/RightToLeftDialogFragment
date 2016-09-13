package sf0404.righttoleftdialogfragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public abstract class CustomWindowDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        window.setGravity(getGravity());
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(getLayoutId(), null);
    }

    protected abstract int getLayoutId();

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setLayout(getWindowWidth(), getWindowHeight());
        window.setWindowAnimations(getAnimationStyle());
    }

    protected abstract int getWindowHeight();

    protected abstract int getWindowWidth();

    protected abstract int getGravity();

    protected int getAnimationStyle() {
        return R.style.bottom_sheet_dialog_anim;
    }

}

