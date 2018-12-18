package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.fragment.ContentFrg;
import ir.sanatisharif.android.konkur96.fragment.DetailsVideoFrg;
import ir.sanatisharif.android.konkur96.listener.OnItemClickListener;
import ir.sanatisharif.android.konkur96.model.CategoryItemSet;
import ir.sanatisharif.android.konkur96.model.Content;
import ir.sanatisharif.android.konkur96.ui.GlideApp;
import ir.sanatisharif.android.konkur96.ui.view.CircleTransform;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;

public class ExtraItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CategoryItemSet> categoryItemSets = new ArrayList<>();
    private ArrayList<Content> contentItems = new ArrayList<>();
    private int type;
    private Context mContext;
    private OnItemClickListener mClickListener;

    public ExtraItemAdapter(Context context, int type) {

        this.type = type;
        this.mContext = context;
    }

    public void updateList(ArrayList<?> items) {
        if (type == AppConstants.CONTENT_ITEM_SET)
            this.contentItems.addAll((ArrayList<Content>) items);
        else if (type == AppConstants.CATEGORY_ITEM_SET)
            this.categoryItemSets.addAll((ArrayList<CategoryItemSet>) items);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == AppConstants.CATEGORY_ITEM_SET)
            return new CategoryHolder(LayoutInflater.from(mContext).inflate(R.layout.extra_category_item, parent, false));
        else if (viewType == AppConstants.CONTENT_ITEM_SET)
            return new ContentHolder(LayoutInflater.from(mContext).inflate(R.layout.extra_content_item, parent, false));

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = getItemViewType(position);

        if (viewType == AppConstants.CATEGORY_ITEM_SET) {

            final CategoryItemSet item = categoryItemSets.get(position);
            final CategoryHolder itemHolder = (CategoryHolder) holder;
            itemHolder.txtTitle.setText(item.getTitle());
            itemHolder.txtAuthor.setText(item.getAuthor());
            itemHolder.txtView.setText(item.getView() + " ویو");

            //w= 460 and h = 259
            int height = AppConfig.itemHeight;
            int width = AppConfig.width;

            width -= 24;

            itemHolder.imgItem.getLayoutParams().width = width;
            itemHolder.imgItem.getLayoutParams().height = height;


            GlideApp.with(AppConfig.context)
                    .load(item.getImageUrl())
                    .override(64, 64)
                    .transform(new CircleTransform(AppConfig.context))
                    .into(itemHolder.imgAuthor);

            GlideApp.with(AppConfig.context)
                    .load(item.getImageUrl())
                    .override(width, height)
                    //.transforms(new CenterCrop(), new RoundedCorners((int) mContext.getResources().getDimension(R.dimen.round_image)))
                    .into(new SimpleTarget<Drawable>(460, 259) {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            itemHolder.imgItem.setImageDrawable(resource);
                            itemHolder.imgPlay.setVisibility(View.VISIBLE);
                        }
                    });

            itemHolder.imgPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    addFrg(DetailsVideoFrg.newInstance(categoryItemSets.get(position)), "DetailsVideoFrg");

                }
            });

            itemHolder.imgMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    PopupMenu popup = new PopupMenu(AppConfig.currentActivity, view);
                    popup.getMenuInflater().inflate(R.menu.menu_post, popup.getMenu());
                    popup.show();


                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item1) {
                            int i = item1.getItemId();
                            if (i == R.id.action_watch) {
                                addFrg(DetailsVideoFrg.newInstance(categoryItemSets.get(position)), "DetailsVideoFrg");

                                return true;
                            } else if (i == R.id.action_share) {
                                return true;
                            }
                            return false;
                        }
                    });

                }
            });

        } else if (viewType == AppConstants.CONTENT_ITEM_SET) {

            final Content item = contentItems.get(position);
            final ContentHolder itemHolder = (ContentHolder) holder;
            itemHolder.txtTitle.setText(item.getTitle());
            itemHolder.txtAuthor.setText(item.getAuthor());
            itemHolder.txtView.setText(item.getView() + " ویو");
            itemHolder.imgType.setImageResource(item.getDocType());
            //w= 460 and h = 259
            int height = AppConfig.itemHeight;
            int width = AppConfig.width;

            width -= 24;

            itemHolder.imgItem.getLayoutParams().width = width;
            itemHolder.imgItem.getLayoutParams().height = height;


            GlideApp.with(AppConfig.context)
                    .load(item.getImageUrl())
                    .override(width, height)
                    //.transforms(new CenterCrop(), new RoundedCorners((int) mContext.getResources().getDimension(R.dimen.round_image)))
                    .into(new SimpleTarget<Drawable>(460, 259) {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            itemHolder.imgItem.setImageDrawable(resource);
                        }
                    });

            itemHolder.imgItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    addFrg(ContentFrg.newInstance(), "ContentFrg");
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if (type == AppConstants.CONTENT_ITEM_SET)
            return (null != contentItems ? contentItems.size() : 0);
        else if (type == AppConstants.CATEGORY_ITEM_SET)
            return (null != categoryItemSets ? categoryItemSets.size() : 0);
        return 0;
    }


    @Override
    public int getItemViewType(int position) {

        if (type == AppConstants.CATEGORY_ITEM_SET)
            return AppConstants.CATEGORY_ITEM_SET;
        else if (type == AppConstants.CONTENT_ITEM_SET)
            return AppConstants.CONTENT_ITEM_SET;
        return -1;

    }


    //<editor-fold desc="Class holder">

    public class BaseHolder extends RecyclerView.ViewHolder {

        protected LinearLayout layout_click;
        protected TextView txtTitle, txtView, txtAuthor;
        protected ImageView imgItem;

        private BaseHolder(View view) {
            super(view);

            layout_click = (LinearLayout) view.findViewById(R.id.layout_click);
            txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            txtView = (TextView) view.findViewById(R.id.txtView);
            txtAuthor = (TextView) view.findViewById(R.id.txtAuthor);
            imgItem = (ImageView) view.findViewById(R.id.imgItem);

            setTypeFace(txtView);
            setTypeFace(txtTitle);
            setTypeFace(txtAuthor);

        }

        void setTypeFace(View view) {
            if (view instanceof TextView)
                ((TextView) view).setTypeface(AppConfig.fontIRSensNumber);
        }


        void ripple(View view, int radius) {
            MaterialRippleLayout.on(view)
                    .rippleOverlay(true)
                    .rippleAlpha(0.1f)
                    .rippleRoundedCorners(radius)
                    .rippleHover(true)
                    .create();
        }
    }

    public class ContentHolder extends BaseHolder {

        private ImageView imgType;

        private ContentHolder(View view) {
            super(view);

            imgType = (ImageView) view.findViewById(R.id.imgType);

            //set ripple
            ripple(imgType, 24);
        }


    }

    public class CategoryHolder extends BaseHolder {

        private ImageView imgAuthor, imgMenu, imgPlay;

        private CategoryHolder(View view) {
            super(view);

            imgAuthor = (ImageView) view.findViewById(R.id.imgAuthor);
            imgMenu = (ImageView) view.findViewById(R.id.imgMenu);
            imgPlay = (ImageView) view.findViewById(R.id.imgPlay);

            //set ripple
            ripple(imgPlay, 24);
            ripple(imgAuthor, 24);
            ripple(imgMenu, 4);
        }

    }
    //</editor-fold>

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

}