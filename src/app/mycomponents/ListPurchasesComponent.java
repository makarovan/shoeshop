/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.mycomponents;

import entity.Shoes;
import entity.Purchase;
import entity.Buyer;
import facade.ShoesFacade;
import facade.PurchaseFacade;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.Locale;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

/**
 *
 * @author pupil
 */
public class ListPurchasesComponent extends JPanel{
    private JLabel caption;
    private JList<Purchase> list;

    public ListPurchasesComponent(String text, int widthWindow, int heightPanel, int listWidth) {
        initComponents(text, widthWindow, heightPanel, listWidth);
    }

    
    private void initComponents(String text, int widthWindow, int heightPanel, int listWidth) {
        this.setPreferredSize(new Dimension(widthWindow, heightPanel));//width equals widthwindow width
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        caption = new JLabel(text);
        caption.setPreferredSize(new Dimension(widthWindow/3, heightPanel));
        caption.setMaximumSize(caption.getPreferredSize());
        caption.setMinimumSize(caption.getPreferredSize());
        caption.setHorizontalAlignment(JLabel.RIGHT);
        caption.setVerticalAlignment(JLabel.TOP);
        caption.setFont(new Font("Tahoma", 0,12)); // 0 - not bold
        this.add(caption);
        
        this.add(Box.createRigidArea(new Dimension(5,0))); //space between components in box

        list = new JList<>();
        list.setModel(getListModel());
        list.setCellRenderer(createListPurchasesRenderer());
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HEIGHT);
        
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setViewportView(list);
        listScroller.setPreferredSize(new Dimension(listWidth,120));
        listScroller.setMaximumSize(listScroller.getPreferredSize());
        listScroller.setMinimumSize(listScroller.getPreferredSize());
        listScroller.setAlignmentX(LEFT_ALIGNMENT);
        listScroller.setAlignmentY(TOP_ALIGNMENT);
        this.add(listScroller);
    }

    /**
     * Метод возвращает модель со списком доступных для выдачи книг
     * @return объект DefaultListModel
     */
    public ListModel<Purchase> getListModel(){
       return getListModel(false);
    }

    public ListModel<Purchase> getListModel(boolean allPurchases) {
        PurchaseFacade purchaseFacade = new PurchaseFacade();
        List<Purchase> purchases=null;
        purchases = purchaseFacade.findAll();
        DefaultListModel<Purchase> defaultListModel = new DefaultListModel<>();
        for (Purchase purchase : purchases) {
            defaultListModel.addElement(purchase);
        }
        return defaultListModel;
    }

    private ListCellRenderer<? super Purchase> createListPurchasesRenderer() {
        return new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                            boolean isSelected, boolean cellHasFocus){
                Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(component instanceof JLabel){
                    JLabel label = (JLabel) component;
                    Purchase purchase = (Purchase) value;
                    label.setText(String.format("%d. Модель: %s. Цена: %d."
                                ,purchase.getId()
                                ,purchase.getShoes().getName()
                                ,purchase.getShoes().getPrice()
                    ));
                }
                return component;
            }
        }; 
    }

    public JList<Purchase> getJList() {
        return list;
    }
    
}