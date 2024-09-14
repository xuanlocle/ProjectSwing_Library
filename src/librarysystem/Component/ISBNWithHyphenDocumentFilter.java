package librarysystem.Component;

import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class ISBNWithHyphenDocumentFilter extends DocumentFilter {

    @Override
    public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr)
            throws BadLocationException {
        if (string != null && isNumber(string)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs)
            throws BadLocationException {
        if (text != null && isValidInput(fb.getDocument().getText(0, fb.getDocument().getLength()), text)) {
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            String combinedText = currentText + text;

            if (combinedText.length() == 3 ||
                    combinedText.length() == 5 ||
                    combinedText.length() == 9 ||
                    combinedText.length() == 15) {
                text += "-";
            }
            super.replace(fb, offset, length, text, attrs);
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        super.remove(fb, offset, length);  // Allow removal without restriction
    }

    private boolean isNumber(String newText) {
        return newText.matches("\\d+");
    }

    // Check if the input is valid (only digits and hyphens)
    private boolean isValidInput(String currentText, String newText) {
        // Combine the current text with the new input
        String combinedText = currentText + newText;

        // Check if the combined text only contains digits and hyphens
        if (!combinedText.matches("[0-9-]*")) {
            return false;
        }

        // Check the number of digits (should not exceed 13 digits)
        int digitCount = combinedText.replace("-", "").length();
        return digitCount <= 13;
    }
}
