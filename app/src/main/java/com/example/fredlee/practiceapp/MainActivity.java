package com.example.fredlee.practiceapp;

import android.app.Activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.String;

import android.content.res.TypedArray;
import android.net.Uri;
import android.net.ParseException;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.GridView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;


public class MainActivity extends Activity {
    //Initialize variables.
    Button add;
    ImageView iv;
    TextView tv;
    double rate = 0;
    int rating;
    String fileAddress;
    String date;
    private GridView gridView;
    private GridViewAdapter customGridAdapter;
    final ArrayList imageItems = new ArrayList();
    Date ddate = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.griddedscreen);
        iv = (ImageView) findViewById(R.id.ivResult);
        tv = (TextView) findViewById(R.id.bResultText);
        add = (Button) findViewById(R.id.bAdd);

        gridView = (GridView) findViewById(R.id.gridView);
        customGridAdapter = new GridViewAdapter(this, R.layout.row_grid, getData());
        gridView.setAdapter(customGridAdapter);
    }

    private ArrayList getData() {
        // retrieve String drawable array
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                    imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, i, new Date()));
        }
        return imageItems;
    }

    //On click of "Add to Food Log," the app moves to the next activity AddLog.java.
    public void goToAddIntent(View view)
    {
        Intent intent = new Intent(MainActivity.this, AddLog.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
        int targetW = iv.getWidth();
        int targetH = iv.getHeight();

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileAddress, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(fileAddress, bmOptions);

		/* Associate the Bitmap to the ImageView */
        iv.setImageBitmap(bitmap);
        iv.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            rate = data.getDoubleExtra("rate", 0);
            fileAddress = data.getStringExtra("address");
            Bitmap bmp = BitmapFactory.decodeFile(fileAddress);
            date = data.getStringExtra("date");
            if(date == null || bmp == null) return;
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
            try {
                ddate = format.parse(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            imageItems.add(new ImageItem(bmp, rate, ddate));
            customGridAdapter = new GridViewAdapter(this, R.layout.row_grid, getData());
            gridView.setAdapter(customGridAdapter);
        }
           // if (resultCode == RESULT_CANCELED) {OPTIONAL} No longer needed.
    }//onActivityResult
}
