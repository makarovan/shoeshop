/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myclasses;

import entity.Buyer;
import entity.Purchase;
import entity.Shoes;
import entity.Shop;
import interfaces.Keeping;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import keeper.FileKeeper;

/**
 *
 * @author pupil
 */
public class App {
    Scanner scanner = new Scanner(System.in);
    List <Shoes> shoes = new ArrayList<>();
    List <Buyer> buyers = new ArrayList<>();
    List <Purchase> purchases = new ArrayList<>();
    List <Shop> shops = new ArrayList<>();
    Keeping keeper = new FileKeeper();
    
        
    public App(){
        shoes = keeper.loadShoes();
        buyers = keeper.loadBuyers();
        purchases = keeper.loadPurchases();
        shops = keeper.loadShop();
    }
    public void run(){
        String repeat = "y";
        do{
            System.out.println("Выберите задачу");
            System.out.println("0: закончить программу");
            System.out.println("1: добавить обувь");
            System.out.println("2: Вывести список моделей обуви");
            System.out.println("3: Добавить покупателя");
            System.out.println("4: Список покупателей");
            System.out.println("5: Сделать покупку");
            System.out.println("6: История покупок");
            System.out.println("7: Доход магазина");
            System.out.println("8: Добавить денег покупателю");
                
            int task = scanner.nextInt();
            scanner.nextLine();
            switch(task){
                case 0: 
                    repeat ="q";
                    System.out.println("Программа закончена");
                    break;
                
                case 1: 
                    System.out.println("Добавление модели обуви");
                    shoes.add(addShoes());
                    keeper.saveShoes(shoes);
                    break;
                    
                case 2:
                    printShoes();
                    break;
                
                case 3:
                    System.out.println("Добавления покупателя");
                    buyers.add(addBuyer());
                    keeper.saveBuyers(buyers);
                    break;
                
                case 4:
                    printBuyers();
                    break;
                    
                case 5:
                    System.out.println("Продажа модели");
                    purchases.add(addPurchase());
                    keeper.savePurchases(purchases);
                    keeper.saveShop(shops);
                    keeper.saveBuyers(buyers);//сохраняем изменение кол-ва денег у покупателя
                    keeper.saveShoes(shoes);
                    break;
                
                case 6:
                    System.out.println("История покупок");
                    int n = 0;
                    //int shopMoney = 0;
                    for (int i = 0; i < purchases.size(); i++) {
                        if (purchases.get(i)!=null){
                            System.out.printf("%d. Модель %s купил %s, покупка %s%n",
                                        (i+1),
                                        purchases.get(i).getShoes().getName(), 
                                        purchases.get(i).getBuyer().getName(),
                                        purchases.get(i).isCanceled());
                            n++;
//                            if (purchases.get(i).isCanceled()==true){
//                            shopMoney = shopMoney + purchases.get(i).getShoes().getPrice();
//                            }
                        }
                    }
                    if (n<1){
                        System.out.println("Нет проданных товаров");
                    }
                    //System.out.println("Shops Money: " + shopMoney/100);
                    break;
                    
                case 7:
                    System.out.print("Доход магазина: ");
                    shops.add(getMoneyShop());
                    System.out.println(shops.get(0).getMoney()/100);
                    break;
                    
                case 8:
                    System.out.println("Добавление средств покупателю");
                    printBuyers();
                    System.out.print("Выберите покупателя: ");
                    int buyerNum = scanner.nextInt();scanner.nextLine();
//                    if(buyers.contains(buyerNum-1)==false){ //подозрительный вызов?
//                        System.out.println("Покупатель не найден");
//                    }else{
                        System.out.print("Введите число денег для пополнения: ");
                        int moneyAdd = scanner.nextInt(); scanner.nextLine();
                        buyers.get(buyerNum-1).setMoney(buyers.get(buyerNum-1).getMoney()+ moneyAdd*100);
                        keeper.saveBuyers(buyers);
                     //}
                    break;
            }
        }while("y".equals(repeat));
    }
    
    private Shoes addShoes(){
        Shoes shoe = new Shoes();
        System.out.print("Название модели: ");
        shoe.setName(scanner.nextLine());
        System.out.print("Цена: ");
        shoe.setPrice(scanner.nextInt()*100);scanner.nextLine();
        System.out.print("Кол-во на складе: ");
        shoe.setAmount(scanner.nextInt());scanner.nextLine();
        return shoe;
    }
    
    private Buyer addBuyer(){
        Buyer buyer = new Buyer();
        System.out.print("Имя покупателя: ");
        buyer.setName(scanner.nextLine());
        System.out.print("Телефон покупателя: ");
        buyer.setPhone(scanner.nextInt());scanner.nextLine();
        System.out.print("Средства покупателя: ");
        buyer.setMoney(scanner.nextInt()*100);scanner.nextLine();
        return buyer;
    }
    
    private Purchase addPurchase(){
        Purchase purchase = new Purchase();

        printBuyers();
        System.out.print("Номер покупателя: ");
        int buyerNumber = scanner.nextInt();scanner.nextLine();
        
        printShoes();
        System.out.print("Номер модели: ");
        int shoesNumber = scanner.nextInt(); scanner.nextLine();
        //if(buyers.contains(buyerNumber-1)==false || shoes.contains(shoesNumber-1)==false){
        //    System.out.println("Покупатель или модель обуви не найдены");
        //}else{
            //если у покупателя денег больше, чем стоймость обуви, то 
            //записываем покупку, отнимаем деньги у покупателя, 
            //добавляем деньги магазину
            if(buyers.get(buyerNumber-1).getMoney()>shoes.get(shoesNumber-1).getPrice() && shoes.get(shoesNumber-1).getAmount()>0){
                    purchase.setBuyer(buyers.get(buyerNumber-1));
                    purchase.setShoes(shoes.get(shoesNumber-1));
                    purchase.setCanceled(true);//покупка прошла
                    buyers.get(buyerNumber-1).setMoney(buyers.get(buyerNumber-1).getMoney()-shoes.get(shoesNumber-1).getPrice());
                    shoes.get(shoesNumber-1).setAmount(shoes.get(shoesNumber-1).getAmount()-1);
                    shops.get(0).setMoney(shops.get(0).getMoney()+shoes.get(shoesNumber-1).getPrice());
            }else{
                purchase.setBuyer(buyers.get(buyerNumber-1));
                purchase.setShoes(shoes.get(shoesNumber-1));
                purchase.setCanceled(false);//покупка не прошла
            }
        //}
        return purchase;
    }
    
    private Shop getMoneyShop(){
        Shop shop = new Shop();
        shop.setMoney(0);
        return shop;
    }
    
    private void printBuyers(){
        System.out.println("Список покупателей");
        for (int i = 0; i < buyers.size(); i++) {
            if (buyers.get(i)!=null){
                System.out.printf("%d. %s%n", (i+1), buyers.get(i).toString()); 
            }
        }
    }
    
    private void printShoes(){
        System.out.println("Список моделей обуви:");
        for (int i = 0; i < shoes.size(); i++) {
            if (shoes.get(i)!=null){
                System.out.println((i+1)+ " " + shoes.get(i).toString()); 
            }
        }
    }
    
    
    
}
