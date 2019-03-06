package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
;
import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.fragment.ExtraItemFrg;
import ir.sanatisharif.android.konkur96.fragment.FilterTagsFrg;
import ir.sanatisharif.android.konkur96.fragment.VideoDownloadedFrg;
import ir.sanatisharif.android.konkur96.listener.OnItemClickListener;
import ir.sanatisharif.android.konkur96.model.Content;
import ir.sanatisharif.android.konkur96.model.MainItem;
import ir.sanatisharif.android.konkur96.model.Video;
import ir.sanatisharif.android.konkur96.model.main_page.Banner;
import ir.sanatisharif.android.konkur96.model.main_page.MainBanner;
import ir.sanatisharif.android.konkur96.model.main_page.Set;
import ir.sanatisharif.android.konkur96.ui.GlideRequest;
import ir.sanatisharif.android.konkur96.ui.GlideRequests;
import ir.sanatisharif.android.konkur96.ui.view.autoscrollviewpager.AutoScrollViewPager;
import ir.sanatisharif.android.konkur96.ui.view.autoscrollviewpager.ViewSliderAdapter;
import ir.sanatisharif.android.konkur96.utils.Utils;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;
import static ir.sanatisharif.android.konkur96.app.AppConstants.MORE_VIDEO_OFFLINE;


public class MainItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MainItem> dataList;
    private Context mContext;
    private GlideRequests glideRequests;
    private OnItemClickListener mClickListener;
    int width = 0;
    //private SnapHelper snapHelper;

    public MainItemAdapter(Context context, List<MainItem> dataList, GlideRequests glideRequests) {
        this.dataList = dataList;
        this.mContext = context;
        this.glideRequests = glideRequests;
        //snapHelper = new GravitySnapHelper(Gravity.START);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == AppConstants.ITEM_SLIDER)
            return new SliderHolder(LayoutInflater.from(mContext).inflate(R.layout.item_slider_adapter, parent, false));
        else if (viewType == AppConstants.HEADER_DATA)
            return new HeaderHolder(LayoutInflater.from(mContext).inflate(R.layout.item_header_adapter, parent, false));
        else if (viewType == AppConstants.ITEM_SET)
            return new ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_set_adapter, parent, false));
        else if (viewType == AppConstants.ITEM_BANNER)
            return new ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_set_adapter, parent, false));
        else if (viewType == AppConstants.ITEM_CONTENT)
            return new ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_set_adapter, parent, false));
        else if (viewType == AppConstants.VIDEO_OFFLINE_ITEM)
            return new VideoOfflineItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_set_adapter, parent, false));

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        // To Determine View Type
        int viewType = getItemViewType(position);

        if (viewType == AppConstants.HEADER_DATA) {

            HeaderHolder headerHolder = (HeaderHolder) holder;
            String url = dataList.get(position).getUrl();

            headerHolder.txtTitle.setText(dataList.get(position).getTitle());

            View.OnClickListener filter = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addFrg(FilterTagsFrg.newInstance(url, null), "FilterTagsFrg");
                }
            };
            View.OnClickListener downloadVideo = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addFrg(VideoDownloadedFrg.newInstance(), "VideoDownloadedFrg");
                }
            };

            if (url != null) {
                if (URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url)) {
                    headerHolder.txtTitle.setOnClickListener(filter);
                    headerHolder.txtMore.setOnClickListener(filter);
                } else if (url.equals(MORE_VIDEO_OFFLINE)) {
                    headerHolder.txtTitle.setOnClickListener(downloadVideo);
                    headerHolder.txtMore.setOnClickListener(downloadVideo);
                }

            } else {
                headerHolder.txtTitle.setVisibility(View.GONE);
            }

        } else if (viewType == AppConstants.ITEM_SET) {

            final ItemHolder itemRowHolder = (ItemHolder) holder;

            final List<Set> items = dataList.get(position).getSets();

            CategoryItemAdapter itemListDataAdapter = new CategoryItemAdapter(mContext, items, glideRequests);

            itemRowHolder.recyclerView.setHasFixedSize(false);
            LinearLayoutManager lin = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            lin.setReverseLayout(false);
            itemRowHolder.recyclerView.setLayoutManager(lin);
            itemRowHolder.recyclerView.setNestedScrollingEnabled(false);
            itemRowHolder.recyclerView.setHasFixedSize(true);
            itemRowHolder.recyclerView.setAdapter(itemListDataAdapter);
            // snapHelper.attachToRecyclerView(itemRowHolder.recyclerView);
            itemListDataAdapter.notifyDataSetChanged();

        } else if (viewType == AppConstants.ITEM_CONTENT) {

            final ItemHolder itemRowHolder = (ItemHolder) holder;

            List<Content> items = dataList.get(position).getContents();

            ContentItemAdapter itemListDataAdapter = new ContentItemAdapter(mContext, items);

            itemRowHolder.recyclerView.setHasFixedSize(false);
            LinearLayoutManager lin = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            lin.setReverseLayout(false);
            itemRowHolder.recyclerView.setLayoutManager(lin);
            itemRowHolder.recyclerView.setNestedScrollingEnabled(false);
            itemRowHolder.recyclerView.setHasFixedSize(true);
            itemRowHolder.recyclerView.setAdapter(itemListDataAdapter);
            // snapHelper.attachToRecyclerView(itemRowHolder.recyclerView);
            itemListDataAdapter.notifyDataSetChanged();

        } else if (viewType == AppConstants.ITEM_BANNER) {

            final ItemHolder itemRowHolder = (ItemHolder) holder;

            List<Banner> items = dataList.get(position).getBanners();

            BannerItemAdapter itemListDataAdapter = new BannerItemAdapter(mContext, items);

            itemRowHolder.recyclerView.setHasFixedSize(true);
            LinearLayoutManager lin = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            lin.setReverseLayout(false);
            itemRowHolder.recyclerView.setLayoutManager(lin);
            itemRowHolder.recyclerView.setAdapter(itemListDataAdapter);
            itemRowHolder.recyclerView.setNestedScrollingEnabled(false);
            // snapHelper.attachToRecyclerView(itemRowHolder.recyclerView);

        } else if (viewType == AppConstants.ITEM_SLIDER) {

            final SliderHolder itemRowHolder = (SliderHolder) holder;

            List<MainBanner> items = dataList.get(position).getSliders();
            itemRowHolder.view_pager.setAdapter(new ViewSliderAdapter(AppConfig.context, items, glideRequests));
            itemRowHolder.view_pager.startAutoScroll();

            itemRowHolder.indicator = (CirclePageIndicator) itemRowHolder.itemView.findViewById(R.id.indicator);
            itemRowHolder.indicator.setViewPager(itemRowHolder.view_pager);

        }

        if (viewType == AppConstants.VIDEO_OFFLINE_ITEM) {

            final List<Video> items = dataList.get(position).getVideos();
            final VideoOfflineItemHolder itemRowHolder = (VideoOfflineItemHolder) holder;

            if (items.size() == 0) {
                itemRowHolder.root.setVisibility(View.GONE);

            } else {

                VideoDownloadedAdapter itemListDataAdapter = new
                        VideoDownloadedAdapter(mContext, items, AppConstants.VIDEO_SHOW_LINEAR, glideRequests);

                itemRowHolder.recyclerView.setHasFixedSize(false);
                LinearLayoutManager lin = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                lin.setReverseLayout(false);
                itemRowHolder.recyclerView.setLayoutManager(lin);
                itemRowHolder.recyclerView.setNestedScrollingEnabled(false);
                itemRowHolder.recyclerView.setHasFixedSize(true);
                itemRowHolder.recyclerView.setAdapter(itemListDataAdapter);
                itemListDataAdapter.notifyDataSetChanged();

            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }


    @Override
    public int getItemViewType(int position) {

        if (dataList.get(position).getType() == AppConstants.ITEM_SLIDER)
            return AppConstants.ITEM_SLIDER;
        else if (dataList.get(position).getType() == AppConstants.HEADER_DATA)
            return AppConstants.HEADER_DATA;
        else if (dataList.get(position).getType() == AppConstants.ITEM_SET)
            return AppConstants.ITEM_SET;
        else if (dataList.get(position).getType() == AppConstants.ITEM_CONTENT)
            return AppConstants.ITEM_CONTENT;
        else if (dataList.get(position).getType() == AppConstants.ITEM_BANNER)
            return AppConstants.ITEM_BANNER;
        else if (dataList.get(position).getType() == AppConstants.VIDEO_OFFLINE_ITEM)
            return AppConstants.VIDEO_OFFLINE_ITEM;

        return -1;

    }

    public void setSize(int w, int h) {

        AppConfig.itemHeight = (int) (w * 0.56f);
        width = w;
    }

    //holder
    public class ItemHolder extends RecyclerView.ViewHolder {

        protected LinearLayout root;
        protected RecyclerView recyclerView;

        private ItemHolder(View view) {
            super(view);

            root = view.findViewById(R.id.root);
            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.getLayoutParams().height = AppConfig.itemHeight;
        }
    }

    public class SliderHolder extends RecyclerView.ViewHolder {

        public AutoScrollViewPager view_pager;
        public CirclePageIndicator indicator;

        private SliderHolder(View view) {
            super(view);

            view_pager = (AutoScrollViewPager) view.findViewById(R.id.view_pager);

            view_pager.startAutoScroll(5000);
            view_pager.setBorderAnimation(true);

            int h = (int) (AppConfig.width * 0.39f);
            view_pager.getLayoutParams().height = h;

        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {

        protected TextView txtTitle;
        protected TextView txtMore;

        private HeaderHolder(View view) {
            super(view);

            txtTitle = view.findViewById(R.id.txtTitle);
            txtMore = view.findViewById(R.id.txtMore);
            txtTitle.setTypeface(AppConfig.fontIRSensNumber);
            txtMore.setTypeface(AppConfig.fontIRSensNumber);
        }
    }

    public class VideoOfflineItemHolder extends ItemHolder {

        private VideoOfflineItemHolder(View view) {
            super(view);

        }
    }
}