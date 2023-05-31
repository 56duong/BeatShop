package beatalbumshop.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.text.JTextComponent;

/**
 * The Validator class provides utility methods for validating input data.
 */
public class Validator {

    /**
     * Checks if the input string is not null or empty and sets focus on the associated text component if it is empty.
     *
     * @param txt the associated text component
     * @param input the input string to be validated
     * @return true if the input is not null or empty, false otherwise
     */
    public static boolean isNotNull(JTextComponent txt, String input) {
        if((input == null || input.isBlank()) && txt != null) txt.requestFocus();
        return input != null && !input.isBlank();
    }
    
    

    /**
     * Validates an input string to allow only alphanumeric characters.
     *
     * @param txt the associated text component
     * @param title the title or description of the input
     * @param input the input string to be validated
     * @param allowNull specifies if the input can be null or empty
     * @return an error message if the input is invalid, an empty string if the input is valid
     */
    public static String allowNumberText(JTextComponent txt, String title, String input, Boolean allowNull) {
        if(!isNotNull(null, input) && !allowNull) {
            if(txt != null) txt.requestFocus();
            return "Vui lòng nhập " + title + "\n";
        }
        else if(isNotNull(null, input) && !input.matches("[a-zA-Z0-9]+")) {
            if(txt != null) txt.requestFocus();
            return title + " chỉ được chứa các kí tự [a-zA-Z0-9]" + "\n";
        }
        
        return "";
    }
    
    
    
    /**
     * Validates an input string to allow alphanumeric characters and spaces.
     *
     * @param txt the associated text component
     * @param title the title or description of the input
     * @param input the input string to be validated
     * @param allowNull specifies if the input can be null or empty
     * @return an error message if the input is invalid, an empty string if the input is valid
     */
    public static String allowNumberTextSpace(JTextComponent txt, String title, String input, Boolean allowNull) {
        if(!isNotNull(null, input) && !allowNull) {
            if(txt != null) txt.requestFocus();
            return "Vui lòng nhập " + title + "\n";
        }
        else if(isNotNull(null, input) && !input.matches("[a-zA-Z0-9\\s]+")) {
            if(txt != null) txt.requestFocus();
            return title + " chỉ được chứa các kí tự [a-zA-Z0-9 và kí tự khoảng trắng]" + "\n";
        }
        
        return "";
    }
    
    
    
    /**
     * Validates an input string to allow only numeric characters.
     *
     * @param txt the associated text component
     * @param title the title or description of the input
     * @param input the input string to be validated
     * @param allowNull specifies if the input can be null or empty
     * @return an error message if the input is invalid, an empty string if the input is valid
     */
    public static String allowNumber(JTextComponent txt, String title, String input, Boolean allowNull) {
        if(!isNotNull(null, input) && !allowNull) {
            if(txt != null) txt.requestFocus();
            return "Vui lòng nhập " + title + "\n";
        }
        else if(isNotNull(null, input) && !input.matches("[0-9]+")) {
            if(txt != null) txt.requestFocus();
            return title + " chỉ được chứa các kí tự [0-9]" + "\n";
        }
        
        return "";
    }
    
    

    /**
     * Validates an input string to allow a double value.
     *
     * @param txt the associated text component
     * @param title the title or description of the input
     * @param input the input string to be validated
     * @param allowNull specifies if the input can be null or empty
     * @return an error message if the input is invalid, an empty string if the input is valid
     */
    public static String allowDouble(JTextComponent txt, String title, String input, Boolean allowNull) {
        if(isNotNull(null, input) && !input.matches("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$")) {
            if(txt != null) txt.requestFocus();
            return title + " sai định dạng" + "\n";
        }
        else if(!isNotNull(null, input) && !allowNull) {
            if(txt != null) txt.requestFocus();
            return "Vui lòng nhập " + title + "\n";
        }
        
        return "";
    }
    
    

    /**
     * Validates an input string to allow only Vietnamese characters and spaces.
     *
     * @param txt the associated text component
     * @param title the title or description of the input
     * @param input the input string to be validated
     * @param allowNull specifies if the input can be null or empty
     * @return an error message if the input is invalid, an empty string if the input is valid
     */
    public static String allowVietnameseSpace(JTextComponent txt, String title, String input, Boolean allowNull) {
        if(!isNotNull(null, input) && !allowNull) {
            if(txt != null) txt.requestFocus();
            return "Vui lòng nhập " + title + "\n";
        }
        else if(isNotNull(null, input) && !input.matches("[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+")) {
            if(txt != null) txt.requestFocus();
            return title + " chỉ được chứa các kí tự chữ cái và kí tự khoảng trắng" + "\n";
        }
        
        return "";
    }
    
    

    /**
     * Validates an input string to ensure it matches the email format.
     *
     * @param txt the associated text component
     * @param title the title or description of the input
     * @param input the input string to be validated
     * @param allowNull specifies if the input can be null or empty
     * @return an error message if the input is invalid, an empty string if the input is valid
     */
    public static String email(JTextComponent txt, String title, String input, Boolean allowNull) {
        if(isNotNull(null, input) && !input.matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$")) {
            if(txt != null) txt.requestFocus();
            return title + " sai định dạng" + "\n";
        }
        else if(!isNotNull(null, input) && !allowNull) {
            if(txt != null) txt.requestFocus();
            return "Vui lòng nhập " + title + "\n";
        }
        
        return "";
    }
    
    

    /**
     * Validates an input string to ensure it matches the date format (yyyy-MM-dd).
     *
     * @param txt the associated text component
     * @param title the title or description of the input
     * @param input the input string to be validated
     * @param allowNull specifies if the input can be null or empty
     * @return an error message if the input is invalid, an empty string if the input is valid
     */
    public static String date(JTextComponent txt, String title, String input, Boolean allowNull) {
        if(isNotNull(null, input)) {
            if(!input.matches("^\\d+\\-\\d+\\-\\d+")) {
                if(txt != null) txt.requestFocus();
                return title + " phải theo định dạng yyyy-mm-dd" + "\n";
            }
            else {
                //tao dinh dang yyyy-MM-dd
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                df.setLenient(false);
                try {
                    df.parse(input);
                } catch (ParseException ex) {
                    if(txt != null) txt.requestFocus();
                    return title + " phải theo định dạng yyyy-mm-dd" + "\n";
                }
            }
        }
        else if(!isNotNull(null, input) && !allowNull) {
            if(txt != null) txt.requestFocus();
            return "Vui lòng nhập " + title + "\n";
        }
        
        return "";
    }
    
    

    /**
     * Validates an input string to ensure it is a double value between 0 and 10.
     *
     * @param txt the associated text component
     * @param title the title or description of the input
     * @param input the input string to be validated
     * @param allowNull specifies if the input can be null or empty
     * @return an error message if the input is invalid, an empty string if the input is valid
     */
    public static String DoubleGrade0To10(JTextComponent txt, String title, String input, Boolean allowNull) {
        if(isNotNull(null, input) && !input.matches("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$")) {
            if(txt != null) txt.requestFocus();
            return title + " sai định dạng" + "\n";
        }
        else if(!isNotNull(null, input) && !allowNull) {
            if(txt != null) txt.requestFocus();
            return "Vui lòng nhập " + title + "\n";
        }
        else if(isNotNull(null, input)) {
            Double inputDouble = Double.parseDouble(input);
            if(inputDouble < 0 || inputDouble > 10) {
                if(txt != null) txt.requestFocus();
                return title + " phải từ 0-10" + "\n";
            }
        }
        
        return "";
    }

}
