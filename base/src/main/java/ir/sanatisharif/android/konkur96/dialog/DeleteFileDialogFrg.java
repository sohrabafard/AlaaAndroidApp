package ir.sanatisharif.android.konkur96.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.helper.FileManager;
import ir.sanatisharif.android.konkur96.model.DownloadUrl;
import ir.sanatisharif.android.konkur96.model.Video;
import ir.sanatisharif.android.konkur96.utils.MyPreferenceManager;

/**
 * Created by Mohamad on 7/7/2017.
 */

public class DeleteFileDialogFrg extends BaseDialogFragment<DeleteFileDialogFrg> {

    //------init UI
    private View dialog;
    private static final String TAG = "LOG";

    private TextView txtOk;
    private TextView txtCancel;
    private List<Video> videos;

    public static DeleteFileDialogFrg newInstance(ArrayList<DownloadUrl> Urls) {
        DeleteFileDialogFrg frag = new DeleteFileDialogFrg();
        return frag;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog_Alert);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog = inflater.inflate(R.layout.dialog_delete_file, container, false);
        setCancelable(false);
        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtOk = dialog.findViewById(R.id.txtOk);
        txtCancel = dialog.findViewById(R.id.txtCancel);

        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videos != null)
                    notifyAndDeleteFile();
            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    private void notifyAndDeleteFile() {

        for (int i = 0; i < videos.size(); i++) {

            String url = videos.get(i).getUrl();

            String mediaPath = FileManager.getPathFromAllaUrl(url);
            String fileName = FileManager.getFileNameFromUrl(url);
            Log.i(TAG, "notifyAndDeleteFile: " + fileName + " " + mediaPath);
            File file = new File(FileManager.getRootPath() + mediaPath + "/" + fileName);
            if (file.exists()) {
                file.delete();
            }
        }
        dismiss();
    }
}
