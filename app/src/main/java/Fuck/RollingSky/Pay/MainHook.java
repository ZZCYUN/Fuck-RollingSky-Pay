package Fuck.RollingSky.Pay;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainHook implements IXposedHookLoadPackage {
    Context context;
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (BuildConfig.APPLICATION_ID.equals(lpparam.packageName)) {
            XposedHelpers.findAndHookMethod(
                MainActivity.class.getName(),
                lpparam.classLoader,
                "isModuleActivated",
                XC_MethodReplacement.returnConstant(true));
        }
		final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 114514) {
					//网络验证成功的Hook
                    //此部分暂不开源
				}}
		};
		Map<String, String> packageToActivityMap = new HashMap<>();
		packageToActivityMap.put("fun.music.rollingsky.mi", "com.turbochilli.rollingsky.AppActivity");
		packageToActivityMap.put("com.turbochilli.rollingsky_cn.huawei", "com.turbochilli.rollingsky.AppActivity");
		packageToActivityMap.put("com.turbochilli.rollingsky_cn.vivo", "com.turbochilli.rollingsky.AppActivity");
		packageToActivityMap.put("com.turbochilli.rollingsky", "com.turbochilli.rollingsky.AppActivity");
		packageToActivityMap.put("com.cmplay.dancingline", "com.cmplay.dancingline.BaseAppActivity");
		packageToActivityMap.put("com.fgol.hsw.zq", "com.fgol.hsw.UnityMainActivity");
		packageToActivityMap.put("com.fgol", "com.unity3d.player.UnityPlayerActivity");
		for (Map.Entry<String, String> entry : packageToActivityMap.entrySet()) {
			String packageName = entry.getKey();
			String activityClassName = entry.getValue();
			if (lpparam.packageName.equals(packageName)) {
				Class<?> mainActivity = XposedHelpers.findClass(activityClassName, lpparam.classLoader);
				if (mainActivity != null) {
					XposedBridge.hookAllMethods(mainActivity, "onCreate", new XC_MethodHook() {
							@Override
							protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
								context = (Context) param.thisObject;
								HookOK(handler);
							}
						});
				}
			}
		}
		//未经网络验证的Hook
        //此部分暂未开源
	}
    private void HookOK(Handler handler) {
        String language = Locale.getDefault().getLanguage();
        String script = Locale.getDefault().getScript();
		String HookOK;
        if ("en".equals(language)) {
            HookOK = "Hook succeeded, please contact the developer if there is any exception";
        } else if ("zh".equals(language) && "Hant".equals(script)) {
            HookOK = "Hook成功，如有異常請聯繫開發者";
        } else {
            HookOK = "Hook成功，如有异常请联系开发者";
        }
		Toast.makeText(context, HookOK , Toast.LENGTH_SHORT).show();
		Api a = new Api();
		a.checkForUpdates(handler, context, true);
    }
}
