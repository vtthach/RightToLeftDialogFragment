package sf0404.righttoleftdialogfragment;

import android.view.Gravity;
import android.view.ViewGroup;

public class RightToLeftDialogFragment extends CustomWindowDialogFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dialog;
    }

    @Override
    protected int getWindowHeight() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected int getWindowWidth() {

        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected int getGravity() {
        return Gravity.END;
    }
}
