package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.content.Intent;
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
;
import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.fragment.ExtraItemFrg;
import ir.sanatisharif.android.konkur96.fragment.VideoDownloadedFrg;
import ir.sanatisharif.android.konkur96.listener.OnItemClickListener;
import ir.sanatisharif.android.konkur96.model.MainItem;
import ir.sanatisharif.android.konkur96.ui.view.autoscrollviewpager.AutoScrollViewPager;
import ir.sanatisharif.android.konkur96.ui.view.autoscrollviewpager.ViewSliderAdapter;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;


public class MainItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MainItem> dataList;
    private Context mContext;
    private SnapHelper snapHelper;
    private OnItemClickListener mClickListener;

    //-----------size------
    private int width;

    public MainItemAdapter(Context context, ArrayList<MainItem> dataList) {
        this.dataList = dataList;
        this.mContext = context;
        snapHelper = new GravitySnapHelper(Gravity.START);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == AppConstants.CATEGORY_ITEM_SET)
            return new CategoryItemHolder(LayoutInflater.from(mContext).inflate(R.layout.category_item_set_adapter, parent, false));
        else if (viewType == AppConstants.BANNER_ITEM)
            return new BannerItemHolder(LayoutInflater.from(mContext).inflate(R.layout.banner_item_set_adapter, parent, false));
        else if (viewType == AppConstants.CONTENT_ITEM_SET)
            return new ContentItemHolder(LayoutInflater.from(mContext).inflate(R.layout.category_item_set_adapter, parent, false));
        else if (viewType == AppConstants.SLIDER_ITEM)
            return new SliderHolder(LayoutInflater.from(mContext).inflate(R.layout.slider_item_set_adapter, parent, false));
        else if (viewType == AppConstants.VIDEO_OFFLINE_ITEM)
            return new VideoOfflineItemHolder(LayoutInflater.from(mContext).inflate(R.layout.category_item_set_adapter, parent, false));

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        // To Determine View Type
        int viewType = getItemViewType(position);

        if (viewType == AppConstants.CATEGORY_ITEM_SET) {

            final String title = dataList.get(position).getTitle();

            final CategoryItemHolder itemRowHolder = (CategoryItemHolder) holder;

            final ArrayList items = dataList.get(position).getItems();

            itemRowHolder.txtTitle.setText(title);

            CategoryItemAdapter itemListDataAdapter = new CategoryItemAdapter(mContext, items);

            itemRowHolder.recyclerView.setHasFixedSize(false);
            LinearLayoutManager lin = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            lin.setReverseLayout(false);
            itemRowHolder.recyclerView.setLayoutManager(lin);
            itemRowHolder.recyclerView.setNestedScrollingEnabled(false);
            itemRowHolder.recyclerView.setHasFixedSize(true);
            itemRowHolder.recyclerView.setAdapter(itemListDataAdapter);
            snapHelper.attachToRecyclerView(itemRowHolder.recyclerView);
            itemListDataAdapter.notifyDataSetChanged();

            itemRowHolder.txtMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mClickListener != null) {
                        mClickListener.onItemClick(position, items, v, itemRowHolder);
                    }

                    addFrg(ExtraItemFrg.newInstance(AppConstants.CATEGORY_ITEM_SET), "ExtraItemFrg");
                }
            });
            itemRowHolder.txtTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addFrg(ExtraItemFrg.newInstance(AppConstants.CATEGORY_ITEM_SET), "ExtraItemFrg");

                }
            });

        } else if (viewType == AppConstants.CONTENT_ITEM_SET) {

            final String title = dataList.get(position).getTitle();

            final ContentItemHolder itemRowHolder = (ContentItemHolder) holder;

            ArrayList items = dataList.get(position).getItems();

            itemRowHolder.txtTitle.setText(title);

            ContentItemAdapter itemListDataAdapter = new ContentItemAdapter(mContext, items);

            itemRowHolder.recyclerView.setHasFixedSize(false);
            LinearLayoutManager lin = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            lin.setReverseLayout(false);
            itemRowHolder.recyclerView.setLayoutManager(lin);
            itemRowHolder.recyclerView.setNestedScrollingEnabled(false);
            itemRowHolder.recyclerView.setHasFixedSize(true);
            itemRowHolder.recyclerView.setAdapter(itemListDataAdapter);
            snapHelper.attachToRecyclerView(itemRowHolder.recyclerView);
            itemListDataAdapter.notifyDataSetChanged();

        } else if (viewType == AppConstants.BANNER_ITEM) {

            final BannerItemHolder itemRowHolder = (BannerItemHolder) holder;

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

        } else if (viewType == AppConstants.SLIDER_ITEM) {

            final SliderHolder itemRowHolder = (SliderHolder) holder;

            ArrayList items = dataList.get(position).getItems();
            itemRowHolder.view_pager.setAdapter(new ViewSliderAdapter(AppConfig.context, items));
            itemRowHolder.view_pager.startAutoScroll();

            itemRowHolder.indicator = (CirclePageIndicator) itemRowHolder.itemView.findViewById(R.id.indicator);
            itemRowHolder.indicator.setViewPager(itemRowHolder.view_pager);

        }

        if (viewType == AppConstants.VIDEO_OFFLINE_ITEM) {

            final ArrayList items = dataList.get(position).getItems();
            final VideoOfflineItemHolder itemRowHolder = (VideoOfflineItemHolder) holder;

            if (items.size() == 0) {

                itemRowHolder.root.setVisibility(View.GONE);

            } else {

                itemRowHolder.txtTitle.setText(dataList.get(position).getTitle());

                VideoDownloadedAdapter itemListDataAdapter = new VideoDownloadedAdapter(mContext, items, AppConstants.VIDEO_SHOW_LINEAR);

                itemRowHolder.recyclerView.setHasFixedSize(false);
                LinearLayoutManager lin = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                lin.setReverseLayout(false);
                itemRowHolder.recyclerView.setLayoutManager(lin);
                itemRowHolder.recyclerView.setNestedScrollingEnabled(false);
                itemRowHolder.recyclerView.setHasFixedSize(true);
                itemRowHolder.recyclerView.setAdapter(itemListDataAdapter);
                snapHelper.attachToRecyclerView(itemRowHolder.recyclerView);
                itemListDataAdapter.notifyDataSetChanged();

                itemRowHolder.txtMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addFrg(VideoDownloadedFrg.newInstance(), "VideoDownloadedFrg");
                    }
                });
            }


        }

    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }


    @Override
    public int getItemViewType(int position) {

        if (dataList.get(position).getType() == AppConstants.CATEGORY_ITEM_SET)
            return AppConstants.CATEGORY_ITEM_SET;
        else if (dataList.get(position).getType() == AppConstants.CONTENT_ITEM_SET)
            return AppConstants.CONTENT_ITEM_SET;
        else if (dataList.get(position).getType() == AppConstants.BANNER_ITEM)
            return AppConstants.BANNER_ITEM;
        else if (dataList.get(position).getType() == AppConstants.SLIDER_ITEM)
            return AppConstants.SLIDER_ITEM;
        else if (dataList.get(position).getType() == AppConstants.VIDEO_OFFLINE_ITEM)
            return AppConstants.VIDEO_OFFLINE_ITEM;

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
            txtTitle = view.findViewById(R.id.txtTitle);
            txtMore = view.findViewById(R.id.txtMore);
            recyclerView = view.findViewById(R.id.recyclerView);

        }
    }

    public class CategoryItemHolder extends ItemHolder {

        private CategoryItemHolder(View view) {
            super(view);

            txtTitle.setTypeface(AppConfig.fontIRSensNumber);
            txtMore.setTypeface(AppConfig.fontIRSensNumber);
            recyclerView.getLayoutParams().height = AppConfig.itemHeight;
        }
    }

    public class ContentItemHolder extends ItemHolder {


        private ContentItemHolder(View view) {
            super(view);

            txtMore.setVisibility(View.GONE);

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

            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            txtTitle = (TextView) view.findViewById(R.id.txtTitle);

            txtTitle.setTypeface(AppConfig.fontIRSensNumber);
            recyclerView.getLayoutParams().height = AppConfig.itemHeight;
        }
    }

    public class SliderHolder extends RecyclerView.ViewHolder {

        public AutoScrollViewPager view_pager;
        public CirclePageIndicator indicator;

        private SliderHolder(View view) {
            super(view);

            view_pager = (AutoScrollViewPager) view.findViewById(R.id.view_pager);

            view_pager.startAutoScroll(10000);
            view_pager.startAutoScroll(5000);
            view_pager.setBorderAnimation(true);

            int h = (int) (AppConfig.width * 0.39f);
            view_pager.getLayoutParams().height = h;

        }
    }

    public class VideoOfflineItemHolder extends ItemHolder {

        private VideoOfflineItemHolder(View view) {
            super(view);

            txtTitle.setTypeface(AppConfig.fontIRSensNumber);
            txtMore.setTypeface(AppConfig.fontIRSensNumber);
        }
    }
}