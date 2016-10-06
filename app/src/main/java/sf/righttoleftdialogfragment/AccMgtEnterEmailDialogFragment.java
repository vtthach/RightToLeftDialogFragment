package sf.righttoleftdialogfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by thach.vo on 14/09/2016.
 */
public class AccMgtEnterEmailDialogFragment extends RightDialogFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.account_enter_email_dialog_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btnChange).setOnClickListener(v -> {
            MainActivity activity = (MainActivity) getActivity();
            activity.showNotificationNewSingleInstance("Init 1" + System.currentTimeMillis());
        });
    }
}
