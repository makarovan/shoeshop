/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.mycomponents.manager;

import app.mycomponents.ButtonComponent;
import app.mycomponents.CaptionComponent;
import app.mycomponents.ComboBoxShoesComponent;
import app.mycomponents.EditorComponent;
import app.mycomponents.InfoComponent;
import app.mycomponents.ListComponent;
import entity.Buyer;
import entity.Shoes;
import facade.BuyerFacade;
import facade.ShoesFacade;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class TabAddShoesComponent extends JPanel{
    private CaptionComponent captionComponent;
    private ButtonComponent buttonComponent;
    private InfoComponent infoComponent;
    private ListComponent listComponent;
    private EditorComponent shoesNameComponent;
    private EditorComponent priceComponent;
    private EditorComponent amountComponent;
    private ComboBoxModel comboBoxModel;
    private ComboBoxShoesComponent comboBoxShoesComponent;
    
    public TabAddShoesComponent(int widthWindow) {
        
        initComponents(widthWindow);
        
    }

    private void initComponents(int widthWindow) {
        this.setPreferredSize(new Dimension(widthWindow, 300));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        captionComponent = new CaptionComponent("Добавить новую модель", widthWindow, 25);
        this.add(captionComponent);
        
        infoComponent = new InfoComponent("", widthWindow, 25);
        this.add(infoComponent);
        
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        
        shoesNameComponent = new EditorComponent("Название модели", widthWindow, 31, 300);
        this.add(shoesNameComponent);
        
        priceComponent = new EditorComponent("Цена", widthWindow, 31, 100);
        this.add(priceComponent);
        
        amountComponent = new EditorComponent("Колличество экземпляров", widthWindow, 31, 50);
        this.add(amountComponent);
        
        buttonComponent = new ButtonComponent("Добавить модель", widthWindow, 31, 178, 150);
        this.add(buttonComponent);
        buttonComponent.getButton().addActionListener(clickToButtonAddShoes());
    }
    
    private ActionListener clickToButtonAddShoes(){
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                Shoes shoes = new Shoes();
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
                
                try{
                    shoes.setAmount(Integer.parseInt(amountComponent.getEditor().getText()));
                }catch(Exception e){
                    infoComponent.getInfo().setText("Введите колличество цифрами");  
                    return;
                }
                
                ShoesFacade shoesFacade = new ShoesFacade();
                try{
                    shoesFacade.create(shoes);
                    infoComponent.getInfo().setText("Модель успешно добавлена");     
                    shoesNameComponent.getEditor().setText("");
                    amountComponent.getEditor().setText("");
                    priceComponent.getEditor().setText("");
                }catch(Exception e){
                    infoComponent.getInfo().setText("Модель добавить не удалось");      
                }
                
            }
        };
    }
    
//    private void setComboBoxModel(){
//        ShoesFacade shoesFacade = new ShoesFacade();
//        List<Shoes> shoess = shoesFacade.findAll();
//        DefaultComboBoxModel<Shoes> defaultComboBoxModel = new DefaultComboBoxModel<>();
//        for (Shoes shoes : shoess) {
//            defaultComboBoxModel.addElement(shoes);
//        }
//        comboBoxModel = defaultComboBoxModel;
//    }
//     public void addComboBoxModel() {
//        infoComponent.getInfo().setText("");
//        setComboBoxModel();
//        //comboBoxReadersComponents.getComboBox().setModel(comboBoxModel);
//        comboBoxShoesComponents.getComboBox().setSelectedIndex(-1);
//    }
    
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
