/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.mycomponents.buyer;

import app.mycomponents.ButtonComponent;
import app.mycomponents.CaptionComponent;
import app.mycomponents.ComboBoxBuyersComponents;
import app.mycomponents.EditorComponent;
import app.mycomponents.InfoComponent;
import app.mycomponents.ListShoesComponent;
//import app.mycomponents.TabReturnBookComponents;
import entity.Buyer;
import facade.BuyerFacade;
import static java.awt.Component.CENTER_ALIGNMENT;
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
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.omg.CosNaming.NameComponent;

/**
 *
 * @author pupil
 */
public class TabBuyerComponent extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private ComboBoxModel comboBoxModel;
    private EditorComponent buyerNameComponent;
    private EditorComponent buyerMoneyComponent;
    private EditorComponent buyerPhoneComponent;
    private ComboBoxBuyersComponents comboBoxReadersComponents;
    private ListShoesComponent listShoesComponent;
    private ButtonComponent buttonComponent;
    private TabBuyShoesComponents tabBuyShoesComponents;
   // private TabEditBuyerComponents tabEditBuyerComponents;
    private Buyer buyer;
    
    public TabBuyerComponent(int widthPanel) {
//        this.comboBoxReadersComponents = comboBoxReadersComponents;
//        this.comboBoxModel = comboBoxModel;
        initComponents(widthPanel);
    }

    private void initComponents(int widthPanel) {//error
        this.setPreferredSize(new Dimension(widthPanel, 450));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        
        JTabbedPane tabBuyer = new JTabbedPane();
        tabBuyer.setPreferredSize(new Dimension(widthPanel-20, 450));
        tabBuyer.setMaximumSize(this.getPreferredSize());
        tabBuyer.setMinimumSize(this.getPreferredSize());
        tabBuyer.setAlignmentX(CENTER_ALIGNMENT);
        tabBuyShoesComponents = new TabBuyShoesComponents(widthPanel);
        tabBuyer.addTab("Покупка обуви", tabBuyShoesComponents);
        TabEditBuyerComponents tabEditBuyerComponents = new TabEditBuyerComponents(widthPanel, comboBoxReadersComponents);
        tabBuyer.addTab("Редактировать покупателя", tabEditBuyerComponents);
        
        this.add(tabBuyer);
        
        tabBuyer.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                if(tabBuyer.indexOfTab("Покупка обуви") >0){
                    tabBuyShoesComponents.addComboBoxModel();
                }else if(tabBuyer.indexOfTab("Редактировать покупателя")>0){
                    tabEditBuyerComponents.addComboBoxModel();
                }
            }
            
        });
    }
}