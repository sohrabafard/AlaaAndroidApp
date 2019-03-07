package ir.sanatisharif.android.konkur96.dialog;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.adapter.ProductAttrAdapter;
import ir.sanatisharif.android.konkur96.api.Models.AttributeModel;

@SuppressLint("ValidFragment")
public class ProductAttrDialogFragment extends DialogFragment {

    private RecyclerView mRecyclerView;
    private ProductAttrAdapter adapter;
    private ArrayList<AttributeModel> attrList;

    @SuppressLint("ValidFragment")
    public ProductAttrDialogFragment(ArrayList<AttributeModel> attrList) {
        this.attrList = attrList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_view_attr, container, false);

        mRecyclerView = v.findViewById(R.id.recycler_view);

        //setadapter
        adapter = new ProductAttrAdapter(attrList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        return v;
    }
}
