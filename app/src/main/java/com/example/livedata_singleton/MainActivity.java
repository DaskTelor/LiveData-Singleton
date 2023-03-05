package com.example.livedata_singleton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;

import com.example.livedata_singleton.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    //тема следующих доп. занятий, binding - это альтернативный вариант получения View из разметок и др.
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //получаем LiveData из dataController и сразу привязываем метод, который будет выполняться при любом изменении LiveData
        DataController.getInstance().getCountLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                //устанавливаем в clickCount новое значение, которое поместили в LiveData
                binding.clickCount.setText(integer.toString());
                //примечание, использование toString() обязательно, потому что по умолчанию Integer распаковывается в int
            }
        });
        //устанавливаем слушателя на кнопку, который будет срабатывать каждый раз, когда на неё нажимают
        binding.clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //вызываем метод, для добавления 1 клика в DataController
                DataController.getInstance().addClick();
            }
        });
    }
}