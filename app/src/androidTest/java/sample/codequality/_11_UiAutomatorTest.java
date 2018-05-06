package sample.codequality;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class _11_UiAutomatorTest {
    private static final double EPSILON = 1e-5;
    private static final String MY_APP_PACKAGE = "sample.codequality";
    private static final String CALC_PACKAGE = "com.android.calculator2";
    private static final int TIMEOUT = 5000;

    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void mainActivityTest() {
        String x = "4";
        String y = "4";
        String operation = "+";
        launchApp(MY_APP_PACKAGE);
        double myResult = calculateByMyApp(x, operation, y, "=");
        launchApp(CALC_PACKAGE);
        double calcResult = calculateByStandardCalc(x, operation, y, "=");
        Assert.assertEquals(calcResult, myResult, EPSILON);
    }

    private double calculateByMyApp(@NonNull String... buttonsSequence) {
        while (mDevice.findObject(By.res(MY_APP_PACKAGE, "calculator_display")).getText() != null) {
            mDevice.findObject(By.text("<")).click();
        }
        for (String button : buttonsSequence) {
            mDevice.findObject(By.text(button)).click();
        }
        mDevice.wait(Until.hasObject(By.res(MY_APP_PACKAGE, "resultText")), TIMEOUT);
        String result = mDevice
                .findObject(By.res(MY_APP_PACKAGE, "calculator_display"))
                .getText()
                .split("=")[1]
                .trim();
        return Double.parseDouble(result);
    }

    private void launchApp(@NonNull String packageName) {
        Context context = InstrumentationRegistry.getContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            throw new NullPointerException("Cannot obtain launch intent for package: " + packageName);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        mDevice.wait(Until.hasObject(By.res(packageName)), TIMEOUT);
    }

    private double calculateByStandardCalc(@NonNull String... buttonsSequence) {
        for (String button : buttonsSequence) {
            mDevice.findObject(By.text(button)).click();
        }
        return Double.parseDouble(mDevice.findObject(By.clazz("android.widget.EditText")).getText());
    }
}