/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import app.mycomponents.ButtonComponent;
import app.mycomponents.CaptionComponent;
import app.mycomponents.EditorComponent;
import app.mycomponents.GuestButtonsComponent;
import app.mycomponents.GuestComponent;
import app.mycomponents.InfoComponent;
import app.mycomponents.ListComponent;
import app.mycomponents.director.TabAddBuyerComponents;
import app.mycomponents.manager.TabManagerComponent;
import app.mycomponents.director.TabDirectorComponent;
import app.mycomponents.buyer.TabBuyerComponent;
import entity.Shoes;
import entity.Buyer;
import entity.Role;
import entity.User;
import entity.UserRoles;
import facade.ShoesFacade;
import facade.BuyerFacade;
import facade.RoleFacade;
import facade.UserFacade;
import facade.UserRolesFacade;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author pupil
 */
public class GuiApp extends JFrame{
     public static final int WIDTH_WINDOWS = 600;
    public static final int HEIGHT_WINDOWS = 450;
    public static User user;
    public static String role;
    private InfoComponent infoTopComponent;
    private GuestComponent guestComponent;
    private GuestButtonsComponent guestButtonsComponent;
    private TabAddBuyerComponents tabAddBuyerComponents;
    private TabBuyerComponent tabBuyerComponent;
    private TabManagerComponent tabManagerComponent;
    private TabDirectorComponent tabDirectorComponent;
    private GuiApp guiApp = this;
    private UserFacade userFacade = new UserFacade();
    private BuyerFacade buyerFacade = new BuyerFacade();
    private RoleFacade roleFacade = new RoleFacade();
    private UserRolesFacade userRolesFacade = new UserRolesFacade();
    
    public GuiApp() {
        List<User> users = userFacade.findAll();
        if(users.isEmpty()){//creating admin
            User user = new User();
            user.setLogin("admin");
            user.setPassword("12345");
            Buyer buyer = new Buyer();
            buyer.setName("name");
            buyer.setPhone("1234567");
            buyer.setMoney(10);
            buyerFacade.create(buyer);
            user.setBuyer(buyer);
            userFacade.create(user);
            Role role = new Role();
            UserRoles userRoles = new UserRoles();
            role.setRoleName("ADMINISTRATOR");//ROLES SHOULD BE WRITTEN IN CAPS
            userRoles.setRole(role);
            userRoles = new UserRoles();
            userRoles.setUser(user);
            roleFacade.create(role);
            userRolesFacade.create(userRoles);
            role = new Role();
            role.setRoleName("MANAGER");
            userRoles.setRole(role);
            userRoles = new UserRoles();
            userRoles.setUser(user);
            roleFacade.create(role);
            userRolesFacade.create(userRoles);
            role = new Role();
            role.setRoleName("BUYER");
            userRoles.setRole(role);
            userRoles = new UserRoles();
            userRoles.setUser(user);
            roleFacade.create(role);
            userRolesFacade.create(userRoles);
        }
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        this.setTitle("JKTV20 Shoeshop");
        this.setPreferredSize(new Dimension(WIDTH_WINDOWS,HEIGHT_WINDOWS));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        infoTopComponent = new InfoComponent("", WIDTH_WINDOWS, 27);
        this.add(infoTopComponent);
        guestComponent = new GuestComponent();
        
        guestButtonsComponent = new GuestButtonsComponent("Войти", "Зарегистрироваться", GuiApp.WIDTH_WINDOWS, 50,100,10, 200);
        this.add(guestButtonsComponent);
       
        //войти
        guestButtonsComponent.getButton1().addActionListener(new ActionListener() {//actionlistener прописать для кнопки входа
            @Override
            public void actionPerformed(ActionEvent ae) {
                int widthWindows = 350;
                int heightWindows = 260;
                JDialog dialogLogin = new JDialog(guiApp,"Введите логин и пароль",Dialog.ModalityType.DOCUMENT_MODAL);
                dialogLogin.setPreferredSize(new Dimension(widthWindows,heightWindows));
                dialogLogin.setMaximumSize(dialogLogin.getPreferredSize());
                dialogLogin.setMinimumSize(dialogLogin.getPreferredSize());
                dialogLogin.getContentPane().setLayout(new BoxLayout(dialogLogin.getContentPane(), BoxLayout.Y_AXIS));
                dialogLogin.setLocationRelativeTo(null);
                CaptionComponent captionComponent = new CaptionComponent("Введите логин и пароль", widthWindows, 27);
                InfoComponent infoComponent = new InfoComponent("", widthWindows, 27);
                EditorComponent loginComponent = new EditorComponent("Логин", widthWindows, 27,80, 200);
                EditorComponent passwordComponent = new EditorComponent("Пароль", widthWindows, 27,80, 200);
                ButtonComponent enterComponent = new ButtonComponent("Войти", widthWindows, 27, 185, 100);
                dialogLogin.getContentPane().add(Box.createRigidArea(new Dimension(0,10)));
                dialogLogin.getContentPane().add(captionComponent);
                dialogLogin.getContentPane().add(Box.createRigidArea(new Dimension(0,5)));
                dialogLogin.getContentPane().add(infoComponent);
                dialogLogin.getContentPane().add(Box.createRigidArea(new Dimension(0,5)));
                dialogLogin.getContentPane().add(loginComponent);
                dialogLogin.getContentPane().add(Box.createRigidArea(new Dimension(0,5)));
                dialogLogin.getContentPane().add(passwordComponent);
                dialogLogin.getContentPane().add(Box.createRigidArea(new Dimension(0,15)));
                dialogLogin.getContentPane().add(enterComponent);
                enterComponent.getButton().addActionListener(new ActionListener() {
                @Override
                    public void actionPerformed(ActionEvent ae) {
                        //Аутентификация - узнать есть ли такой пользователь
                        User user = userFacade.find(loginComponent.getEditor().getText().trim());
                        if(user == null){
                            infoComponent.getInfo().setText("Нет такого пользователя");
                            return;
                        }
                        //Авторизация - он ли это пользователь и какие у него права.
                        if(!user.getPassword().equals(passwordComponent.getEditor().getText().trim())){
                            infoComponent.getInfo().setText("Нет такого пользователя, или неверный пароль");
                            return;
                        }
                        GuiApp.user = user;
                        //Пользователь тот за кого себя выдает, устанавливаем разрешения.
                        String role = userRolesFacade.topRole(user);
                        GuiApp.role = role;
                        infoTopComponent.getInfo().setText("Hello "+user.getBuyer().getName());
                        guiApp.getContentPane().remove(guestComponent);
                        guiApp.getContentPane().remove(guestButtonsComponent);
                        JTabbedPane jTabbedPane = new JTabbedPane();
                        jTabbedPane.setPreferredSize(new Dimension(WIDTH_WINDOWS,HEIGHT_WINDOWS));
                        jTabbedPane.setMinimumSize(jTabbedPane.getPreferredSize());
                        jTabbedPane.setMaximumSize(jTabbedPane.getPreferredSize());
                        if("BUYER".equals(GuiApp.role)){
                            tabBuyerComponent = new TabBuyerComponent(GuiApp.WIDTH_WINDOWS);
                            jTabbedPane.addTab("Покупатель", tabBuyerComponent);
                        }else if("MANAGER".equals(GuiApp.role)){
                            tabBuyerComponent = new TabBuyerComponent(GuiApp.WIDTH_WINDOWS);
                            jTabbedPane.addTab("Покупатель", tabBuyerComponent);
                            tabManagerComponent = new TabManagerComponent(GuiApp.WIDTH_WINDOWS);
                            jTabbedPane.addTab("Продавец", tabManagerComponent);
                        }else if("ADMINISTRATOR".equals(GuiApp.role)){
                            tabBuyerComponent = new TabBuyerComponent(GuiApp.WIDTH_WINDOWS);
                            jTabbedPane.addTab("Покупатель", tabBuyerComponent);
                            tabManagerComponent = new TabManagerComponent(GuiApp.WIDTH_WINDOWS);
                            jTabbedPane.addTab("Продавец", tabManagerComponent);
                            tabDirectorComponent = new TabDirectorComponent(GuiApp.WIDTH_WINDOWS);
                            jTabbedPane.addTab("Директор", tabDirectorComponent);
                        }
                        guiApp.getContentPane().add(jTabbedPane);
                        guiApp.repaint();
                        guiApp.revalidate();
                        dialogLogin.setVisible(false);
                        dialogLogin.dispose();
                    }
                });
                dialogLogin.pack();
                dialogLogin.setVisible(true);
                
            }
        });
        
        //зарегестрироваться
        guestButtonsComponent.getButton2().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                guiApp.getContentPane().remove(guestComponent);
                tabAddBuyerComponents = new TabAddBuyerComponents(GuiApp.WIDTH_WINDOWS);
                guiApp.getContentPane().add(tabAddBuyerComponents);
                guiApp.repaint();
                guiApp.revalidate();
            }
        });
        if(GuiApp.user == null){
           this.add(guestComponent);
        }
        
                
        
        
//        JTabbedPane jTabbedPane = new JTabbedPane();
//        this.setPreferredSize(new Dimension(width_windows,height_window));
//        this.setMinimumSize(jTabbedPane.getPreferredSize());
//        this.setMaximumSize((jTabbedPane.getPreferredSize()));
//        this.getContentPane().add(jTabbedPane);
//        
//        TabReaderComponent tabReaderComponent = new TabReaderComponent(this.getWidth());
//        jTabbedPane.addTab("Читатель", tabReaderComponent);
//        
//        TabLibrarianComponent tabLibrarianComponent = new TabLibrarianComponent(this.getWidth());
//        jTabbedPane.addTab("Библиотекарь", tabLibrarianComponent);
//        this.getContentPane().add(jTabbedPane);
//        
//        TabDirectorComponent tabDirectorComponent = new TabDirectorComponent(this.getWidth());
//        jTabbedPane.addTab("Директор", tabDirectorComponent);
    }
    
    public static void main(String[] args) {//psvm + tab
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GuiApp().setVisible(true);//show window
            } //project properties - run - browse main class - guiapp
        });
    }

}
