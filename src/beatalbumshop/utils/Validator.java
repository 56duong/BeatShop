package beatalbumshop.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.text.JTextComponent;

public class Validator {

    public static boolean isNotNull(JTextComponent txt, String input) {
        if((input == null || input.isBlank()) && txt != null) txt.requestFocus();
        return input != null && !input.isBlank();
    }
    
    

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
