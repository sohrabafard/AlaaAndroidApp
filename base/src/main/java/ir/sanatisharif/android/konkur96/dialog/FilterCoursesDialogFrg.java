package ir.sanatisharif.android.konkur96.dialog;

import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.model.DownloadUrl;

/**
 * Created by Mohamad on 7/7/2017.
 */

public class FilterCoursesDialogFrg extends BaseDialogFragment<FilterCoursesDialogFrg> implements AdapterView.OnItemSelectedListener {

    //------init UI
    private static final String TAG = "LOG";
    private String[] filters = new String[2];
    private View dialog;
    private Button btnOk;
    private FilterSelectedCallback callback;
    private Spinner spinnerSection;
    private Spinner spinnerField;

    public FilterCoursesDialogFrg setFilterSelectedCallback(FilterSelectedCallback callback) {
        this.callback = callback;
        return this;
    }

    public static FilterCoursesDialogFrg newInstance(ArrayList<DownloadUrl> Urls) {
        FilterCoursesDialogFrg frag = new FilterCoursesDialogFrg();
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog_Alert);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog = inflater.inflate(R.layout.dialog_selected_filter, container, false);
        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnOk = dialog.findViewById(R.id.btnOK);
        spinnerField = (Spinner) dialog.findViewById(R.id.spinnerField);
        spinnerSection = (Spinner) dialog.findViewById(R.id.spinnerSection);


        spinnerField.setOnItemSelectedListener(this);
        spinnerSection.setOnItemSelectedListener(this);

        spinnerSection.setAdapter(
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.FilterSection)));
        spinnerField.setAdapter(
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.FilterField)));


        ripple(btnOk, 8);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (callback != null) {

                    ArrayList<String> f = new ArrayList<>();
                    if (!filters[0].equals("null"))
                        f.add(filters[0]);
                    if (!filters[1].equals("null"))
                        f.add(filters[1]);
                    dismiss();
                    callback.onList(f);

                }
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        int i = parent.getId();
        if (i == R.id.spinnerField) {
            if (pos == 0)
                filters[0] = "null";
            else
                filters[0] = (String) parent.getItemAtPosition(pos);

        } else if (i == R.id.spinnerSection) {
            if (pos == 0)
                filters[1] = "null";
            else
                filters[1] = (String) parent.getItemAtPosition(pos);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface FilterSelectedCallback {
        void onList(List<String> filters);
    }

}
