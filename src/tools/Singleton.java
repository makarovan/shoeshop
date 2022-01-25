/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author pupil
 */
public class Singleton {
    private static Singleton instance;
    private EntityManager entityManager;
    private Singleton(){//приватный конструктор, нельзя вызвать извне, только изнутри
        EntityManagerFactory emf = Persistence.
                createEntityManagerFactory("shoeshopjktv20PU");
        entityManager = emf.createEntityManager();//инициируем em
    }
    public static Singleton getInstance(){
        if(instance == null){//если объект еще не создан
            instance = new Singleton();	//создать новый объект
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
    
}
