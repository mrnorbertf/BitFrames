package proj.me.bitframedemo.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import proj.me.bitframe.BeanBitFrame;
import proj.me.bitframe.BeanImage;
import proj.me.bitframe.FrameCallback;
import proj.me.bitframe.ImageType;
import proj.me.bitframe.helper.FrameType;
import proj.me.bitframe.helper.Utils;
import proj.me.bitframedemo.R;
import proj.me.bitframedemo.beans.FrameBean;
import proj.me.bitframedemo.binders.CardBinder;
import proj.me.bitframedemo.databinding.CardContainerBinding;

/**
 * Created by root on 20/9/16.
 */

public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.ViewHolder>{

    List<FrameBean> frameBeanList;
    int containerWidth;
    int containerHeight;
    public FrameAdapter(int containerWidth, int containerHeight, List<FrameBean> frameBeanList){
        this.frameBeanList = frameBeanList;
        this.containerWidth = containerWidth;
        this.containerHeight = containerHeight;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new ViewHolder(inflater.inflate(R.layout.card_container, parent, false), containerWidth, containerHeight);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FrameBean frameBean = frameBeanList.get(position);
        Utils.logVerbose("IMAGE_LOADING : "+" came to load view frame at "+position);
        holder.cardContainerBinding.viewFrame.clearContainerChilds();
        holder.cardBinder.setTitle(frameBean.getTitle());
        holder.cardBinder.setDescription(frameBean.getDescription());
        holder.cardBinder.setTitleColor(Color.parseColor("#000000"));
        holder.cardBinder.setDescriptionColor(Color.parseColor("#000000"));
        List<BeanImage> beanImageList = frameBean.getBeanBitFrameList();
        holder.cardContainerBinding.viewFrame.showBitFrame(beanImageList, holder, FrameType.FRAMED);
    }

    @Override
    public int getItemCount() {
        return frameBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements FrameCallback {
        CardContainerBinding cardContainerBinding;
        CardBinder cardBinder = new CardBinder();
        public ViewHolder(View itemView, int containerWidth, int containerHeight) {
            super(itemView);
            cardContainerBinding = DataBindingUtil.bind(itemView);
            cardContainerBinding.setCardBinder(cardBinder);

            cardContainerBinding.viewFrame.setFrameDimensions(0, 0, containerWidth, containerHeight);

            ViewGroup.LayoutParams layoutParams = cardContainerBinding.viewFrame.getLayoutParams();
            layoutParams.width = containerWidth;
            layoutParams.height = containerHeight;
            cardContainerBinding.viewFrame.setLayoutParams(layoutParams);
            cardContainerBinding.viewFrame.invalidate();
        }

        @Override
        public void imageClick(ImageType imageType, int imagePosition, String imageLink) {}
        @Override
        public void frameResult(List<BeanBitFrame> beanBitFrameList) {}
        @Override
        public void addMoreClick() {}
        @Override
        public void containerAdded(int containerWidth, int containerHeight, boolean isAddInLayout) {}

        @Override
        public void loadedFrameColors(int lastLoadedFrameColor, int mixedLoadedColor, int inverseMixedLoadedColor) {
            cardBinder.setTitleColor(mixedLoadedColor);
            cardBinder.setDescriptionColor(mixedLoadedColor);
        }
    }
}
