package com.interworks.inspektar.annotations.base;

import java.util.List;

import mk.com.interworks.domain.model.AnnotationEntity;

public interface IAnnotationsLayerContract {
    void displayAnnotation(AnnotationEntity model);
    void displayAnnotations(List<AnnotationEntity> lsAnnotations);
}
