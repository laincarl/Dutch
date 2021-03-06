package com.dutch.wang.dutch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;  //声明一个ListView对象
    private List<info> mlistInfo = new ArrayList<info>();  //声明一个list，动态存储要显示的信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, NewActivity.class);
                startActivity(intent);
            }
        });
        listView=(ListView)this.findViewById(R.id.listview);    //将listView与布局对象关联

        setInfo();  //给信息赋值函数，用来测试

        listView.setAdapter(new ListViewAdapter(mlistInfo));
        /*//处理Item的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                info getObject = mlistInfo.get(position);   //通过position获取所点击的对象
                int infoId = getObject.getId(); //获取信息id
                String infoTitle = getObject.getTitle();    //获取信息标题
                String infoTotal = getObject.getTotal();    //获取信息详情
                String infoNum   =getObject.getNum();
                //Toast显示测试
                Toast.makeText(MainActivity.this, "信息ID:"+infoId,Toast.LENGTH_SHORT).show();
            }
        });*/

        //长按菜单显示
        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            public void onCreateContextMenu(ContextMenu conMenu, View view , ContextMenu.ContextMenuInfo info) {
                conMenu.setHeaderTitle("MENU");
                conMenu.add(0, 0, 0, "delete");
                //conMenu.add(0, 1, 1, "条目二");
                //conMenu.add(0, 2, 2, "条目三");
            }
        });
    }

    //长按菜单处理函数
    public boolean onContextItemSelected(MenuItem aItem) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)aItem.getMenuInfo();
        switch (aItem.getItemId()) {
            case 0:
                //Toast.makeText(MainActivity.this, "你点击了条目一",Toast.LENGTH_SHORT).show();
                return true;
            case 1:
                Toast.makeText(MainActivity.this, "你点击了条目二",Toast.LENGTH_SHORT).show();

                return true;
            case 2:
                Toast.makeText(MainActivity.this, "你点击了条目三",Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }


    public class ListViewAdapter extends BaseAdapter {
        View[] itemViews;

        public ListViewAdapter(List<info> mlistInfo) {
            // TODO Auto-generated constructor stub
            itemViews = new View[mlistInfo.size()];
            for(int i=0;i<mlistInfo.size();i++){
                info getInfo=(info)mlistInfo.get(i);    //获取第i个对象
                //调用makeItemView，实例化一个Item
                itemViews[i]=makeItemView(
                        getInfo.getTitle(), getInfo.getTotal(),getInfo.getAvatar()
                );
            }
        }

        public int getCount() {
            return itemViews.length;
        }

        public View getItem(int position) {
            return itemViews[position];
        }

        public long getItemId(int position) {
            return position;
        }

        //绘制Item的函数
        private View makeItemView(String strTitle, String strText, int resId) {
            LayoutInflater inflater = (LayoutInflater) MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            View itemView = inflater.inflate(R.layout.list_item, null);

            // 通过findViewById()方法实例R.layout.item内各组件
            TextView title = (TextView) itemView.findViewById(R.id.title);
            title.setText(strTitle);    //填入相应的值
            TextView text = (TextView) itemView.findViewById(R.id.info);
            text.setText(strText);
            ImageView image = (ImageView) itemView.findViewById(R.id.img);
            image.setImageResource(resId);

            return itemView;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            return itemViews[position];
        }
    }

    public void setInfo() {
        mlistInfo.clear();
        int j = 0;
        int k = 0;
        //分割字符串
        String str = read();
        //String str ="我|11|2|ta|22|3";
        //Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
        String[] strarray = str.split("[|]");

        if (strarray.length >1) {
            for (int i = 0; i < strarray.length-2; i++) {
                System.out.println(strarray.length);
                //System.out.println(strarray[i]);
                //information.setId(0);
                info information = new info();
                information.setTitle(strarray[i]);
                System.out.println(strarray[i]);
                information.setTotal("Total:" + strarray[i + 1] +"     "+ "Num:" + strarray[i + 2]);
                System.out.println(strarray[i + 1]);
                information.setAvatar(R.drawable.ic_tabbar_course_normal);
                mlistInfo.add(information); //将新的info对象加入到信息列表中
                i = i + 2;
            }


            /*else if(j%3==0){
                information.setNum("Num" + strarray[i]);
                j++;
            }*/


        }
    }

    /**
     * @author chenzheng_java
     * 读取刚才用户保存的内容
     */
    public String read() {
        String content="";
        try {
            FileInputStream inputStream = this.openFileInput("ss.txt");
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            while (inputStream.read(bytes) != -1) {
                arrayOutputStream.write(bytes, 0, bytes.length);
            }
            inputStream.close();
            arrayOutputStream.close();
             content = new String(arrayOutputStream.toByteArray());
            //showTextView.setText(content);
            //Toast.makeText(MainActivity.this,content, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       return content;
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        setInfo();
        listView.setAdapter(new ListViewAdapter(mlistInfo));
        super.onResume();

    }
}
