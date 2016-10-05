package sf.righttoleftdialogfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;

public abstract class RightDialogFragment extends CustomWindowDialogFragment {

    private int[] screenSize;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenSize = getScreenSize();
    }

    private int[] getScreenSize() {
        return new int[]{getResources().getDisplayMetrics().widthPixels
                , getResources().getDisplayMetrics().heightPixels};
    }

    @Override
    protected int getWindowHeight() {
        return screenSize[1] - getMastheadSize();
    }

    private int getMastheadSize() {
        Log.i("vtt", "getMastheadSize " + dpToPixel(160));
        return dpToPixel(160);
    }

    public int dpToPixel(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                , dp
                , getResources().getDisplayMetrics());
    }

    @Override
    protected int getWindowWidth() {
        return screenSize[0] * 45 / 100;
    }

    @Override
    protected int getGravity() {
        return Gravity.END | Gravity.BOTTOM;
    }
}
