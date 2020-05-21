package DCOMS;

/**
 *
 * @author fnuke
 */
class Order implements java.io.Serializable {

    private int orderid,quantity, status;
    private String itemname,itemid,customerid;
    private double total;    
        
    public Order(int orderid, String itemname, int quantity, double total) {

        this.orderid=orderid;
        this.itemname=itemname;
        this.quantity=quantity;
        this.total=total;
    }

    public Order(int orderid,String itemid, String itemname,String customerid, int quantity, double total, int status) {   
        this.orderid=orderid;
        this.itemid=itemid;
        this.customerid=customerid;
        this.itemname=itemname;
        this.quantity=quantity;
        this.total=total;   
        this.status = status; 
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}