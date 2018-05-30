package org.khtn.group12.cgg.model;

public class BookTicketSelected {
    private String row;
    private String numbers;

    public BookTicketSelected(String row, String numbers) {
        this.row = row;
        this.numbers = numbers;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}
