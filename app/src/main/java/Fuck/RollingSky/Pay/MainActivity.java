package Fuck.RollingSky.Pay;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Api api = new Api();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getActionBar();
        actionBar.setCustomView(R.layout.actionbar_title);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        Button openBrowserButton = findViewById(R.id.clickButton);
        final TextView moduleStatusTextView = findViewById(R.id.moduleStatusTextView);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(new float[]{30, 30, 30, 30, 30, 30, 30, 30});
        gradientDrawable.setColor(getResources().getColor(R.color.buttonColor));
        if (isModuleActivated()) {
			moduleStatusTextView.setText(R.string.Modules_On);
			String absolutePath = getDataDir().getAbsolutePath();
			Matcher matcher = Pattern.compile("/data/user/.*/data/user/.*").matcher(absolutePath);
			Matcher matcher2 = Pattern.compile(".*/0/.*/0.*").matcher(absolutePath);
			if (matcher.find() || matcher2.find()) {
				moduleStatusTextView.setText(R.string.Modules_On_VM);
			}
        } else {
            AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(R.string.Modules_Off)
                .setMessage(R.string.Modules_Off_Tips)
                .setPositiveButton(R.string.OK, null)
                .create();
            dialog.show();
        }
        openBrowserButton.setBackground(gradientDrawable);
        openBrowserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    api.showgg(true);
                }
            });
        Button openRSButton = findViewById(R.id.openRSButton);
        GradientDrawable rsButtonDrawable = new GradientDrawable();
        rsButtonDrawable.setShape(GradientDrawable.RECTANGLE);
        rsButtonDrawable.setCornerRadii(new float[]{30, 30, 30, 30, 30, 30, 30, 30});
        rsButtonDrawable.setColor(getResources().getColor(R.color.buttonColor));
        openRSButton.setBackground(rsButtonDrawable);
        openRSButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String packageName = "com.turbochilli.rollingsky";
                    Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
                    if (intent != null) {
                        Toast.makeText(MainActivity.this, R.string.Open_RollingSky, Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else {
                        final String mi;
                        final String hw;
						final String vo;
                        final String qx = getString(R.string.No);
                        String language = Locale.getDefault().getLanguage();
                        String script = Locale.getDefault().getScript();
                        if ("en".equals(language)) {
                            mi = "XiaoMi";
                            hw = "Hwawei";
							vo = "VIVO";
                        } else if ("zh".equals(language) && "Hant".equals(script)) {;
                            mi = "小米版";
                            hw = "華為版";
							vo = "VIVO版";
                        } else {
                            mi = "小米版";
                            hw = "华为版";
							vo = "VIVO版";
                        }
                        final String[] items = {mi, hw, qx};
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle(R.string.mrs)
                            .setItems(items, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String selectedOption = items[which];
                                    if (selectedOption.equals(mi)) {
                                        String packageName = "fun.music.rollingsky.mi";
                                        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
                                        if (intent != null) {
                                            Toast.makeText(MainActivity.this, R.string.Open_RollingSky, Toast.LENGTH_SHORT).show();
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(MainActivity.this, R.string.No_RS, Toast.LENGTH_SHORT).show();
                                        }
                                    } else if (selectedOption.equals(hw)) {
                                        String packageName = "com.turbochilli.rollingsky_cn.huawei";
                                        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
                                        if (intent != null) {
                                            Toast.makeText(MainActivity.this, R.string.Open_RollingSky, Toast.LENGTH_SHORT).show();
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(MainActivity.this, R.string.No_RS, Toast.LENGTH_SHORT).show();
                                        }
                                    } else if (selectedOption.equals(vo)) {
                                        String packageName = "com.turbochilli.rollingsky_cn.vivo";
                                        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
                                        if (intent != null) {
                                            Toast.makeText(MainActivity.this, R.string.Open_RollingSky, Toast.LENGTH_SHORT).show();
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(MainActivity.this, R.string.No_RS, Toast.LENGTH_SHORT).show();
                                        }
									}
                                }
                            });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });
        Button openMGButton = findViewById(R.id.openMGButton);
        GradientDrawable mgButtonDrawable = new GradientDrawable();
        mgButtonDrawable.setShape(GradientDrawable.RECTANGLE);
        mgButtonDrawable.setCornerRadii(new float[]{30, 30, 30, 30, 30, 30, 30, 30});
        mgButtonDrawable.setColor(getResources().getColor(R.color.buttonColor));
        openMGButton.setBackground(mgButtonDrawable);
        openMGButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String dl;
                    final String zq;
                    final String jh;
                    final String qx = getString(R.string.No);
                    String language = Locale.getDefault().getLanguage();
                    String script = Locale.getDefault().getScript();
                    if ("en".equals(language)) {
                        dl = "Dancing Line";
                        zq = "Hungry Shark World";
                        jh = "Hungry Shark Evolution";
                    } else if ("zh".equals(language) && "Hant".equals(script)) {
                        dl = "跳舞的線";
                        zq = "飢餓鯊：世界";
                        jh = "飢餓鯊：進化";
                    } else {
                        dl = "跳舞的线";
                        zq = "饥饿鲨：世界";
                        jh = "饥饿鲨：进化";
                    }
                    final String[] items = {dl, zq, jh, qx};
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setTitle(R.string.Open_MG)
						.setItems(items, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								String selectedOption = items[which];
								if (selectedOption.equals(dl)) {
									String packageName = "com.cmplay.dancingline";
									Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
									if (intent != null) {
										Toast.makeText(MainActivity.this, R.string.opmg, Toast.LENGTH_SHORT).show();
										startActivity(intent);
									} else {
										Toast.makeText(MainActivity.this, R.string.nomg, Toast.LENGTH_SHORT).show();
									}
								} else if (selectedOption.equals(zq)) {
									String packageName = "com.fgol.hsw.zq";
									Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
									if (intent != null) {
										Toast.makeText(MainActivity.this, R.string.opmg, Toast.LENGTH_SHORT).show();
										startActivity(intent);
									} else {
										Toast.makeText(MainActivity.this, R.string.nomg, Toast.LENGTH_SHORT).show();
									}
								} else if (selectedOption.equals(jh)) {
									String packageName = "com.fgol";
									Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
									if (intent != null) {
										Toast.makeText(MainActivity.this, R.string.opmg, Toast.LENGTH_SHORT).show();
										startActivity(intent);
									} else {
										Toast.makeText(MainActivity.this, R.string.nomg, Toast.LENGTH_SHORT).show();
									}
								}
							}
						});
					AlertDialog dialog = builder.create();
					dialog.show();
                }
            });
        ImageView image = findViewById(R.id.ONEicon);
        image.setImageResource(R.drawable.about);
        image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(intent);
                }
            });
        api.checkForUpdates(null, MainActivity.this, false);
        String filePath = getDataDir().getAbsolutePath() + "/file/imginfo.txt";
        File file = new File(filePath);
        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                reader.close();
                if (line != null && line.equals("1")) {
                    setimg();
                } else {
                    getimg();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            getimg();
        }
    }
    public void getimg() {
        ImageView imageView = findViewById(R.id.Background);
        //二次元随机图片API
        String imageUrl = "https://www.loliapi.com/acg/";
        LoadImageTask task = new LoadImageTask(this, imageView);
        task.execute(imageUrl);
    }
    private boolean isModuleActivated() {
        return false;
    }
    public class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private Context context;
        private ProgressDialog progressDialog;
        private ImageView imageView;
        public LoadImageTask(Context context, ImageView imageView) {
            this.context = context;
            this.imageView = imageView;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle(R.string.Load);
            progressDialog.setMessage(getString(R.string.Load_Ing));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            progressDialog.dismiss();
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                new SaveImageTask().execute(bitmap);
            } else {
                Toast.makeText(MainActivity.this, R.string.No_Web, Toast.LENGTH_SHORT).show();
                setimg();
            }
        }
    }
    private void setimg() {
        String filePath = getDataDir().getAbsolutePath() + "/file/image.png";
        File file = new File(filePath);
        if (file.exists()) {
            ImageView imageView = findViewById(R.id.Background);
            Bitmap bitmap2 = BitmapFactory.decodeFile(filePath);
            imageView.setImageBitmap(bitmap2);
        }
    }
    private class SaveImageTask extends AsyncTask<Bitmap, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Bitmap... bitmaps) {
            Bitmap bitmap = bitmaps[0];
            String filePath = getDataDir().getAbsolutePath() + "/file/";
            String fileName = "image.png";
            File directory = new File(filePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(directory, fileName);
            try {
                FileOutputStream outStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                outStream.flush();
                outStream.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast.makeText(MainActivity.this, "Failed to save image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
