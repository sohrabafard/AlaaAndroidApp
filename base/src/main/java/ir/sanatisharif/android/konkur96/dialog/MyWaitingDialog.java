package ir.sanatisharif.android.konkur96.dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import ir.sanatisharif.android.konkur96.R;

/**
 * Created by Mohamad on 3/4/2019.
 */

public class MyWaitingDialog {

    private AlertDialog dialog;
    private Context context;

    public MyWaitingDialog(Context context) {
        this.context = context;
    }

    public MyWaitingDialog setDialog() {
        dialog = new AlertDialog.Builder(context)
                .setView(R.layout.progress_dialog)
                .create();
        return this;
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {

        dialog.dismiss();
    }
}
