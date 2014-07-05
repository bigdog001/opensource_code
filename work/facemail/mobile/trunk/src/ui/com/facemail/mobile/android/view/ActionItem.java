package com.facemail.mobile.android.view;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/22/13
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActionItem {
    private Drawable icon;
    private String title;
    private View.OnClickListener listener;
    private int actionID;

    public int getActionID() {
        return actionID;
    }

    public void setActionID(int actionID) {
        this.actionID = actionID;
    }

    /**
     * Constructor
     *
     * @param icon {@link Drawable} action icon
     */
    public ActionItem(Drawable icon, String title, View.OnClickListener listener,int id) {
        this.icon = icon;
        this.title = title;
        this.listener = listener;
        this.actionID=id;
    }

    /**
     * Set action title
     *
     * @param title action title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get action title
     *
     * @return action title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Set action icon
     *
     * @param icon {@link Drawable} action icon
     */
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    /**
     * Get action icon
     * @return  {@link Drawable} action icon
     */
    public Drawable getIcon() {
        return this.icon;
    }

    /**
     * Set on click listener
     *
     * @param listener on click listener {@link View.OnClickListener}
     */
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * Get on click listener
     *
     * @return on click listener {@link View.OnClickListener}
     */
    public View.OnClickListener getListener() {
        return this.listener;
    }
}