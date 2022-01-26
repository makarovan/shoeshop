/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Shoes;
import entity.Purchase;
import entity.Buyer;
import java.util.List;
import javax.persistence.EntityManager;
import tools.Singleton;

/**
 *
 * @author pupil
 */
public class PurchaseFacade extends AbstractFacade<Purchase>{
    //три поля из basekeeper    
    private EntityManager em;
    
    public PurchaseFacade() {
        super(Purchase.class);
        Singleton singleton = Singleton.getInstance();
        em = singleton.getEntityManager();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
//    public Purchase findHistoryByGivenBook(Book book) {
//        //механизм защиты от инъекций
//        return (History) getEntityManager().createQuery("SELECT history FROM history WHERE history.book = :book AND history.returnDate = null")
//                .setParameter("book", book)
//                .getSingleResult();
//    }
    
//    public List<History> findHistoryWithGivenBooks(){
//        return getEntityManager().createQuery("SELECT h FROM History h WHERE h.returnDate = null").getResultList();
//    }

    public List<Purchase> findAll(int year, int month) {
        return getEntityManager().createQuery("SELECT h.price FROM Purchase h WHERE h.year = :year AND h.month = month")
                .setParameter(year, month)
                .getResultList();
    }

    
}
