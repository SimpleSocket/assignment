package app.demo.utils;

import com.peper.blog.authentication.AuthenticationRequest;
import com.peper.blog.authentication.UserExtended;
import com.peper.blog.entities.EntryEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class ObjectGenerator {


    public ObjectGenerator(){

    }

    public EntryEntity createEntryEntity(Integer id, Integer userId, String tittle, String text){
        EntryEntity entryEntity = new EntryEntity();
        entryEntity.setIdEntry(id);
        entryEntity.setFkPersonidPerson(userId);
        entryEntity.setText(tittle);
        entryEntity.setTittle(text);
        return entryEntity;
    }

    public UserExtended createUserExtended(Integer id, String username, String password){
        UserExtended userExtended = new UserExtended(id, username, password);
        return userExtended;
    }

    public AuthenticationRequest createAuthenticationRequest(String email, String password){
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, password);
        return authenticationRequest;
    }


    public EntryEntity createDefaultEntryEntity(){
        EntryEntity entryEntity = createEntryEntity(0,0, "Tittle", "Text");
        return entryEntity;
    }

    public EntryEntity createEntryEntityWithBlankTittle(){
        EntryEntity entryEntity = createEntryEntity(0,0, "", "Text");
        return entryEntity;
    }

    public UserExtended createDefaultUserExtended(){
        UserExtended userExtended = createUserExtended(0, "test@mail.com", "password");
        return userExtended;
    }

    public AuthenticationRequest createDefaultAuthenticationRequest(){
        AuthenticationRequest authenticationRequest = createAuthenticationRequest("test@mail.com", "password");
        return authenticationRequest;
    }
}
