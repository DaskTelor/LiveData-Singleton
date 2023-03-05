package com.example.livedata_singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

//класс DataController реализован в шаблоне Singleton, это значит что объект данного класса будет существовать в единстевнном экземляре
//необходимость дополнительного класса, для хранения информации вызвана тем,
//что при повороте экрана и других деййствиях пользователя с активностью, активность может пересоздаваться и все переменные,
//которые хранятся внутри класса активности обнуляются,
//еще это позволяет хранить всю информацию в одном месте
public class DataController {
    //переменная, где мы храним количество кликов
    private int countClicks;

    //MutableLivedata - то-же самое, что и LiveData, но есть возможность менять значения внутри
    private MutableLiveData<Integer> countClicksLiveData;

    //переменная, где хранится единственный объект класса DataController
    //vilatile позволяет безопасно работать с переменной в многопотоке
    private static volatile DataController instance;

    //приватный конструктор, если бы он был публичным, мы бы смогли создать множество объектов класса DataController
    private DataController(){
        countClicks = 0;
        countClicksLiveData = new MutableLiveData<>(countClicks);
    }
    //метод для получения и создания instance, он немного усложнен, чтобы можно было вызываеть его в многопотоке без рисков
    public static DataController getInstance() {
        if(instance == null){
            synchronized (DataController.class){
                if(instance == null)
                    instance = new DataController();
            }
        }
        return instance;
    }
    //возвращаем LiveData, которая была преобразована из MutableLiveData. это возможно, потому что MutableLiveData наследник LiveData
    public LiveData<Integer> getCountLiveData() {
        return countClicksLiveData;
    }
    //метод для добавления 1 клика
    public void addClick(){
        countClicks++;
        //используем postValue, для многопотока, если же планируется только однопоточная программа возможно исользование setValue
        countClicksLiveData.postValue(countClicks);
    }
}
