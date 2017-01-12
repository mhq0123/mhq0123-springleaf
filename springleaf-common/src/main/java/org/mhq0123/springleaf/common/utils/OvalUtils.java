package org.mhq0123.springleaf.common.utils;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * project: springleaf
 * author:  mhq0123
 * date:    2017/1/12.
 * desc:    校验框架
 */
public class OvalUtils {

    private static final Logger logger = LoggerFactory.getLogger(OvalUtils.class);

    /**
     * 校验
     * @param object
     */
    public static void validate(Object object) {

        Validator validator = new Validator();
        List<ConstraintViolation> violations = validator.validate(object);
        if(null != violations && !violations.isEmpty()) {
            ConstraintViolation constraintViolation = violations.get(0);
            logger.error(">>>>>>>>>>>>>>field:{},value:{},errorMessage:{}", constraintViolation.getCheckName(), constraintViolation.getInvalidValue(), constraintViolation.getMessage());
            throw new IllegalArgumentException(constraintViolation.getMessage());
        }
    }

    /**
     * 校验
     * @param object
     */
    public static void validate(Object object, String... profiles) {

        Validator validator = new Validator();
        List<ConstraintViolation> violations = validator.validate(object, profiles);
        if(null != violations && !violations.isEmpty()) {
            ConstraintViolation constraintViolation = violations.get(0);
            logger.error(">>>>>>>>>>>>>>field:{},value:{},errorMessage:{}", constraintViolation.getCheckName(), constraintViolation.getInvalidValue(), constraintViolation.getMessage());
            throw new IllegalArgumentException(constraintViolation.getMessage());
        }
    }
}
