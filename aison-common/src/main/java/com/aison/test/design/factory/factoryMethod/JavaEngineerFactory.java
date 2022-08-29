package com.aison.test.design.factory.factoryMethod;

public class JavaEngineerFactory {
    public JobContent getEngineer(){
        return new JavaEngineer();
    }

}
