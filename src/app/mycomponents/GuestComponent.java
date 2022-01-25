/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.mycomponents;

import app.GuiApp;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author pupil
 */
public class GuestComponent extends JPanel{
    private ListShoesComponent listShoesComponent;
    private GuestButtonsComponent guestButtonsComponent;

    public GuestComponent() {
        setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOWS, GuiApp.HEIGHT_WINDOWS));
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
        initComponents();
    }

    private void initComponents() {
        //this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        listShoesComponent = new ListShoesComponent(true, "Список моделей обуви", GuiApp.WIDTH_WINDOWS, GuiApp.HEIGHT_WINDOWS-100, 400);//now showing
        //listBooksComponent.getJList().setModel(listBooksComponent.getListModel(true));
        //listBooksComponent.getJList().setCellRenderer(listBooksComponent.createListBooksRenderer());
        this.add(listShoesComponent);

    }

    
    
    
    public ListShoesComponent getListShoesComponent() {
        return listShoesComponent;
    }

}
