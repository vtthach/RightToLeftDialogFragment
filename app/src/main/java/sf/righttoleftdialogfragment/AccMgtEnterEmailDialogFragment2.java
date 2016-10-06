package sf.righttoleftdialogfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

public class AccMgtEnterEmailDialogFragment2 extends RightDialogFragment {

    private int i;

    @Override
    protected int getLayoutId() {
        return R.layout.account_enter_email_dialog_fragment;
    }

    @Override
    protected int getGravity() {
        return Gravity.LEFT;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        view.findViewById(R.id.btnChange).setOnClickListener(v -> {
            MainActivity activity = (MainActivity) getActivity();
            activity.showNotificationUpdateData("Init 2" + i++);
        });
    }
}
