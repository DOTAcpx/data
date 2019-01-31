package com.cn.jm.web.core.valid.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cn.jm.web.core.valid.rule.JMVaildNull;
import com.cn.jm.web.core.valid.rule.JMVaildRule;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(JMRulesVaild.class)
public @interface JMRuleVaild {

	Class<? extends JMVaildRule> ruleClass() default JMVaildNull.class;

	String[] fields() default {};

	String[] exclusive() default {};

	boolean isAll() default false;

	String[] maxlength() default {};

	String[] minlength() default {};

	String[] regex() default {};

}
