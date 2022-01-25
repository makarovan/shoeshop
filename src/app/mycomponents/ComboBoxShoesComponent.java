/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.mycomponents;

import entity.Buyer;
import entity.Shoes;
import facade.BuyerFacade;
import facade.ShoesFacade;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import static javax.swing.SwingConstants.TOP;
import javax.swing.UIManager;

/**
 *
 * @author pupil
 */
public class ComboBoxShoesComponent extends JPanel{
    private JLabel caption;
    private JComboBox<Shoes> comboBox;

    public ComboBoxShoesComponent(String text, int widthWindow, int heightPanel, int listWidth) {
        initComponents(text, widthWindow, heightPanel, listWidth);
    }

    private void initComponents(String text, int widthWindow, int heightPanel, int listWidth) {
        this.setPreferredSize(new Dimension(widthWindow, heightPanel));//width equals widthwindow width
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        caption = new JLabel(text);
        caption.setPreferredSize(new Dimension(widthWindow/3, 27));
        caption.setMaximumSize(caption.getPreferredSize());
        caption.setMinimumSize(caption.getPreferredSize());
        caption.setHorizontalAlignment(JLabel.RIGHT);

        caption.setAlignmentY(CENTER_ALIGNMENT);
        caption.setFont(new Font("Tahoma", 0,12)); // 0 - not bold
        this.add(caption);
        
        this.add(Box.createRigidArea(new Dimension(5,0))); //space between components in box
        
        comboBox = new JComboBox<>();
        //comboBox.setModel(getListModel());
        comboBox.setPreferredSize(new Dimension(listWidth, 27));
        comboBox.setMaximumSize(comboBox.getPreferredSize());
        comboBox.setMinimumSize(comboBox.getPreferredSize());
        comboBox.setRenderer(createListShoesRenderer());
        //сколько элементов выпадают при открытыи списка без прокрутки
        comboBox.setMaximumRowCount(5); 
        //comboBox.setSelectedIndex(-1);
        this.add(comboBox);
    }

    private ComboBoxModel<Shoes> getListModel() {
        ShoesFacade shoesFacade = new ShoesFacade(); //инициировали фасад
        List<Shoes> shoess = shoesFacade.findAll();
        DefaultComboBoxModel<Shoes> defaultComboBoxModel = new DefaultComboBoxModel<>();
        for (Shoes shoes : shoess){
            defaultComboBoxModel.addElement(shoes); 
        }
        return defaultComboBoxModel;
    }

    private ListCellRenderer<? super Shoes> createListShoesRenderer() {
        return new DefaultListCellRenderer(){
            private final Color background = new Color(0,100,255,15);
            private final Color defaultbackground = (Color) UIManager.getColor("List.background");
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                                boolean isSelected, boolean cellHasFocus){
                Component component = super.getListCellRendererComponent(list, value, TOP, isSelected, cellHasFocus);
                if(component instanceof JLabel){
                    JLabel label = (JLabel) component;
                    Shoes shoes = (Shoes) value;
                    if(shoes == null) return component;
                    label.setText(String.format("%d. %s, %s евро%n", 
                                                shoes.getId(),
                                                shoes.getName(),
                                                shoes.getPrice()));
                    if(!isSelected){
                        label.setBackground(index % 2 == 0? background : defaultbackground);
                    }
                }
                return component;
            }
        };
    }
    public JComboBox<Shoes> getComboBox() {
        return comboBox;
    }
}