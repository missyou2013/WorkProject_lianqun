package activity.lianqun.herry.com.workproject_lianqun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.button2)
    Button button2;
    private String str_wwwq;
    private Button btn;

    @SuppressWarnings("LossyEncoding")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ac_home);
        ButterKnife.bind(this);

        PollingUtils.startPollingService(this, 5, PollingService.class, PollingService.ACTION);
    }

    @OnClick(R.id.button2)
    public void onClick() {
        System.out.println("Start polling service...");

//        Toast.makeText(this, "aaaaaaaaaa", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Stop polling service
        System.out.println("Stop polling service...");
//        PollingUtils.stopPollingService(this, PollingService.class, PollingService.ACTION);
    }
}
