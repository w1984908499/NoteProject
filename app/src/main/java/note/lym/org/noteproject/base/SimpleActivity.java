package note.lym.org.noteproject.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import butterknife.ButterKnife;
import note.lym.org.noteproject.R;
import note.lym.org.noteproject.manage.ActivityManage;
import note.lym.org.noteproject.utils.StatusBarCompat;

/**
 * 无MVP的activity
 */
public abstract class SimpleActivity extends BaseRunTimePermission {


    protected Activity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        StatusBarCompat.compat(this, ContextCompat.getColor(this, R.color.status_bar_color));
        //竖屏锁定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        mContext = this;
        initEventAndData();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract int getLayout();

    protected abstract void initEventAndData();

    /**
     * 退出程序
     */
    protected void exitApp() {
        ActivityManage.getInstance().backAllActivityToStack();
        android.os.Process.killProcess(Process.myPid());
    }



}
