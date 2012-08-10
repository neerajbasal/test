package com.android.animation.lib;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;



/**
 * @author Thomas
 * 
 *         Manages opening and closing of left and right slider. An activity
 *         that uses this class MUST use a layout that implements
 *         slider_header_to_include.xml!
 */
public class SliderHelper implements OnClickListener, OnTouchListener {

	private static final int SLIDING_TIME = 500;
	private ImageButton buttonSliderLeft;
	private ImageButton buttonSliderRight;
	private LinearLayout sliderContainerLeft;
	private HorizontalScrollView sliderScrollerLeft;
	private HorizontalScrollView sliderScrollerRight;
	private TextView sliderTitle;
	private TextView sliderSubTitle;
	// private LinearLayout sliderTitleContainer;

	private int residLefIconOpen;
	private int residLefIconClosed;
	private int residRightIconOpen;
	private int residRightIconClosed;

	private boolean leftSliderOpen = false;
	private boolean rightSliderOpen = false;

	private Button time_now;
	private Button time_prime;
	private Button time_other;

//	private final Activity activity;
	private Activity mActivity;
	private RelativeLayout leftSliderButtonContainer;
	private RelativeLayout rightSliderButtonContainer;

	private boolean isLeftGesture = false;
   private View convertView =null;
   private LinearLayout linearLayout =null;
	public SliderHelper(Activity activity) {
		this.mActivity = activity;
		LayoutInflater inflater = (LayoutInflater) this.mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.slider_header_to_include, null);
		linearLayout = (LinearLayout)convertView.findViewById(R.id.SliderContainerLeft);
		initViews();

		
	}
      
	public View getView(){
		return convertView;
	}
	
	/**
	 * Use this method if you use a single layout file that does not change for
	 * the content of the left container. If you want to add content dynamically
	 * use {@link #addButtonToLeftContainer(String)} instead. Sets the content
	 * of the left slider when opened. To see how to inflate a layout check
	 * {@link #setRightContainerAsTime}.
	 * 
	 * @param residLefIconOpen
	 *            the icon when the slider is closed
	 * @param residLefIconClosed
	 *            the icon when the slider is opened
	 * @param v
	 *            the view that contains the content that is displayed in the
	 *            slider
	 */
	public void setIconsForLeftContainer(int residLefIconOpen,
			int residLefIconClosed) {
		this.residLefIconOpen = residLefIconOpen;
		this.residLefIconClosed = residLefIconClosed;

		buttonSliderLeft.setBackgroundDrawable(mActivity.getResources()
				.getDrawable(residLefIconClosed));
		
	}
	public void setIconsForRightContainer(int residRightIconOpen,
			int residRightIconClosed) {
		this.residRightIconOpen = residRightIconOpen;
		this.residRightIconClosed = residRightIconClosed;

		buttonSliderRight.setBackgroundDrawable(mActivity.getResources()
				.getDrawable(residRightIconClosed));
		
	}
	/**
	 * Use this method if you use a single layout file that does not change for
	 * the content of the left container. If you want to add content dynamically
	 * use {@link #addButtonToLeftContainer(String)} instead. Sets the content
	 * of the left slider when opened. To see how to inflate a layout check
	 * {@link #setRightContainerAsTime}.
	 * 
	 * @param residLefIconOpen
	 *            the icon when the slider is closed
	 * @param residLefIconClosed
	 *            the icon when the slider is opened
	 * @param v
	 *            the view that contains the content that is displayed in the
	 *            slider
	 */
	@Deprecated
	private void setContentForLeftContainer(int residLefIconOpen,
			int residLefIconClosed, View v) {
		this.residLefIconOpen = residLefIconOpen;
		this.residLefIconClosed = residLefIconClosed;

		sliderScrollerLeft.removeAllViews();
		sliderScrollerLeft.addView(v);
		buttonSliderLeft.setBackgroundDrawable(mActivity.getResources()
				.getDrawable(residLefIconClosed));
	}

	/**
	 * Can only be used if you do not use setContentForLeftContainer. Use this
	 * method if you want to add content dynamically to the left container. If
	 * you use a layout file that does not change then use
	 * {@link #setContentForLeftContainer(int, int, View)} instead.
	 * 
	 * @param text
	 */
	public Button addButtonToLeftContainer(String text) {
		LayoutInflater inflator = mActivity.getLayoutInflater();
		Button singleButton = (Button) inflator.inflate(
				R.layout.slider_single_button, null);
		singleButton.setText(text);
		sliderContainerLeft.addView(singleButton);
		return singleButton;
	}

	public void removeAllButtonsFromLeftContainer() {
		sliderContainerLeft.removeAllViews();
	}

	/**
	 * The right container is always filled with a time menu. That's why we make
	 * this method private and call it in the constructor.
	 * */
	private void setRightContainerAsTime() {

		
		buttonSliderRight.setBackgroundDrawable(mActivity.getResources()
				.getDrawable(residLefIconClosed));

		
	}

	private void initViews() {
		// Initialize variables
		sliderContainerLeft = (LinearLayout) convertView
				.findViewById(R.id.SliderContainerLeft);
		sliderScrollerLeft = (HorizontalScrollView) convertView
				.findViewById(R.id.SliderScrollerLeft);
		sliderScrollerRight = (HorizontalScrollView) convertView
				.findViewById(R.id.SliderScrollerRight);

		buttonSliderLeft = (ImageButton) convertView
				.findViewById(R.id.ButtonSliderLeft);
		leftSliderButtonContainer = (RelativeLayout) convertView
				.findViewById(R.id.leftSliderButtonContainer);
		buttonSliderRight = (ImageButton) convertView
				.findViewById(R.id.ButtonSliderRight);
		rightSliderButtonContainer = (RelativeLayout) convertView
				.findViewById(R.id.rightSliderButtonContainer);

		sliderTitle = (TextView) convertView.findViewById(R.id.SliderTitle);
		sliderSubTitle = (TextView) convertView.findViewById(R.id.SliderSubTitle);

		// Set the subTitle to the current date and time
		refreshDateInSubtitle(new Date());

		// Bind OnClickListener
		buttonSliderLeft.setOnClickListener(this);
		buttonSliderRight.setOnClickListener(this);

		// buttonSliderLeft.setOnTouchListener(this);

	}

	public void refreshDateInSubtitle(Date date) {
		DateFormat dfmt = new SimpleDateFormat("dd.MM.yyyy H:mm");
		sliderSubTitle.setText(dfmt.format(date));
	}

	public void setListTitle(String title) {
		sliderTitle.setText(title);
	}

	/**
	 * @param leftSlider
	 *            : if true set left slider, else right slider
	 */
	private void updateSlider(boolean leftSlider) {
		if (leftSlider) {
			if (leftSliderOpen) {
				if (rightSliderOpen) {
					animateCloseRightScrollbar();
					rightSliderOpen = false;
				}
				buttonSliderLeft.setBackgroundDrawable(mActivity.getResources()
						.getDrawable(residLefIconOpen));
				animateOpenLeftScrollbar();
			} else {
				buttonSliderLeft.setBackgroundDrawable(mActivity.getResources()
						.getDrawable(residLefIconClosed));
				animateCloseLeftScrollbar();
			}
		} else {
			if (rightSliderOpen) {
				if (leftSliderOpen) {
					animateCloseLeftScrollbar();
					leftSliderOpen = false;
				}
				buttonSliderRight.setBackgroundDrawable(mActivity.getResources()
						.getDrawable(residRightIconOpen));
				animateOpenRightScrollbar();
			} else {
				buttonSliderRight.setBackgroundDrawable(mActivity.getResources()
						.getDrawable(residRightIconClosed));
				animateCloseRightScrollbar();
			}
		}
	}

	private void animateOpenLeftScrollbar() {
		final AnimationSet sliderAnimation = new AnimationSet(true);
		LayoutParams params = new RelativeLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.LEFT_OF, R.id.invisibleButtonSliderRight);
		params.rightMargin = 10;
		leftSliderButtonContainer.setLayoutParams(params);
		int leftPos = mActivity.getResources().getDisplayMetrics().widthPixels
				- params.rightMargin - buttonSliderLeft.getWidth()
				- buttonSliderRight.getWidth();

		TranslateAnimation a = new TranslateAnimation(Animation.ABSOLUTE,
				-leftPos, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
				Animation.ABSOLUTE, 0);
		a.setDuration(SLIDING_TIME);
		sliderAnimation.addAnimation(a);

		leftSliderButtonContainer.startAnimation(sliderAnimation);
	}

	private void animateCloseLeftScrollbar() {
		final AnimationSet sliderAnimation = new AnimationSet(true);
		sliderAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				LayoutParams params = new RelativeLayout.LayoutParams(
						android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
						android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
				params.addRule(RelativeLayout.ALIGN_RIGHT,
						R.id.invisibleButtonSliderLeft);
				leftSliderButtonContainer.setLayoutParams(params);

				/**
				 * bugfix to avoid flickering at the end of animation
				 */
				animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f);
				animation.setDuration(1);
				leftSliderButtonContainer.startAnimation(animation);
			}
		});
		int currentLeft = leftSliderButtonContainer.getRight()
				- buttonSliderLeft.getWidth();
		TranslateAnimation a = new TranslateAnimation(Animation.ABSOLUTE, 0,
				Animation.ABSOLUTE, -currentLeft, Animation.ABSOLUTE, 0,
				Animation.ABSOLUTE, 0);
		a.setDuration(SLIDING_TIME);
		sliderAnimation.addAnimation(a);

		leftSliderButtonContainer.startAnimation(sliderAnimation);
	}

	private void animateOpenRightScrollbar() {
		final AnimationSet sliderAnimation = new AnimationSet(true);

		LayoutParams params = new RelativeLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.RIGHT_OF, R.id.invisibleButtonSliderLeft);
		params.leftMargin = 10;
		// params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		rightSliderButtonContainer.setLayoutParams(params);
		int leftPos = mActivity.getResources().getDisplayMetrics().widthPixels
				- params.leftMargin - buttonSliderRight.getWidth()
				- buttonSliderLeft.getWidth();
		TranslateAnimation a = new TranslateAnimation(Animation.ABSOLUTE,
				leftPos, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
				Animation.ABSOLUTE, 0);
		a.setDuration(SLIDING_TIME);
		sliderAnimation.addAnimation(a);

		rightSliderButtonContainer.startAnimation(sliderAnimation);
	}

	private void animateCloseRightScrollbar() {
		final AnimationSet sliderAnimation = new AnimationSet(true);
		sliderAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				LayoutParams params = new RelativeLayout.LayoutParams(
						android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
						android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
				params.addRule(RelativeLayout.ALIGN_LEFT,
						R.id.invisibleButtonSliderRight);
				rightSliderButtonContainer.setLayoutParams(params);

				/**
				 * bugfix to avoid flickering at the end of animation
				 */
				animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f);
				animation.setDuration(1);
				rightSliderButtonContainer.startAnimation(animation);
			}
		});
		int currentLeft = buttonSliderLeft.getWidth() + 10;
		int leftPos = mActivity.getResources().getDisplayMetrics().widthPixels
				- currentLeft - buttonSliderRight.getWidth();
		TranslateAnimation a = new TranslateAnimation(Animation.ABSOLUTE, 0,
				Animation.ABSOLUTE, leftPos, Animation.ABSOLUTE, 0,
				Animation.ABSOLUTE, 0);
		a.setDuration(SLIDING_TIME);
		sliderAnimation.addAnimation(a);

		rightSliderButtonContainer.startAnimation(sliderAnimation);
	}

	@Override
	public void onClick(View v) {
		if (v == buttonSliderLeft) {
			leftSliderOpen = !leftSliderOpen;
			updateSlider(true);
		} else if (v == buttonSliderRight) {
			rightSliderOpen = !rightSliderOpen;
			updateSlider(false);
		}
	}

	public Button getButtonTimeNow() {
		return time_now;
	}

	public Button getButtonTimeOther() {
		return time_other;
	}

	public Button getButtonPrimeTime() {
		return time_prime;
	}

	

	public void closeLeftSlider() {
		/* close left slider after selecting time */
		leftSliderOpen = false;
		updateSlider(true);
	}

	int originalX;
	int originalY;
	int X = 0;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		// int oldX = params.x;

		int oldX = X;
		X = (int) event.getX();
		int currentMarginX = 0;
		int leftPos = 0;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			originalX = X;

			// X = (int) event.getX();
			currentMarginX = ((LayoutParams) leftSliderButtonContainer
					.getLayoutParams()).rightMargin;

			break;

		case MotionEvent.ACTION_UP:
			if (isLeftGesture) {
				leftSliderOpen = false;
				updateSlider(true);
			} else {
				leftSliderOpen = true;
				updateSlider(true);
			}
			break;

		case MotionEvent.ACTION_MOVE:
			// Log.d("oldX", "" + oldX + " " + X + " " + (int) event.getX());
			if (X < oldX) {
				isLeftGesture = true;
			} else {
				isLeftGesture = false;
			}

			// ImageButton ib = (ImageButton) activity
			// .findViewById(R.id.invisibleButtonSliderLeft);

			LayoutParams params = (LayoutParams) leftSliderButtonContainer
					.getLayoutParams();
			// params.addRule(RelativeLayout.LEFT_OF,
			// R.id.invisibleButtonSliderRight);

			// params.setMargins(0, 0, params.rightMargin - oldX, 0);
			// currentMarginX = currentMarginX - oldX;
			if (params.rightMargin >= 68 && params.rightMargin <= 262) {
				params.rightMargin = params.rightMargin - (X - oldX);
				// params.setMargins(0, 0, params.rightMargin, 0);
				leftSliderButtonContainer.setLayoutParams(params);
			}

			// ib.setLayoutParams(params);

			break;

		}
		return false;
	}
	
	public void setListInSlider(final ArrayList<Object> arrayList,boolean isImage){
//		LinearLayout listLayout = linearL;
		
		for(int i=0;i<arrayList.size();i++){
			if(isImage){
			final ImageView iv = new ImageView(mActivity);
			iv.setId(i);
//			iv.setTag(galId);
			// iv.setAdjustViewBounds(true);
			iv.setLayoutParams(new LinearLayout.LayoutParams(50, 40));
			iv.setImageResource((Integer)arrayList.get(i));
			iv.setPadding(2, 0, 2, 0);
			iv.setOnClickListener(this);
			linearLayout.addView(iv);
//			iv.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					imageV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
//					imageV.setImageResource((Integer)arrayList.get(iv.getId()));
//				}
//			});
			}else{
				TextView iv = new TextView(mActivity);
				iv.setId(i);
//				iv.setTag(galId);
				// iv.setAdjustViewBounds(true);
				iv.setLayoutParams(new LinearLayout.LayoutParams(50, 40));
				iv.setText((String)arrayList.get(i));
				iv.setPadding(2, 0, 2, 0);
				iv.setOnClickListener(this);
				linearLayout.addView(iv);
			}
		}
	}
}