package com.aison.test.design.factory.factoryMethod;

import com.aison.test.design.factory.abstractFactory.AbstractFactory;
import com.aison.test.design.factory.abstractFactory.ColorFactory;
import com.aison.test.design.factory.abstractFactory.ShapeFactory;

public class JavaEngineerFactory {
    public JobContent getEngineer(){
        return new JavaEngineer();
    }

}
