/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.mycomponents.manager;

import app.mycomponents.ButtonComponent;
import app.mycomponents.CaptionComponent;
import app.mycomponents.ComboBoxBuyersComponents;
import app.mycomponents.ComboBoxShoesComponent;
import app.mycomponents.EditorComponent;
import app.mycomponents.InfoComponent;
import app.mycomponents.ListComponent;
import app.mycomponents.ListShoesComponent;
import entity.Buyer;
import entity.Shoes;
import facade.BuyerFacade;
import facade.ShoesFacade;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;

/**
 *
 * @author pupil
 */
public class TabEditShoesComponent extends JPanel{
    private CaptionComponent captionComponent;
    private ButtonComponent buttonComponent;
    private InfoComponent infoComponent;
    private ListComponent listComponent;
    private ComboBoxModel comboBoxModel;
    private EditorComponent shoesNameComponent;
    private EditorComponent priceComponent;
    private EditorComponent amountComponent;
    private ComboBoxShoesComponent comboBoxShoesComponent;
    private ListShoesComponent listShoesComponent;
    private Shoes shoes;
    
    public TabEditShoesComponent(int widthWindow) {
        initComponents(widthWindow);
        
    }

    private void initComponents(int widthWindow) {
        this.setPreferredSize(new Dimension(widthWindow, 300));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        captionComponent = new CaptionComponent("Изменить модель", widthWindow, 25);
        this.add(captionComponent);
        
        infoComponent = new InfoComponent("", widthWindow, 25);
        this.add(infoComponent);
        
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        
        //ERROR
        //попробовать не выпадающий список а табличку
//        comboBoxShoesComponent = new ComboBoxShoesComponent("Обувь", widthWindow, 30, 300);
//        //comboBoxShoesComponent.getComboBox().setModel(comboBoxModel);
//        comboBoxShoesComponent.getComboBox().setSelectedIndex(-1);
//        comboBoxShoesComponent.getComboBox().addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent ie) {
//                shoes=(Shoes) ie.getItem();
//                shoesNameComponent.getEditor().setText(shoes.getName());
//                Integer price = shoes.getPrice();
//                Integer amount = shoes.getAmount();
//                priceComponent.getEditor().setText(price.toString());
//                amountComponent.getEditor().setText(amount.toString());
//            }
//        });
//        this.add(comboBoxShoesComponent);
//        this.add(Box.createRigidArea(new Dimension(0,10)));

        this.add(Box.createRigidArea(new Dimension(0,10)));
            comboBoxShoesComponent = new ComboBoxShoesComponent("Обувь", widthWindow, 30, 300);
            //comboBoxShoesComponent.getComboBox().setModel(comboBoxModel);
            comboBoxShoesComponent.getComboBox().setSelectedIndex(-1);
            comboBoxShoesComponent.getComboBox().addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent ie) {
                    shoes=(Shoes) ie.getItem();
                    shoesNameComponent.getEditor().setText(shoes.getName());
                }
            });
            this.add(comboBoxShoesComponent);
        
//        this.listShoesComponent = new ListShoesComponent("Обувь", 450, 80, 300);
//        this.add(listShoesComponent);
        //listSelectionModel = listShoesComponent.gets
        
        shoesNameComponent = new EditorComponent("Название модели", widthWindow, 31, 300);
        this.add(shoesNameComponent);
        
        priceComponent = new EditorComponent("Цена", widthWindow, 31, 100);
        this.add(priceComponent);
        
        amountComponent = new EditorComponent("Колличество экземпляров", widthWindow, 31, 50);
        this.add(amountComponent);
        
        buttonComponent = new ButtonComponent("Изменить модель", widthWindow, 31, 178, 150);
        this.add(buttonComponent);
        buttonComponent.getButton().addActionListener(clickToButtonEditShoes());
    }
    
    private ActionListener clickToButtonEditShoes(){
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Shoes shoes = new Shoes();
                if(shoesNameComponent.getEditor().getText().isEmpty()){
                   infoComponent.getInfo().setText("введите название модели");
                   return; //exit from action
                }
                shoes.setName(shoesNameComponent.getEditor().getText());
                
                try{
                    shoes.setPrice(Integer.parseInt(priceComponent.getEditor().getText()));
                }catch(Exception e){
                    infoComponent.getInfo().setText("Введите цену цифрами");  
                    return;
                }
                shoes.setPrice(Integer.parseInt(priceComponent.getEditor().getText()));
                
                try{
                    shoes.setAmount(Integer.parseInt(amountComponent.getEditor().getText()));
                }catch(Exception e){
                    infoComponent.getInfo().setText("Введите колличество цифрами");  
                    return;
                }
                shoes.setAmount(Integer.parseInt(amountComponent.getEditor().getText()));
                
                ShoesFacade shoesFacade = new ShoesFacade();
                try{
                    shoesFacade.edit(shoes);
                    infoComponent.getInfo().setText("Модель успешно изменена");     
                    comboBoxShoesComponent.getComboBox().setSelectedIndex(-1);
                    shoesNameComponent.getEditor().setText("");
                    amountComponent.getEditor().setText("");
                    priceComponent.getEditor().setText("");
                }catch(Exception e){
                    infoComponent.getInfo().setText("Модель изменить не удалось");      
                }
                
            }
        };
    }
    public void addComboBoxModel() {
        ShoesFacade shoesFacade = new ShoesFacade();
        List<Shoes> shoess = shoesFacade.findAll();
        DefaultComboBoxModel<Shoes> defaultComboBoxModel = new DefaultComboBoxModel<>();
        for (Shoes shoes : shoess) {
            defaultComboBoxModel.addElement(shoes);
        }
        comboBoxShoesComponent.getComboBox().setModel(defaultComboBoxModel);
        comboBoxShoesComponent.getComboBox().setSelectedIndex(-1);
        shoesNameComponent.getEditor().setText("");
        amountComponent.getEditor().setText("");
        priceComponent.getEditor().setText("");
        infoComponent.getInfo().setText("");
    }
}
