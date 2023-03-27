package ru.subbotin.dao;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.subbotin.models.Cat;
import ru.subbotin.models.Owner;
import ru.subbotin.utils.HibernateSessionFactoryUtil;

public class DaoImpl implements Dao {
  public void saveOwner(Owner owner) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    session.save(owner);
    transaction.commit();
    session.close();
  }

  public void updateOwner(Owner owner) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    session.update(owner);
    transaction.commit();
    session.close();
  }

  public void deleteOwner(Owner owner) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    session.delete(owner);
    transaction.commit();
    session.close();
  }

  public Owner findOwnerByID(int id) throws HibernateException {
    return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Owner.class, id);
  }

  public Cat findCatByID(int id) throws HibernateException{
    return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Cat.class, id);
  }

  public ArrayList<Owner> getAllOwners(){
    return  (ArrayList<Owner>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Owners").list();
  }
}
