package com.xiao360.baselibrary.edit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.alibaba.android.arouter.utils.TextUtils;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.example.baselibrary.R;
import com.xiao360.baselibrary.edit.listener.OnSaveListener;

import java.util.ArrayList;
import java.util.List;

public class EditStateLayout extends StateLayout {
    private View text_edit;

    public View getDate_pick() {
        return date_pick;
    }

    private View date_pick;
    private View text_pick;

    public void setSaveListener(OnSaveListener saveListener) {
        this.saveListener = saveListener;
    }

    private OnSaveListener saveListener;

    public EditStateLayout(Context context) {
        super(context);
    }

    public EditStateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initContainer(FrameLayout container) {
        text_edit = getView(R.id.text_edit);
        date_pick = getView(R.id.date_pick);
        text_pick = getView(R.id.text_pick);
    }

    @Override
    protected int getContainerLayoutID() {
        return R.layout.layout_edit_container;
    }

    /** 显示“文字编辑”的View
     * @param strDefault
     * @param tip
     * */
    public void showTextEditView(String strDefault, String tip) {
        showView(text_edit);

        final EditText editText = getView(R.id.text);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        if(!TextUtils.isEmpty(strDefault)){
            editText.setText(strDefault);
            editText.setSelection(strDefault.length());
        }

        if(!TextUtils.isEmpty(tip)){
            TextView tip_Text = getView(R.id.tip);
            tip_Text.setVisibility(VISIBLE);
            tip_Text.setText(tip);
        }

        getView(R.id.btn_save_editText).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveListener!=null)
                    saveListener.onSaveEditText(editText.getText().toString());
            }
        });

    }

    /** 显示“日期选择”的View
     */
    public void showDatePickView() {
        showView(date_pick);
    }

    /** 显示“自定义选择项”的View */
    public void showTextPickView() {
        showView(text_pick);
    }
}
