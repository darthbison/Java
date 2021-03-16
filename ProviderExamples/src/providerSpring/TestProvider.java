package providerSpring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

//@Configuration
public class TestProvider {

    
    @Bean
    public Boundary boundaryBean() {
        return new Boundary();
    }    
    
    @Bean
    public Index myIndex() {
        return new Index();
    }
    
    public static void main(String[] args) {
        
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(TestProvider.class);
        Index indexBean = context.getBean(Index.class);
        indexBean.setMessage("Hello");
        System.out.println(indexBean.getMessage());
        context.close();
    }

}
