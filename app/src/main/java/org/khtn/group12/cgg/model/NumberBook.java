package org.khtn.group12.cgg.model;

public class NumberBook {
    private boolean isBook;
    private String nameNumber;
    private boolean isSelect;

    public NumberBook() {
    }

    public NumberBook(boolean isBook, String nameNumber, boolean isSelect) {
        this.isBook = isBook;
        this.nameNumber = nameNumber;
        this.isSelect = isSelect;
    }

    public boolean isBook() {
        return isBook;
    }

    public void setBook(boolean book) {
        isBook = book;
    }

    public String getNameNumber() {
        return nameNumber;
    }

    public void setNameNumber(String nameNumber) {
        this.nameNumber = nameNumber;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
