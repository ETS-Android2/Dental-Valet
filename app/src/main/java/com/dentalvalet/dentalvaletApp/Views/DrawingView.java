package com.dentalvalet.dentalvaletApp.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.View;

import com.dentalvalet.www.dentalvaletApp.R;

/**
 * Created by Awais Mahmood on 24-Nov-15.
 */
class DrawingView extends View {
    Bitmap bitmap;

    public DrawingView(Context context) {
        super(context);
        bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.lock);

    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        // paint.setColor(Color.CYAN);
        canvas.drawBitmap(getclip(), 30, 20, paint);
    }

    public Bitmap getclip() {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        // paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2,
                bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
