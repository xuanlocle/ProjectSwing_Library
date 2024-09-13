package business;

import java.io.Serializable;
import java.time.LocalDate;

final public class CheckoutRecord implements Serializable {

    private static final long serialVersionUID = -1319816079467717030L;
//     private int checkOutID;
    private BookCopy bookCopy;
    private LocalDate dueDate;
    private LocalDate checkedOutDate;
    private double fine;
    private boolean isCheckedIn;

    public CheckoutRecord(LocalDate dueDate, BookCopy bookCopy) {
        this.dueDate = dueDate;
        this.bookCopy = bookCopy;
        this.checkedOutDate = LocalDate.now();
        this.isCheckedIn = false;
    }

    public Object[] toRowData(){
      return new Object[] {
              bookCopy.getBook().getIsbn(),
              bookCopy.getBook().getTitle(),
              checkedOutDate,
              dueDate,
      };
    };

    public LocalDate getCheckedOutDate() {
        return checkedOutDate;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public double getFine() {
        return fine;
    }

    public boolean getCheckInStatus() {
        return isCheckedIn;
    }

    public BookCopy getCheckOutBook() {
        return bookCopy;
    }

    public String toString() {
        return bookCopy.getBook().getIsbn() + "\t\t" + bookCopy.getBook().getTitle() + "\t\t\t" + checkedOutDate + "\t\t" + dueDate + "\t\t" + bookCopy.getCopyNum() + "\n";
    }

}

