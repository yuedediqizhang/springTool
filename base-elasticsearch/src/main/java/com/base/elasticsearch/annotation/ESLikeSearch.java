package com.base.elasticsearch.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在需要检索的字段上使用，
 * scene、value 用来区分业务场景,scene + id 生成在es中唯一id
 * fieldIdName 指定该类下的主键字段名，空的表示由es随机生成
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ESLikeSearch {
    /**
     * 应用于不同业务在一个index下的情况区分，
     * 如果是一个业务单独使用的index，可以不填
     * 例如：商品名称和文章标题都在一个index内的情况 ， 可以用1表示商品，2表示文章
     */
    String scene () default "0";

    /** 使用的主键字段名，用fieldIdName名字的字段值当主键的一个参数，如果不填fieldIdName由es随机生成主键
     *  例如：类{“egId”=1000L};
     * @ESLikeSearch(scene = “1”,fieldIdName = "egId")
     *  生成的主键就是11000
     */
    String fieldIdName() default "";
}
