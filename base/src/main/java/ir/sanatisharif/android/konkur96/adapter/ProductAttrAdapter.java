package ir.sanatisharif.android.konkur96.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.Models.AttributeDataModel;
import ir.sanatisharif.android.konkur96.Models.AttributeModel;

public class ProductAttrAdapter extends RecyclerView.Adapter<ProductAttrAdapter.MyViewHolder> {
    
    private ArrayList<AttributeModel> attrList;
    
    public ProductAttrAdapter(ArrayList<AttributeModel> attrList) {
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
        
        AttributeModel model = attrList.get(position);
        holder.title.setText(model.getTitle());
        
        String data = "";
        
        for (AttributeDataModel attrData : model.getData()) {
            
            data += " " + attrData.getName() + " ";
            
        }
        
        holder.data.setText(data);
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
