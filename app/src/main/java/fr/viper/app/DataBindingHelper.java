package fr.viper.app;

import android.databinding.BindingAdapter;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import android.widget.ViewFlipper;

import static android.content.Context.INPUT_METHOD_SERVICE;

@SuppressWarnings("unused")
public class DataBindingHelper {
    private DataBindingHelper() {

    }

    @BindingAdapter("displayedChild")
    public static void setDisplayedChild(ViewFlipper viewFlipper, int whichChild) {
        if (viewFlipper.getDisplayedChild() != whichChild) {
            viewFlipper.setDisplayedChild(whichChild);
        }
    }

    @BindingAdapter("shouldHideKeyboard")
    public static void shouldHideKeyboard(View view, boolean shouldHideKeyboard) {
        if (shouldHideKeyboard) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @BindingAdapter("toast")
    public static void toast(View view, @StringRes int resId) {
        if (resId != 0) {
            Toast.makeText(view.getContext(), resId, Toast.LENGTH_SHORT).show();
        }
    }
}
