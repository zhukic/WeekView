package com.github.zhukic.weekview;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * @author Vladislav Zhukov (https://github.com/zhukic)
 */

public class Decorator extends RecyclerView.ItemDecoration {

    private final int dp;

    public Decorator(Context context) {
        this.dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, context.getResources().getDisplayMetrics());
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);

        outRect.top = dp;

        if (position == parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = dp;
        }

    }
}
