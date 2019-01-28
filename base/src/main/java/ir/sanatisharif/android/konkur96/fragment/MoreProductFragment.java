package ir.sanatisharif.android.konkur96.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.adapter.MoreProductAdapter;
import ir.sanatisharif.android.konkur96.model.MoreProductModel;

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


        mAdapter = new MoreProductAdapter(getContext() , dummydata());
        recyclerMoreProduct.setAdapter(mAdapter);


    }

    private void initView(View view) {

        recyclerMoreProduct = view.findViewById(R.id.recycler_more_product);


    }

    private ArrayList<MoreProductModel> dummydata() {

        ArrayList<MoreProductModel> moreProductModels = new ArrayList<>();

        MoreProductModel moreProductModels1 = new MoreProductModel();
        MoreProductModel moreProductModels2 = new MoreProductModel();
        MoreProductModel moreProductModels3 = new MoreProductModel();
        MoreProductModel moreProductModels4 = new MoreProductModel();
        MoreProductModel moreProductModels5 = new MoreProductModel();

        moreProductModels1.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171019113948.jpg?w=460&h=259");
        moreProductModels1.setTitle("زیست یازدهم");
        moreProductModels1.setAuthor(" عباس راسنی");
        moreProductModels1.setPrice("200000");

        moreProductModels2.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170920041635.jpg?w=253&h=142");
        moreProductModels2.setTitle("صفر تا صد فیزیک یازدهم");
        moreProductModels2.setAuthor(" فرشید داداشی");
        moreProductModels2.setPrice("400000");

        moreProductModels3.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/zist_yazdahom_1810011438.jpg?w=253&h=142");
        moreProductModels3.setTitle("زیست یازدهم");
        moreProductModels3.setAuthor("جلال موقاری");
        moreProductModels3.setPrice("400000");

        moreProductModels4.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/zaban11_1810070959.jpg?w=253&h=142");
        moreProductModels4.setTitle("صفر تا صد زبان");
        moreProductModels4.setAuthor("علی اکبر عزتی");
        moreProductModels4.setPrice("600000");

        moreProductModels5.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170920125924.jpg?w=253&h=142");
        moreProductModels5.setTitle("صفر تا صد زبان");
        moreProductModels5.setAuthor("علی اکبر عزتی");
        moreProductModels5.setPrice("100000");

        moreProductModels.add(moreProductModels1);
        moreProductModels.add(moreProductModels2);
        moreProductModels.add(moreProductModels3);
        moreProductModels.add(moreProductModels4);
        moreProductModels.add(moreProductModels5);

        return moreProductModels;

    }


    public static MoreProductFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        MoreProductFragment fragment = new MoreProductFragment();
        fragment.setArguments(args);
        return fragment;
    }


}
