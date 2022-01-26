/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.mycomponents.director;

import app.mycomponents.ButtonComponent;
import app.mycomponents.CaptionComponent;
import app.mycomponents.ComboBoxBuyersComponents;
import app.mycomponents.EditorComponent;
import app.mycomponents.InfoComponent;
import app.mycomponents.director.TabAddBuyerComponents;
import app.mycomponents.director.TabEditBuyerComponents;
import entity.Shoes;
import entity.Buyer;
import facade.ShoesFacade;
import facade.BuyerFacade;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author pupil
 */
public class TabDirectorComponent extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private EditorComponent readerNameComponent;
    private EditorComponent readerLastnameComponent;
    private EditorComponent readerPhoneComponent;
    private ButtonComponent buttonComponent;
    private ComboBoxModel comboBoxModel;
    private TabAddBuyerComponents tabAddReaderComponents;
    private ComboBoxBuyersComponents comboBoxReadersComponents;
    private TabEditBuyerComponents tabEditBuyerComponents;

    public TabDirectorComponent(int widthWindow) {
        //setComboBoxModel();
        initComponents(widthWindow);
    }

    private void initComponents(int widthPanel) {
        this.setPreferredSize(new Dimension(widthPanel, 450));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        
        JTabbedPane tabDirector = new JTabbedPane();
        tabDirector.setPreferredSize(new Dimension(widthPanel-20, 450));
        tabDirector.setMaximumSize(this.getPreferredSize());
        tabDirector.setMinimumSize(this.getPreferredSize());
        tabDirector.setAlignmentX(CENTER_ALIGNMENT);
        tabAddReaderComponents = new TabAddBuyerComponents(widthPanel);
        tabDirector.addTab("Регистрация", tabAddReaderComponents);
        
        //comboBoxReadersComponents = new ComboBoxReadersComponents("Читатели", widthPanel, 30, 300);
        tabEditBuyerComponents = new TabEditBuyerComponents(widthPanel, comboBoxReadersComponents);
        tabDirector.addTab("Редактировать покупателя", tabEditBuyerComponents);
        this.add(tabDirector);
        TabIncomeComponent tabIncomeComponent = new TabIncomeComponent(widthPanel);
        tabDirector.addTab("Доход магазина", tabIncomeComponent);
        
        tabDirector.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                //if(tabDirector.indexOfTab("Доход магазина")>0){
                    //tabIncomeComponent.addComboBoxModel();
                /*}else*/ if(tabDirector.indexOfTab("Редактировать покупателя")>0){
                    tabEditBuyerComponents.addComboBoxModel();
                }
            }
        });        
        
    }
//    private void setComboBoxModel(){
//        BuyerFacade buyerFacade = new BuyerFacade();
//        List<Buyer> buyers = buyerFacade.findAll();
//        DefaultComboBoxModel<Buyer> defaultComboBoxModel = new DefaultComboBoxModel<>();
//        for (Buyer buyer : buyers) {
//            defaultComboBoxModel.addElement(buyer);
//        }
//        comboBoxModel = defaultComboBoxModel;
//    }
    

}
