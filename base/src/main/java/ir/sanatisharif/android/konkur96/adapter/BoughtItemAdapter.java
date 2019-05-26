package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.listener.OnItemClickListener;
import ir.sanatisharif.android.konkur96.model.MainShopItem;
import ir.sanatisharif.android.konkur96.ui.view.autoscrollviewpager.AutoScrollViewPager;
import ir.sanatisharif.android.konkur96.ui.view.autoscrollviewpager.ViewSliderAdapter;

public class BoughtItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MainShopItem> dataList;
    private Context mContext;
    private SnapHelper snapHelper;
    private OnItemClickListener mClickListener;

    //-----------size------
    private int width;

    public BoughtItemAdapter(Context context, ArrayList<MainShopItem> dataList) {
        this.dataList = dataList;
        this.mContext = context;
        snapHelper = new GravitySnapHelper(Gravity.START);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == AppConstants.SHOP_SLIDER_ITEM) {
            return new BoughtItemAdapter.SliderHolder(LayoutInflater.from(mContext).inflate(R.layout.slider_item_set_adapter, parent, false));
        } else if (viewType == AppConstants.INCREDIBLEOFFER_ITEM_SET) {
            return new BoughtItemAdapter.IncredibleOffersItemHolder(LayoutInflater.from(mContext).inflate(R.layout.category_item_set_adapter, parent, false));
        } else if (viewType == AppConstants.CATEGORY_SHOP_ITEM_SET) {
            return new BoughtItemAdapter.CategoryShopItemHolder(LayoutInflater.from(mContext).inflate(R.layout.category_item_set_adapter, parent, false));
        } else if (viewType == AppConstants.SHOP_BANNER_ITEM) {
            return new BoughtItemAdapter.BannerItemHolder(LayoutInflater.from(mContext).inflate(R.layout.banner_item_set_adapter, parent, false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        // To Determine View Type
        int viewType = getItemViewType(position);

        if (viewType == AppConstants.INCREDIBLEOFFER_ITEM_SET) {

            final MainShopItem model = dataList.get(position);

            final String title = model.getTitle();
            final ArrayList items = model.getItems();

            final MainShopItemAdapter.IncredibleOffersItemHolder itemRowHolder = (MainShopItemAdapter.IncredibleOffersItemHolder) holder;

            itemRowHolder.txtTitle.setText(title);

            IncredibleOfferItemAdapter itemListDataAdapter = new IncredibleOfferItemAdapter(mContext, items);

            itemRowHolder.recyclerView.setHasFixedSize(false);
            LinearLayoutManager lin = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            lin.setReverseLayout(false);
            itemRowHolder.recyclerView.setLayoutManager(lin);
            itemRowHolder.recyclerView.setNestedScrollingEnabled(false);
            itemRowHolder.recyclerView.setHasFixedSize(true);
            itemRowHolder.recyclerView.setAdapter(itemListDataAdapter);
            snapHelper.attachToRecyclerView(itemRowHolder.recyclerView);
            itemListDataAdapter.notifyDataSetChanged();


        } else if (viewType == AppConstants.SHOP_BANNER_ITEM) {

            final BoughtItemAdapter.BannerItemHolder itemRowHolder = (BoughtItemAdapter.BannerItemHolder) holder;

            ArrayList items = dataList.get(position).getItems();
            String title = dataList.get(position).getTitle();
            itemRowHolder.txtTitle.setText(title);

            BannerItemAdapter itemListDataAdapter = new BannerItemAdapter(mContext, items);

            itemRowHolder.recyclerView.setHasFixedSize(true);
            LinearLayoutManager lin = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            lin.setReverseLayout(false);
            itemRowHolder.recyclerView.setLayoutManager(lin);
            itemRowHolder.recyclerView.setAdapter(itemListDataAdapter);
            itemRowHolder.recyclerView.setNestedScrollingEnabled(false);
            snapHelper.attachToRecyclerView(itemRowHolder.recyclerView);

        } else if (viewType == AppConstants.SHOP_SLIDER_ITEM) {

            final MainShopItemAdapter.SliderHolder itemRowHolder = (MainShopItemAdapter.SliderHolder) holder;

            ArrayList items = dataList.get(position).getItems();
            itemRowHolder.view_pager.setAdapter(new ViewSliderAdapter(AppConfig.context, items, null));
            itemRowHolder.view_pager.startAutoScroll();

            itemRowHolder.indicator = itemRowHolder.itemView.findViewById(R.id.indicator);
            itemRowHolder.indicator.setViewPager(itemRowHolder.view_pager);

        } else if (viewType == AppConstants.CATEGORY_SHOP_ITEM_SET) {

            final MainShopItem model = dataList.get(position);

            final String title = model.getTitle();
            final ArrayList items = model.getItems();

            final MainShopItemAdapter.CategoryShopItemHolder itemRowHolder = (MainShopItemAdapter.CategoryShopItemHolder) holder;

            itemRowHolder.txtMore.setOnClickListener(v -> {

                if (mClickListener != null) {
                    mClickListener.onItemClick(position, items, v, itemRowHolder);
                }

                //todo: complete this
                // addFrg(ExtraItemFrg.newInstance(AppConstants.CATEGORY_ITEM_SET), "ExtraItemFrg");

            });

            itemRowHolder.txtTitle.setText(title);

            CategoryShopItemAdapter itemListDataAdapter = new CategoryShopItemAdapter(mContext, items);

            itemRowHolder.recyclerView.setHasFixedSize(false);
            LinearLayoutManager lin = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            lin.setReverseLayout(false);
            itemRowHolder.recyclerView.setLayoutManager(lin);
            itemRowHolder.recyclerView.setNestedScrollingEnabled(false);
            itemRowHolder.recyclerView.setHasFixedSize(true);
            itemRowHolder.recyclerView.setAdapter(itemListDataAdapter);
            snapHelper.attachToRecyclerView(itemRowHolder.recyclerView);
            itemListDataAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }


    @Override
    public int getItemViewType(int position) {

        if (dataList.get(position).getType() == AppConstants.INCREDIBLEOFFER_ITEM_SET)
            return AppConstants.INCREDIBLEOFFER_ITEM_SET;
        else if (dataList.get(position).getType() == AppConstants.CATEGORY_SHOP_ITEM_SET)
            return AppConstants.CATEGORY_SHOP_ITEM_SET;
        else if (dataList.get(position).getType() == AppConstants.SHOP_BANNER_ITEM)
            return AppConstants.SHOP_BANNER_ITEM;
        else if (dataList.get(position).getType() == AppConstants.SHOP_SLIDER_ITEM)
            return AppConstants.SHOP_SLIDER_ITEM;

        return -1;

    }

    public void setSize(int w, int h) {

        AppConfig.itemHeight = (int) (w * 0.56f);
        width = w;
    }


    public class ItemHolder extends RecyclerView.ViewHolder {

        protected LinearLayout root;
        protected TextView txtTitle;
        protected TextView txtMore;
        protected RecyclerView recyclerView;

        private ItemHolder(View view) {
            super(view);

            root = view.findViewById(R.id.root);
            txtTitle = view.findViewById(R.id.txt_title);
            txtMore = view.findViewById(R.id.txtMore);
            recyclerView = view.findViewById(R.id.recyclerView);

        }
    }

    public class IncredibleOffersItemHolder extends BoughtItemAdapter.ItemHolder {

        private IncredibleOffersItemHolder(View view) {
            super(view);

            txtMore.setVisibility(View.GONE);
            txtTitle.setTypeface(AppConfig.fontIRSensNumber);
            txtMore.setTypeface(AppConfig.fontIRSensNumber);
            recyclerView.getLayoutParams().height = AppConfig.itemHeight;
        }
    }

    public class CategoryShopItemHolder extends BoughtItemAdapter.ItemHolder {

        private CategoryShopItemHolder(View view) {
            super(view);

            txtTitle.setTypeface(AppConfig.fontIRSensNumber);
            txtMore.setTypeface(AppConfig.fontIRSensNumber);
            recyclerView.getLayoutParams().height = AppConfig.itemHeight;
        }
    }


    public class BannerItemHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerView;
        private TextView txtTitle;

        private BannerItemHolder(View view) {
            super(view);

            recyclerView = view.findViewById(R.id.recyclerView);
            txtTitle = view.findViewById(R.id.txt_title);

            txtTitle.setTypeface(AppConfig.fontIRSensNumber);
            recyclerView.getLayoutParams().height = AppConfig.itemHeight;
        }
    }

    public class SliderHolder extends RecyclerView.ViewHolder {

        public AutoScrollViewPager view_pager;
        public CirclePageIndicator indicator;

        private SliderHolder(View view) {
            super(view);

            view_pager = view.findViewById(R.id.view_pager);

            view_pager.startAutoScroll(5000);
            view_pager.setBorderAnimation(true);

            int h = (int) (AppConfig.width * 0.39f);
            view_pager.getLayoutParams().height = h;

        }
    }


}
