package ru.clientapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import ru.customservice.IPostService;
import ru.customservice.Post;

/**
 * Created by sergey-rush on 14.04.2018.
 */

public class MainActivity extends AppCompatActivity {

    private String PACKAGE_NAME = "ru.serviceapp";
    private String CLASS_NAME = "ru.serviceapp.PostService";
    private IPostService mService;
    private TextView tvId;
    private TextView tvUserId;
    private TextView tvTitle;
    private TextView tvBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvId = (TextView) findViewById(R.id.tvId);
        tvUserId = (TextView) findViewById(R.id.tvUserId);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvBody = (TextView) findViewById(R.id.tvBody);
    }

    public void onBind(View view) {
        Intent serviceIntent = new Intent().setComponent(new ComponentName(PACKAGE_NAME, CLASS_NAME));
        startService(serviceIntent);
        bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);
    }

    public void onRequest(View view) {
        try {
            if (mService != null) {
                Post post = mService.getLastPost();
                onDisplay(post);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void onDisplay(Post post) {
        if (post != null) {
            tvId.setText(Integer.toString(post.getId()));
            tvUserId.setText(Integer.toString(post.getUserId()));
            tvTitle.setText(post.getTitle());
            tvBody.setText(post.getBody());
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = IPostService.Stub.asInterface(service);
            try {
                if (mService != null) {
                    Post post = mService.getLastPost();
                    onDisplay(post);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            mService = null;
        }
    };
}

