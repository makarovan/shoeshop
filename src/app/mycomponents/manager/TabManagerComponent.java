/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.mycomponents.manager;

import app.mycomponents.ButtonComponent;
import app.mycomponents.CaptionComponent;
import app.mycomponents.EditorComponent;
import app.mycomponents.InfoComponent;
import app.mycomponents.ListComponent;
import entity.Shoes;
import facade.ShoesFacade;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author pupil
 */
public class TabManagerComponent extends JPanel{
    private CaptionComponent captionComponent;
    private ButtonComponent buttonComponent;
    private InfoComponent infoComponent;
    private ListComponent listComponent;
    private EditorComponent shoesNameComponent;
    private EditorComponent priceComponent;
    private EditorComponent amountComponent;
    private TabAddShoesComponent tabAddShoesComponent;
    private TabEditShoesComponent tabEditShoesComponent;
    
    public TabManagerComponent(int widthPanel) {
//        this.comboBoxReadersComponents = comboBoxReadersComponents;
//        this.comboBoxModel = comboBoxModel;
        initComponents(widthPanel);
    }

    private void initComponents(int widthPanel) {//error
        this.setPreferredSize(new Dimension(widthPanel, 450));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        
        JTabbedPane tabManager = new JTabbedPane();
        tabManager.setPreferredSize(new Dimension(widthPanel-20, 450));
        tabManager.setMaximumSize(this.getPreferredSize());
        tabManager.setMinimumSize(this.getPreferredSize());
        tabManager.setAlignmentX(CENTER_ALIGNMENT);
        tabAddShoesComponent = new TabAddShoesComponent(widthPanel);
        tabManager.addTab("Добавить обувь", tabAddShoesComponent);
        TabEditShoesComponent tabEditShoesComponent = new TabEditShoesComponent(widthPanel);
        tabManager.addTab("Редактировать обувь", tabEditShoesComponent);
        
        this.add(tabManager);
        
        tabManager.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                if(tabManager.indexOfTab("Добавить обувь") >0){
                    tabAddShoesComponent.addComboBoxModel();
                }else if(tabManager.indexOfTab("Редактировать обувь")>0){
                    tabEditShoesComponent.addComboBoxModel();
                }
            }
            
        });
    }
}