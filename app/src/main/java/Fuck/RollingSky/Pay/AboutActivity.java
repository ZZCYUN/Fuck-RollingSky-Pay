package Fuck.RollingSky.Pay;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AboutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ActionBar actionBar = getActionBar();
        actionBar.setCustomView(R.layout.actionbar_title);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		String filePath = getDataDir().getAbsolutePath() + "/file/image.png";
        File file = new File(filePath);
        if (file.exists()) {
            ImageView imageView = findViewById(R.id.Background);
            Bitmap bitmap2 = BitmapFactory.decodeFile(filePath);
            imageView.setImageBitmap(bitmap2);
        }
		ImageView image = findViewById(R.id.ONEicon);
        image.setImageResource(R.drawable.back);
        image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
		findViewById(R.id.github).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(AboutActivity.this, R.string.Open_Browser, Toast.LENGTH_SHORT).show();
                    String url = "https://github.com/ZZCYUN/Fuck-RollingSky-Pay";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(AboutActivity.this, R.string.No_Browser, Toast.LENGTH_SHORT).show();
                    }
                }
            });
		genggai();
        Button mainicon = findViewById(R.id.ycicon);
        mainicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PackageManager pm = getPackageManager();
					final String packageName = getPackageName();
					final ComponentName componentName = new ComponentName(packageName, OpenActivity.class.getName());
					int componentState = pm.getComponentEnabledSetting(componentName);
					if (componentState == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
						pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
						Toast.makeText(AboutActivity.this, R.string.hf, Toast.LENGTH_SHORT).show();
					} else {
						AlertDialog dialog = new AlertDialog.Builder(AboutActivity.this)
							.setTitle(R.string.danger)
							.setMessage(R.string.alert)
							.setPositiveButton(R.string.sb, new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dia, int which) {
									pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
									Toast.makeText(AboutActivity.this, R.string.yci, Toast.LENGTH_SHORT).show();
								}
							})
							.setNeutralButton(R.string.No, null)
							.create();
						dialog.show();
					}
					genggai();
                }
            });
        Button openQQButton = findViewById(R.id.openQQButton);
        openQQButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(AboutActivity.this, R.string.Open_Browser, Toast.LENGTH_SHORT).show();
                    String url = "https://github.com/ZZCYUN/Fuck-RollingSky-Pay/issues";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(AboutActivity.this, R.string.No_Browser, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        Button jinruMaiain = findViewById(R.id.zuozheMain);
        jinruMaiain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(AboutActivity.this, R.string.Open_Browser, Toast.LENGTH_SHORT).show();
                    String url = "https://sb6.me/";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(AboutActivity.this, R.string.No_Browser, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        Button szimg = findViewById(R.id.szimg);
        szimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String filePath = getDataDir().getAbsolutePath() + "/file/imginfo.txt";
                    File file = new File(filePath);
                    try {
                        if (!file.exists()) {
                            file.createNewFile();
                            Api.writeToFile(file, "1");
                            Toast.makeText(AboutActivity.this, R.string.sfi, Toast.LENGTH_SHORT).show();
                        } else {
                            BufferedReader reader = new BufferedReader(new FileReader(file));
                            String line = reader.readLine();
                            reader.close();

                            if (line != null) {
                                if (line.equals("0")) {
                                    Api.writeToFile(file, "1");
                                    Toast.makeText(AboutActivity.this, R.string.sfi, Toast.LENGTH_SHORT).show();
                                } else if (line.equals("1")) {
                                    Api.writeToFile(file, "0");
                                    Toast.makeText(AboutActivity.this, R.string.sni, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
					genggai();
                }
            });
    }
	private void genggai() {
		String SfilePath = getDataDir().getAbsolutePath() + "/file/imginfo.txt";
		File Sfile = new File(SfilePath);
		if (Sfile.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(Sfile));
				String content = reader.readLine();
				Button szimgButton = findViewById(R.id.szimg);
				reader.close();
				if (content != null && content.equals("1")) {
					szimgButton.setText(R.string.szimgx);
				} else {
					szimgButton.setText(R.string.szimg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		final PackageManager pm = getPackageManager();
		final String packageName = getPackageName();
		final ComponentName componentName = new ComponentName(packageName, OpenActivity.class.getName());
		int componentState = pm.getComponentEnabledSetting(componentName);
		Button ycButton = findViewById(R.id.ycicon);
		if (componentState == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
			ycButton.setText(R.string.hf_icon);
		} else if (componentState == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
			ycButton.setText(R.string.hide_icon);
		}
	}
}
