/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


public class News {
    private int id;
    private String title;
    private String image;
    private String content;
    private int newCategory;
    private int accountId;

    public News() {
    }

    public News(int id, String title, String content, int newCategory, int accountId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.newCategory = newCategory;
        this.accountId = accountId;
    }
     public News( String title, String image,String content, int newCategory, int accountId) {
        this.title = title;
         this.image = image;
        this.content = content;
        this.newCategory = newCategory;
        this.accountId = accountId;
    }

    public News(int id, String title, String image, String content, int newCategory, int accountId) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.content = content;
        this.newCategory = newCategory;
        this.accountId = accountId;
    }
     
     
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNewCategory() {
        return newCategory;
    }

    public void setNewCategory(int newCategory) {
        this.newCategory = newCategory;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    
    
}