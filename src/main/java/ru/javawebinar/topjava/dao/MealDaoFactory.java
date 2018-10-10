package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MealDaoFactory {
    private static final Logger LOG = LoggerFactory.getLogger(MealDaoFactory.class);
    public static enum DAOSources{
        memory
    }

    public static MealDao getMealDAO(DAOSources src){
        switch (src){
            case memory:{
                return new MealDaoMemoryImpl();
            }
            default:{
                LOG.debug("dao source unrecognized: " + src);
                return null;
            }
        }
    }
}
