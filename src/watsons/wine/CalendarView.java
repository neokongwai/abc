package watsons.wine;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.MonthDisplayHelper;
import android.view.MotionEvent;
import android.widget.ImageView;

public class CalendarView extends ImageView {
    private static int WEEK_TOP_MARGIN;// = 74;
    private static int WEEK_LEFT_MARGIN,WEEK_TITLE_LEFT_MARGIN;// = 40;
    private static int WEEK_RIGHT_MARGIN,WEEK_TITLE_RIGHT_MARGIN;
    private static int CELL_WIDTH;// = 58;
    private static int CELL_HEIGH;// = 53;
    private static int CELL_MARGIN_TOP;// = 92;
    private static int CELL_MARGIN_LEFT;// = 39;
    private static int BTN_LEFT_MARGIN;
    private static int BTN_RIGHT_MARGIN;
    private static int BTN_TOP_MARGIN;
    private static int BTN_BOTTOM_MARGIN;
    
    private static float CELL_TEXT_SIZE;
    
	private static final String TAG = "CalendarView"; 
	private Calendar mRightNow = null;
    private Drawable mWeekTitle = null;
    private Drawable mBtnLeft = null;
    private Drawable mBtnRight = null;
    //private Cell mToday = null;
    private Cell[][] mCells = new Cell[6][7];
    private OnDrawableTouchListener mOnDrawableTouchListener = null;
    private OnCellTouchListener mOnCellTouchListener = null;
    MonthDisplayHelper mHelper;
    //Drawable mDecoration = null;
    private Context mContext;
    
    private List<Integer> list = null;
    
	public interface OnCellTouchListener {
    	public void onTouch(Cell cell);
    }
	public interface OnDrawableTouchListener {
		public void onTouch(boolean left, boolean right);
	}

	public CalendarView(Context context) {
		this(context, null);
	}
	
	public CalendarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CalendarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		//mDecoration = context.getResources().getDrawable(R.drawable.icon_circle);
		//mCircle = context.getResources().getDrawable(R.drawable.icon_circle);
		initCalendarView();
	}
	
	private void initCalendarView() {
		mRightNow = Calendar.getInstance();
		// prepare static vars
		Resources res = getResources();
		WEEK_TOP_MARGIN  = (int) res.getDimension(R.dimen.week_top_margin);
		WEEK_LEFT_MARGIN = (int) res.getDimension(R.dimen.week_left_margin);
		WEEK_RIGHT_MARGIN = (int) res.getDimension(R.dimen.week_right_margin);
		WEEK_TITLE_LEFT_MARGIN = (int) res.getDimension(R.dimen.week_title_left_margin);
		WEEK_TITLE_RIGHT_MARGIN = (int) res.getDimension(R.dimen.week_title_right_margin);
		
		BTN_LEFT_MARGIN = (int) res.getDimension(R.dimen.btn_left_margin);
		BTN_RIGHT_MARGIN = (int) res.getDimension(R.dimen.btn_right_margin);
		BTN_TOP_MARGIN = (int) res.getDimension(R.dimen.btn_top_margin);
		BTN_BOTTOM_MARGIN = (int) res.getDimension(R.dimen.btn_bottom_margin);
		
		CELL_WIDTH = (int) res.getDimension(R.dimen.cell_width);
		CELL_HEIGH = (int) res.getDimension(R.dimen.cell_heigh);
		CELL_MARGIN_TOP = (int) res.getDimension(R.dimen.cell_margin_top);
		CELL_MARGIN_LEFT = (int) res.getDimension(R.dimen.cell_margin_left);

		CELL_TEXT_SIZE = res.getDimension(R.dimen.cell_text_size);
		// set background
		setImageResource(R.drawable.bg_calendar);
		mWeekTitle = res.getDrawable(R.drawable.calendar_week);
		mBtnLeft = res.getDrawable(R.drawable.arrow_cellar_left);
		mBtnRight = res.getDrawable(R.drawable.arrow_cellar_right);
		
		mHelper = new MonthDisplayHelper(mRightNow.get(Calendar.YEAR), mRightNow.get(Calendar.MONTH));

		//goToday();
    }
	
	private void initCells() {
	    class _calendar {
	    	public int day;
	    	public boolean thisMonth;
	    	public _calendar(int d, boolean b) {
	    		day = d;
	    		thisMonth = b;
	    	}
	    	public _calendar(int d) {
	    		this(d, false);
	    	}
	    };
	    _calendar tmp[][] = new _calendar[6][7];
	    
	    for(int i=0; i<tmp.length; i++) {
	    	int n[] = mHelper.getDigitsForRow(i);
	    	for(int d=0; d<n.length; d++) {
	    		if(mHelper.isWithinCurrentMonth(i,d))
	    			tmp[i][d] = new _calendar(n[d], true);
	    		else
	    			tmp[i][d] = new _calendar(n[d]);
	    		
	    	}
	    }

	    /*Calendar today = Calendar.getInstance();
	    int thisDay = 0;
	    mToday = null;

	    if(mHelper.getYear()==today.get(Calendar.YEAR) && mHelper.getMonth()==today.get(Calendar.MONTH)) {
	    	System.out.println("Year:"+String.valueOf(mHelper.getYear())+"   Month:"+String.valueOf(mHelper.getMonth()));
	    	thisDay = today.get(Calendar.DAY_OF_MONTH);
	    	System.out.println("Day:"+String.valueOf(today.get(Calendar.DAY_OF_MONTH)));
	    }*/
	    
		// build cells
		Rect Bound = new Rect(CELL_MARGIN_LEFT, CELL_MARGIN_TOP, CELL_WIDTH+CELL_MARGIN_LEFT, CELL_HEIGH+CELL_MARGIN_TOP);
		for(int week=0; week<mCells.length; week++) {
			for(int day=0; day<mCells[week].length; day++) {
				if(tmp[week][day].thisMonth) {
					if(day==0 || day==6 )
						mCells[week][day] = new RedCell(mContext, tmp[week][day].day, new Rect(Bound), CELL_TEXT_SIZE, true);
					else 
						mCells[week][day] = new Cell(mContext,tmp[week][day].day, new Rect(Bound), CELL_TEXT_SIZE, true);
				} else {
					mCells[week][day] = new GrayCell(mContext, tmp[week][day].day, new Rect(Bound), CELL_TEXT_SIZE, false);
				}
				
				Bound.offset(CELL_WIDTH, 0); // move to next column 
				
				// get today
				//if(tmp[week][day].day==thisDay && tmp[week][day].thisMonth) {
				//	mToday = mCells[week][day];
				//	mDecoration.setBounds(mToday.getBound());
				//}
			}
			Bound.offset(0, CELL_HEIGH); // move to next row and first column
			Bound.left = CELL_MARGIN_LEFT;
			Bound.right = CELL_MARGIN_LEFT+CELL_WIDTH;
		}		
	}
	
	@Override
	public void onLayout(boolean changed, int left, int top, int right, int bottom) {
		Rect re = getDrawable().getBounds();
		WEEK_LEFT_MARGIN = CELL_MARGIN_LEFT = (right-left - re.width()) / 2;
		mWeekTitle.setBounds(WEEK_LEFT_MARGIN+WEEK_TITLE_LEFT_MARGIN, WEEK_TOP_MARGIN, 
				WEEK_LEFT_MARGIN+mWeekTitle.getMinimumWidth()+WEEK_TITLE_RIGHT_MARGIN, WEEK_TOP_MARGIN+mWeekTitle.getMinimumHeight());
		mBtnLeft.setBounds(WEEK_LEFT_MARGIN+BTN_LEFT_MARGIN, WEEK_TOP_MARGIN+BTN_TOP_MARGIN, 
				WEEK_LEFT_MARGIN+BTN_RIGHT_MARGIN+mBtnLeft.getMinimumWidth(), WEEK_TOP_MARGIN+BTN_BOTTOM_MARGIN);
		mBtnRight.setBounds(WEEK_RIGHT_MARGIN+BTN_LEFT_MARGIN, WEEK_TOP_MARGIN+BTN_TOP_MARGIN, 
				WEEK_RIGHT_MARGIN+BTN_RIGHT_MARGIN+mBtnRight.getMinimumWidth(), WEEK_TOP_MARGIN+BTN_BOTTOM_MARGIN);
		initCells();
		super.onLayout(changed, left, top, right, bottom);
	}
	
    public void setTimeInMillis(long milliseconds) {
    	mRightNow.setTimeInMillis(milliseconds);
    	if (list!=null)
    		drawDate();
    	initCells();
    	this.invalidate();
    }
        
    public int getYear() {
    	return mHelper.getYear();
    }
    
    public int getMonth() {
    	return mHelper.getMonth();
    }
    
    public void nextMonth() {
    	mHelper.nextMonth();
    	initCells();
    	if (list!=null)
    		drawDate();
    	invalidate();
    }
    
    public void previousMonth() {
    	mHelper.previousMonth();
    	initCells();
    	if (list!=null)
    		drawDate();
    	invalidate();
    }
    
    public boolean hitDay(int year, int month, int day)
    {
    	return (getYear()==year && this.getMonth()==month );
    }
    
    public boolean firstDay(int day) {
    	return day==1;
    }
    
    public boolean lastDay(int day) {
    	return mHelper.getNumberOfDaysInMonth()==day;
    }
    
    public boolean ClickPrevious(Drawable button)
    {
    	return button.equals(mBtnLeft);
    }
    
    public boolean ClickNext(Drawable button)
    {
    	return button.equals(mBtnRight);
    }
    
    public void goToday() {
    	Calendar cal = Calendar.getInstance();
    	mHelper = new MonthDisplayHelper(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
    	initCells();
    	invalidate();
    }
    
    public Calendar getDate() {
    	return mRightNow;
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	if(mOnDrawableTouchListener!=null)
    	{
    		if (hitPrevious((int)event.getX(), (int)event.getY()))
    		{
    			mOnDrawableTouchListener.onTouch(true,false);
    		}
    		else if (hitNext((int)event.getX(), (int)event.getY()))
    		{
    			mOnDrawableTouchListener.onTouch(false,true);
    		}
    	}

    	if(mOnCellTouchListener!=null){
	    	for(Cell[] week : mCells) {
				for(Cell day : week) {
					if(day.hitTest((int)event.getX(), (int)event.getY())) {
						invalidate();
						System.out.print("Day:");
						System.out.println(day.mDayOfMonth);
						System.out.print("hittest:");
						System.out.println(day.mEvent);
						mOnCellTouchListener.onTouch(day);
					}						
				}
			}
    	}
    	return super.onTouchEvent(event);
    }
  
    public void setOnCellTouchListener(OnCellTouchListener p) {
		mOnCellTouchListener = p;
	}
    public void setOnDrawableTouchListener(OnDrawableTouchListener q)
    {
    	mOnDrawableTouchListener = q;
    }

	@Override
	protected void onDraw(Canvas canvas) {
		// draw background
		super.onDraw(canvas);
		mWeekTitle.draw(canvas);
		mBtnLeft.draw(canvas);
		mBtnRight.draw(canvas);
		// draw cells
		for(Cell[] week : mCells) {
			for(Cell day : week) {
				day.draw(canvas);	
				day.drawCircle(canvas);
			}
		}
		
		/* draw today
		if(mDecoration!=null && mToday!=null) {
			mDecoration.draw(canvas);
		}*/

	}
	
	public class GrayCell extends Cell {
		public GrayCell(Context c, int dayOfMon, Rect rect, float s, boolean b) {
			super(c, dayOfMon, rect, s, b);
			mPaint.setColor(Color.LTGRAY);
		}			
	}
	
	private class RedCell extends Cell {
		public RedCell(Context c, int dayOfMon, Rect rect, float s, boolean b) {
			super(c, dayOfMon, rect, s, b);
			mPaint.setColor(0xdddd0000);
		}			
		
	}
	
	public void drawDate(List<Integer> dateList)
	{
		list = dateList;
		if (list != null)
			drawDate();
		invalidate();
	}
	
	public boolean hitPrevious(int x, int y) {
		Rect rect = mBtnLeft.copyBounds();
		rect.bottom+=8;
		rect.top-=8;
		rect.right+=8;
		rect.left-=8;
		return rect.contains(x, y); 
	}
	
	public boolean hitNext(int x, int y) {
		Rect rect = mBtnRight.copyBounds();
		rect.bottom+=8;
		rect.top-=8;
		rect.right+=8;
		rect.left-=8;
		return rect.contains(x, y); 
	}
	
	public void drawDate()
	{
		int year,month,day,num;
		num = list.size()/3;
		for (int k=0; k<num;k++)
		{
			year = list.get(k*3);
			month = list.get(k*3+1);
			day = list.get(k*3+2);
			if(year == getYear() && month == getMonth()+1)
			{
				for(Cell[] cweek : mCells) {
					for(Cell cday : cweek) {
						if(cday.IsThisMonth() && day == cday.getDayOfMonth())
							cday.setEvent();
					}
				}
			}
		}
		
	}
}
