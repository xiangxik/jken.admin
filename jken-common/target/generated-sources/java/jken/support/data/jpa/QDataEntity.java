/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.398+08:00
 */

package jken.support.data.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDataEntity is a Querydsl query type for DataEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QDataEntity extends EntityPathBase<DataEntity<?, ? extends java.io.Serializable>> {

    private static final long serialVersionUID = -1307519967L;

    public static final QDataEntity dataEntity = new QDataEntity("dataEntity");

    public final org.springframework.data.jpa.domain.QAbstractAuditable _super = new org.springframework.data.jpa.domain.QAbstractAuditable(this);

    //inherited
    public final SimplePath<Object> createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final SimplePath<java.io.Serializable> id = createSimple("id", java.io.Serializable.class);

    //inherited
    public final SimplePath<Object> lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QDataEntity(String variable) {
        super((Class) DataEntity.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QDataEntity(Path<? extends DataEntity> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QDataEntity(PathMetadata metadata) {
        super((Class) DataEntity.class, metadata);
    }

}

