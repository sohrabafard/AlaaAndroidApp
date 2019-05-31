package ir.sanatisharif.android.konkur96.ui.component.paginate.item;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.ui.component.paginate.callback.OnRepeatListener;


public interface ErrorItem {


    ErrorItem DEFAULT = new ErrorItem() {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View
                    view =
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_error, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, final OnRepeatListener onRepeatListener) {

            Button btnRepeat = holder.itemView.findViewById(R.id.btnRepeat);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                btnRepeat.setBackgroundResource(R.drawable.no_pagination_button_ripple);
            } else {
                btnRepeat.setBackgroundResource(R.drawable.no_pagination_button_selector);
            }

            btnRepeat.setOnClickListener(v -> {
                if (onRepeatListener != null) {
                    onRepeatListener.onClickRepeat();
                }
            });
        }


    };

    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    void onBindViewHolder(RecyclerView.ViewHolder holder, int position, OnRepeatListener onRepeatListener);


}
