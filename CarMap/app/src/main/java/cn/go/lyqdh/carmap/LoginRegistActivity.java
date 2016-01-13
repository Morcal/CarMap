package cn.go.lyqdh.carmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.listener.SaveListener;
import cn.go.lyqdh.carmap.modle.User;

public class LoginRegistActivity extends AppCompatActivity {
    private static final String TAG = LoginRegistActivity.class.getSimpleName();
    private TextView butLogin;
    private TextView butRegister;
    private EditText userName;
    private EditText password;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    public void initView() {
        userName = (EditText) findViewById(R.id.et_name);
        password = (EditText) findViewById(R.id.et_password);
        butLogin = (TextView) findViewById(R.id.btn_login);
        butRegister = (TextView) findViewById(R.id.btn_register);
        butLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        butRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Login() {
        user.setUsername(userName.getText().toString().trim());
        user.setPassword(password.getText().toString().trim());
        user.login(LoginRegistActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(LoginRegistActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginRegistActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("USER", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(LoginRegistActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void register() {

        String name = userName.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        user.setUsername(name);
        user.setPassword(pwd);
        user.signUp(LoginRegistActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(LoginRegistActivity.this, "注册成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(LoginRegistActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
