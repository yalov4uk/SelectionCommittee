package interfaces.factories;

import interfaces.IBaseDao;

/**
 * Created by valera on 5/6/17.
 */
public interface IDaoFactory {

    IBaseDao getDao(Class some);
}
