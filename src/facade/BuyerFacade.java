/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Buyer;
import javax.persistence.EntityManager;
import tools.Singleton;

/**
 *
 * @author pupil
 */
public class BuyerFacade extends AbstractFacade<Buyer>{
    private EntityManager em;
    
    public BuyerFacade() {//constructor
        super(Buyer.class);
        Singleton singleton = Singleton.getInstance();
        em = singleton.getEntityManager();
    }



    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
