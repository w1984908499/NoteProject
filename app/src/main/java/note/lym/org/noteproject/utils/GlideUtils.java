package note.lym.org.noteproject.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Glide管理类
 *
 * @author yaoming.li
 * @since 2017-05-03 15:06
 */
public class GlideUtils {

    private GlideUtils() {
        throw new RuntimeException("GlideUtils cannot be initialized!");
    }

    /**
     * 就加载一张图片，啥都不设置。
     *
     * @param act 上下文对象
     * @param img 设置加载的图片
     * @param url 图片地址
     */
    public static void load(Context act, ImageView img, String url) {
        Glide.with(act).load(url).into(img);
    }

    /**
     * 设置加载中的图片以及加载失败的占位图
     *
     * @param context      上下文
     * @param url          图片地址
     * @param errorImage   加载失败时的图片
     * @param defaultImage 加载中的图片
     * @param img          设置加载的图片
     */
    public static void load(Context context, String url, int errorImage, int defaultImage, ImageView img) {
        Glide.with(context).load(url).error(errorImage).dontAnimate().placeholder(defaultImage).into(img);
    }

    /**
     * 可设置加载图片的大小，并居中显示
     *
     * @param context      上下文
     * @param url          图片地址
     * @param errorImage   加载失败时的图片
     * @param defaultImage 加载中的图片
     * @param image        加载的图片
     * @param width        图片剪裁的宽度
     * @param height       图片剪裁的高度
     */
    public static void load(Context context, String url, int errorImage, int defaultImage, ImageView image, int width, int height) {
        Glide.with(context).load(url).centerCrop().error(errorImage).dontAnimate().placeholder(defaultImage).override(width, height).centerCrop().into(image);
    }

    /**
     * 可设置加载时的动画
     * @param context   上下文
     * @param url   图片地址
     * @param errorImage    加载失败时的图片
     * @param defaultImage  加载中的图片
     * @param imageView 加载的图片
     * @param width 剪裁图片的宽度
     * @param height    剪裁图片的高度
     * @param animationRes  加载时的动画
     */
    public static void load(Context context,String url,int errorImage,int defaultImage,ImageView imageView,int width,int height,int animationRes){
        Glide.with(context).load(url).error(errorImage).placeholder(defaultImage).override(width,height).crossFade().centerCrop().animate(animationRes).into(imageView);
    }

    /**
     * 暂停请求图片
     *
     * @param act Context
     */
    public static void pauseRequest(Context act) {
        Glide.with(act).pauseRequests();
    }

    /**
     * 恢复请求图片
     *
     * @param act Context
     */
    public static void resumeRequest(Context act) {
        Glide.with(act).resumeRequests();
    }

}