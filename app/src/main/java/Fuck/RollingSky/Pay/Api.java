package Fuck.RollingSky.Pay;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class Api {
	private int currentVersionCode = (int) BuildConfig.VERSION_CODE;
    Context context;
	URL url;
	int ggc;
    public static void writeToFile(File file, String content) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public void checkForUpdates(final Handler handler, Context a, final boolean b) {
		context = a;
		try {
			if (b){
				url = new URL("https://sb6.me/FuckRollingSky/getmodever.txt");
			} else {
				url = new URL("https://sb6.me/FuckRollingSky/getvercode.txt");
			}
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String versionCodeString = reader.readLine();
                    return Integer.parseInt(versionCodeString);
                } catch (IOException e) {
                    e.printStackTrace();
                    return -1;
                }
            }
            @Override
            protected void onPostExecute(Integer latestVersionCode) {
                super.onPostExecute(latestVersionCode);
                if (latestVersionCode == 0) {
					String bt;
				    String nr;
					String tc;
				    String language = Locale.getDefault().getLanguage();
                    String script = Locale.getDefault().getScript();
                    if ("en".equals(language)) {
						bt = "Sorry, we're on the run.";
                        nr = "We know that the Fuck RollingSky Pay itself is not formal \nThank you for your support, now under official pressure, forced to stop maintaining \nThank you for your support, we are destined to meet again! \n \nPlease remove this module or uncheck this module scope";
						tc = "exit";
					} else if ("zh".equals(language) && "Hant".equals(script)) {
						bt = "對不起，我們跑路了";
                        nr = "我們知道Fuck RollingSky Pay本身並不正規\n感謝你們的支持，今受官方壓力，迫不得已停止維護\n謝謝大家支持，有緣再相會！\n\n請刪除此模塊或取消勾選此模塊作用域";
						tc = "退出";
					} else {
						bt = "对不起，我们跑路了";
                        nr = "我们知道Fuck RollingSky Pay本身并不正规\n感谢你们的支持，今受官方压力，迫不得已停止维护\n谢谢大家支持，有缘再相会！\n\n请删除此模块或取消勾选此模块作用域";
						tc = "退出";
					}
                    AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle(bt)
                        .setMessage(nr)
                        .setPositiveButton(tc, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dia, int which) {
                                System.exit(0);
                            }
                        })
                        .create();
                    dialog.show();
                } else if (latestVersionCode > currentVersionCode) {
                    showUpdateDialog();
					GetGG();
                } else if (latestVersionCode == -1) {
					if(b){
						String language = Locale.getDefault().getLanguage();
						String script = Locale.getDefault().getScript();
						if ("en".equals(language)) {
							Toast.makeText(context, "Network connection failed, only RS for TapTap networking verification skipped", Toast.LENGTH_SHORT).show();
						} else if ("zh".equals(language) && "Hant".equals(script)) {
							Toast.makeText(context, "網絡連接失敗，盡調過TapTap版和小米版RS聯網驗證", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(context, "网络连接失败，仅跳过TapTap版和小米版RS联网验证", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(context, R.string.Inspect_Fail, Toast.LENGTH_SHORT).show();
					}
                } else {
					GetGG();
                    if(b){
						handler.sendEmptyMessage(114514);
					}
                }
            }
        }.execute();
    }
    private void showUpdateDialog() {
        String language = Locale.getDefault().getLanguage();
        String script = Locale.getDefault().getScript();
		String bt;
		String nr;
		String gx;
		String qx;
        if ("en".equals(language)) {
            bt = "New version found";
			nr = "A new version of the module is available. Update?";
			gx = "Update";
			qx = "Cancel";
        } else if ("zh".equals(language) && "Hant".equals(script)) {
			bt = "發現新版本";
			nr = "模塊有新版本可用，是否更新？";
			gx = "更新";
			qx = "取消";
        } else {
			bt = "发现新版本";
			nr = "模块有新版本可用，是否更新？";
			gx = "更新";
			qx = "取消";
        }
		AlertDialog dialog = new AlertDialog.Builder(context)
			.setCancelable(false)
			.setTitle(bt)
			.setMessage(nr)
			.setPositiveButton(gx, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String url = "https://sb6.me/FuckRollingSky/download";
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					context.startActivity(intent);
				}
			})
			.setNegativeButton(qx, null)
			.create();
		dialog.show();
    }
	private void GetGG() {
		new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    URL url = new URL("https://sb6.me/FuckRollingSky/gonggao/code.txt");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String versionCodeString = reader.readLine();
                    return Integer.parseInt(versionCodeString);
                } catch (IOException e) {
                    e.printStackTrace();
                    return -1;
                }
            }
            @Override
            protected void onPostExecute(Integer latestVersionCode) {
                super.onPostExecute(latestVersionCode);
				ggc = latestVersionCode;
				if (latestVersionCode == 0) {
                } else if (latestVersionCode == -1) {
                    Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
                } else {
					String filePath = context.getDataDir().getAbsolutePath() + "/file/ggCode.txt";
                    File parentfile = new File(context.getDataDir().getAbsolutePath() + "/file");
                    if(!parentfile.exists()){
                        parentfile.mkdirs();
                    }
					File file = new File(filePath);
					try {
						if (!file.exists()) {
							file.createNewFile();
							writeToFile(file, "0");
							showgg();
						} else {
							BufferedReader reader = new BufferedReader(new FileReader(file));
							String line = reader.readLine();
							int i = Integer.parseInt(line);
							reader.close();
							if (line != null) {
								if (i == latestVersionCode) {
								} else {
									showgg();
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
            };
        }.execute();
	}
	private void showgg() {
		String language = Locale.getDefault().getLanguage();
		String script = Locale.getDefault().getScript();
		if ("en".equals(language)) {
			new DownloadContentTask().execute("https://sb6.me/FuckRollingSky/context_en.txt");
		} else if ("zh".equals(language) && "Hant".equals(script)) {
			new DownloadContentTask().execute("https://sb6.me/FuckRollingSky/context_tw.txt");
		} else {
			new DownloadContentTask().execute("https://sb6.me/FuckRollingSky/context.txt");
		}
	}
	public class DownloadContentTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			try {
				URL url = new URL(urls[0]);
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				StringBuilder stringBuilder = new StringBuilder();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line).append("\n");
				}
				bufferedReader.close();
				return stringBuilder.toString();
			} catch (Exception e) {
				return null;
			}
		}
		@Override
		protected void onPostExecute(String result) {
			String language = Locale.getDefault().getLanguage();
			String script = Locale.getDefault().getScript();
			String bt;
			String gx;
			String qx;
			if ("en".equals(language)) {
				bt = "Notice";
				gx = "OK";
				qx = "No longer prompt";
			} else if ("zh".equals(language) && "Hant".equals(script)) {
				bt = "公告";
				gx = "確定";
				qx = "不再提示";
			} else {
				bt = "公告";
				gx = "确定";
				qx = "不再提示";
			}
			if (result != null) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle(bt);
				LinearLayout linear = new LinearLayout(context);
				TextView textview = new TextView(context);
				DisplayMetrics metrics = context.getResources().getDisplayMetrics();
				int dpToPxFactor = (int) Math.ceil(metrics.scaledDensity);
				textview.setPadding((20 * dpToPxFactor), (10 * dpToPxFactor), (20 * dpToPxFactor), 0);
				result = result.replaceAll("\\n$", "");
				textview.setText(result);
				textview.setTextIsSelectable(true);
				linear.addView(textview);
				builder.setView(linear);
				builder.setPositiveButton(gx, null);
				builder.setNeutralButton(qx, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dia, int which) {
							String filePath = context.getDataDir().getAbsolutePath() + "/file/ggCode.txt";
							File file = new File(filePath);
							String s = String.valueOf(ggc);
							Api.writeToFile(file, s);
						}
					});
				builder.show();
			} else {
				Toast.makeText(context, "Error Fail", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
