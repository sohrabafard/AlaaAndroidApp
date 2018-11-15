package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.fragment.ExtraVideoFrg;
import ir.sanatisharif.android.konkur96.listener.OnItemClickListener1111;
import ir.sanatisharif.android.konkur96.model.MainItem;
import ir.sanatisharif.android.konkur96.ui.view.autoscrollviewpager.AutoScrollViewPager;
import ir.sanatisharif.android.konkur96.ui.view.autoscrollviewpager.ViewSliderAdapter;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;


public class MainItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MainItem> dataList;
    private Context mContext;
    private SnapHelper snapHelper;
    private OnItemClickListener1111 mClickListener;

    //-----------size------
    private int width;

    public MainItemAdapter(Context context, ArrayList<MainItem> dataList) {
        this.dataList = dataList;
        this.mContext = context;
        snapHelper = new GravitySnapHelper(Gravity.START);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == AppConstants.VIDEO_ITEM_SET)
            return new VideoItemHolder(LayoutInflater.from(mContext).inflate(R.layout.video_item_set_adapter, parent, false));
        else if (viewType == AppConstants.BANNER_ITEM)
            return new BannerItemHolder(LayoutInflater.from(mContext).inflate(R.layout.banner_item_set_adapter, parent, false));
        else if (viewType == AppConstants.SLIDER_ITEM)
            return new SliderHolder(LayoutInflater.from(mContext).inflate(R.layout.slider_item_set_adapter, parent, false));

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        // To Determine View Type
        int viewType = getItemViewType(position);

        if (viewType == AppConstants.VIDEO_ITEM_SET) {

            final String title = dataList.get(position).getTitle();

            final VideoItemHolder itemRowHolder = (VideoItemHolder) holder;

            ArrayList items = dataList.get(position).getItems();

            itemRowHolder.txtTitle.setText(title);

            VideoItemAdapter itemListDataAdapter = new VideoItemAdapter(mContext, items);

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
                        mClickListener.onItemClick(position, v, itemRowHolder);
                    }

                    addFrg(ExtraVideoFrg.newInstance(), "ExtraVideoFrg");
                }
            });
            itemRowHolder.txtTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addFrg(ExtraVideoFrg.newInstance(), "ExtraVideoFrg");

                }
            });

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

    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }


    @Override
    public int getItemViewType(int position) {

        if (dataList.get(position).getType() == 0)
            return AppConstants.VIDEO_ITEM_SET;
        else if (dataList.get(position).getType() == 1)
            return AppConstants.BANNER_ITEM;
        else if (dataList.get(position).getType() == 2)
            return AppConstants.SLIDER_ITEM;

        return -1;

    }

    public void setSize(int w, int h) {

        AppConfig.itemHeight = (int) (w * 0.56f);
        width = w;
    }

    public class VideoItemHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle;
        private RecyclerView recyclerView;
        private TextView txtMore;

        private VideoItemHolder(View view) {
            super(view);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            txtMore = (TextView) view.findViewById(R.id.txtMore);

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

    public void setOnItemClickListener(OnItemClickListener1111 itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}