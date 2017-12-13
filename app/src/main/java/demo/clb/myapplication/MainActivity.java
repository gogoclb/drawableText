package demo.clb.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hexin.android.view.DrawableTextView;

public class MainActivity extends AppCompatActivity {

    static int i = 1;

    DrawableTextView drawableTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawableTextView = (DrawableTextView) findViewById(R.id.drawableText);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i % 6 == 0) {
                    Drawable drawable = getResources().getDrawable(R.drawable.b60a7aa66f2197205420088828280c22);
                    drawableTextView.setDrawable(DrawableTextView.LEFT_DRAWABLE, drawable);
                } else if (i % 6 == 1) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.b60a7aa66f2197205420088828280c22);
                    drawableTextView.setBitmap(DrawableTextView.LEFT_DRAWABLE, null);
                } else if (i % 6 == 2) {
                    Drawable drawable = getResources().getDrawable(R.mipmap.a448c607d81e265ba313beef89e2a4c92);
                    drawable.setBounds(new Rect(0, 0, 150, 150));
                    drawableTextView.setDrawable(DrawableTextView.LEFT_DRAWABLE, drawable);
                } else if (i % 6 == 3) {
                    Drawable drawable = getResources().getDrawable(R.drawable.b60a7aa66f2197205420088828280c22);
                    drawableTextView.setDrawable(DrawableTextView.RIGHT_DRAWABLE, drawable);
                } else if (i % 6 == 4) {
                    drawableTextView.setDrawable(DrawableTextView.RIGHT_DRAWABLE, null);
                } else if (i % 6 == 5) {
                    drawableTextView.setDrawable(DrawableTextView.RIGHT_DRAWABLE, R.drawable.b60a7aa66f2197205420088828280c22);
                }

                i++;
            }
        });
    }

}
