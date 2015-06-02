package pt.uc.dei.aor.paj.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    	System.out.println(value);
        String password = component.getAttributes().get("confirm").toString();
        System.out.println(password);

        for (String k : component.getAttributes().keySet()) {
        	System.out.println(k+" -> "+component.getAttributes().get(k));
        }
        if (value == null || password == null) {
            return;
        }
        
        if (!password.equals(value)) {
        	FacesMessage message = new FacesMessage();
            message.setDetail("Please enter a valid email");
            message.setSummary("Email not valid");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

}