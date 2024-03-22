package Fuck.RollingSky.Pay;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Api {
	private int currentVersionCode = (int) BuildConfig.VERSION_CODE;
    Context context;
	URL url;
	int ggc;
    boolean hsb;
    public static void writeToFile(File file, String content) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public void checkForUpdates(Handler h, Context a, final boolean b) {
        context = a;
        try {
            if (b) {
                url = new URL("https://sb6.me/FuckRollingSky/getmodever.txt");
            } else {
                url = new URL("https://sb6.me/FuckRollingSky/getvercode.txt");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(new Callable<Integer>() {
                @Override
                public Integer call() {
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
            });
        try {
            int latestVersionCode = future.get();
            handleResult(latestVersionCode, b, h);
        } catch (Exception e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
    private void handleResult(int latestVersionCode, boolean b, Handler handler) {
        if (latestVersionCode == 0) {
        } else if (latestVersionCode > currentVersionCode) {
            showUpdateDialog();
            GetGG();
        } else if (latestVersionCode == -1) {
            if (b) {
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
            if (b) {
                handler.sendEmptyMessage(114514);
            }
        }
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
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(new Callable<Integer>() {
                @Override
                public Integer call() {
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
            });

        try {
            int latestVersionCode = future.get();
            handleGGResult(latestVersionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
    private void handleGGResult(int latestVersionCode) {
        ggc = latestVersionCode;
        if (latestVersionCode == 0) {
            // Handle case where latestVersionCode is 0
        } else if (latestVersionCode == -1) {
            // Handle case where latestVersionCode is -1
            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
        } else {
            // Handle case where latestVersionCode is neither 0 nor -1
            String filePath = context.getDataDir().getAbsolutePath() + "/file/ggCode.txt";
            File parentfile = new File(context.getDataDir().getAbsolutePath() + "/file");
            if (!parentfile.exists()) {
                parentfile.mkdirs();
            }
            File file = new File(filePath);
            try {
                if (!file.exists()) {
                    file.createNewFile();
                    writeToFile(file, "0");
                    showgg(false);
                } else {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line = reader.readLine();
                    int i = Integer.parseInt(line);
                    reader.close();
                    if (line != null) {
                        if (i == latestVersionCode) {
                        } else {
                            showgg(false);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	public void showgg(boolean b) {
        hsb = b;
        String language = Locale.getDefault().getLanguage();
        String script = Locale.getDefault().getScript();
        if (hsb) {
            if ("en".equals(language)) {
                downloadContent("https://sb6.me/FuckRollingSky/zcbb_en.txt");
            } else if ("zh".equals(language) && "Hant".equals(script)) {
                downloadContent("https://sb6.me/FuckRollingSky/zcbb_tw.txt");
            } else {
                downloadContent("https://sb6.me/FuckRollingSky/zcbb.txt");
            }
        } else {
            if ("en".equals(language)) {
                downloadContent("https://sb6.me/FuckRollingSky/context_en.txt");
            } else if ("zh".equals(language) && "Hant".equals(script)) {
                downloadContent("https://sb6.me/FuckRollingSky/context_tw.txt");
            } else {
                downloadContent("https://sb6.me/FuckRollingSky/context.txt");
            }
        }
    }
    private void downloadContent(final String q) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final String u = q;
        Future<String> future = executor.submit(new Callable<String>() {
                @Override
                public String call() {
                    try {
                        URL url = new URL(u);
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
            });
        try {
            String result = future.get();
            handleDownloadedContent(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
    private void handleDownloadedContent(String result) {
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
            result = result.replaceAll("\\n$", "");
            if (hsb) {
                builder.setTitle(R.string.zcbb_a);
                builder.setMessage(result);
            } else {
                builder.setTitle(bt);
                LinearLayout linear = new LinearLayout(context);
                TextView textview = new TextView(context);
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                int dpToPxFactor = (int) Math.ceil(metrics.scaledDensity);
                textview.setPadding((20 * dpToPxFactor), (10 * dpToPxFactor), (20 * dpToPxFactor), 0);
                textview.setText(result);
                textview.setTextIsSelectable(true);
                linear.addView(textview);
                builder.setView(linear);
                builder.setNeutralButton(qx, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dia, int which) {
                            String filePath = context.getDataDir().getAbsolutePath() + "/file/ggCode.txt";
                            File file = new File(filePath);
                            String s = String.valueOf(ggc);
                            Api.writeToFile(file, s);
                        }
                    });
            }
            builder.setPositiveButton(gx, null);
            builder.show();
        } else {
            if (hsb) {
                Toast.makeText(context, R.string.No_Web, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error Fail", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
