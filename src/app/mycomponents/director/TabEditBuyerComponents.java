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
import entity.Buyer;
import entity.User;
import facade.BuyerFacade;
import facade.UserFacade;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import org.omg.CosNaming.NameComponent;

/**
 *
 * @author pupil
 */
class TabEditBuyerComponents extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private ComboBoxModel comboBoxModel;
    private ComboBoxBuyersComponents comboBoxBuyersComponents;
    private EditorComponent buyerNameComponent;
    private EditorComponent buyerPhoneComponent;
    private EditorComponent buyerMoneyComponent;
    private EditorComponent buyerLoginComponent;
    private EditorComponent buyerPasswordComponent;
    private ButtonComponent buttonComponent;
    private Buyer buyer;
    private User user;
    
    public TabEditBuyerComponents(int widthPanel, ComboBoxBuyersComponents comboBoxBuyersComponents) {
//        this.comboBoxReadersComponents = comboBoxReadersComponents;
        this.comboBoxModel = comboBoxModel;
        initComponents(widthPanel);
    }

    private void initComponents(int widthPanel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea((new Dimension(0,15))));
        captionComponent = new CaptionComponent("Редактировать данные покупателя", widthPanel, 25);
        this.add(captionComponent);
        
        infoComponent = new InfoComponent("", widthPanel, 25);
        this.add(infoComponent);
        this.add(Box.createRigidArea(new Dimension(0,15)));

        comboBoxBuyersComponents = new ComboBoxBuyersComponents("Покупатели", widthPanel, 30, 300);
//        comboBoxReadersComponents.getComboBox().setModel(comboBoxModel);
        comboBoxBuyersComponents.getComboBox().setSelectedIndex(-1);
        comboBoxBuyersComponents.getComboBox().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                buyer = (Buyer) ie.getItem();
                //user = (User) ie.getItem();
                buyerNameComponent.getEditor().setText(buyer.getName());
                buyerPhoneComponent.getEditor().setText(buyer.getPhone());
                Integer money = buyer.getMoney();
                buyerMoneyComponent.getEditor().setText(money.toString());
                //buyerLoginComponent.getEditor().setText(user.getLogin());
                //buyerPasswordComponent.getEditor().setText(user.getPassword());
            }
        });
        this.add(comboBoxBuyersComponents);
        
        buyerNameComponent = new EditorComponent("Имя покупателя", widthPanel, 31, 300);
        this.add(buyerNameComponent);
        
        buyerPhoneComponent = new EditorComponent("Номер телефона покупателя", widthPanel, 31, 195);
        this.add(buyerPhoneComponent);
        
        buyerMoneyComponent = new EditorComponent("Кол-во стредств", widthPanel, 31, 195);
        this.add(buyerMoneyComponent);
        
        buyerLoginComponent = new EditorComponent("Логин", widthPanel, 31, 195);
        this.add(buyerLoginComponent);
        
        buyerPasswordComponent = new EditorComponent("Пароль", widthPanel, 31, 195);
        this.add(buyerPasswordComponent);
        
        this.add(Box.createRigidArea((new Dimension(0,15))));
        buttonComponent = new ButtonComponent("Изменить данные покупателя", widthPanel, 35, 5, 300);
        this.add(buttonComponent);
        buttonComponent.getButton().addActionListener(clickToButtonEditBuyer());
    }
    
    private ActionListener clickToButtonEditBuyer(){//error
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(buyerNameComponent.getEditor().getText().isEmpty()){
                   infoComponent.getInfo().setText("введите имя покупателя");
                   return; 
                }
                buyer.setName(buyerNameComponent.getEditor().getText());
                
                if(buyerPhoneComponent.getEditor().getText().isEmpty()){
                   infoComponent.getInfo().setText("введите телефон читателя");
                   return;
                }
                buyer.setPhone(buyerPhoneComponent.getEditor().getText());
                
                if(buyerMoneyComponent.getEditor().getText().isEmpty()){
                   infoComponent.getInfo().setText("Кол-во стредств");
                   return;
                }
                buyer.setMoney(Integer.parseInt(buyerMoneyComponent.getEditor().getText()));
                
                UserFacade userFacade = new UserFacade();
                String login = buyerLoginComponent.getEditor().getText();
                user = userFacade.find(login);
                //нужен вариант если пользователя нет
                if(user!=null){
                    if(buyerLoginComponent.getEditor().getText().isEmpty()){
                        infoComponent.getInfo().setText("введите логин читателя");
                        return;
                    }
                    user.setLogin(buyerLoginComponent.getEditor().getText());

                    if(buyerPasswordComponent.getEditor().getText().isEmpty()){
                        infoComponent.getInfo().setText("введите пароль читателя");
                        return;
                    }
                    user.setPassword(buyerPasswordComponent.getEditor().getText());
                    
                    user.setBuyer(buyer);
                    userFacade.edit(user);
                }
//                else{
//                    
//                }
                
                
                BuyerFacade buyerFacade = new BuyerFacade();
                //UserFacade userFacade = new UserFacade();
                
                try{
                    buyerFacade.edit(buyer);
                    infoComponent.getInfo().setText("Читатель успешно изменен");     
                    comboBoxBuyersComponents.getComboBox().setSelectedIndex(-1);
                    buyerNameComponent.getEditor().setText("");
                    buyerPhoneComponent.getEditor().setText("");
                    buyerMoneyComponent.getEditor().setText("");
                    buyerLoginComponent.getEditor().setText("");
                    buyerPasswordComponent.getEditor().setText("");
                }catch(Exception e){
                    infoComponent.getInfo().setText("читателя изменить не удалось");      
                }
            }
        };
    }
    public void addComboBoxModel() {
        BuyerFacade buyerFacade = new BuyerFacade();
        List<Buyer> buyers = buyerFacade.findAll();
        DefaultComboBoxModel<Buyer> defaultComboBoxModel = new DefaultComboBoxModel<>();
        for (Buyer buyer : buyers) {
            defaultComboBoxModel.addElement(buyer);
        }

        comboBoxBuyersComponents.getComboBox().setModel(defaultComboBoxModel);
        comboBoxBuyersComponents.getComboBox().setSelectedIndex(-1);
        buyerNameComponent.getEditor().setText("");
        buyerMoneyComponent.getEditor().setText("");
        buyerPhoneComponent.getEditor().setText("");
        infoComponent.getInfo().setText("");
    }
}
