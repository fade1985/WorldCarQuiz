package com.android.worldcarquiz.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class LoaderImageView extends RelativeLayout {
    private Context     context;
    private ProgressBar progressBar;
    private ImageView   imageView;

    public LoaderImageView(final Context context, final AttributeSet attrSet) {
    super(context, attrSet);
    instantiate(context, attrSet);
    }

   private void instantiate(final Context _context, AttributeSet attrSet) {
       context = _context;
       imageView = new ImageView(context, attrSet);
       progressBar = new ProgressBar(context, attrSet);
       
       imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
       imageView.setScaleType(ScaleType.CENTER_CROP);
       imageView.setAdjustViewBounds(true);
       
       progressBar.setIndeterminate(true);
       progressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
       addView(progressBar);
       addView(imageView);

       this.setGravity(Gravity.CENTER);
   }
   
   public void setImage(Bitmap bitmap) {
       progressBar.setVisibility(View.GONE);
       imageView.setVisibility(View.VISIBLE);
       
       imageView.setImageBitmap(bitmap);
   }
   
   public void setLoader(int resource) {
       progressBar.setVisibility(View.VISIBLE);
       imageView.setVisibility(View.GONE);
       
       imageView.setImageResource(resource);
   }
}