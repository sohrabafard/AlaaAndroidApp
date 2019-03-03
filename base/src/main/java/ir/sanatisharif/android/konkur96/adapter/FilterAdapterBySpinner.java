package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;


/**
 * Created by Mohamad on 1/6/2018.
 */

public class FilterAdapterBySpinner extends ArrayAdapter<String> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private String[] values;

    public FilterAdapterBySpinner(Context context, int textViewResourceId, String[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public boolean isEnabled(int position) {

        if (position == 0) {
            return false;
        } else
            return true;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public String getItem(int position) {
        return values[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own custom layout for each spinner item
        TextView label = new TextView(context);
        label.setTextColor(context.getResources().getColor(R.color.Gray));
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        label.setTypeface(AppConfig.fontIRSensLight);
        label.setPadding(1, 1, 4, 1);
        label.setText(values[position]);

        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText(values[position]);
        label.setTextColor(context.getResources().getColor(R.color.Red));
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        label.setPadding(1, 1, 4, 1);
        label.setTypeface(AppConfig.fontIRSensLight);

        if (position == 0) {
            // Set the hint text color gray
            label.setTextColor(Color.GRAY);
        } else {
            label.setTextColor(Color.BLACK);
        }

        return label;
    }
}
