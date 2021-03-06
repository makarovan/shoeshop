/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.mycomponents.buyer;

import app.GuiApp;
import app.mycomponents.ButtonComponent;
import app.mycomponents.CaptionComponent;
import app.mycomponents.ComboBoxBuyersComponents;
import app.mycomponents.InfoComponent;
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
import javax.swing.JPanel;

/**
 *
 * @author pupil
 */
class TabBuyShoesComponents extends JPanel{
    private boolean isBuyer = true;
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private ComboBoxBuyersComponents comboBoxBuyersComponents;
    private Buyer buyer;
    private ListShoesComponent listShoesComponent;
    private ButtonComponent buttonComponent;
    private ComboBoxModel comboBoxModel;
    private PurchaseFacade purchaseFacade = new PurchaseFacade();
    private ShoesFacade shoesFacade = new ShoesFacade(); 
    //private BuyerFacade buyerFacade = new BuyerFacade(); 
    private Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Tallinn"));
    private UserRolesFacade userRolesFacade = new UserRolesFacade();
    private Shoes shoes;
    
    public TabBuyShoesComponents(int widthPanel) {
        setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOWS, GuiApp.HEIGHT_WINDOWS));
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
        setComboBoxModel();
        initComponents(widthPanel);
    }

    private void initComponents(int widthPanel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea((new Dimension(0,15))));
        captionComponent = new CaptionComponent("?????????? ????????????", widthPanel, 25);
        this.add(captionComponent);

        infoComponent = new InfoComponent("", widthPanel, 25);
        this.add(infoComponent);
        
        //???????? ???? ????????????????????, ???? ???????????????????????? ???????????? ??????????????????????
        isBuyer = !userRolesFacade.isRole("MANAGER", GuiApp.user);
        if(!isBuyer){
            this.add(Box.createRigidArea(new Dimension(0,10)));
            comboBoxBuyersComponents = new ComboBoxBuyersComponents("????????????????????", widthPanel, 30, 300);
            comboBoxBuyersComponents.getComboBox().setModel(comboBoxModel);
            comboBoxBuyersComponents.getComboBox().setSelectedIndex(-1);
            comboBoxBuyersComponents.getComboBox().addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent ie) {
                    buyer=(Buyer) ie.getItem();
                }
            });
            this.add(comboBoxBuyersComponents);
            this.add(Box.createRigidArea(new Dimension(0,10)));
        }
        
        
        JCheckBox checkBoxAllShoes = new JCheckBox("???????????????? ?????? ????????????");
        checkBoxAllShoes.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if(ie.getStateChange() == ItemEvent.SELECTED){
                    listShoesComponent.getJList().setModel(listShoesComponent.getListModel(true));
                    buttonComponent.getButton().setEnabled(false);
                    listShoesComponent.getJList().setEnabled(false);
                }else{
                    listShoesComponent.getJList().setModel(listShoesComponent.getListModel(false));
                    listShoesComponent.getJList().setEnabled(true);
                    buttonComponent.getButton().setEnabled(true);
                }
            }
        });
        this.add(checkBoxAllShoes);
        
        this.listShoesComponent = new ListShoesComponent("??????????", 450, 80, 300);
        this.add(listShoesComponent);

        this.add(Box.createRigidArea((new Dimension(0,15))));
        buttonComponent = new ButtonComponent("???????????? ??????????", widthPanel, 35, 5, 200);
        this.add(buttonComponent);
        buttonComponent.getButton().addActionListener(clickToButtonBuyShoes());
    }

    private ActionListener clickToButtonBuyShoes(){
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(!isBuyer){
                    if(comboBoxBuyersComponents.getComboBox().getSelectedIndex() == -1){
                       infoComponent.getInfo().setForeground(Color.RED);
                       infoComponent.getInfo().setText("???? ???? ?????????????? ????????????????????");
                       return;
                    }
                }
                List<Shoes> shoess = listShoesComponent.getJList().getSelectedValuesList();
                if(shoess.isEmpty()){
                    infoComponent.getInfo().setText("???? ???? ?????????????? ??????????");
                    return;
                }
                
                //???? ????????????????
//                try{
//                    if(buyer.getMoney()<shoes.getPrice() && shoes.getAmount()>0){
//                        infoComponent.getInfo().setText("?? ?????? ???? ?????????????? ?????????????? ?????? ??????????????");
//                        return;
//                    }
//                }catch(Exception e){
//                    infoComponent.getInfo().setText("?? ?????? ???? ?????????????? ?????????????? ?????? ??????????????");
//                    return;
//                }
                
                
                try {
                    for (Shoes shoes : shoess) {
                        if(buyer.getMoney()<shoes.getPrice() && shoes.getAmount()>0){
                            infoComponent.getInfo().setText("?? ?????? ???? ?????????????? ?????????????? ?????? ??????????????");
                            return;
                        }
                        Purchase purchase = new Purchase();
                        shoes.setAmount(shoes.getAmount()-1);
                        shoesFacade.edit(shoes);
                        purchase.setShoes(shoes);
                        purchase.setPrice(shoes.getPrice());
                        BuyerFacade buyerFacade = new BuyerFacade();

                        if(isBuyer){
                            purchase.setBuyer(GuiApp.user.getBuyer());
                            GuiApp.user.getBuyer().setMoney(GuiApp.user.getBuyer().getMoney()-shoes.getPrice());
                            buyerFacade.edit(GuiApp.user.getBuyer());
                        }else{
                            purchase.setBuyer(buyer);
                            buyer.setMoney(buyer.getMoney()-shoes.getPrice());
                            buyerFacade.edit(buyer);
                        }
                        purchase.setMonth(cal.get(Calendar.MONTH)+1);
                        purchase.setYear(cal.get(Calendar.YEAR));
                        purchaseFacade.create(purchase);
                        infoComponent.getInfo().setForeground(Color.BLUE);
                        infoComponent.getInfo().setText("?????????? ??????????????");
                        if(!isBuyer){
                            comboBoxBuyersComponents.getComboBox().setSelectedIndex(-1);
                        }
                        listShoesComponent.getJList().setModel(listShoesComponent.getListModel(false));
                        listShoesComponent.getJList().clearSelection();
                    }
                } catch (Exception e) {
                    infoComponent.getInfo().setForeground(Color.RED);
                    infoComponent.getInfo().setText("?????????????? ???? ??????????????");
                }
            }
        };
    }
    private void setComboBoxModel(){
        BuyerFacade buyerFacade = new BuyerFacade();
        List<Buyer> buyers = buyerFacade.findAll();
        DefaultComboBoxModel<Buyer> defaultComboBoxModel = new DefaultComboBoxModel<>();
        for (Buyer buyer : buyers) {
            defaultComboBoxModel.addElement(buyer);
        }
        comboBoxModel = defaultComboBoxModel;
    }
    public void addComboBoxModel() {
        infoComponent.getInfo().setText("");
        setComboBoxModel();
        //comboBoxReadersComponents.getComboBox().setModel(comboBoxModel);
        comboBoxBuyersComponents.getComboBox().setSelectedIndex(-1);
    }
}
    

