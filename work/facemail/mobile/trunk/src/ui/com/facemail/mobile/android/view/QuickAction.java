package com.facemail.mobile.android.view;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/23/13
 * Time: 7:36 AM
 * To change this template use File | Settings | File Templates.
 */

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facemail.mobile.android.R;
import com.facemail.mobile.android.db.conf.Config;
import com.facemail.mobile.android.util.SystemUtil;

import java.util.ArrayList;



/**
 * Popup window, shows action list as icon and text (QuickContact / Twitter app).
 *
 */
public class QuickAction extends CustomPopupWindow {
    private final View root;
    private final ImageView mArrowUp;
    private final ImageView mArrowDown;
    private final Animation mTrackAnim;
    private final LayoutInflater inflater;
    private final Context context;

    protected static final int ANIM_GROW_FROM_LEFT = 1;
    protected static final int ANIM_GROW_FROM_RIGHT = 2;
    protected static final int ANIM_GROW_FROM_CENTER = 3;
    protected static final int ANIM_AUTO = 4;

    private int animStyle;
    private boolean animateTrack;
    private ViewGroup mTrack;
    private ArrayList<ActionItem> actionList;

    /**
     * Constructor
     *
     * @param anchor  {@link View} on where the popup should be displayed
     */
    public QuickAction(View anchor) {
        super(anchor);

        actionList	= new ArrayList<ActionItem>();
        context		= anchor.getContext();
        inflater 	= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        root		= (ViewGroup) inflater.inflate(R.layout.face_quick_action_popup, null);

        mArrowDown 	= (ImageView) root.findViewById(R.id.arrow_down);
        mArrowUp 	= (ImageView) root.findViewById(R.id.arrow_up);

        setContentView(root);

        mTrackAnim 	= AnimationUtils.loadAnimation(anchor.getContext(), R.anim.rail);

        mTrackAnim.setInterpolator(new Interpolator() {
            public float getInterpolation(float t) {
                // Pushes past the target area, then snaps back into place.
                // Equation for graphing: 1.2-((x*1.6)-1.1)^2
                final float inner = (t * 1.55f) - 1.1f;

                return 1.2f - inner * inner;
            }
        });

        mTrack 			= (ViewGroup) root.findViewById(R.id.tracks);
        animStyle		= ANIM_AUTO;
        animateTrack	= true;
    }

    /**
     * Animate track
     *
     * @param animateTrack flag to animate track
     */
    public void animateTrack(boolean animateTrack) {
        this.animateTrack = animateTrack;
    }

    /**
     * Set animation style
     *
     * @param animStyle animation style, default is set to ANIM_AUTO
     */
    public void setAnimStyle(int animStyle) {
        this.animStyle = animStyle;
    }

    /**
     * Add action item
     *
     * @param action  {@link ActionItem}
     */
    public void addActionItem(ActionItem action) {
        actionList.add(action);
    }

    public void clear(){
        for(ActionItem action:actionList){
            action.setOnClickListener(null);
            action = null;
        }
        actionList.clear();
    }

    /**
     * Show popup window
     */
    public void show () {
        this.dismiss();
        preShow();

        int[] location 		= new int[2];

        anchor.getLocationOnScreen(location);

        Rect anchorRect 	= new Rect(location[0], location[1], location[0] + anchor.getWidth(), location[1]
                + anchor.getHeight());

        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        root.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        int rootWidth 		= root.getMeasuredWidth();
        int rootHeight 		= root.getMeasuredHeight();

        int screenWidth 	= windowManager.getDefaultDisplay().getWidth();
        int screenHeight 	= windowManager.getDefaultDisplay().getHeight();

        int xPos 			= (screenWidth - rootWidth) / 2;
        int yPos	 		= anchorRect.bottom;
//		int yPos	 		= anchorRect.top - rootHeight;

//		boolean onTop		= true;
//
//		// display on bottom
//		if (rootHeight > anchorRect.top) {
//			yPos 	= anchorRect.bottom;
//			onTop	= false;
//		}

        boolean onTop = false;
        if (yPos + anchorRect.height() > screenHeight) {
            yPos 	= anchorRect.top - rootHeight;
            onTop	= true;
        }


        showArrow(((onTop) ? R.id.arrow_down : R.id.arrow_up), anchorRect.centerX());

        setAnimationStyle(screenWidth, anchorRect.centerX(), onTop);

        createActionList();

        window.showAtLocation(this.anchor, Gravity.NO_GRAVITY, xPos, yPos);

        if (animateTrack) mTrack.startAnimation(mTrackAnim);
    }

    /**
     * Set animation style
     *
     * @param screenWidth Screen width
     * @param requestedX distance from left screen
     * @param onTop flag to indicate where the popup should be displayed. Set TRUE if displayed on top of anchor and vice versa
     */
    private void setAnimationStyle(int screenWidth, int requestedX, boolean onTop) {
        int arrowPos = requestedX - mArrowUp.getMeasuredWidth()/2;

        switch (animStyle) {
            case ANIM_GROW_FROM_LEFT:
                window.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Left : R.style.Animations_PopDownMenu_Left);
                break;

            case ANIM_GROW_FROM_RIGHT:
                window.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Right : R.style.Animations_PopDownMenu_Right);
                break;

            case ANIM_GROW_FROM_CENTER:
                window.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Center : R.style.Animations_PopDownMenu_Center);
                break;

            case ANIM_AUTO:
                if (arrowPos <= screenWidth/4) {
                    window.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Left : R.style.Animations_PopDownMenu_Left);
                } else if (arrowPos > screenWidth/4 && arrowPos < 3 * (screenWidth/4)) {
                    window.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Center : R.style.Animations_PopDownMenu_Center);
                } else {
                    window.setAnimationStyle((onTop) ? R.style.Animations_PopDownMenu_Right : R.style.Animations_PopDownMenu_Right);
                }

                break;
        }
    }

    /**
     * Create action list
     *
     */
    private void createActionList() {
        if (mTrack.getChildCount() > 2) {
            return;
        }
        View view;
        String title;
        Drawable icon;
        View.OnClickListener listener;
        int index = 1;
        int ViewID= Config.QickActionID.REFRESH;

        for (int i = 0; i < actionList.size(); i++) {
            title 		= actionList.get(i).getTitle();
            icon 		= actionList.get(i).getIcon();
            listener	= actionList.get(i).getListener();
            ViewID      = actionList.get(i).getActionID();
            view 		= getActionItem(title, icon, listener,ViewID);

            view.setFocusable(true);
            view.setClickable(true);
            mTrack.addView(view, index);
            index++;
//			ImageView divider = new ImageView(context);
//			divider.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.divider_new));
//			mTrack.addView(divider, index);
//			index++;
        }
    }

    /**
     * Get action item {@link View}
     *
     * @param title action item title
     * @param icon {@link Drawable} action item icon
     * @param listener {@link View.OnClickListener} action item listener
     * @return action item {@link View}
     */
    private View getActionItem(String title, Drawable icon, final View.OnClickListener listener,int ViewID) {
        LinearLayout container	= (LinearLayout) inflater.inflate(R.layout.face_quick_action_item_popup, null);
        ImageView img 			= (ImageView) container.findViewById(R.id.icon);
        TextView text 			= (TextView) container.findViewById(R.id.title);

        if (icon != null) {
            img.setImageDrawable(icon);
        } else {
            img.setVisibility(View.GONE);
        }

        if (title != null) {
            text.setText(title);
        } else {
            text.setVisibility(View.GONE);
        }

        if (listener != null) {
            final View.OnClickListener click = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    SystemUtil.log("guy`s id:"+v.getX());
                    listener.onClick(v);
                    QuickAction.this.dismiss();
                }
            };
            container.setOnClickListener(click);

            img.setOnClickListener(listener);
            img.setTag(ViewID);
        }

        return container;
    }

    /**
     * Show arrow
     *
     * @param whichArrow arrow type resource id
     * @param requestedX distance from left screen
     */
    private void showArrow(int whichArrow, int requestedX) {
        final View showArrow = (whichArrow == R.id.arrow_up) ? mArrowUp : mArrowDown;
        final View hideArrow = (whichArrow == R.id.arrow_up) ? mArrowDown : mArrowUp;

        final int arrowWidth = mArrowUp.getMeasuredWidth();

        //showArrow.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams)showArrow.getLayoutParams();

        param.leftMargin = requestedX - arrowWidth / 2;

        hideArrow.setVisibility(View.INVISIBLE);
    }
}