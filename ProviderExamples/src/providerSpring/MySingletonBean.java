package providerSpring;

import javax.inject.Provider;

import org.springframework.beans.factory.annotation.Autowired;

public class MySingletonBean {

    @Autowired
    private Provider<MyPrototypeBean> myPrototypeBeanProvider;

    public void showMessage () {
        MyPrototypeBean bean = myPrototypeBeanProvider.get();
        System.out.println("Hi, the time is " + bean.getDateTime());
    }
}
