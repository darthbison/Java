package providerSpring;

import javax.inject.Provider;

import org.springframework.beans.factory.annotation.Autowired;


public class Index {

    private String message;
    
    @Autowired
    private Provider<Boundary> boundaryProvider;

    public String getMessage() {
        Boundary boundaryBean = boundaryProvider.get();
        return boundaryBean.message(message);
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }

}
