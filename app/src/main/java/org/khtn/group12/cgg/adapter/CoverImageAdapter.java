package org.khtn.group12.cgg.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.khtn.group12.cgg.R;

import java.util.List;

public class CoverImageAdapter extends PagerAdapter {
    private Context context;
    private List<String> data;

    public CoverImageAdapter(Context context, List<String> urls) {
        this.context = context;
        this.data = urls;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_image_view);
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setAdjustViewBounds(true);
        imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide.with(context).load(data.get(position))
                .apply(new RequestOptions()
                        .fitCenter()
                        .placeholder(android.R.drawable.ic_menu_report_image))
                .into(imageView);
        (container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((ImageView) object);
    }

}
