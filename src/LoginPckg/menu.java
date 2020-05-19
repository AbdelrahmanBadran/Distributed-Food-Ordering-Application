/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginPckg;

/**
 *
 * @author Taonga Lungu
 */
class menu implements java.io.Serializable{ 
    private int itemNumber;
    private double price;
    private String name, category,description;

   public int getitemNumber(){
       return itemNumber;
   }
   
   public void setitemNumber(int itemNumber){
       this.itemNumber = itemNumber;
   }
   
   public double getprice(){
       return price;
   }
    
   public void setprice(double price){
       this.price = price;
   } 
   
   public String getname(){
       return name;
   }
   
   public void setname (String name){
       this.name = name;
   }
   
   public String getCat(){
       return category;
   }
   
   public void setCat(String category){
       this.category = category;
   }
   
   public String getDes (){
       return description;
   }
   
   public void setDes (String description){
       this.description = description;
   }
}
