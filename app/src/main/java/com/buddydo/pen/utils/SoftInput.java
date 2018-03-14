package com.buddydo.pen.utils;import android.app.Activity;import android.content.BroadcastReceiver;import android.content.Context;import android.content.Intent;import android.content.IntentFilter;import android.os.Bundle;import android.os.Handler;import android.os.Message;import android.os.ResultReceiver;import android.util.Log;import android.view.View;import android.view.inputmethod.InputMethodManager;public class SoftInput {    private static final String TAG = "SoftInput";    public final static int SOFT_INPUT_DELIMITER_HIDE = 0;    public final static int SOFT_INPUT_READY_HIDE = 1;    public final static int SOFT_INPUT_HIDDING = 2;    public final static int SOFT_INPUT_HIDDEN = 3;    public final static int SOFT_INPUT_DELIMITER_SHOW = 4;    public final static int SOFT_INPUT_READY_SHOW = 5;    public final static int SOFT_INPUT_SHOWING = 6;    public final static int SOFT_INPUT_SHOWN = 7;    public interface Listener {        void onSoftInputStateChanged(int state);    }    private Listener mListener;    private int mState = SOFT_INPUT_HIDDEN;    private Context mContext;    private Handler mHandler = new Handler() {        @Override        public void handleMessage(Message msg) {            mHandler.removeMessages(1);            if ((mState & SOFT_INPUT_DELIMITER_HIDE) != 0) {                setState(SOFT_INPUT_HIDDEN);            } else if ((mState & SOFT_INPUT_DELIMITER_SHOW) != 0) {                setState(SOFT_INPUT_SHOWN);            }        }    };    public SoftInput(Context context) {        mContext = context;        IntentFilter keypadFilter = new IntentFilter();        keypadFilter.addAction("ResponseAxT9Info");        mContext.registerReceiver(mInputMethodChangedReceiver, keypadFilter);    }    public void setListener(Listener listener) {        if (listener != null) {            mListener = listener;        }    }    public void close() {        mContext.unregisterReceiver(mInputMethodChangedReceiver);    }    public int getState() {        return mState;    }    private boolean showSoftInput(Activity activity, View v) {        return ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(v, 0, new ResultReceiver(null) {            @Override            protected void onReceiveResult(int resultCode, Bundle resultData) {                Log.d(TAG, "showSoftInput onReceiveResult resultCode = " + resultCode);                if (mState == SOFT_INPUT_READY_SHOW) {                    mHandler.removeMessages(1);                    setState(SOFT_INPUT_SHOWING);                    mHandler.sendEmptyMessageDelayed(1, 300);                }            }        });    }    public int show(Activity activity) {        return show(activity, null);    }    public int show(Activity activity, View v) {        if (v == null) {            v = activity.getWindow().getCurrentFocus();            if (v == null) {                return -1;            }        }        boolean ret;        if (mState != SOFT_INPUT_SHOWING) {            setState(SOFT_INPUT_READY_SHOW);            mHandler.removeMessages(1);            mHandler.sendEmptyMessageDelayed(1, 700);        }        ret = showSoftInput(activity, v);        if (ret) {            return 1;        }        return 0;    };    public void hide(Activity activity) {        hide(activity, null);    }    public void hide(Activity activity, View v) {        if (v == null) {            v = activity.getWindow().getCurrentFocus();            if (v == null) {                return;            }        }        if (mState != SOFT_INPUT_HIDDING) {            setState(SOFT_INPUT_READY_HIDE);            mHandler.removeMessages(1);            mHandler.sendEmptyMessageDelayed(1, 700);        }        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0, new ResultReceiver(null) {            @Override            protected void onReceiveResult(int resultCode, Bundle resultData) {                Log.d(TAG, "hideSoftInputFromWindow onReceiveResult resultCode = " + resultCode);                if (mState == SOFT_INPUT_READY_HIDE) {                    mHandler.removeMessages(1);                    setState(SOFT_INPUT_HIDDING);                    mHandler.sendEmptyMessageDelayed(1, 500);                }            }        });    }    public void onSizeChanged(int value) {        Log.d(TAG, "onSizeChanged value = " + value);        if (value > 0) {            if (mState == SOFT_INPUT_READY_HIDE || mState == SOFT_INPUT_HIDDING) {                mHandler.removeMessages(1);                setState(SOFT_INPUT_HIDDEN);            }        } else if (value < 0) {            if (mState == SOFT_INPUT_READY_SHOW || mState == SOFT_INPUT_SHOWING) {                mHandler.removeMessages(1);                setState(SOFT_INPUT_SHOWN);            }        }    }    private void setState(int state) {        if (mListener != null && state != mState) {            mHandler.post(new SendSoftInputStateChanged(mListener, state));        }        mState = state;    }    private class SendSoftInputStateChanged implements Runnable {        private int state;        private Listener listener;        private SendSoftInputStateChanged(Listener l, int s) {            listener = l;            state = s;        }        @Override        public void run() {            if (listener != null) {                listener.onSoftInputStateChanged(state);                listener = null;            }        }    }    private final BroadcastReceiver mInputMethodChangedReceiver = new BroadcastReceiver() {        @Override        public void onReceive(Context context, Intent intent) {            Log.d(TAG, "onReceive() - input method changed receiver");            boolean isKeypadShown = intent.getBooleanExtra("AxT9IME.isVisibleWindow", false);            if (isKeypadShown) {                if (mState != SOFT_INPUT_SHOWN) {                    setState(SOFT_INPUT_READY_SHOW);                }            } else {                setState(SOFT_INPUT_READY_HIDE);            }            Log.d(TAG, "onReceive() - input method changed receiver isKeypadShown = " + isKeypadShown);        }    };}