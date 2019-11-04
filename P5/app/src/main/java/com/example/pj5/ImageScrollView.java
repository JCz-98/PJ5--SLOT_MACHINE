package com.example.pj5;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ImageScrollView extends FrameLayout
{
    private static int SPIN_DUR = 150;


    ImageView currimage,nextimage;

    int last_result = 0;
    int old_rot = 0;

    ScrollEnd scrollEnd;

    public void setScrollEnd(ScrollEnd scrollEnd)
    {
        this.scrollEnd = scrollEnd;
    }

    public ImageScrollView(@NonNull Context context)
    {
        super(context);
        init(context);
    }

    public ImageScrollView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    private void init(Context context)
    {
        LayoutInflater.from(context).inflate(R.layout.image_scroll, this);
        currimage = (ImageView) getRootView().findViewById(R.id.current_image);
        nextimage = (ImageView) getRootView().findViewById(R.id.next_image);

        nextimage.setTranslationY(getHeight());

    }

    public void setValueRandom(final int image, final int rotate_count)
    {
        currimage.animate().translationY(-getHeight()).setDuration(SPIN_DUR).start();
        nextimage.setTranslationY(nextimage.getHeight());
        nextimage.animate().translationY(0).setDuration(SPIN_DUR)
                .setListener(new Animator.AnimatorListener()
                {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        setImage(currimage, old_rot%6);
                        currimage.setTranslationY(0);

                        if(old_rot != rotate_count)
                        {
                            setValueRandom(image, rotate_count);
                            old_rot++;

                        }
                        else
                        {
                            last_result = 0;
                            old_rot = 0;
                            setImage(nextimage,image);
                            scrollEnd.scrollEnd(image%6,rotate_count);
                        }

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    private void setImage(ImageView img_view, int val)
    {
        if(val == ImgValue.BAR)
            img_view.setImageResource(R.drawable.bar_done);

        else if(val == ImgValue.SEVEN)
            img_view.setImageResource(R.drawable.sevent_done);

        else if(val == ImgValue.ORANGE)
            img_view.setImageResource(R.drawable.orange_done);

        else if(val == ImgValue.LEMON)
            img_view.setImageResource(R.drawable.lemon_done);

        else if(val == ImgValue.TRIPLE)
            img_view.setImageResource(R.drawable.triple_done);

        else
            img_view.setImageResource(R.drawable.waternelon_done);

        img_view.setTag(val);
        last_result = val;

    }

    public int getImgValue()
    {
        return Integer.parseInt(nextimage.getTag().toString());
    }
}
