package com.dongfang.spring.mvc.bean;

public class Book {
    private String bookName;
    private String author;
    private Double price;
    private Integer sales;

    public Book() {
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"bookName\":\"")
                .append(bookName).append('\"');
        sb.append(",\"author\":\"")
                .append(author).append('\"');
        sb.append(",\"price\":")
                .append(price);
        sb.append(",\"sales\":")
                .append(sales);
        sb.append('}');
        return sb.toString();
    }
}
