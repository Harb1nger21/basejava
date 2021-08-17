package ru.javawebinar.basejava.storage;


import java.io.File;

public class StrategyStorage{
    private final int strategyNumber;
    private final String fileName;
    private Strategy strategy;

    public StrategyStorage(int strategyNumber, String fileName) {
        this.strategyNumber = strategyNumber;
        this.fileName = fileName;
    }

    public Strategy getStrategy(){
        if(strategyNumber == 1){
            strategy = new ObjectStreamStorage(new File(fileName));
        }if(strategyNumber == 2){
            strategy = new ObjectStreamPathStorage(fileName);
        }
        return strategy;
    }
}
