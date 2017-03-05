package com.dutch.wang.dutch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wang on 2016/7/26 0026.
 */
public class NewActivity extends AppCompatActivity {
    private String fileName = "ss.txt";
    Button button_add;
    EditText title;
    EditText total;
    EditText num;
    String my="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        //read();
        //
button_add=(Button)findViewById(R.id.add);
        title=(EditText)findViewById(R.id.editor_title);
        total=(EditText)findViewById(R.id.editor_total);
        num=(EditText)findViewById(R.id.editor_num);
button_add.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(!TextUtils.isEmpty(title.getText())&&!TextUtils.isEmpty(total.getText())&&!TextUtils.isEmpty(num.getText())){
            my=title.getText().toString()+"|"+total.getText()+"|"+num.getText()+"|";
            save(my);
            //read();
            finish();
        }
else{
            Toast.makeText(NewActivity.this, "plase enter every information", Toast.LENGTH_LONG).show();
        }
    }
});
    }
    //处理后退键的情况
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            this.finish();  //finish当前activity
            overridePendingTransition(0, R.anim.slide_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     *@author chenzheng_Java
     *保存用户输入的内容到文件
     */
    private void save(String content) {

        //String content ="ssssss";
        try {
      /* 根据用户提供的文件名，以及文件的应用模式，打开一个输出流.文件不存系统会为你创建一个的，
       * 至于为什么这个地方还有FileNotFoundException抛出，我也比较纳闷。在Context中是这样定义的
       *  public abstract FileOutputStream openFileOutput(String name, int mode)
       *  throws FileNotFoundException;
       * openFileOutput(String name, int mode);
       * 第一个参数，代表文件名称，注意这里的文件名称不能包括任何的/或者/这种分隔符，只能是文件名
       *     该文件会被保存在/data/data/应用名称/files/chenzheng_java.txt
       * 第二个参数，代表文件的操作模式
       *     MODE_PRIVATE 私有（只能创建它的应用访问） 重复写入时会文件覆盖
       *     MODE_APPEND 私有  重复写入时会在文件的末尾进行追加，而不是覆盖掉原来的文件
       *     MODE_WORLD_READABLE 公用 可读
       *     MODE_WORLD_WRITEABLE 公用 可读写
       * */
            FileOutputStream outputStream = openFileOutput(fileName,
                    Activity.MODE_APPEND);
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
            Toast.makeText(NewActivity.this, "save success", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @author chenzheng_java
     * 读取刚才用户保存的内容
     */
    public void read() {
        try {
            FileInputStream inputStream = this.openFileInput(fileName);
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            while (inputStream.read(bytes) != -1) {
                arrayOutputStream.write(bytes, 0, bytes.length);
            }
            inputStream.close();
            arrayOutputStream.close();
            String content = new String(arrayOutputStream.toByteArray());
            //showTextView.setText(content);
            Toast.makeText(NewActivity.this,content, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
