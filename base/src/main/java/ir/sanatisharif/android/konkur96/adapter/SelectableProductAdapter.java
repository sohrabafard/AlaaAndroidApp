package ir.sanatisharif.android.konkur96.adapter;


import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.dialog.ProductAttrDialogFragment;
import ir.sanatisharif.android.konkur96.model.SelectableProduct;
import ir.sanatisharif.android.konkur96.utils.GalleryWorker;


public class SelectableProductAdapter extends RecyclerView.Adapter<SelectableProductAdapter.MyViewHolder> {

    private Context context;
    private CheckListeners checkListeners;
    private ArrayList<SelectableProduct> productList;
    private GalleryWorker imgGallery;

    public SelectableProductAdapter(Context context, ArrayList<SelectableProduct> productList, CheckListeners checkListeners) {

        this.productList = productList;
        this.checkListeners = checkListeners;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_selectable_product, parent, false);
        imgGallery = new GalleryWorker(context);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (productList != null)
            return productList.size();
        else
            return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        SelectableProduct selectablemodel = productList.get(holder.getAdapterPosition());
        ProductModel model = productList.get(holder.getAdapterPosition()).getModel();

        holder.checkBox.setOnCheckedChangeListener(null);

        holder.cardAttrProduct.setOnClickListener(v -> {
            if (null != model.getAttributes() && null != model.getAttributes().getInformation()){

                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                ProductAttrDialogFragment alertDialog = new ProductAttrDialogFragment(model.getAttributes().getInformation());
                alertDialog.show(fm, "ProductAttr");

            }else {

                Toast.makeText(context, "ویژگی وجود ندارد", Toast.LENGTH_LONG).show();
            }
        });


        holder.cardSampleProduct.setOnClickListener(v -> {

            if (null != model.getSamplePhotos()){

                imgGallery.setImages(model.getSamplePhotos());
                imgGallery.openFullView(0);

            }else {

                Toast.makeText(context, "تصویر نمونه وجود ندارد", Toast.LENGTH_LONG).show();
            }


        });
        holder.textView.setTypeface(AppConfig.fontIRSensLight);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.checkBox.setButtonTintList(ContextCompat.getColorStateList(context, R.color.checkboxtint));
        }

        Glide.with(AppConfig.context)
                .load(model.getPhoto())
                .into(holder.imageView);

        holder.textView.setText(model.getName());

        if(selectablemodel.isChecked()) {

            holder.checkBox.setChecked(true);

        } else {

            holder.checkBox.setChecked(false);
        }



        holder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                selectablemodel.setChecked(true);
                checkListeners.onItemCheck(model, holder.getAdapterPosition());
            }
            else {
                selectablemodel.setChecked(false);
                checkListeners.onItemUncheck(model, holder.getAdapterPosition());
            }
        });


    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        ImageView imageView;
        TextView textView;
        CardView cardAttrProduct, cardSampleProduct;

        MyViewHolder(View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkBox);
            imageView = itemView.findViewById(R.id.img_pro);
            textView = itemView.findViewById(R.id.txt_name);
            cardAttrProduct = itemView.findViewById(R.id.card_attr_product);
            cardSampleProduct = itemView.findViewById(R.id.card_sample_product);

        }
    }

    public interface CheckListeners {

        void onItemCheck(ProductModel model, int position);
        void onItemUncheck(ProductModel model, int position);

    }



}

