package ir.sanatisharif.android.konkur96.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.model.SelectableProduct;
import ir.sanatisharif.android.konkur96.utils.GalleryWorker;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;


public class SelectableProductAdapter extends RecyclerView.Adapter<SelectableProductAdapter.MyViewHolder> {

    private Context context;
    private CheckListeners checkListeners;
    private ArrayList<SelectableProduct> productList;
    private GalleryWorker imgGallery;
    private SelectableProductAdapter adapter;

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


//        holder.bindView(productList.get(position).getModel());
//        holder.bindChildes(productList.get(position));


        SelectableProduct selectablemodel = productList.get(holder.getAdapterPosition());
        ProductModel model = productList.get(holder.getAdapterPosition()).getModel();

        holder.checkBox.setOnCheckedChangeListener(null);


        holder.textView.setTypeface(AppConfig.fontIRSensLight);

        //selectablemodel.setChecked(checkChilds(selectablemodel.getChilds()));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.checkBox.setButtonTintList(ContextCompat.getColorStateList(context, R.color.checkboxtint));
        }


        holder.textView.setText(model.getName());
        setFinalPrice(holder.txtFinalPrice, model);



        if (null != model.getChildren()){



            holder.recyclerView.setVisibility(View.VISIBLE);

            ArrayList<SelectableProduct> items;
            if (null != selectablemodel.getChilds()){

                items = selectablemodel.getChilds();
            }else {

                items = ShopUtils.convertToSelectableProductModel(model.getChildren());
            }



            adapter = new SelectableProductAdapter(context, items, new SelectableProductAdapter.CheckListeners() {
                @Override
                public void onItemCheck(ProductModel model, int position, boolean isFirst) {

                    if (checkChilds(selectablemodel.getChilds())){

                        selectablemodel.setChecked(true);
                        checkListeners.onItemCheck(selectablemodel.getModel(), position, true);
                        notifyDataSetChanged();


                    }

                    checkListeners.onItemCheck(model, position, false);





                }

                @Override
                public void onItemUncheck(ProductModel model, int position) {

                    if (unCheckChilds(selectablemodel.getChilds())){

                        selectablemodel.setChecked(false);
                        checkListeners.onItemUncheck(selectablemodel.getModel(), position);
                        notifyDataSetChanged();


                    }


                    checkListeners.onItemUncheck(model, position);






                }
            });



            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            holder.recyclerView.setLayoutManager(mLayoutManager);
            holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
            holder.recyclerView.setAdapter(adapter);

            selectablemodel.setParent(selectablemodel);
            selectablemodel.setChilds(items);
            adapter.notifyDataSetChanged();

        }else {

            holder.recyclerView.setVisibility(View.GONE);
        }

        if(selectablemodel.isChecked()) {


            holder.checkBox.setChecked(true);


        } else {

            holder.checkBox.setChecked(false);
        }



        holder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                selectablemodel.setChecked(true);
                if (selectablemodel.getChilds() != null && selectablemodel.getChilds().size() > 0){

                    for(SelectableProduct childModel : selectablemodel.getChilds()){

                        childModel.setChecked(true);
                        checkListeners.onItemCheck(childModel.getModel(), holder.getAdapterPosition(), false);

                    }
                }


                adapter.notifyDataSetChanged();
                checkListeners.onItemCheck(model, holder.getAdapterPosition(), true);
            }
            else {

                selectablemodel.setChecked(false);
                for (int i = 0; i <= selectablemodel.getChilds().size(); i++){

                    SelectableProduct childModel = selectablemodel.getChilds().get(i);

                    childModel.setChecked(false);
                    checkListeners.onItemUncheck(childModel.getModel(), holder.getAdapterPosition());

                }

                adapter.notifyDataSetChanged();
                checkListeners.onItemUncheck(model, holder.getAdapterPosition());

                /*for(SelectableProduct childModel : selectablemodel.getChilds()){

                    childModel.setChecked(false);
                    checkListeners.onItemUncheck(childModel.getModel(), holder.getAdapterPosition());

                }*/



            }
        });


    }



    @SuppressLint("SetTextI18n")
    private void setFinalPrice(TextView textView , ProductModel model) {


        if (model.getPrice().getMfinal() > 0) {

            textView.setText(ShopUtils.formatPrice(model.getPrice().getMfinal()) + " تومان ");

        } else {

            textView.setText(ShopUtils.formatPrice(0) + " تومان ");
        }
    }

    private boolean checkChilds(ArrayList<SelectableProduct> list){

        if (null != list) {

            for (SelectableProduct model : list){
                if (!model.isChecked()) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }

    }


    private boolean unCheckChilds(ArrayList<SelectableProduct> list){

        if (null != list) {

            for (SelectableProduct model : list){
                if (model.isChecked()) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }

    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private TextView textView, txtFinalPrice;
        private RecyclerView recyclerView;
        private SelectableProductAdapter adapter;


        MyViewHolder(View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkBox);
            textView = itemView.findViewById(R.id.txt_name);
            txtFinalPrice = itemView.findViewById(R.id.txt_final_price);
            recyclerView = itemView.findViewById(R.id.recycler_child);


        }

        private void bindView(ProductModel model) {
            textView.setTypeface(AppConfig.fontIRSensLight);
            setFinalPrice(txtFinalPrice, model);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                checkBox.setButtonTintList(ContextCompat.getColorStateList(context, R.color.checkboxtint));
            }

            textView.setText(model.getName());

            ArrayList<SelectableProduct> items = ShopUtils.convertToSelectableProductModel(model.getChildren());

            adapter = new SelectableProductAdapter(context, items, new SelectableProductAdapter.CheckListeners() {
                @Override
                public void onItemCheck(ProductModel model, int position, boolean isFirst) {

                    checkListeners.onItemCheck(model, position, false);

                }

                @Override
                public void onItemUncheck(ProductModel model, int position) {

                    checkListeners.onItemUncheck(model, position);


                }
            });

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        }

        public void bindChildes(SelectableProduct selectableProduct) {


            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked) {

                        if (checkChilds(selectableProduct.getChilds())) {

                        }

                    } else {

                    }
                }
            });



        }
    }

    public interface CheckListeners {

        void onItemCheck(ProductModel model, int position, boolean isFirst);
        void onItemUncheck(ProductModel model, int position);

    }



}

