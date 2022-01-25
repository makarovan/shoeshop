/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Shoes;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import tools.Singleton;

/**
 *
 * @author pupil
 */
public class ShoesFacade extends AbstractFacade<Shoes>{

    private EntityManager em;
    
    public ShoesFacade() {
        super(Shoes.class);
        Singleton singleton = Singleton.getInstance();
        em = singleton.getEntityManager();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Shoes> findEnabledShoes() {
        try {
            return em.createQuery("SELECT b FROM Shoes b WHERE b.amount > 0")
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
        
    }
    
}