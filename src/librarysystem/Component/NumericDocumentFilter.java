package librarysystem.Component;

import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NumericDocumentFilter extends DocumentFilter {
    // Default max length
    private int maxLength = 100;

    private NumericDocumentFilter() {}
    public NumericDocumentFilter(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr)
            throws BadLocationException {
        if (string != null &&
                fb.getDocument().getLength() + string.length() <= maxLength &&
                string.matches("\\d+")) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs)
            throws BadLocationException {
        if (text.isEmpty()) {
            super.replace(fb, offset, length, text, attrs);
            return;
        }

        if (fb.getDocument().getLength() - length + text.length() <= maxLength && text.matches("\\d+")) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        super.remove(fb, offset, length);  // Allow deletion
    }
}
