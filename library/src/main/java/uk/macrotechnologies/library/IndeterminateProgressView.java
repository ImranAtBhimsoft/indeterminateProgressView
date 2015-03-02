package uk.macrotechnologies.library;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import uk.macrotechnologies.library.utils.Utils;


/**
 * Created by Imran on 3/02/2015.
 * Indeterminate Progress Indicator
 */
public class IndeterminateProgressView extends View {
    private Paint mPaintSmallCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int[] colorScheme = {Color.BLACK, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.RED};
    private static final int DEFAULT_WIDTH = 48;
    private static final int DEFAULT_HEIGHT = 48;
    private static final int DEFAULT_DURATION = 2000;
    private int defaultDuration;
    private static final int radiusSmall = 8;
    private int initialIndex;
    private boolean shouldDrawOutCircle;
    private boolean drawOutCircles;
    private boolean isReverseAnimation;
    private ObjectAnimator startAngleAnimator;
    private float startAngle;
    private int interpolator;

    public IndeterminateProgressView(Context context) {
        this(context, null);
    }

    public IndeterminateProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndeterminateProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IndeterminateProgressView, defStyleAttr, 0);
        shouldDrawOutCircle = a.getBoolean(R.styleable.IndeterminateProgressView_outCircle, false);
        drawOutCircles = a.getBoolean(R.styleable.IndeterminateProgressView_drawCircles, false);
        isReverseAnimation = a.getBoolean(R.styleable.IndeterminateProgressView_reverseAnimation, true);
        defaultDuration = a.getInt(R.styleable.IndeterminateProgressView_duration, DEFAULT_DURATION);
        int outCircleColor = a.getColor(R.styleable.IndeterminateProgressView_outCircleColor, Color.RED);
        interpolator = a.getInt(R.styleable.IndeterminateProgressView_interpolator, 0);
        a.recycle();
        mPaintCircle.setColor(outCircleColor);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        try {
            if (startAngleAnimator != null) {
                startAngleAnimator.cancel();
            }
        } catch (Exception e) {
            Log.e("Exception", "cancel Animation", e);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(Utils.dpToPixels(getContext(), DEFAULT_WIDTH), widthSize);
        } else {
            width = Utils.dpToPixels(getContext(), DEFAULT_WIDTH);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(width = Utils.dpToPixels(getContext(), DEFAULT_HEIGHT), heightSize);
        } else {
            height = width = Utils.dpToPixels(getContext(), DEFAULT_HEIGHT);
        }
        setMeasuredDimension(width + getPaddingLeft() + getPaddingRight(), height + getPaddingBottom() + getPaddingTop());
    }

    public void setColorScheme(int... colorScheme) {
        this.colorScheme = colorScheme;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAngleAnimator = ObjectAnimator.ofFloat(this, "startAngle", 0, 360);
        startAngleAnimator.setDuration(defaultDuration);
        if (isReverseAnimation) {
            startAngleAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        } else {
            startAngleAnimator.setRepeatMode(ObjectAnimator.INFINITE);
        }
        startAngleAnimator.setRepeatCount(ObjectAnimator.INFINITE);


        Interpolator mInterpolator;
        switch (interpolator) {
            case 0:
                mInterpolator = new AccelerateDecelerateInterpolator();
                break;
            case 1:
                mInterpolator = new LinearInterpolator();
                break;
            default:
                mInterpolator = new AccelerateDecelerateInterpolator();
        }

        startAngleAnimator.setInterpolator(mInterpolator);
        startAngleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        startAngleAnimator.start();
    }

    @SuppressWarnings("unused")
    public void setStartAngle(float sAngle) {
        startAngle = sAngle;
    }

    @SuppressWarnings("unused")
    public float getStartAngle() {
        return startAngle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getMeasuredWidth() - (getPaddingLeft() + getPaddingRight());
        int h = getMeasuredHeight() - (getPaddingTop() + getPaddingBottom());
        float r = getRadius(w, h) - radiusSmall * 2;
        double c = 2 * Math.PI * r;
        int points = (int) (c / (radiusSmall * 3));
        if (initialIndex == 0 && points != 0) {
            initialIndex = points % colorScheme.length;
        }
        int actualIndex = initialIndex;
        float cX = w / 2;
        float cY = h / 2;
        double piByEighty = Math.PI / 180;
        mPaintCircle.setAlpha(80);
        if (shouldDrawOutCircle) {
            drawMainCircle(canvas, r, cX, cY);
        }
        for (int i = 0; i < points; i++) {
            float angle = i * 360 / points;
            angle += startAngle;
            double radian = angle * piByEighty;
            float x = (float) (cX + r * Math.cos(radian));
            float y = (float) (cY + r * Math.sin(radian));
            int index = actualIndex % colorScheme.length;
            mPaintSmallCircle.setColor(colorScheme[index]);
            if (drawOutCircles) {
                canvas.drawCircle(x, y, radiusSmall, mPaintSmallCircle);
            } else {
                canvas.drawRect(x - radiusSmall, y - radiusSmall, x + radiusSmall, y + radiusSmall, mPaintSmallCircle);
            }
            actualIndex++;
        }
    }

    private void drawMainCircle(Canvas canvas, float r, float cX, float cY) {
        mPaintCircle.setStrokeWidth(2);
        mPaintCircle.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(cX, cY, r, mPaintCircle);
    }

    private float getRadius(float w, float h) {
        return Math.min(w, h) / 2;
    }
}
