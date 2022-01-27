/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import entity.Buyer;
import entity.Purchase;
import entity.Shoes;
import entity.Shop;
import interfaces.Keeping;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TimeZone;
import keeper.FileKeeper;
import keeper.BaseKeeper;

/**
 *
 * @author pupil
 */
public class App {
    public static boolean isBase;
    private Scanner scanner = new Scanner(System.in);
    private Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Tallinn"));
    private List <Shoes> shoes = new ArrayList<>();
    private List <Buyer> buyers = new ArrayList<>();
    private List <Purchase> purchases = new ArrayList<>();
    //private List <Shop> shops = new ArrayList<>();
    private Keeping keeper;
    
        
    public App(){
        if(App.isBase){
            keeper = new BaseKeeper(); //база данных
        }else{
            keeper = new FileKeeper(); //файлы
        }
        shoes = keeper.loadShoes();
        buyers = keeper.loadBuyers();
        purchases = keeper.loadPurchases();
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
            System.out.println("9: Редактирование товара");
            System.out.println("10: Редактирование покупателя");
                
            int task = getNumber();
            switch(task){
                case 0: 
                    repeat ="q";
                    System.out.println("Программа закончена");
                    break;
                
                case 1: 
                    addShoes();
                    break;
                    
                case 2:
                    printShoes();
                    break;
                
                case 3:
                    addBuyer();
                    break;
                
                case 4:
                    printBuyers();
                    break;
                    
                case 5:
                    addPurchase();
                    break;
                
                case 6:
                    purchasesHistory();
                    break;
                    
                case 7:
                    shopMoney();
                    break;
                    
                case 8:
                    addMoneyToBuyer();
                    break;
                    
                case 9:
                    updateShoes();
                    break;
                
                case 10:
                    updateBuyer();
                    break;
            }
        }while("y".equals(repeat));
    }
    
    private void addShoes(){
        System.out.println("Добавление модели обуви");
        if(quit()) return;
        Shoes shoe = new Shoes();
        System.out.print("Название модели: ");
        shoe.setName(scanner.nextLine());
        System.out.print("Цена: ");
        shoe.setPrice(getNumber()*100);
        System.out.print("Кол-во на складе: ");
        shoe.setAmount(getNumber());
        shoes.add(shoe);
        keeper.saveShoes(shoes);
    }
    
    private void addBuyer(){
        System.out.println("Добавления покупателя");
        if(quit()) return;
        Buyer buyer = new Buyer();
        System.out.print("Имя покупателя: ");
        buyer.setName(scanner.nextLine());
        System.out.print("Телефон покупателя: ");
        buyer.setPhone(scanner.nextLine());
        System.out.print("Средства покупателя: ");
        buyer.setMoney(getNumber()*100);
        buyers.add(buyer);
        keeper.saveBuyers(buyers);
    }
    
    private void addPurchase(){
        System.out.println("Продажа модели");
        if(quit()) return;
        Purchase purchase = new Purchase();
        
        Set<Integer> setNumbersBuyers = printBuyers();
        if(setNumbersBuyers.isEmpty()){
            return;
        } 
        System.out.print("Номер покупателя: ");
        int buyerNumber = insertNumber(setNumbersBuyers);
        purchase.setBuyer(buyers.get(buyerNumber-1));
        
        Set <Integer> setNumbersShoes = printShoes();
        if(setNumbersShoes.isEmpty()){//если все книги выданы, то не дает выдать
            return;
        }
        System.out.print("Номер модели: ");
        int shoesNumber = insertNumber(setNumbersShoes);
        purchase.setShoes(shoes.get(shoesNumber-1));

        //если у покупателя денег больше, чем стоймость обуви, то 
        //записываем покупку, отнимаем деньги у покупателя, 
        //добавляем деньги магазину
        if(buyers.get(buyerNumber-1).getMoney()>shoes.get(shoesNumber-1).getPrice() && shoes.get(shoesNumber-1).getAmount()>0){
            purchase.setBuyer(buyers.get(buyerNumber-1));
            purchase.setShoes(shoes.get(shoesNumber-1));
            //purchase.setBought(true);//покупка прошла
            purchase.setPrice(shoes.get(shoesNumber-1).getPrice());
            buyers.get(buyerNumber-1).setMoney(buyers.get(buyerNumber-1).getMoney()-shoes.get(shoesNumber-1).getPrice());
            shoes.get(shoesNumber-1).setAmount(shoes.get(shoesNumber-1).getAmount()-1);
            purchase.setMonth(cal.get(Calendar.MONTH));
            purchase.setYear(cal.get(Calendar.YEAR));
        }else{
            purchase.setBuyer(buyers.get(buyerNumber-1));
            purchase.setShoes(shoes.get(shoesNumber-1));
            purchase.setMonth(cal.get(Calendar.MONTH));
            purchase.setYear(cal.get(Calendar.YEAR));
            purchase.setPrice(shoes.get(shoesNumber-1).getPrice());
            //purchase.setBought(false);//покупка не прошла
        }

        purchases.add(purchase);
        keeper.savePurchases(purchases);
        keeper.saveBuyers(buyers);//сохраняем изменение кол-ва денег у покупателя
        keeper.saveShoes(shoes);
    }

    private Set<Integer> printBuyers(){
        Set<Integer> setNumbersBuyers = new HashSet();
        System.out.println("Список покупателей");
        for (int i = 0; i < buyers.size(); i++) {
            if (buyers.get(i)!=null){
                System.out.printf("%d. %s%n", (i+1), buyers.get(i).toString()); 
                setNumbersBuyers.add(i+1);
            }
        }
        if(setNumbersBuyers.isEmpty()){
            System.out.println("Список покупателей пуст");
        }
        return setNumbersBuyers;
    }
    
    private Set<Integer> printShoes(){
        Set <Integer> setNumbersShoes = new HashSet();
        System.out.println("Список моделей обуви:");
        for (int i = 0; i < shoes.size(); i++) {
            if (shoes.get(i)!=null && shoes.get(i).getAmount()>0){
                System.out.println((i+1)+ " " + shoes.get(i).toString());
                setNumbersShoes.add(i+1);
            }else if(shoes.get(i)!=null){
                System.out.println("%d. %s нет в наличии.");
            }
        }
        return setNumbersShoes;
    }
    
    private int getNumber(){
        do{
            try{
                String strNumber = scanner.nextLine();
                return Integer.parseInt(strNumber);
            }catch(Exception e){
                System.out.println("Попробуйте еще раз");
            }
        }while(true);
    }
    
    private boolean quit(){
        System.out.println("Чтобы закончить операцию нажмите \"q\", для продолжения любой другой символ");
        String quit = scanner.nextLine();
        if("q".equals(quit)) return true;
      return false;
    }
    
    private int insertNumber(Set<Integer> setNumbers){
        do{
            int historyNumber = getNumber();
            if (setNumbers.contains(historyNumber)){
                return historyNumber; 
            }
            System.out.println("Попробуй еще раз");
        }while(true);  
    }
    
    private Set<Integer> purchasesHistory(){
        System.out.println("История покупок");
        Set<Integer> setNumberPurchases = new HashSet();
        for (int i = 0; i < purchases.size(); i++) {
            if (purchases.get(i)!=null){
                System.out.printf("%d. Модель %s купил %s, покупка, стоймость: %d%n",
                            (i+1),
                            purchases.get(i).getShoes().getName(), 
                            purchases.get(i).getBuyer().getName(),
                            //purchases.get(i).isBought(),
                            purchases.get(i).getPrice()/100
                        );
                setNumberPurchases.add(i+1);
            }
        }
        if(setNumberPurchases.isEmpty()){
            System.out.println("Список покупок пуст");
        }
        return setNumberPurchases;
    }
    
     private Set<Integer> shopMoney(){
        Set <Integer> setNumberPurchases = new HashSet();
        System.out.print("Доход магазина. Введите месяц: ");
        int monthMoney = getNumber();
        System.out.print("Введите год: ");
        int yearMoney = getNumber();
        int money=0;
        for (int i = 0; i < purchases.size(); i++) {
            if (purchases.get(i)!=null && 
                    purchases.get(i).getMonth()+1 == monthMoney && 
                    purchases.get(i).getYear()==yearMoney /*&&
                    purchases.get(i).isBought()==true*/){
                money = money+purchases.get(i).getPrice();
                setNumberPurchases.add(i+1);
            }
        }
        if(setNumberPurchases.isEmpty()){
            System.out.println("Список покупок пуст");
        }else{
            System.out.println("Доход магазина за "+monthMoney+"."+yearMoney +" : " + money/100);
        }
        return setNumberPurchases;
    }
    
    private void addMoneyToBuyer(){
        System.out.println("Добавление средств покупателю");
        if(quit()) return;
        
        Set<Integer> setNumbersBuyers = printBuyers();
        if(setNumbersBuyers.isEmpty()){
            return;
        }
        
        System.out.print("Номер покупателя: ");
        int buyerNumber = insertNumber(setNumbersBuyers);
        System.out.print("Введите число денег для пополнения: ");
        int moneyAdd = scanner.nextInt(); scanner.nextLine();
        buyers.get(buyerNumber-1).setMoney(buyers.get(buyerNumber-1).getMoney()+ moneyAdd*100);
        keeper.saveBuyers(buyers);
    }
    
    private void updateShoes(){
        System.out.println("Изменение модели обуви");
        Set <Integer> setNumbersShoes = printShoes();
        if(setNumbersShoes.isEmpty()){//если книг нет то закроется
            return;
        }
        System.out.print("Введите номер модели: ");
        int shoesNumber = insertNumber(setNumbersShoes);
        System.out.println("Редактировать название:" + shoes.get(shoesNumber-1).getName());
        System.out.print("y/n: ");
        String answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("Введите новое название: ");
            shoes.get(shoesNumber-1).setName(scanner.nextLine());
        }
        System.out.println("Редактировать цену:" + shoes.get(shoesNumber-1).getPrice()/100);
        System.out.print("y/n: ");
        answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("Введите новую цену: ");
            shoes.get(shoesNumber-1).setPrice(getNumber()*100);
        }
        System.out.println("Изменить количество экземпляров? Сейчас на складе: " + shoes.get(shoesNumber-1).getAmount());
        System.out.print("y/n: ");
        answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("Введите количество: ");
            int newAmount;
            do{
                newAmount = getNumber();
                if(newAmount >= 0){
                    break;
                }
                System.out.println("Попробуй еще");
            }while(true);
            shoes.get(shoesNumber-1).setAmount(newAmount);
        }
        keeper.saveShoes(shoes);  
    }

    private void updateBuyer(){
        System.out.println("Изменение данных покупателя");
        if(quit()) return;
        Set <Integer> setNumbersBuyers = printBuyers();
        if(setNumbersBuyers.isEmpty()){//если книг нет то закроется
            return;
        }
        System.out.print("Введите номер покупателя из списка: ");
        int buyerNumber = insertNumber(setNumbersBuyers);
        System.out.println("Редактировать поле название:" + buyers.get(buyerNumber-1).getName());
        System.out.print("y/n: ");
        String answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("Введите новое имя: ");
            buyers.get(buyerNumber-1).setName(scanner.nextLine());
        }
        System.out.println("Редактировать телефон:" + buyers.get(buyerNumber-1).getPhone());
        System.out.print("y/n: ");
        answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("Введите новый телефон: ");
            buyers.get(buyerNumber-1).setPhone(scanner.nextLine());
        }
        System.out.println("Изменить количество денег:" + buyers.get(buyerNumber-1).getMoney()/100);
        System.out.print("y/n: ");
        answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("Введите количество: ");
            int newMoney;
            do{
                newMoney = getNumber();
                if(newMoney >= 0){
                    break;
                }
                System.out.println("Попробуй еще");
            }while(true);
            buyers.get(buyerNumber-1).setMoney(newMoney*100);
        }
        keeper.saveBuyers(buyers); 
    }
}