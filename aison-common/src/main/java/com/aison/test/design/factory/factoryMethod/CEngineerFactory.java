package com.aison.test.design.factory.factoryMethod;

public class CEngineerFactory {
    public JobContent getEngineer(){
        return new CEngineer();
    }
}
