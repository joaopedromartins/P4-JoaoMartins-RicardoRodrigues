package pt.uc.dei.aor.paj.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import pt.uc.dei.aor.paj.MusicEJB;


@FacesValidator("titleValidator")
public class TitleValidator implements Validator {
	
	@Inject
	private MusicEJB ejb;
	
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    	String title = (String) value;
    	UIInput artistComp = (UIInput) component.getAttributes().get("artist");
    	String artist = (String) artistComp.getSubmittedValue();
    	
    	UIInput albumComp = (UIInput) component.getAttributes().get("album");
    	String album = (String) albumComp.getSubmittedValue();
    	
    	if (title == null || artist == null) {
            return;
        }
        
        if (ejb.findMusicByTitleArtistAlbum(title, artist, album).size() > 0) {
        	throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Music already added to the database", null));
        }
    }

}