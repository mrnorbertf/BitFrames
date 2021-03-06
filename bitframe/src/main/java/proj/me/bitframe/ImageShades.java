package proj.me.bitframe;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.view.View;

import com.squareup.picasso.Picasso;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.List;

import proj.me.bitframe.exceptions.FrameException;
import proj.me.bitframe.helper.Utils;

/**
 * Created by root on 23/9/16.
 */

public abstract class ImageShades implements ImageClickHandler{
    ImageCallback imageCallback;
    void setImageCallback(ImageCallback imageCallback){
        this.imageCallback = imageCallback;
    }

    boolean result;
    void setResult(boolean result){
        this.result = result;
    }

    protected boolean getResult(){
        return result;
    }

    Picasso currentFramePicasso;

    protected Picasso getCurrentFramePicasso() {
        return currentFramePicasso;
    }

    void setCurrentFramePicasso(Picasso currentFramePicasso) {
        this.currentFramePicasso = currentFramePicasso;
    }

    protected void addImageView(View view, int viewWidth, int viewHeight, boolean hasAddInLayout) {
        imageCallback.addImageView(view, viewWidth, viewHeight, hasAddInLayout);
    }

    protected void imageClicked(ImageType imageType, int imagePosition, String imageLink) {
        imageCallback.imageClicked(imageType, imagePosition, imageLink);
    }

    protected void setColorsToAddMoreView(int resultColor, int mixedColor, int invertedColor) {
        imageCallback.setColorsToAddMoreView(resultColor, mixedColor, invertedColor);
    }

    protected void frameResult(BeanBitFrame... beanBitFrames) {
        imageCallback.frameResult(beanBitFrames);
    }

    protected void addMore() {
        imageCallback.addMore();
    }

    protected abstract void updateFrameUi(List<Bitmap> images, List<BeanImage> beanImages, boolean hasImageProperties) throws FrameException;
    protected abstract void onPaletteGenerated(Palette palette, int viewId) throws FrameException;

    public static class PaletteListener implements Palette.PaletteAsyncListener{
        int viewId;
        WeakReference<ImageShades> imageShadesSoftReference;
        public PaletteListener(int viewId, ImageShades imageShades){
            this.viewId = viewId;
            imageShadesSoftReference = new WeakReference<>(imageShades);
        }
        @Override
        public void onGenerated(Palette palette) {
            ImageShades imageShades = imageShadesSoftReference.get();
            if(imageShades == null) return;
            try {
                imageShades.onPaletteGenerated(palette, viewId);
            } catch (FrameException e) {
                e.printStackTrace();
            }
        }
    }
}