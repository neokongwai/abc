package watsons.wine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.Typeface;

public class Cell {
	private static final String TAG = "Cell";
	protected Boolean mThisMonth = false;
	protected Boolean mEvent = false;
	protected Rect mBound = null;
	protected Drawable mCircle = null;
	protected int mDayOfMonth = 1;	// from 1 to 31
	protected Paint mPaint = new Paint(Paint.SUBPIXEL_TEXT_FLAG
            |Paint.ANTI_ALIAS_FLAG);
	int dx, dy;
	public Cell(Context context, int dayOfMon, Rect rect, float textSize, boolean thisMonth) {
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Arial.ttf");
		
		mDayOfMonth = dayOfMon;
		mBound = rect;
		mPaint.setTextSize(textSize/*26f*/);
		mPaint.setTypeface(tf);
		mPaint.setColor(Color.BLACK);
		//if(bold) mPaint.setFakeBoldText(true);
		if (thisMonth)
			mThisMonth = true;
		
		dx = (int) mPaint.measureText(String.valueOf(mDayOfMonth)) / 2;
		dy = (int) (-mPaint.ascent() + mPaint.descent()) / 2;
		mCircle = context.getResources().getDrawable(R.drawable.icon_circle);
	}
	
	//public Cell(Context c,int dayOfMon, Rect rect, float textSize) {
	//	this(c,dayOfMon, rect, textSize, false);
	//}
	
	protected void draw(Canvas canvas) {
		canvas.drawText(String.valueOf(mDayOfMonth), mBound.centerX() - dx, mBound.centerY() + dy, mPaint);
	}
	
	protected void drawCircle(Canvas canvas) {
		if (mEvent)
		{
			mCircle.setBounds(mBound);
			mCircle.draw(canvas);
		}
	}
	
	public int getDayOfMonth() {
		return mDayOfMonth;
	}
	
	public boolean hitTest(int x, int y) {
		return mBound.contains(x, y); 
	}
	
	public Rect getBound() {
		return mBound;
	}
	
	public void setEvent()
	{
		mEvent = true;
	}
	
	//public void setCircle() {
	//	 mCircle.setBounds(mBound);
	//	 return;
	//}
	
	public boolean IsEvent()
	{
		return mEvent;
	}
	
	public String toString() {
		return String.valueOf(mDayOfMonth)+"("+mBound.toString()+")";
	}
	
	public boolean IsThisMonth()
	{
		return mThisMonth;
	}
	
}

