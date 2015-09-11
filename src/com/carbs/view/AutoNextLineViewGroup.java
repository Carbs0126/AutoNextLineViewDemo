package com.carbs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class AutoNextLineViewGroup extends ViewGroup {
	private static final String TAG = AutoNextLineViewGroup.class.getSimpleName();

	private int mMarginVertical = 2;

	private int mMarginHorizontal = 2;

	public AutoNextLineViewGroup(Context context) {
		super(context);
	}

	public AutoNextLineViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public int getMarginVertical() {
		return mMarginVertical;
	}

	public void setMarginVertical(int marginVertical) {
		this.mMarginVertical = marginVertical;
	}

	public int getMarginHorizontal() {
		return mMarginHorizontal;
	}

	public void setMarginHorizontal(int marginHorizontal) {
		this.mMarginHorizontal = marginHorizontal;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		Log.d("wang", "onMeasure");
		
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
		int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

		int paddingtop = this.getPaddingTop();
		int paddingbottom = this.getPaddingBottom();

		int right = 0;
		int bottom = paddingtop;

		for (int index = 0; index < getChildCount(); index++) {
			final View child = getChildAt(index);
			child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

			Log.d("wang", "onMeasure  index is " + index
					+ " | child.getLeft() is " + child.getLeft()
					+ " | child.getTop() is " + child.getTop()
					+ " | child.getRight() is " + child.getRight()
					+ " | child.getBottom() is " + child.getBottom()
					+ " | child.getMeasuredWidth() is " + child.getMeasuredWidth()
					+ " | child.getMeasuredHeight() is " + child.getMeasuredHeight());

			int tempButtom = child.getBottom();

			if (tempButtom > bottom) {
				bottom = tempButtom;
			}
			
			int tempRight = child.getRight();

			if (tempRight > right) {
				right = tempRight;
			}
		}

		setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
				: right, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
				: bottom + paddingbottom);
	}

	//these four parameters represent the position of this view in parent
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {

		Log.d("0910", "onLayout changed is " + changed 
				+ " | left is " + left + " | top is " + top 
				+ " | right is " + right + " | bottom is " + bottom);
		
		int groupWidth = right - left;
		int paddingleft = this.getPaddingLeft();
		int paddingtop = this.getPaddingTop();
		int paddingright = this.getPaddingRight();

		final int count = getChildCount();
		int row = 0;
		int lengthX = paddingleft;
		int lengthY = paddingtop;

		for (int index = 0; index < count; index++) {

			View child = getChildAt(index);
			int width = child.getMeasuredWidth();
			int height = child.getMeasuredHeight();
			lengthX += width + mMarginHorizontal;
			lengthY = row * (height + mMarginVertical) + mMarginVertical
					+ height + paddingtop;

			if (lengthX > groupWidth - paddingright) {

				lengthX = width + mMarginHorizontal + paddingleft;
				row++;
				lengthY = row * (height + mMarginVertical) + mMarginVertical
						+ height + paddingtop;

			}
			
			Log.d(TAG, "onLayout index is " + index
					+ " | child.getLeft() is " + child.getLeft()
					+ " | child.getTop() is " + child.getTop()
					+ " | child.getRight() is " + child.getRight()
					+ " | child.getBottom() is " + child.getBottom()
					+ " | child.getMeasuredWidth() is " + child.getMeasuredWidth()
					+ " | child.getMeasuredHeight() is " + child.getMeasuredHeight());
			
			Log.d(TAG, "onLayout index is " + index
					+ " | child.layout( " + (lengthX - width)
					+ " , " + (lengthY - height)
					+ " , " + lengthX
					+ " , " + lengthY + " );");
			//these four parameters represent the position of this view in parent
			child.layout(lengthX - width, lengthY - height, lengthX, lengthY);
		}
	}
}