package ir.sanatisharif.android.konkur96.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.api.Models.BonModel;

public class ProductBonsAdapter extends RecyclerView.Adapter<ProductBonsAdapter.MyViewHolder> {

    private ArrayList<BonModel> attrList;

    public ProductBonsAdapter(ArrayList<BonModel> attrList) {
        this.attrList = attrList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_productattr, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        BonModel model = attrList.get(position);
        holder.title.setText(model.getDisplayName());
        holder.data.setText(model.getDescription());
    }

    @Override
    public int getItemCount() {
        return attrList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, data;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.txt_titel);
            data = view.findViewById(R.id.txt_data);
        }
    }
}
