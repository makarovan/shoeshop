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
import javax.swing.JTextField;

/**
 *
 * @author pupil
 */
public class CaptionComponent extends JPanel{
    private JTextField editor;

    public CaptionComponent(String text, int widthWindow, int heightPanel) {
        initComponents(text, widthWindow, heightPanel);
    }

    private void initComponents(String text, int widthWindow, int heightPanel) {
        this.setPreferredSize(new Dimension(widthWindow, heightPanel));//width equals widthwindow width
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
//        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel caption = new JLabel(text);
        caption.setPreferredSize(new Dimension(widthWindow, heightPanel));
        caption.setMaximumSize(caption.getPreferredSize());
        caption.setMinimumSize(caption.getPreferredSize());
//        caption.setBorder(BorderFactory.createLineBorder(Color.YELLOW));//border around caption
        caption.setHorizontalAlignment(JLabel.CENTER);
        caption.setFont(new Font("Tahoma", 1,16)); //1 - bold
        this.add(caption);
    }
    
    public JTextField getEditor() {
        return editor;
    }
}
