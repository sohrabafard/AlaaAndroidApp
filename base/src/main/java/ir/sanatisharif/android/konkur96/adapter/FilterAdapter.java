package ir.sanatisharif.android.konkur96.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.fragment.DetailsVideoFrg;
import ir.sanatisharif.android.konkur96.fragment.FilterTagsFrg;
import ir.sanatisharif.android.konkur96.fragment.ProductDetailFragment;
import ir.sanatisharif.android.konkur96.fragment.ShowArticleInfoFrg;
import ir.sanatisharif.android.konkur96.fragment.ShowContentInfoFrg;
import ir.sanatisharif.android.konkur96.listener.OnItemClickListener;
import ir.sanatisharif.android.konkur96.model.filter.ArticleCourse;
import ir.sanatisharif.android.konkur96.model.filter.FilterBaseModel;
import ir.sanatisharif.android.konkur96.model.filter.Pagination;
import ir.sanatisharif.android.konkur96.model.filter.PamphletCourse;
import ir.sanatisharif.android.konkur96.model.filter.SetFilterCourse;
import ir.sanatisharif.android.konkur96.model.filter.SetFilterProduct;
import ir.sanatisharif.android.konkur96.model.filter.VideoCourse;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;

public class FilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<? extends FilterBaseModel> mList;
    private Context mContext;
    private OnItemClickListener mClickListener;
    private int width, height;
    RequestOptions requestOptions;

    public FilterAdapter(Context context, List<? extends FilterBaseModel> list) {
        this.mList = list;
        this.mContext = context;
        setSize();
    }

    private void setSize() {

        //w= 460 and h = 259
        width = AppConfig.width;
        height = (int) (AppConfig.width * 0.56);
        requestOptions = new RequestOptions()
                .override(width, height)
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == AppConstants.FILTER_VIDEO)
            return new VideoHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_video_filter, parent, false));
        else if (viewType == AppConstants.FILTER_ARTICLE)
            return new ArticleHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_article_filter, parent, false));
        else if (viewType == AppConstants.FILTER_PAMPHLET)
            return new PamphletHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_pamphlet_filter, parent, false));
        else if (viewType == AppConstants.FILTER_SET)
            return new SetHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_set_filter, parent, false));
        else if (viewType == AppConstants.FILTER_PRODUCT)
            return new ProductHolder(LayoutInflater.from(mContext).inflate(R.layout.item_filter_product, parent, false));

        return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = getItemViewType(position);

        if (viewType == AppConstants.FILTER_VIDEO) {

            final VideoCourse item = (VideoCourse) mList.get(position);
            final VideoHolder itemHolder = (VideoHolder) holder;
            itemHolder.txtTitle.setText(item.getName());
            itemHolder.txtAuthor.setText(item.getAuthor().getFullName());
            itemHolder.txtSession.setText(" - جلسه " + item.getOrder());

            //set Size
            itemHolder.imgItem.getLayoutParams().width = width;
            itemHolder.imgItem.getLayoutParams().height = height;

            Glide.with(mContext)
                    .load(item.getThumbnail())
                    .apply(requestOptions)
                    .thumbnail(0.1f)
                    .into(new SimpleTarget<Drawable>(460, 259) {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            itemHolder.imgItem.setImageDrawable(resource);
                            itemHolder.loader.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            itemHolder.loader.setVisibility(View.GONE);
                        }
                    });

            itemHolder.layout_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    addFrg(DetailsVideoFrg.newInstance(mList, position), "DetailsVideoFrg");

                }
            });

        } else if (viewType == AppConstants.FILTER_PAMPHLET) {

            final PamphletCourse item = (PamphletCourse) mList.get(position);
            final PamphletHolder itemHolder = (PamphletHolder) holder;
            itemHolder.txtTitle.setText(item.getName());
            itemHolder.txtAuthor.setText(item.getAuthor().getFullName());

            itemHolder.layout_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addFrg(ShowContentInfoFrg.newInstance(item), "DetailsVideoFrg");
                }
            });

        } else if (viewType == AppConstants.FILTER_SET) {

            final SetFilterCourse item = (SetFilterCourse) mList.get(position);
            final SetHolder itemHolder = (SetHolder) holder;
            itemHolder.txtTitle.setText(item.getName());
            itemHolder.txtAuthor.setText(item.getAuthor().getFullName());

            itemHolder.imgItem.getLayoutParams().width = width;
            itemHolder.imgItem.getLayoutParams().height = height;

            Glide.with(mContext)
                    .load(item.getPhoto())
                    .apply(requestOptions)
                    .thumbnail(0.1f)
                    .into(new SimpleTarget<Drawable>(460, 259) {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            itemHolder.imgItem.setImageDrawable(resource);
                            itemHolder.loader.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            itemHolder.loader.setVisibility(View.GONE);
                        }
                    });

            itemHolder.layout_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addFrg(FilterTagsFrg.newInstance(item.getContentUrl(), null), "DetailsVideoFrg");
                }
            });

        } else if (viewType == AppConstants.FILTER_ARTICLE) {
            final ArticleCourse item = (ArticleCourse) mList.get(position);
            final ArticleHolder itemHolder = (ArticleHolder) holder;

            itemHolder.txtAuthor.setText(item.getAuthor().getFullName());
            itemHolder.txtTitle.setText(item.getName());

            itemHolder.layout_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addFrg(ShowArticleInfoFrg.newInstance(item), "DetailsVideoFrg");
                }
            });
        } else if (viewType == AppConstants.FILTER_PRODUCT) {

            SetFilterProduct item = (SetFilterProduct) mList.get(position);
            final ProductHolder itemHolder = (ProductHolder) holder;

            Glide.with(mContext)
                    .load(item.getPhoto())
                    .apply(requestOptions)
                    .thumbnail(0.1f)
                    .into(new SimpleTarget<Drawable>(460, 259) {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            itemHolder.imageView.setImageDrawable(resource);

                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);

                        }
                    });

            itemHolder.txtn.setText(item.getName());
            itemHolder.txtPrice.setText(ShopUtils.formatPrice(item.getPrice().getMfinal()) + " تومان ");
            itemHolder.txtDiscount.setPaintFlags(itemHolder.txtDiscount.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            if (item.getPrice().getDiscount() > 0) {
                itemHolder.txtDiscount.setVisibility(View.VISIBLE);
                itemHolder.txtDiscount.setText(ShopUtils.formatPrice(item.getPrice().getBase()) + " تومان ");

            } else {

                itemHolder.txtDiscount.setVisibility(View.GONE);
            }

            itemHolder.cardViewRoot.setOnClickListener(view -> addFrg(ProductDetailFragment.newInstance((ProductModel) item), "ProductDetailFragment"));

        }
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


    @Override
    public int getItemViewType(int position) {


        if (mList.get(position).getViewType() == AppConstants.FILTER_VIDEO)
            return AppConstants.FILTER_VIDEO;
        else if (mList.get(position).getViewType() == AppConstants.FILTER_ARTICLE)
            return AppConstants.FILTER_ARTICLE;
        else if (mList.get(position).getViewType() == AppConstants.FILTER_PAMPHLET)
            return AppConstants.FILTER_PAMPHLET;
        else if (mList.get(position).getViewType() == AppConstants.FILTER_SET)
            return AppConstants.FILTER_SET;
        else if (mList.get(position).getViewType() == AppConstants.FILTER_PRODUCT)
            return AppConstants.FILTER_PRODUCT;
        return -1;

    }


    //<editor-fold desc="Class holder">

    public class BaseHolder extends RecyclerView.ViewHolder {

        protected LinearLayout layout_click;
        protected TextView txtTitle, txtAuthor, txtSession;

        private BaseHolder(View view) {
            super(view);

            layout_click = view.findViewById(R.id.layout_click);
            txtTitle = view.findViewById(R.id.txtTitle);
            txtAuthor = view.findViewById(R.id.txtAuthor);
            txtSession = view.findViewById(R.id.txtSession);

            setTypeFace(txtTitle);
            setTypeFace(txtAuthor);
            setTypeFace(txtSession);
            ripple(layout_click, 0);

        }

        void setTypeFace(View view) {
            if (view instanceof TextView)
                ((TextView) view).setTypeface(AppConfig.fontIRSensNumber);
        }


        void ripple(View view, int radius) {
            MaterialRippleLayout.on(view)
                    .rippleOverlay(true)
                    .rippleAlpha(0.2f)
                    .rippleRoundedCorners(radius)
                    .rippleHover(true)
                    .create();
        }
    }

    public class VideoHolder extends BaseHolder {

        private ImageView imgItem;
        private ProgressBar loader;

        private VideoHolder(View view) {
            super(view);

            imgItem = (ImageView) view.findViewById(R.id.imgItem);
            loader = view.findViewById(R.id.loader);
            loader.getIndeterminateDrawable().setColorFilter(0xFFFFB700, android.graphics.PorterDuff.Mode.MULTIPLY);

        }
    }

    public class PamphletHolder extends BaseHolder {

        private TextView txtPDFSize;

        private PamphletHolder(View view) {
            super(view);

            txtPDFSize = view.findViewById(R.id.txtPDFSize);

            setTypeFace(txtPDFSize);
        }
    }

    public class SetHolder extends BaseHolder {

        private ImageView imgItem;
        private ProgressBar loader;

        private SetHolder(View view) {
            super(view);

            imgItem = (ImageView) view.findViewById(R.id.imgItem);
            loader = view.findViewById(R.id.loader);
            loader.getIndeterminateDrawable().setColorFilter(0xFFFFB700, android.graphics.PorterDuff.Mode.MULTIPLY);
        }
    }

    public class ArticleHolder extends BaseHolder {


        private ArticleHolder(View view) {
            super(view);

        }
    }

    public class ProductHolder extends RecyclerView.ViewHolder  {

        private ImageView imageView;
        private CardView cardViewRoot;
        private TextView txtn, txtPrice, txtDiscount;

        public ProductHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img);
            txtn = itemView.findViewById(R.id.txt_titel);
            txtPrice = itemView.findViewById(R.id.txt_price);
            txtDiscount = itemView.findViewById(R.id.txt_discount);
            cardViewRoot = itemView.findViewById(R.id.cardViewRoot);

            setTypeFace(txtn);
            setTypeFace(txtPrice);
            setTypeFace(txtDiscount);
            //ripple(cardViewRoot, 0);
        }

        void setTypeFace(View view) {
            if (view instanceof TextView)
                ((TextView) view).setTypeface(AppConfig.fontIRSensNumber);
        }
    }


    //</editor-fold>

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

}