package com.example.courseapp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class LineNumbersDecoration extends RecyclerView.ItemDecoration {
    private final Paint paint;
    private final float textSize;

    public LineNumbersDecoration(int textColor, float textSize) {
        this.textSize = textSize;
        paint = new Paint();
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int childCount = parent.getChildCount();
        float offset = textSize / 3; // Adjust the offset to position the numbers correctly

        // Définir la couleur de fond de la colonne ici (par exemple, couleur bleue)
        int columnBackgroundColor = ContextCompat.getColor(parent.getContext(), R.color.dashboard_item_1);
        canvas.drawRect(parent.getLeft(), parent.getTop(), parent.getLeft() + offset, parent.getBottom(), paint);

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            String lineNumber = String.valueOf(position + 1);

            float x = child.getLeft() - offset;
            float y = child.getTop() + child.getHeight() / 2 + textSize / 2;
            canvas.drawText(lineNumber, x, y, paint);
        }
    }

}

