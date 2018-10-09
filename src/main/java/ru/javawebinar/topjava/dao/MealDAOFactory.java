package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MealDAOFactory {
    private static final Logger LOG = LoggerFactory.getLogger(MealDAOFactory.class);
    public static enum DAOSources{
        memory
    }

    public static MealDAO getMealDAO(DAOSources src){
        switch (src){
            case memory:{
                return new MealDAOMemoryImpl();
            }
            default:{
                LOG.debug("dao source unrecognized: " + src);
                return null;
            }
        }
    }
}
