package ir.sanatisharif.android.konkur96.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.duolingo.open.rtlviewpager.RtlViewPager;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.adapter.FullScreenAdapter;
import ir.sanatisharif.android.konkur96.adapter.IndicatorAdapter;
import ir.sanatisharif.android.konkur96.api.Models.ProductPhotoModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.interfaces.PositionFounder;
import ir.sanatisharif.android.konkur96.model.FullScreenModel;

public class GalleryFullView extends AppCompatActivity implements PositionFounder {

    TextView title, description;
    private RtlViewPager pagerFullScreen;
    private RecyclerView recyclerIndicator;
    private FullScreenModel fullScreenModel;
    private LinearLayoutManager layoutManager;
    private ImageView imgRightArrow;
    private ImageView imgLeftArrow;
    private ImageView imgTopArrow;
    private BottomSheetBehavior bottemSheetBehavior;
    private int position = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_full);

        pagerFullScreen = findViewById(R.id.pager_fullscreen);

        title = findViewById(R.id.tv_title_screen);
        description = findViewById(R.id.tv_desc_screen);

        imgRightArrow = findViewById(R.id.img_right);
        imgLeftArrow = findViewById(R.id.img_left);

        View bottomSheet = findViewById(R.id.bottem_sheet);
        bottemSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottemSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        getItem();

        recyclerIndicator = findViewById(R.id.recycler_indicator);
        FullScreenAdapter fullScreenImageAdapter = new FullScreenAdapter(this, getAllImage(), selectedPosition());
        pagerFullScreen.setAdapter(fullScreenImageAdapter);


        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        recyclerIndicator.setLayoutManager(layoutManager);
        IndicatorAdapter indicatorAdapter = new IndicatorAdapter(this, getAllImage(), selectedPosition(), this);
        recyclerIndicator.setAdapter(indicatorAdapter);
        setCurrentIndicator();
        initPosition(selectedPosition());

        pagerFullScreen.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                initPosition(position);

                ProductPhotoModel temp = getTitleAndDesc(position);

                if (temp.getTitle() == null) {
                    title.setVisibility(View.GONE);
                }

                if (temp.getDescription() == null) {
                    description.setVisibility(View.GONE);
                }

                title.setText(temp.getTitle());
                description.setText(temp.getDescription());

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        pagerFullScreen.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                final float normalizedposition = Math.abs(Math.abs(position) - 1);
                page.setScaleX(normalizedposition / 2 + 0.5f);
                page.setScaleY(normalizedposition / 2 + 0.5f);
            }
        });

        checkArrowButtons(selectedPosition());

        imgRightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagerFullScreen.setCurrentItem(getPosition() - 1);
            }
        });

        imgLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pagerFullScreen.setCurrentItem(getPosition() + 1);
            }
        });


        recyclerAnimation();


        //todo:fix this
        pagerFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AppConfig.context, "tapped", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getItem() {

        if (getIntent().hasExtra("TAG_MODEL")) {
            Intent getData = this.getIntent();
            Bundle bundle = getData.getExtras();
            assert bundle != null;
            fullScreenModel = bundle.getParcelable("TAG_MODEL");

        }
    }

    private ArrayList<ProductPhotoModel> getAllImage() {
        return fullScreenModel.getImageGalleryModels();
    }

    private int selectedPosition() {
        return fullScreenModel.getPosition();
    }

    private void setCurrentPic() {
        pagerFullScreen.setCurrentItem(selectedPosition());
    }

    private void setCurrentIndicator() {
        recyclerIndicator.scrollToPosition(selectedPosition());


    }

    public void setCurrentPic(int pos) {
        pagerFullScreen.setCurrentItem(pos);
        layoutManager.scrollToPositionWithOffset(pos, 0);

        checkArrowButtons(pos);
        setPosition(pos);
        recyclerAnimation();
    }


    @Override
    public void initPosition(int pos) {
        setCurrentPic(pos);
        setPosition(pos);

    }

    @Override
    public int getPosition() {
        return position;
    }


    public void setPosition(int pos) {
        position = pos;
    }


    public void recyclerAnimation() {
        int resId = R.anim.item_animation_scale;
//        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(), resId);
//        recyclerIndicator.setLayoutAnimation(animation);
    }

    public ProductPhotoModel getTitleAndDesc(int pos) {

        ArrayList<ProductPhotoModel> imageGalleryModels = getAllImage();

        return imageGalleryModels.get(pos);
    }

    private void checkArrowButtons(final int pos) {

        if (pos == 0) {
            imgRightArrow.setVisibility(View.INVISIBLE);
        } else {
            imgRightArrow.setVisibility(View.VISIBLE);
        }

        if (pos == getAllImage().size() - 1) {
            imgLeftArrow.setVisibility(View.INVISIBLE);
        } else {
            imgLeftArrow.setVisibility(View.VISIBLE);
        }

    }

}