package ru.subbotin.utils;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import ru.subbotin.models.Cat;
import ru.subbotin.models.Owner;

public class HibernateSessionFactoryUtil {
  private static SessionFactory sessionFactory;
  private HibernateSessionFactoryUtil() { }
  public static SessionFactory getSessionFactory(){
    if (sessionFactory == null){
      try {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Cat.class);
        configuration.addAnnotatedClass(Owner.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
      } catch (Exception e){
        System.out.println(e.getMessage());
      }
    }

    return sessionFactory;
  }
}
