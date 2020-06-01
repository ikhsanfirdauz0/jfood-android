package ikhsan.firdauzjfood_android;

public class Food
{
    private int id;
    private String name;
    private int price;
    private String category;
    private Seller seller;

    /**
     * this is the constructor for objects in Food class
     * @param id is id of the food
     * @param name is name of the food
     * @param seller is refering to some object of Seller class
     * @param price is price of the food
     * @param category is the category of the food
     */
    public Food(int id, String name, Seller seller, int price, String category)
    {
        this.id = id;
        this.name = name;
        this.seller = seller;
        this.price = price;
        this.category = category;
    }

    /**
     * this is the getter of food's id
     * @return id of the food
     */
    public int getId()
    {
        return id;
    }

    /**
     * this is the getter of food's name
     * @return name of the food
     */
    public String getName()
    {
        return name;
    }

    /**
     * this is the getter of food's price
     * @return price of the food
     */
    public int getPrice()
    {
        return price;
    }

    /**
     * this is the getter of food's category
     * @return category of the food
     */
    public String getCategory()
    {
        return category;
    }

    /**
     * this is the getter of food's seller object to call any parts of the seller class
     * @return any parts of Seller's object
     */
    public Seller getSeller()
    {
        return seller;
    }

    /**
     * this is the setter of food's id
     * @param id is the id of food
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * this is the setter of food's name
     * @param name is the name of food
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * this is the setter of food's price
     * @param price is price of food
     */
    public void setPrice(int price)
    {
        this.price = price;
    }

    /**
     * this is the setter of food's category
     * @param category is category of food
     */
    public void setCategory(String category)
    {
        this.category = category;
    }

    /**
     * this is the setter of seller
     */
    public void setSeller(Seller seller)
    {
        this.seller = seller;
    }

    public String toString()
    {
        return "==========FOOD==========" +
                "\nId = " + getId() +
                "\nName = " + getName() +
                "\nSeller = " + getSeller().getName() +
                "\nCity = " + seller.getLocation().getCity() +
                "\nPrice = " + getPrice() +
                "\nCategory = " + getCategory() + "\n";
    }

}
