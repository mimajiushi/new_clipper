package wenjie.star.new_clipper;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/**
 * 剪切板服务
 */
public class ClipboardService extends IntentService {

    private final String TAG = this.getClass().getSimpleName();
    private final String SET = "set";
    private final String GET = "get";
    private final String TEXT = "text";
    private Context context;
    private ClipboardManager clipboardManager;


    public ClipboardService() {
        super("ClipboardService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        startForeground();

        context = getApplicationContext();
        clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        context.getSystemService(Context.CLIPBOARD_SERVICE);

        switch (intent.getAction()) {
            case SET:
                Log.i(TAG, "命中set");
                set(intent);
                break;
            case GET:
                Log.i(TAG, "命中get");
                get(intent);
                break;
            default:
                Log.i(TAG, "没有命中action");
                break;
        }
    }

    /**
     * 设置剪切板
     */
    private void set(Intent intent) {
        String type = intent.getStringExtra("type");
        switch (type) {
            case TEXT:
                String text = intent.getStringExtra(TEXT);
                ClipData clip = ClipData.newPlainText("test_text", text);
                clipboardManager.setPrimaryClip(clip);
                break;
            default:
                Log.e(TAG, "没配到type参数");
        }
    }

    /**
     * 获取剪切板内容
     */
    private void get(Intent intent) {

    }

    /**
     * 系统规定必须有这个
     */
    private void startForeground() {
        Intent intent = new Intent();
        String title = "后台服务";
        String content = "设置剪切板ing";
        Notification.Builder builder = new Notification.Builder(this, title)
                .setContentIntent(PendingIntent.getService(this, 0, intent, 0))// 设置PendingIntent
                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
//                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentTitle(title)
                .setContentText(content)
                .setWhen(System.currentTimeMillis());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ONE_ID = "clip_board_service";
            // 修改安卓8.1以上系统报错
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ONE_ID, title, NotificationManager.IMPORTANCE_MIN);
            notificationChannel.enableLights(false);//如果使用中的设备支持通知灯，则说明此通知通道是否应显示灯
            notificationChannel.setShowBadge(false);//是否显示角标
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
            builder.setChannelId(CHANNEL_ONE_ID);
        }
        startForeground(1, builder.build());
    }
}