/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.mycomponents.director;

import app.GuiApp;
import app.mycomponents.ButtonComponent;
import app.mycomponents.CaptionComponent;
import app.mycomponents.EditorComponent;
import app.mycomponents.InfoComponent;
import entity.Buyer;
import facade.BuyerFacade;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author pupil
 */
public class TabAddBuyerComponents extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private EditorComponent buyerNameComponent;
    private EditorComponent loginComponent;
    private EditorComponent passwordComponent;
    private EditorComponent buyerPhoneComponent;
    private EditorComponent buyerMoneyComponent;
    private ButtonComponent buttonComponent;
    
    public TabAddBuyerComponents(int widthPanel) {
        //setPreferredSize(new Dimension(GuiApp.width_windows, GuiApp.height_window));
        //setMinimumSize(getPreferredSize());
        //setMaximumSize(getPreferredSize());
        initComponents(widthPanel);
    }

    private void initComponents(int widthPanel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        captionComponent = new CaptionComponent("Регистрация нового покупателя", widthPanel, 31);
        this.add(captionComponent);
        
        infoComponent = new InfoComponent("", widthPanel, 31);
        this.add(infoComponent);
        
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        
        buyerNameComponent = new EditorComponent("Имя покупателя", widthPanel, 31, 300);
        this.add(buyerNameComponent);
        
        loginComponent = new EditorComponent("Логин", widthPanel, 31, 200);
        this.add(loginComponent);
        
        passwordComponent = new EditorComponent("Пароль", widthPanel, 31, 200);
        this.add(passwordComponent);
        
        buyerPhoneComponent = new EditorComponent("Номер телефона покупателя", widthPanel, 31, 240);
        this.add(buyerPhoneComponent);
        
        buyerMoneyComponent = new EditorComponent("Количество средств у покупателя", widthPanel, 31, 240);
        this.add(buyerMoneyComponent);
        
        buttonComponent = new ButtonComponent("Добавить покупателя", widthPanel, 31, 160, 150);
        this.add(buttonComponent);
        buttonComponent.getButton().addActionListener(clickToButtonAddBuyer());
    }
    
    private ActionListener clickToButtonAddBuyer(){
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                Buyer buyer = new Buyer();
                if(buyerNameComponent.getEditor().getText().isEmpty()){
                   infoComponent.getInfo().setText("введите имя покупателя");
                   return; 
                }
                buyer.setName(buyerNameComponent.getEditor().getText());
                
                if(buyerPhoneComponent.getEditor().getText().isEmpty()){
                   infoComponent.getInfo().setText("введите телефон покупателя");
                   return;
                }
                buyer.setPhone(buyerPhoneComponent.getEditor().getText());
                
                if(buyerMoneyComponent.getEditor().getText().isEmpty()){
                   infoComponent.getInfo().setText("Кол-во стредств");
                   return;
                }
                buyer.setMoney(Integer.parseInt(buyerMoneyComponent.getEditor().getText()));
                
                BuyerFacade buyerFacade = new BuyerFacade();
                try{
                    buyerFacade.create(buyer);
                    infoComponent.getInfo().setText("Покупатель успешно добавлен");     
                    buyerNameComponent.getEditor().setText("");
                    buyerPhoneComponent.getEditor().setText("");
                    buyerMoneyComponent.getEditor().setText("");
                }catch(Exception e){
                    infoComponent.getInfo().setText("Покупателя добавить не удалось");      
                }
                
            }
        };
    }
    
}
