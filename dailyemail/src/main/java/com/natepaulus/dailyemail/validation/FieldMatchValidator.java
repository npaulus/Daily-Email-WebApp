package com.natepaulus.dailyemail.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;


/**
 * The Class FieldMatchValidator.
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object>
{
    
    /** The first field name. */
    private String firstFieldName;
    
    /** The second field name. */
    private String secondFieldName;

    /* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
     */
    @Override
    public void initialize(final FieldMatch constraintAnnotation)
    {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    /* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context)
    {
        try
        {
            final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);
            
            String errorMessage = "Fields do not match!";
            
            if(firstFieldName.equals("password")){
            	errorMessage = "Passwords entered do not match!";
            } else {
            	errorMessage = "Emails entered do not match!";
            }
            
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate( errorMessage ).addNode(firstFieldName).addConstraintViolation();
            return firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        }
        catch (final Exception ignore)
        {
            // ignore
        }
        return true;
    }
}