package cn.dreamn.qianji_auto.core.hook.app.qianji.hooks;

import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

import cn.dreamn.qianji_auto.core.hook.Utils;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class AutoError {
    public static void init(Utils utils) throws ClassNotFoundException {
        ClassLoader mAppClassLoader = utils.getClassLoader();
        Class<?> AutoTaskLog = mAppClassLoader.loadClass("com.mutangtech.qianji.data.model.AutoTaskLog");
        Class<?> WebViewActivity = mAppClassLoader.loadClass("com.mutangtech.qianji.ui.webview.WebViewActivity");
        XposedHelpers.findAndHookMethod("com.mutangtech.qianji.bill.auto.AddBillIntentAct", mAppClassLoader, "a", String.class, AutoTaskLog, new XC_MethodHook() {
            protected void beforeHookedMethod(MethodHookParam param) {
                String string = (String) param.args[0];
                //钱迹弹出错误信息，转发给自动记账处理~
                if (string != null) {
                    String url = getSolvedUrl(string);
                    Intent v0 = new Intent(utils.getContext(), WebViewActivity);
                    v0.putExtra("param_web_url", url);
                    v0.putExtra("param_web_title", "自动记账错误解决方案");
                    v0.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    utils.getContext().startActivity(v0);
                    //使用钱迹的WebView
                    //加载解决方案
                    Looper.prepare();
                    Toast.makeText(utils.getContext(), string + "\n发生了错误，正在为您加载解决方案！", Toast.LENGTH_LONG).show();
                    Looper.loop();
                    utils.log("钱迹错误捕获： " + string, true);
                }


            }
        });
    }

    public static String getSolvedUrl(String problems) {
        String url = "https://pan.ankio.net";
        if (problems.contains("bookname")) {
            // TODO 根据不同错误信息给出解决方案地址
        } else if (problems.contains("accountname")) {

        }
        return url;
    }
}