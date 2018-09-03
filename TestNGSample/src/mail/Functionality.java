package mail;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A Custom Annotation to inject additional information into a TestNG Test
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Functionality {
    
    /**
     * Service.
     *
     * @return the string
     */
  public  String[] module() default {};
    
   public String[] story() default {};

    
}