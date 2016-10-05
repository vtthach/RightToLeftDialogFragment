package sf.righttoleftdialogfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;

public class AccMgtEnterEmailDialogFragment2 extends RightDialogFragment {

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
        view.findViewById(R.id.btnChange).setOnClickListener(v -> {
            MainActivity activity = (MainActivity) getActivity();
            activity.showNotification("Init 2" + System.currentTimeMillis());
        });
    }
}
