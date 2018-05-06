package sample.codequality;

import android.Manifest;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.LayoutInflater;
import android.view.View;

import com.facebook.testing.screenshot.Screenshot;
import com.facebook.testing.screenshot.ViewHelpers;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class _12_ScreenshotTest {
    private static LayoutInflater sLayoutInflater;

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    @BeforeClass
    public static void runBeforeAllTests() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        sLayoutInflater = LayoutInflater.from(appContext);
    }

    @AfterClass
    public static void runAfterAllTests() {
        sLayoutInflater = null;
    }

    @Test
    public void numpadView_shouldDisplayCorrectly() {
        View calculatorLayout = sLayoutInflater.inflate(R.layout.fragment_calculator, null, false);
        View numpadView = calculatorLayout.findViewById(R.id.calculator_numpad);

        ViewHelpers.setupView(numpadView)
                .setExactWidthDp(500)
                .layout();

        Screenshot.snap(numpadView).record();
    }
}
