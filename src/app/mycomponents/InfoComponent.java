/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.mycomponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author pupil
 */
public class InfoComponent extends JPanel{
    private JLabel info;
    
    public InfoComponent(String text, int widthWindow, int heightPanel) {
        initComponents(text, widthWindow, heightPanel);
    }

    private void initComponents(String text, int widthWindow, int heightPanel) {
        this.setPreferredSize(new Dimension(widthWindow, heightPanel));//width equals widthwindow width
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
//        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        info =  new JLabel(text);
        info.setPreferredSize(new Dimension(widthWindow, heightPanel));
        info.setMaximumSize(info.getPreferredSize());
        info.setMinimumSize(info.getPreferredSize());
//        caption.setBorder(BorderFactory.createLineBorder(Color.YELLOW));//border around caption
        info.setHorizontalAlignment(JLabel.CENTER);
        info.setFont(new Font("Tahoma", 0,12)); 
        info.setForeground(Color.red);
        this.add(info);
    }

    public JLabel getInfo() {
        return info;
    }
    
}
