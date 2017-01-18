package fr.viper.app;

import android.databinding.BindingAdapter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import android.widget.ViewFlipper;

import static android.content.Context.INPUT_METHOD_SERVICE;

@SuppressWarnings("unused")
public class DataBindingHelper {
    private DataBindingHelper() {

    }

    @BindingAdapter("bind:displayed")
    public static void setDisplayed(View view, boolean displayed) {
        if (!displayed) {
            return;
        }
        if (!(view.getParent() instanceof ViewFlipper)) {
            return;
        }

        ViewFlipper viewFlipper = (ViewFlipper) view.getParent();
        if (viewFlipper.getCurrentView() == view) {
            return;
        }

        for (int i = 0; i < viewFlipper.getChildCount(); i++) {
            if (viewFlipper.getChildAt(i) == view) {
                viewFlipper.setDisplayedChild(i);
                return;
            }
        }
    }

    @BindingAdapter("bind:shouldHideKeyboard")
    public static void shouldHideKeyboard(View view, boolean shouldHideKeyboard) {
        if (shouldHideKeyboard) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @BindingAdapter("bind:toast")
    public static void toast(View view, String toast) {
        if (toast != null) {
            Toast.makeText(view.getContext(), toast, Toast.LENGTH_SHORT).show();
        }
    }
}
