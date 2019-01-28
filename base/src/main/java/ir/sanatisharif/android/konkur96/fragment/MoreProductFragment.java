package ir.sanatisharif.android.konkur96.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.adapter.MoreProductAdapter;

public class MoreProductFragment extends BaseFragment{

    private RecyclerView recyclerMoreProduct;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more_product, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initView(view);

        recyclerMoreProduct.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerMoreProduct.setLayoutManager(mLayoutManager);

        String[] myDataset = new String[30];
        mAdapter = new MoreProductAdapter(myDataset);
        recyclerMoreProduct.setAdapter(mAdapter);


    }

    private void initView(View view) {

        recyclerMoreProduct = view.findViewById(R.id.recycler_more_product);

    }



    public static MoreProductFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        MoreProductFragment fragment = new MoreProductFragment();
        fragment.setArguments(args);
        return fragment;
    }


}
