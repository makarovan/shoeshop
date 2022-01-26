/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.mycomponents.director;

import app.mycomponents.buyer.*;
import app.GuiApp;
import app.mycomponents.ButtonComponent;
import app.mycomponents.CaptionComponent;
import app.mycomponents.ComboBoxBuyersComponents;
import app.mycomponents.EditorComponent;
import app.mycomponents.InfoComponent;
import app.mycomponents.ListPurchasesComponent;
import app.mycomponents.ListShoesComponent;
import entity.Shoes;
import entity.Purchase;
import entity.Buyer;
import facade.ShoesFacade;
import facade.PurchaseFacade;
import facade.BuyerFacade;
import facade.UserRolesFacade;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author pupil
 */
class TabIncomeComponent extends JPanel{
    private boolean isBuyer = true;
    private CaptionComponent captionComponent;
    private CaptionComponent incomeComponent;
    private CaptionComponent incomeMonthComponent;
    
    private InfoComponent infoComponent;
    //private ComboBoxPurchaseComponent comboBoxPurchaseComponent;
    private Buyer buyer;
    private ListPurchasesComponent listPurchasesComponent;
    private ComboBoxModel comboBoxModel;
    private PurchaseFacade purchaseFacade;
    private ShoesFacade shoesFacade = new ShoesFacade(); 
    //private BuyerFacade buyerFacade = new BuyerFacade(); 
    private Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Tallinn"));
    private UserRolesFacade userRolesFacade = new UserRolesFacade();
    private EditorComponent yearComponent;
    private EditorComponent monthComponent;
    private ButtonComponent buttonComponent;
    private Purchase purchase;
    
    public TabIncomeComponent(int widthPanel) {
        setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOWS, GuiApp.HEIGHT_WINDOWS));
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
        //setComboBoxModel();
        initComponents(widthPanel);
    }

    private void initComponents(int widthPanel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea((new Dimension(0,15))));
        captionComponent = new CaptionComponent("Доход магазина", widthPanel, 25);
        this.add(captionComponent);
        //this = tabIncomeComponent;

        infoComponent = new InfoComponent("", widthPanel, 25);
        this.add(infoComponent);
       
        this.listPurchasesComponent = new ListPurchasesComponent("Доход", 450, 80, 300);
        this.add(listPurchasesComponent);
        listPurchasesComponent.getJList().setEnabled(false);
        
        int income = 0;
        JList<Purchase> purchases = listPurchasesComponent.getJList();
        for(int i=0; i<purchases.getModel().getSize(); i++){
            income = income + purchases.getModel().getElementAt(i).getPrice();
        }
        
        String inc = String.valueOf(income);
        
        incomeComponent = new CaptionComponent("Доход магазина: " + inc, widthPanel, 25);
        this.add(incomeComponent);
        
        //yearComponent = new EditorComponent("Год", widthPanel, 31, 300);
        //this.add(yearComponent);
        
        //monthComponent = new EditorComponent("Месяц", widthPanel, 31, 300);
        //this.add(monthComponent);
        
        //incomeMonthComponent = new CaptionComponent("", 400, 25);
        
        //buttonComponent = new ButtonComponent("Доход за месяц", widthPanel, 35, 5, 300);
        //this.add(buttonComponent);
        //buttonComponent.getButton().addActionListener(clickToButtonMonthIncome());
    }

    private ActionListener clickToButtonMonthIncome() {
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                int year = Integer.parseInt(yearComponent.getEditor().getText());
                int month = Integer.parseInt(monthComponent.getEditor().getText());
                //эта строчка не работает
                //List<Purchase> purchases = purchaseFacade.findAll(year,month);
                
//                List<Purchase> purchasess = new ArrayList<>();
                
                //DefaultComboBoxModel<Purchase> defaultComboBoxModel = new DefaultComboBoxModel<>();
//                for (Purchase purchase : purchases) {
//                    purchasess.add(purchase);
//                }
                
                
                //int monthIncome = 0;
               // for (int i = 0; i<purchases.size(); i++) {
                    //monthIncome = monthIncome + Integer.parseInt(purchases.get(purchase));
                   // System.out.println(purchases.get(i));
                //}
//                int monthIncome = 0;
//                for (int i = 0; i < purchasess.size(); i++) {
//                    monthIncome = monthIncome + Integer.parseInt(purchasess.get(i).toString());
// 
//                }
//                
//                incomeMonthComponent.getEditor().setText(String.valueOf(monthIncome));
                
            }
        };
    }

}
    

